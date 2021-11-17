package com.jainamd.shiksha.student;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        ButterKnife.bind(this);

        SharedPreferences userPrefs = getSharedPreferences("USER_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.putString("userPrefs", "student");
        editor.apply();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference courses = database.getReference("users").child(FirebaseAuth.getInstance().getUid()).child("courses");

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DATABASE_ERROR", error.getMessage());
            }
        });

        DatabaseReference accountId = database.getReference("users").child(FirebaseAuth.getInstance().getUid()).child("account_id");

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
}