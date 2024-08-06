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

public class addTask extends AppCompatActivity {
    EditText taskTitleA, taskDetailsz;
    Button bk, submitTsk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        taskTitleA = findViewById(R.id.taskTitleA);
        taskDetailsz = findViewById(R.id.taskDetailsA);
        bk = findViewById(R.id.backA);
        submitTsk = findViewById(R.id.uploadTskA);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email="";
        if (user != null) {
            email = user.getEmail();
        }

        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),adminDash.class));
                finish();
            }
        });
        String [] spliyz= email.split("@");
        String finalEmail = spliyz[0];
        submitTsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                Date c = Calendar.getInstance().getTime();
                String currentDte = df.format(c);
                Map<String, Object> map = new HashMap<>();
                map.put("supervisor", finalEmail);
                map.put("details",taskDetailsz.getText().toString());
                map.put("task",taskTitleA.getText().toString());
                map.put("uploaded",currentDte);
                FirebaseDatabase.getInstance().getReference().child("TaskManager").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(addTask.this,"Task Added",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(addTask.this,"Failed, "+e,Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}