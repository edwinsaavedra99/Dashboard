package com.example.dashboard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.example.dashboard.Models.User;
import com.example.dashboard.R;
import com.example.dashboard.ViewModel.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0;
    Button signInButton;
    AuthViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(AuthViewModel.class);
        setContentView(R.layout.activity_login);
        signInButton = findViewById(R.id.btn_gmail);
        signInButton.setOnClickListener(v -> signIn());
    }

    private void signIn() {
        Intent signInIntent = model.getGoogleSIC().getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(GoogleSignInAccount account) {
        if(account != null){
            User user = new User(account.getEmail(),account.getDisplayName(),account.getPhotoUrl()+"");
            model.getUser(user).observe(this, data -> {
                if(data.isLogin()){
                    Intent intent = new Intent(Login.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(Login.this, "Bienvenido !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && data != null) {
            model.login(data).observe(this, this::updateUI);
        }
    }
}
