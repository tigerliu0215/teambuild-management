package com.oocl.com.teambuildmanagement.app.activity.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.common.HttpDict;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivity;
import com.oocl.com.teambuildmanagement.util.JsonUtil;
import com.oocl.com.teambuildmanagement.util.OkHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActivityDetailActivity extends AppCompatActivity {
    private WebView webview;
    private Button commentBtn;
    private Button likeBtn;
    private Button collectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        commentBtn = (Button) findViewById(R.id.commentBtn);
        commentBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 2017/1/15
            }
        });

        likeBtn = (Button) findViewById(R.id.likeBtn);
        likeBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 2017/1/15
            }
        });

        collectBtn = (Button) findViewById(R.id.collectBtn);
        collectBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 2017/1/15
            }
        });

        String activityId = this.getIntent().getCharSequenceExtra("id").toString();
        System.out.println(activityId);

        webview = (WebView) findViewById(R.id.webview);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webview.loadUrl("http://112.74.166.187:8443/activities/" + activityId + "/mobile");
        //设置Web视图
        webview.setWebViewClient(new HelloWebViewClient ());
    }

    public void getActivityDetailData(String id){
        OkHttpUtil.get(HttpDict.URL_IP + HttpDict.URL_ACTIVITIES + id, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("get ACTIVITIES fail");
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String body = response.body().string();
                    TeamActivity teamActivity = JsonUtil.fromJson(body, TeamActivity.class);
                }
            }
        });
    }

    //返回上级
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
