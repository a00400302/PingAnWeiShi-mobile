package com.yxdz.pinganweishi.activity;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leaf.library.StatusBarUtil;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.load.RetryNetWorkImpl;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.view.CustomRecyclerView;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.adapter.GateAdapter;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.GateBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GateRecordActivity extends BaseHeadActivity implements XRecyclerView.LoadingListener, RetryNetWorkImpl {


    private TitleBarView titleBarView;
    private CustomRecyclerView recordlist;
    private LinearLayout noDataLayout;
    private List<GateBean.DataBean> gateBeans;
    private GateAdapter gateAdapter;
    private Button btnCloseGate;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initView() {
        recordlist = findViewById(R.id.record_view);
        noDataLayout = findViewById(R.id.fire_device_no_data);
        btnCloseGate = findViewById(R.id.btnCloseGate);
        gateBeans = new ArrayList<>();
    }

    private void initData(final Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GateRecordActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recordlist.setLayoutManager(linearLayoutManager);
        recordlist.setLoadingListener(this);
        recordlist.setLoadingMoreEnabled(false);


        String SN = getIntent().getStringExtra("SN");
        btnCloseGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SN = getIntent().getStringExtra("SN");
                Map<String, Object> map = new HashMap<>();
                map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                map.put("sn", SN);
                RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).remoteShutDown(map), new RxSubscriber<DefaultBean>(context) {
                    @Override
                    protected void onSuccess(DefaultBean defaultBean) {
                        Toast.makeText(GateRecordActivity.this, defaultBean.getData(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //网络错误e：java.net.ConnectException
                        LogUtils.e("onError=" + e);
                        recordlist.refreshComplete();
                        recordlist.loadMoreComplete();
                    }
                });
            }
        });


        Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
//        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        map.put("sn", SN);
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getRecordBySn(map), new RxSubscriber<GateBean>(context) {
            @Override
            protected void onSuccess(GateBean gateBean) {
//                LogUtils.e("gateBean=" + gateBean);
                netErrorView.removeNetErrorView();
                gateBeans.clear();
                gateBeans.addAll(gateBean.getData());
                if (gateBeans != null && gateBeans.size() > 0) {
                    //有数据
                    recordlist.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    if (gateAdapter == null) {
                        gateAdapter = new GateAdapter(getApplicationContext(), gateBeans);
                        recordlist.setAdapter(gateAdapter);
                    } else {
                        gateAdapter.notifyDataSetChanged();
                    }
                    recordlist.refreshComplete();
                } else {
                    //无数据
                    recordlist.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                //message：链接超时
                LogUtils.e("onFailure=" + message);
                recordlist.setPullRefreshEnabled(false);
                recordlist.setLoadingMoreEnabled(false);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //网络错误e：java.net.ConnectException
                LogUtils.e("onError=" + e);
                recordlist.refreshComplete();
                recordlist.loadMoreComplete();
            }

            @Override
            public void onNetError() {
                super.onNetError();
                //无网络
                recordlist.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.GONE);
                netErrorView.addNetErrorView();
            }

            @Override
            protected void onOtherError(String code) {
                super.onOtherError(code);
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
            }
        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.content_gate_record;
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

    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("阀门记录");
    }

    @Override
    public void onRefresh() {
        initData(GateRecordActivity.this);
    }

    @Override
    public void onLoadMore() {
        initData(GateRecordActivity.this);
    }

    @Override
    public void retryNetWork() {
        initData(GateRecordActivity.this);
    }
}
