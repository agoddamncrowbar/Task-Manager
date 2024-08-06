package com.example.taskorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class taskDetails extends AppCompatActivity {
    TextView taskTitle, taskDetail, taskSupervisor;
    Button report, back;
    String tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_details);
        taskDetail = findViewById(R.id.details_txt);
        taskSupervisor = findViewById(R.id.supervisorTv);
        taskTitle = findViewById(R.id.title_txt);
        report = findViewById(R.id.reportBtn);
        back = findViewById(R.id.backBtn);
        Intent i = getIntent();
        if(i.getStringExtra("Details")!=null){
            taskDetail.setText(i.getStringExtra("Details"));
        }
        if(i.getStringExtra("Supervisor")!=null){
            String sups = "Supervisor" + i.getStringExtra("Supervisor");
            taskSupervisor.setText(sups);
        }
        if(i.getStringExtra("Title")!=null){
            tt = i.getStringExtra("Title");
            taskTitle.setText(tt);

        }
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getApplicationContext(), reportsActivity.class);
                j.putExtra("tl", tt);
                startActivity(j);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),taskRecycler.class));
                finish();
            }
        });
    }
}