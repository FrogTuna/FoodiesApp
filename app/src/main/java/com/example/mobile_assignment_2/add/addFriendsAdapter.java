package com.example.mobile_assignment_2.add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.mobile_assignment_2.R;

import java.util.ArrayList;

public class addFriendsAdapter extends ArrayAdapter {
    public addFriendsAdapter(@NonNull Context context, ArrayList<CustomItem> customItemList) {
        super(context, 0,customItemList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_layout,parent,false);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dropdown_layout,parent,false);
        }
        CustomItem item = (CustomItem) getItem(position);
        ImageView dropDownTV = convertView.findViewById(R.id.ivDropDownLayout);
        if(item!=null) {
            dropDownTV.setImageResource(item.getSpinnerItemImage());
        }
        return convertView;
    }
}
