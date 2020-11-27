package com.yxdz.pinganweishi.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.leaf.library.StatusBarUtil;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.bean.YxdzInfo;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.FireSmokeEquipmentBean;
import com.yxdz.pinganweishi.bean.FireSmokeEquipmentInfoBean;
import com.yxdz.pinganweishi.bean.UserInfoBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.pinganweishi.view.SingleChoiceDialogFragment;
import com.yxkj.yunshicamera.realplay.EZRealPlayActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备详情
 */
public class FireDeviceInfoActivity extends BaseHeadActivity {

    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    private TextView startTime;
    private TextView endTime;
    private TextView tvDeviceState;
    private TextView tvDevicePlaceName;
    private TextView tvDevicePlaceAddress;
    private TextView tvDevicePlacePosition;
    private Button btnDeal;
    private ImageView ivCamera1;
    private String deviceId;
    private String placeAddress;
    private String placeName;
    private int index;
    private FireSmokeEquipmentBean.ListSmokeEquipmentBean listSmokeEquipmentBean;
    private int createType;
    private boolean isSet = false;
    private String startTimedata;
    private String endTimedata;
    private TextView deviceName;
    private TextView startTime2;
    private TextView endTime2;
    private String startTimedata2;
    private String endTimedata2;
    private TextView fire_device_info_type;
    private TitleBarView titleBarView;
    private LinearLayout deal_result_layout;
    private TextView check_time;
    private TextView human_check_time;
    private TextView resend_limit;
    private TextView check_temperture;
    private TextView check_temperture_time;
    private LinearLayout gate_record;

    public static double[] bd09_To_Gcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat, tempLon};
        return gps;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_fire_device_info;
    }

    @Override
    protected void setContentView() {
        titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this, R.color.primarystart), ContextCompat.getColor(this, R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);
        initView();
        initData(this);
    }

    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("设备详情");
        if (createType != 3) {
            titleBarView.getRightImageView().setVisibility(View.VISIBLE);
            titleBarView.getRightImageView().setImageResource(R.mipmap.edit_row_wh);
            titleBarView.getRightImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FireDeviceInfoActivity.this, EditFireDeviceActivity.class);
                    intent.putExtra("DeviceSn", listSmokeEquipmentBean.getSn());
                    intent.putExtra("Deviceame", deviceName.getText());
                    intent.putExtra("Location", tvDevicePlacePosition.getText());
                    intent.putExtra("startTime", startTime.getText());
                    intent.putExtra("endTime", endTime.getText());
                    intent.putExtra("startTime2", startTime2.getText());
                    intent.putExtra("endTime2", endTime2.getText());
                    intent.putExtra("DeviceId", deviceId);
                    intent.putExtra("PlaceId", listSmokeEquipmentBean.getPlaceId());
                    intent.putExtra("check_time", listSmokeEquipmentBean.getYgSensitivity());
                    intent.putExtra("human_check_time", listSmokeEquipmentBean.getCheckTime());
                    intent.putExtra("resend_limit", listSmokeEquipmentBean.getRepeatTime());
                    intent.putExtra("check_temperture", listSmokeEquipmentBean.getTemperatureWarn());
                    intent.putExtra("check_temperture_time", listSmokeEquipmentBean.getRtSensitivity());
                    startActivityForResult(intent, 110);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 110) {
            deviceName.setText(data.getStringExtra("deviceName"));
            startTime.setText(data.getStringExtra("install_start_time"));
            endTime.setText(data.getStringExtra("install_end_time"));
            startTime2.setText(data.getStringExtra("install_start_time2"));
            endTime2.setText(data.getStringExtra("install_end_time2"));
            check_time.setText(data.getStringExtra("check_time"));
            human_check_time.setText(data.getStringExtra("human_check_time"));
            resend_limit.setText(data.getStringExtra("resend_limit"));
            check_temperture.setText(data.getStringExtra("check_temperture"));
            check_temperture_time.setText(data.getStringExtra("check_temperture_time"));
        }
    }

    private void initView() {
        deviceName = findViewById(R.id.fire_device_info_name);
        startTime = findViewById(R.id.fire_device_info_start_time);
        endTime = findViewById(R.id.fire_device_info_end_time);
        startTime2 = findViewById(R.id.fire_device_info_start_time2);
        endTime2 = findViewById(R.id.fire_device_info_end_time2);
        fire_device_info_type = findViewById(R.id.fire_device_info_type);

        gate_record = findViewById(R.id.gate_record);

        gate_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FireDeviceInfoActivity.this, GateRecordActivity.class);
                intent.putExtra("SN", listSmokeEquipmentBean.getSn());
                startActivity(intent);
            }
        });


        tvDeviceState = findViewById(R.id.fire_device_info_state);
        tvDevicePlaceName = findViewById(R.id.fire_device_info_place_name);
        tvDevicePlaceAddress = findViewById(R.id.fire_device_info_place_address);
        tvDevicePlacePosition = findViewById(R.id.fire_device_info_place_position);
        btnDeal = findViewById(R.id.fire_device_info_deal);
        ivCamera1 = findViewById(R.id.ivCamera1);
        check_time = findViewById(R.id.check_time);
        human_check_time = findViewById(R.id.human_check_time);
        resend_limit = findViewById(R.id.resend_limit);
        check_temperture = findViewById(R.id.check_temperture);
        check_temperture_time = findViewById(R.id.check_temperture_time);

        btnDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialogFragment(v);
            }
        });

        deal_result_layout = findViewById(R.id.deal_result_layout);

        final double lat = getIntent().getDoubleExtra("lat", 0);
        final double lng = getIntent().getDoubleExtra("lng", 0);


        findViewById(R.id.navigation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FireDeviceInfoActivity.this);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_layout);
                bottomSheetDialog.findViewById(R.id.gaodemap).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double[] doubles = bd09_To_Gcj02(lat, lng);
