package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private EditText e1 , e2;
    private Button l1;
    private FirebaseFirestore fstore;
    private boolean lpFlag = false;
    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = findViewById(R.id.etuname);
        e2 = findViewById(R.id.etpass);
        l1 = findViewById(R.id.btnlog);

        fstore = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();


        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = e1.getText().toString();
                String Pass = e2.getText().toString();

                if(TextUtils.isEmpty(Name)){
                    e1.setError("Required");
                }if(TextUtils.isEmpty(Pass)){
                    e2.setError("Required");
                }else{
                    sig(Name,Pass);

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser User =fauth.getCurrentUser();

        if(User != null){
            startActivity(new Intent(MainActivity.this,home.class));
            finish();
        }
    }

    public void  sig(String a , String b){
        fauth.signInWithEmailAndPassword(a,b).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                checkStat(fauth.getCurrentUser().getUid());

            }
        });
    }

    private void checkStat(String uid) {
        fstore.collection("Accounts").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.get("isAdmin") != null) {
                    startActivity(new Intent(MainActivity.this, home.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Invalid Authorization State", Toast.LENGTH_SHORT).show();
                    fauth.signOut();
                }
            }
        });
        }
    }


