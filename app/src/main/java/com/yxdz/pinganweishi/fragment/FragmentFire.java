package com.yxdz.pinganweishi.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import com.leaf.library.StatusBarUtil;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.load.RetryNetWorkImpl;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.CustomRecyclerView;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;



import com.yxdz.pinganweishi.activity.ModuleActivity;
import com.yxdz.pinganweishi.activity.PlaceAddActivity;
import com.yxdz.pinganweishi.adapter.FirePlaceAdapter;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.FirePlaceBean;

import com.yxdz.pinganweishi.interfac.OnItemClickListener;
import com.yxdz.pinganweishi.scan.CaptureActivity;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.pinganweishi.view.NetErrorView;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 消防
 */
public class FragmentFire extends Fragment implements View.OnClickListener, XRecyclerView.LoadingListener, RetryNetWorkImpl {
    private View view;
    private CustomRecyclerView mRecyclerView;
    private LinearLayout noDataLayout;

    private List<FirePlaceBean.ListPlaceBean> listPlace = new ArrayList<>();

    private FirePlaceAdapter firePlaceAdapter;

    public final static int FragmentFireRequestCode = 1;
    public final static int FragmentFireResultCode = 1;



    private NetErrorView netErrorView;
    private EasyPopup mCirclePop;

    public TitleBarView toolbar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //引用创建好的xml布局
        view = inflater.inflate(R.layout.main_fragment_fire, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(getContext(),R.color.primarystart), ContextCompat.getColor(getContext(),R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        toolbar.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(getActivity(), toolbar);
        toolbar.setTitleBarText("瓶安卫士");
        initView();
        initData(getActivity());
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initData(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fire_no_data://无数据重新请求接口
                initData(getActivity());
                mCirclePop.showAtAnchorView(toolbar.getRightImageView(), YGravity.BELOW, XGravity.ALIGN_RIGHT, 0, 10);
                break;
        }
    }

    @Override
    public void onRefresh() {
        initData(getContext());
    }

    @Override
    public void onLoadMore() {

    }



    private void initView() {
        //无网络处理
        netErrorView = new NetErrorView.Builder(getActivity(), ((ViewGroup) view.findViewById(R.id.fire_layout))).setRetryNetWorkImpl(this).create();
        mRecyclerView = view.findViewById(R.id.fire_recyclerview);
        noDataLayout = view.findViewById(R.id.fire_no_data);
        noDataLayout.setOnClickListener(this);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.setLoadingMoreEnabled(false);


        //标题栏
        toolbar.getRightImageView().setVisibility(View.VISIBLE);
        toolbar.getRightImageView().setBackground(getResources().getDrawable(R.mipmap.add_wh));

        mCirclePop = EasyPopup.create()
                .setContentView(getContext(), R.layout.custom_dialog)
//                .setAnimationStyle(R.style.RightPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                .setFocusAndOutsideEnable(true)
                .apply();


        mCirclePop.findViewById(R.id.deviceslayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PlaceAddActivity.class));
                mCirclePop.dismiss();
            }
        });
        mCirclePop.findViewById(R.id.placelayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getContext(), PlaceQrAddActivity.class));
                startScanEcode();
                mCirclePop.dismiss();
            }
        });


        toolbar.setRightImageViewOnClickListener(new TitleBarView.RightImageViewOnClickListener() {
            @Override
            public void onRightOnClick(View v) {
                mCirclePop.showAtAnchorView(toolbar.getRightImageView(), YGravity.BELOW, XGravity.ALIGN_RIGHT, 0, 10);

            }
        });


    }

    private void startScanEcode() {
        new IntentIntegrator(getActivity())
                .setCaptureActivity(CaptureActivity.class)//打开自定义的View
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                .setPrompt("请对准二维码")// 设置提示语
                .setCameraId(0)// 选择摄像头,可使用前置或者后置
                .setBeepEnabled(true)// 是否开启声音,扫完码之后会"哔"的一声
                .setBarcodeImageEnabled(true)// 扫完码之后生成二维码的图片
                .initiateScan();// 初始化扫码
    }

    public void initData(Context context) {
        Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        map.put("placeType", "1");//场所类型（1，烟感场所；2 巡检场所)
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getFireControlQueryPlace(map), new RxSubscriber<FirePlaceBean>(context) {
            @Override
            protected void onSuccess(FirePlaceBean firePlaceBean) {
                netErrorView.removeNetErrorView();
                listPlace.clear();
                listPlace.addAll(firePlaceBean.getData().getListPlace());
                if (firePlaceBean.getData().getListPlace() != null && firePlaceBean.getData().getListPlace().size() > 0) {
                    //有数据
                    mRecyclerView.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    if (firePlaceAdapter == null) {
                        firePlaceAdapter = new FirePlaceAdapter(getActivity(), listPlace);
                        mRecyclerView.setAdapter(firePlaceAdapter);
                        firePlaceAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), ModuleActivity.class);
                                intent.putExtra("listPlaceBean", (Serializable) listPlace.get(position));
                                startActivityForResult(intent, FragmentFireRequestCode);
                            }
                        });
                    } else {
                        firePlaceAdapter.notifyDataSetChanged();
                    }
                    mRecyclerView.refreshComplete();
                } else {
                    //无数据
                    mRecyclerView.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                //message：链接超时
                LogUtils.e("onFailure=" + message);
                mRecyclerView.setPullRefreshEnabled(false);
                mRecyclerView.setLoadingMoreEnabled(false);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //网络错误e：java.net.ConnectException
                LogUtils.e("onError=" + e);
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
            }

            @Override
            public void onNetError() {
                super.onNetError();
                //无网络
                mRecyclerView.setVisibility(View.GONE);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FragmentFireRequestCode && resultCode == FragmentFireResultCode) {
            initData(getContext());
        }
        if (requestCode == 123) {
            Log.e("MainActivity", data.getStringExtra(Intents.Scan.RESULT));
            Map<String, Object> map = new HashMap<>();
            map.put("qrCode", data.getStringExtra(Intents.Scan.RESULT));
            map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
            RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).uploadQrPlace(map), new RxSubscriber<DefaultBean>(getActivity()) {
                @Override
                protected void onSuccess(DefaultBean yxdzInfo) {

                    if (yxdzInfo.getCode() == 0) {
                        if (yxdzInfo.getData() == null) {
                            ToastUtils.showShort(getActivity(), "添加失败");
                        } else {
                            ToastUtils.showShort(getActivity(), "添加成功");
                            initData(getContext());
                            Log.d("FragmentFire", yxdzInfo.getData());
                        }
                    } else {
                        ToastUtils.showShort(getActivity(), "添加失败");
                    }
                }
            });
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRecyclerView != null) {
            mRecyclerView.destroy(); // this will totally release XR's memory
            mRecyclerView = null;
        }
    }

    @Override
    public void retryNetWork() {
        initData(getActivity());
    }
}
