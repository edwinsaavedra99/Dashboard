package com.example.dashboard.Services;

import org.json.JSONObject;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ApiCall {

    private static String URL_BASE = "http://34.204.87.129:5000/";

    //GET network request
    public static Call GET(String url) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(
                60,TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(URL_BASE + url)
                .addHeader("Content-Type", "application/json")
                .build();
        return client.newCall(request);
    }

    //POST network request
    public static Call POST(JSONObject postData,String url){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(
                60,TimeUnit.SECONDS).build();
        RequestBody body = RequestBody.create(MEDIA_TYPE,postData.toString());
        Request request = new Request.Builder().url(URL_BASE + url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        return client.newCall(request);
    }

}
