package com.oocl.com.teambuildmanagement.app.vote;

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
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.common.HttpDict;
import com.oocl.com.teambuildmanagement.model.vo.Option;
import com.oocl.com.teambuildmanagement.model.vo.TeamActivity;
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

public class VoteViewActivity extends AppCompatActivity {

    private Button likeBtn;
    private Button collectBtn;
    private Toolbar toolbar;

    private TextView subjectTxtView;
    private TextView creatorValTxtView;
    private TextView createDateValTxtView;
    private TextView collectCountTxtView;
    private TextView likeCountTxtView;
    private TextView descriptionTxtView;
    private BarChart chart;


    private TeamActivity teamActivity;
    private Handler refreshUiHandler;
    private int flag = 0;
    private final int REFRESH_UI_FLAG = 1;
    private final int REFRESH_LIKE_DATA_FLAG = 3;
    private final int REFRESH_COLLECT_DATA_FLAG = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_view);

        initHandler();
        final String activityId = this.getIntent().getCharSequenceExtra("id").toString();
        System.out.println(activityId);
        this.getActivityDetailData(activityId);

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

        //画柱状图
        chart = (BarChart) findViewById(R.id.chart);

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
            BarData data = new BarData(getXAxisValues(optionList), getDataSet(optionList));
            chart.setData(data);
            chart.setDescription("");
            chart.animateXY(2000, 2000);
            chart.invalidate();

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

    private ArrayList<BarDataSet> getDataSet(List<Option> optionList) {
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        ArrayList<BarEntry> valueSet = new ArrayList<>();
        for (int i = 0; i < optionList.size(); i++) {
            Option option = optionList.get(optionList.size() - 1 - i);
            BarEntry barEntry = new BarEntry(option.getVoteDetails().size(), i);
            valueSet.add(barEntry);
        }
        BarDataSet barDataSet = new BarDataSet(valueSet, "人数");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //设置柱状上方数值的格式
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value,
                                            com.github.mikephil.charting.data.Entry entry,
                                            int dataSetIndex, ViewPortHandler viewPortHandler) {
                int n = (int) value;
                return n + "";
            }
        });

        dataSets.add(barDataSet);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues(List<Option> optionList) {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < optionList.size(); i++)  {
            Option option = optionList.get(optionList.size() - 1 - i);
            xAxis.add(option.getDescription());
        }
        return xAxis;
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

    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
