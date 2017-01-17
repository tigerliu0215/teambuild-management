package com.oocl.com.teambuildmanagement.app.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.home.fragment.HomeFragment;
import com.oocl.com.teambuildmanagement.app.home.fragment.MineFragment;
import com.oocl.com.teambuildmanagement.app.vote.VoteCreateActivity;

/**
 * Author：Jonas Yu on 2017/1/2 02:47
 * Description:
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView tvToolTitle;

    private HomeFragment homeFragment;
    private MineFragment mineFragment;

    private FrameLayout fl_home;
    private FrameLayout fl_mine;
    private FrameLayout fl_activity;

    private ImageView iv_home;
    private ImageView iv_activity;
    private ImageView iv_mine;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        fl_home.setSelected(true);
        chooseFragment(homeFragment);
    }
    public void initFragments(){
        homeFragment = new HomeFragment();
        mineFragment = new MineFragment();
    }
    public void initViews(){
        initToolBar();
        initFragments();

        fl_home = (FrameLayout)findViewById(R.id.fl_home);
        fl_mine = (FrameLayout)findViewById(R.id.fl_mine);
        fl_activity = (FrameLayout)findViewById(R.id.fl_activity);

        iv_home = (ImageView)findViewById(R.id.iv_home);
        iv_activity = (ImageView)findViewById(R.id.iv_activity);
        iv_mine = (ImageView)findViewById(R.id.iv_mine);

        fl_home.setOnClickListener(this);
        fl_mine.setOnClickListener(this);
        fl_activity.setOnClickListener(this);
    }
    public void initToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolTitle = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void chooseFragment(Fragment fragment){
        if (!fragment.isAdded()) {
            //得到Fragment事务管理器
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            //替换当前的页面
            fragmentTransaction.replace(R.id.frame_content, fragment);
            //事务管理提交
            fragmentTransaction.commit();
        }
    }

    private void clearAllChoose() {
        fl_home.setSelected(false);
        iv_home.setSelected(false);

        fl_activity.setSelected(false);
        fl_activity.setSelected(false);

        fl_mine.setSelected(false);
        iv_mine.setSelected(false);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.fl_home:
                clearAllChoose();
                fl_home.setSelected(true);
                tvToolTitle.setText(getResources().getString(R.string.home_fragment_title));
                chooseFragment(homeFragment);
                break;
            case R.id.fl_mine:
                clearAllChoose();
                fl_mine.setSelected(true);
                tvToolTitle.setText(getResources().getString(R.string.mine_fragment_title));
                chooseFragment(mineFragment);
                break;
            case R.id.fl_activity:
                Intent intent = new Intent(view.getContext(), VoteCreateActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
