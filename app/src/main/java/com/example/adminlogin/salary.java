package com.example.adminlogin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class salary extends AppCompatActivity {
    private FirebaseDatabase fbase;
    private FirebaseFirestore fstore;
    private FirebaseAuth fauth;
    private EditText date,salppd;
    private TextView name , Inf;
    private Button calc , pd , save , ret2;
    private ImageView ibk;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private EditText etm;
    private Spinner sp,sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

        fbase = FirebaseDatabase.getInstance();
        fstore = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();

        date = findViewById(R.id.etdta);
        name = findViewById(R.id.tvnames);
        calc = findViewById(R.id.btncalc);
        ibk = findViewById(R.id.ivback);
        Inf = findViewById(R.id.info);
        pd = findViewById(R.id.btnpaid);
        salppd =findViewById(R.id.etsalpd);
        Inf.setVisibility(View.INVISIBLE);

        pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pop();
            }
        });


        ibk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(salary.this,Employeedt.class));
                finish();
            }
        });

          name.setText(home.name);


        calc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
               String d = date.getText().toString();
               String sal = salppd.getText().toString().trim();



                String ddate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                if (!isValids(d)) {
                    date.setError("Invalid Format ex. 21-11-2001");

                } if(TextUtils.isEmpty(sal)){
                    salppd.setError("Required");

                } else{
                    int ssal = Integer.parseInt(sal);

                    try {
                        Btndays(d,ddate);
                        Inf.setText(" " +salarypday(ssal,+Btndays(d,ddate)) + " Rupees");
                        Inf.setVisibility(View.VISIBLE);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long Btndays (String FirstDate , String SecondDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        Date start = format.parse(FirstDate);
        Date end = format.parse(SecondDate);


            return ChronoUnit.DAYS.between(start.toInstant(),end.toInstant());

    }


    public  boolean isValids(String date){
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

    public static long salarypday(int a , long b){
        final int i = (int) (a * b);
        return i;

    }

    public void pop(){
        builder = new AlertDialog.Builder(salary.this);
        final View popView = getLayoutInflater().inflate(R.layout.popup,null);

        etm = popView.findViewById(R.id.etamnt);
        sp = popView.findViewById(R.id.spinner);
        sp2 = popView.findViewById(R.id.spinner2);
        save = popView.findViewById(R.id.btsav);
        ret2 = popView.findViewById(R.id.btret2);
        ret2.setVisibility(View.INVISIBLE);

        String Date  = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        String itm[] = {"Cash","Bank Transfer","Upi"};
        String Month[] = {"January", "February" , "March","April","May","June","July","August","September","October","November","December"};

        ArrayAdapter<String> adt = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,itm);
        adt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp.setAdapter(adt);


        ArrayAdapter<String> adt1 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Month);
        adt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp2.setAdapter(adt1);


        builder.setView(popView);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String am = etm.getText().toString().trim();
                String mod = sp.getSelectedItem().toString();
                String mon = sp2.getSelectedItem().toString();
                if (TextUtils.isEmpty(am)) {
                    etm.setError("Required");
                } else {
                    DatabaseReference dref = fbase.getReference("Employe");
                    if (TextUtils.isEmpty(am)) {
                        etm.setError("Required");
                    } else {
                        dref.child("Salary").child(home.name).child("" + year).child(mon).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Toast.makeText(salary.this, "Data Already Exist For Selected Month", Toast.LENGTH_SHORT).show();
                                    ret2.setVisibility(View.VISIBLE);
                                } else {
                                    dref.child("Salary").child(home.name).child("" + year).child(mon).child("Mode Of Payment :").setValue(mod);
                                    dref.child("Salary").child(home.name).child("" + year).child(mon).child("Amount :").setValue(am);
                                    dref.child("Salary").child(home.name).child("" + year).child(mon).child("Month :").setValue(mon);
                                    dref.child("Salary").child(home.name).child("" + year).child(mon).child("Date Of DataChange :").setValue(Date);

                                    ret2.setVisibility(View.INVISIBLE);
                                    Toast.makeText(salary.this, "Successful", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });


        ret2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String am = etm.getText().toString().trim();
                String mod = sp.getSelectedItem().toString();
                String mon = sp2.getSelectedItem().toString();
                if (TextUtils.isEmpty(am)) {
                    etm.setError("Required");
                }else{
                    DatabaseReference dref = fbase.getReference("Employe");
                    dref.child("Salary").child(home.name).child("" + year).child(mon).child("Mode Of Payment :").setValue(mod);
                    dref.child("Salary").child(home.name).child("" + year).child(mon).child("Amount :").setValue(am);
                    dref.child("Salary").child(home.name).child("" + year).child(mon).child("Month :").setValue(mon);
                    dref.child("Salary").child(home.name).child("" + year).child(mon).child("Date Of DataChange :").setValue(Date);
                    ret2.setVisibility(View.INVISIBLE);
                    Toast.makeText(salary.this, "Reset Successful", Toast.LENGTH_SHORT).show();

                }
            }
        });



        /*setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String am = etm.getText().toString();
                String mod = sp.getSelectedItem().toString();
                String mon = sp2.getSelectedItem().toString();
                DatabaseReference dref = fbase.getReference("Employe");
                if (TextUtils.isEmpty(am)) {
                    etm.setError("Required");
                } else {
                    dref.child("Salary").child(home.name).child("" + year).child(mon).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(salary.this, "Data Already Exist For Selected Month", Toast.LENGTH_SHORT).show();
                            } else {
                                dref.child("Salary").child(home.name).child("" + year).child(mon).child("Mode Of Payment :").setValue(mod);
                                dref.child("Salary").child(home.name).child("" + year).child(mon).child("Amount :").setValue(am);
                                dref.child("Salary").child(home.name).child("" + year).child(mon).child("Month :").setValue(mon);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });*/
        dialog = builder.create();
        dialog.show();
        Window window =dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);







    }



}