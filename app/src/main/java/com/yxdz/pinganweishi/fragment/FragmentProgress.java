package com.yxdz.pinganweishi.fragment;


import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.load.RetryNetWorkImpl;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.view.CustomRecyclerView;

import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.adapter.InspectionRecordAdapter;
import com.yxdz.pinganweishi.api.InspectionApi;
import com.yxdz.pinganweishi.bean.EventBusBean;
import com.yxdz.pinganweishi.bean.InspectionRecordBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.pinganweishi.view.NetErrorView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进度
 */
public class FragmentProgress extends Fragment implements View.OnClickListener, XRecyclerView.LoadingListener, RetryNetWorkImpl {

    private View view;
    private static final String TYPE = "type";
    private static final String ID = "id";
    private CustomRecyclerView mRecyclerView;
    private LinearLayout noDataLayout;
    private NetErrorView netErrorView;
    private int pageNum = 1;

    private InspectionRecordAdapter inspectionRecordAdapter;
    private List<InspectionRecordBean.ListBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.inspection_progress_fragment, container, false);

        initView();
        initData(getActivity());

        return view;
    }


    private void initView() {
        //无网络处理
        netErrorView = new NetErrorView.Builder(getActivity(), ((ViewGroup) view.findViewById(R.id.inspection_device_progress_layout))).setRetryNetWorkImpl(this).create();
        mRecyclerView = view.findViewById(R.id.inspection_device_progress_recyclerview);
        noDataLayout = view.findViewById(R.id.inspection_device_progress_no_data);
        noDataLayout.setOnClickListener(this);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setLoadingListener(this);
//        mRecyclerView.setLoadingMoreEnabled(false);
        netErrorView.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNum = 1;
                initData(getActivity());
            }
        });

    }

    private void initData(Context context) {
        int deviceId = getArguments().getInt(ID, 0);
//        token	string	是	用户token
//        id	string	是	设备Id
//        pusher	string	否	提交人(可选)
//        checkTime	string	否	提交时间(可选)
//        isQualified	string	否	设备状态 1、合格 2、异常不合格(可选)
//        remark	string	否	备注(可选)
//         pageSize	string	是	显示报告的最大数（默认10条）
//        pageNum	string	是	第几页
        Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
//        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        map.put("pageNum", pageNum);
        map.put(ActValue.Id, deviceId);



        RetrofitHelper.subscriber(RetrofitHelper.Https(InspectionApi.class).getInspectionRecord(map), new RxSubscriber<InspectionRecordBean>(context) {
            @Override
            protected void onSuccess(InspectionRecordBean inspectionRecordBean) {
                LogUtils.e("inspectionRecordBean" + inspectionRecordBean);
                netErrorView.removeNetErrorView();

                if (pageNum == 1) {
                    //下拉刷新
//                    eventBeanList.clear();
                    //通知企业单位详情界面刷新是否合格
                    EventBusBean eventBusBean = new EventBusBean();
                    eventBusBean.setFlag("FragmentDetail");
                    eventBusBean.setObject(inspectionRecordBean.getData().getListPlace().getList().get(0).getIsQualified());
                    EventBus.getDefault().post(eventBusBean);
                    list.clear();
                } else {
                    //加载更多，没有数据了
                    if (pageNum > inspectionRecordBean.getData().getListPlace().getTotalPage()) {
                        mRecyclerView.setNoMore(true);
                        return;
                    }
                }

                if (inspectionRecordBean.getData().getListPlace().getList() != null && inspectionRecordBean.getData().getListPlace().getList().size() > 0) {
                    //有数据
                    mRecyclerView.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    list.addAll(inspectionRecordBean.getData().getListPlace().getList());

                    if (inspectionRecordAdapter == null) {
                        inspectionRecordAdapter = new InspectionRecordAdapter(getActivity(), list);
                        mRecyclerView.setAdapter(inspectionRecordAdapter);
                    } else {
                        inspectionRecordAdapter.setOnRefresh(true);
                        inspectionRecordAdapter.notifyDataSetChanged();
                    }

                    if (pageNum == 1) {
                        //下拉刷新完成
                        mRecyclerView.refreshComplete();
                    } else {
                        //加载更多完成
                        mRecyclerView.loadMoreComplete();
                    }
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
                netErrorView.addNetErrorView();

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //网络错误e：java.net.ConnectException
                LogUtils.e("onError=" + e);
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
                netErrorView.addNetErrorView();
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
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(),code);
            }

        });


    }

    /*    *//**
     * fragment静态传值
     *//*
    public static FragmentProgress newInstance(int type) {
        FragmentProgress fragment = new FragmentProgress();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);

        return fragment;
    }*/

    /**
     * fragment静态传值
     */
    public static FragmentProgress newInstance(int deviceId) {
        FragmentProgress fragment = new FragmentProgress();
        Bundle bundle = new Bundle();
        bundle.putInt(ID, deviceId);
        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        pageNum = 1;
        initData(null);
    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        initData(null);
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        initData(null);
    }

    @Override
    public void retryNetWork() {
        initData(getActivity());
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inspection_device_progress_no_data:
                pageNum = 1;
                initData(getActivity());
                break;
        }
    }

    static List<InspectionRecordBean.ListBean> getSingle(List<InspectionRecordBean.ListBean> list,List<InspectionRecordBean.ListBean> list2) {

        //创建一个新集合，循环传递进来的集合进行比较，有重复不添加
        ArrayList<InspectionRecordBean.ListBean> arrayList = new  ArrayList<InspectionRecordBean.ListBean>();
        for (InspectionRecordBean.ListBean listBean : list) {

            for (InspectionRecordBean.ListBean bean : list2) {


            }
        }
        return list2;
    }

}
