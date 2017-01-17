package com.oocl.com.teambuildmanagement.app.myCollect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.activity.detail.ActivityDetailActivity;
import com.oocl.com.teambuildmanagement.app.home.adapter.ActivityAdapter;
import com.oocl.com.teambuildmanagement.app.myCollect.adapter.MyCollectionsAdapter;
import com.oocl.com.teambuildmanagement.app.vote.VoteActivity;
import com.oocl.com.teambuildmanagement.app.vote.VoteViewActivity;
import com.oocl.com.teambuildmanagement.common.HttpDict;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivity;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivityVo;
import com.oocl.com.teambuildmanagement.util.JsonUtil;
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
 * Created by YUJO2 on 1/16/2017.
 */

public class MyCollectActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv_collections;
    private MyCollectionsAdapter myCollectionsAdapter;
    private List<TeamActivity> collectionList;
    private Handler uiHandler;
    private final int REFRESH_FLAG = 1;
    private Toolbar toolbar;

    private TeamActivity teamActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        initUiHandler();
        initViews();
        refreshData();
    }

    public void initViews(){
        collectionList = new ArrayList<>();
        myCollectionsAdapter = new MyCollectionsAdapter(collectionList,MyCollectActivity.this);
        myCollectionsAdapter.setOnItemClickListener(new MyCollectionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String activityId, String type) {
                //todo
                if(ActivityAdapter.VOTE_TYPE.equals(type)){  //vote
                    LogUtil.info("VOTE_TYPE Click");
                    getActivityDetailData(activityId);
                    if (teamActivity.getVotings().get(0).isVoted()) {
                        LogUtil.info("VOTE_VIEW_TYPE Click");
                        Intent intent = new Intent(MyCollectActivity.this, VoteViewActivity.class);
                        intent.putExtra("id", activityId);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MyCollectActivity.this, VoteActivity.class);
                        intent.putExtra("id", activityId);
                        startActivity(intent);
                    }
                }else{
                    LogUtil.info("ACTIVITY_TYPE Click"); // activity
                    Intent intent = new Intent(MyCollectActivity.this, ActivityDetailActivity.class);
                    intent.putExtra("id", activityId);
                    startActivity(intent);
                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorRefresh);
        rv_collections = (RecyclerView)findViewById(R.id.rv_collections);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rv_collections.setLayoutManager(new LinearLayoutManager(MyCollectActivity.this));
        rv_collections.setAdapter(myCollectionsAdapter);

    }

    public void initUiHandler(){
        uiHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what){
                    case  REFRESH_FLAG:
                        refreshUi();
                        break;
                }
                return false;
            }
        });
    }

    public void refreshData(){
        showRefresh(false);
        OkHttpUtil.get(HttpDict.URL_IP + HttpDict.URL_COLLECTION, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SnackBarUtil.showSanckBarUtil(swipeRefreshLayout,"Refresh fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.info(response.code() + "");
                if(response.code() == 200){
                    String responseBody = response.body().string();
                    LogUtil.info(responseBody);
                    TeamActivityVo teamActivityVo = JsonUtil.fromJson(responseBody,TeamActivityVo.class);

                    collectionList.clear();
                    if(null != teamActivityVo && null != teamActivityVo.getData() && teamActivityVo.getData().size() > 0){
                        collectionList.addAll(teamActivityVo.getData());

                    }
                    uiHandler.sendEmptyMessage(REFRESH_FLAG);
                }else{
                   //todo judge login status
                }
            }
        });
    }

    public void refreshUi(){
        myCollectionsAdapter.notifyDataSetChanged();
        showRefresh(false);
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    public void showRefresh(boolean judge){
        swipeRefreshLayout.setRefreshing(judge);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) //back
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getActivityDetailData(String id){
        OkHttpUtil.get(HttpDict.URL_IP + HttpDict.URL_ACTIVITIES + "/" + id, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("get ACTIVITIES fail");
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String body = response.body().string();
                    String jsonStr = body.substring(body.indexOf(":")+1, body.lastIndexOf("}"));
                    teamActivity = JsonUtil.fromJson(jsonStr, TeamActivity.class);
                    System.out.println(teamActivity.getTitle());
                }
            }
        });
    }
}
