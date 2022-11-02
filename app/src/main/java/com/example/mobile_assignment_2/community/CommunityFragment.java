package com.example.mobile_assignment_2.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobile_assignment_2.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class CommunityFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TabLayout tabLayout;
    private ImageView addView;
    ViewPager2 viewPager;
    ViewPagerAdapter viewPagerAdapter;

    public CommunityFragment() {
    }

    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getActivity().setTitle("Community");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community_home, container, false);
        tabLayout = view.findViewById(R.id.main_tab_layout);
        addView = view.findViewById(R.id.add_community);
        addView.setOnClickListener(this);
        viewPager = view.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Discover");
                    } else {
                        tab.setText("My Community");
                    }
                }
        ).attach();
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_community:
                Intent i = new Intent(getActivity(), AddCommunity.class);
                startActivity(i);
                break;
        }
    }


    public class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(Fragment fm) {
            super(fm);

        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new DiscoverCommunityFragment();
                case 1:
                    return new MyCommunityFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
