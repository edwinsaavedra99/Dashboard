package com.example.dashboard.Activity.Study;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dashboard.Activity.Doctor.DoctorPagerAdapter;
import com.example.dashboard.Activity.Doctor.Fragments.FragmentDoctorPatients;
import com.example.dashboard.Activity.Doctor.Fragments.FragmentDoctorShare;
import com.example.dashboard.Activity.MainActivity;
import com.example.dashboard.Activity.Study.Fragments.FragmentStudyProjects;
import com.example.dashboard.Activity.Study.Fragments.FragmentStudyShare;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
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

public class StudyActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private ImageView imagePatients;
    private TextView tetPatients;
    private ImageView imageShared;
    private TextView textShared;
    private CardView cardView;
    protected static final int PICK_IMAGE =0;
    protected static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageurl;
    private ImageView import_;
    private AlertDialog dialog_image;
    private ImageView camera;
    private ImageView galery;
    private TextView txt_camera;
    private  TextView txt_galery;
    private int flagimage =0;
    private String currentPhotoPath;
    private ImageButton imageButton;
    private JSONObject respuestaConsulta;
    private CardView cardViewUsuario;
    private TextView textViewApp;
    private SearchView searchView;
    private ImageView addProject;
    private SearchView searchViewShare;
    private CardView cardOne;
    private CardView cardTwo;
    private boolean flagSearchOpen1;
    private boolean flagSearchOpen2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        getSupportActionBar().hide();
        cardView = (CardView) findViewById(R.id.addCardViewStudy);
        imagePatients = (ImageView) findViewById(R.id.imageGroupFragmentStudy);
        tetPatients = (TextView) findViewById(R.id.textGroupFragmentStudy);
        imageShared = (ImageView) findViewById(R.id.imageSharedFragmentStudy);
        textShared = (TextView) findViewById(R.id.textSharedFragmentStudy);
        cardViewUsuario = (CardView) findViewById(R.id.cardUsuarioStudy);
        textViewApp = (TextView) findViewById(R.id.textAppStudy);
        searchView = (SearchView) findViewById(R.id.searchProjectStudy);
        cardOne = (CardView) findViewById(R.id.cardOneSearchStudy);
        cardTwo = (CardView) findViewById(R.id.cardTwoSearchStudy);
        searchViewShare = (SearchView) findViewById(R.id.searchProjectStudyShare);
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
        list.add(new FragmentStudyProjects());
        list.add(new FragmentStudyShare());
        pager = findViewById(R.id.studyPager);
        pagerAdapter = new StudyPagerAdapter(getSupportFragmentManager(),list);
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
                    searchView.setVisibility(View.GONE);
                    cardOne.setVisibility(View.GONE);
                    cardTwo.setVisibility(View.VISIBLE);
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
            case R.id.fragment1Study:{
                pager.setCurrentItem(0);
                break;
            }
            case R.id.fragment2Study:{
                pager.setCurrentItem(1);
                break;
            }
        }
    }



}
