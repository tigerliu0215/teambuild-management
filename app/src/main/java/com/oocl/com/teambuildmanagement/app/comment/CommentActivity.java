package com.oocl.com.teambuildmanagement.app.comment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.common.HttpDict;
import com.oocl.com.teambuildmanagement.util.LogUtil;
import com.oocl.com.teambuildmanagement.util.OkHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity {
    private EditText commentEditText;
    private Button commentBtn;

    private Handler refreshUiHandler;
    private int flag = 0;
    private final int REFRESH_UI_FLAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initHandler();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String activityId = this.getIntent().getCharSequenceExtra("id").toString();
        System.out.println(activityId);

        commentEditText = (EditText) findViewById(R.id.commentEditText);
        commentBtn = (Button) findViewById(R.id.commentBtn);
        commentBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEditText.getText().toString();
                publishComment(activityId, comment);
            }
        });
    }

    public void publishComment(String id, String content) {
        OkHttpUtil.postByJson(HttpDict.URL_IP + HttpDict.URL_ACTIVITIES + HttpDict.URL_ACTION_COMMMNET + "/" + id, "{\"content\":\""+content+"\"}", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String userProfile = response.body().string();
                LogUtil.info(userProfile);
                if (response.code() == 200) {
                    LogUtil.info("push commnet " + userProfile);
                    ///跳转到 detail page
                    flag = 1;
                    refreshUiHandler.sendEmptyMessage(1);
                    finish();
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
                }
            }
        };
    }

    public void refreshUI(){
        if(flag == 1){
            LogUtil.info("refreshUI");
            Toast.makeText(getApplicationContext(), "谢谢你的评论~",
                    Toast.LENGTH_LONG).show();
        }
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
}
