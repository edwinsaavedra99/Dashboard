package com.example.dashboard;

import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Files {
    //Load and Save data
    private String nameFile;
    private String nameBinder;
    private File file;

    public Files(String nameFile, String nameBinder) throws IOException {
        this.nameFile = nameFile;
        this.nameBinder = nameBinder;
        createJSONFile();
    }
    public void createJSONFile() throws IOException {
        String file_path = (Environment.getExternalStorageState()+nameBinder);
        File localFile = new File(file_path);
        if (!localFile.getParentFile().exists()) {
            localFile.getParentFile().mkdir();
            System.out.println("test1");
        }
        if(!localFile.exists())
            localFile.createNewFile();
        this.file = new File(localFile,nameFile);
        try{
            System.out.println("test2");
            file.createNewFile();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void writeJSONFile(String data){
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try{
            fileWriter = new FileWriter(file);
            printWriter = new PrintWriter(fileWriter);
            printWriter.print(data);
            printWriter.flush();
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public  void readJSONFile(File file){
        //IN PROGRESS
    }

}
