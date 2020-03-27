package com.example.dashboard.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.Activity.Doctor.FiguresModelActivity;
import com.example.dashboard.Activity.FileProjectActivity;
import com.example.dashboard.Activity.ProjectActivity;
import com.example.dashboard.Activity.Study.LandMarkModelActivity;
import com.example.dashboard.Models.FileProject;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.example.dashboard.Utils.StringUtil;
import com.google.android.material.textfield.TextInputEditText;

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
    public void addElement(FileProject project){
        items.add(project);
        notifyDataSetChanged();
    }

    public void editElement(FileProject project, int position){
        FileProject projectAux = items.get(position);
        projectAux.setNameFileProject(project.getNameFileProject());
        projectAux.setDescriptionFileProject(project.getDescriptionFileProject());
        projectAux.DateTimeInitial();
        projectAux.setImageFileProject(project.getImageFileProject());
        items.set(position,projectAux);
        notifyDataSetChanged();
    }
    public void deleteElement(int position){
        items.remove(position);
        notifyDataSetChanged();
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
        fileViewHolder.imageViewFileProject.setImageBitmap(StringUtil.decodeBase64AndSetImage(items.get(i).getImageFileProject()));
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
                            if(Resource.role == 2) { //study
                                Resource.openFile = true;
                                Intent intent = new Intent(context, LandMarkModelActivity.class);
                                context.startActivity(intent);
                            }else if (Resource.role == 1){ //doctor
                                Resource.openFile = true;
                                Intent intent = new Intent(context, FiguresModelActivity.class);
                                context.startActivity(intent);
                            }
                            //Intent intent = new Intent(context, ProjectActivity.class);
                            //context.startActivity(intent);
                        }else if(item.getTitle().equals("Edit")){
                            showAlertDialogEdit(items.get(i),i);
                        }else if(item.getTitle().equals("Delete")){
                            showAlertDialogDelete(i);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });


    }

    private void showAlertDialogDelete(final int position){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("DELETE FILE PROJECT");
        dialog.setMessage("Are you sure to delete  this file ? , All data will be deleted" );
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(context);
        dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteElement(position);
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showAlertDialogEdit(final FileProject project, final int position){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("EDIT FILE PROJECT");
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View add_layout = inflater.inflate(R.layout.project_structure_data,null);
        final TextInputEditText editName = add_layout.findViewById(R.id.txt_nameProject_1);
        editName.setText(project.getNameFileProject());
        dialog.setView(add_layout);
        dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editName.getText().toString().trim();
                if(name.length() == 0){
                    editName.setError("Error ...");
                    editName.requestFocus();
                }else {
                    FileProject project1 = new FileProject(name, "");
                    project1.setImageFileProject(project.getImageFileProject());
                    editElement(project1, position);
                }
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
