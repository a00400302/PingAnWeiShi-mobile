package com.yxdz.pinganweishi.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leaf.library.StatusBarUtil;
import com.yxdz.common.load.RetryNetWorkImpl;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.view.CustomRecyclerView;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.adapter.SettingAdapter;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.ServerBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class SettingActivity extends BaseHeadActivity implements XRecyclerView.LoadingListener, RetryNetWorkImpl {


    private CustomRecyclerView recyclerView;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_setting;
    }

    @Override
    protected void setContentView() {
        TitleBarView titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this, R.color.primarystart), ContextCompat.getColor(this, R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);
        initView();
        initData(this);
    }

    private void initData(final Context context) {
        recyclerView.refreshComplete();
        Request.Builder request = new Request.Builder();
        Request build = request.url("https://www.zsyuxinkeji.com/surpass/mobile/getServerList").get().build();

        Call call = RetrofitHelper.getInstance().getClient().newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                final ServerBean serverBean = gson.fromJson(response.body().string(), ServerBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SettingAdapter adapter = new SettingAdapter(serverBean.getData(), SettingActivity.this);
                        adapter.setOnliceListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, final int position) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("是否需要设置到当前选择服务器");

                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SPUtils.getInstance().put("SERVER", serverBean.getData().get(position).getServerUrl());
                                        SPUtils.getInstance().put("HTTP", serverBean.getData().get(position).getServerUrl() + "/surpass/");
                                        SPUtils.getInstance().put("HTTPS", serverBean.getData().get(position).getServerUrl() + "/surpass/");
                                        finish();
                                        dialog.dismiss();
                                        SPUtils.getInstance().getString("SERVER");
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                });
//                Log.d("SettingActivity", response.body().string());
            }
        });


    }

    private void initView() {
        recyclerView = findViewById(R.id.server_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLoadingListener(this);
        recyclerView.setLoadingMoreEnabled(false);
    }

    @Override
    protected void setTitlebarView() {

    }

    @Override
    public void onRefresh() {
        initData(this);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void retryNetWork() {

    }
}
