package com.jainamd.shiksha;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jainamd.shiksha.faculty.FacultyDashboardActivity;
import com.jainamd.shiksha.student.StudentDashboardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @BindView(R.id.login_email)
    TextInputEditText login_email;

    @BindView(R.id.password)
    TextInputEditText login_password;

    @BindView(R.id.login_btn)
    MaterialButton login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users = database.getReference("users");

        mAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(view -> {
            String email = login_email.getText().toString();
            String password = login_password.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(view, "Invaild Input", Snackbar.LENGTH_SHORT).show();
            } else {

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            users.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.child("account_type").getValue().toString().equalsIgnoreCase("student")) {
                                        startActivity(new Intent(LoginActivity.this, StudentDashboardActivity.class));
                                        finish();
                                    } else {
                                        startActivity(new Intent(LoginActivity.this, FacultyDashboardActivity.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Snackbar.make(view, "Please Try Again Later!", Snackbar.LENGTH_LONG).show();
                                    Log.e("DATABASE_ERROR", error.getMessage());
                                }
                            });
                        } else {
                            Snackbar.make(view, "Please Try Again Later!", Snackbar.LENGTH_LONG).show();
                            Log.e("AUTHENTICATION_ERROR", task.getException().getMessage());
                        }
                    }
                });
            }
        });


    }
}