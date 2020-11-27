package com.yxdz.pinganweishi.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
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
import com.yxdz.pinganweishi.adapter.InspectionDeviceAdapter;
import com.yxdz.pinganweishi.api.InspectionApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.InspectionEquipmentBean;
import com.yxdz.pinganweishi.bean.ScanBean;
import com.yxdz.pinganweishi.bean.UserInfoBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;
import com.yxdz.pinganweishi.scan.CaptureActivity;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.pinganweishi.view.NetErrorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 巡检设备列表
 */
public class InspectionDeviceAty extends BaseHeadActivity implements XRecyclerView.LoadingListener, RetryNetWorkImpl {

    private CustomRecyclerView mRecyclerView;
    private LinearLayout noDataLayout;
    private NetErrorView netErrorView;

    private List<InspectionEquipmentBean.ListFirefightingEquipmenForMobleBean> listFirefightingEquipmenForMobileBeanList = new ArrayList<>();
    private InspectionDeviceAdapter inspectionDeviceAdapter;
    private TitleBarView titleBarView;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_inspection_device_aty;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this, R.color.primarystart), ContextCompat.getColor(this, R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(this);
    }

    private void initView() {
        //无网络处理
        netErrorView = new NetErrorView.Builder(this, ((ViewGroup) findViewById(R.id.inspection_device_layout))).setRetryNetWorkImpl(this).create();
        mRecyclerView = findViewById(R.id.inspection_device_recyclerview);
        noDataLayout = findViewById(R.id.inspection_device_no_data);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InspectionDeviceAty.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.setLoadingMoreEnabled(false);

//        titleBarView.getRightImageView().setImageResource(R.mipmap.scan_add_bk);
//        titleBarView.setRightImageViewOnClickListener(new TitleBarView.RightImageViewOnClickListener() {
//            @Override
//            public void onRightOnClick(View v) {
//                // 创建IntentIntegrator对象
//                IntentIntegrator intentIntegrator = new IntentIntegrator(InspectionDeviceAty.this);
//                // 设置自定义扫描Activity
//                intentIntegrator.setCaptureActivity(CaptureActivity.class);
//                intentIntegrator.setPrompt("将二维码放入框内, 即可自动扫描");
////              intentIntegrator.setTimeout(15000);
//                // 开始扫描
//                intentIntegrator.initiateScan();
//            }
//        });
    }

    /**
     * @param context
     */
    private void initData(Context context) {
        final Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
//        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        map.put(ActValue.PlaceId, getIntent().getIntExtra("id", -1));

        RetrofitHelper.subscriber(RetrofitHelper.Https(InspectionApi.class).getInspectionDeviceList(map), new RxSubscriber<InspectionEquipmentBean>(context) {
            @Override
            protected void onSuccess(InspectionEquipmentBean inspectionEquipmentBean) {
                netErrorView.removeNetErrorView();
                listFirefightingEquipmenForMobileBeanList.clear();
                listFirefightingEquipmenForMobileBeanList.addAll(inspectionEquipmentBean.getData().getListFirefightingEquipmenForMoble());
                if (listFirefightingEquipmenForMobileBeanList != null && listFirefightingEquipmenForMobileBeanList.size() > 0) {
                    //有数据
                    mRecyclerView.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    if (inspectionDeviceAdapter == null) {
                        inspectionDeviceAdapter = new InspectionDeviceAdapter(InspectionDeviceAty.this, listFirefightingEquipmenForMobileBeanList);
                        mRecyclerView.setAdapter(inspectionDeviceAdapter);
                        inspectionDeviceAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(InspectionDeviceAty.this, InspectionDeviceDetialAty.class);
                                intent.putExtra("listFirefightingEquipmenForMobileBeanList", listFirefightingEquipmenForMobileBeanList.get(position));
                                intent.putExtra("placeName", getIntent().getStringExtra("placeName"));
                                startActivity(intent);
                            }
                        });
                    } else {
                        inspectionDeviceAdapter.notifyDataSetChanged();
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
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("设备列表");
    }

    @Override
    public void onRefresh() {
        initData(null);
    }

    @Override
    public void onLoadMore() {
        mRecyclerView.setNoMore(true);
    }

    @Override
    public void retryNetWork() {
        initData(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 二维码获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                LogUtils.e("取消扫描");
            } else {
                LogUtils.e("扫描内容=" + result.getContents());
                Log.d("InspectionDeviceAty", result.getContents());
                scanInitData(null, result.getContents());

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 扫码请求数据
     *
     * @param context
     * @param scanId
     */
    private void scanInitData(Context context, final String scanId) {
        final Map<String, Object> map = new HashMap<>();
//        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        map.put("onlyUuId", scanId);

        RetrofitHelper.subscriber(RetrofitHelper.Https(InspectionApi.class).getInspectionScanDeviceList(map), new RxSubscriber<ScanBean>(context) {
            @Override
            protected void onSuccess(ScanBean scanBean) {

                ScanBean.FireEquipmentMsgBean fireEquipmentMsgBean = scanBean.getData().getFireEquipmentMsg();

                if (fireEquipmentMsgBean != null) {
                    InspectionEquipmentBean.ListFirefightingEquipmenForMobleBean data = new InspectionEquipmentBean.ListFirefightingEquipmenForMobleBean();
                    data.setId(fireEquipmentMsgBean.getId());
                    data.setCreateTime(fireEquipmentMsgBean.getCreateTime());
                    data.setUpdateTime(fireEquipmentMsgBean.getUpdateTime());
                    data.setCreateBy(fireEquipmentMsgBean.getCreateBy());
                    data.setUpdateBy(fireEquipmentMsgBean.getUpdateBy());
                    data.setType(fireEquipmentMsgBean.getType());
                    data.setAreaId(fireEquipmentMsgBean.getAreaId());
                    data.setQrCode(fireEquipmentMsgBean.getQrCode());
                    data.setAddTime(fireEquipmentMsgBean.getAddTime());
                    data.setEquipmentName(fireEquipmentMsgBean.getEquipmentName());
                    data.setEquipmentPic(fireEquipmentMsgBean.getEquipmentPic());
                    data.setIsEnable(fireEquipmentMsgBean.getIsEnable());
                    data.setIsWork(fireEquipmentMsgBean.getIsWork());
                    data.setIsQualified(fireEquipmentMsgBean.getIsQualified());
                    data.setLocation(fireEquipmentMsgBean.getLocation());
                    data.setPlaceId(fireEquipmentMsgBean.getPlaceId());
                    data.setSn(fireEquipmentMsgBean.getSn());
                    data.setWarning(fireEquipmentMsgBean.getWarning());
                    data.setWarningNum(fireEquipmentMsgBean.getWarningNum());
                    data.setEqType(fireEquipmentMsgBean.getEqType());
                    data.setRemark(fireEquipmentMsgBean.getRemark());
                    data.setPlaceName(fireEquipmentMsgBean.getPlaceName());
                    Intent intent = new Intent(InspectionDeviceAty.this, InspectionDeviceDetialAty.class);
                    intent.putExtra("listFirefightingEquipmenForMobileBeanList", data);
                    intent.putExtra("scan", true);
                    startActivity(intent);
                }
            }

            @Override
            protected void onOtherError(String code) {
                super.onOtherError(code);
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
            }
        });

    }

}
