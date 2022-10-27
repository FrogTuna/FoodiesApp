package com.example.mobile_assignment_2.add.activities.addBySearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mobile_assignment_2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<friendItems> friendItemsList = null;
    private ArrayList<friendItems> arraylist;

    public ListViewAdapter(Context context, List<friendItems> friendItemsList) {
        mContext = context;
        this.friendItemsList = friendItemsList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<friendItems>();
        this.arraylist.addAll(friendItemsList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return friendItemsList.size();
    }

    @Override
    public friendItems getItem(int position) {
        return friendItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.addbysearch_list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(friendItemsList.get(position).getFriendItem());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        friendItemsList.clear();
        if (charText.length() == 0) {
            friendItemsList.addAll(arraylist);
        } else {
            for (friendItems wp : arraylist) {
                if (wp.getFriendItem().toLowerCase(Locale.getDefault()).contains(charText)) {
                    friendItemsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
