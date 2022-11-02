package com.example.mobile_assignment_2.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_assignment_2.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

/**
 * @author:
 * @date: 2022/11/2 22:13
 * @description:
 */
public class BottomSheet extends BottomSheetDialogFragment {
    BottomSheetBehavior<View> bottomSheetBehavior;

    public BottomSheet() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_popup_window, container, false);
        String postName = getArguments().getString("postName");
        TextView postNameView = view.findViewById(R.id.comm_name);
        postNameView.setText(postName);
        String postDesc = getArguments().getString("postDesc");
        TextView postDescView = view.findViewById(R.id.comm_desc);
        postDescView.setText(postDesc);
        String postIngUrl = getArguments().getString("postIngUrl");
        ImageView postImgView = view.findViewById(R.id.postImg);
        Picasso.with(getContext()).load(postIngUrl).fit().centerCrop().into(postImgView);
        return view;

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
