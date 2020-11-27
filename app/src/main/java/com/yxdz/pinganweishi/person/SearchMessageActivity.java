package com.yxdz.pinganweishi.person;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.leaf.library.StatusBarUtil;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.load.RetryNetWorkImpl;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.TimeUtil;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.FirePlaceBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.pinganweishi.view.NetErrorView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SearchMessageActivity
 * @Desription:
 * @author: Dreamcoding
 * @date: 2018/9/3
 */
public class SearchMessageActivity extends BaseHeadActivity implements View.OnClickListener, RetryNetWorkImpl {

    private ImageView ivSelectPlace;
    private EditText etSearchPlace;
    private ImageView ivSelectTime;
    private EditText etStartTime;
    private EditText etEndTime;
    private Button btnSearch;
    private FrameLayout flSearch;
    private LinearLayout llSearch;

    private boolean isSelectPlace;
    private boolean isSelectTime;
    private ArrayList<String> placeList = new ArrayList<>();
    private ArrayList<String> placeIdList = new ArrayList<>();
    private String selectPlaceId;
    private String selectPlaceName;

    private Date beginSearchDate;
    private Date endSearchDate;

    private static int TIME_TYPE_START = 1;
    private static int TIME_TYPE_END = 2;

    public static int RESULT_CODE = 2;
    private String startDateStr;
    private String endDateStr;
    private TitleBarView titleBarView;


