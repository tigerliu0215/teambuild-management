package com.oocl.com.teambuildmanagement.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.activity.detail.ActivityDetailActivity;
import com.oocl.com.teambuildmanagement.model.vo.AD;
import com.oocl.com.teambuildmanagement.util.ImageUtil;
import com.oocl.com.teambuildmanagement.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YUJO2 on 1/12/2017.
 */

public class ADView extends RelativeLayout {

    private List<View> viewList = null;//广告图片
    private boolean isLoop = false;//滚动标识
    private int adPicPage = 0;//当前图片页
    LinearLayout ll_point;//小圆点
    private List<ImageView> ivList = null;//小圆点
    private List<AD> listAD = null;
    private ViewPager vp_ad;
    private Context context = null;
    private final int time = 5 * 1000;

    private int index = 0;

    public ADView(Context context) {
        super(context);
        this.context = context;
        init(context);
    }

    public ADView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context);
    }

    public ADView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.ad, null);
        ll_point = (LinearLayout) view.findViewById(R.id.ll_point);
        vp_ad = (ViewPager) view.findViewById(R.id.vp_ad);
        addView(view);
    }

    public ViewPager getVp() {
        return vp_ad;
    }

    public void setADPic(List<AD> listAD) {
        setADPic(listAD, ImageView.ScaleType.FIT_XY,1);
    }

    public void setADPic(List<AD> listAD,int type) {
        setADPic(listAD, ImageView.ScaleType.FIT_XY,type);
    }

    /**
     * 设置广告图片
     *
     * @param scaleType
     */
    public void setADPic(List<AD> listAD, ImageView.ScaleType scaleType,int type) {
        vp_ad.setAdapter(null);
        this.listAD = listAD;
        if (listAD != null) {
            viewList = new ArrayList<View>();
            ivList = new ArrayList<ImageView>();
            ll_point.removeAllViews();
            for (int i = 0; i < listAD.size(); i++) {
                final AD ad = listAD.get(i);
                View view = LayoutInflater.from(getContext()).inflate(R.layout.ad_pic, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_ad_pic);

                //调整图片比例尺寸
//                ViewGroup.LayoutParams lp = imageView.getLayoutParams();
//                switch (type){
//                    case 1:
//                        ScaleUtils.getAD((Activity) getContext(), lp);
//                        break;
//                    case 2:
//                        ScaleUtils.getDetailAD((Activity) getContext(), lp);
//                        imageView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
//                        break;
//                }
//                imageView.setLayoutParams(lp);
//                imageView.setScaleType(scaleType);
                // 加载网络图片
//                if (StringUtils.isNotEmpty(listAD.get(i).getPicUrl())) {
//                    ImageUtil.setImage(imageView, ad.getPicUrl());
//                }
                ImageUtil.show(getContext(),ad.getLink(),imageView);
                viewList.add(view);
                View viewPoint = LayoutInflater.from(getContext()).inflate(R.layout.ad_point, null);
                ImageView iv_point = (ImageView) viewPoint.findViewById(R.id.iv_point);
                if (i == 0)
                    iv_point.setImageResource(R.mipmap.product_dot1);
                ll_point.addView(iv_point);
                ivList.add(iv_point);
            }
            vp_ad.setAdapter(new PagerAdapter() {

                @Override
                public boolean isViewFromObject(View arg0, Object arg1) {

                    return arg0 == arg1;
                }


                @Override
                public int getCount() {
                    return viewList.size();
                }

                @Override
                public void destroyItem(ViewGroup container, int position,
                                        Object object) {
                    container.removeView(viewList.get(position));
                }


                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    View view = viewList.get(position);
                    container.addView(view);
                    return view;
                }

            });

            vp_ad.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i2) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < ivList.size(); i++) {
                        ivList.get(i).setImageResource(R.mipmap.product_dot2);
                    }
                    ivList.get(position).setImageResource(R.mipmap.product_dot1);
                    index = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    public String openUrl() {
        if (listAD == null) {
            return null;
        }
        final AD ad = listAD.get(index);
        LogUtil.info("ad index " + index);
        LogUtil.info("ad url " + ad.getLink());
        return ad.getActivityId();
    }

    public void start() {
        if (!isLoop) {
            isLoop = true;
            handler.sendEmptyMessageDelayed(0, time);
        }
    }

    public void stop() {
        isLoop = false;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (isLoop) {
                        adPicPage = vp_ad.getCurrentItem() + 1;
                        if (adPicPage >= viewList.size()) {
                            adPicPage = 0;
                        }
                        vp_ad.setCurrentItem(adPicPage);

                        handler.sendEmptyMessageDelayed(0, time);
                    }
                    break;
            }
        }
    };


}
