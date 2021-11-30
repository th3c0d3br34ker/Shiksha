package com.jainamd.shiksha.student;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jainamd.shiksha.R;
import com.jainamd.shiksha.utils.Course;

import java.util.List;

public class StudentCourseAdapter extends RecyclerView.Adapter<StudentCourseAdapter.StudentViewHolder> {

    private final List<Course> courseList;

    public StudentCourseAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.student_course_item, parent, false);

        return new StudentViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Course course = courseList.get(position);

        holder.course_item_title.setText(String.format("%s - %s", course.getCourseId(), course.getName()));
        holder.course_item_marks.setText(String.format("Marks: %d", course.getMarks()));
        holder.course_item_attendance.setText(String.format("Attendance: %d%%", course.getAttendance()));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        LinearLayoutCompat student_course_item_collapse;
        ImageButton toggle_item;
        TextView course_item_title, course_item_marks, course_item_attendance;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            student_course_item_collapse = itemView.findViewById(R.id.student_course_item_collapse);
            toggle_item = itemView.findViewById(R.id.toggle_item);

            if (student_course_item_collapse.getVisibility() == View.GONE) {
                toggle_item.setImageResource(R.drawable.ic_close);
                student_course_item_collapse.setVisibility(View.VISIBLE);
            } else {
                toggle_item.setImageResource(R.drawable.ic_open);
                student_course_item_collapse.setVisibility(View.GONE);
            }

            toggle_item.setOnClickListener(view -> {
                if (student_course_item_collapse.getVisibility() == View.GONE) {
                    student_course_item_collapse.setVisibility(View.VISIBLE);
                    toggle_item.setImageResource(R.drawable.ic_close);
                } else {
                    student_course_item_collapse.setVisibility(View.GONE);
                    toggle_item.setImageResource(R.drawable.ic_open);
                }
            });

            course_item_title = itemView.findViewById(R.id.course_item_title);
            course_item_marks = itemView.findViewById(R.id.course_item_marks);
            course_item_attendance = itemView.findViewById(R.id.course_item_attendance);
        }
    }
}
