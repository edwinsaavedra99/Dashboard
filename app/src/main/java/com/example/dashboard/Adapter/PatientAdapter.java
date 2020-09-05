package com.example.dashboard.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.Activity.MainActivity;
import com.example.dashboard.Activity.ProjectActivity;
import com.example.dashboard.Activity.Study.LandMarkModelActivity;
import com.example.dashboard.Figures.Line;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.google.android.material.button.MaterialButtonToggleGroup;
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

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> implements Filterable {

    private List<Patient> items;
    private List<Patient> itemsFull;
    private Context context;

    public static class PatientViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView age;
        public TextView description;
        public TextView sex;
        public TextView home;
        public ImageView menuItem;
        public RelativeLayout cardLayout;
        public LinearLayout boxPatient;
        public TextView dniPatient;

        public PatientViewHolder(View v){
            super(v);
            name = (TextView) v.findViewById(R.id.namePatient);
            age = (TextView) v.findViewById(R.id.agePatient);
            description = (TextView) v.findViewById(R.id.descrptionPatient);
            sex = (TextView) v.findViewById(R.id.sexPatient);
            home = (TextView) v.findViewById(R.id.homePatient);
            dniPatient  = (TextView) v.findViewById(R.id.dniPatient);
            menuItem = (ImageView)v.findViewById(R.id.menuItemPatient);
            cardLayout = (RelativeLayout) v.findViewById(R.id.cardLayout);
            boxPatient = (LinearLayout) v.findViewById(R.id.boxPatient);
        }
    }
    public PatientAdapter(List<Patient> items,Context context){
         this.context = context;
         this.items = items;
         itemsFull = new ArrayList<>(items);
    }
    public void addElement(Patient patient){
        items.add(patient);
        notifyDataSetChanged();
    }
    public void editElement(Patient patient, int position){
        items.get(position).setAge(patient.getAge());
        items.get(position).setCod(patient.getCod());
        items.get(position).setDescription(patient.getDescription());
        items.get(position).setName(patient.getName());
        items.get(position).setResidencia(patient.getResidencia());
        items.get(position).setSex(patient.isSex());
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
    public PatientAdapter.PatientViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout,viewGroup,false);
        return new PatientViewHolder(v);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder( final PatientViewHolder patientViewHolder,final int i){
        patientViewHolder.name.setText(items.get(i).getName().toUpperCase());
        patientViewHolder.age.setText("Edad: "+items.get(i).getAge());
        patientViewHolder.description.setText("Descripción: "+items.get(i).getDescription().toUpperCase());
        patientViewHolder.home.setText("Dirección: "+items.get(i).getResidencia().toUpperCase());
        patientViewHolder.dniPatient.setText("DNI: "+ items.get(i).getCod());
        patientViewHolder.boxPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resource.idPacient = items.get(i).getCod();
                Resource.residecyPatient = items.get(i).getResidencia();
                Resource.agePatient = items.get(i).getAge();
                if(items.get(i).isSex())
                    Resource.genderPatient = 0;
                else
                    Resource.genderPatient = 1;
                Intent intent = new Intent(context, ProjectActivity.class);
                context.startActivity(intent);
            }
        });
        if(items.get(i).isSex())
            patientViewHolder.sex.setText("Genero: Masculino,");
        else
            patientViewHolder.sex.setText("GENDER: Femenino ,");
        patientViewHolder.menuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        System.out.println("SELECT ITEM: "+ item.getTitle()+"position: "+i);
                        if(item.getTitle().equals("Abrir")){
                            Resource.idPacient = items.get(i).getCod();
                            Resource.residecyPatient = items.get(i).getResidencia();
                            Resource.agePatient = items.get(i).getAge();
                            if(items.get(i).isSex())
                                Resource.genderPatient = 0;
                            else
                                Resource.genderPatient = 1;
                            Intent intent = new Intent(context, ProjectActivity.class);
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

    private void showAlertDialogEdit(final Patient patient, final int position){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("EDIT PROJECT");
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View add_layout = inflater.inflate(R.layout.patient_structure_data,null);
        final TextInputEditText editName = add_layout.findViewById(R.id.txt_namePatient);
        final TextInputEditText editDescription = add_layout.findViewById(R.id.txt_descriptionPatient);
        final TextInputEditText editResidencia = add_layout.findViewById(R.id.addreesPatient);
        final TextInputEditText editDNI = add_layout.findViewById(R.id.dniPatient);
        final TextInputEditText editAge = add_layout.findViewById(R.id.txt_agePatient);
        final RadioGroup genderGroup = add_layout.findViewById(R.id.sexPatient);
        final int auxDni = patient.getCod();
        editName.setText(patient.getName());
        editDescription.setText(patient.getDescription());
        editResidencia.setText(patient.getResidencia());
        editDNI.setText(patient.getCod()+"");
        //editDNI.setClickable(false);
        //editDNI.setEnabled(false);
        editAge.setText(patient.getAge()+"");
        if(patient.isSex()) {
            genderGroup.check(R.id.radioMasculino);
        }else{
            genderGroup.check(R.id.radioFemenino);
        }
        dialog.setView(add_layout);
        dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editName.getText().toString().trim();//
                String residency = editResidencia.getText().toString().trim();//
                int age = 0;
                String description = editDescription.getText().toString().trim();//
                int dni = 0;
                boolean sex = false;
                if(genderGroup.getCheckedRadioButtonId() == R.id.radioFemenino){
                    sex = false;
                }else if(genderGroup.getCheckedRadioButtonId() == R.id.radioMasculino){
                    sex = true;
                }
                boolean flagAddPatient = true;
                if(name.length() == 0){
                    flagAddPatient = false;
                    editName.setError("Error ...");
                    editName.requestFocus();
                }
                if(residency.length() == 0){
                    flagAddPatient = false;
                    editResidencia.setError("Error ...");
                    editResidencia.requestFocus();
                }
                if(description.length() == 0){
                    flagAddPatient = false;
                    editDescription.setError("Error ...");
                    editDescription.requestFocus();
                }
                if(editAge.getText().toString().trim().length() == 0){
                    flagAddPatient = false;
                    editAge.setError("Error ...");
                    editAge.requestFocus();
                }else{
                    age = Integer.parseInt(editAge.getText().toString().trim());
                }
                if(editDNI.getText().toString().trim().length() == 0){
                    flagAddPatient = false;
                    editDNI.setError("Error ...");
                    editDNI.requestFocus();
                }else{
                    dni = Integer.parseInt(editDNI.getText().toString().trim());
                }
                if(flagAddPatient) {
                    Patient patient = new Patient(name, residency, age, description, dni, sex);
                    editService(position,auxDni,patient);
                }else{
                    Toast.makeText(context,"Error in Data",Toast.LENGTH_SHORT).show();
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
    public void editService(final int position,final int auxDni,final Patient patient){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        String addURL = "";
        if(Resource.role == 1) { //medicine
            addURL = "medicine/updatepatient";
            try {
                postdata.put("email", Resource.emailUserLogin);
                postdata.put("patient",auxDni);
                postdata.put("information_new",patient.getJsonPatient());
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
                                editElement(patient,position);
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


    private void showAlertDialogDelete(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Eliminar Paciente");
        builder.setMessage("Esta Seguro Eliminar al Paciente? , Todos sus datos se Elimaran" );
        builder.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(context);
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFileService(position,items.get(position).getCod());
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

    public void deleteFileService(final int position,final int cod){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        String addURL = "";
        if(Resource.role == 1) { //medicine
            addURL = "medicine/deletepatient";
            try {
                postdata.put("email", Resource.emailUserLogin);
                postdata.put("patient",cod);
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
