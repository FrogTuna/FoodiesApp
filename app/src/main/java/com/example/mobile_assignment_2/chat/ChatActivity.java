package com.example.mobile_assignment_2.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.mobile_assignment_2.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ChatActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 3;
    TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ChatPagerAdapter chatPagerAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        viewPager2 = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);
        searchView = findViewById(R.id.searchView);

        chatPagerAdapter = new ChatPagerAdapter(this, NUM_PAGES);
        viewPager2.setAdapter(chatPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2,
            new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(TabLayout.Tab tab, int position) {
                    switch (position) {
                        case 0:
                            tab.setText("Chat");
                            break;
                        case 1:
                            tab.setText("Friend");
                            break;
                        case 2:
                            tab.setText("Request");
                            break;
                        default:
                            break;
                    }
                }
            }).attach();

    }
    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }

    public void searchUsername() {

    }



}