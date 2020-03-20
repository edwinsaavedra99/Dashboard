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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dashboard.Activity.Study.LandMarkModelActivity;
import com.example.dashboard.Adapter.FileProjectAdapter;
import com.example.dashboard.Models.FileProject;
import com.example.dashboard.R;
import com.example.dashboard.Utils.StringUtil;
import com.example.dashboard.Resources.Resource;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileProjectActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFileProject;
    private FileProjectAdapter adapterFileProject;
    private RecyclerView.LayoutManager layoutManagerFileProject;
    private TextView textViewApp;
    private SearchView searchViewFileProject;
    private ImageView addFileProject;
    private CardView cardViewUsuario;
    private Uri imageurl;
    private String currentPhotoPath;
    private ImageButton imageButton;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_project);
        getSupportActionBar().hide();
        /*DATA VIEW*/
        textViewApp = findViewById(R.id.textApp);
        cardViewUsuario = (CardView) findViewById(R.id.cardUsuario);
        searchViewFileProject = findViewById(R.id.searchFileProject);
        addFileProject = findViewById(R.id.addFileProject);
        flag = 0;
        addFileProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFileProjectDialog();
            }
        });

        searchViewFileProject.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchViewFileProject.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterFileProject.getFilter().filter(newText);
                return false;
            }
        });
        /*DATA BASE*/
        searchViewFileProject.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewUsuario.setVisibility(View.GONE);
                textViewApp.setVisibility(View.GONE);

            }
        });
        searchViewFileProject.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                cardViewUsuario.setVisibility(View.VISIBLE);
                textViewApp.setVisibility(View.VISIBLE);
                return false;
            }
        });
        List list = new ArrayList();
        String imag64 = StringUtil.loadFromAsset("raw/image64.txt",this);
        list.add(new FileProject(imag64,"NOMBRE DE FILE PROJECT 1","ESTE SE REFIERE A LA FRACTURA DE UNA PIERNA"));
        list.add(new FileProject(imag64,"NOMBRE DE FILE PROJECT 2","ESTE SE REFIERE A LA FRACTURA DE UNA PIERNA"));
        list.add(new FileProject(imag64,"NOMBRE DE FILE PROJECT 3","ESTE SE REFIERE A LA FRACTURA DE UNA PIERNA"));
        list.add(new FileProject(imag64,"NOMBRE DE FILE PROJECT 4","ESTE SE REFIERE A LA FRACTURA DE UNA PIERNA"));
        addFileProject.setBackgroundColor(Color.parseColor("#80000000"));
        recyclerViewFileProject = (RecyclerView) findViewById(R.id.recicler_File);
        recyclerViewFileProject.setHasFixedSize(true);
        layoutManagerFileProject = new LinearLayoutManager(getApplicationContext());
        recyclerViewFileProject.setLayoutManager(layoutManagerFileProject);
        adapterFileProject =  new FileProjectAdapter(list,getApplicationContext());
        recyclerViewFileProject.setAdapter(adapterFileProject);
    }

    private void showAddFileProjectDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("NEW PROJECT: ");
        dialog.setMessage("Insert Project's Data: " );
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(this);
        View add_layout = inflater.inflate(R.layout.data_project,null);
        final TextInputEditText editDescription = add_layout.findViewById(R.id.txt_descriptionProject);
        final TextInputEditText editName = add_layout.findViewById(R.id.txt_nameProject);
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
                String name = editName.getText().toString().trim();
                String description = editDescription.getText().toString().trim();
                if(name.length() == 0){
                    editName.setError("Error ...");
                    editName.requestFocus();
                }else {
                    if (flag == 1) {

                        //Resource.uriImageResource;

                        FileProject project = new FileProject(Resource.uriImageResource,name, description);
                        adapterFileProject.addElement(project);
                        Intent intent = new Intent(FileProjectActivity.this, LandMarkModelActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(FileProjectActivity.this, "Insert-Image, Please", Toast.LENGTH_SHORT).show();
                    }
                }
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

}
