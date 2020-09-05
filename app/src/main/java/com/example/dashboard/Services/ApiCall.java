package com.example.dashboard.Services;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class ApiCall {

    //GET network request
    static String GET(OkHttpClient client, String url) throws IOException {
        Request request = new Request.Builder()
                .url("http://192.168.12.97:5000/"+url)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    //POST network request
    static String POST(OkHttpClient client, String url, RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .url("http://192.168.12.97:80/"+url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();

    }

}
