package com.example.antisocial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;

public class profiledashboard extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;


    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;

    TextView userNamePP;
    TextView userEmailPP;
    private Bitmap bitmap;
    private Bitmap bitmap2;


    public void signout(View view)
    {
        Intent intent=new Intent(profiledashboard.this,Loginpage.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiledashboard);
        imageView=findViewById(R.id.cover);
        imageView2=findViewById(R.id.changeCover);
        imageView3=findViewById(R.id.profilePhoto);
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        userNamePP=findViewById(R.id.userNameP);
        userEmailPP=findViewById(R.id.userEmailP);


        database.getReference().child("users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    UserModel user =snapshot.getValue(UserModel.class);
                    Picasso.get().load(user.getCoverPhoto()).placeholder(R.drawable.empty).into(imageView);
                    Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.empty).into(imageView3);

                    userEmailPP.setText(user.getEmail());
                    userNamePP.setText(user.getName());


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,11);



            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,22);



            }
        });

    }
    public void goback(View view)
    {
        Intent intent=new Intent(profiledashboard.this,Dashboard.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11&&resultCode==RESULT_OK)
        {
            Uri uri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
            final StorageReference reference = storage.getReference().child("coverPhoto").child(FirebaseAuth.getInstance().getUid());
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getBaseContext(), "cover photo updated", Toast.LENGTH_LONG).show();


                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("users").child(auth.getUid()).child("coverPhoto").setValue(uri.toString());

                        }
                    });

                }
            });


        }
        else
        {
            Uri uri=data.getData();
            try {
                bitmap2=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView3.setImageBitmap(bitmap2);
            final StorageReference reference = storage.getReference().child("profilePhoto").child(FirebaseAuth.getInstance().getUid());
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getBaseContext(), "profile photo updated", Toast.LENGTH_LONG).show();


                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("users").child(auth.getUid()).child("profilePhoto").setValue(uri.toString());

                        }
                    });

                }
            });

        }
    }
}