package com.example.dashboard.Activity.Doctor.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dashboard.Adapter.FileProjectAdapter;
import com.example.dashboard.Adapter.PatientAdapter;
import com.example.dashboard.Adapter.SharedFileAdapter;
import com.example.dashboard.Models.FileProject;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.Models.SharedFile;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.example.dashboard.Utils.StringUtil;

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

public class FragmentDoctorShare extends Fragment {

    private RecyclerView recyclerView;
    private SharedFileAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private LinearLayout linearLayout;
    private List list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_fragment_doctor_share, container, false);
        linearLayout = viewGroup.findViewById(R.id.noDataShareDoctor);

        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recicler_project_file);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        getInfoMedicine();
        return viewGroup;
    }
    public void getInfoMedicine(){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("email", Resource.emailUserLogin);
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url(getString(R.string.url)+"medicine/shared/selectfiles") /*URL ... INDEX PX DE WILMER*/
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
                    if(getActivity() == null)
                        return;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Resource.infoStudy =  new JSONObject(responseData);;
                                if(Resource.infoStudy!=null){
                                    list = new ArrayList();
                                    try {
                                        JSONArray jsonArray = Resource.infoStudy.getJSONArray("shared");
                                        for(int k = 0; k < jsonArray.length();k++){
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(k);
                                            String image = jsonObject1.getString("image");//
                                            String name = jsonObject1.getString("file");//
//                                            String description = jsonObject1.getString("description");//
                                            String date = jsonObject1.getString("date");//
                                            String email = jsonObject1.getString("email");//
                                            String id = jsonObject1.getString("id");//
                                            String privilege = jsonObject1.getString("privilege");
                                            String record = jsonObject1.getString("record");
                                            int patient = jsonObject1.getInt("patient");
                                            list.add(new SharedFile(image,name,"",date,email,privilege,id,record,patient));
                                        }
                                        adapter =  new SharedFileAdapter(list,getActivity());
                                        recyclerView.setAdapter(adapter);
                                        if(jsonArray.length() == 0)
                                            linearLayout.setVisibility(View.VISIBLE);
                                        else
                                            linearLayout.setVisibility(View.GONE);

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
}
