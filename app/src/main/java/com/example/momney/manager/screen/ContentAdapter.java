package com.example.momney.manager.screen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.momney.manager.R;
import com.example.momney.manager.activities.ContentActivity;
import com.example.momney.manager.activities.TransactionActivity;
import com.example.momney.manager.data.Content;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {

    private ArrayList<Content> contents;
    private Context mcontext;
    private OnItemClickListener listener;

    public ContentAdapter(ArrayList<Content> contents, Context mcontext, OnItemClickListener listener) {
        this.contents = contents;
        this.mcontext = mcontext;
        this.listener = listener;
    }


    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentHolder(LayoutInflater.from(mcontext).
                inflate(R.layout.list_content, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ContentHolder holder, int position) {
        Content currentContent = contents.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentContent);

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public class ContentHolder extends RecyclerView.ViewHolder{
        private TextView mContent;
        private ImageView mIcon;
        private Content content;

        public ContentHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(content);
                }
            });
            mContent = itemView.findViewById(R.id.content);
            mIcon = itemView.findViewById(R.id.icon_content);

        }

        void bindTo(Content currentContent) {
            // Populate the textviews with data.
            this.content = currentContent;
            mContent.setText(currentContent.getContent());
            mIcon.setImageResource(currentContent.getImageResource());

        }

    }
}
