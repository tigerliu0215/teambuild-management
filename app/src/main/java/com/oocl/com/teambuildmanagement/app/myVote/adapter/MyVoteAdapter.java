package com.oocl.com.teambuildmanagement.app.myVote.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivity;
import com.oocl.com.teambuildmanagement.util.ImageUtil;

import java.util.List;

/**
 * Created by YUJO2 on 1/17/2017.
 */

public class MyVoteAdapter extends RecyclerView.Adapter<MyVoteAdapter.ViewHolder>{
    public static final String VOTE_TYPE = "VOTE_TYPE";
    public static final String ACTIVITY_TYPE = "ACTIVITY_TYPE";
    private List<TeamActivity> collectionsData;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public MyVoteAdapter(List<TeamActivity> collectionsData, Context context) {
        this.collectionsData = collectionsData;
        this.context = context;
    }

    @Override
    public MyVoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyVoteAdapter.ViewHolder holder = new MyVoteAdapter.ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.recycler_item_my_collections, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyVoteAdapter.ViewHolder holder, int position) {
        TeamActivity tempData = collectionsData.get(position);
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
        if (onItemClickListener != null)
        {
            holder.ll_content.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    TeamActivity clickActivity = collectionsData.get(pos);
                    onItemClickListener.onItemClick(pos,clickActivity.get_id(),null == clickActivity.getVotings() || clickActivity.getVotings().size() == 0?ACTIVITY_TYPE:VOTE_TYPE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return collectionsData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_activity;
        TextView tv_title;
        TextView tv_desc;
        TextView tv_date;
        LinearLayout ll_content;
        public ViewHolder(View view)
        {
            super(view);
            iv_activity = (ImageView) view.findViewById(R.id.iv_activity);
            ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position, String activityId, String type);
    }
}
