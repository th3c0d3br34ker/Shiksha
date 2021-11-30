package com.jainamd.shiksha.faculty.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jainamd.shiksha.R;
import com.jainamd.shiksha.utils.Student;

import java.util.List;

public class PostAttendanceAdapter extends RecyclerView.Adapter<PostAttendanceAdapter.AttendanceViewHolder> {
    private final Context context;
    private final List<Student> usersList;

    public PostAttendanceAdapter(Context context, List<Student> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public PostAttendanceAdapter.AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.post_marks_item, parent, false);

        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAttendanceAdapter.AttendanceViewHolder holder, int position) {
        Student student = usersList.get(position);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        holder.account_id.setText(student.getAccountId());
        holder.attendance_input.setText(student.getAttendance());
        holder.post_marks_btn.setOnClickListener(
                view -> {
                    Toast.makeText(context, "Attendance Updated", Toast.LENGTH_LONG).show();
                    Integer newAttendance = Integer.valueOf(holder.attendance_input.getText().toString());
                    myRef
                            .child(student.getUuid())
                            .child("courses")
                            .child(student.getCourseCode())
                            .child("attendance")
                            .setValue(newAttendance);
                }
        );
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }


    public static class AttendanceViewHolder extends RecyclerView.ViewHolder {

        TextView account_id;
        TextInputEditText attendance_input;
        ImageButton post_marks_btn;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);

            account_id = itemView.findViewById(R.id.account_id);
            attendance_input = itemView.findViewById(R.id.marks_input);
            post_marks_btn = itemView.findViewById(R.id.post_marks_btn);
        }
    }
}
