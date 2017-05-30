package com.aziz.tariq.classroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String emailText;
    String passwordText;
    String duplicatePasswordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in - TODO: Add functionality to bypass logon UI
                } else {
                    // User is signed out - do nothing
                }
            }
        });

    }

    public void signUp(View v){
        EditText emailEditText = (EditText)findViewById(R.id.email_edit_text);
        EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);
        EditText duplicatePasswordEditText = (EditText)findViewById(R.id.duplicate_password_edit_text);

         emailText = emailEditText.getText().toString();
         passwordText = passwordEditText.getText().toString();
         duplicatePasswordText = duplicatePasswordEditText.getText().toString();

        if(emailText.equals("") || passwordText.equals("") || duplicatePasswordText.equals("")){
            Toast.makeText(SignUpActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        }
        else if(!passwordText.equals(duplicatePasswordText)){
            Toast.makeText(SignUpActivity.this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
        }

        else {
            mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.v("AUTHENITCATION:", "success");
                                setDisplayName();
                            // ...
                        }
                    });
        }
    }

    public void setDisplayName(){
        int atIndex = emailText.indexOf('@');
        String displayName = emailText.substring(0, atIndex);

        mDatabase.child("users").child(displayName).child("name").setValue(displayName);
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        i.putExtra("username", displayName);
        startActivity(i);
    }
}
