package com.example.mobile_assignment_2.me;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_assignment_2.R;

import java.util.LinkedList;

public class PostAdapter extends BaseAdapter {

    private LinkedList<Posts> postsList;
    private Context context;

    public PostAdapter(LinkedList<Posts> postsList, Context context){
        this.postsList = postsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return postsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.me_postlists,parent,false);
//        ImageView img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
        TextView authorText = (TextView) convertView.findViewById(R.id.MyPostsList);
//        TextView titleText = (TextView) convertView.findViewById(R.id.title_yourPosts);
//        img_icon.setBackgroundResource(postsList.get(position).getaIcon());
        authorText.setText(postsList.get(position).getAuthor());
//        titleText.setText(postsList.get(position).getTitle());
        return convertView;
    }
}
