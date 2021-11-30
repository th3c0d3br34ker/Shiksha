package com.jainamd.shiksha.faculty.marks;

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

public class PostMarksAdapter extends RecyclerView.Adapter<PostMarksAdapter.MarksViewHolder> {

    private final Context context;
    private final List<Student> usersList;

    public PostMarksAdapter(Context context, List<Student> usersList) {
        this.context = context;
        this.usersList = usersList;
    }


    @NonNull
    @Override
    public PostMarksAdapter.MarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.post_marks_item, parent, false);

        return new MarksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostMarksAdapter.MarksViewHolder holder, int position) {
        Student student = usersList.get(position);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");


        holder.account_id.setText(student.getAccountId());
        holder.marks_input.setText(student.getMarks());
        holder.post_marks_btn.setOnClickListener(
                view -> {
                    Toast.makeText(context, "Marks Updated", Toast.LENGTH_LONG).show();
                    Integer newMark = Integer.valueOf(holder.marks_input.getText().toString());
                    myRef
                            .child(student.getUuid())
                            .child("courses")
                            .child(student.getCourseCode())
                            .child("marks")
                            .setValue(newMark);
                }
        );
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class MarksViewHolder extends RecyclerView.ViewHolder {

        TextView account_id;
        TextInputEditText marks_input;
        ImageButton post_marks_btn;

        public MarksViewHolder(@NonNull View itemView) {
            super(itemView);

            account_id = itemView.findViewById(R.id.account_id);
            marks_input = itemView.findViewById(R.id.marks_input);
            post_marks_btn = itemView.findViewById(R.id.post_marks_btn);
        }
    }
}
