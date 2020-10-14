package br.com.dmchallenge.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.dmchallenge.R;
import br.com.dmchallenge.ui.login.LoginActivity;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(() -> {
            try {
                sleep(1500);
                runOnUiThread(() -> {
                    Intent intent = new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}