package com.yxdz.common.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast = null; //Toast的对象！

    public static void showShort(Context mContext, String id) {
        if (toast == null) {
            toast = Toast.makeText(mContext, null, Toast.LENGTH_SHORT);
            toast.setText(id);
        }
        else {
            toast.setText(id);
        }
        toast.show();
    }

    public static void showLong(Context mContext, String id) {

        if (toast == null) {
            toast = Toast.makeText(mContext, null, Toast.LENGTH_LONG);
            toast.setText(id);
        }
        else {
            toast.setText(id);
        }
        toast.show();
    }

}
