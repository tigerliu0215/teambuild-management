package com.oocl.com.teambuildmanagement.app.welcome.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.home.activity.HomeActivity;
import com.oocl.com.teambuildmanagement.common.SharedPreferenceDict;
import com.oocl.com.teambuildmanagement.util.LogUtil;
import com.oocl.com.teambuildmanagement.util.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;

/**
 * Author：Jonas Yu on 2017/1/2 01:52
 * Description:
 */
public class WelcomeActivity extends AppCompatActivity implements Animation.AnimationListener{

    private ImageView iv_welcome;
    private Animation animation;
    private Bitmap mBitmap = null;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Intent intent = new Intent(WelcomeActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
            return false;
        }
    });
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initViews();
        if(SharedPreferenceUtil.getBoolean(WelcomeActivity.this, SharedPreferenceDict.FIRST_OPEN,true)){
            LogUtil.info("createShortCut");
            createShortCut();
            SharedPreferenceUtil.putBoolean(WelcomeActivity.this,SharedPreferenceDict.FIRST_OPEN,false);
        }
    }

    public void initViews(){
        iv_welcome = (ImageView)findViewById(R.id.iv_welcome);
        animation = AnimationUtils.loadAnimation(this,R.anim.anim_welcome);
        Picasso.with(this).load("http://112.74.166.187:8443/modules/activities/client/img/mobile-welcome.jpg").into(iv_welcome);
//        iv_welcome.setImageResource(R.mipmap.welcome);
        animation.setFillEnabled(true); //启动Fill保持
        animation.setFillAfter(true);  //设置动画的最后一帧是保持在View上面
        iv_welcome.setAnimation(animation);
        animation.setAnimationListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        handler.sendEmptyMessageDelayed(1,2000);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void createShortCut(){
        //创建快捷方式的Intent
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //不允许重复创建
        shortcutintent.putExtra("duplicate", false);
        //需要现实的名称
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        //快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(WelcomeActivity.this, R.mipmap.app);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        //点击快捷图片，运行的程序主入口
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(WelcomeActivity.this, WelcomeActivity.class));
        //发送广播。OK
        sendBroadcast(shortcutintent);
    }
}
