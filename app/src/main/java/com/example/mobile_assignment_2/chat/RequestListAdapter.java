package com.example.mobile_assignment_2.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import com.example.mobile_assignment_2.MainActivity;
import com.example.mobile_assignment_2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * @author:
 * @date: 2022/11/2 22:13
 * @description:
 */
public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ViewHolder> {
    private RequestListData[] requestListData;

    public RequestListAdapter(RequestListData[] _requestListData) {
        this.requestListData = _requestListData;
    }

    @Override
    public RequestListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View requestListItem = layoutInflater.inflate(R.layout.request_list_item, parent, false);
        RequestListAdapter.ViewHolder viewHolder = new RequestListAdapter.ViewHolder(requestListItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RequestListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final RequestListData requestListItem = requestListData[position];
        holder.textViewUsername.setText(requestListData[position].getUsername());
        Picasso.with(holder.itemView.getContext()).load(requestListData[position].getAvatar()).into(holder.imageViewAvatar);
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("buttonClick: ", requestListData[position].getUserID());
                FirebaseAuth myAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = myAuth.getCurrentUser();
                FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("requests").child(requestListData[position].getUserID()).removeValue();
                FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("friends").child(requestListData[position].getUserID()).setValue(requestListData[position].getUserID());
                FirebaseDatabase.getInstance().getReference("Users").child(requestListData[position].getUserID()).child("friends").child(firebaseUser.getUid()).setValue(firebaseUser.getUid());
                Context context = view.getContext();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to user page
                Toast.makeText(view.getContext(), "click on item: " + requestListItem.getUsername(), Toast.LENGTH_LONG).show();
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
            this.addBtn = itemView.findViewById(R.id.btnFriendDelete);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}
