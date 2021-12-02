package com.example.antisocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://antisocial-ef3fb-default-rtdb.firebaseio.com/");

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextPhone;
    String names;
    String emails;
    String passwords;
    String phones;
    int f=0;


    public void registerinput()
    {

          editTextName=(EditText)findViewById(R.id.name);
       editTextEmail=(EditText)findViewById(R.id.email);
       editTextPassword=(EditText)findViewById(R.id.password);
       editTextPhone=(EditText)findViewById(R.id.phone);

        names=editTextName.getText().toString().trim();
        emails=editTextEmail.getText().toString().trim();
        passwords=editTextPassword.getText().toString().trim();
        phones=editTextPhone.getText().toString().trim();

    }




    public void loginme(View view)
    {
        Intent intent=new Intent(this,Loginpage.class);
        startActivity(intent);
    }

    public void registerme(View view)
    {
        registerinput();

        if(names.isEmpty())
        {
            editTextName.setError("Name is empty");
            editTextName.requestFocus();
            return;
        }
       if(emails.isEmpty())
        {
            editTextEmail.setError("Email is empty");
            editTextEmail.requestFocus();
            return;
        }


        if(phones.isEmpty())
        {
            editTextPhone.setError("Phone no. is required.");
            editTextPhone.requestFocus();
            return;
        }
        int l=phones.length();
        int ff=0;
        for (int i=0;i<l;i++)
        {
            char ch=phones.charAt(i);
            if(ch<48||ch>57)
            {
                ff=1;
                break;
            }
        }
        if(ff==1||l<10||l>10)
        {
            editTextPhone.setError("Phone no. is invalid");
            editTextPhone.requestFocus();
            return;
        }


        if(passwords.isEmpty())
        {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(passwords.length()<8)
        {
            editTextPassword.setError("Minimum length is 8");
            editTextPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emails).matches())  // checks if email is valid
        {
            editTextEmail.setError("Invalid email");
            editTextEmail.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(emails,passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    String id=task.getResult().getUser().getUid();

                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {}
                    else {

                        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(id))
                                {
                                    Toast.makeText(MainActivity.this, "user already created .", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    UserModel user=new UserModel(names,phones,passwords,emails);
                                    databaseReference.child("users").child(id).child("name").setValue(names);
                                    databaseReference.child("users").child(id).child("email").setValue(emails);
                                    databaseReference.child("users").child(id).child("password").setValue(passwords);
                                    databaseReference.child("users").child(id).child("phoneno").setValue(phones);
                                    f=1;
                                    usercreated();
                                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void usercreated()
    {
        if(f==1)
        Toast.makeText(this, "User is created", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}