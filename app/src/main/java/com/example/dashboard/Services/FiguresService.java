package com.example.dashboard.Services;

import com.example.dashboard.Models.FigureModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.example.dashboard.ListFigures.ListFigure;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class FiguresService {
    public static String sendFigures(final String imageBase64, ListFigure listFigure) throws IOException {
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(
                60,TimeUnit.SECONDS).build();
        JSONObject postData = new JSONObject();
        try {
            postData.put("image", imageBase64);
            FigureModel figureModel = new FigureModel();
            postData.put("information", figureModel.setJsonObjectFigures(listFigure));
            System.out.println(postData.toString());

        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,postData.toString());
        return ApiCall.POST(client,"figures",body);
    }

    public static String readFigures() throws IOException {
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(
                60,TimeUnit.SECONDS).build();
        JSONObject postData = new JSONObject();
        RequestBody body = RequestBody.create(MEDIA_TYPE,postData.toString());
        return ApiCall.GET(client,"figures");
    }
}
