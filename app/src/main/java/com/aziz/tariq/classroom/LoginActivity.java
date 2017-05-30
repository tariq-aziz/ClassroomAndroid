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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onResume(){
        super.onResume();
        EditText emailEditText = (EditText) findViewById(R.id.email_edit_text);
        EditText passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        // Clear all value here
        passwordEditText.setText("");
    }

    public void signIn(View v) {
        EditText emailEditText = (EditText) findViewById(R.id.email_edit_text);
        final EditText passwordEditText = (EditText) findViewById(R.id.password_edit_text);

        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        if(email.equals("") || password.equals("")){
            Toast.makeText(LoginActivity.this, "Fill both fields!", Toast.LENGTH_SHORT).show();
        }
        else {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                //Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, "Sign in failed",
                                        Toast.LENGTH_SHORT).show();
                                passwordEditText.setText("");
                            } else {
                                setDisplayName();
                            }
                            // ...
                        }
                    });
        }
    }

    public void setDisplayName(){
        int atIndex = email.indexOf('@');
        String displayName = email.substring(0, atIndex);
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("username", displayName);
        startActivity(i);
    }


    public void signUp(View v){
        Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
    }
}
