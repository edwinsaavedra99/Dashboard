package com.example.dashboard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dashboard.Adapter.FileProjectAdapter;
import com.example.dashboard.Adapter.PatientAdapter;
import com.example.dashboard.Models.FileProject;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.R;

import java.util.ArrayList;
import java.util.List;

public class FileProjectActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFileProject;
    private FileProjectAdapter adapterFileProject;
    private RecyclerView.LayoutManager layoutManagerFileProject;
    private ImageView backFileProject;
    private TextView titleFileProject;
    private TextView searchFileProject;
    private SearchView searchViewFileProject;
    private Button addFileProject;
    private Button buttonTestFileProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_project);
        getSupportActionBar().hide();
        /*DATA VIEW*/
        titleFileProject = findViewById(R.id.titleFileProject);
        searchViewFileProject = findViewById(R.id.searchFileProject);
        addFileProject = findViewById(R.id.addFileProject);
        /*DATA BASE*/
        List list = new ArrayList();
        list.add(new FileProject("CARGA IMAGEN IN STRIN64","NOMBRE DE FILE PROJECT","ESTE SE REFIERE A LA FRACTURA DE UNA PIERNA"));
        list.add(new FileProject("CARGA IMAGEN IN STRIN64","NOMBRE DE FILE PROJECT","ESTE SE REFIERE A LA FRACTURA DE UNA PIERNA"));
        list.add(new FileProject("CARGA IMAGEN IN STRIN64","NOMBRE DE FILE PROJECT","ESTE SE REFIERE A LA FRACTURA DE UNA PIERNA"));
        list.add(new FileProject("CARGA IMAGEN IN STRIN64","NOMBRE DE FILE PROJECT","ESTE SE REFIERE A LA FRACTURA DE UNA PIERNA"));
        addFileProject.setBackgroundColor(Color.parseColor("#80000000"));
        recyclerViewFileProject = (RecyclerView) findViewById(R.id.recicler_File);
        recyclerViewFileProject.setHasFixedSize(true);
        layoutManagerFileProject = new LinearLayoutManager(getApplicationContext());
        recyclerViewFileProject.setLayoutManager(layoutManagerFileProject);
        adapterFileProject =  new FileProjectAdapter(list,getApplicationContext());
        recyclerViewFileProject.setAdapter(adapterFileProject);


    }
}
