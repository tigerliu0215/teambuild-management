package com.oocl.com.teambuildmanagement.app.myCollect.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.myCollect.adapter.MyCollectionsAdapter;
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
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTheme);
        rv_collections = (RecyclerView)findViewById(R.id.rv_collections);
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
}
