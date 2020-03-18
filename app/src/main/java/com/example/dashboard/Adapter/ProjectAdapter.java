package com.example.dashboard.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.Activity.FileProjectActivity;
import com.example.dashboard.Activity.ProjectActivity;
import com.example.dashboard.Figures.Line;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> implements Filterable {
    private List<Project> items;
    private List<Project> itemsFull;
    private Context context;
    public static class ProjectViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView menuOptions;
        public ImageView folderImage;
        public LinearLayout boxProject;
        public ProjectViewHolder(View v){
            super(v);
            name = (TextView) v.findViewById(R.id.nameProyect);
            boxProject = (LinearLayout) v.findViewById(R.id.boxProject);
            folderImage = (ImageView) v.findViewById(R.id.folderProject);
            menuOptions = (ImageView) v.findViewById(R.id.menuOptions);
            folderImage.setColorFilter(Color.LTGRAY);

        }
    }
    public ProjectAdapter(List<Project> items,Context context){
        this.context = context;
        this.items = items;
        itemsFull = new ArrayList<>(items);
    }

    public void addElement(Project project){
        items.add(project);
        notifyDataSetChanged();
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
        projectViewHolder.menuOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        System.out.println("SELECT ITEM: "+ item.getTitle()+"position: "+i);
                        if(item.getTitle().equals("Open")){
                            Intent intent = new Intent(context, FileProjectActivity.class);
                            context.startActivity(intent);
                        }else if(item.getTitle().equals("Edit")){

                        }
                        return true;
                    }
                });
                popupMenu.show();
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
            List<Project> filterProject = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterProject.addAll(itemsFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for ( Project project : itemsFull){
                    if(project.getNameProject().toLowerCase().contains(filterPattern)){
                        filterProject.add(project);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterProject;
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
