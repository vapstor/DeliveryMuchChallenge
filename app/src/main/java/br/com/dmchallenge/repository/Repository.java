package br.com.dmchallenge.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import br.com.dmchallenge.model.AccessToken;
import br.com.dmchallenge.model.LoggedInUser;
import br.com.dmchallenge.model.Repo;
import br.com.dmchallenge.model.RepoResponse;
import br.com.dmchallenge.network.ApiService;
import br.com.dmchallenge.network.AuthService;
import br.com.dmchallenge.room.MyRoomDatabase;
import br.com.dmchallenge.room.RepoDAO;
import br.com.dmchallenge.room.UserDAO;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;

import static br.com.dmchallenge.utils.Constants.MY_UNGUESSABLE_STRING;
import static br.com.dmchallenge.utils.Constants.REDIRECT_URI;

public class Repository {
    private final MyRoomDatabase database;
    private final UserDAO userDAO;
    private final MutableLiveData<String> accessTokenLiveData;
    private ApiService apiService;
    private RepoDAO repoDAO;
    private AuthService authService;
    private MutableLiveData<LoggedInUser> loggedInUserLiveData;

    @Inject
    public Repository(ApiService apiService, AuthService authService, Application application, MutableLiveData<String> accessTokenLiveData) {
        this.database = MyRoomDatabase.getDatabase(application);
        this.repoDAO = database.repoDAO();
        this.userDAO = database.userDAO();
        this.apiService = apiService;
        this.authService = authService;
        this.accessTokenLiveData = accessTokenLiveData;
    }

    public LiveData<LoggedInUser> getUser() {
        //refreshUser(userId);
        // Returns a LiveData object directly from the database.
        return userDAO.getLoggedUser();
    }

    private void refreshUser() {
        // Runs in a background thread.
//        ((Executor) command -> {
//            // Check if user data was fetched recently.
//            boolean userExists = userDAO.hasUser(FRESH_TIMEOUT);
//            if (!userExists) {
//                // Invalidate User
//                userDao.save(response.body() !!)
//            }
//        }).execute();
    }

    //Seta token de acesso em um livedata no modulo para evitar ficar enviando em toda request
    public void setAccessToken(String token) {
        accessTokenLiveData.setValue(token);
    }

    public MutableLiveData<LoggedInUser> getLoggedInUserLiveData() {
        if (loggedInUserLiveData == null) {
            loggedInUserLiveData = new MutableLiveData<>();
        }
        return loggedInUserLiveData;
    }

    public void setLoggedInUserLiveData(LoggedInUser user) {
        loggedInUserLiveData.setValue(user);
    }

    public Observable<RepoResponse> getRepos() {
        return apiService.getRepos();
    }

    public Observable<AccessToken> getAccessToken(String clientId, String clientSecret, String code) {
        return authService.getAccessToken(clientId, clientSecret, code, REDIRECT_URI, MY_UNGUESSABLE_STRING);
    }

    public Observable<ResponseBody> fetchLoggedUserInfo() {
        return apiService.getLoggedUserInfo();
    }

    public Observable<LoggedInUser> getUserInfo(String username) {
        return apiService.getUserInfoByUsername(username);
    }

    public void insertUser(LoggedInUser user) {
        userDAO.insert(user);
    }
//
//    public void deletePokemon(String pokemonName){
//        repoDAO.deletePokemon(pokemonName);
//    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Repo repo) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> repoDAO.insert(repo));
    }

    public void deleteAll() {
        repoDAO.deleteAll();
    }

    public LiveData<List<Repo>> getAlphabetizedRepos() {
        return repoDAO.getAlphabetizedRepos();
    }
}
