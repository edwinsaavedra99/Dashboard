package com.example.dashboard.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StringUtil {

    public static String loadFromAsset(String flName, Context context){
        String string = null;
        try{
            InputStream is = context.getAssets().open(flName);
            int size =  is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            string =new String(buffer,"UTF-8");

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return string;
    }
    public static Bitmap decodeBase64AndSetImage(String complete){
        String image = complete.substring(complete.indexOf(",")+1);
        InputStream stream = new ByteArrayInputStream(Base64.decode(image.getBytes(),Base64.DEFAULT));
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        return bitmap;
    }

}
