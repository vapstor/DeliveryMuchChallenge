package br.com.dmchallenge.ui.login;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;

import br.com.dmchallenge.R;
import br.com.dmchallenge.activity.MainActivity;
import br.com.dmchallenge.databinding.ActivityLoginBinding;
import br.com.dmchallenge.model.LoggedInUser;
import dagger.hilt.android.AndroidEntryPoint;

import static br.com.dmchallenge.utils.Constants.CLIENT_ID;
import static br.com.dmchallenge.utils.Constants.CLIENT_SECRET;
import static br.com.dmchallenge.utils.Constants.MY_UNGUESSABLE_STRING;
import static br.com.dmchallenge.utils.Constants.REDIRECT_URI;

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

        loginViewModel.getLoggedInUser().observe(this, user -> {
            toggleButtonAndProgressBar(false);
            if (user != null) {
                startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
                fetchUserInformation(authResult.getSuccess());
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            toggleButtonAndProgressBar(true);
            Intent intent =
                    new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://github.com/login/oauth/authorize"
                                    + "?client_id=" + CLIENT_ID
                                    + "&scope=repo"
                                    + "&state=" + MY_UNGUESSABLE_STRING
                                    + "&redirect_uri=" + REDIRECT_URI
                            )).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(REDIRECT_URI)) {
            /**Workaround para não resetar estado na tela ao voltar*/
            if (binding.loading.getVisibility() != View.VISIBLE) {
                toggleButtonAndProgressBar(true);
            }
            String token = uri.getQueryParameter("code");
            loginViewModel.auth(CLIENT_ID, CLIENT_SECRET, token);
        }
    }

    private void fetchUserInformation(LoggedInUser user) {
        loginViewModel.fetchLoggedUserInformation(user);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void toggleButtonAndProgressBar(boolean isLoading) {
        final ViewGroup root = binding.rootLoginScrollview;
        TransitionManager.beginDelayedTransition(root, new Fade());
        if (isLoading) {
            binding.loading.setVisibility(View.VISIBLE);
            binding.btnAnonimo.setVisibility(View.INVISIBLE);
            binding.btnLogin.setVisibility(View.INVISIBLE);
        } else {
            binding.loading.setVisibility(View.INVISIBLE);
            binding.btnAnonimo.setVisibility(View.VISIBLE);
            binding.btnLogin.setVisibility(View.VISIBLE);
        }
    }
}