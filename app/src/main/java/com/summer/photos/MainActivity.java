package com.summer.photos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.summer.photos.adapter.MyRecyclerViewAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;

    private final static int[] PICTURE = {R.drawable.camera, R.drawable.camera,
            R.drawable.camera, R.drawable.camera};
    private final static String[] TEXT = {"照相机", "相册", "编辑", "修整"};
    private List<Intent> mIntents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
//        Intent intent = new Intent(this,)
    }

    private void initView() {
//        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
//        mAdapter = new MyRecyclerViewAdapter(this,TEXT,PICTURE);
//        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter(mAdapter);
    }
}
