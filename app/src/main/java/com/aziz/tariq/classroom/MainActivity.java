package com.aziz.tariq.classroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//WHATS THE SIGNIFICANCE OF EXTENDING APPCOMPATACTIVITY?
public class MainActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    final int JOIN_CLASSROOM_INTENT = 0;
    String username;
    boolean isSignedIn = false;
    ListView classroomsListView;
    List<Classroom> classroomsList;
    ClassroomAdapter classroomAdapter;
    final int CLASSROOM_DETAILED_REQUEST = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent receivedIntent = getIntent();
        username = receivedIntent.getStringExtra("username");
        if(username == null){
            //user not signed in - go to LoginActivity
            Intent notSignedInIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(notSignedInIntent);
        }
        else {
            isSignedIn=true;
            classroomsList = new ArrayList<Classroom>();

            classroomsListView = (ListView) findViewById(R.id.classrooms_list_view);
            classroomAdapter = new ClassroomAdapter(this, classroomsList);

            classroomsListView.setAdapter(classroomAdapter);

            classroomsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(MainActivity.this, DetailedClassroomActivity.class);
                    intent.putExtra("courseCode", classroomsList.get(position).getCourseCode());
                    intent.putExtra("username", username);
                    startActivityForResult(intent, CLASSROOM_DETAILED_REQUEST);
                }
            });

            mDatabase = FirebaseDatabase.getInstance().getReference();

            mDatabase.child("users").child(username).child("classrooms").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String courseCode = dataSnapshot.getKey().toString();
                    //get the corresponding Classroom object, then add that to the listView
                    Log.v("CLASS_STRING", courseCode);


                    mDatabase.child("classrooms").child(courseCode).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Classroom classroom = dataSnapshot.getValue(Classroom.class);
                            classroomsList.add(classroom);
                            classroomAdapter.notifyDataSetChanged();
                            //Log.v("CLASS_PROF", classroom.profName);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

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
    }

    @Override
    protected void onResume(){
        super.onResume();
        //user is not signed in, return to LoginActivity
        if(!isSignedIn){
            Intent notSignedInIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(notSignedInIntent);
            //once loginActivity started, prevents back button from causing activity from reopening
            finish();
        }
    }

    public void joinClassroom(View v){
        Intent joinClassroomIntent = new Intent(MainActivity.this, JoinClassroomActivity.class);
        joinClassroomIntent.putExtra("username", username);
        startActivityForResult(joinClassroomIntent, JOIN_CLASSROOM_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
