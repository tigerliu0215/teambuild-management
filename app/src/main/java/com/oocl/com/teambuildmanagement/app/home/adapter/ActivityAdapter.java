package com.oocl.com.teambuildmanagement.app.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.util.LogUtil;

import java.util.List;

/**
 * Created by YUJO2 on 1/10/2017.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder>{

    private List<String> dataList;
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;

    public ActivityAdapter(List<String> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener
    {
        void onItemClick(int position);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.info("onCreateViewHolder  " + viewType);
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.recycler_item_activities, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        LogUtil.info("onBindViewHolder  " + position);
        if(position == 0){
            //header view
            holder.ll_header.setVisibility(View.VISIBLE);
            holder.ll_content.setVisibility(View.GONE);
            if (mOnItemClickLitener != null)
            {
                holder.ll_header.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(pos);
                    }
                });
            }
        }else{
            holder.ll_header.setVisibility(View.GONE);
            holder.ll_content.setVisibility(View.VISIBLE);
            holder.tv_title.setText(dataList.get(position));
            if (mOnItemClickLitener != null)
            {
                holder.ll_content.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(pos);
                    }
                });
            }
        }

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
        LinearLayout ll_content;
        LinearLayout ll_header;
        public ViewHolder(View view)
        {
            super(view);
            iv_activity = (ImageView) view.findViewById(R.id.iv_activity);
            ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
            ll_header = (LinearLayout) view.findViewById(R.id.ll_header);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
        }
    }
}
