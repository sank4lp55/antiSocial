package com.example.antisocial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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

import java.util.Date;


public class addPost extends AppCompatActivity {

ImageView postPhotu;
    Button postButton;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    TextView userNamee;
    ImageView postPhoto;
    TextView description;


    public void bringphoto(View view)
    {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,11);


    }
    public void postgo(View view)
    {
        final StorageReference reference=storage.getReference().child("posts")
                .child(new Date().getTime()+" ");
        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        postModel post=new postModel();
                        post.setPostImage(uri.toString());
                        post.setPostedBy(FirebaseAuth.getInstance().getUid());
                        post.setDes(description.getText().toString());
                        post.setPostedAt(new Date().getTime());

                        database.getReference().child("posts")
                                .push()
                                .setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getBaseContext(), "Posted successfully", Toast.LENGTH_LONG).show();

                                Intent intent=new Intent(addPost.this,Dashboard.class);
                                startActivity(intent);

                            }
                        });

                    }
                });
            }
        });


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        auth= FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();


        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    Picasso.get()
                            .load(userModel.getProfilePhoto())
                            .placeholder(R.drawable.empty)
                            .into(postPhoto);
                    userNamee.setText(userModel.getName());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        description = (TextView) findViewById(R.id.postDescription);
       postButton = (Button) findViewById(R.id.button);
        postPhoto=(ImageView) findViewById(R.id.picpic);
      postPhotu=(ImageView) findViewById(R.id.imageView4);
      userNamee=(TextView) findViewById(R.id.userName);
     description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String descriptionText=description.getText().toString();
                if(descriptionText.isEmpty())
                {

                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String descriptionText=description.getText().toString();
            if(!descriptionText.isEmpty())
            {

            }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String descriptionText=description.getText().toString();
                if(descriptionText.isEmpty()&&f!=1)
                {

                }
            }
        });

    }
    int f=0;

    Uri uri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null)
        {
            f=1;
            uri=data.getData();
            postPhotu.setImageURI(uri);
            postPhotu.setVisibility(View.VISIBLE);
            postButton.setEnabled(true);
        }
    }
}