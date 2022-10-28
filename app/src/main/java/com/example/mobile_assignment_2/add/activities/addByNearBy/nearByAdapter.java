package com.example.mobile_assignment_2.add.activities.addByNearBy;

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

public class nearByAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;

    public nearByAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return view;
    }

}
