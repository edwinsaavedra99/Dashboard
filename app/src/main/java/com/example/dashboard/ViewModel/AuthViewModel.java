package com.example.dashboard.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.dashboard.Models.User;
import com.example.dashboard.Repository.LoginRepository;

public class AuthViewModel extends ViewModel{
    private MutableLiveData<User> user;
    private final LoginRepository loginRepository;

    public AuthViewModel(){
        loginRepository = LoginRepository.getInstance();
    }

    public LiveData<User> getUser(User _user) {
        if (user == null) {
            user = new MutableLiveData<>();
            loadUser(_user);
        }
        return user;
    }

    private void loadUser(User _user) {
        user = loginRepository.checkEmail(_user);
    }
}
