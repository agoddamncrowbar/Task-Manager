package com.example.taskorganizer;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class reportListActivity extends AppCompatActivity {
RecyclerView recyclerView;
reportListAdapter rptAdptr;
FirebaseAuth mAuth;
FirebaseUser User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_list);
        recyclerView = findViewById(R.id.reportRv);
        mAuth=FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        User = mAuth.getCurrentUser();
        FirebaseRecyclerOptions<reportModel> options =
                new FirebaseRecyclerOptions.Builder<reportModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("TaskReports"), reportModel.class)
                        .build();
        rptAdptr = new reportListAdapter(options);
        recyclerView.setAdapter(rptAdptr);

    }
    @Override
    protected void onStart() {
        super.onStart();
        rptAdptr.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        rptAdptr.stopListening();
    }
}
