package com.oocl.com.teambuildmanagement.app.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oocl.com.teambuildmanagement.R;

import java.util.List;

/**
 * Created by YUJO2 on 1/10/2017.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder>{

    private List<String> dataList;
    private Context context;

    public ActivityAdapter(List<String> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.recycler_item_activities, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_title.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView iv_activity;
        TextView tv_title;
        TextView tv_desc;
        TextView tv_date;
        public ViewHolder(View view)
        {
            super(view);
            iv_activity = (ImageView) view.findViewById(R.id.iv_activity);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
        }
    }
}
