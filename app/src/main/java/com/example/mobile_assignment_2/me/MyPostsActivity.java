package com.example.mobile_assignment_2.me;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_assignment_2.R;

import java.util.LinkedList;
import java.util.List;

public class MyPostsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private List<Posts> postsList;
    private Context context;
    private PostAdapter adapter;
    private ListView list_animal;
    private LinearLayout ly_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.me_postlists);
        context = MyPostsActivity.this;
        list_animal = (ListView) findViewById(R.id.MyPostsList);
        //动态加载顶部View和底部View
//        final LayoutInflater inflater = LayoutInflater.from(this);
//        View headView = inflater.inflate(R.layout.view_header, null, false);
//        View footView = inflater.inflate(R.layout.view_footer, null, false);

        postsList = new LinkedList<Posts>();
        postsList.add(new Posts("狗说", "你是狗么?"));
        postsList.add(new Posts("牛说", "你是牛么?"));
        postsList.add(new Posts("鸭说", "你是鸭么?"));
        postsList.add(new Posts("鱼说", "你是鱼么?"));
        postsList.add(new Posts("马说", "你是马么?"));
        adapter = new PostAdapter((LinkedList<Posts>) postsList, context);
        //添加表头和表尾需要写在setAdapter方法调用之前！！！
//        list_animal.addHeaderView(headView);
//        list_animal.addFooterView(footView);

        list_animal.setAdapter(adapter);
        list_animal.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context,"你点击了第" + position + "项",Toast.LENGTH_SHORT).show();
    }
}
