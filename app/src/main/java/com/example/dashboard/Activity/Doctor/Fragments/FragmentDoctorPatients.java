package com.example.dashboard.Activity.Doctor.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dashboard.Adapter.PatientAdapter;
import com.example.dashboard.Adapter.ProjectAdapter;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
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

public class FragmentDoctorPatients extends Fragment {

    private RecyclerView recyclerView;
    private PatientAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView;
    private ImageView addPatient;
    private List list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_fragment_doctor, container, false);
        addPatient = (ImageView) getActivity().findViewById(R.id.addPatient);
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPatientDialog();
            }
        });
        searchView = getActivity().findViewById(R.id.searchPatient);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recicler_patient);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        getInfoMedicine();
        return viewGroup;
    }

    public void getInfoMedicine(){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("email", Resource.emailUserLogin);
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url(getString(R.string.url)+"medicine/information") /*URL ... INDEX PX DE WILMER*/
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Resource.infoMedicine =  new JSONObject(responseData);;
                                if(Resource.infoMedicine!=null){
                                    list = new ArrayList();
                                    try {
                                        JSONArray jsonArray = Resource.infoMedicine.getJSONArray("patients");
                                        for(int i = 0; i<jsonArray.length();i++){
                                            JSONObject aux = jsonArray.getJSONObject(i);
                                            String name = aux.getString("name");
                                            String residency = aux.getString("residency");
                                            int age = aux.getInt("age");
                                            String description = aux.getString("description");
                                            int dni = aux.getInt("dni");
                                            int gender = aux.getInt("gender");
                                            boolean sex;
                                            if(gender == 0){
                                                sex = true;
                                            }else{
                                                sex = false;
                                            }
                                            list.add(new Patient(name,residency,age,description,dni,sex));
                                        }
                                        adapter =  new PatientAdapter(list,getActivity());
                                        recyclerView.setAdapter(adapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    private void showAddPatientDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("NEW PROJECT: ");
        dialog.setMessage("Insert Project's Data: " );
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View add_layout = inflater.inflate(R.layout.patient_structure_data,null);
        final TextInputEditText editName = add_layout.findViewById(R.id.txt_namePatient);
        final TextInputEditText editDescription = add_layout.findViewById(R.id.txt_descriptionPatient);
        final TextInputEditText editResidencia = add_layout.findViewById(R.id.addreesPatient);
        final TextInputEditText editDNI = add_layout.findViewById(R.id.dniPatient);
        final TextInputEditText editAge = add_layout.findViewById(R.id.txt_agePatient);
        final RadioGroup genderGroup = add_layout.findViewById(R.id.sexPatient);
        dialog.setView(add_layout);
        dialog.setPositiveButton("ADD PATIENT", new DialogInterface.OnClickListener() {
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
                    addPatientService(Resource.emailUserLogin, patient);
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

    public void addPatientService(String email, final Patient patient){

        MediaType MEDIA_TYPE =
                MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(
                60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("email",email);
            postdata.put("patient",patient.getCod());
            postdata.put("information",patient.getJsonPatient());
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url(getString(R.string.url)+"medicine/createpatient")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    System.out.println("*****"+responseData);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responseData.equals("error")){
                                Toast.makeText(getActivity(),"The DNI already exists",Toast.LENGTH_SHORT).show();
                            }else{
                                adapter.addElement(patient);
                            }
                        }
                    });
                }
            }
        });
    }
}
