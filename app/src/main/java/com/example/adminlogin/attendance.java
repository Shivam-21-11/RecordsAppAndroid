    package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class attendance extends AppCompatActivity {
    private FirebaseAuth fauth;
    private FirebaseFirestore fstore;
    private FirebaseDatabase fbase;
    private DatabaseReference dref;
    private TextView date , name , stat;
    private Button abs , ret,sh;
    private ImageView ivb;
    public static String Date;
    private static String TAG ="atd";
    public static String sm[] = {"January", "February" , "March","April","May","June","July","August","September","October","November","December"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        fbase = FirebaseDatabase.getInstance();
        fstore = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();

        date = findViewById(R.id.tvdt);
        name = findViewById(R.id.tvn);
        abs = findViewById(R.id.btnabst);
        ret = findViewById(R.id.btnret);
        ivb = findViewById(R.id.ivb1);
        stat = findViewById(R.id.tval);
        sh=findViewById(R.id.btnshow);

        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        String k = sm[m];


        sh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(attendance.this,attendanceshow.class));
            }
        });




        Date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        date.setText(Date);
        name.setText(home.name);

        ivb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(attendance.this, Employeedt.class));
                finish();
            }
        });


        abs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dref = fbase.getReference("Employe").child("Attendance");
                dref.child(home.name).child(""+y).child(k).child(Date).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()){
                            dref.child(home.name).child(""+y).child(k).child(Date).setValue(Date);
                            stat.setText("Marked Absent :"+Date);
                            stat.setVisibility(View.VISIBLE);
                            ret.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(attendance.this, "Already Marked Absent", Toast.LENGTH_SHORT).show();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dref = fbase.getReference("Employe").child("Attendance");
                dref.child(home.name).child(""+y).child(k).child(Date).setValue(null);
                Toast.makeText(attendance.this, "Successful", Toast.LENGTH_SHORT).show();
                stat.setVisibility(View.INVISIBLE);
                ret.setVisibility(View.INVISIBLE);
            }
        });












    }





    @Override
    protected void onStart() {
        super.onStart();

        Calendar calendar = Calendar.getInstance();
        int ys = calendar.get(Calendar.YEAR);
        int ms = calendar.get(Calendar.MONTH);
        String ks = sm[ms];

        dref = fbase.getReference("Employe").child("Attendance");
        dref.child(home.name).child(""+ys).child(ks).child(Date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    stat.setText("Marked Absent :"+Date);
                    stat.setVisibility(View.VISIBLE);
                    ret.setVisibility(View.VISIBLE);
                }else{
                    stat.setVisibility(View.INVISIBLE);
                    ret.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




       

    }
}