//                高德地图
                        try {
                            Uri uri = Uri.parse("amapuri://route/plan/?dlat=" + doubles[0] + "&dlon=" + doubles[1] + "&dname=" + tvDevicePlaceName.getText() + "&dev=0&t=0");
                            startActivity(new Intent(Intent.ACTION_VIEW, uri));
                        } catch (Exception e) {
                            Toast.makeText(FireDeviceInfoActivity.this, "没有安装高德地图,请先安装", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                bottomSheetDialog.findViewById(R.id.baidumap).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                百度地图
                        try {
                            Uri uri = Uri.parse("baidumap://map/direction?destination=latlng:" + lat + "," + lng + "|name:" + tvDevicePlaceName.getText() + "&mode=driving");
                            startActivity(new Intent(Intent.ACTION_VIEW, uri));
                        } catch (Exception e) {
                            Toast.makeText(FireDeviceInfoActivity.this, "没有安装百度地图,请先安装", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                bottomSheetDialog.show();
            }
        });


        deviceId = getIntent().getStringExtra("deviceId");
        placeAddress = getIntent().getStringExtra("placeAddress");
        placeName = getIntent().getStringExtra("placeName");

    }

    @SuppressLint("SetTextI18n")
    private void initData(final Context context) {
        Intent intent = getIntent();
        listSmokeEquipmentBean = (FireSmokeEquipmentBean.ListSmokeEquipmentBean) intent.getSerializableExtra("listSmokeEquipmentBean");
        Map<String, Object> map = new HashMap<>();
        map.put("id", listSmokeEquipmentBean.getId());
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getFireControlSmokeInfo(map), new RxSubscriber<FireSmokeEquipmentInfoBean>(FireDeviceInfoActivity.this) {


            @Override
            protected void onSuccess(final FireSmokeEquipmentInfoBean fireSmokeEquipmentInfoBean) {
                ivCamera1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fireSmokeEquipmentInfoBean.getData().getDeviceSerial() != null && !fireSmokeEquipmentInfoBean.getData().getDeviceSerial().equals("")) {
                            Intent intent = new Intent(FireDeviceInfoActivity.this, EZRealPlayActivity.class);
                            intent.putExtra("DeviceSerial", fireSmokeEquipmentInfoBean.getData().getDeviceSerial());
                            intent.putExtra("CameraName", fireSmokeEquipmentInfoBean.getData().getCameraName());
                            intent.putExtra("mVerifyCode", fireSmokeEquipmentInfoBean.getData().getValidateCode());
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, "该设备没有绑定摄像头", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        createType = intent.getIntExtra("createType", 1);
        deviceName.setText(listSmokeEquipmentBean.getEquipmentName());
        tvDevicePlacePosition.setText(listSmokeEquipmentBean.getLocation());
        //iswork 0,离线，1，在线
        if (listSmokeEquipmentBean.getIsWork() == 1) {
            if (listSmokeEquipmentBean.getWarning() == 2) {
                tvDeviceState.setTextColor(Color.RED);
                tvDeviceState.setText("报警");
                deal_result_layout.setVisibility(View.VISIBLE);
            } else {
                tvDeviceState.setTextColor(getResources().getColor(R.color.txt_color_green));
                tvDeviceState.setText("正常");
                deal_result_layout.setVisibility(View.GONE);
            }
        } else {
            tvDeviceState.setTextColor(getResources().getColor(R.color.txt_color_yello));
            tvDeviceState.setText("离线");
            deal_result_layout.setVisibility(View.GONE);
        }
        tvDevicePlaceName.setText(placeName);
        tvDevicePlaceAddress.setText(listSmokeEquipmentBean.getLocation());
        if (listSmokeEquipmentBean.getStartTime() != null) {
//            String[] split1 = listSmokeEquipmentBean.getStartTime().split(":");
            startTimedata = listSmokeEquipmentBean.getStartTime();
            startTime.setText(listSmokeEquipmentBean.getStartTime());

        } else {
            startTimedata = "";
            startTime.setText("---");
        }
        if (listSmokeEquipmentBean.getEndTime() != null) {
//            String[] split2 = listSmokeEquipmentBean.getEndTime().split(":");
            endTimedata = listSmokeEquipmentBean.getEndTime();
            endTime.setText(listSmokeEquipmentBean.getEndTime());
        } else {
            endTimedata = "";
            endTime.setText("---");
        }

        if (listSmokeEquipmentBean.getStartTime2() != null && !listSmokeEquipmentBean.getStartTime2().equals("")) {
//            String[] split1 = listSmokeEquipmentBean.getStartTime2().split(":");
            startTimedata2 = listSmokeEquipmentBean.getStartTime2();
            startTime2.setText(listSmokeEquipmentBean.getStartTime2());
        } else {
            startTimedata2 = "";
            startTime2.setText("---");
        }
        if (listSmokeEquipmentBean.getEndTime2() != null && !listSmokeEquipmentBean.getEndTime2().equals("")) {
//            String[] split2 = listSmokeEquipmentBean.getEndTime2().split(":");
            endTimedata2 = listSmokeEquipmentBean.getEndTime2();
            endTime2.setText(listSmokeEquipmentBean.getEndTime2());
        } else {
            endTimedata2 = "";
            endTime2.setText("---");
        }

        switch (listSmokeEquipmentBean.getEqType()) {
            case 1:
                fire_device_info_type.setText("烟感");
                break;
            case 2:
                fire_device_info_type.setText("人体");
                break;
            case 3:
                fire_device_info_type.setText("人体+烟感");
                break;
            case 4:
                fire_device_info_type.setText("应急按钮");
                break;
            case 5:
                fire_device_info_type.setText("燃气");
                break;
            case 6:
                fire_device_info_type.setText("煤气");
                break;
            case 7:
                fire_device_info_type.setText("应急灯");
                break;
            case 8:
                fire_device_info_type.setText("门磁");
                break;
        }
//        if (createType == 3) {
//            btnDeal.setVisibility(View.GONE);
//        }


        check_time.setText(listSmokeEquipmentBean.getYgSensitivity() + "");
        human_check_time.setText(listSmokeEquipmentBean.getCheckTime() + "");
        resend_limit.setText(listSmokeEquipmentBean.getRepeatTime() + "");
        check_temperture.setText(listSmokeEquipmentBean.getTemperatureWarn() + "");
        check_temperture_time.setText(listSmokeEquipmentBean.getRtSensitivity() + "");


    }


    public void showSingleChoiceDialogFragment(View view) {
        SingleChoiceDialogFragment singleChoiceDialogFragment = new SingleChoiceDialogFragment();
        final String[] items = {"报警误报", "报警测试", "真实报警"};
        singleChoiceDialogFragment.show("请选择处理类型", items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                index = which;
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSmokeDeal(index);
            }
        }, getSupportFragmentManager());
    }

    /**
     * 报警处理
     *
     * @param index
     */
    private void getSmokeDeal(int index) {
        Map<String, Object> map = new HashMap<>();
//        map.put(ActValue.Token, SPUtils.get(FireDeviceInfoActivity.this, ConstantUtils.TOKEN, ""));
        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        map.put(ActValue.DeviceId, deviceId + "");
        map.put(ActValue.OperCode, "" + (index + 1));
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getFireControlSmokeDeal(map), new RxSubscriber<YxdzInfo>(FireDeviceInfoActivity.this) {
            @Override
            protected void onSuccess(YxdzInfo yxdzInfo) {
                ToastUtils.showShort(FireDeviceInfoActivity.this, "事件处理成功！");
                //处理成功返回刷新，resultCode=1；
                setResult(FireDeviceActivity.FireDeviceResultCode);
                finish();
            }

            @Override
            protected void onOtherError(String code) {
                super.onOtherError(code);
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
            }
        });
    }
}