package com.example.dashboard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dashboard.Adapter.PatientAdapter;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PatientAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List list = new ArrayList();
        list.add(new Patient("Edwin Enrique Saavedra Parisaca",21,"RADIOGRAFIA"));
        list.add(new Patient("Luis Lorenzo Quilla",21,"RADIOGRAFIA"));
        list.add(new Patient("Wilmer Pachecho Jimenez",26,"RADIOGRAFIA"));
        list.add(new Patient("Edwin Enrique Saavedra Parisaca",21,"RADIOGRAFIA"));
        list.add(new Patient("Luis Lorenzo Quilla",21,"RADIOGRAFIA"));
        list.add(new Patient("Wilmer Pachecho Jimenez",26,"RADIOGRAFIA"));
        list.add(new Patient("Edwin Enrique Saavedra Parisaca",21,"RADIOGRAFIA"));
        list.add(new Patient("Luis Lorenzo Quilla",21,"RADIOGRAFIA"));
        list.add(new Patient("Wilmer Pachecho Jimenez",26,"RADIOGRAFIA"));

        recyclerView = (RecyclerView) findViewById(R.id.recicler_patient);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new PatientAdapter(list);
        recyclerView.setAdapter(adapter);

    }
}
