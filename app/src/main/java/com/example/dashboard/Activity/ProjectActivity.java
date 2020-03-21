package com.example.dashboard.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dashboard.Adapter.ProjectAdapter;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.dashboard.Resources.Resource;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProjectActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView addProject;
    private ImageView usuarioApp;
    private TextView textViewApp;
    private JSONObject respuestaConsulta;
    private List list;
    private int flag;
    private SearchView searchViewProject;
    private CardView cardViewUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        getSupportActionBar().hide();
        /*DATA VIEW*/
        addProject = findViewById(R.id.addProject);
        /*DATA BASE*/
        list = new ArrayList();
        //initialComponentsActivity();
        list.add(new Project("Project 01"));
        list.add(new Project("Project 02"));
        list.add(new Project("Project 03"));
        list.add(new Project("Project 04"));
        list.add(new Project("Project 05"));
        addProject.setColorFilter(Color.WHITE);
        cardViewUsuario = (CardView) findViewById(R.id.cardUsuario);
        searchViewProject = findViewById(R.id.searchProject);
        recyclerView = (RecyclerView) findViewById(R.id.recicler_project);
        recyclerView.setHasFixedSize(true);
        textViewApp = findViewById(R.id.textApp);
        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new ProjectAdapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);
        usuarioApp = (ImageView) findViewById(R.id.usuarioApp);
        Glide.with(this).load(Resource.urlImageUserLogin).into(usuarioApp);
        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAddDialog();
            }
        });
        searchViewProject.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchViewProject.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        /*DATA BASE*/
        searchViewProject.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewUsuario.setVisibility(View.GONE);
                textViewApp.setVisibility(View.GONE);

            }
        });
        searchViewProject.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                cardViewUsuario.setVisibility(View.VISIBLE);
                textViewApp.setVisibility(View.VISIBLE);
                return false;
            }
        });

    }

    private void ShowAddDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("NEW PROJECT: ");
        dialog.setMessage("Insert Project's Data: " );
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(this);
        final View add_layout = inflater.inflate(R.layout.project_structure_data,null);

        final TextInputEditText editDescription = add_layout.findViewById(R.id.txt_descriptionProject_1);
        final TextInputEditText editName = add_layout.findViewById(R.id.txt_nameProject_1);

        dialog.setView(add_layout);
        dialog.setPositiveButton("ADD PROJECT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Project project = new Project(editName.getText().toString(),editDescription.getText().toString());
                adapter.addElement(project);
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void setProjects(){
        MediaType MEDIA_TYPE =
                MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(

                60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("usuario", Resource.emailUserLogin);
            System.out.println(postdata.toString());

        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url("http://192.168.137.1:5000/information") /*URL ... INDEX PX DE WILMER*/
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Wilmer ERROR :" + e);
                //auxOriginal = myFilters.filterRGB();
            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    System.out.println(responseData);
                    try {
                        respuestaConsulta = new JSONObject(responseData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void initialComponentsActivity(){
        setProjects();
        if(respuestaConsulta!=null){
            try {
                JSONArray jsonArray = respuestaConsulta.getJSONArray("proyectos");
                for(int i = 0; i<jsonArray.length();i++){
                    JSONObject aux = jsonArray.getJSONObject(i);
                    String name = aux.getString("carpeta");
                    list.add(new Project(name));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*myListSegmentation.loadImage(myListSegmentation.decodeBase64AndSetImage(image));
            JSONObject information = landMarks.getJSONObject("information");
            JSONArray jsonArray = information.getJSONArray("landmarks");
            float imageX = Float.parseFloat(information.getString("imageX"));
            float imagey = Float.parseFloat(information.getString("imagey"));
            myListSegmentation.readDataSegments(jsonArray,imageX,imagey);*/
        }
    }
}
