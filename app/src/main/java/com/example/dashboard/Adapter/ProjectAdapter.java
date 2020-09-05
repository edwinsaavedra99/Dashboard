package com.example.dashboard.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.ContextThemeWrapper;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.Activity.FileProjectActivity;
import com.example.dashboard.Activity.ProjectActivity;
import com.example.dashboard.Activity.Study.LandMarkModelActivity;
import com.example.dashboard.Figures.Line;
import com.example.dashboard.Models.FileProject;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    public void editElement(Project project, int position){
        items.get(position).setNameProject(project.getNameProject());
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
    public ProjectAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_layout_item_project,viewGroup,false);
        return new ProjectViewHolder(v);
    }
    @Override
    public void onBindViewHolder( final ProjectViewHolder projectViewHolder,final int i){
        projectViewHolder.name.setText(items.get(i).getNameProject().toUpperCase());
        projectViewHolder.boxProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resource.idCarpeta = items.get(i).getNameProject();
                Intent intent = new Intent(context, FileProjectActivity.class);
                context.startActivity(intent);
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
                        if(item.getTitle().equals("Abrir")){
                            Resource.idCarpeta = items.get(i).getNameProject();
                            Intent intent = new Intent(context, FileProjectActivity.class);
                            context.startActivity(intent);
                        }else if(item.getTitle().equals("Editar")){
                            showAlertDialogEdit(items.get(i),i);
                        }else if(item.getTitle().equals("Eliminar")){
                            showAlertDialogDelete(i);
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


    private void showAlertDialogDelete(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Eliminar Proyecto");
        builder.setMessage("Esta Seguro de Eliminar el Proyecto? , Todos los datos se eliminaran" );
        builder.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(context);
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFileService(position,items.get(position).getNameProject());
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
    }

    private void showAlertDialogEdit(final Project project, final int position){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Editar Proyecto");
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View add_layout = inflater.inflate(R.layout.project_structure_data,null);
        final TextInputEditText editName = add_layout.findViewById(R.id.txt_nameProject_1);
        editName.setText(project.getNameProject());
        dialog.setView(add_layout);
        dialog.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String new_name = editName.getText().toString().trim();
                editFileService(position,items.get(position).getNameProject(),new_name);
            }
        });
        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void editFileService(final int position,final String name,final String new_name){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        String addURL = "";
        if(Resource.role == 1) { //medicine
            addURL = "medicine/updaterecord";
            try {
                postdata.put("email", Resource.emailUserLogin);
                postdata.put("patient",Resource.idPacient);
                postdata.put("record",name);
                postdata.put("record_new",new_name);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }else if(Resource.role  == 2){ //study
            addURL = "study/updateproject";
            try {
                postdata.put("email", Resource.emailUserLogin);
                postdata.put("project",name);
                postdata.put("project_new",new_name);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url(context.getString(R.string.url)+addURL) /*URL ... INDEX PX DE WILMER*/
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    System.out.println("-------**********-----------"+responseData);
                    Activity das = (Activity) context;
                    das.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(responseData.equals("success")) {
                                Project project1 = new Project(new_name,"");
                                editElement(project1,position);
                                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    public void deleteFileService(final int position,final String name){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        String addURL = "";
        if(Resource.role == 1) { //medicine
            addURL = "medicine/deleterecord";
            try {
                postdata.put("email", Resource.emailUserLogin);
                postdata.put("patient",Resource.idPacient);
                postdata.put("record",name);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }else if(Resource.role  == 2){ //study
            addURL = "study/deleteproject";
            try {
                postdata.put("email", Resource.emailUserLogin);
                postdata.put("project",name);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url(context.getString(R.string.url)+addURL) /*URL ... INDEX PX DE WILMER*/
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    System.out.println("-------**********-----------"+responseData);
                    Activity das = (Activity) context;
                    das.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(responseData.equals("success")) {
                                deleteElement(position);
                                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

}
