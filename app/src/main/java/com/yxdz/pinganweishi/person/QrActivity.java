package com.yxdz.pinganweishi.person;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.leaf.library.StatusBarUtil;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.YuXinUrl;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.FirePlaceBean;
import com.yxdz.pinganweishi.bean.QrBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.ConstantUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class QrActivity extends BaseHeadActivity {


    private ImageView qr;
    private TextView reload;
    private FirePlaceBean.ListPlaceBean listPlaceBean;
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    System.out.println("111");
                    Bitmap bmp=(Bitmap)msg.obj;
                    qr.setImageBitmap(bmp);
                    break;
            }
        };
    };
    private TitleBarView titleBarView;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_qr;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this,R.color.primarystart), ContextCompat.getColor(this,R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);
        initView();
        initData(this);
    }

    private void initView() {
        qr = findViewById(R.id.qr);
        reload = findViewById(R.id.reload);
    }

    private void initData(Context context) {
        listPlaceBean = (FirePlaceBean.ListPlaceBean) getIntent().getSerializableExtra("listPlaceBean");
        getImg();
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImg();
            }
        });

    }

    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("二维码");
    }

    public void getImg(){
        Map<String, Object> map = new HashMap<>();
        map.put("placeId", listPlaceBean.getId());
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getQrCode(map), new RxSubscriber<QrBean>(this) {

            @Override
            protected void onSuccess(final QrBean qrBean) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bmp = getURLimage(YuXinUrl.SERVER+qrBean.getData());
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = bmp;
                        System.out.println("000");
                        handle.sendMessage(msg);
                    }
                }).start();

            }
        });
    }

    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
}


