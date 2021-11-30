package com.jainamd.shiksha.student;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jainamd.shiksha.LoginActivity;
import com.jainamd.shiksha.R;
import com.jainamd.shiksha.utils.Course;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentDashboardActivity extends AppCompatActivity {
    @BindView(R.id.student_course_recycler_view)
    RecyclerView student_course_recycler_view;

    @BindView(R.id.account_id)
    TextView account_id;

    @BindView(R.id.student_dashboard_loader)
    ProgressBar student_dashboard_loader;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference courses = database.getReference("users").child(mAuth.getUid()).child("courses");

        courses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    List<Course> courseList = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        courseList.add(new Course(
                                snap.getKey(),
                                snap.child("name").getValue().toString(),
                                Integer.valueOf(snap.child("attendance").getValue().toString()),
                                Integer.valueOf(snap.child("marks").getValue().toString())
                        ));
                    }

                    student_course_recycler_view.setAdapter(new StudentCourseAdapter(courseList));
                    student_dashboard_loader.setVisibility(View.GONE);
                    student_course_recycler_view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                student_dashboard_loader.setVisibility(View.GONE);
                Log.e("DATABASE_ERROR", error.getMessage());
            }
        });

        DatabaseReference accountId = database.getReference("users").child(mAuth.getUid()).child("account_id");

        accountId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                account_id.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DATABASE_ERROR", error.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_button:
                Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show();
                mAuth.signOut();
                startActivity(
                        new Intent(StudentDashboardActivity.this, LoginActivity.class)
                );
                finish();
                break;
            default:
                Toast.makeText(this, "Invalid Selection", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}