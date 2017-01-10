package com.oocl.com.teambuildmanagement.app.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.home.adapter.ActivityAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Jonas Yu on 2017/1/8 23:10
 * Description:
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View view;
    //activity list and ad
    private RecyclerView rv_activities;
    private ActivityAdapter activityAdapter;
    private List<String> activitiesList;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            refreshDatas();
            initViews();
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        return view;
    }
    public void refreshDatas(){
        activitiesList = new ArrayList<>();
        activitiesList.add("Hong Kong Colleagues Christmas Celebrations 2016");
        activitiesList.add("CEO New Year Message 2017");
        activitiesList.add("OOCL Sri Lanka Celebrates 10th");
        activitiesList.add("OOCL Sri Lanka Celebrates 10th 1");
        activitiesList.add("OOCL Sri Lanka Celebrates 10th 2");
        activitiesList.add("OOCL Sri Lanka Celebrates 10th 3");
    }
    public void initViews(){
        rv_activities = (RecyclerView)view.findViewById(R.id.rv_activities);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setDistanceToTriggerSync(300);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTheme);
//        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
        rv_activities.setLayoutManager(new LinearLayoutManager(getContext()));
        activityAdapter = new ActivityAdapter(activitiesList,getContext());
        rv_activities.setAdapter(activityAdapter);
    }

    public void initHeader(ActivityAdapter activityAdapter){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_header,rv_activities,false);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 停止刷新
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000); // 5秒后发送消息，停止刷新
    }
}
