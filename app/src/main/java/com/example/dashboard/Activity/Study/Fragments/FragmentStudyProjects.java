package com.example.dashboard.Activity.Study.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.Activity.MainActivity;
import com.example.dashboard.Activity.Study.LandMarkModelActivity;
import com.example.dashboard.Adapter.PatientAdapter;
import com.example.dashboard.Adapter.ProjectAdapter;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
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

import static android.app.Activity.RESULT_OK;

public class FragmentStudyProjects extends Fragment {
    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
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
    private SearchView searchView;
    private ImageView addProject;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_fragment_study, container, false);

        searchView = getActivity().findViewById(R.id.searchProjectStudy);
        addProject = (ImageView) getActivity().findViewById(R.id.addProjectStudy);
        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProjectDialog();
                //ShowAddDialog();
            }
        });
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        list = new ArrayList();
        //initialComponentsActivity();
        list.add(new Project("Pruebas"));
        list.add(new Project("Clase 2019"));
        list.add(new Project("Clase 2020"));
        list.add(new Project("Project 01"));
        list.add(new Project("Project 02"));

        Project project = new Project("PROYECTO TEST ADD");


        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recicler_project);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new ProjectAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);
        return viewGroup;
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

    private void showAddProjectDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("NEW PROJECT: ");
        dialog.setMessage("Insert Project's Data: " );
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
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

    private void ShowAddDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("NEW PROJECT: ");
        dialog.setMessage("Insert Project's Data: " );
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
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
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());

                }
            }
        });
        dialog.setView(add_layout);
        dialog.setPositiveButton("START PROJECT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), LandMarkModelActivity.class);
                getActivity().startActivity(intent);

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
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 4 && resultCode == RESULT_OK){
            int flag = 0;
            imageurl = data.getData();
            FileOutputStream outputStream = null;
            String fileName = "photo";
            File file = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try{
                Bitmap bitmap = null;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getActivity().getContentResolver(),imageurl));
                }else{
                    bitmap =  MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageurl);;
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

}
