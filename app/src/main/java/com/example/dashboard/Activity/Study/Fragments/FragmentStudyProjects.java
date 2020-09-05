package com.example.dashboard.Activity.Study.Fragments;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dashboard.Adapter.ProjectAdapter;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
public class FragmentStudyProjects extends Fragment {
    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List list;
    private Button addProject;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_fragment_study, container, false);


        addProject = viewGroup.findViewById(R.id.btn_crear_proyecto);
        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProjectDialog();
            }
        });

        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recicler_project);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        getInfoStudy();
        return viewGroup;
    }
    public void getInfoStudy(){
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
                .url(getString(R.string.url)+"study/selectprojects") /*URL ... INDEX PX DE WILMER*/
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Resource.infoStudy =  new JSONObject(responseData);;
                                if(Resource.infoStudy!=null){
                                    list = new ArrayList();
                                    try {
                                        JSONArray jsonArray = Resource.infoStudy.getJSONArray("projects");
                                        for(int i = 0; i<jsonArray.length();i++){
                                            list.add(new Project(jsonArray.get(i).toString()));
                                        }
                                        adapter =  new ProjectAdapter(list,getActivity());
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
                    System.out.println("-------**********-----------"+responseData);
                }
            }
        });
    }
    private void showAddProjectDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("NEW PROJECT: ");
        dialog.setMessage("Insert Project's Data: " );
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View add_layout = inflater.inflate(R.layout.project_structure_data,null);
        final TextInputEditText editName = add_layout.findViewById(R.id.txt_nameProject_1);
        dialog.setView(add_layout);
        dialog.setPositiveButton("ADD PROJECT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editName.getText().toString().trim();
                if(name.length() != 0){
                    addProjectService(Resource.emailUserLogin,editName.getText().toString());
                }else{
                    editName.setError("Error ...");
                    editName.requestFocus();
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
    public void addProjectService(String email, final String record){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("email",email);
            postdata.put("record",record);
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url(getString(R.string.url)+"study/createrecord")
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responseData.equals("error")){
                                Toast.makeText(getActivity(),"The name already exists",Toast.LENGTH_SHORT).show();
                            }else{
                                Project project = new Project(record,"");
                                adapter.addElement(project);
                            }
                        }
                    });
                }
            }
        });
    }

}
