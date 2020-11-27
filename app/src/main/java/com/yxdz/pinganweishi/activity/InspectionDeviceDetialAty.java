package com.yxdz.pinganweishi.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.leaf.library.StatusBarUtil;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.utils.NoDoubleClick;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.api.InspectionApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.InspectionEquipmentBean;
import com.yxdz.pinganweishi.bean.ScanBean;
import com.yxdz.pinganweishi.bean.UserInfoBean;
import com.yxdz.pinganweishi.fragment.FragmentDetail;
import com.yxdz.pinganweishi.fragment.FragmentProgress;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.pinganweishi.view.InspectionSubmitDialog;
import com.yxdz.pinganweishi.view.PageTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 巡检设备列表详情
 */
public class InspectionDeviceDetialAty extends BaseHeadActivity {

    private InspectionEquipmentBean.ListFirefightingEquipmenForMobleBean data;

    private TabLayout tabLayout;

    private ViewPager viewPager;
    private FragmentDetail fragmentDetail;
    private FragmentProgress fragmentProgress;
    private TitleBarView titleBarView;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_inspection_device_detial_aty);
//    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_inspection_device_detial_aty;
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
        initData();
    }

    private void initView() {
        tabLayout = findViewById(R.id.inspection_device_tablayout);
        viewPager = findViewById(R.id.inspection_device_viewpager);

        //标题栏
        titleBarView.getRightImageView().setVisibility(View.VISIBLE);
        titleBarView.getRightImageView().setBackground(getResources().getDrawable(R.mipmap.record));
        titleBarView.setRightImageViewOnClickListener(new TitleBarView.RightImageViewOnClickListener() {
            @Override
            public void onRightOnClick(View v) {
                if (NoDoubleClick.isFastDoubleClick(600)) {
                    return;
                }

//                if (getIntent().getBooleanExtra("scan", false)) {
                //通过扫码进来
                Intent intent = new Intent(InspectionDeviceDetialAty.this, InspectionSubmitDialog.class);
                intent.putExtra("id", data.getId());
                intent.putExtra("type", 2);
                startActivityForResult(intent, 1);
//                } else {
//                    //点击数据进来
//                    // 创建IntentIntegrator对象
//                    IntentIntegrator intentIntegrator = new IntentIntegrator(InspectionDeviceDetialAty.this);
//                    // 设置自定义扫描Activity
//                    intentIntegrator.setCaptureActivity(CaptureActivity.class);
//                    intentIntegrator.setPrompt("将二维码放入框内, 即可自动扫描");
////                  intentIntegrator.setTimeout(15000);
//                    // 开始扫描
//                    intentIntegrator.initiateScan();
//                }

            }
        });

    }

    @SuppressLint("WrongConstant")
    private void initData() {

        data = (InspectionEquipmentBean.ListFirefightingEquipmenForMobleBean)
                getIntent().getSerializableExtra("listFirefightingEquipmenForMobileBeanList");

        final List<String> listTitles = new ArrayList<>();
        final List<Fragment> fragments = new ArrayList<>();
        listTitles.add("设备详情");
        listTitles.add("巡检记录");

        PageTransformer pageTransformer = new PageTransformer();
        viewPager.setPageTransformer(true, pageTransformer);


        fragmentDetail = FragmentDetail.newInstance(data, getIntent().getStringExtra("placeName"));
        fragmentProgress = FragmentProgress.newInstance(data.getId());
        fragments.add(fragmentDetail);
        fragments.add(fragmentProgress);

        tabLayout.setTabMode(TabLayout.SCROLL_AXIS_HORIZONTAL);//设置tab模式，当前为系统默认模式
        for (int i = 0; i < listTitles.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(listTitles.get(i)));//添加tab选项
        }

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
            @Override
            public CharSequence getPageTitle(int position) {
                return listTitles.get(position);
            }
        };
        viewPager.setAdapter(mAdapter);
//
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。


    }

    /**
     * 扫码请求数据
     *
     * @param context
     * @param scanId
     */
    private void scanInitData(Context context, String scanId) {
        final Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
//        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        map.put("onlyUuId", scanId);

        RetrofitHelper.subscriber(RetrofitHelper.Https(InspectionApi.class).getInspectionScanDeviceList(map), new RxSubscriber<ScanBean>(context) {
            @Override
            protected void onSuccess(ScanBean scanBean) {

                if (scanBean.getData().getFireEquipmentMsg() != null && scanBean.getData().getFireEquipmentMsg().getId() == data.getId()) {
                    Intent intent = new Intent(InspectionDeviceDetialAty.this, InspectionSubmitDialog.class);
                    intent.putExtra("id", data.getId());
                    intent.putExtra("type", 1);
                    startActivityForResult(intent, 1);
                } else {
                    ToastUtils.showShort(InspectionDeviceDetialAty.this, "该设备与扫码设备不是同一设备");
                }
            }

            @Override
            protected void onOtherError(String code) {
                super.onOtherError(code);
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
            }

        });

    }

    /**
     * 提交上传报告
     */
    private void submitData() {

        UserInfoBean userInfoBean = UserInfoBean.getInstance();

//        token	string	是	数据访问token
//        id	string	是	设备的Id
//        equipmentPicError1	string	否	设备的现场照片
//        equipmentPicError2	string	否	设备的现场照片
//        equipmentPicError3	string	否	设备的现场照片
//        isQualified	string	否	是否合格，不合格即为异常（巡检报告需要这字段，整改报告不用）
//        remark	string	否	巡检人员的备注或管理人员的整改信息
        Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
//        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        map.put(ActValue.Id, data.getId());
        map.put("remark", userInfoBean.getName());

        //登录类型(1：系统超级管理员， 2：系统普通用户 3：消防的巡检员， 4：消防企业人员)
//        if (!TextUtils.isEmpty(userInfoBean.getLoginType()) && userInfoBean.getLoginType().equals("3")) {
//            map.put("isQualified", data.getIsQualified());
//        }

//        RetrofitHelper.subscriber(RetrofitHelper.Https(InspectionApi.class).getInformation(map), new RxSubscriber<YxdzInfo>(this) {
//            @Override
//            protected void onSuccess(YxdzInfo yxdzInfo) {
//                ToastUtils.showShort(InspectionDeviceDetialAty.this, "提交成功");
//                finish();
//            }
//
//        });

    }

    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("设备信息");
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
                scanInitData(this, result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            // RESULT_OK，判断另外一个activity已经结束数据输入功能，Standard activity result:
            // operation succeeded. 默认值是-1
            if (resultCode == 1) {
                if (requestCode == 1) {
                    if (data.getBooleanExtra("data", false)) {
                        LogUtils.e("-----------------------------------刷新");
                        fragmentProgress.refreshData();
                    }
                }
            }
        }

    }


}
