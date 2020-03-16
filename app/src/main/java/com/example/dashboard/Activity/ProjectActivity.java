package com.example.dashboard.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button addProject;

    protected static final int PICK_IMAGE =0;
    protected static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageurl;
    private ImageView import_;
    private AlertDialog dialog_image;
    private ImageView camera;
    private ImageView galery;
    private TextView txt_camera;
    private  TextView txt_galery;
    private int flagimage =0;
    private String currentPhotoPath;
    private ImageButton imageButton;
    private JSONObject respuestaConsulta;
    private List list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        getSupportActionBar().hide();
        /*DATA VIEW*/
        addProject = findViewById(R.id.addProject);
        /*DATA BASE*/
        list = new ArrayList();
        initialComponentsActivity();

        //backPatient.setColorFilter(Color.parseColor("#00BB2D"));
        addProject.setBackgroundColor(Color.parseColor("#80000000"));
        recyclerView = (RecyclerView) findViewById(R.id.recicler_project);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new ProjectAdapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAddDialog();
            }
        });


    }

    private void ShowAddDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("NEW PROJECT: ");
        dialog.setMessage("Insert Project's Data: " );
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(this);
        View add_layout = inflater.inflate(R.layout.data_project,null);
        final TextInputEditText editDescription = add_layout.findViewById(R.id.txt_description);
        imageButton = add_layout.findViewById(R.id.addImageProject);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent gallery = new Intent();
                    gallery.setType("image/*");
                    gallery.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(gallery, "Select picture"), 4);
                    Toast.makeText(getApplicationContext(), "image from galery", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    Toast.makeText(getApplicationContext(),"Ups :)",Toast.LENGTH_SHORT).show();

                }
            }
        });
        dialog.setView(add_layout);
        dialog.setPositiveButton("START PROJECT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                getApplicationContext().startActivity(intent);

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 4 && resultCode == RESULT_OK){
            int flag = 0;
            imageurl = data.getData();
            FileOutputStream outputStream = null;
            String fileName = "photo";
            File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try{
                Bitmap bitmap = null;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(),imageurl));
                }else{
                    bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(),imageurl);;
                }
                File imageFile = File.createTempFile(fileName,".JPEG",file);
                currentPhotoPath = imageFile.getAbsolutePath();
                outputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                outputStream.flush();
                outputStream.close();
                flag = 1;
            }catch (Exception e ){
                e.printStackTrace();
            }
            if(flag == 1){
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
                imageButton.setImageBitmap(imageBitmap);
                Resource.uriImageResource = currentPhotoPath;

            }
        }
    }

    public void setProjects(){
        MediaType MEDIA_TYPE =
                MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(

                60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("usuario", Resource.usuario);
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
