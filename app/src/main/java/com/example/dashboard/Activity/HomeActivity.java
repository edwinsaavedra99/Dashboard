package com.example.dashboard.Activity;
//IMPORTS
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dashboard.Activity.Doctor.DoctorActivity;
import com.example.dashboard.Activity.Study.StudyActivity;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * This class define the Home Activity : Role of the user
 * @author Edwin Saavedra
 * @version 4
 */
public class HomeActivity extends AppCompatActivity {
    final int DOCTOR_ID = 1;
    final int STUDENT_ID = 2;
    private ImageView usuarioApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MaterialToolbar toolbar =findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        /*
        usuarioApp = (ImageView) findViewById(R.id.usuarioApp);
        Glide.with(this).load(Resource.urlImageUserLogin).into(usuarioApp);
        getSupportActionBar().hide();
        */
        Button medicine = findViewById(R.id.btn_medico);
        Button research = findViewById(R.id.btn_estudiante);
        //ImageView exit = findViewById(R.id.exitApp01);
        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resource.role = DOCTOR_ID;
                Intent intent = new Intent(getApplicationContext(), DoctorActivity.class);
                startActivity(intent);
            }
        });
        research.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resource.role = STUDENT_ID;
                Intent intent = new Intent(getApplicationContext(), StudyActivity.class);
                startActivity(intent);
            }
        });
        /*
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });*/
    }
    private void signOut() {

        Resource.SignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });


    }
}