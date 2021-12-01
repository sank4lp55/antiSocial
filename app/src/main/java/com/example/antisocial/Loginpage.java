package com.example.antisocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Loginpage extends AppCompatActivity {
    EditText editTextEmaill;
    EditText editTextPasswordl;
    private FirebaseAuth mAuth;


    public void logininput()
    {
        editTextEmaill=(EditText)findViewById(R.id.emaillogin);
        editTextPasswordl=(EditText)findViewById(R.id.passwordlogin);
    }


    public void goregister(View view)
    {
        Intent intent=new Intent(Loginpage.this,MainActivity.class);
        startActivity(intent);
    }

    String emails;
    String passwords;

    public void logintodash(View view)
    {
        logininput();
        emails=editTextEmaill.getText().toString().trim();
        passwords=editTextPasswordl.getText().toString().trim();
        if(emails.isEmpty())
        {
            editTextEmaill.setError("Email is empty");
            editTextEmaill.requestFocus();
            return;
        }


        if(passwords.isEmpty())
        {
            editTextPasswordl.setError("Password is required");
            editTextPasswordl.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(editTextEmaill.getText().toString(),editTextPasswordl.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent i = new Intent(Loginpage.this, Dashboard.class);
// set the new task and clear flags
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
    }
}