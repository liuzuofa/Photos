package com.summer.photos.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.summer.photos.R;

import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private String[] mText;
    private int[] mPicture;
    private List<Intent> mIntents;


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_picture;
        TextView tv_text;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }

    public MyRecyclerViewAdapter(Context context, String[] text, int[] picture) {
        this.mContext = context;
        this.mText = text;
        this.mPicture = picture;
    }

    public MyRecyclerViewAdapter(Context context, String[] text, int[] picture ,List<Intent> intents) {
        this.mContext = context;
        this.mText = text;
        this.mPicture = picture;
        this.mIntents = intents;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.iv_picture.setImageResource(mPicture[position]);
        holder.tv_text.setText(mText[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mText.length;
    }

}
