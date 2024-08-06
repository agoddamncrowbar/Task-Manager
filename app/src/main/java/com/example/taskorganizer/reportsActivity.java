package com.example.taskorganizer;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class reportsActivity extends AppCompatActivity {
EditText emEdt, rptEdt;
TextView ttl;
Button bk, submitRpt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reports);
        emEdt = findViewById(R.id.userEmEdt);
        rptEdt = findViewById(R.id.reportEdt);
        ttl = findViewById(R.id.t123);
        bk = findViewById(R.id.bkbtn);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email="";
        if (user != null) {
            email = user.getEmail();
        }
        Intent j = getIntent();
        if(j.getStringExtra("tl")!=null){
            ttl.setText(j.getStringExtra("tl"));
        }
        emEdt.setText(email);
        emEdt.setEnabled(false);
        submitRpt = findViewById(R.id.submitRpt);
        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),taskRecycler.class));
                finish();
            }
        });
        submitRpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                Date c = Calendar.getInstance().getTime();
                String currentDte = df.format(c);
                Map<String, Object> map = new HashMap<>();
                map.put("Email",emEdt.getText().toString());
                map.put("Report",rptEdt.getText().toString());
                map.put("Title",ttl.getText().toString());
                map.put("uploaded",currentDte);
                FirebaseDatabase.getInstance().getReference().child("TaskReports").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(reportsActivity.this,"Report Sent to Supervisor",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(reportsActivity.this,"Failed, "+e,Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }
}