package com.aziz.tariq.classroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class JoinClassroomActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    String courseCodeText;
    String classroomPasswordText;
    String username;
    final int CREATE_CLASSROOM_REQUEST = 1;
    List<String> availableClassrooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_classroom);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent receivedIntent = getIntent();
        username = receivedIntent.getStringExtra("username");
        //TODO: implement with a hashtable
        availableClassrooms = new ArrayList<String>();

        mDatabase.child("classrooms").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Classroom classroom = dataSnapshot.getValue(Classroom.class);
                availableClassrooms.add(classroom.getCourseCode());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void createClassroom(View v){
        Intent i = new Intent(JoinClassroomActivity.this, CreateClassroomActivity.class);
        startActivityForResult(i, CREATE_CLASSROOM_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CREATE_CLASSROOM_REQUEST){
            finish();
        }
    }

    public void joinClassroom(View v){

        EditText courseCodeEditText = (EditText)findViewById(R.id.course_code_edit_text);
        EditText classroomPasswordEditText = (EditText)findViewById(R.id.classroom_password_edit_text);

        courseCodeText = courseCodeEditText.getText().toString();
        classroomPasswordText = classroomPasswordEditText.getText().toString();

        if(courseCodeText.equals("") || classroomPasswordText.equals("")){
            Toast.makeText(JoinClassroomActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        }
        else{
            //first check if the classroom with courseCodeText exists!
            if(availableClassrooms.contains(courseCodeText)) {
                //add the class to the user's list of classes
                mDatabase.child("users").child(username).child("classrooms").child(courseCodeText).setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });
            }
            else{
                Toast.makeText(JoinClassroomActivity.this, "Course not available!", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
