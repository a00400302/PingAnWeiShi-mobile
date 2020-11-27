package com.yxdz.http.utils;

import android.content.Context;

import com.yxdz.common.utils.LogUtils;
import com.yxdz.http.R;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class HttpsUtils {


    public  static SSLSocketFactory getSSLSocketFactory(Context context) {

//        final String CLIENT_TRUST_PASSWORD = ".zsyxdz123456";//信任证书密码，该证书默认密码是123456
        final String CLIENT_TRUST_PASSWORD = "83tsy0h7";//信任证书密码，该证书默认密码是123456
        final String CLIENT_AGREEMENT = "SSL";//使用协议
        final String CLIENT_TRUST_KEYSTORE = "pfx";
//        final String CLIENT_TRUST_KEYSTORE = "CER";
        SSLContext sslContext = null;
        try {
            //取得SSL的SSLContext实例
            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
            //取得TrustManagerFactory的X509密钥管理器实例
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            //取得BKS密库实例
            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
            InputStream is = context.getResources().openRawResource(R.raw.zfefires);
            try {
                tks.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
            } finally {
                is.close();
            }
            //初始化密钥管理器
            trustManager.init(tks);
            //初始化SSLContext
            sslContext.init(null, trustManager.getTrustManagers(), null);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e( e.getMessage());
        }
        return sslContext.getSocketFactory();
    }




}
