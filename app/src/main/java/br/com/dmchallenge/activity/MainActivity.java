package br.com.dmchallenge.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.dmchallenge.R;
import br.com.dmchallenge.ui.main.RepoFragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RepoFragment.newInstance())
                    .commitNow();
        }
    }
}