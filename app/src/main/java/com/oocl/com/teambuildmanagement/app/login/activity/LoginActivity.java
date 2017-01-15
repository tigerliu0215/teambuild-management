package com.oocl.com.teambuildmanagement.app.login.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.home.fragment.MineFragment;
import com.oocl.com.teambuildmanagement.common.HttpDict;
import com.oocl.com.teambuildmanagement.common.SharedPreferenceDict;
import com.oocl.com.teambuildmanagement.model.request.LoginRequest;
import com.oocl.com.teambuildmanagement.util.JsonUtil;
import com.oocl.com.teambuildmanagement.util.LogUtil;
import com.oocl.com.teambuildmanagement.util.OkHttpUtil;
import com.oocl.com.teambuildmanagement.util.SharedPreferenceUtil;
import com.oocl.com.teambuildmanagement.util.SnackBarUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.oocl.com.teambuildmanagement.R.id.login;

/**
 * Created by YUJO2 on 1/12/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private ProgressDialog pd;
    private Handler handler;
    private final int LOGIN_SUCC = 1;
    private final int LOGIN_FAIL = 2;
    private String username;
    private String password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    public void initViews(){

        et_username = (EditText)findViewById(R.id.et_username);
        et_username.setText(SharedPreferenceUtil.getString(LoginActivity.this,SharedPreferenceDict.USER_NAME,""));
        et_password = (EditText)findViewById(R.id.et_password);
        et_password.setText(SharedPreferenceUtil.getString(LoginActivity.this,SharedPreferenceDict.PASSWORD,""));
        btn_login = (Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pd.dismiss();
                switch (msg.what){
                    case LOGIN_SUCC:
                        SharedPreferenceUtil.putString(LoginActivity.this, SharedPreferenceDict.USER_NAME,username);
                        SharedPreferenceUtil.putString(LoginActivity.this, SharedPreferenceDict.PASSWORD,password);
                        setResult(MineFragment.LOGIN_REQUEST_CODE);
                        finish();
                        break;
                    case LOGIN_FAIL:
                        SnackBarUtil.showSanckBarUtil(btn_login,getResources().getString(R.string.login_fail));
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                if(null != username && !username.equals("") && null != password && !password.equals("")){
                    pd = ProgressDialog.show(LoginActivity.this, "Login", "正在登录中....");
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setUsernameOrEmail(username);
                    loginRequest.setPassword(password);
                    OkHttpUtil.postByJson(HttpDict.URL_IP + HttpDict.URL_AUTH_SIGNIN, JsonUtil.toJson(loginRequest), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            handler.sendEmptyMessage(2);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            LogUtil.info("login response.code " + response.code());
                            String userProfile = response.body().string();
                            LogUtil.info(userProfile);
                            if(response.code() == 200){
                                LogUtil.info("login " + userProfile);
                                if(null != response.header("set-cookie")){
                                    String[] cookieArr = response.header("set-cookie").split(";");
                                    if(null != cookieArr && cookieArr.length > 0){

                                        SharedPreferenceUtil.putBoolean(LoginActivity.this, SharedPreferenceDict.LOGIN_STATUS,true);
                                        SharedPreferenceUtil.putString(LoginActivity.this, SharedPreferenceDict.USER_SESSION_COOKIE,cookieArr[0]);
                                        SharedPreferenceUtil.putBoolean(LoginActivity.this, SharedPreferenceDict.LOGIN_STATUS,true);
                                        handler.sendEmptyMessage(1);
                                        return;
                                    }
                                }
                            }
                            handler.sendEmptyMessage(2);
                        }
                    });
                }else{
                    SnackBarUtil.showSanckBarUtil(btn_login,getResources().getString(R.string.usernameOrPassword_empty));
                }
                break;
        }
    }
}
