package com.example.antisocial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class postAdapter extends RecyclerView.Adapter<postAdapter.postViewHolder> {


    Context context;
    ArrayList<postModel> postlist;
    public postAdapter(Context context , ArrayList<postModel> postlist)
    {
        this.context=context;
        this.postlist=postlist;
    }

    @NonNull
    @Override
    public postViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.postlayout,parent,false);


        return new postAdapter.postViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull postAdapter.postViewHolder holder, int position) {

        postModel postMOdel= postlist.get(position);
        Picasso.get()
                .load(postMOdel.getPostImage())
                .placeholder(R.drawable.empty)
                .into(holder.postImagee);
                 holder.descr.setText(postMOdel.getDes());

        FirebaseDatabase.getInstance().getReference().child("users")
                .child(postMOdel.getPostedBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user=snapshot.getValue(UserModel.class);
                Picasso.get()
                        .load(user.getProfilePhoto())
                        .placeholder(R.drawable.empty)
                        .into(holder.picpostt);
               holder.nameofpost.setText(user.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    @Override
    public int getItemCount() {
        return postlist.size();
    }


    public class postViewHolder extends RecyclerView.ViewHolder {

        ImageView postImagee,picpostt;
        TextView descr,postedbyy,postedatt,nameofpost;

        public postViewHolder(@NonNull View itemView) {
            super(itemView);


            descr=itemView.findViewById(R.id.descre);

            nameofpost=itemView.findViewById(R.id.userName);
            picpostt=itemView.findViewById(R.id.picofpost);
            postImagee = itemView.findViewById(R.id.view5);

            postedbyy = itemView.findViewById(R.id.namePost);

        }
    }
}
