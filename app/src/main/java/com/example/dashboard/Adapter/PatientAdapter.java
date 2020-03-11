package com.example.dashboard.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.Models.Patient;
import com.example.dashboard.R;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
    private List<Patient> items;

    public static class PatientViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView age;
        public TextView description;
        public PatientViewHolder(View v){
            super(v);
            name = (TextView) v.findViewById(R.id.namePatient);
            age = (TextView) v.findViewById(R.id.agePatient);
            description = (TextView) v.findViewById(R.id.descriptionPatient);
        }
    }
    public PatientAdapter(List<Patient> items){
        this.items = items;
    }

    @Override
    public int getItemCount(){
        return items.size();
    }
    @Override
    public PatientAdapter.PatientViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout,viewGroup,false);
        return new PatientViewHolder(v);
    }
    @Override
    public void onBindViewHolder( PatientViewHolder patientViewHolder, int i){
        patientViewHolder.name.setText(items.get(i).getName());
        patientViewHolder.age.setText("Edad: "+String.valueOf(items.get(i).getAge()));
        patientViewHolder.description.setText(items.get(i).getDescription());
    }
}
