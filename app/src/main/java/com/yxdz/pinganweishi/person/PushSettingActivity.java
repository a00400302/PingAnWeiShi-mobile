package com.yxdz.pinganweishi.person;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;

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
import com.yxdz.pinganweishi.adapter.PushSettingAdapter;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.FirePlaceBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushSettingActivity extends BaseHeadActivity implements XRecyclerView.LoadingListener, RetryNetWorkImpl {


    CustomRecyclerView placeEditList;
    private PushSettingAdapter pushSettingAdapter;

    private String areaId = "";
    private LinearLayout noDataLayout;
    private List<FirePlaceBean.ListPlaceBean> listPlace = new ArrayList<>();
    private TitleBarView titleBarView;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_push_setting;
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
        initData(this, null);
    }

    private void initView() {
        noDataLayout = findViewById(R.id.fire_no_data);
        placeEditList = findViewById(R.id.place_edit_list);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        placeEditList.setLayoutManager(linearLayoutManager);
        placeEditList.setLoadingListener(this);
        placeEditList.setLoadingMoreEnabled(false);
    }

    private void initData(Context context, final String areaId) {
        this.areaId = areaId;
        final Map<String, Object> map = new HashMap<>();
//        if (!TextUtils.isEmpty(areaId)) {
//            map.put(ActValue.AreaId, areaId);
//        }
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        map.put("placeType", "1");//场所类型（1，烟感场所；2 巡检场所）
            RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getFireControlQueryPlace(map), new RxSubscriber<FirePlaceBean>(context) {
            @Override
            protected void onSuccess(final FirePlaceBean firePlaceBean) {
                netErrorView.removeNetErrorView();
                listPlace.clear();
                listPlace.addAll(firePlaceBean.getData().getListPlace());
                if (firePlaceBean.getData().getListPlace() != null && firePlaceBean.getData().getListPlace().size() > 0) {
                    //有数据
                    placeEditList.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    if (pushSettingAdapter == null) {
                        pushSettingAdapter = new PushSettingAdapter(PushSettingActivity.this, listPlace);
                        placeEditList.setAdapter(pushSettingAdapter);
                    } else {
                        pushSettingAdapter.notifyDataSetChanged();
                    }
                    placeEditList.refreshComplete();
                } else {
                    //无数据
                    placeEditList.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                //message：链接超时
                LogUtils.e("onFailure=" + message);
                placeEditList.setPullRefreshEnabled(false);
                placeEditList.setLoadingMoreEnabled(false);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //网络错误e：java.net.ConnectException
                LogUtils.e("onError=" + e);
                placeEditList.refreshComplete();
                placeEditList.loadMoreComplete();
            }

            @Override
            public void onNetError() {
                super.onNetError();
                //无网络
                placeEditList.setVisibility(View.GONE);
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
        titleBarView.setTitleBarText("推送设置");
    }

    @Override
    public void onRefresh() {
        initData(null, "refresh");
    }

    @Override
    public void onLoadMore() {
        placeEditList.setNoMore(true);
    }

    @Override
    public void retryNetWork() {
        initData(this, areaId);
    }
}



