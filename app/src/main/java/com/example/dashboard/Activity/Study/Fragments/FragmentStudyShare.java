package com.example.dashboard.Activity.Study.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.Adapter.FileProjectAdapter;
import com.example.dashboard.Models.FileProject;
import com.example.dashboard.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentStudyShare extends Fragment {
    private RecyclerView recyclerView;
    private FileProjectAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView;
    private LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_fragment_study_share, container, false);
        linearLayout = viewGroup.findViewById(R.id.noDataShareStudy);
        searchView = getActivity().findViewById(R.id.searchProjectStudyShare);
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
        list.add(new FileProject("","RADIOGRAFIA_1","DESGUARRE DEL FEMUR Y LIGAMENTOS ROTOS"));
        list.add(new FileProject("","RADIOGRAFIA_2","DESGUARRE DEL FEMUR Y LIGAMENTOS ROTOS"));
        list.add(new FileProject("","RADIOGRAFIA_3","DESGUARRE DEL FEMUR Y LIGAMENTOS ROTOS"));

        if(list.isEmpty())
            linearLayout.setVisibility(View.VISIBLE);
        else
            linearLayout.setVisibility(View.GONE);

        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recicler_project_file);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new FileProjectAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);
        return viewGroup;
    }
}