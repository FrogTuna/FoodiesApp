package com.example.mobile_assignment_2.chat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;


public class ChatPagerAdapter extends FragmentStateAdapter {
//    getItem method gives the fragment with respect to the position,
//    getCount method gives total number of fragments present and
//    getPageTitle method gives the title of the fragment.
    private DatabaseReference databaseReference;
    private int NUM_PAGES;
    ArrayList friendArrayList, userArrayList, reqArrayList, chatArrayList;
    public ChatPagerAdapter(FragmentActivity fa,
                            int _NUM_PAGES,
                            ArrayList _friendArrayList,
                            ArrayList _userArrayList,
                            ArrayList _reqArrayList,
                            ArrayList _chatArrayList
    ) {
        super(fa);
        NUM_PAGES = _NUM_PAGES;
        friendArrayList = _friendArrayList;
        userArrayList = _userArrayList;
        reqArrayList = _reqArrayList;
        chatArrayList = _chatArrayList;
    }

    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;

        switch(position) {
            case 0:
                fragment = new ChatFragment(this.chatArrayList, this.userArrayList);
                break;
            case 1:
                fragment = new FriendFragment(this.friendArrayList, this.userArrayList);
                break;
            case 2:
                fragment = new RequestFragment(this.reqArrayList);
                break;

        }
        return fragment;

    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }

}