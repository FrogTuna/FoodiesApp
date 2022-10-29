package com.example.mobile_assignment_2.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyMessageViewHolder> {

    Context context;
    ArrayList<ChatMessage> conversation;


    public MessageAdapter(Context context, ArrayList<ChatMessage> conversation){

        this.context = context;
        this.conversation = conversation;

    }

    @NonNull
    @Override
    public MyMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout (Giving a loop to our view)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_window_right, parent, false);
        return new MessageAdapter.MyMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMessageViewHolder holder, int position) {
        //assigning values to the views we created in the recycler_view_row layout file
//      //based on the position of the recycler views

        holder.senderInfo.setText(conversation.get(position).getSenderText());
        holder.senderTime.setText(conversation.get(position).getSenderTime());
        holder.senderImage.setImageResource(conversation.get(position).getSendImage());

    }

    @Override
    public int getItemCount() {
        //the recycler view just wants to know the number of items you want displayed
        return conversation.size();
    }


    public static class MyMessageViewHolder extends RecyclerView.ViewHolder {
        //grabbing the views from our recycler_view_row layout file
        //kind like in the onCreate method

        CircleImageView senderImage;
        TextView senderInfo, senderTime;

        public MyMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderImage = itemView.findViewById(R.id.chatWindowRightUserImage);
            senderInfo = itemView.findViewById(R.id.chatMessageRightSendText);
            senderTime = itemView.findViewById(R.id.chatMessageRightDateAndTime);

        }
    }


}
