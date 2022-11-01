package com.example.mobile_assignment_2.add.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.MainActivity;
import com.example.mobile_assignment_2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.io.InputStream;

public class addFriendListAdapter extends RecyclerView.Adapter<addFriendListAdapter.ViewHolder> {
    public addFriendListData[] friendListData;
    public static String username;
//    public static String userID;
    public static String imageUrl;
    private DatabaseReference mDatabase;
    private String currentUID,flag;
    private boolean runOnce;
    private boolean hasFriends;


    public addFriendListAdapter(addFriendListData[] _friendListData, String currentUID, String flag) {
        this.friendListData = _friendListData;
        this.currentUID = currentUID;
        this.flag =flag;
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }
    public addFriendListData[] getAddFriendListData(){
        return friendListData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View friendListItem= layoutInflater.inflate(R.layout.add_friendslist_item, parent, false);
        addFriendListAdapter.ViewHolder viewHolder = new addFriendListAdapter.ViewHolder(friendListItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(addFriendListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final addFriendListData friendListItem = friendListData[position];

        holder.textViewUsername.setText(friendListData[position].getUsername());

//        holder.textViewRemark.setText(friendListData[position].getRemark());
        new addFriendListAdapter.DownloadImageFromInternet((ImageView) holder.imageViewAvatar).execute(friendListData[position].getImgURL());

//        holder.imageViewAvatar.setImageResource(friendListData[position].getImgURL());
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Redirect to user page

//
//            }
//        });


        holder.addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to user page
                //Toast.makeText(view.getContext(),"click on item: "+friendListItem.getUsername(),Toast.LENGTH_LONG).show();
                //Ke YANG *********************************
                Query databaseReference = mDatabase.child("Users");
                runOnce  =true;
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(runOnce) {
                            runOnce = false;

                            FirebaseAuth myAuth = FirebaseAuth.getInstance();
                            FirebaseUser firebaseUser = myAuth.getCurrentUser();

                            if (flag.equals("add")) {
                                if (snapshot.child(currentUID).child("requests").hasChild(friendListItem.getUID())) {
                                    Log.d("debug: ", "************* You are already friends");
                                    Toast.makeText(view.getContext(), "You are already friends", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("buttonClick: ", friendListItem.getUID() + " " + currentUID);
                                    mDatabase.child("Users").child(friendListItem.getUID()).child("requests").child(currentUID).child("name").setValue(firebaseUser.getDisplayName());
                                    mDatabase.child("Users").child(friendListItem.getUID()).child("requests").child(currentUID).child("imageUrl").setValue(firebaseUser.getPhotoUrl().toString());
                                    mDatabase.child("Users").child(friendListItem.getUID()).child("requests").child(currentUID).child("userID").setValue(currentUID);
                                    Toast.makeText(view.getContext(), "send add request successful!", Toast.LENGTH_SHORT).show();
                                }
                                holder.addFriendBtn.setVisibility(View.INVISIBLE);
                            }

                            if (flag.equals("shake")) {
                                FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("friends").child(friendListItem.getUID()).setValue(friendListItem.getUID());
                                FirebaseDatabase.getInstance().getReference("Users").child(friendListItem.getUID()).child("friends").child(firebaseUser.getUid()).setValue(firebaseUser.getUid());
                                Toast.makeText(view.getContext(), "add successful!", Toast.LENGTH_SHORT).show();
                                holder.addFriendBtn.setVisibility(View.INVISIBLE);
                            }
                        }
//

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //Ke YANG ***********************************

//                Log.d("buttonClick: ", friendListItem.getUID() + " " + currentUID);
//                FirebaseAuth myAuth = FirebaseAuth.getInstance();
//                FirebaseUser firebaseUser = myAuth.getCurrentUser();
//                mDatabase.child("Users").child(friendListItem.getUID()).child("requests").child(currentUID).child("name").setValue(firebaseUser.getDisplayName());
//                mDatabase.child("Users").child(friendListItem.getUID()).child("requests").child(currentUID).child("imageUrl").setValue(firebaseUser.getPhotoUrl().toString());
//                mDatabase.child("Users").child(friendListItem.getUID()).child("requests").child(currentUID).child("userID").setValue(currentUID);
                //mDatabase.child("Users").child(friendListItem.getUID()).child("requests").child("comment").setValue(friendListItem.ge);
//                Context context = view.getContext();
//                Intent intent = new Intent(context, MainActivity.class);
//                context.startActivity(intent);
                //mDatabase.child("Users").child(currentUID).child("request").child(friendListItem.getUID()).setValue(currentUID);
            }
        });

    }


    @Override
    public int getItemCount() {
        return friendListData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewAvatar;
        public TextView textViewUsername, textViewRemark;
        public Button addFriendBtn;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageViewAvatar = (ImageView) itemView.findViewById(R.id.imageViewAvatar);
            this.textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
            this.addFriendBtn = (Button) itemView.findViewById(R.id.btnFriendDelete);
//            this.textViewRemark = (TextView) itemView.findViewById(R.id.textViewRemark);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
            Toast.makeText(imageView.getContext(), "Please wait, it may take a few minute...",Toast.LENGTH_SHORT).show();
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

}
