package com.oocl.com.teambuildmanagement.app.vote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.home.activity.HomeActivity;
import com.oocl.com.teambuildmanagement.common.HttpDict;
import com.oocl.com.teambuildmanagement.model.vo.OptionVo;
import com.oocl.com.teambuildmanagement.model.vo.Vote;
import com.oocl.com.teambuildmanagement.model.vo.VoteVo;
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

import static android.R.attr.id;
import static android.R.interpolator.linear;
import static com.oocl.com.teambuildmanagement.R.id.subject;
import static com.oocl.com.teambuildmanagement.R.mipmap.vote;

public class VoteCreateActivity extends AppCompatActivity {
    LinearLayout mainLayout;
    LinearLayout minusL1;
    LinearLayout minusL2;
    LinearLayout plusL;
    LinearLayout bodyLayout;

    private Toolbar toolbar;

    ImageButton minusBtn1;
    ImageButton minusBtn2;
    ImageButton plusBtn;
    Button hiddenPlusBtn;

    EditText subject;
    RadioGroup radioGroup;
    EditText descriptionEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_create);

        mainLayout = (LinearLayout)findViewById(R.id.main);
        minusL1 = (LinearLayout)findViewById(R.id.minusL1);
        minusL2 = (LinearLayout)findViewById(R.id.minusL2);
        plusL = (LinearLayout)findViewById(R.id.plusL);
        bodyLayout = (LinearLayout)findViewById(R.id.bodyLayout);

        subject = (EditText)findViewById(R.id.subject);
        radioGroup = (RadioGroup)findViewById(R.id.RadioGroup);
        descriptionEditTxt = (EditText)findViewById(R.id.description);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        minusBtn1 = (ImageButton)findViewById(R.id.minusBtn1);
        minusBtn2 = (ImageButton)findViewById(R.id.minusBtn2);
        plusBtn = (ImageButton)findViewById(R.id.plusBtn);
        hiddenPlusBtn = (Button)findViewById(R.id.hiddenPlusBtn);

        minusBtn1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyLayout.removeView(minusL1);
            }
        });
        minusBtn2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyLayout.removeView(minusL2);
            }
        });
        plusBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton plusBtn = (ImageButton)findViewById(v.getId());
                plusBtn.setBackgroundResource(R.mipmap.round_close_fill);
                plusBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageButton plusBtn = (ImageButton)findViewById(v.getId());
                        LinearLayout layout = (LinearLayout)plusBtn.getParent();
                        bodyLayout.removeView(layout);
                    }
                });
                //todo click hidden button
                hiddenPlusBtn.performClick();
            }
        });
        hiddenPlusBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout plusLayout = new LinearLayout(getApplicationContext());
                plusLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams plusLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                bodyLayout.addView(plusLayout, plusLayoutParams);

                // newPlusBtn 加入到 plusLayout 中
                ImageButton newPlusBtn = new ImageButton(getApplicationContext());
                newPlusBtn.setId(View.generateViewId());
                LinearLayout.LayoutParams newPlusBtnLayoutParams=new LinearLayout.LayoutParams(80, 80);
                newPlusBtn.setLayoutParams(newPlusBtnLayoutParams);
//                imgbtn3.setImageDrawable(getResources().getDrawable(R.drawable.icon2));
//                newPlusBtn.setImageResource(R.mipmap.plus_btn);
                newPlusBtn.setBackgroundResource(R.mipmap.round_add);
                newPlusBtn.setScaleType(ImageButton.ScaleType.FIT_XY);
                plusLayout.addView(newPlusBtn);
                newPlusBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageButton plusBtn = (ImageButton)findViewById(v.getId());
                        plusBtn.setBackgroundResource(R.mipmap.round_close_fill);
                        plusBtn.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImageButton plusBtn = (ImageButton)findViewById(v.getId());
                                LinearLayout layout = (LinearLayout)plusBtn.getParent();
                                bodyLayout.removeView(layout);
                            }
                        });
                        hiddenPlusBtn.performClick();
                    }
                });

                // EditText 加入到LinearLayout 中
                EditText editText = new EditText(getApplicationContext());
                LinearLayout.LayoutParams newEditTextLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                editText.setLayoutParams(newEditTextLayoutParams);
                editText.setTextColor(Color.rgb(0, 0, 0));
//                editText.setHint("添加新选项");
                plusLayout.addView(editText);

            }
        });

        Button createVoteBtn = (Button)findViewById(R.id.createVoteBtn);
        createVoteBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishNewVote();
            }
        });
    }

    public void publishNewVote() {
        VoteVo voteVo = new VoteVo();
        List<OptionVo> optionVoList = new ArrayList<OptionVo>();
        for (int i = 3; i < bodyLayout.getChildCount(); i++) {
            LinearLayout layout = (LinearLayout)bodyLayout.getChildAt(i);
            EditText editText = (EditText)layout.getChildAt(1);
            OptionVo vo = new OptionVo();
            vo.setSequence(i - 2);
            vo.setDescription(editText.getText().toString());
            optionVoList.add(vo);
        }
        voteVo.setOptions(optionVoList);

        RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        String text = radioButton.getText().toString();
        if (text.equals("单选")) {
            voteVo.setSelectionType("single");
        } else {
            voteVo.setSelectionType("multi");
        }
        voteVo.setDescription(descriptionEditTxt.getText().toString());
        voteVo.setTitle(subject.getText().toString());

        OkHttpUtil.postByJson(HttpDict.URL_IP + HttpDict.URL_ACTIVITIES + HttpDict.URL_CREATE_VOTE , JsonUtil.toJson(voteVo), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    LogUtil.info("navigate to home page");
                    ///跳转到home page
                    Intent intent = new Intent(VoteCreateActivity.this, HomeActivity.class);
                    startActivity(intent);
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
}
