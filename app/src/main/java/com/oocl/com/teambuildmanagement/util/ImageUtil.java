package com.oocl.com.teambuildmanagement.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Author：Jonas Yu on 2017/1/8 22:34
 * Description:
 */
public class ImageUtil {
    public static void show(Context context,String url,ImageView view){
        Picasso.with(context).load(url).into(view);
    }
}
