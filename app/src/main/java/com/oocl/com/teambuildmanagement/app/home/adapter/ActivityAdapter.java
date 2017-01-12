package com.oocl.com.teambuildmanagement.app.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.model.vo.AD;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivity;
import com.oocl.com.teambuildmanagement.util.LogUtil;
import com.oocl.com.teambuildmanagement.widget.ADView;

import java.util.List;

/**
 * Created by YUJO2 on 1/10/2017.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder>{

    private List<TeamActivity> dataList;
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;
    private int headerNum = 0;
    private List<AD> adDatas;
    private float x1 = -1;
    private float x2 = -1;
    public ActivityAdapter(List<TeamActivity> dataList, Context context) {
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
        if(headerNum > position){
            //header view
            holder.ll_header.setVisibility(View.VISIBLE);
            holder.ll_content.setVisibility(View.GONE);
            holder.adView.setADPic(adDatas);
            holder.adView.start();
            holder.adView.getVp().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        x1 = motionEvent.getX();
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        x2 = motionEvent.getX();
                    }
                    if (x1 != -1 && x2 != -1) {
                        if (Math.abs(x1 - x2) < 50) {
                            holder.adView.openUrl();
                        }
                        x1 = -1;
                        x2 = -1;
                    }
                    return false;
                }
            });
        }else{
            holder.ll_header.setVisibility(View.GONE);
            holder.ll_content.setVisibility(View.VISIBLE);
            holder.tv_title.setText(dataList.get(position - headerNum).getTitle());
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
        return dataList.size() + headerNum;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView iv_activity;
        TextView tv_title;
        TextView tv_desc;
        TextView tv_date;
        LinearLayout ll_content;
        LinearLayout ll_header;
        ADView adView;
        public ViewHolder(View view)
        {
            super(view);
            iv_activity = (ImageView) view.findViewById(R.id.iv_activity);
            ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
            adView = (ADView) view.findViewById(R.id.adView);
            ll_header = (LinearLayout) view.findViewById(R.id.ll_header);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
        }
    }

    public void setHeaderData(List<AD> adDatas){
        this.adDatas = adDatas;
        headerNum = 1;
        this.notifyDataSetChanged();
    }

    public void removeHeaderData(){
        this.adDatas = null;
        this.headerNum = 0;
        this.notifyDataSetChanged();
    }
}
