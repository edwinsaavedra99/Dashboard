package com.example.dashboard.Activity;
//IMPORTS
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.dashboard.Activity.Doctor.DoctorActivity;
import com.example.dashboard.Activity.Study.StudyActivity;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * This class define the Home Activity : Role of the user
 * @author Edwin Saavedra
 * @version 4
 */
public class HomeActivity extends AppCompatActivity {
    final int DOCTOR_ID = 1;
    final int STUDENT_ID = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        ConstraintLayout doctorLayout = findViewById(R.id.constraintLayout);
        ConstraintLayout studentLayout = findViewById(R.id.constraintLayout01);
        ImageView exit = findViewById(R.id.exitApp01);
        doctorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resource.role = DOCTOR_ID;
                Intent intent = new Intent(getApplicationContext(), DoctorActivity.class);
                getApplicationContext().startActivity(intent);
            }
        });
        studentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resource.role = STUDENT_ID;
                Intent intent = new Intent(getApplicationContext(), StudyActivity.class);
                getApplicationContext().startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
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