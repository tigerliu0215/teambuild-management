package com.oocl.com.teambuildmanagement.app.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.home.activity.HomeActivity;
import com.oocl.com.teambuildmanagement.app.login.activity.LoginActivity;
import com.oocl.com.teambuildmanagement.app.myCollect.activity.MyCollectActivity;
import com.oocl.com.teambuildmanagement.app.myVote.activity.MyVoteActivity;
import com.oocl.com.teambuildmanagement.app.welcome.activity.WelcomeActivity;
import com.oocl.com.teambuildmanagement.common.SharedPreferenceDict;
import com.oocl.com.teambuildmanagement.util.SharedPreferenceUtil;

/**
 * Author：Jonas Yu on 2017/1/8 23:10
 * Description:
 */
public class MineFragment extends Fragment implements View.OnClickListener{
    private View view;
    private LinearLayout ll_logout;
    private LinearLayout ll_login;
    private Button btn_login;
    private TextView tv_my_collect;
    private TextView tv_my_votes;
    private TextView tv_login_out;
    public static final int LOGIN_REQUEST_CODE = 31;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, container, false);
            initViews();
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        refreshUI();
        return view;
    }

    public void initViews(){
        ll_logout = (LinearLayout)view.findViewById(R.id.ll_logout);
        ll_login = (LinearLayout)view.findViewById(R.id.ll_login);
        btn_login = (Button)view.findViewById(R.id.btn_login);
        tv_my_collect = (TextView)view.findViewById(R.id.tv_my_collect);
        tv_my_votes = (TextView)view.findViewById(R.id.tv_my_votes);
        tv_login_out = (TextView)view.findViewById(R.id.tv_login_out);

        btn_login.setOnClickListener(this);
        tv_my_collect.setOnClickListener(this);
        tv_my_votes.setOnClickListener(this);
        tv_login_out.setOnClickListener(this);
    }

    public void refreshUI(){
        if(SharedPreferenceUtil.getBoolean(getContext(),SharedPreferenceDict.LOGIN_STATUS,false)){
            ll_login.setVisibility(View.VISIBLE);
            ll_logout.setVisibility(View.GONE);
        }else{
            ll_logout.setVisibility(View.VISIBLE);
            ll_login.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivityForResult(intent,31);
                break;
            case R.id.tv_my_collect:
                Intent myCollectIntent = new Intent(getContext(),MyCollectActivity.class);
                startActivity(myCollectIntent);
                break;
            case R.id.tv_my_votes:
                Intent myVoteIntent = new Intent(getContext(),MyVoteActivity.class);
                startActivity(myVoteIntent);
                break;
            case R.id.tv_login_out:
                SharedPreferenceUtil.remove(getContext(),SharedPreferenceDict.USER_SESSION_COOKIE);
                SharedPreferenceUtil.remove(getContext(),SharedPreferenceDict.LOGIN_STATUS);
                SharedPreferenceUtil.remove(getContext(),SharedPreferenceDict.USER_PROFILE);
                refreshUI();
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(LOGIN_REQUEST_CODE == requestCode && LOGIN_REQUEST_CODE == resultCode){
            refreshUI();
        }
    }
}
