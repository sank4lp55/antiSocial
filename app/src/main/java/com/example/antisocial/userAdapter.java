package com.example.antisocial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userViewHolder> {
    @NonNull
            Context context;
    ArrayList<UserModel> list;
    public userAdapter(Context context , ArrayList<UserModel> list)
    {
        this.context=context;
        this.list=list;

    }

    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.userlayout,parent,false);


        return new userAdapter.userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter.userViewHolder holder, int position) {

    UserModel userModel= list.get(position);
        Picasso.get()
                .load(userModel.getProfilePhoto())
                .placeholder(R.drawable.empty)
                .into(holder.profile);
        holder.name.setText(userModel.getName());
        holder.email.setText(userModel.getEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class userViewHolder extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView name,email;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.picpic);
            email=itemView.findViewById(R.id.textView);
            name = itemView.findViewById(R.id.userName);
        }
    }
}
