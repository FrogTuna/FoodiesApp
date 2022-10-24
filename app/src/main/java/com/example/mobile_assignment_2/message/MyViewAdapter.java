package com.example.mobile_assignment_2.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.R;


public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.MyViewHolder>{

    @NonNull
    @Override
    public MyViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_window_left);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }


    }

}
