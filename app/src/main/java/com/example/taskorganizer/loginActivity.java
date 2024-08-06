package com.example.taskorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
TextView tv;
Button loginbtn;
ProgressBar pb;

EditText email, pword;
private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String email = "";
            if (user != null) {
                email = user.getEmail();
            }if(email.contains("@taskmanager.com")){
                Intent i = new Intent(getApplicationContext(), adminDash.class);
                startActivity(i);

            }else{
            Intent i = new Intent(getApplicationContext(), taskRecycler.class);
            startActivity(i);
            }
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailEdt);
        pb=findViewById(R.id.pb);
        pword = findViewById(R.id.pwordEdt);
        loginbtn = findViewById(R.id.loginBtn);
        tv = findViewById(R.id.registerTv);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                String em = email.getText().toString();
                String pw = pword.getText().toString();
                mAuth.signInWithEmailAndPassword(em, pw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pb.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if(em.contains("admin@taskmanager.com")){
                                        Intent i = new Intent(getApplicationContext(), adminDash.class);
                                        startActivity(i);
                                    }else{
                                        Intent i = new Intent(getApplicationContext(), taskRecycler.class);
                                        startActivity(i);
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),registerActivity.class);
                startActivity(i);
            }
        });
    }

}