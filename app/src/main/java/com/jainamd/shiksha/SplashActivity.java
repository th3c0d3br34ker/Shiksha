package com.jainamd.shiksha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jainamd.shiksha.faculty.FacultyDashboardActivity;
import com.jainamd.shiksha.student.StudentDashboardActivity;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //send user to login or main screen based on login status after 2 secs
        new Handler().postDelayed(() -> {
            if (currentUser == null) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            } else {
                SharedPreferences userPrefs = getSharedPreferences("USER_PREF", MODE_PRIVATE);
                String userType = userPrefs.getString("user_type", null);
                if (userType == null) {
                    Toast.makeText(SplashActivity.this, "User Not Found!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(SplashActivity.this, "User Logged In!", Toast.LENGTH_SHORT).show();
                    if (userType.equals("student")) {
                        startActivity(new Intent(SplashActivity.this, StudentDashboardActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, FacultyDashboardActivity.class));
                        finish();
                    }
                }
            }
            finish();
        }, 1400);
    }
}