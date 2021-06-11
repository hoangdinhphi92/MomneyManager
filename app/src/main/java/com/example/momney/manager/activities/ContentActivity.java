package com.example.momney.manager.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momney.manager.R;
import com.example.momney.manager.activities.TransactionActivity;
import com.example.momney.manager.data.Content;
import com.example.momney.manager.screen.ContentAdapter;
import com.example.momney.manager.screen.OnItemClickListener;
import com.example.momney.manager.utils.Utils;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity implements OnItemClickListener {

    private ArrayList<Content> mContent;
    private ContentAdapter mAdapter;
    public static final String EXTRA_CONTENT =
            "com.example.momney.manager.extra.content";
    public static final String EXTRA_ICON =
            "com.example.momney.manager.extra.icon";
    Bundle tState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Utils.restoreState(this);
        RecyclerView mRecyclerView = findViewById(R.id.rcv_content);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mContent = new ArrayList<>();

        mAdapter = new ContentAdapter(mContent, this, this::onItemClick);
        mRecyclerView.setAdapter(mAdapter);
        initializeData();
        Intent intent = getIntent();
        tState = intent.getBundleExtra("state");
    }

    private void initializeData() {
        // Get the resources from the XML file.
        String[] contentList = getResources()
                .getStringArray(R.array.content);
        TypedArray contentImageResources =
                getResources().obtainTypedArray(R.array.content_images);

        // Clear the existing data (to avoid duplication).
        mContent.clear();


        // Create the ArrayList of Sports objects with titles and
        // information about each sport.
        for (int i = 0; i < contentList.length; i++) {
            mContent.add(new Content(contentList[i], contentImageResources.getResourceId(i, 0)));
        }
        contentImageResources.recycle();

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }

    public void Return(View view) {
        Intent intent = new Intent(ContentActivity.this, TransactionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Content content) {
        Intent intent = new Intent(this, TransactionActivity.class);
        String mContent = content.getContent();
        int imageResource = content.getImageResource();
        intent.putExtra("state", tState);
        intent.putExtra(EXTRA_CONTENT, mContent);
        intent.putExtra(EXTRA_ICON, imageResource);
        startActivity(intent);
    }


}