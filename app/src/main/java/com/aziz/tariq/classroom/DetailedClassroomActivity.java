package com.aziz.tariq.classroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DetailedClassroomActivity extends AppCompatActivity {
    String courseCode;
    DatabaseReference mDatabase;
    List<Message> messagesList;
    ListView messagesListView;
    MessageAdapter messageAdapter;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_classroom);
        Intent receivedIntent = getIntent();
        courseCode = receivedIntent.getStringExtra("courseCode");
        getSupportActionBar().setTitle(courseCode);  // provide compatibility to all the versions

        username = receivedIntent.getStringExtra("username");

        messagesList = new ArrayList<Message>();
        messageAdapter = new MessageAdapter(this, messagesList);
        messagesListView = (ListView)findViewById(R.id.messages_list_view);
        messagesListView.setAdapter(messageAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("messages").child(courseCode).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                messagesList.add(message);
                messageAdapter.notifyDataSetChanged();
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

    public void sendMessage(View v){
        EditText messageEditText = (EditText)findViewById(R.id.message_edit_text);
        String messageText = messageEditText.getText().toString();

        if(!messageText.equals("")){
            Message message = new Message(messageText,username);

            String key = mDatabase.child("messages").child(courseCode).push().getKey();
            mDatabase.child("messages").child(courseCode).child(key).setValue(message);
        }
    }
}
