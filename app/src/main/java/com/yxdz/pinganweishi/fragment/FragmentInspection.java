package com.yxdz.pinganweishi.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.yxdz.pinganweishi.activity.InspectionDeviceAty;
import com.yxdz.pinganweishi.adapter.InspectionPlaceAdapter;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.bean.FireAreaBean;
import com.yxdz.pinganweishi.bean.FirePlaceBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.pinganweishi.view.NetErrorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 巡检
 */
public class FragmentInspection extends Fragment implements View.OnClickListener, XRecyclerView.LoadingListener, RetryNetWorkImpl {

    private TitleBarView toolbar;
    private View view;
    private CustomRecyclerView mRecyclerView;
    private LinearLayout noDataLayout;

    private List<FirePlaceBean.ListPlaceBean> listPlace = new ArrayList<>();

    private InspectionPlaceAdapter inspectionPlaceAdapter;

    //区域选择
    private String areaId = "";

    private NetErrorView netErrorView;
    private ArrayList<Object> strings;
    private List<FireAreaBean.DataBean.ListAreaBean> listArea;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //引用创建好的xml布局
        view = inflater.inflate(R.layout.main_fragment_inspection, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(getContext(),R.color.primarystart), ContextCompat.getColor(getContext(),R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        toolbar.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(getActivity(), toolbar);
        toolbar.setTitleBarText("巡检");
        setStrings();
        toolbar.getRightImageView().setVisibility(View.VISIBLE);
//        toolbar.getRightImageView().setBackground(getResources().getDrawable(R.mipmap.place_marker_wh));
//        toolbar.getRightImageView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (UserInfoBean.getInstance().getUserType() != 100) {
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//                    final AlertDialog alertDialog = alertDialogBuilder.create();
//                    alertDialog.show();
//                    alertDialog.getWindow().setLayout(900, 650);
//                    alertDialog.getWindow().setContentView(R.layout.place_type_dialog);
//                    final WheelView<Object> wheelView = alertDialog.getWindow().findViewById(R.id.wheelview);
//                    TextView check = alertDialog.getWindow().findViewById(R.id.check);
//                    wheelView.setData(strings);
//                    check.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            for (FireAreaBean.DataBean.ListAreaBean listAreaBean : listArea) {
//                                if (listAreaBean.getName().equals(wheelView.getSelectedItemData().toString())) {
//                                    initData(getActivity(), String.valueOf(listAreaBean.getAreaId()));
//                                }
//                            }
//                            alertDialog.dismiss();
//                        }
//                    });
//                } else {
//                    Toast.makeText(getActivity(), "当前用户不是管理员", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        initView();
        initData(getActivity(), areaId);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.inspection_no_data) {//无数据重新请求接口
            initData(getActivity(), areaId);
        }
    }

    @Override
    public void onRefresh() {
        initData(null, areaId);
    }

    @Override
    public void onLoadMore() {
        mRecyclerView.setNoMore(true);
    }

    @Override
    public void retryNetWork() {
        initData(getActivity(), areaId);
    }


    private void initView() {
        //无网络处理
        netErrorView = new NetErrorView.Builder(getActivity(), ((ViewGroup) view.findViewById(R.id.inspection_layout))).setRetryNetWorkImpl(this).create();

        mRecyclerView = view.findViewById(R.id.inspection_recyclerview);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.setLoadingMoreEnabled(false);
        noDataLayout = view.findViewById(R.id.inspection_no_data);
        noDataLayout.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData(getActivity(), areaId);
    }

    public void initData(Context context, final String areaId) {
        //MainActivity中调用，传入areaId
        this.areaId = areaId;

        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(areaId)) {
            map.put(ActValue.AreaId, areaId);
        }
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
//        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        map.put(ActValue.PlaceType, "2");//场所类型（1，烟感场所；2 巡检场所）
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
                    if (inspectionPlaceAdapter == null) {
                        inspectionPlaceAdapter = new InspectionPlaceAdapter(getActivity(), listPlace);
                        mRecyclerView.setAdapter(inspectionPlaceAdapter);
                        inspectionPlaceAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), InspectionDeviceAty.class);
                                intent.putExtra("id", listPlace.get(position).getId());
                                intent.putExtra("placeName", listPlace.get(position).getPlaceName());
                                startActivity(intent);
                            }
                        });
                    } else {
                        inspectionPlaceAdapter.notifyDataSetChanged();
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


    public void setStrings() {
        final Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getFireControlSmokeAreaList(map), new RxSubscriber<FireAreaBean>(getActivity()) {


            @Override
            protected void onSuccess(FireAreaBean fireAreaBean) {
                if (fireAreaBean.getCode() == 0) {
                    listArea = fireAreaBean.getData().getListArea();
                    strings = new ArrayList<>();
                    for (FireAreaBean.DataBean.ListAreaBean listAreaBean : listArea) {
                        strings.add(listAreaBean.getName());
                    }
                } else {
                    ToastUtils.showShort(getActivity(), "区域列表获取失败");
                }
            }
        });
    }
}
