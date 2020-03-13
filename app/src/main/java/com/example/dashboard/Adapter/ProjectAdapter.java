package com.example.dashboard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dashboard.Figures.Line;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    private List<Project> items;
    private Context context;
    public static class ProjectViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public LinearLayout boxProject;
        public ProjectViewHolder(View v){
            super(v);
            name = (TextView) v.findViewById(R.id.nameProyect);
            boxProject = (LinearLayout) v.findViewById(R.id.boxProject);
        }
    }
    public ProjectAdapter(List<Project> items,Context context){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount(){
        return items.size();
    }
    @Override
    public ProjectAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_layout_item_project,viewGroup,false);
        return new ProjectViewHolder(v);
    }
    @Override
    public void onBindViewHolder( final ProjectViewHolder projectViewHolder,final int i){
        projectViewHolder.name.setText(items.get(i).getNameProject());
        projectViewHolder.boxProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //paso a nuevo activity
            }
        });

//        patientViewHolder.description.setText(items.get(i).getDescription());
    }



}
