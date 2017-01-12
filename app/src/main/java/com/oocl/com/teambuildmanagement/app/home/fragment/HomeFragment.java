package com.oocl.com.teambuildmanagement.app.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.home.adapter.ActivityAdapter;
import com.oocl.com.teambuildmanagement.model.vo.AD;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivity;
import com.oocl.com.teambuildmanagement.util.HttpUtil;
import com.oocl.com.teambuildmanagement.util.LogUtil;
import com.oocl.com.teambuildmanagement.util.OkHttpUtil;
import com.oocl.com.teambuildmanagement.util.SnackBarUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Author：Jonas Yu on 2017/1/8 23:10
 * Description:
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View view;
    //activity list and ad
    private RecyclerView rv_activities;
    private ActivityAdapter activityAdapter;
    private List<TeamActivity> activitiesList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<AD> adList;
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
        TeamActivity teamActivity = new TeamActivity();
        teamActivity.setTitle("test1");
        activitiesList.add(teamActivity);
        teamActivity = new TeamActivity();
        teamActivity.setTitle("test2");
        activitiesList.add(teamActivity);

        adList = new ArrayList<>();
        AD ad = new AD();
        ad.setLink("http://112.74.166.187:8443/modules/activities/client/images/uploads/b198616e49ffb9e0529c69cfad844efb");
        adList.add(ad);
        ad = new AD();
        ad.setLink("http://112.74.166.187:8443/modules/activities/client/images/uploads/a22822d5110e810779e0c3dc06990f93");
        adList.add(ad);
        ad = new AD();
        ad.setLink("http://112.74.166.187:8443/modules/activities/client/images/uploads/b198616e49ffb9e0529c69cfad844efb");
        adList.add(ad);
        ad = new AD();
        ad.setLink("http://112.74.166.187:8443/modules/activities/client/images/uploads/a22822d5110e810779e0c3dc06990f93");
        adList.add(ad);
        ad = new AD();
        ad.setLink("http://112.74.166.187:8443/modules/activities/client/images/uploads/b198616e49ffb9e0529c69cfad844efb");
        adList.add(ad);
        activityAdapter = new ActivityAdapter(activitiesList,getContext());
        activityAdapter.setHeaderData(adList);
    }

    public void initViews(){
        rv_activities = (RecyclerView)view.findViewById(R.id.rv_activities);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setDistanceToTriggerSync(300);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTheme);
//        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
        rv_activities.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_activities.setAdapter(activityAdapter);
        activityAdapter.setOnItemClickLitener(new ActivityAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                LogUtil.info(position + "");
            }
        });
    }

    public void refreshHeader(ActivityAdapter activityAdapter){
        OkHttpUtil.get(HttpUtil.URL_IP + HttpUtil.URL_ACTIVITIES, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("get ACTIVITIES fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}
