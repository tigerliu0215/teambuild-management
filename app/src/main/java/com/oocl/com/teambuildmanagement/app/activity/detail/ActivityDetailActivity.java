package com.oocl.com.teambuildmanagement.app.activity.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.comment.CommentActivity;
import com.oocl.com.teambuildmanagement.common.HttpDict;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivity;
import com.oocl.com.teambuildmanagement.util.JsonUtil;
import com.oocl.com.teambuildmanagement.util.LogUtil;
import com.oocl.com.teambuildmanagement.util.OkHttpUtil;
import com.oocl.com.teambuildmanagement.util.SnackBarUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActivityDetailActivity extends AppCompatActivity {
    private WebView webview;
    private Button commentBtn;
    private Button likeBtn;
    private Button collectBtn;
    private Toolbar toolbar;

    private TextView subjectTxtView;
    private TextView creatorValTxtView;
    private TextView createDateValTxtView;
    private TextView collectCountTxtView;
    private TextView likeCountTxtView;

    private ProgressBar progressBar;

    private TeamActivity teamActivity;
    private Handler refreshUiHandler;
    private int flag = 0;
    private final int REFRESH_UI_FLAG = 1;
    private final int REFRESH_LIKE_DATA_FLAG = 3;
    private final int REFRESH_COLLECT_DATA_FLAG = 4;

    @Override
    protected void onResume() {
        super.onResume();

        final String activityId = this.getIntent().getCharSequenceExtra("id").toString();
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://112.74.166.187:8443/activities/" + activityId + "/mobile");
        webview.setWebViewClient(new HelloWebViewClient ());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initHandler();
        final String activityId = this.getIntent().getCharSequenceExtra("id").toString();
        System.out.println(activityId);
        this.getActivityDetailData(activityId);

        subjectTxtView = (TextView) findViewById(R.id.subject);
        creatorValTxtView = (TextView) findViewById(R.id.creatorVal);
        createDateValTxtView = (TextView) findViewById(R.id.createDateVal);
        collectCountTxtView = (TextView) findViewById(R.id.collectCount);
        likeCountTxtView = (TextView) findViewById(R.id.likeCount);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        commentBtn = (Button) findViewById(R.id.commentBtn);
        commentBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDetailActivity.this, CommentActivity.class);
                intent.putExtra("id", activityId);
                startActivity(intent);
            }
        });

        likeBtn = (Button) findViewById(R.id.likeBtn);
        likeBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLikesData(activityId);
            }
        });

        collectBtn = (Button) findViewById(R.id.collectBtn);
        collectBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCollectsData(activityId);
            }
        });

        webview = (WebView) findViewById(R.id.webview);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webview.setWebViewClient(new HelloWebViewClient());
        webview.loadUrl("http://112.74.166.187:8443/activities/" + activityId + "/mobile");
    }

    public void getActivityDetailData(String id){
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
                    flag = 1;
                    refreshUiHandler.sendEmptyMessage(1);
                    System.out.println(teamActivity.getTitle());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //返回上级
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_action_like:
                SnackBarUtil.showSanckBarUtil(toolbar,"点赞");
                break;
            case R.id.menu_action_collect:
                SnackBarUtil.showSanckBarUtil(toolbar,"收藏");
                break;

        }
        return true;
    }

    public void getLikesData(String id){
        OkHttpUtil.get(HttpDict.URL_IP + HttpDict.URL_ACTIVITIES + HttpDict.URL_ACTION_LIKE + "/" + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("get Activity Like fail");
                return;
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String body = response.body().string();
                    String jsonStr = body.substring(body.indexOf(":")+1, body.lastIndexOf("}"));
                    teamActivity = JsonUtil.fromJson(jsonStr, TeamActivity.class);
                    flag = 3;
                    refreshUiHandler.sendEmptyMessage(3);
                    System.out.println(teamActivity.getTitle());
                }
            }
        });
    }

    public void getCollectsData(String id){
        OkHttpUtil.get(HttpDict.URL_IP + HttpDict.URL_ACTIVITIES + HttpDict.URL_ACTION_COLLECT + "/" + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("get Activity collects fail");
                return;
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String body = response.body().string();
                    String jsonStr = body.substring(body.indexOf(":")+1, body.lastIndexOf("}"));
                    teamActivity = JsonUtil.fromJson(jsonStr, TeamActivity.class);
                    flag = 4;
                    refreshUiHandler.sendEmptyMessage(4);
                    System.out.println(teamActivity.getTitle());
                }
            }
        });
    }

    public void initHandler(){
        refreshUiHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case REFRESH_UI_FLAG:
                        refreshUI();
                        break;
                    case REFRESH_LIKE_DATA_FLAG:
                        refreshLikeData();;
                        break;
                    case REFRESH_COLLECT_DATA_FLAG:
                        refreshCollectData();;
                        break;
                }
            }
        };
    }

    public void refreshUI(){
        if(flag == 1){
            LogUtil.info("refreshUI");
            subjectTxtView.setText(teamActivity.getTitle());
            creatorValTxtView.setText(teamActivity.getCreatedBy().getDisplayName());
            createDateValTxtView.setText(teamActivity.getCreated().substring(0,10));
            if (teamActivity.getCollects() != null && teamActivity.getCollects().size() != 0) {
                collectCountTxtView.setText(String.valueOf(teamActivity.getCollects().size()));
            } else {
                collectCountTxtView.setText("0");
            }
            if (teamActivity.getLikes() != null && teamActivity.getLikes().size() != 0) {
                likeCountTxtView.setText(String.valueOf(teamActivity.getLikes().size()));
            } else {
                likeCountTxtView.setText("0");
            }
            if(teamActivity.isLiked()) {
                likeBtn.setText("不赞了");;
            } else {
                likeBtn.setText("点赞");;
            }
            if(teamActivity.isCollected()) {
                collectBtn.setText("取消收藏");;
            } else {
                collectBtn.setText("收藏");;
            }
        }
    }

    public void refreshLikeData(){
        if(flag == 3){
            LogUtil.info("refresh Like DATA");
            if (teamActivity.getLikes() != null && teamActivity.getLikes().size() != 0) {
                likeCountTxtView.setText(String.valueOf(teamActivity.getLikes().size()));
            } else {
                likeCountTxtView.setText("0");
            }
            if(teamActivity.isLiked()) {
                likeBtn.setText("不赞了");;
            } else {
                likeBtn.setText("点赞");;
            }
        }
    }

    public void refreshCollectData(){
        if(flag == 4){
            LogUtil.info("refresh Collect DATA");
            if (teamActivity.getCollects() != null && teamActivity.getCollects().size() != 0) {
                collectCountTxtView.setText(String.valueOf(teamActivity.getCollects().size()));
            } else {
                collectCountTxtView.setText("0");
            }
            if(teamActivity.isCollected()) {
                collectBtn.setText("取消收藏");;
            } else {
                collectBtn.setText("收藏");;
            }
        }
    }

//    @Override
//    //设置回退
//    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
//            webview.goBack(); //goBack()表示返回WebView的上一页面
//            return true;
//        }
//        return false;
//    }

    private class HelloWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

}
