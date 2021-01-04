package com.example.dashboard.Utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils<T> {

    public JSONUtils(){

    }

    public JSONObject Mapper(T entity){
        String jsonInString = new Gson().toJson(entity);
        JSONObject postData = null;
        try {
            postData = new JSONObject(jsonInString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postData;
    }
}
