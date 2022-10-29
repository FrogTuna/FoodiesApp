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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.message.ChatWindowActivity;

import java.io.InputStream;


public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    public FriendListData[] friendListData;
    public static String username;
    public static String userID;
    public static String imageUrl;

    public FriendListAdapter(FriendListData[] _friendListData) {
        this.friendListData = _friendListData;
    }
    public FriendListData[] getFriendListData(){
        return friendListData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View friendListItem= layoutInflater.inflate(R.layout.friend_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(friendListItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final FriendListData friendListItem = friendListData[position];

        holder.textViewUsername.setText(friendListData[position].getUsername());
//        holder.textViewRemark.setText(friendListData[position].getRemark());
        new DownloadImageFromInternet((ImageView) holder.imageViewAvatar).execute(friendListData[position].getImgURL());

//        holder.imageViewAvatar.setImageResource(friendListData[position].getImgURL());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println("[+] catch : " + friendListData[position].getUID());
                username = "";
                userID = "";
                imageUrl = "";
                // Redirect to user page
                Toast.makeText(view.getContext(),"click on item: "+friendListItem.getUsername(),Toast.LENGTH_LONG).show();
                username = friendListItem.getUsername();
                userID = friendListData[position].getUID();
                imageUrl = friendListData[position].getImgURL();
                Context context = view.getContext();
                Intent intent = new Intent(context,ChatWindowActivity.class);
                context.startActivity(intent);
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
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageViewAvatar = (ImageView) itemView.findViewById(R.id.imageViewAvatar);
            this.textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
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