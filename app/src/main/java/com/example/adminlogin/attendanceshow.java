package com.example.adminlogin;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Arrays;

public class attendanceshow extends AppCompatActivity {
    private FirebaseAuth fauth;
    private FirebaseFirestore fstore;
    private FirebaseDatabase fbase;
    private DatabaseReference dref;
    private ListView lmv;
    private Spinner l ;
    private ImageView ik;
    public static String yra;
    public static String mra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendanceshow);

        lmv = findViewById(R.id.lvmo);
        ik = findViewById(R.id.ivbk);
        l = findViewById(R.id.spinneryr1);

        String month[] = {"January", "February" , "March","April","May","June","July","August","September","October","November","December"};
        String Year[] = {"2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033"};

        ArrayAdapter<String> adt = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Year);
        adt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        l.setAdapter(adt);

        ArrayAdapter<String> adt2 = new ArrayAdapter<String>(this,R.layout.row,R.id.tvtv, Arrays.asList(month));
        lmv.setAdapter(adt2);

        lmv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(attendanceshow.this,nmona.class));
                mra = month[position].toString();
            }
        });

        l.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yra = Year[position].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                yra = Year[0];
            }
        });


        ik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(attendanceshow.this,attendance.class));
                finish();
            }
        });






    }
}