package com.example.taskorganizer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class settingsActivity extends AppCompatActivity {
    TextView accTxt, statustxt;
    Button logOutBtn, delBtn;
    EditText eml, pword;
    String pwrd = "";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        eml = findViewById(R.id.emailEt);
        pword = findViewById(R.id.pwordEt);
        accTxt = findViewById(R.id.nmeTxt);
        statustxt = findViewById(R.id.statusTxt);
        logOutBtn = findViewById(R.id.logoutBtn);
        delBtn = findViewById(R.id.deleteUsr);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        if (user != null) {
            email = user.getEmail();
        }
        accTxt.setText("Email: " + email);
        eml.setText(email);
        if(email.contains("admin@taskmanager.com")){
            statustxt.setText("Status: Admin");
        }else{
            statustxt.setText("Status: Intern");
        }

        String finalEmail = email;

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View vv = LayoutInflater.from(settingsActivity.this).inflate(R.layout.confirm_delete_dialog, null);
                TextInputEditText pwww = vv.findViewById(R.id.pwEt);
                AlertDialog ad = new MaterialAlertDialogBuilder(settingsActivity.this)
                        .setTitle("Delete:" + finalEmail)
                        .setView(vv)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                pwrd = pwww.getText().toString();
                                loginzz(pwrd);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create();
                ad.show();

                }

        });
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
            }
        });

    }

    Boolean b = false;

    public Boolean loginzz(String pw) {
        String em = eml.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        if (em.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email Empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pw.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Password Empty", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            FirebaseAuth.getInstance().signOut();
            mAuth.signInWithEmailAndPassword(em, pw)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(getApplicationContext(), taskRecycler.class);
                                startActivity(i);
                                b = true;
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "User account deleted.", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), loginActivity.class));
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                b = false;
                            }
                        }
                    });
            return b;
        }
    }
}