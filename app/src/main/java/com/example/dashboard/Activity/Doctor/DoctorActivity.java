package com.example.dashboard.Activity.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dashboard.Activity.Doctor.Fragments.FragmentDoctorPatients;
import com.example.dashboard.Activity.Doctor.Fragments.FragmentDoctorShare;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;

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

public class DoctorActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private ImageView imagePatients;
    private TextView tetPatients;
    private ImageView imageShared;
    private TextView textShared;
    private CardView cardView;
    private CardView cardViewUsuario;
    private TextView textViewApp;
    private SearchView searchView;
    private SearchView searchViewShare;
    private ImageView usuarioApp;
    private CardView cardOne;
    private CardView cardTwo;
    private boolean flagSearchOpen1;
    private boolean flagSearchOpen2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        getSupportActionBar().hide();
        cardView = (CardView) findViewById(R.id.addCardView);
        cardOne = (CardView) findViewById(R.id.cardOneSearch);
        cardTwo = (CardView) findViewById(R.id.cardTwoSearch);
        imagePatients = (ImageView) findViewById(R.id.imageGroupFragmentDoctor);
        tetPatients = (TextView) findViewById(R.id.textGroupFragmentDoctor);
        imageShared = (ImageView) findViewById(R.id.imageSharedFragmentDoctor);
        textShared = (TextView) findViewById(R.id.textSharedFragmentDoctor);
        cardViewUsuario = (CardView) findViewById(R.id.cardUsuario);
        textViewApp = (TextView) findViewById(R.id.textApp);
        searchView = (SearchView) findViewById(R.id.searchPatient);
        searchViewShare = (SearchView) findViewById(R.id.searchProjectShare);
        usuarioApp = (ImageView) findViewById(R.id.usuarioApp);
        Glide.with(this).load(Resource.urlImageUserLogin).into(usuarioApp);
        //getInfoMedicine();
        flagSearchOpen1 = false;
        flagSearchOpen2 = false;
        searchViewShare.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewUsuario.setVisibility(View.GONE);
                textViewApp.setVisibility(View.GONE);
                flagSearchOpen1 = true;
            }
        });
        searchViewShare .setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                cardViewUsuario.setVisibility(View.VISIBLE);
                textViewApp.setVisibility(View.VISIBLE);
                flagSearchOpen1 = false;
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewUsuario.setVisibility(View.GONE);
                textViewApp.setVisibility(View.GONE);
                flagSearchOpen2 = true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                cardViewUsuario.setVisibility(View.VISIBLE);
                textViewApp.setVisibility(View.VISIBLE);
                flagSearchOpen2 = false;
                return false;
            }
        });


        List<Fragment> list = new ArrayList<>();
        list.add(new FragmentDoctorPatients());
        list.add(new FragmentDoctorShare());
        pager = findViewById(R.id.doctorPager);
        pagerAdapter = new DoctorPagerAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(pagerAdapter);
        imageShared.setColorFilter(Color.GRAY);
        textShared.setTextColor(Color.GRAY);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    imagePatients.setColorFilter(Color.WHITE);
                    tetPatients.setTextColor(Color.WHITE);
                    imageShared.setColorFilter(Color.GRAY);
                    textShared.setTextColor(Color.GRAY);
                    searchView.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                    cardOne.setVisibility(View.VISIBLE);
                    cardTwo.setVisibility(View.GONE);
                    if(flagSearchOpen1){
                        cardViewUsuario.setVisibility(View.VISIBLE);
                        textViewApp.setVisibility(View.VISIBLE);
                    }
                    if(flagSearchOpen2){
                        cardViewUsuario.setVisibility(View.GONE);
                        textViewApp.setVisibility(View.GONE);
                    }

                }else if( position == 1){
                    imagePatients.setColorFilter(Color.GRAY);
                    tetPatients.setTextColor(Color.GRAY);
                    imageShared.setColorFilter(Color.WHITE);
                    textShared.setTextColor(Color.WHITE);
                    cardView.setVisibility(View.GONE);
                    cardOne.setVisibility(View.GONE);
                    cardTwo.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.GONE);
                    if(flagSearchOpen2){
                        cardViewUsuario.setVisibility(View.VISIBLE);
                        textViewApp.setVisibility(View.VISIBLE);
                    }
                    if(flagSearchOpen1){
                        cardViewUsuario.setVisibility(View.GONE);
                        textViewApp.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void changeFragment(View view) {
        switch (view.getId()){
            case R.id.fragment1:{
                pager.setCurrentItem(0);
                break;
            }
            case R.id.fragment2:{
                pager.setCurrentItem(1);
                break;
            }
        }
    }


    public void getInfoMedicine(){
        MediaType MEDIA_TYPE =
                MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(

                60,TimeUnit.SECONDS).build();
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
                System.out.println("Wilmer ERROR :" + e);
            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    System.out.println("-------**********-----------\n"+responseData);
                    try {
                        Resource.infoMedicine =  new JSONObject(responseData);;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
