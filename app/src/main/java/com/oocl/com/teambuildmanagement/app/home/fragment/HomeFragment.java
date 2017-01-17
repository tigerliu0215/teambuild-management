package com.oocl.com.teambuildmanagement.app.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.activity.detail.ActivityDetailActivity;
import com.oocl.com.teambuildmanagement.app.home.adapter.ActivityAdapter;
import com.oocl.com.teambuildmanagement.app.vote.VoteActivity;
import com.oocl.com.teambuildmanagement.app.vote.VoteViewActivity;
import com.oocl.com.teambuildmanagement.model.vo.AD;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivity;
import com.oocl.com.teambuildmanagement.common.HttpDict;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivityVo;
import com.oocl.com.teambuildmanagement.util.JsonUtil;
import com.oocl.com.teambuildmanagement.util.LogUtil;
import com.oocl.com.teambuildmanagement.util.OkHttpUtil;

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
    private Handler refreshUiHandler;
    private int flag = 0;
    private final int REFRESH_UI_FLAG = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            initViews();
            initHandler();
            closeOrShowRefreshUI(true);
            refreshData();
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        return view;
    }

    public void initHandler(){
        refreshUiHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case REFRESH_UI_FLAG:
                        refreshUI();
                        break;
                }
            }
        };
    }

    public void initViews(){
        rv_activities = (RecyclerView)view.findViewById(R.id.rv_activities);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTheme);
        adList = new ArrayList<AD>();
        activitiesList = new ArrayList<>();
        activityAdapter = new ActivityAdapter(activitiesList,getContext());
        activityAdapter.setHeaderData(adList);
        rv_activities.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_activities.setAdapter(activityAdapter);
        activityAdapter.setOnItemClickLitener(new ActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position,String activityId,String type) {
                if(ActivityAdapter.VOTE_TYPE.equals(type)){  //vote
                    LogUtil.info("VOTE_TYPE Click");
                    Intent intent = new Intent(view.getContext(), VoteActivity.class);
                    intent.putExtra("id", activityId);
                    startActivity(intent);
                }else if (ActivityAdapter.ACTIVITY_TYPE.equals(type)) {
                    LogUtil.info("ACTIVITY_TYPE Click"); // activity
                    Intent intent = new Intent(view.getContext(), ActivityDetailActivity.class);
                    intent.putExtra("id", activityId);
                    startActivity(intent);
                } else if (ActivityAdapter.VOTE_VIEW_TYPE.equals(type)) {//vote view
                    LogUtil.info("VOTE_VIEW_TYPE Click");
                    Intent intent = new Intent(view.getContext(), VoteViewActivity.class);
                    intent.putExtra("id", activityId);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onRefresh() {
        LogUtil.info("onRefresh");
        flag = 0;
        refreshData();
    }

    public void refreshData(){
        flag = 0;
        updateADData();
        updateActivitiesData();
    }

    public void closeOrShowRefreshUI(boolean isShow){
        swipeRefreshLayout.setRefreshing(isShow);
    }

    public void updateActivitiesData(){
        OkHttpUtil.get(HttpDict.URL_IP + HttpDict.URL_ACTIVITIES, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                flag++;
                refreshUiHandler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    TeamActivityVo teamActivityVo = JsonUtil.fromJson(response.body().string(),TeamActivityVo.class);
                    if(null != teamActivityVo && null != teamActivityVo.getData() && teamActivityVo.getData().size() > 0){
                        activitiesList.clear();
                        activitiesList.addAll(teamActivityVo.getData());
                    }
                }else{

                }
                flag++;
                refreshUiHandler.sendEmptyMessage(1);
            }

        });
    }
    public void updateADData(){
        OkHttpUtil.get(HttpDict.URL_IP + HttpDict.URL_POPULAR_IMAGES, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                flag++;
                refreshUiHandler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    List<AD> tempList = JsonUtil.fromJson(response.body().string(),new TypeToken<ArrayList<AD>>(){});
                    if(null != tempList && tempList.size() > 0){
                        adList.clear();
                        adList.addAll(tempList);
                    }
                }else{

                }
                flag++;
                refreshUiHandler.sendEmptyMessage(1);
            }
        });

    }
    public void refreshUI(){
        if(flag == 2){
            closeOrShowRefreshUI(false);
            if(!rv_activities.isComputingLayout()){
                LogUtil.info("refreshUI");
                activityAdapter.notifyDataSetChanged();
            }
        }
    }
}
