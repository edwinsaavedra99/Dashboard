package com.example.dashboard.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {
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
        getSupportActionBar().hide();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                //.requestIdToken(getString(R.string.default_web_client_id))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = findViewById(R.id.sign_in_button);
        linearLayout = findViewById(R.id.liner_layout_sign);
        textView = findViewById(R.id.txt_title);
        imageView = findViewById(R.id.imageView_hand);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        //se implementara u metodo de revoque acces y ademas un boton de alir
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
            Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
        }else {
            Resource.emailUserLogin = account.getEmail();
            Resource.nameUserLogin = account.getDisplayName();
            Resource.urlImageUserLogin = account.getPhotoUrl()+"";

            //Llamar al servicio - este servicio veriica el correo electronico
            // - si no hay data crea las carpetas en caso contrario - devulve toda su estructura
            checkEmailService(account.getEmail());
            Intent intent = new Intent(Login.this, HomeActivity.class);
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
            updateUI(account); // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            e.printStackTrace(); // The ApiException status code indicates the detailed failure reason.
            Toast.makeText(this, "Error in GoogleSignIn", Toast.LENGTH_SHORT).show(); // Please refer to the GoogleSignInStatusCodes class reference for more information.
            updateUI(null);
        }
    }
    public void handleAnimation(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView,View.ALPHA,0.0f,1.0f);
        long animationDuration = 2000;
        animator.setDuration(animationDuration);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(textView,View.ALPHA,0.0f,1.0f);
        animator2.setDuration(animationDuration);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(linearLayout,View.ALPHA,0.0f,1.0f);
        animator3.setDuration(animationDuration);
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(animator,animator2,animator3);
        animationSet.start();
    }

    public void checkEmailService(String email){

        MediaType MEDIA_TYPE =
                MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(
                60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("email",email);
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url(getString(R.string.url)+"login")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    System.out.println("*****"+responseData);

                }
            }
        });
    }

}
