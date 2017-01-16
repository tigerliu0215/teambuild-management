package com.oocl.com.teambuildmanagement.app.myCollect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.oocl.com.teambuildmanagement.app.home.adapter.ActivityAdapter;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivity;

import java.util.List;

/**
 * Created by YUJO2 on 1/16/2017.
 */

public class MyCollectionsAdapter extends RecyclerView.Adapter<MyCollectionsAdapter.ViewHolder>{

    private List<TeamActivity> collectionsData;
    private Context context;

    @Override
    public MyCollectionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyCollectionsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
