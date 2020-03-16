package com.example.dashboard.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dashboard.Models.FileProject;
import com.example.dashboard.R;
import java.util.List;

public class FileProjectAdapter extends RecyclerView.Adapter<FileProjectAdapter.FileProjectViewHolder> {
    private List<FileProject> items;
    private Context context;
    private static int currentPosition = 0;
    static class FileProjectViewHolder extends RecyclerView.ViewHolder{
        TextView nameFileProject;
        TextView descriptionFileProject;
        TextView dateFileProject;
        RelativeLayout relativeLayoutFileProject;
        LinearLayout boxFileProject;
        Button buttonOpen;
        ImageView imageViewFileProject;
        FileProjectViewHolder(View v){
            super(v);
            nameFileProject = (TextView) v.findViewById(R.id.namePatient);
            descriptionFileProject = (TextView) v.findViewById(R.id.descriptionFileProject);
            dateFileProject = (TextView) v.findViewById(R.id.dateFileProject);
            relativeLayoutFileProject = (RelativeLayout) v.findViewById(R.id.relativeLayoutPatient);
            boxFileProject= (LinearLayout) v.findViewById(R.id.boxPatient);
            buttonOpen = (Button) v.findViewById(R.id.buttonOpen);
            imageViewFileProject = (ImageView) v.findViewById(R.id.imageFileProject);
        }
    }
    public FileProjectAdapter(List<FileProject> items,Context context){
        this.context = context;
        this.items = items;
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
        fileViewHolder.descriptionFileProject.setText(items.get(i).getDescriptionFileProject());
        fileViewHolder.dateFileProject.setText(items.get(i).getDateFileProject());
        //ADD IMAGE EN ITEM LAYOUT
        //items.get(i).getImageFileProject();
        //fileViewHolder.imageViewFileProject.setImageBitmap();
        if(currentPosition == i){
            Animation slideDown = AnimationUtils.loadAnimation(context,R.anim.slide_down);
            fileViewHolder.relativeLayoutFileProject.setVisibility(View.VISIBLE);
            fileViewHolder.relativeLayoutFileProject.startAnimation(slideDown);
        }
        fileViewHolder.boxFileProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = i;
                notifyDataSetChanged();
            }
        });
        fileViewHolder.buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open project
            }
        });
    }


}
