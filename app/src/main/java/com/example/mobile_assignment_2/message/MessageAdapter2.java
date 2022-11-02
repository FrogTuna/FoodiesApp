package com.example.mobile_assignment_2.message;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.R;
import com.example.mobile_assignment_2.chat.FriendListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    //fields
    Context context;
    public ArrayList<ChatMessage> allConversation;
    private static final int LAYOUT_LEFT = 0;
    private static final int LAYOUT_RIGHT = 1;
    FirebaseUser fuser;
    FirebaseAuth firebaseAuth;



    //constructor
    public MessageAdapter2(Context context, ArrayList<ChatMessage> conversationRight){
        this.context = context;
        this.allConversation = conversationRight;
        firebaseAuth = FirebaseAuth.getInstance();
        fuser = firebaseAuth.getCurrentUser();
    }




    //verify which viewholder should be returned
    @Override
    public int getItemViewType(int position) {
        if (allConversation.get(position).getRole().equals(FriendListAdapter.userID)) {
            return LAYOUT_LEFT;
        } else if(allConversation.get(position).getRole().equals(fuser.getUid())) {
            return LAYOUT_RIGHT;
        }
        return 0;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //This is where you inflate the layout (Giving a loop to our view)
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


        //assigning values to the views we created in the recycler_view_row layout file
        //based on the position of the recycler views

        if(allConversation.get(position).getRole().equals(FriendListAdapter.userID)){
            ViewHolderLeft holderLeft = (ViewHolderLeft) holder;
            holderLeft.receiverInfo.setText(allConversation.get(position).getSenderText());
            holderLeft.receiverTime.setText(allConversation.get(position).getSenderTime());
            Picasso.with(context).load(allConversation.get(position).getSenderImage()).into(holderLeft.receiverImage);


        }
        else if (allConversation.get(position).getRole().equals(fuser.getUid())){
            ViewHolderRight holderRight = (ViewHolderRight) holder;
            holderRight.senderInfo.setText(allConversation.get(position).getSenderText());
            holderRight.senderTime.setText(allConversation.get(position).getSenderTime());
            Picasso.with(context).load(fuser.getPhotoUrl().toString()).into(holderRight.senderImage);

        }

    }




    //the recycler view just wants to know the number of items you want displayed
    @Override
    public int getItemCount() {
        return allConversation.size();
    }




    //****************  VIEW HOLDER 1 ******************//

    public class ViewHolderLeft extends RecyclerView.ViewHolder {

        //grabbing the views from our recycler_view_row layout file
        //kind like in the onCreate method

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

        //grabbing the views from our recycler_view_row layout file
        //kind like in the onCreate method

        CircleImageView senderImage;
        TextView senderInfo, senderTime;

        public ViewHolderRight(@NonNull View itemView) {
            super(itemView);

            senderImage = itemView.findViewById(R.id.chatWindowRightUserImage);
            senderInfo = itemView.findViewById(R.id.chatMessageRightSendText);
            senderTime = itemView.findViewById(R.id.chatMessageRightDateAndTime);

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