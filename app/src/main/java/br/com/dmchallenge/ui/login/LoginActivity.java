package br.com.dmchallenge.ui.login;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.com.dmchallenge.R;
import br.com.dmchallenge.activity.MainActivity;
import br.com.dmchallenge.databinding.ActivityLoginBinding;
import br.com.dmchallenge.model.LoggedInUser;
import dagger.hilt.android.AndroidEntryPoint;

import static br.com.dmchallenge.utils.Constants.CLIENT_ID;
import static br.com.dmchallenge.utils.Constants.CLIENT_SECRET;
import static br.com.dmchallenge.utils.Constants.MY_UNGUESSABLE_STRING;
import static br.com.dmchallenge.utils.Constants.REDIRECT_URI;
import static br.com.dmchallenge.utils.Constants.REQUEST_GITHUB_LOGIN;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Para animação suave do teclado (setar na raiz do xml correspondente "animateLayoutChanges=true")
        LayoutTransition layoutTransition = binding.rootLoginScrollview.getLayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            binding.btnLogin.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                binding.usernameLayout.setErrorEnabled(true);
                binding.usernameLayout.setError(getString(loginFormState.getUsernameError()));
            } else {
                binding.usernameLayout.setErrorEnabled(false);
            }
            if (loginFormState.getPasswordError() != null) {
                binding.passwordLayout.setErrorEnabled(true);
                binding.passwordLayout.setError(getString(loginFormState.getPasswordError()));
            } else {
                binding.passwordLayout.setErrorEnabled(false);
            }
        });

        loginViewModel.getLoggedInUser().observe(this, user -> {
            if(binding.loading.getVisibility() == View.VISIBLE) {
                binding.loading.setVisibility(View.GONE);
            }
            if (user != null) {
                //updateUiWithUser(loggedInUser);
            } else {
                showLoginFailed(R.string.login_falhou);
            }
        });

        loginViewModel.getAuthResult().observe(this, authResult -> {
            if (authResult == null) {
                return;
            }
            if (authResult.getError() != null) {
                showLoginFailed(authResult.getError());
            }
            if (authResult.getSuccess() != null) {
//                LoggedInUser loggedInUser = authResult.getSuccess();
                fetchUserInformation();
            }
            setResult(Activity.RESULT_OK);
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(binding.username.getText().toString(),
                        binding.password.getText().toString());
            }
        };

        binding.username.addTextChangedListener(afterTextChangedListener);
        binding.password.addTextChangedListener(afterTextChangedListener);
        binding.password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                loginViewModel.login();
            }
            return false;
        });

        binding.btnLogin.setOnClickListener(v -> {
            binding.loading.setVisibility(View.VISIBLE);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize" + "?client_id=" + CLIENT_ID + "&scope=repo&state=" + MY_UNGUESSABLE_STRING + "&redirect_uri=" + REDIRECT_URI)).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, REQUEST_GITHUB_LOGIN);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_GITHUB_LOGIN) {
            if(resultCode == Activity.RESULT_OK) {
                Uri uri = getIntent().getData();
                if (uri != null && uri.toString().startsWith(REDIRECT_URI)) {
                    String token = uri.getQueryParameter("code");
                    loginViewModel.auth(CLIENT_ID, CLIENT_SECRET, token);
                }
            }
        }
    }

    private void updateUiWithUser(LoggedInUser user) {
        String welcome = getString(R.string.bem_vindo) + user.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    private void fetchUserInformation() {
        loginViewModel.fetchLoggedUserInformation();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}