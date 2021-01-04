package com.example.dashboard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dashboard.Models.User;
import com.example.dashboard.R;
import com.example.dashboard.ViewModel.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SplashActivity extends AppCompatActivity {
    AuthViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(AuthViewModel.class);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected  void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        redirectUI(account);
    }

    private void redirectUI(GoogleSignInAccount account) {
        if(account == null){
            Intent intent = new Intent(SplashActivity.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }else {
            User user = new User(account.getEmail(),account.getDisplayName(),account.getPhotoUrl()+"");
            model.getUser(user).observe(this, data -> {
                if(data.isLogin()){
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    Toast.makeText(SplashActivity.this, "Bienvenido !", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}