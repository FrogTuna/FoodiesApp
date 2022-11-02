package com.example.mobile_assignment_2.add.activities.addBySearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.mobile_assignment_2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author:
 * @date: 2022/11/2 22:13
 * @description:
 */
public class ListViewAdapter extends BaseAdapter implements Filterable {

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


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            friendItemsList.clear();
            if (constraint == null || constraint.length() == 0) {
                friendItemsList.addAll(arraylist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (friendItems item : arraylist) {
                    if (item.getFriendItem().toLowerCase().contains(filterPattern)) {
                        friendItemsList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = friendItemsList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arraylist.clear();
            arraylist.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


}
