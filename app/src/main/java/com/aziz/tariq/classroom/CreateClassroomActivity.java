package com.aziz.tariq.classroom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateClassroomActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    String courseCodeText;
    String profNameText;
    String classroomPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_classroom);

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void createClassroom(View v){
        EditText courseCodeTextView = (EditText)findViewById(R.id.course_code_edit_text);
        EditText profNameEditText = (EditText)findViewById(R.id.prof_name_edit_text);
        EditText classroomPasswordEditText = (EditText)findViewById(R.id.classroom_password_edit_text);

        courseCodeText = courseCodeTextView.getText().toString();
        profNameText = profNameEditText.getText().toString();
        classroomPasswordText = classroomPasswordEditText.getText().toString();

        if(courseCodeText.equals("") || profNameText.equals("") || classroomPasswordText.equals("")){
            Toast.makeText(CreateClassroomActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        }
        else{
            Classroom classroom = new Classroom(courseCodeText, profNameText, classroomPasswordText);
            mDatabase.child("classrooms").child(courseCodeText).setValue(classroom).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    finish();
                }
            });
        }
    }


}
