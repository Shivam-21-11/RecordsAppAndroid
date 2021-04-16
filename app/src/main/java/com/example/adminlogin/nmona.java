package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class nmona extends AppCompatActivity {
    private FirebaseAuth fauth;
    private FirebaseFirestore fstore;
    private FirebaseDatabase fbase;
    private DatabaseReference dref;
    private ListView lview;
    private ImageView iv;
    public ArrayList<String> mList = new ArrayList<>();
    private TextView tg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmona);

        tg =findViewById(R.id.tvb);

        tg.setText("( "+attendanceshow.mra+" )");

        iv = findViewById(R.id.ivbiv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(nmona.this,attendanceshow.class));
                finish();
            }
        });

        lview =findViewById(R.id.lv1);

    }

    @Override
    protected void onStart() {
        super.onStart();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.row,R.id.tvtv,mList);
        lview.setAdapter(adapter);
        fbase = FirebaseDatabase.getInstance();
        dref = fbase.getReference("Employe").child("Attendance");
        dref.child(home.name).child(attendanceshow.yra).child(attendanceshow.mra).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String Value = snapshot.getValue(String.class);
                mList.add(Value);
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