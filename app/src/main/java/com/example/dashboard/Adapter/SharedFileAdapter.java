package com.example.dashboard.Adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.Activity.Doctor.FiguresModelActivity;
import com.example.dashboard.Activity.Study.LandMarkModelActivity;
import com.example.dashboard.Models.SharedFile;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.example.dashboard.Utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
public class SharedFileAdapter extends RecyclerView.Adapter<SharedFileAdapter.SharedFileViewHolder> implements Filterable {

    private List<SharedFile> items;
    private List<SharedFile> itemsFull;
    private Context context;

    public  static class SharedFileViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewFileProject;
        TextView nameFileProject;
        //TextView dateFileProject;
        //TextView timeFileProject;
        TextView descriptionFile;
        TextView emailFromFile;
        SharedFileViewHolder(View v){
            super(v);
            nameFileProject = v.findViewById(R.id.nameFileProject);//
          //  dateFileProject = (TextView) v.findViewById(R.id.dateFileProject);//
//            timeFileProject = (TextView) v.findViewById(R.id.timeFileProject);//
            descriptionFile =  v.findViewById(R.id.descriptionFile);//
            imageViewFileProject =  v.findViewById(R.id.imageFileProject);//
            emailFromFile =  v.findViewById(R.id.emailFrom);//

        }
    }
    public SharedFileAdapter(List<SharedFile> items, Context context){
        this.context = context;
        this.items = items;
        itemsFull = new ArrayList<>(items);
    }
    @Override
    public int getItemCount(){
        return items.size();
    }
    @Override
    public SharedFileAdapter.SharedFileViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_layout_fileprojectshared,viewGroup,false);
        return new SharedFileAdapter.SharedFileViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final SharedFileAdapter.SharedFileViewHolder fileViewHolder, final int i){
        fileViewHolder.nameFileProject.setText(items.get(i).getNameFileProject());
        fileViewHolder.descriptionFile.setText(items.get(i).getDescriptionFileProject());
  //      fileViewHolder.dateFileProject.setText(items.get(i).getDateFileProject());
    //    fileViewHolder.timeFileProject.setText(items.get(i).getTimeFileProject());
        fileViewHolder.emailFromFile.setText("by: "+items.get(i).getEmailFrom());

        fileViewHolder.imageViewFileProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Resource.role == 2) { //study
                    Intent intent = new Intent(context, LandMarkModelActivity.class);
                    context.startActivity(intent);
                }else if (Resource.role == 1){ //doctor
                    Resource.changeSaveFigures = true;
                    Intent intent = new Intent(context, FiguresModelActivity.class);
                    context.startActivity(intent);
                }
                Resource.privilegeFile = items.get(i).getPrivilege();
                Resource.emailSharedFrom = items.get(i).getEmailFrom();
                Resource.openFile = false;
                Resource.openShareFile = true;
                Resource.nameFile = items.get(i).getNameFileProject();
                Resource.descriptionFile = items.get(i).getDescriptionFileProject();
                Resource.dateFile = items.get(i).getDateAux();
                Resource.idCarpeta = items.get(i).getCarpeta();
                Resource.idPacient = items.get(i).getPatient();
            }
        });
        fileViewHolder.imageViewFileProject.setImageBitmap(StringUtil.decodeBase64AndSetImage(items.get(i).getImageFileProject()));



    }
    @Override
    public Filter getFilter(){
        return itemsFilter;
    }
    private Filter itemsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SharedFile> filterPatient = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterPatient.addAll(itemsFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for ( SharedFile fileProject : itemsFull){
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
