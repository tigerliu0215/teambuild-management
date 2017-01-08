package com.oocl.com.teambuildmanagement.app.welcome.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.home.activity.HomeActivity;

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
    }

    public void initViews(){
        iv_welcome = (ImageView)findViewById(R.id.iv_welcome);
        animation = AnimationUtils.loadAnimation(this,R.anim.anim_welcome);
        iv_welcome.setImageResource(R.mipmap.welcome);
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
}
