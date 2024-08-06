package com.example.taskorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class taskRecycler extends AppCompatActivity {
    RecyclerView rv;
    recyclerAdapter reAdapter;
    FloatingActionButton fab;
    TextView logout;
    FirebaseAuth mAuth;
    FirebaseUser User;
    DatabaseReference dbr;
    String uname;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_recycler);
        rv= findViewById(R.id.rV);
        rv.setItemAnimator(null);
        mAuth=FirebaseAuth.getInstance();
        btn=findViewById(R.id.settingsBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),settingsActivity.class));
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        User = mAuth.getCurrentUser();
        FirebaseRecyclerOptions<taskModel> options =
                new FirebaseRecyclerOptions.Builder<taskModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("TaskManager"), taskModel.class)
                        .build();
        reAdapter = new recyclerAdapter(options,getApplicationContext());
        rv.setAdapter(reAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        reAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        reAdapter.stopListening();
    }
}
