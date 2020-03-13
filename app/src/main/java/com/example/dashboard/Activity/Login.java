package com.example.dashboard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dashboard.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Login extends AppCompatActivity {
    private long animationDuration = 2000;
    LinearLayout linearLayout;
    TextView textView;
    ImageView imageView;
    private static final int RC_SIGN_IN = 0;
    private static final String TAG ="Error" ;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = findViewById(R.id.sign_in_button);
        linearLayout = findViewById(R.id.liner_layout_sign);
        textView = findViewById(R.id.txt_title);
        imageView = findViewById(R.id.imageView_hand);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });
        handleAnimation();
    }

    @Override
    protected  void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(GoogleSignInAccount account) {
        if(account == null){
            Toast.makeText(this, "no se encontraron usuarios anteriores", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(Login.this, PatientsActivity.class);
            startActivity(intent);
            // onExploredClicked();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            e.printStackTrace();
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "error en el manejador", Toast.LENGTH_SHORT).show();
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    public void handleAnimation(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView,View.ALPHA,0.0f,1.0f);
        animator.setDuration(animationDuration);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(textView,View.ALPHA,0.0f,1.0f);
        animator2.setDuration(animationDuration);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(linearLayout,View.ALPHA,0.0f,1.0f);
        animator3.setDuration(animationDuration);
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(animator,animator2,animator3);
        animationSet.start();
    }

}
