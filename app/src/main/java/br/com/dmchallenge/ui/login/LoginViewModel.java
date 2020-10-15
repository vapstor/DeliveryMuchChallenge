package br.com.dmchallenge.ui.login;

import android.util.Log;
import android.util.Patterns;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

import br.com.dmchallenge.R;
import br.com.dmchallenge.model.AccessToken;
import br.com.dmchallenge.model.LoggedInUser;
import br.com.dmchallenge.repository.Repository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LoginViewModel extends ViewModel {
    private final String TAG = "LoginViewModel";
    private Repository repository;
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<AuthResult> authResult = new MutableLiveData<>();
    private MutableLiveData<LoggedInUser> loggedInUser = new MutableLiveData<>();

    @ViewModelInject
    public LoginViewModel(Repository repository) {
        this.repository = repository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<AuthResult> getAuthResult() {
        return authResult;
    }

    MutableLiveData<LoggedInUser> getLoggedInUser() {
        return loggedInUser;
    }

    public void auth(String CLIENT_ID, String CLIENT_SECRET, String code) {
        // can be launched in a separate asynchronous job

//        Observable<AccessToken> accessTokenCall = this.repository.getAccessToken(CLIENT_ID, CLIENT_SECRET, token);
        this.repository.getAccessToken(CLIENT_ID, CLIENT_SECRET, code)
                .subscribeOn(Schedulers.io())
                .map(new Function<AccessToken, AuthResult>() {

                    @Override
                    public AuthResult apply(AccessToken accessToken) throws Throwable {
                        if (accessToken.getToken() != null && !accessToken.getToken().equals("")) {
                            return new AuthResult(new LoggedInUser(accessToken));
                        } else {
                            throw new Exception("Token Nulo!");
                        }

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            //Atualiza no módulo para ficar registrado no live data o token e podermos fazer consultas autorizadas
                            LoggedInUser user = result.getSuccess();
                            if (user != null) {
                                this.repository.setAccessToken(user.getAccessToken().getToken());
                            }
                            this.authResult.setValue(result);
                        },

                        error -> {
                            Log.e(TAG, "Error" + error);
                            this.authResult.setValue(new AuthResult(R.string.login_falhou));
                        }
                );
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public void fetchLoggedUserInformation() {
        this.repository.fetchLoggedUserInfo()
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, LoggedInUser>() {
                         @Override
                         public LoggedInUser apply(ResponseBody body) throws Throwable {
                             JSONObject json = new JSONObject(body.string());
                             Log.d(TAG, "Sucesso");
                             LoggedInUser user = getLoggedInUser().getValue();
                             if (user != null) {
                                 user.setUserId(json.getString("id"));
                                 user.setDisplayName(json.getString("name"));
                                 user.setEmail(json.getString("email"));
                                 user.setUsername(json.getString("login"));
                                 user.setAvatarUrl(json.getString("avatar_url"));
                                 return user;
                             } else {
                                 throw new Exception("Erro na configuração do usuário");
                             }
                         }
                     }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> loggedInUser.setValue(result),
                        error -> {
                            Log.e(TAG, "Ocorreu um erro fetchLoggedUserInformation(): " + error.getMessage());
                            loggedInUser.setValue(null);
                        });
    }
}