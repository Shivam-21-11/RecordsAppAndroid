package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class add extends AppCompatActivity {
    private static final String TAG = "add";
    private EditText na , pa , sdt;
    private Button bt;
    private FirebaseAuth fauth;
    private FirebaseFirestore fstore;
    private FirebaseDatabase fbase;
    private DatabaseReference df;
    private ImageView iv;
    public static String pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        na = findViewById(R.id.etname);
        pa = findViewById(R.id.etphone);
        bt = findViewById(R.id.btnc);
        iv = findViewById(R.id.iv);
        sdt = findViewById(R.id.etsd);

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        fbase = FirebaseDatabase.getInstance();

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(add.this,home.class));
                finish();
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nav = na.getText().toString();
                String ph = pa.getText().toString();
                 pd = sdt.getText().toString();

                if(TextUtils.isEmpty(nav)){
                    na.setError("Required");
                }if(TextUtils.isEmpty(ph) || ph.length() != 10){
                    pa.setError("Enter Valid Phone Number");
                }if(TextUtils.isEmpty(pd) ){
                    sdt.setError("Required");
                }if(!isValid(pd)){
                    sdt.setError("Date Format ex. 21-11-2001");

                }
                else{
                    // return to home and show id
                    asd(nav,ph,pd);
                }
            }
        });
    }
    private void asd(String a , String b , String c) {




        df = fbase.getReference("Employe");
        df.child("Name").child(""+a).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() !=null){
                    //user exist
                    Toast.makeText(add.this, "Employee Already Exist", Toast.LENGTH_SHORT).show();
                }else{
                    //firebase real time


                    df.child("Employe").child(""+a).setValue(a);
                    df.child("Employee phone").child(""+a).setValue("+91"+b);
                    df.child("Start Date").child(a).setValue(c);
                    df.child("Name").child(a).setValue(a);




                    //firestore
                    Map<String, Object> User = new HashMap<>();
                    User.put("Name ", a);
                    User.put("Phone ", "+91"+b);
                    User.put("Start Date",c);
                    User.put("isEmployee ", 1);
                    fstore.collection("Employee Details").document(a).set(User).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: Task Successfull ");
                            startActivity(new Intent(add.this, home.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Task Failed");
                            Toast.makeText(add.this, "Data Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
    public  boolean isValid(String date){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setLenient(false);
        try{
            format.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return  false;
        }
        return true;
    }

}