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
import com.example.dashboard.Adapter.PatientAdapter;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.R;
import java.util.ArrayList;
import java.util.List;

public class PatientsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PatientAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView backPatient;
    private TextView titlePatient;
    private TextView searchPatient;
    private SearchView searchView;
    private Button addPatient;
    private Button buttonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        getSupportActionBar().hide();
        /*DATA VIEW*/
        titlePatient = findViewById(R.id.titlePatient);
        searchView = findViewById(R.id.searchPatient);
        addPatient = findViewById(R.id.addPatient);
        /*DATA BASE*/
        List list = new ArrayList();
        list.add(new Patient("Edwin Enrique Saavedra Parisaca",21,"RADIOGRAFIA"));
        list.add(new Patient("Luis Lorenzo Quilla",21,"RADIOGRAFIA"));
        list.add(new Patient("Wilmer Pachecho Jimenez",26,"RADIOGRAFIA"));
        list.add(new Patient("Edwin Enrique Saavedra Parisaca",21,"RADIOGRAFIA"));
        list.add(new Patient("Luis Lorenzo Quilla",21,"RADIOGRAFIA"));
        list.add(new Patient("Wilmer Pachecho Jimenez",26,"RADIOGRAFIA"));
        list.add(new Patient("Edwin Enrique Saavedra Parisaca",21,"RADIOGRAFIA"));
        list.add(new Patient("Luis Lorenzo Quilla",21,"RADIOGRAFIA"));
        //backPatient.setColorFilter(Color.parseColor("#00BB2D"));
        addPatient.setBackgroundColor(Color.parseColor("#80000000"));
        recyclerView = (RecyclerView) findViewById(R.id.recicler_patient);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new PatientAdapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }
}
