package com.example.dashboard.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.dashboard.Activity.Doctor.FiguresModelActivity;
import com.example.dashboard.Activity.Study.LandMarkModelActivity;
import com.example.dashboard.Adapter.FileProjectAdapter;
import com.example.dashboard.Adapter.ProjectAdapter;
import com.example.dashboard.ListFigures.MemoryFigure;
import com.example.dashboard.Models.FileProject;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;
import com.example.dashboard.Utils.StringUtil;
import com.example.dashboard.Resources.Resource;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FileProjectActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFileProject;
    private FileProjectAdapter adapterFileProject;
    private RecyclerView.LayoutManager layoutManagerFileProject;
    private List list;
    private Uri imageurl;
    private String currentPhotoPath;
    private ImageButton imageButton;
    private int flag;
    Button btn_crear_file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_project);
        /*DATA VIEW*/
        MaterialToolbar toolbar =findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_crear_file = findViewById(R.id.btn_crear_file);
        flag = 0;

        btn_crear_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFileProjectDialog();
            }
        });
        getInfo(Resource.role);
        recyclerViewFileProject = (RecyclerView) findViewById(R.id.recicler_File);
        recyclerViewFileProject.setHasFixedSize(true);
        layoutManagerFileProject = new LinearLayoutManager(getApplicationContext());
        recyclerViewFileProject.setLayoutManager(layoutManagerFileProject);
    }

    private void showAddFileProjectDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Nuevo Archivo: ");
        dialog.setMessage("Touch para Seleccionar una Imagen" );
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(this);
        View add_layout = inflater.inflate(R.layout.data_project,null);
        imageButton = add_layout.findViewById(R.id.addImageProject);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent gallery = new Intent();
                    gallery.setType("image/*");
                    gallery.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(gallery, "Select picture"), 4);
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());

                }
            }
        });
        dialog.setView(add_layout);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    if (flag == 1) {
                        //FileProject project = new FileProject(Resource.uriImageResource,name, description);
                        //adapterFileProject.addElement(project);
                        if(Resource.role == 2) { //study
                            Intent intent = new Intent(FileProjectActivity.this, LandMarkModelActivity.class);
                            startActivity(intent);
                        }else if (Resource.role == 1){ //doctor
                            Resource.changeSaveFigures = false;
                            Intent intent = new Intent(FileProjectActivity.this, FiguresModelActivity.class);
                            startActivity(intent);
                        }
                        Resource.openFile = false;
                        Resource.openShareFile = false;
                        Resource.privilegeFile = "edit";
                        MemoryFigure.changeSave = false;
                    } else {
                        Toast.makeText(FileProjectActivity.this, "Insert-Image, Please", Toast.LENGTH_SHORT).show();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 4 && resultCode == RESULT_OK){
            flag = 0;
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


    public void getInfo(final int role){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        String addURL = "";
        if(role == 1) { //medicine
            addURL = "medicine/selectfiles";
            try {
                postdata.put("email", Resource.emailUserLogin);
                postdata.put("dni",Resource.idPacient);
                postdata.put("record",Resource.idCarpeta);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }else if(role == 2){ //study
            addURL = "study/selectfiles";
            try {
                postdata.put("email", Resource.emailUserLogin);
                postdata.put("project",Resource.idCarpeta);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url(getString(R.string.url)+addURL) /*URL ... INDEX PX DE WILMER*/
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
                                if(role == 1){//medicine
                                    Resource.infoMedicine =  new JSONObject(responseData);;
                                    if(Resource.infoMedicine!=null){
                                        list = new ArrayList();
                                        try {
                                            JSONArray jsonArray = Resource.infoMedicine.getJSONArray("files");
                                            for(int k = 0; k < jsonArray.length();k++){
                                                JSONObject jsonObject1 = jsonArray.getJSONObject(k);
                                                String image = jsonObject1.getString("image");
                                                String name = jsonObject1.getString("name");
                                                String description = jsonObject1.getString("description");
                                                String date = jsonObject1.getString("date");
                                                list.add(new FileProject(image,name,description,date));
                                            }
                                            adapterFileProject =  new FileProjectAdapter(list,FileProjectActivity.this);
                                            recyclerViewFileProject.setAdapter(adapterFileProject);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }else if(role ==2){ //study
                                    Resource.infoStudy =  new JSONObject(responseData);;
                                    if(Resource.infoStudy!=null){
                                        list = new ArrayList();
                                        try {
                                            JSONArray jsonArray = Resource.infoStudy.getJSONArray("files");
                                            for(int k = 0; k < jsonArray.length();k++){
                                                JSONObject jsonObject1 = jsonArray.getJSONObject(k);
                                                String image = jsonObject1.getString("image");
                                                String name = jsonObject1.getString("name");
                                                String description = jsonObject1.getString("description");
                                                String date = jsonObject1.getString("date");
                                                list.add(new FileProject(image,name,description,date));
                                            }
                                            adapterFileProject =  new FileProjectAdapter(list,FileProjectActivity.this);
                                            recyclerViewFileProject.setAdapter(adapterFileProject);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
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

    @Override
    public void onRestart() {
        super.onRestart();
        getInfo(Resource.role);
    }


}
