package com.example.dashboard.Services;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class FiltersService {

   public static String getFilters(final String imageBase64, String nameFilter) throws IOException {
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(
                60,TimeUnit.SECONDS).build();
        JSONObject postData = new JSONObject();
        try {
            postData.put("image", imageBase64);
            postData.put("type", nameFilter);
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,postData.toString());
        return ApiCall.POST(client,"filters",body);
    }
}
