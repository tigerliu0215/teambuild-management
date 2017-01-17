package com.oocl.com.teambuildmanagement.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.oocl.com.teambuildmanagement.R;
import com.oocl.com.teambuildmanagement.app.login.activity.LoginActivity;

/**
 * Created by YUJO2 on 1/17/2017.
 */

public class DialogUtil {

    public static void showLoginDialog(final AppCompatActivity context, String title){
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle(title).setIcon(R.mipmap.app)
                .setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivityForResult(intent,1);
                    }
                })
                .setMessage("立即跳转至登录页面？").create();
        dialog.show();
    }

    public static void showDialog(Context context,String title,String content){
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle(title).setIcon(R.mipmap.app)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setMessage(content).create();
        dialog.show();
    }
}
