package com.example.mobile_assignment_2.chat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.R;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ViewHolder>{
    private RequestListData[] requestListData;

    public RequestListAdapter(RequestListData[] _requestListData) {
        this.requestListData = _requestListData;
    }

    @Override
    public RequestListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View requestListItem= layoutInflater.inflate(R.layout.request_list_item, parent, false);
        RequestListAdapter.ViewHolder viewHolder = new RequestListAdapter.ViewHolder(requestListItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RequestListAdapter.ViewHolder holder, int position) {
        final RequestListData requestListItem = requestListData[position];
        holder.textViewUsername.setText(requestListData[position].getUsername());
//        holder.textViewComment.setText(requestListData[position].getComment());
        holder.imageViewAvatar.setImageResource(requestListData[position].getAvatar());
//        holder.addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("buttonClick: ", requestListData[position].getID() + " " + user);
//                mDatabase.child("Users").child(friendListItem.getUID()).child("friends").child(currentUID).setValue("true");
//                mDatabase.child("Users").child(currentUID).child("friends").child(friendListItem.getUID()).setValue("true");
//            }
//        });


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to user page
                Toast.makeText(view.getContext(),"click on item: "+requestListItem.getUsername(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return requestListData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewAvatar;
        public TextView textViewUsername, textViewComment;
        public Button addBtn;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageViewAvatar = (ImageView) itemView.findViewById(R.id.imageViewAvatar);
            this.textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
//            this.textViewComment = (TextView) itemView.findViewById(R.id.textViewComment);
            this.addBtn = itemView.findViewById(R.id.btnFriendAdd);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}
