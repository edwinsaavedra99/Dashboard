package com.example.dashboard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import com.example.dashboard.Adapter.FileProjectAdapter;
import com.example.dashboard.Adapter.PatientAdapter;
import com.example.dashboard.Models.FileProject;
import com.example.dashboard.Models.Patient;
import com.example.dashboard.R;
import java.util.ArrayList;
import java.util.List;

public class FileProjectActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFileProject;
    private FileProjectAdapter adapterFileProject;
    private RecyclerView.LayoutManager layoutManagerFileProject;
    private TextView textViewApp;
    private SearchView searchViewFileProject;
    private ImageView addFileProject;
    private CardView cardViewUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_project);
        getSupportActionBar().hide();
        /*DATA VIEW*/
        textViewApp = findViewById(R.id.textApp);
        cardViewUsuario = (CardView) findViewById(R.id.cardUsuario);
        searchViewFileProject = findViewById(R.id.searchFileProject);
        addFileProject = findViewById(R.id.addFileProject);
        searchViewFileProject.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchViewFileProject.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterFileProject.getFilter().filter(newText);
                return false;
            }
        });
        /*DATA BASE*/

        searchViewFileProject.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewUsuario.setVisibility(View.GONE);
                textViewApp.setVisibility(View.GONE);

            }
        });
        searchViewFileProject.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                cardViewUsuario.setVisibility(View.VISIBLE);
                textViewApp.setVisibility(View.VISIBLE);
                return false;
            }
        });



        List list = new ArrayList();
        list.add(new FileProject("CARGA IMAGEN IN STRIN64","NOMBRE DE FILE PROJECT","ESTE SE REFIERE A LA FRACTURA DE UNA PIERNA"));
        list.add(new FileProject("CARGA IMAGEN IN STRIN64","NOMBRE DE FILE PROJECT","ESTE SE REFIERE A LA FRACTURA DE UNA PIERNA"));
        list.add(new FileProject("CARGA IMAGEN IN STRIN64","NOMBRE DE FILE PROJECT","ESTE SE REFIERE A LA FRACTURA DE UNA PIERNA"));
        list.add(new FileProject("CARGA IMAGEN IN STRIN64","NOMBRE DE FILE PROJECT","ESTE SE REFIERE A LA FRACTURA DE UNA PIERNA"));
        addFileProject.setBackgroundColor(Color.parseColor("#80000000"));
        recyclerViewFileProject = (RecyclerView) findViewById(R.id.recicler_File);
        recyclerViewFileProject.setHasFixedSize(true);
        layoutManagerFileProject = new LinearLayoutManager(getApplicationContext());
        recyclerViewFileProject.setLayoutManager(layoutManagerFileProject);
        adapterFileProject =  new FileProjectAdapter(list,getApplicationContext());
        recyclerViewFileProject.setAdapter(adapterFileProject);


    }
}
