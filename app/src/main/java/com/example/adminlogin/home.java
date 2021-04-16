package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class home extends AppCompatActivity {
    private Button out;
    private FirebaseAuth fauth;
    private FirebaseFirestore fstore;
    private Button btn;
    public static String name;
    private ListView lv;
    private FirebaseDatabase fbase;
    private DatabaseReference df;
    ArrayList list ;
    Context context;
    public ArrayList<String> myList =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        out = findViewById(R.id.btnout);
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        fbase = FirebaseDatabase.getInstance();
        btn=findViewById(R.id.btnadd);


        lv = findViewById(R.id.lvi);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 name =  myList.get(position).toString();
                 startActivity(new Intent(home.this,Employeedt.class));
                 finish();

            }
        });











        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this,add.class));
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fauth.signOut();
                startActivity(new Intent(home.this,MainActivity.class));
                finish();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();



            ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.row, R.id.tvtv, myList);

            lv.setAdapter(adapter);
            df = fbase.getReference("Employe").child("Name");
            df.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    String Value = snapshot.getValue(String.class);
                    myList.add(Value);
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

}