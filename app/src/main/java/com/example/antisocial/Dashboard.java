package com.example.antisocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth auth;
 ArrayList<postModel> postlist=new ArrayList<>();

    public void myprofile(View view)
    {
        Intent intent=new Intent(Dashboard.this,profiledashboard.class);
        startActivity(intent);
    }


    public void gouserlist(View view)
    {
        Intent intent=new Intent(Dashboard.this,showuserlist.class);
        startActivity(intent);
    }
    public void gotoaddpost(View view)
    {
        Intent intent=new Intent(Dashboard.this,addPost.class);
        startActivity(intent);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
      postAdapter adapter=new postAdapter(getBaseContext(),postlist);
        ImageView profiletooll=(ImageView)findViewById(R.id.profiletool);



        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.postRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

     database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {

             for(DataSnapshot dataSnapshot:snapshot.getChildren())
             {
                 postModel post=dataSnapshot.getValue(postModel.class);
                 postlist.add(post);


             }
             adapter.notifyDataSetChanged();
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });
        database.getReference().child("users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    UserModel user =snapshot.getValue(UserModel.class);

                    Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.empty).into(profiletooll);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}