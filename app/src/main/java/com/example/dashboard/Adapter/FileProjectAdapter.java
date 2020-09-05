package com.example.dashboard.Adapter;

import android.app.Activity;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.Activity.Doctor.FiguresModelActivity;
import com.example.dashboard.Activity.Study.LandMarkModelActivity;
import com.example.dashboard.ListFigures.MemoryFigure;
import com.example.dashboard.Models.FileProject;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.example.dashboard.Utils.StringUtil;
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

public class FileProjectAdapter extends RecyclerView.Adapter<FileProjectAdapter.FileProjectViewHolder> implements Filterable {

    private List<FileProject> items;
    private List<FileProject> itemsFull;
    private Context context;

    public  static class FileProjectViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewFileProject;
        TextView nameFileProject;
        ImageView moreOptionItem;
        ImageView shareOptionItem;
        TextView dateFileProject;
        TextView descriptionFile;
        LinearLayout boxFileProject;
        FileProjectViewHolder(View v){
            super(v);
            nameFileProject = (TextView) v.findViewById(R.id.nameFileProject);
            dateFileProject = (TextView) v.findViewById(R.id.agePatient);
            descriptionFile = (TextView) v.findViewById(R.id.descriptionFile);
            imageViewFileProject = (ImageView) v.findViewById(R.id.imageFileProject);
            moreOptionItem = (ImageView) v.findViewById(R.id.moreOptionItem);
            shareOptionItem = (ImageView) v.findViewById(R.id.shareOptionItem);
            boxFileProject=v.findViewById(R.id.boxFileProject);
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
        items.get(position).setNameFileProject(project.getNameFileProject());
        items.get(position).setDescriptionFileProject(project.getDescriptionFileProject());
       /* FileProject projectAux = items.get(position);
        projectAux.setNameFileProject(project.getNameFileProject());
        projectAux.setDescriptionFileProject(project.getDescriptionFileProject());
        projectAux.DateTimeInitial();
        projectAux.setImageFileProject(project.getImageFileProject());
        items.set(position,projectAux);*/
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
        fileViewHolder.nameFileProject.setText(items.get(i).getNameFileProject().toUpperCase());
        fileViewHolder.descriptionFile.setText("Descripcion : "+items.get(i).getDescriptionFileProject().toUpperCase());
        fileViewHolder.dateFileProject.setText("Fecha : "+items.get(i).getDateFileProject());
        fileViewHolder.boxFileProject.setOnClickListener(new View.OnClickListener() {
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
                MemoryFigure.changeSave = true;
                Resource.privilegeFile = "edit";
                Resource.openFile = true;
                Resource.openShareFile = false;
                Resource.nameFile = items.get(i).getNameFileProject();
                Resource.descriptionFile = items.get(i).getDescriptionFileProject();
                Resource.dateFile = items.get(i).getDateAux();
            }
        });
        fileViewHolder.imageViewFileProject.setImageBitmap(StringUtil.decodeBase64AndSetImage(items.get(i).getImageFileProject()));
        fileViewHolder.moreOptionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.opciones_file,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        System.out.println("SELECT ITEM: "+ item.getTitle()+"position: "+i);
                        if(item.getTitle().equals("Abrir")){
                            if(Resource.role == 2) { //study
                                Intent intent = new Intent(context, LandMarkModelActivity.class);
                                context.startActivity(intent);
                            }else if (Resource.role == 1){ //doctor
                                Resource.changeSaveFigures = true;
                                Intent intent = new Intent(context, FiguresModelActivity.class);
                                context.startActivity(intent);
                            }
                            MemoryFigure.changeSave = true;
                            Resource.privilegeFile = "edit";
                            Resource.openFile = true;
                            Resource.openShareFile = false;
                            Resource.nameFile = items.get(i).getNameFileProject();
                            Resource.descriptionFile = items.get(i).getDescriptionFileProject();
                            Resource.dateFile = items.get(i).getDateAux();
                        }else if(item.getTitle().equals("Editar")){
                            showAlertDialogEdit(items.get(i),i);
                        }else if(item.getTitle().equals("Eliminar")){
                            showAlertDialogDelete(i,items.get(i).getNameFileProject(),items.get(i).getDateAux());
                        }
                        else if(item.getTitle().equals("Compartir")){
                            showAlertDialogShare(items.get(i).getNameFileProject(),items.get(i).getDateAux());
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        fileViewHolder.shareOptionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogShare(items.get(i).getNameFileProject(),items.get(i).getDateAux());
            }
        });

    }
    private void showAlertDialogShare(final String nameF,final String dateF){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Compartir Proyecto");
        dialog.setMessage("Ingresar Email Para Compartir");
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View add_layout = inflater.inflate(R.layout.share_structure_data,null);
        final TextInputEditText editEmail = add_layout.findViewById(R.id.txt_shareProject_1);
        final RadioGroup privilegeGroup = add_layout.findViewById(R.id.privilege);
        dialog.setView(add_layout);
        dialog.setPositiveButton("Compartir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emailTo = editEmail.getText().toString().trim();
                String privi="";
                if(privilegeGroup.getCheckedRadioButtonId() == R.id.radioLecture){
                    privi="lecture";
                }else if(privilegeGroup.getCheckedRadioButtonId() == R.id.radioEditer){
                    privi="edit";
                }
                if(emailTo.length() == 0){
                    editEmail.setError("Error ...");
                    editEmail.requestFocus();
                }else {
                    getInfo(Resource.role,nameF,emailTo,dateF,privi);
                }
            }
        });
        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
        //getInfo(Resource.role,nameF,"edwinsaavedra99@gmail.com",dateF,"lecture");
    }
    public void getInfo(final int role,final String nameF,final String emailTo,final String dateTo, final String provilege){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        String addURL = "";
        if(role == 1) { //medicine
            addURL = "medicine/shared/file";
            try {
                JSONObject from = new JSONObject();
                from.put("email", Resource.emailUserLogin);
                from.put("patient",Resource.idPacient);
                from.put("record",Resource.idCarpeta);
                from.put("file",nameF);
                from.put("date",dateTo);
                from.put("privilege",provilege);
                JSONObject to = new JSONObject();
                to.put("email",emailTo);
                postdata.put("from",from);
                postdata.put("to",to);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }else if(role == 2){ //study
            addURL = "study/shared/file";
            try {
                JSONObject from = new JSONObject();
                from.put("email", Resource.emailUserLogin);
                from.put("project",Resource.idCarpeta);
                from.put("file",nameF);
                from.put("date",dateTo);
                from.put("privilege",provilege);
                JSONObject to = new JSONObject();
                to.put("email",emailTo);
                postdata.put("from",from);
                postdata.put("to",to);
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
                            Toast.makeText(context,"SHARED",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    private void showAlertDialogDelete(final int position, final String name,final String date){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("DELETE FILE PROJECT");
        dialog.setMessage("Are you sure to delete  this file ? , All data will be deleted" );
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(context);
        dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               deleteFileService(position,name,date);
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
    public void deleteFileService(final int position,final String name,final String date){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        String addURL = "";
        if(Resource.role == 1) { //medicine
            addURL = "medicine/deletefile";
            try {
                postdata.put("email", Resource.emailUserLogin);
                postdata.put("patient",Resource.idPacient);
                postdata.put("record",Resource.idCarpeta);
                postdata.put("file",name);
                postdata.put("date",date);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }else if(Resource.role  == 2){ //study
            addURL = "study/deletefile";
            try {
                postdata.put("email", Resource.emailUserLogin);
                postdata.put("project",Resource.idCarpeta);
                postdata.put("file",name);
                postdata.put("date",date);
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
    private void showAlertDialogEdit(final FileProject project, final int position){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("EDIT FILE PROJECT");
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View add_layout = inflater.inflate(R.layout.layout_save_figure,null);
        final TextInputEditText editName = add_layout.findViewById(R.id.txt_nameProject);
        final TextInputEditText editDescription = add_layout.findViewById(R.id.txt_descriptionProject);
        editName.setText(project.getNameFileProject());
        editDescription.setText(project.getDescriptionFileProject());
        dialog.setView(add_layout);
        dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editName.getText().toString().trim();
                String description = editDescription.getText().toString().trim();
                if(name.length() == 0 || name.contains("@") || nameInList(name)){
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    editName.setError("Error ...");
                    editName.requestFocus();
                }else {
                    editFileService(position,items.get(position).getNameFileProject(),items.get(position).getDateAux(),
                            name,description);

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
    public void editFileService(final int position,final String name,final String date,final String new_name,final String new_description){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        String addURL = "";
        if(Resource.role == 1) { //medicine
            addURL = "medicine/updatefile";
            try {
                postdata.put("email", Resource.emailUserLogin);
                postdata.put("patient",Resource.idPacient);
                postdata.put("record",Resource.idCarpeta);
                postdata.put("file",name);
                postdata.put("date",date);
                postdata.put("file_new",new_name);
                postdata.put("description_new",new_description);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }else if(Resource.role  == 2){ //study
            addURL = "study/updatefile";
            try {
                postdata.put("email", Resource.emailUserLogin);
                postdata.put("project",Resource.idCarpeta);
                postdata.put("file",name);
                postdata.put("date",date);
                postdata.put("file_new",new_name);
                postdata.put("description_new",new_description);
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
                                FileProject project1 = new FileProject(new_name, new_description);
                                editElement(project1, position);
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
    private boolean nameInList(String name){
        for(int i = 0; i<items.size(); i++){
            if(name.equals(items.get(i).getNameFileProject())){
                return true;
            }
        }
        return false;
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
