package com.yxdz.pinganweishi.activity;

import android.content.Context;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leaf.library.StatusBarUtil;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.load.RetryNetWorkImpl;
import com.yxdz.common.utils.LogUtils;

import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.adapter.MagnetismBeanListAdapter;
import com.yxdz.pinganweishi.api.LockApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.MagnetismBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagnetismActivity extends BaseHeadActivity implements XRecyclerView.LoadingListener, RetryNetWorkImpl {



    private SwipeRecyclerView lockList;
    private int placeId;
    private LinearLayout noDataLayout;
    private List<MagnetismBean.DataBean.LockListBean> listPlace = new ArrayList<>();
    private MagnetismBeanListAdapter doorLockListAdapter;
    private SwipeRefreshLayout refreshLayout;
    private TitleBarView titleBarView;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_magnetism;
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
        lockList = findViewById(R.id.lockList);
        noDataLayout = findViewById(R.id.fire_no_data);

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(MagnetismActivity.this);
            }
        });
        refreshLayout.setColorSchemeResources(R.color.fire_control_theme);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MagnetismActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lockList.setLayoutManager(linearLayoutManager);
    }

    private void initData(final Context context) {
        placeId = getIntent().getIntExtra("placeId", 0);
        final Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        RetrofitHelper.subscriber(RetrofitHelper.Https(LockApi.class).getCtrInfo(map), new RxSubscriber<MagnetismBean>(this) {
            @Override
            protected void onSuccess(MagnetismBean magnetismBean) {
                if (magnetismBean.getData().getLockList().getName() != null) {
                    netErrorView.removeNetErrorView();
                    listPlace.clear();
                    listPlace.add(magnetismBean.getData().getLockList());
                    if (magnetismBean.getData().getLockList() != null) {
                        //有数据
                        lockList.setVisibility(View.VISIBLE);
                        noDataLayout.setVisibility(View.GONE);
                        if (doorLockListAdapter == null) {
                            doorLockListAdapter = new MagnetismBeanListAdapter(context, listPlace);
                            doorLockListAdapter.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                }
                            });
                            doorLockListAdapter.setonDoorClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    RetrofitHelper.subscriber(RetrofitHelper.Https(LockApi.class).remoteDoorOpening(map), new RxSubscriber<DefaultBean>(MagnetismActivity.this) {

                                        @Override
                                        protected void onSuccess(DefaultBean defaultBean) {
                                            Toast.makeText(MagnetismActivity.this, defaultBean.getData(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                            lockList.setAdapter(doorLockListAdapter);
                        } else {
                            doorLockListAdapter.notifyDataSetChanged();
                        }
                    } else {
                        //无数据
                        lockList.setVisibility(View.GONE);
                        noDataLayout.setVisibility(View.VISIBLE);
                    }
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                }else{
                    lockList.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                //message：链接超时
                LogUtils.e("onFailure=" + message);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                noDataLayout.setVisibility(View.VISIBLE);
                LogUtils.e("onError=" + e);
            }

            @Override
            public void onNetError() {
                super.onNetError();
                //无网络
                lockList.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.GONE);

                /**
                 * 防止重复添加 netErrorView,
                 * 否则会报 Caused by: java.lang.IllegalStateException:
                 *   The specified child already has a parent.
                 */
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
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("门磁列表");
    }

    @Override
    public void onRefresh() {
        initData(null);
    }

    @Override
    public void onLoadMore() {
        //        lockList.setNoMore(true);
    }

    @Override
    public void retryNetWork() {
        initData(this);
    }
}
