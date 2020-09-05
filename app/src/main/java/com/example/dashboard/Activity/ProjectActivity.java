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
import android.view.ContextThemeWrapper;
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
import com.example.dashboard.Adapter.PatientAdapter;
import com.example.dashboard.Adapter.ProjectAdapter;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private List list;
    Button btn_crear_proyecto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        MaterialToolbar toolbar =findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_crear_proyecto = findViewById(R.id.btn_crear_proyecto);
        recyclerView = (RecyclerView) findViewById(R.id.recicler_project);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        getInfoMedicine();

        btn_crear_proyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAddDialog();
            }
        });
    }

    private void ShowAddDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Nuevo Proyecto");
        dialog.setMessage("Ingrese el Nombre del Proyecto" );
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(this);
        final View add_layout = inflater.inflate(R.layout.project_structure_data,null);
        final TextInputEditText editName = add_layout.findViewById(R.id.txt_nameProject_1);
        dialog.setView(add_layout);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editName.getText().toString().trim();
                if(name.length() != 0){
                    Project project = new Project(editName.getText().toString(),"");
                    addProjectService(Resource.emailUserLogin,Resource.idPacient,project.getNameProject());
                }else{
                    editName.setError("Error ...");
                    editName.requestFocus();
                }
            }
        });
        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void addProjectService(String email, int patient, final String record){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(
                60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("email",email);
            postdata.put("patient",patient);
            postdata.put("record",record);
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url(getString(R.string.url)+"medicine/createrecord")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responseData.equals("error")){
                                Toast.makeText(getApplicationContext(),"The name already exists",Toast.LENGTH_SHORT).show();
                            }else{
                                Project project = new Project(record,"");
                                adapter.addElement(project);
                            }
                        }
                    });
                    System.out.println("*****"+responseData);

                }
            }
        });
    }
    public void getInfoMedicine(){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("email", Resource.emailUserLogin);
            postdata.put("dni",Resource.idPacient);
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url(getString(R.string.url)+"medicine/selectrecords") /*URL ... INDEX PX DE WILMER*/
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    System.out.println("-------**********-----------"+responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Resource.infoMedicine =  new JSONObject(responseData);;
                                if(Resource.infoMedicine!=null){
                                    list = new ArrayList();
                                    try {
                                        JSONArray jsonArray = Resource.infoMedicine.getJSONArray("records");
                                        for(int i = 0; i<jsonArray.length();i++){
                                            list.add(new Project(jsonArray.get(i).toString()));
                                        }
                                        adapter =  new ProjectAdapter(list,ProjectActivity.this);
                                        recyclerView.setAdapter(adapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

}
