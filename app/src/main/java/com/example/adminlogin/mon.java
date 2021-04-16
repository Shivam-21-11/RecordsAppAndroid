package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mon extends AppCompatActivity {
    private FirebaseDatabase fbase;
    private FirebaseAuth fauth;
    private TextView m1 , m2 ,yr,da,mop ,amt,mont,yr2 , mops , amn , daw,w;
    private ImageView ivi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon);

        m1 = findViewById(R.id.montv);
        m2 = findViewById(R.id.montv2);
        yr = findViewById(R.id.yrtv);
        da = findViewById(R.id.doptv);
        amt = findViewById(R.id.amttv);
        mop = findViewById(R.id.moptv);
        mont = findViewById(R.id.mondtv);
        ivi = findViewById(R.id.iv2);
        yr2 = findViewById(R.id.yrtv2);
        mops = findViewById(R.id.mop);
        amn = findViewById(R.id.amtp);
        daw = findViewById(R.id.dop);
        w = findViewById(R.id.warn);
        w.setVisibility(View.INVISIBLE);




       /* fbase = FirebaseDatabase.getInstance();
        DatabaseReference dref = fbase.getReference("Employe");
        dref.child("Salary").child(home.name).child(prevmo.yre).child(prevmo.month0).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String dop = snapshot.child("Date Of DataChange :").getValue().toString();
                    String mop = snapshot.child("Mode Of Payment :").getValue().toString();
                    String amnw = snapshot.child("Amount :").getValue().toString();
                    if (TextUtils.isEmpty(dop) || TextUtils.isEmpty(mop) || TextUtils.isEmpty(amnw)) {
                        Toast.makeText(mon.this, "Data Does not Exist", Toast.LENGTH_SHORT).show();
                    }else{
                        daw.setText(dop);
                        mops.setText(mop);
                        amn.setText(amnw);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/



        m1.setText(prevmo.month0);
        mont.setText(prevmo.month0);
        yr2.setText(prevmo.yre);

        ivi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mon.this,prevmo.class));
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        fbase = FirebaseDatabase.getInstance();
        DatabaseReference dref = fbase.getReference("Employe");

        if(TextUtils.isEmpty(prevmo.month0)){
            Toast.makeText(this, "Error:prevo_Mon", Toast.LENGTH_SHORT).show();
        }if(TextUtils.isEmpty(prevmo.yre)){
            Toast.makeText(this, "Error:prevo_yre", Toast.LENGTH_SHORT).show();
        }else{

        dref.child("Salary").child(prevmo.nava).child(prevmo.yre).child(prevmo.month0).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String dop = snapshot.child("Date Of DataChange :").getValue().toString();
                    String mop = snapshot.child("Mode Of Payment :").getValue().toString();
                    String amnw = snapshot.child("Amount :").getValue().toString();
                    w.setVisibility(View.INVISIBLE);
                    if (TextUtils.isEmpty(dop) || TextUtils.isEmpty(mop) || TextUtils.isEmpty(amnw)) {
                        Toast.makeText(mon.this, "Data Does not Exist", Toast.LENGTH_SHORT).show();

                    }else{
                        daw.setText(dop);
                        mops.setText(mop);
                        amn.setText(amnw);

                    }
                }else{
                    Toast.makeText(mon.this, "Data Does Not Exist", Toast.LENGTH_SHORT).show();
                    w.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }

}