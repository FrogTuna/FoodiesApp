//package com.example.mobile_assignment_2.chat;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.mobile_assignment_2.R;
//
//public class ChatListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
//    private FriendListData[] friendListData;
//
//    public ChatListAdapter(FriendListData[] _friendListData) {
//        this.friendListData = _friendListData;
//    }
//
//    @Override
//    public FriendListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View friendListItem= layoutInflater.inflate(R.layout.friend_list_item, parent, false);
//        FriendListAdapter.ViewHolder viewHolder = new FriendListAdapter.ViewHolder(friendListItem);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(FriendListAdapter.ViewHolder holder, int position) {
//        final FriendListData friendListItem = friendListData[position];
//        holder.textViewUsername.setText(friendListData[position].getUsername());
//        holder.textViewRemark.setText(friendListData[position].getRemark());
//        holder.imageViewAvatar.setImageResource(friendListData[position].getAvatar());
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Redirect to user page
//                Toast.makeText(view.getContext(),"click on item: "+friendListItem.getUsername(),Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return friendListData.length;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView imageViewAvatar;
//        public TextView textViewUsername, textViewRemark;
//        public RelativeLayout relativeLayout;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            this.imageViewAvatar = (ImageView) itemView.findViewById(R.id.imageViewAvatar);
//            this.textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
//            this.textViewRemark = (TextView) itemView.findViewById(R.id.textViewRemark);
//            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
//        }
//    }
//}
//
