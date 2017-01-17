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
import com.oocl.com.teambuildmanagement.util.ImageUtil;
import com.oocl.com.teambuildmanagement.util.LogUtil;
import com.oocl.com.teambuildmanagement.widget.ADView;

import java.util.List;

/**
 * Created by YUJO2 on 1/10/2017.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder>{
    public static final String VOTE_TYPE = "VOTE_TYPE";
    public static final String VOTE_VIEW_TYPE = "VOTE_VIEW_TYPE";
    public static final String ACTIVITY_TYPE = "ACTIVITY_TYPE";
    private List<TeamActivity> dataList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private int headerNum = 0;
    private List<AD> adDatas;
    private float x1 = -1;
    private float x2 = -1;
    public ActivityAdapter(List<TeamActivity> dataList, Context context) {
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(headerNum > position){
            //header view
            holder.ll_header.setVisibility(View.VISIBLE);
            holder.ll_content.setVisibility(View.GONE);
            holder.adView.setADPic(adDatas);
            LogUtil.info(adDatas.size() + "");
            holder.adView.getVp().getAdapter().notifyDataSetChanged();
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
                            String activityId = holder.adView.openUrl();
                            mOnItemClickListener.onItemClick(0,activityId,ACTIVITY_TYPE);
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
            TeamActivity tempData = dataList.get(position - headerNum);
            holder.tv_title.setText(tempData.getTitle());
            holder.tv_desc.setText(tempData.getSummary());
            holder.tv_date.setText(null != tempData.getCreated() && tempData.getCreated().length() >= 10?tempData.getCreated().substring(0,10):"");
            if(null != tempData.getAttachments() && tempData.getAttachments().size() > 0){
                ImageUtil.show(context,tempData.getAttachments().get(0).getLink(),holder.iv_activity);
            }else{
                if(null == tempData.getVotings() || tempData.getVotings().size() == 0){
                    holder.iv_activity.setImageResource(R.mipmap.news);
                }else{
                    holder.iv_activity.setImageResource(R.mipmap.vote);
                }
            }
            if (mOnItemClickListener != null)
            {
                holder.ll_content.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int pos = holder.getLayoutPosition();
                        TeamActivity clickActivity = dataList.get(pos - headerNum);
                        String type;
                        if (null == clickActivity.getVotings() || clickActivity.getVotings().size() == 0) {
                            type = ACTIVITY_TYPE;
                        } else {
                            type = clickActivity.getVotings().get(0).isVoted()? VOTE_VIEW_TYPE : VOTE_TYPE;
                        }
                        mOnItemClickListener.onItemClick(pos,clickActivity.get_id(),type);
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
        this.headerNum = 1;
    }

    public void removeHeaderData(){
        this.adDatas = null;
        this.headerNum = 0;
    }

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener)
    {
        this.mOnItemClickListener = mOnItemClickLitener;
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position,String activityId,String type);
    }

}
