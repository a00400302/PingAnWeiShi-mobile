package com.yxdz.pinganweishi.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.EventBusBean;
import com.yxdz.pinganweishi.bean.InspectionEquipmentBean;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 详情
 */
public class FragmentDetail extends Fragment {

    private View view;
    private static final String TYPE = "type";

    private TextView tvDeviceName;
    private TextView tvDeviceState;
    private TextView tvDeviceType;
    private TextView tvDevicePlaceName;
    private LinearLayout tvPlaceNameLayout;
    //    private TextView tvDevicePlaceAddress;
    private TextView tvDevicePlacePosition;
    //    private Button btnDeal;
    private InspectionEquipmentBean.ListFirefightingEquipmenForMobleBean data;

    private String loginType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.inspection_detail_fragment, container, false);

        initView();
        initData();
        EventBus.getDefault().register(this);
        return view;
    }

    private void initView() {
        tvDeviceName = view.findViewById(R.id.inspection_device_info_name);
        tvDeviceState = view.findViewById(R.id.inspection_device_info_state);
        tvDeviceType = view.findViewById(R.id.inspection_device_info_type);
        tvDevicePlaceName = view.findViewById(R.id.inspection_device_info_place_name);
//        tvDevicePlaceAddress = view.findViewById(R.id.inspection_device_info_place_address);
        tvDevicePlacePosition = view.findViewById(R.id.inspection_device_info_place_position);
        tvPlaceNameLayout = view.findViewById(R.id.placeNameLayout);
    }

    private void initData() {
//        data = (InspectionEquipmentBean.ListFirefightingEquipmenForMobileBean)
//                getIntent().getSerializableExtra("listFirefightingEquipmenForMobileBeanList");
        data = (InspectionEquipmentBean.ListFirefightingEquipmenForMobleBean) getArguments().getSerializable("listFirefightingEquipmenForMobileBeanList");
        if (data != null) {
            tvDeviceName.setText("" + data.getEquipmentName());

            //消防设备是否合格 1、合格正常 2、不合格异常
            if (data.getIsQualified() == 1) {
                tvDeviceState.setText("合格");
                tvDeviceState.setTextColor(getResources().getColor(R.color.txt_color_green));
            } else if (data.getIsQualified() == 2) {
                tvDeviceState.setText("不合格");
                tvDeviceState.setTextColor(Color.RED);
            }

//            tvDeviceType.setText("" + data.getEquipmentType());
            setDeviceType(data.getEqType());
            if (getArguments().getString("placeName") != null) {
                tvDevicePlaceName.setText(getArguments().getString("placeName"));
            } else {
                tvPlaceNameLayout.setVisibility(View.GONE);
            }
//            tvDevicePlaceAddress.setText("" + data.getLocation());
            tvDevicePlacePosition.setText("" + data.getLocation());

//            loginType = UserInfoBean.getInstance().getLoginType();
//            if ("2".equals(loginType)) {
//                btnDeal.setVisibility(View.GONE);
//            } else {
//                btnDeal.setVisibility(View.VISIBLE);
//            }

        }

    }

    /**
     * fragment静态传值
     */
    public static FragmentDetail newInstance(InspectionEquipmentBean.ListFirefightingEquipmenForMobleBean data, String placeName) {
        FragmentDetail fragment = new FragmentDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable("listFirefightingEquipmenForMobileBeanList", data);
        bundle.putString("placeName", placeName);
        fragment.setArguments(bundle);


        return fragment;
    }

    //订阅方法，当接收到事件的时候，会调用该方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusBean eventBusBean) {
        //切换到主页面消防模块
//        if ("FragmentDetail".equals(eventBusBean.getFlag())) {
//            if (((int) ((EventBusBean) eventBusBean).getObject()) == 1) {
//                tvDeviceState.setText("合格");
//                tvDeviceState.setTextColor(getResources().getColor(R.color.txt_color_green));
//            } else if (((int) ((EventBusBean) eventBusBean).getObject()) == 2) {
//                tvDeviceState.setText("不合格");
//                tvDeviceState.setTextColor(Color.RED);
//            }
//        }
    }

    /**
     * 显示设备类型
     *
     * @param type
     */
    private void setDeviceType(int type) {
        switch (type) {
            case 1://1 : 自动火灾报警系统（包括烟感、温感、报警按钮、警铃、广播、报警模块、电话等）
                tvDeviceType.setText("自动火灾报警系统");
                break;
            case 2://2 :  消防水灭火系统（喷淋、消火栓、湿式报警阀、干式报警阀等）
                tvDeviceType.setText("消防水灭火系统");
                break;
            case 3://3 : 气体灭火系统（七氟丙烷、二氧化碳）
                tvDeviceType.setText("气体灭火系统");
                break;
            case 4://4 : 应急照明灯与疏散指灯
                tvDeviceType.setText("应急照明灯与疏散指灯");
                break;
            case 5://5 :  灭火器（手提式灭火器、推车式灭火器）
                tvDeviceType.setText("灭火器");
                break;
            case 6://6 : 防排烟系统
                tvDeviceType.setText("防排烟系统");
                break;
            case 7://7 : 以及其他附属设施（水泵、阀门、防火卷帘门、防火门，疏散通道、应急通道等）
                tvDeviceType.setText("其他附属设施");
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}







