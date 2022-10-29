package com.example.mobile_assignment_2.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.R;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    private ChatListData[] chatListData;

    public ChatListAdapter(ChatListData[] _chatListData) {
        this.chatListData = _chatListData;
    }

    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View chatListItem= layoutInflater.inflate(R.layout.chat_list_item, parent, false);
        ChatListAdapter.ViewHolder viewHolder = new ChatListAdapter.ViewHolder(chatListItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatListAdapter.ViewHolder holder, int position) {
        final ChatListData chatListItem = chatListData[position];
        holder.textViewUsername.setText(chatListData[position].getUsername());
        holder.textViewLastMsg.setText(chatListData[position].getLastMsg());
        holder.imageViewAvatar.setImageResource(chatListData[position].getAvatar());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to user page
                Toast.makeText(view.getContext(),"click on item: "+chatListItem.getUsername(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return chatListData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewAvatar;
        public TextView textViewUsername, textViewLastMsg;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageViewAvatar = (ImageView) itemView.findViewById(R.id.imageViewAvatar);
            this.textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
            this.textViewLastMsg = (TextView) itemView.findViewById(R.id.textViewLastMsg);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}

