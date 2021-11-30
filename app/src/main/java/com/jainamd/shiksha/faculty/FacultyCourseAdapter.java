package com.jainamd.shiksha.faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jainamd.shiksha.R;
import com.jainamd.shiksha.faculty.attendance.PostAttendanceActivity;
import com.jainamd.shiksha.faculty.marks.PostMarksActivity;
import com.jainamd.shiksha.utils.Course;

import java.util.List;

public class FacultyCourseAdapter extends RecyclerView.Adapter<FacultyCourseAdapter.FacultyViewHolder> {

    private final List<Course> courseList;
    private Context context;

    public FacultyCourseAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public FacultyCourseAdapter.FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.faculty_course_item, parent, false);

        return new FacultyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyCourseAdapter.FacultyViewHolder holder, int position) {
        Course course = courseList.get(position);

        holder.course_item_title.setText(String.format("%s - %s", course.getCourseId(), course.getName()));
        holder.post_marks_expand.setOnClickListener(view -> {
            Intent postMarksIntent = new Intent(context, PostMarksActivity.class);
            postMarksIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            postMarksIntent.putExtra("course_id", course.getCourseId());
            context.startActivity(postMarksIntent);
        });

        holder.post_attendance_expand.setOnClickListener(view -> {
            Intent postAttendanceIntent = new Intent(context, PostAttendanceActivity.class);
            postAttendanceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            postAttendanceIntent.putExtra("course_id", course.getCourseId());
            context.startActivity(postAttendanceIntent);
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    protected static class FacultyViewHolder extends RecyclerView.ViewHolder {
        LinearLayoutCompat faculty_course_item_collapse, post_marks_expand, post_attendance_expand;
        ImageButton toggle_item;
        TextView course_item_title;

        public FacultyViewHolder(@NonNull View itemView) {
            super(itemView);

            faculty_course_item_collapse = itemView.findViewById(R.id.faculty_course_item_collapse);
            post_marks_expand = itemView.findViewById(R.id.post_marks_expand);
            post_attendance_expand = itemView.findViewById(R.id.post_attendance_expand);
            toggle_item = itemView.findViewById(R.id.toggle_item);

            if (faculty_course_item_collapse.getVisibility() == View.GONE) {
                faculty_course_item_collapse.setVisibility(View.VISIBLE);
                toggle_item.setImageResource(R.drawable.ic_close);
            } else {
                faculty_course_item_collapse.setVisibility(View.GONE);
                toggle_item.setImageResource(R.drawable.ic_open);
            }

            toggle_item.setOnClickListener(view -> {
                if (faculty_course_item_collapse.getVisibility() == View.GONE) {
                    faculty_course_item_collapse.setVisibility(View.VISIBLE);
                    toggle_item.setImageResource(R.drawable.ic_close);
                } else {
                    faculty_course_item_collapse.setVisibility(View.GONE);
                    toggle_item.setImageResource(R.drawable.ic_open);
                }
            });

            course_item_title = itemView.findViewById(R.id.course_item_title);
        }
    }
}
