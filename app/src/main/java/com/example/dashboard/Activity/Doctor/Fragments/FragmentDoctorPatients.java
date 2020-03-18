package com.example.dashboard.Activity.Doctor.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dashboard.Adapter.PatientAdapter;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentDoctorPatients extends Fragment {

    private RecyclerView recyclerView;
    private PatientAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_fragment_doctor, container, false);
        searchView = getActivity().findViewById(R.id.searchPatient);
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
        List list = new ArrayList();
        list.add(new Patient("Edwin Enrique Saavedra Parisaca","Arequipa",21,"FRACTURA DE PIERNA",12353,true));
        list.add(new Patient("Edwin Enrique Saavedra Parisaca","Arequipa",21,"FRACTURA DE PIERNA",12353,true));
        list.add(new Patient("Edwin Enrique Saavedra Parisaca","Arequipa",21,"FRACTURA DE PIERNA",12353,true));
        //backPatient.setColorFilter(Color.parseColor("#00BB2D"));
        //addPatient.setBackgroundColor(Color.parseColor("#80000000"));
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recicler_patient);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new PatientAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);
        return viewGroup;
    }

}
