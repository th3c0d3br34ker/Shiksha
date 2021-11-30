package com.jainamd.shiksha.faculty.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jainamd.shiksha.R;
import com.jainamd.shiksha.utils.Student;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAttendanceActivity extends AppCompatActivity {

    @BindView(R.id.faculty_post_attendance_item_collapse)
    RecyclerView faculty_post_attendance_item_collapse;

    @BindView(R.id.go_back_btn)
    ImageButton go_back_btn;

    @BindView(R.id.account_id)
    TextView account_id;

    @BindView(R.id.post_attendance_loader)
    ProgressBar post_attendance_loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_attendance);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String courseCode = intent.getStringExtra("course_id");
        account_id.setText(String.format("Post Attendance | %s", courseCode));

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

        go_back_btn.setOnClickListener(view -> finish());

        DatabaseReference students = mDatabase.getReference("users");

        students.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    List<Student> usersList = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        if (snap.child("account_type").getValue().toString().equals("student")) {
                            String accountId = snap.child("account_id").getValue().toString();
                            String attendance;

                            Student student = new Student(accountId);

                            if (snap.child("courses").exists() && snap.child("courses").hasChild(courseCode)) {
                                attendance = snap.child("courses").child(courseCode).child("attendance").getValue().toString();
                                student.setAttendance(attendance);
                            }

                            student.setCourseCode(courseCode);
                            student.setUuid(snap.getKey());

                            usersList.add(student);
                        }
                    }

                    faculty_post_attendance_item_collapse.setAdapter(new PostAttendanceAdapter(getApplicationContext(), usersList));
                    post_attendance_loader.setVisibility(View.GONE);
                    faculty_post_attendance_item_collapse.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DATABASE_ERROR", error.getMessage());
                post_attendance_loader.setVisibility(View.GONE);
            }
        });
    }
}