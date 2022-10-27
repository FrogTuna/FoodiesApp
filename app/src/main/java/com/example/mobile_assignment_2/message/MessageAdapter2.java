package com.example.mobile_assignment_2.message;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    public ArrayList<ChatMessage> allConversation;
    private static final int LAYOUT_LEFT = 0;
    private static final int LAYOUT_RIGHT = 1;
    FirebaseUser fuser;
    FirebaseAuth firebaseAuth;


    public MessageAdapter2(Context context, ArrayList<ChatMessage> conversationRight){
        this.context = context;
        this.allConversation = conversationRight;
        firebaseAuth = FirebaseAuth.getInstance();
        fuser = firebaseAuth.getCurrentUser();
    }




    @Override
    public int getItemViewType(int position) {
        if (allConversation.get(position).getRole().equals("HFpfPrZESjfWBQGS7A8C5EO83e53")) {
            return LAYOUT_LEFT;
        } else if(allConversation.get(position).getRole().equals(fuser.getUid())) {
            return LAYOUT_RIGHT;
        }

        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;


        if(viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_window_left,parent,false);
            viewHolder = new ViewHolderLeft(view);
        }
        else if (viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_window_right,parent,false);
            viewHolder= new ViewHolderRight(view);
        }

        return viewHolder;

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(allConversation.get(position).getRole().equals("HFpfPrZESjfWBQGS7A8C5EO83e53")){
            ViewHolderLeft holderLeft = (ViewHolderLeft) holder;
            holderLeft.receiverInfo.setText(allConversation.get(position).getSenderText());
            holderLeft.receiverTime.setText(allConversation.get(position).getSenderTime());
            holderLeft.receiverImage.setImageResource(allConversation.get(position).getSenderImage());
        }
        else if (allConversation.get(position).getRole().equals(fuser.getUid())){
            ViewHolderRight holderRight = (ViewHolderRight) holder;
            holderRight.senderInfo.setText(allConversation.get(position).getSenderText());
            holderRight.senderTime.setText(allConversation.get(position).getSenderTime());
            holderRight.senderImage.setImageResource(allConversation.get(position).getSenderImage());
        }

    }

    @Override
    public int getItemCount() {
        return allConversation.size();
    }

    //****************  VIEW HOLDER 1 ******************//

    public class ViewHolderLeft extends RecyclerView.ViewHolder {

        CircleImageView receiverImage;
        TextView receiverInfo, receiverTime;

        public ViewHolderLeft(@NonNull View itemView) {

            super(itemView);

            receiverImage = itemView.findViewById(R.id.chatWindowLeftUserImage);
            receiverInfo = itemView.findViewById(R.id.chatMessageLeftReceivedText);
            receiverTime = itemView.findViewById(R.id.chatMessageLeftDateAndTime);

        }
    }


    //****************  VIEW HOLDER 2 ******************//

    public class ViewHolderRight extends RecyclerView.ViewHolder {

        CircleImageView senderImage;
        TextView senderInfo, senderTime;

        public ViewHolderRight(@NonNull View itemView) {
            super(itemView);

            senderImage = itemView.findViewById(R.id.chatWindowRightUserImage);
            senderInfo = itemView.findViewById(R.id.chatMessageRightSendText);
            senderTime = itemView.findViewById(R.id.chatMessageRightDateAndTime);

        }
    }
}