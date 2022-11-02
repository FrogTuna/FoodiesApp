package com.example.mobile_assignment_2.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_assignment_2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @author:
 * @date: 2022/11/2 22:13
 * @description:
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private ArrayList<String> imageUrls = new ArrayList<String>();
    private Context context;
    private int resource;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView imagePositionView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.post_image);
            imagePositionView = (TextView) view.findViewById(R.id.image_position);

        }

    }

    public ImagesAdapter(ArrayList<String> imageUrls, Context context, int resource) {
        this.imageUrls = imageUrls;
        this.context = context;
        this.resource = resource;
    }

    // Create new view
    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(resource, viewGroup, false);

        return new ImagesAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ImagesAdapter.ViewHolder viewHolder, final int position) {

        // Download image from URL and set to imageView
        Picasso.with(context).load(imageUrls.get(position)).fit().centerCrop().into(viewHolder.imageView);
        viewHolder.imagePositionView.setText(position + 1 + "/" + imageUrls.size());
    }


    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
