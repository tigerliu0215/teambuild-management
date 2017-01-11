package com.oocl.com.teambuildmanagement.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by YUJO2 on 1/11/2017.
 */

public class SnackBarUtil {
    public static void showSanckBarUtil(View view, String text){
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }
}
