package com.example.dashboard.Activity;
//IMPORTS
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.dashboard.Activity.Doctor.DoctorActivity;
import com.example.dashboard.Activity.Study.StudyActivity;
import com.example.dashboard.R;
import com.example.dashboard.Resources.Resource;
import com.example.dashboard.ViewModel.AuthViewModel;
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
    AuthViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        model = new ViewModelProvider(this).get(AuthViewModel.class);
        MaterialToolbar toolbar =findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> signOut());
        /*
        usuarioApp = (ImageView) findViewById(R.id.usuarioApp);
        Glide.with(this).load(Resource.urlImageUserLogin).into(usuarioApp);
        getSupportActionBar().hide();
        */
        Button medicine = findViewById(R.id.btn_medico);
        Button research = findViewById(R.id.btn_estudiante);
        //ImageView exit = findViewById(R.id.exitApp01);
        medicine.setOnClickListener(v -> {
            Resource.role = DOCTOR_ID;
            Intent intent = new Intent(getApplicationContext(), DoctorActivity.class);
            startActivity(intent);
        });
        research.setOnClickListener(v -> {
            Resource.role = STUDENT_ID;
            Intent intent = new Intent(getApplicationContext(), StudyActivity.class);
            startActivity(intent);
        });
    }
    private void signOut() {
        model.logout().observe(this, data->{
            if (data){
                Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}