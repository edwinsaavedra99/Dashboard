package com.example.dashboard.ViewModel;

import android.app.Application;
import android.content.Intent;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.dashboard.Models.User;
import com.example.dashboard.Repository.LoginRepository;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class AuthViewModel extends AndroidViewModel {
    private MutableLiveData<User> user;
    private MutableLiveData<Boolean> operation;
    private final LoginRepository loginRepository;

    public AuthViewModel(Application application){
        super(application);
        loginRepository = LoginRepository.getInstance(application);
    }

    public LiveData<User> getUser(User _user) {
        if (user == null) {
            user = new MutableLiveData<>();
            loadUser(_user);
        }
        return user;
    }

    public LiveData<Boolean> logout(){
        if(operation == null){
            operation = loginRepository.logout();
        }
        return operation;
    }

    public LiveData<GoogleSignInAccount> login(Intent data){
        return loginRepository.login(data);
    }

    private void loadUser(User _user) {
        user = loginRepository.checkEmail(_user);
    }

    public GoogleSignInClient getGoogleSIC(){
        return loginRepository.getMGoogleSignInClient();
    }

}
