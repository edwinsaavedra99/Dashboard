package com.example.dashboard.Repository;

import android.app.Application;
import android.content.Intent;
import androidx.lifecycle.MutableLiveData;
import com.example.dashboard.Models.User;
import com.example.dashboard.Resources.Resource;
import com.example.dashboard.Services.ApiCall;
import com.example.dashboard.Utils.JSONUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginRepository {

    private static LoginRepository INSTANCE;
    private JSONUtils<User> jsonUtils;
    GoogleSignInClient mGoogleSignInClient;


    public synchronized static LoginRepository getInstance(Application application) {
        if (INSTANCE == null)
            INSTANCE = new LoginRepository(application);
        return INSTANCE;
    }

    private LoginRepository(Application application) {
        super();
        jsonUtils = new JSONUtils<>();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(application, gso);
    }

    public GoogleSignInClient getMGoogleSignInClient(){
        return mGoogleSignInClient;
    }

    public MutableLiveData<GoogleSignInAccount> login(Intent data){
        MutableLiveData<GoogleSignInAccount> taskMLD = new MutableLiveData<>();
        GoogleSignIn.getSignedInAccountFromIntent(data).addOnSuccessListener(taskMLD::postValue);
        return taskMLD;
    }

    public MutableLiveData<Boolean> logout(){
        MutableLiveData<Boolean> taskMLD = new MutableLiveData<>();
        mGoogleSignInClient.signOut().addOnCompleteListener(task -> taskMLD.postValue(true));
        return taskMLD;
    }


    public MutableLiveData<User> checkEmail(User _user){
        MutableLiveData<User> authenticatedUserMutableLiveData = new MutableLiveData<>();
        ApiCall.POST(jsonUtils.Mapper(_user),"login").enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                User userFailed = new User();
                userFailed.setLogin(false);
                e.printStackTrace();
                authenticatedUserMutableLiveData.postValue(userFailed);
            }
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    Resource.emailUserLogin = _user.getEmail();
                    Resource.nameUserLogin = _user.getName();
                    Resource.urlImageUserLogin = _user.getPhotoUrl();
                    _user.setLogin(true);
                    authenticatedUserMutableLiveData.postValue(_user);
                }
            }
        });
        return authenticatedUserMutableLiveData;
    }

}
