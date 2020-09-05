package com.example.dashboard.Activity.Study;

import androidx.annotation.NonNull;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dashboard.Activity.Doctor.DoctorPagerAdapter;
import com.example.dashboard.Activity.Doctor.Fragments.FragmentDoctorPatients;
import com.example.dashboard.Activity.Doctor.Fragments.FragmentDoctorShare;
import com.example.dashboard.Activity.MainActivity;
import com.example.dashboard.Activity.Study.Fragments.FragmentStudyProjects;
import com.example.dashboard.Activity.Study.Fragments.FragmentStudyShare;
import com.example.dashboard.Models.Project;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    public BottomNavigationView bottom_navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        bottom_navigation = findViewById(R.id.bottom_navigation);

        MaterialToolbar toolbar =findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<Fragment> list = new ArrayList<>();
        list.add(new FragmentStudyProjects());
        list.add(new FragmentStudyShare());
        pager = findViewById(R.id.studyPager);
        pagerAdapter = new StudyPagerAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    bottom_navigation.getMenu().findItem(R.id.proyecto).setChecked(true);
                }else if( position == 1){
                    bottom_navigation.getMenu().findItem(R.id.compartido).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.proyecto:
                            pager.setCurrentItem(0);
                            return true;
                        case R.id.compartido:
                            pager.setCurrentItem(1);
                            return true;
                    }
                    return false;
                }
            };
}