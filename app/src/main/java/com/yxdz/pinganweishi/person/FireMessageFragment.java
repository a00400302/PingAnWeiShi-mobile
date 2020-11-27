package com.yxdz.pinganweishi.person;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.yxdz.pinganweishi.adapter.MessageAdapter;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.bean.FireSmokeMessageBean;
import com.yxdz.pinganweishi.bean.SmokeMessageBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.pinganweishi.view.NetErrorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: FireMessageFragment
 * @Desription:
 * @author: Dreamcoding
 * @date: 2018/9/3
 */
public class FireMessageFragment extends Fragment implements View.OnClickListener, XRecyclerView.LoadingListener, RetryNetWorkImpl {

    private View view;
    private CustomRecyclerView mRecyclerView;
    private LinearLayout noDataLayout;

    private List<SmokeMessageBean> listMessage = new ArrayList<>();

    private MessageAdapter messageAdapter;

    private NetErrorView netErrorView;
    private int pageNum = 1;

    private String placeId;
    private String startTime;
    private String endTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //引用创建好的xml布局
        view = inflater.inflate(R.layout.fragment_fire_message, container, false);
        initView();
        initData(getActivity());
        return view;
    }

    /**
     * fragment静态传值
     */
    public static FireMessageFragment newInstance() {
        FireMessageFragment fragment = new FireMessageFragment();
        return fragment;
    }


    private void initView() {
        //无网络处理
        netErrorView = new NetErrorView.Builder(getActivity(), ((ViewGroup) view.findViewById(R.id.flMessage))).setRetryNetWorkImpl(this).create();

        mRecyclerView = view.findViewById(R.id.crvMessage);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.setLoadingMoreEnabled(true);
        noDataLayout = view.findViewById(R.id.llMessageNoData);
        noDataLayout.setOnClickListener(this);
    }

    public void initData(Context context) {
        Map<String, Object> map = new HashMap<>();
        map.put(ActValue.PageNum, pageNum);
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        if (!TextUtils.isEmpty(placeId)){
            map.put(ActValue.PlaceId,placeId);
        }
        if (!TextUtils.isEmpty(startTime)){
            map.put(ActValue.EventStart,startTime);
        }
        if (!TextUtils.isEmpty(endTime)){
            map.put(ActValue.EventEnd,endTime);
        }
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getFireControlSmokeMessage(map), new RxSubscriber<FireSmokeMessageBean>(context) {
            @Override
            protected void onSuccess(FireSmokeMessageBean fireSmokeMessageBean) {
                LogUtils.e("fireSmokeMessageBean" + fireSmokeMessageBean);
                netErrorView.removeNetErrorView();
                if (pageNum == 1) {
                    //下拉刷新
                    listMessage.clear();

                } else {
                    //加载更多，没有数据了
                    if (pageNum > fireSmokeMessageBean.getData().getPAGEModel().getTotalPage()) {
                        mRecyclerView.setNoMore(true);
                        return;
                    }
                }

                if (fireSmokeMessageBean.getData().getPAGEModel() != null &&fireSmokeMessageBean.getData().getPAGEModel().getList().size() > 0) {
                    //有数据
                    mRecyclerView.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);

                    listMessage.addAll(fireSmokeMessageBean.getData().getPAGEModel().getList());
                    if (messageAdapter == null) {
                        messageAdapter = new MessageAdapter(getActivity(),listMessage);
                        mRecyclerView.setAdapter(messageAdapter);
                    } else {
                        messageAdapter.notifyDataSetChanged();
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
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(),code);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMessageNoData://无数据重新请求接口
                initData(getActivity());
                break;
        }


    }
    /**
     * 刷新数据
     */
    public void refreshData(String placeId,String startTime,String endTime) {
        this.placeId=placeId;
        this.startTime=startTime;
        this.endTime=endTime;
        pageNum = 1;
        initData(getActivity());
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

}
