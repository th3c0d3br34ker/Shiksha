package com.jainamd.shiksha.faculty;

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

import java.text.BreakIterator;
import java.util.List;

public class FacultyCourseAdapter extends RecyclerView.Adapter<FacultyCourseAdapter.FacultyViewHolder> {

    private final List<Course> courseList;

    public FacultyCourseAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public FacultyCourseAdapter.FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faculty_course_item, parent, false);

        return new FacultyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyCourseAdapter.FacultyViewHolder holder, int position) {
        Course course = courseList.get(position);

        holder.course_item_title.setText(String.format("%s - %s", course.getCourseId(), course.getName()));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    protected static class FacultyViewHolder extends RecyclerView.ViewHolder {
        LinearLayoutCompat faculty_course_item_collapse;
        ImageButton toggle_item;
        TextView course_item_title;

        public FacultyViewHolder(@NonNull View itemView) {
            super(itemView);

            faculty_course_item_collapse =  itemView.findViewById(R.id.faculty_course_item_collapse);
            toggle_item = itemView.findViewById(R.id.toggle_item);

            if (faculty_course_item_collapse.getVisibility() == View.GONE) {
                toggle_item.setImageResource(R.drawable.ic_close);
            } else {
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
