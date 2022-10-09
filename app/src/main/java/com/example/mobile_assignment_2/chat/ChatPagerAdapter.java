package com.example.mobile_assignment_2.chat;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.firebase.database.DatabaseReference;


public class ChatPagerAdapter extends FragmentStateAdapter {
//    getItem method gives the fragment with respect to the position,
//    getCount method gives total number of fragments present and
//    getPageTitle method gives the title of the fragment.
    private DatabaseReference databaseReference;
    private int NUM_PAGES;
    public ChatPagerAdapter(FragmentActivity fa, int _NUM_PAGES) {
        super(fa);
        NUM_PAGES = _NUM_PAGES;
    }

    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;

        switch(position) {
            case 0:
                fragment = new ChatFragment();
                break;
            case 1:
                fragment = new FriendFragment();
                break;
            case 2:
                fragment = new RequestFragment();
                break;

        }
        return fragment;

    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }



}