package com.example.mobile_assignment_2.chat;

import androidx.recyclerview.widget.RecyclerView;

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

import com.example.mobile_assignment_2.MainActivity;
import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.message.ChatWindowActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FieldValue;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Yao-Wen Chang
 * @date: 2022/11/2 22:13
 * @description: The adapter connect FriendListData and the FriendFragment. This defines the function
 * of each view of the interface.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    public FriendListData[] friendListData;
    public static String username;
    public static String userID;
    public static String imageUrl;
    private boolean runOnce1, runOnce2;


    public FriendListAdapter(FriendListData[] _friendListData) {
        this.friendListData = _friendListData;
    }

    public FriendListData[] getFriendListData() {
        return friendListData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View friendListItem = layoutInflater.inflate(R.layout.friend_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(friendListItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final FriendListData friendListItem = friendListData[position];

        holder.textViewUsername.setText(friendListData[position].getUsername());
        new DownloadImageFromInternet((ImageView) holder.imageViewAvatar).execute(friendListData[position].getImgURL());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = "";
                userID = "";
                imageUrl = "";
                // Redirect to user page
                Toast.makeText(view.getContext(), "click on item: " + friendListItem.getUsername(), Toast.LENGTH_LONG).show();
                username = friendListItem.getUsername();
                userID = friendListData[position].getUID();
                imageUrl = friendListData[position].getImgURL();
                Context context = view.getContext();
                Intent intent = new Intent(context, ChatWindowActivity.class);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("delete button Click: ", friendListItem.getUID());
                FirebaseAuth myAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = myAuth.getCurrentUser();

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


                Map<String, Object> update1 = new HashMap<>();
                update1.put(friendListItem.getUID(), FieldValue.delete());
                Map<String, Object> update2 = new HashMap<>();
                update2.put(firebaseUser.getUid(), FieldValue.delete());

                runOnce1 = true;
                runOnce2 = true;

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                Query applesQuery = ref.child("Users").child(firebaseUser.getUid()).child("friends").orderByKey().equalTo(friendListItem.getUID());
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (runOnce1) {
                            runOnce1 = false;
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                                Log.e("TAG", "***************** deleted successful!");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("TAG", "onCancelled", databaseError.toException());
                    }
                });

                Query applesQuery2 = ref.child("Users").child(friendListItem.getUID()).child("friends").orderByKey().equalTo(firebaseUser.getUid());
                applesQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (runOnce2) {
                            runOnce2 = false;
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                                Log.e("TAG", "***************** deleted successful!");
                            }
                        }

                        if (!runOnce1 && !runOnce2) {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("TAG", "onCancelled", databaseError.toException());
                    }
                });

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
        public RelativeLayout relativeLayout;
        public Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageViewAvatar = (ImageView) itemView.findViewById(R.id.imageViewAvatar);
            this.textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
            this.delete = itemView.findViewById(R.id.btnFriendDelete);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(imageView.getContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);
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