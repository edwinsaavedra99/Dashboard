package com.example.dashboard.Repository;

import androidx.lifecycle.MutableLiveData;
import com.example.dashboard.Models.User;
import com.example.dashboard.Resources.Resource;
import com.example.dashboard.Services.ApiCall;
import com.example.dashboard.Utils.JSONUtils;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginRepository {

    private static LoginRepository INSTANCE;
    private JSONUtils<User> jsonUtils;


    public synchronized static LoginRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new LoginRepository();
        return INSTANCE;
    }

    private LoginRepository() {
        super();
        jsonUtils = new JSONUtils<>();
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
