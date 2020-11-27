package com.yxdz.pinganweishi.utils;

import android.content.Context;
import android.content.Intent;

import com.yxdz.common.AppManager;
import com.yxdz.pinganweishi.ui.login.LoginActivity;

/**
 * @ClassName: ${CLASS_NAME}
 * @Desription:
 * @author: Dreamcoding
 * @date: 2018/9/5
 */
public class AppCommonUtil {

    public static void doOtherRespone(Context context,String code){
        if (ConstantUtils.RESPONE_CODE_NO_UDER.equals(code) || ConstantUtils.RESPONE_CODE_TOKEN_EXPIRED.equals(code)){
            AppManager.getAppManager().finishAllActivity();
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

}
