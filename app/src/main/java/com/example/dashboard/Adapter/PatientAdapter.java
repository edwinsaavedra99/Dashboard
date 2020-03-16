package com.example.dashboard.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.Activity.MainActivity;
import com.example.dashboard.Activity.ProjectActivity;
import com.example.dashboard.Figures.Line;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.R;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> implements Filterable {

    private List<Patient> items;
    private List<Patient> itemsFull;

    private Context context;
    private static int currentPosition = 0;
    public static class PatientViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView age;
        public TextView description;
        public RelativeLayout relativeLayout;
        public ImageView downPatientShow;
        public RelativeLayout cardLayout;
        public LinearLayout boxPatient;
        public Button buttonOpen;
        public Button buttonEdit;

        public PatientViewHolder(View v){
            super(v);
            name = (TextView) v.findViewById(R.id.namePatient);
            age = (TextView) v.findViewById(R.id.agePatient);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayoutPatient);
            cardLayout = (RelativeLayout) v.findViewById(R.id.cardLayout);
            boxPatient = (LinearLayout) v.findViewById(R.id.boxPatient);
            buttonOpen = (Button) v.findViewById(R.id.buttonOpen);
            buttonEdit = (Button) v.findViewById(R.id.buttonEdit);
        }
    }
    public PatientAdapter(List<Patient> items,Context context){
         this.context = context;
         this.items = items;
         itemsFull = new ArrayList<>(items);
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
    public void onBindViewHolder( final PatientViewHolder patientViewHolder,final int i){
        patientViewHolder.name.setText(items.get(i).getName());
        patientViewHolder.age.setText("Caso: "+String.valueOf(items.get(i).getAge()));
        if(currentPosition == i){
            Animation slideDown = AnimationUtils.loadAnimation(context,R.anim.slide_down);
            patientViewHolder.relativeLayout.setVisibility(View.VISIBLE);
            patientViewHolder.relativeLayout.startAnimation(slideDown);
        }
        patientViewHolder.boxPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = i;
                notifyDataSetChanged();
            }
        });
        patientViewHolder.buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProjectActivity.class);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public Filter getFilter(){
        return itemsFilter;
    }

    private Filter itemsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Patient> filterPatient = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterPatient.addAll(itemsFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for ( Patient patient : itemsFull){
                    if(patient.getName().toLowerCase().contains(filterPattern)){
                        filterPatient.add(patient);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterPatient;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



}
