package com.oocl.com.teambuildmanagement.app.vote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.common.HttpDict;
import com.oocl.com.teambuildmanagement.model.vo.Option;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivity;
import com.oocl.com.teambuildmanagement.model.vo.Vote;
import com.oocl.com.teambuildmanagement.util.JsonUtil;
import com.oocl.com.teambuildmanagement.util.LogUtil;
import com.oocl.com.teambuildmanagement.util.OkHttpUtil;
import com.oocl.com.teambuildmanagement.util.SnackBarUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VoteActivity extends AppCompatActivity {
    LinearLayout bodyLayout;

    private Button voteBtn;
    private Button likeBtn;
    private Button collectBtn;
    private Toolbar toolbar;

    private TextView subjectTxtView;
    private TextView creatorValTxtView;
    private TextView createDateValTxtView;
    private TextView collectCountTxtView;
    private TextView likeCountTxtView;
    private TextView descriptionTxtView;


    private TeamActivity teamActivity;
    private Handler refreshUiHandler;
    private int flag = 0;
    private final int REFRESH_UI_FLAG = 1;
    private final int REFRESH_LIKE_DATA_FLAG = 3;
    private final int REFRESH_COLLECT_DATA_FLAG = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        initHandler();
        final String activityId = this.getIntent().getCharSequenceExtra("id").toString();
        System.out.println(activityId);
        this.getActivityDetailData(activityId);

        bodyLayout = (LinearLayout)findViewById(R.id.bodyLayout);
        subjectTxtView = (TextView) findViewById(R.id.subject);
        creatorValTxtView = (TextView) findViewById(R.id.creatorVal);
        createDateValTxtView = (TextView) findViewById(R.id.createDateVal);
        collectCountTxtView = (TextView) findViewById(R.id.collectCount);
        likeCountTxtView = (TextView) findViewById(R.id.likeCount);
        descriptionTxtView = (TextView) findViewById(R.id.description);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        voteBtn = (Button) findViewById(R.id.voteBtn);
        voteBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishVote(activityId);
//                Intent intent = new Intent(VoteActivity.this, CommentActivity.class);
//                intent.putExtra("id", activityId);
//                startActivity(intent);
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
    }

    public void publishVote(final String id) {
        String jsonStr = "";
        if ("single".equals(teamActivity.getVotings().get(0).getSelectionType())) {
            RadioGroup radioGroup = (RadioGroup) bodyLayout.getChildAt(1);
            RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
            String text = radioButton.getText().toString();
            Vote vote = teamActivity.getVotings().get(0);
            for (Option option : vote.getOptions()) {
                if (option.getDescription().equals(text)) {
//                    selection:[1,3]. selection:2 "{\"content\":\""+content+"\"}"
                    jsonStr = "{\"selection\":\""+option.getSequence()+"\"}";
                    break;
                }
            }
        } else {

        }
        OkHttpUtil.postByJson(HttpDict.URL_IP + HttpDict.URL_ACTIVITIES + HttpDict.URL_ACTION_VOTE + "/" + id + "/" + 0, jsonStr, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String userProfile = response.body().string();
                LogUtil.info(userProfile);
                if (response.code() == 200) {
                    LogUtil.info("navigate to vote_view page" + userProfile);
                    ///跳转到 vote view page
                    Intent intent = new Intent(VoteActivity.this, VoteViewActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });
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
            descriptionTxtView.setText(teamActivity.getVotings().get(0).getDescription());

            List<Option> optionList = teamActivity.getVotings().get(0).getOptions();

            if ("single".equals(teamActivity.getVotings().get(0).getSelectionType())){
                RadioGroup radioGroup = new RadioGroup(getApplicationContext());
                radioGroup.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams checkBoxLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                bodyLayout.addView(radioGroup, checkBoxLayoutParams);

                for (int i = 0; i < optionList.size(); i++) {
                    Option option = optionList.get(i);

                    RadioButton radioButton = new RadioButton(getApplicationContext());
                    LinearLayout.LayoutParams radioButtonLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                    radioButton.setText(option.getDescription());
                    radioButton.setTextColor(Color.rgb(0, 0, 0));
                    radioButton.setId(View.generateViewId());
                    if (i == 0) {radioButton.setChecked(true);}
                    radioGroup.addView(radioButton, radioButtonLayoutParams);
                }
            } else {
                for (Option option : optionList) {
                    LinearLayout optionLayout = new LinearLayout(getApplicationContext());
                    optionLayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams optionLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    bodyLayout.addView(optionLayout, optionLayoutParams);

                    CheckBox checkBox = new CheckBox(getApplicationContext());
                    LinearLayout.LayoutParams checkBoxLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    optionLayout.addView(checkBox, checkBoxLayoutParams);

                    TextView textView = new TextView(getApplicationContext());
                    textView.setText(option.getDescription());
                    textView.setTextColor(Color.rgb(0, 0, 0));
                    LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    optionLayout.addView(textView, textViewLayoutParams);
                }
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

    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
