package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Employeedt extends AppCompatActivity {
    private TextView na , ph;
    private Button sa , at , pm , rm;
    private FirebaseFirestore fstore;
    private FirebaseDatabase fbase;
    private FirebaseAuth fauth;
    private ImageView iv;
    private DatabaseReference dref;
    private CalendarView cv;
    private AlertDialog.Builder builder;
    private AlertDialog dialoge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeedt);

        na= findViewById(R.id.tv);
        ph = findViewById(R.id.tvph2);
        iv = findViewById(R.id.ivb);
        sa = findViewById(R.id.btsal);
        at = findViewById(R.id.btatd);
        cv = findViewById(R.id.calendarView);
        pm = findViewById(R.id.btcal);
        rm = findViewById(R.id.btnem);

        fstore = FirebaseFirestore.getInstance();
        fbase = FirebaseDatabase.getInstance();
        fauth = FirebaseAuth.getInstance();

        String Date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(Employeedt.this);

                builder.setMessage("Are  You   Sure   You   Want   to   Remove   Employee.   All   The   Data   Form   The   DataBase   Will   Be   Deleted.").setTitle("Warn").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dref = fbase.getReference("Employe");
                        dref.child("Employe").child(home.name).setValue(null);
                        dref.child("Employee phone").child(home.name).setValue(null);
                        dref.child("Salary").child(home.name).setValue(null);
                        dref.child("Start Date").child(home.name).setValue(null);
                        dref.child("Attendance").child(home.name).setValue(null);
                        dref.child("Name").child(home.name).setValue(null);
                        Toast.makeText(Employeedt.this, "Employee Removed Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Employeedt.this,home.class));
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialoge = builder.create();
                dialoge.show();
                dialoge.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        dialoge.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.cbg));
                        dialoge.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.cbg));

                    }
                });
                Window window =dialoge.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

            }
        });

        pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(Employeedt.this,prevmo.class));

            }
        });

        sa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Employeedt.this,salary.class));
            }
        });


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Employeedt.this,home.class));
                finish();
            }
        });

        at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Employeedt.this,attendance.class));
            }
        });






    }

    @Override
    protected void onStart() {
        super.onStart();

        dref = fbase.getReference("Employe").child("Employee phone");
        if (TextUtils.isEmpty(home.name)) {
            Toast.makeText(this, "Error Code:Edt101", Toast.LENGTH_SHORT).show();
        } else {
            dref.child(home.name).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String num = snapshot.getValue(String.class);
                        ph.setText(num);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            ;
            na.setText("" + home.name);
        }
    }
}