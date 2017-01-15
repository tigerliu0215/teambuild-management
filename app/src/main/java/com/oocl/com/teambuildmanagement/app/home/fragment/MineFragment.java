package com.oocl.com.teambuildmanagement.app.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.login.activity.LoginActivity;
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
        return view;
    }

    public void initViews(){
        ll_logout = (LinearLayout)view.findViewById(R.id.ll_logout);
        ll_login = (LinearLayout)view.findViewById(R.id.ll_login);
        btn_login = (Button)view.findViewById(R.id.btn_login);
        tv_my_collect = (TextView)view.findViewById(R.id.tv_my_collect);
        tv_my_votes = (TextView)view.findViewById(R.id.tv_my_votes);
        tv_login_out = (TextView)view.findViewById(R.id.tv_login_out);

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
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_my_collect:
                break;
            case R.id.tv_my_votes:
                break;
            case R.id.tv_login_out:
                break;

        }
    }
}
