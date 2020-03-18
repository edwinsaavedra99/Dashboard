package com.example.dashboard.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.Activity.ProjectActivity;
import com.example.dashboard.Models.FileProject;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.R;

import java.util.ArrayList;
import java.util.List;

public class FileProjectAdapter extends RecyclerView.Adapter<FileProjectAdapter.FileProjectViewHolder> implements Filterable {

    private List<FileProject> items;
    private List<FileProject> itemsFull;
    private Context context;

    static class FileProjectViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewFileProject;
        TextView nameFileProject;
        ImageView moreOptionItem;
        TextView dateFileProject;
        TextView timeFileProject;
        TextView descriptionFile;

        FileProjectViewHolder(View v){
            super(v);
            nameFileProject = (TextView) v.findViewById(R.id.nameFileProject);
            dateFileProject = (TextView) v.findViewById(R.id.dateFileProject);
            timeFileProject = (TextView) v.findViewById(R.id.timeFileProject);
            descriptionFile = (TextView) v.findViewById(R.id.descriptionFile);
            imageViewFileProject = (ImageView) v.findViewById(R.id.imageFileProject);
            moreOptionItem = (ImageView) v.findViewById(R.id.moreOptionItem);
        }
    }
    public FileProjectAdapter(List<FileProject> items,Context context){
        this.context = context;
        this.items = items;
        itemsFull = new ArrayList<>(items);
    }

    @Override
    public int getItemCount(){
        return items.size();
    }
    @Override
    public FileProjectAdapter.FileProjectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_layout_file_project,viewGroup,false);
        return new FileProjectAdapter.FileProjectViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final FileProjectAdapter.FileProjectViewHolder fileViewHolder, final int i){
        fileViewHolder.nameFileProject.setText(items.get(i).getNameFileProject());
        fileViewHolder.descriptionFile.setText(items.get(i).getDescriptionFileProject());
        fileViewHolder.dateFileProject.setText(items.get(i).getDateFileProject());
        fileViewHolder.timeFileProject.setText(items.get(i).getTimeFileProject());
        //ADD IMAGE EN ITEM LAYOUT
        //items.get(i).getImageFileProject();
        //fileViewHolder.imageViewFileProject.setImageBitmap();
        fileViewHolder.moreOptionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        System.out.println("SELECT ITEM: "+ item.getTitle()+"position: "+i);
                        if(item.getTitle().equals("Open")){
                            //Intent intent = new Intent(context, ProjectActivity.class);
                            //context.startActivity(intent);
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
            List<FileProject> filterPatient = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterPatient.addAll(itemsFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for ( FileProject fileProject : itemsFull){
                    if(fileProject.getNameFileProject().toLowerCase().contains(filterPattern)){
                        filterPatient.add(fileProject);
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
