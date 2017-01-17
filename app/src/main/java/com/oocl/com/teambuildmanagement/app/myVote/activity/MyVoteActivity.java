package com.oocl.com.teambuildmanagement.app.myVote.activity;

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
import com.oocl.com.teambuildmanagement.app.myVote.adapter.MyVoteAdapter;
import com.oocl.com.teambuildmanagement.app.vote.VoteViewActivity;
import com.oocl.com.teambuildmanagement.common.HttpDict;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivity;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivityVo;
import com.oocl.com.teambuildmanagement.util.DialogUtil;
import com.oocl.com.teambuildmanagement.util.JsonUtil;
import com.oocl.com.teambuildmanagement.util.LogUtil;
import com.oocl.com.teambuildmanagement.util.OkHttpUtil;
import com.oocl.com.teambuildmanagement.util.SnackBarUtil;
import com.oocl.com.teambuildmanagement.util.ValidationUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by YUJO2 on 1/17/2017.
 */

public class MyVoteActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv_collections;
    private MyVoteAdapter myVoteAdapter;
    private List<TeamActivity> voteList;
    private Handler uiHandler;
    private final int REFRESH_FLAG = 1;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vote);
        initUiHandler();
        initViews();
        refreshData();
    }

    public void initViews(){
        voteList = new ArrayList<>();
        myVoteAdapter = new MyVoteAdapter(voteList,MyVoteActivity.this);
        myVoteAdapter.setOnItemClickListener(new MyVoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String activityId, String type) {
                Intent intent = new Intent(MyVoteActivity.this, VoteViewActivity.class);
                intent.putExtra("id", activityId);
                startActivity(intent);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorRefresh);
        rv_collections = (RecyclerView)findViewById(R.id.rv_votings);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rv_collections.setLayoutManager(new LinearLayoutManager(MyVoteActivity.this));
        rv_collections.setAdapter(myVoteAdapter);
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
        OkHttpUtil.get(HttpDict.URL_IP + HttpDict.URL_VOTINGS, new Callback() {
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
                    voteList.clear();
                    if(null != teamActivityVo && null != teamActivityVo.getData()){
                        voteList.addAll(teamActivityVo.getData());
                    }
                    uiHandler.sendEmptyMessage(REFRESH_FLAG);
                }else{
                    //todo judge login status
                    if(ValidationUtil.getInstance().validateResponse(response) == ValidationUtil.LOGIN_INVALID){
                        DialogUtil.showLoginDialog(MyVoteActivity.this,getString(R.string.title_login_invalid));
                    }
                }
            }
        });
    }

    public void refreshUi(){
        myVoteAdapter.notifyDataSetChanged();
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

}
