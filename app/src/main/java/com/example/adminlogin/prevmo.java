
package com.example.adminlogin;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class prevmo extends AppCompatActivity {
    private ListView lview;
    private FirebaseDatabase fbase;
    private FirebaseAuth fauth;
    private ImageView iview;
    public static String month0 ;
    private Spinner spinner;
    public static String yre;
    public static String nava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevmo);

        lview = findViewById(R.id.lvm);
        iview = findViewById(R.id.imageView);
        spinner = findViewById(R.id.spinneryr);

        String month[] = {"January", "February" , "March","April","May","June","July","August","September","October","November","December"};
        String Year[] = {"2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033"};

        ArrayAdapter<String> myAdt = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Year);
        myAdt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(myAdt);
        nava = home.name;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yre = Year[position].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               yre = "2021";
            }
        });




        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.row,R.id.tvtv, Arrays.asList(month));
        lview.setAdapter(arrayAdapter);

        iview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(prevmo.this,Employeedt.class));
                finish();
            }
        });

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                month0 = month[position].toString();
                startActivity(new Intent(prevmo.this,mon.class));


            }
        });


    }
}