    @Override
    public int getLayoutResource() {
        return R.layout.activity_search_message;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        //无网络处理
        titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this,R.color.primarystart), ContextCompat.getColor(this,R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);
        netErrorView = new NetErrorView.Builder(this, ((ViewGroup) findViewById(R.id.flSearch))).setRetryNetWorkImpl(this).create();
        ivSelectPlace = findViewById(R.id.ivSelectPlace);
        etSearchPlace = findViewById(R.id.etSearchPlace);
        ivSelectTime = findViewById(R.id.ivSelectTime);
        etStartTime = findViewById(R.id.etStartTime);
        etEndTime = findViewById(R.id.etEndTime);
        btnSearch = findViewById(R.id.btnSearch);
        flSearch = findViewById(R.id.flSearch);
        llSearch = findViewById(R.id.llSearch);
        ivSelectPlace.setOnClickListener(this);
        etSearchPlace.setOnClickListener(this);
        ivSelectTime.setOnClickListener(this);
        etStartTime.setOnClickListener(this);
        etEndTime.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        initData();
    }

    public void initData() {

        selectPlaceId = getIntent().getStringExtra("placeId");
        selectPlaceName = getIntent().getStringExtra("placeName");
        if (!TextUtils.isEmpty(selectPlaceName)) {
            isSelectPlace = true;
            ivSelectPlace.setImageResource(R.mipmap.radio_choseed);
            etSearchPlace.setText(selectPlaceName);
        }
        startDateStr = getIntent().getStringExtra("startTime");
        endDateStr = getIntent().getStringExtra("endTime");
        if (!TextUtils.isEmpty(startDateStr)) {
            isSelectTime = true;

            ivSelectTime.setImageResource(R.mipmap.radio_choseed);
            etStartTime.setText(startDateStr);
            etEndTime.setText(endDateStr);
        }
        getPlaceData();
    }

    private void getPlaceData() {
        Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        map.put("placeType", "1");//场所类型（1，烟感场所；2 巡检场所）
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getFireControlQueryPlace(map), new RxSubscriber<FirePlaceBean>(null) {
            @Override
            protected void onSuccess(FirePlaceBean firePlaceBean) {
                netErrorView.removeNetErrorView();
                llSearch.setVisibility(View.VISIBLE);
                if (firePlaceBean.getData().getListPlace() != null && firePlaceBean.getData().getListPlace().size() > 0) {
                    for (FirePlaceBean.ListPlaceBean places : firePlaceBean.getData().getListPlace()) {
                        placeList.add(places.getPlaceName());
                        placeIdList.add(places.getId() + "");
                    }
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
                //网络错误e：java.net.ConnectException
                LogUtils.e("onError=" + e);
            }

            @Override
            public void onNetError() {
                super.onNetError();
                /**
                 * 防止重复添加 netErrorView,
                 * 否则会报 Caused by: java.lang.IllegalStateException:
                 *   The specified child already has a parent.
                 */
                llSearch.setVisibility(View.GONE);
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
        titleBarView.setTitleBarText("搜索");
    }


    private void showPlace() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {


            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                selectPlaceId = placeIdList.get(options1);
                selectPlaceName = placeList.get(options1);
                etSearchPlace.setText(selectPlaceName);
            }
        }).setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("请选择场所")//标题
                .setSubCalSize(16)//确定和取消文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.primary))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.primary))//取消按钮文字颜色
                .setTitleBgColor(0xfff0f0f0)//标题背景颜色 Night mode
                .setDividerColor(getResources().getColor(R.color.gray))
                .setBgColor(0xFFffffff)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

        pvOptions.setPicker(placeList);//添加数据源
        pvOptions.show();
    }


    private void showTimePicker(String title, final int type) {
        final Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2016, 0, 1);
        endDate.set(2100, 11, 31);

        TimePickerView timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (type == TIME_TYPE_START) {
                    beginSearchDate = date;
                    startDateStr = TimeUtil.getDateStr(date, TimeUtil.DATA_FORMAT2);
                    etStartTime.setText(startDateStr);
                } else if (type == TIME_TYPE_END) {
                    if (TextUtils.isEmpty(etStartTime.getText().toString())) {
                        ToastUtils.showShort(SearchMessageActivity.this, "请先输入开始时间!");
                    } else if (beginSearchDate.getTime() > date.getTime()) {
                        ToastUtils.showLong(SearchMessageActivity.this, "您输入的结束时间不能小于开始时间!");
                        etEndTime.setText("");
                    } else {
                        endDateStr = TimeUtil.getDateStr(date, TimeUtil.DATA_FORMAT2);
                        etEndTime.setText(endDateStr);
                    }
                }
            }
        }).setType(new boolean[]{true, true, true, true, true, false})
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText(title)//标题文字
                .setDate(Calendar.getInstance())
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .build();

        timePickerView.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSelectPlace:
                if (isSelectPlace) {
                    isSelectPlace = false;
                    ivSelectPlace.setImageResource(R.mipmap.radio_unchose);
                } else {
                    isSelectPlace = true;
                    ivSelectPlace.setImageResource(R.mipmap.radio_choseed);
                }
                break;
            case R.id.etSearchPlace:
                showPlace();
                break;
            case R.id.ivSelectTime:
                if (isSelectTime) {
                    isSelectTime = false;
                    ivSelectTime.setImageResource(R.mipmap.radio_unchose);
                } else {
                    isSelectTime = true;
                    ivSelectTime.setImageResource(R.mipmap.radio_choseed);
                }
                break;
            case R.id.etStartTime:
                showTimePicker("设置开始时间", TIME_TYPE_START);
                break;
            case R.id.etEndTime:
                showTimePicker("设置结束时间", TIME_TYPE_END);
                break;
            case R.id.btnSearch:
                Intent intent = new Intent();
                if (isSelectPlace) {
                    if (isSelectTime) {
                        intent.putExtra("placeId", selectPlaceId + "");
                        intent.putExtra("placeName", selectPlaceName);
                        intent.putExtra("startTime", startDateStr);
                        intent.putExtra("endTime", endDateStr);
                    } else {
                        intent.putExtra("placeId", selectPlaceId + "");
                        intent.putExtra("placeName", selectPlaceName);
                    }
                } else {
                    if (isSelectTime) {
                        intent.putExtra("startTime", startDateStr);
                        intent.putExtra("endTime", endDateStr);
                    }
                }
                setResult(RESULT_CODE, intent);
                finish();
                break;
        }
    }

    @Override
    public void retryNetWork() {
        getPlaceData();
    }
}
