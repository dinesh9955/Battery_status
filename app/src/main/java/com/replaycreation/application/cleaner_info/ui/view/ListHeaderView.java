package com.replaycreation.application.cleaner_info.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.replaycreation.application.R;

public class ListHeaderView extends RelativeLayout {
    private Context mContext;
    public TextView mSize;
    public TextView mProgress;

    public ListHeaderView(Context context, ViewGroup listView) {
        super(context);
        this.mContext = context;
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.list_header_view, listView, false);
        addView(view);
        mSize = (TextView) findViewById(R.id.total_size);
        mProgress = (TextView) findViewById(R.id.progress_msg);
    }
}