package com.yxdz.pinganweishi.activity;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.leaf.library.StatusBarUtil;
import com.yxdz.common.BaseApplication;
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
import com.yxdz.pinganweishi.base.SplashActivity;
import com.yxdz.pinganweishi.bean.EventBusBean;
import com.yxdz.pinganweishi.bean.FirePlaceBean;
import com.yxdz.pinganweishi.bean.UserInfoBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.pinganweishi.utils.MediaPlayerAlert;
import com.yxkj.yunshicamera.realplay.EZRealPlayActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

public class FireMapActivity extends BaseHeadActivity implements View.OnClickListener, SensorEventListener {

    private MapView mMapView;
    // 定位相关
    private LocationClient mLocClient;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private FireMapActivity.MyLocationListenner myListener = new FireMapActivity.MyLocationListenner();

    private BaiduMap mBaiduMap;

    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    //    private MediaPlayer mediaPlayer;
    private String placeId;
    private String deviceId;
    private String fireDate;

    private Button btnKnow;
    private TextView tvFirePlaceName;
    private TextView tvFirePlaceAddress;
    private TextView tvFireAlarmDate;
    private String deviceSerial;
    private String validateCode;
    private String cameraName;
    private String type;
    private LatLng point;
    private FirePlaceBean.ListPlaceBean placeData;
    private TitleBarView titleBarView;
    ;


    @Override
    public int getLayoutResource() {
        return R.layout.activity_fire_map;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
//        yxscheme://com.lejiaanju.push/?placeId=aasdf&dateTime=1231234
        titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this,R.color.primarystart), ContextCompat.getColor(this,R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);
        Uri uri = getIntent().getData();
        if (uri != null) {
            String url = uri.toString();
            LogUtils.e("url:" + url);
            placeId = uri.getQueryParameter("placeId");
            deviceId = uri.getQueryParameter("deviceId");
            deviceSerial = uri.getQueryParameter("deviceSerial");
            validateCode = uri.getQueryParameter("validateCode");
            cameraName = uri.getQueryParameter("cameraName");
            type = uri.getQueryParameter("type");
            fireDate = TimeUtil.getDateStr(Long.parseLong(uri.getQueryParameter("dateTime")));
        }
        initView();
        getLocationPermission();
        initMap();
        initMediaPlayer();
        initData();
        LogUtils.e("FireMapActivity--------------------------------------------------------------------------------");

    }

    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText(type);

        titleBarView.getRightImageView().setImageResource(R.mipmap.play_video_bu);
        titleBarView.getRightImageView().setVisibility(View.VISIBLE);

        titleBarView.setTvRightOnClickListener(new TitleBarView.RightImageViewOnClickListener() {
            @Override
            public void onRightOnClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EZRealPlayActivity.class);
                intent.putExtra("DeviceSerial", deviceSerial);
                intent.putExtra("CameraName", cameraName);
                intent.putExtra("mVerifyCode", validateCode);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.e("=================================================================================");
    }


    /**
     * 请求定位权限
     */
    private void getLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] mPermissionList = new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                };
                ActivityCompat.requestPermissions(this, mPermissionList, 110);
                //其中110是requestcode，可以根据这个code判断，用户是否同意了授权。如果没有同意，可以根据回调进行相应处理：
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == 110) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LogUtils.e("定位授权成功！");
            } else {
                ToastUtils.showShort(this, "没有授权定位,无法定位当前位置！");
            }
        }
    }

    private void initMediaPlayer() {
//        mediaPlayer = ((MyApplication) this.getApplication()).getMediaPlay();
//        LogUtils.e("mediaPlayer:" + mediaPlayer);
        if (!MediaPlayerAlert.getInstance().getMediaPlayer(this).isPlaying()) {
            MediaPlayerAlert.getInstance().getMediaPlayer(this).start();
        }
    }

    private void initData() {

//        placeId = getIntent().getStringExtra("placeId");
//        fireDate = getIntent().getStringExtra("date");

/*        Uri uri = getIntent().getData();
        if (uri != null) {
            String url = uri.toString();
            placeId= uri.getQueryParameter("placeId");
            fireDate= TimeUtil.getDateStr(Long.parseLong(uri.getQueryParameter("dateTime")));
        }*/
//        placeId = 10;
//        fireDate = "2018-06-20 10:36";

        getData(this);

        //模拟数据

        /**
         * architectType : 1
         * areaId : null
         * checkedType : 2
         * formalitiesType : 0
         * id : 10
         * isAllot : null
         * isEnable : 1
         * lat : 40.901126
         * lng : 110.589708
         * placeAddress : 浙江省嘉兴市秀洲区
         * placeName : rwerq
         * placeType : 1
         * placeimgurl :
         * remark :
         * reviewedType : 1
         * warning : 2
         */

//        List<FirePlaceBean.ListPlaceBean> listPlaceBeanList = new ArrayList<>();
//        FirePlaceBean.ListPlaceBean data = new FirePlaceBean.ListPlaceBean();
//        data.setArchitectType("1");
//        data.setAreaId(null);
//        data.setCheckedType(2);
//        data.setFormalitiesType(0);
//        data.setId(10);
//        data.setIsAllot(null);
//        data.setIsEnable(1);
//        data.setLat("40.901126");
//        data.setLng("110.589708");
//        data.setPlaceAddress("浙江省嘉兴市秀洲区");
//        data.setPlaceName("rwerq");
//        data.setPlaceType(1);
//        data.setPlaceimgurl("");
//        data.setRemark("");
//        data.setReviewedType(1);
//        data.setWarning(2);
//        listPlaceBeanList.add(data);

//        showPlaceData(listPlaceBeanList.get(0));
    }

    private void initView() {
        mMapView = findViewById(R.id.fire_control_mapView);
        btnKnow = findViewById(R.id.fire_control_know);
        tvFirePlaceName = findViewById(R.id.fire_control_place_name);
        tvFirePlaceAddress = findViewById(R.id.fire_control_place_address);
        tvFireAlarmDate = findViewById(R.id.fire_control_place_date);
        btnKnow.setOnClickListener(this);
        titleBarView.setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toKnowAction();
            }
        });
        findViewById(R.id.mapButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FireMapActivity.this);

                bottomSheetDialog.setContentView(R.layout.bottom_sheet_layout);
                bottomSheetDialog.findViewById(R.id.gaodemap).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        double[] doubles = bd09_To_Gcj02(point.latitude, point.longitude);
//                高德地图
                        try {
                            Uri uri = Uri.parse("amapuri://route/plan/?dlat=" + doubles[0] + "&dlon=" + doubles[1] + "&dname=" + placeData.getPlaceName() + "&dev=0&t=0");
                            startActivity(new Intent(Intent.ACTION_VIEW, uri));
                        } catch (Exception e) {
                            Toast.makeText(FireMapActivity.this, "没有安装高德地图,请先安装", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                bottomSheetDialog.findViewById(R.id.baidumap).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                百度地图
                        try {
                            Uri uri = Uri.parse("baidumap://map/direction?destination=latlng:" + point.latitude + "," + point.longitude + "|name:" + placeData.getPlaceName() + "&mode=driving");
                            startActivity(new Intent(Intent.ACTION_VIEW, uri));
                        } catch (Exception e) {
                            Toast.makeText(FireMapActivity.this, "没有安装百度地图,请先安装", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                bottomSheetDialog.show();

            }
        });

    }

    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    public static double[] bd09_To_Gcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat, tempLon};
        return gps;
    }


    public void toKnowAction() {
        if (TextUtils.isEmpty(UserInfoBean.getInstance().getToken())) {
            startActivity(SplashActivity.class);
            finish();
        } else {
            //通知主页刷新
            EventBusBean eventBusBean = new EventBusBean();
            eventBusBean.setFlag("refresh");
            EventBus.getDefault().post(eventBusBean);
//            Intent intent=new Intent(FireMapActivity.this,FireDeviceInfoActivity.class);
//            intent.putExtra("deviceId", deviceId);
//            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fire_control_know:
//                Intent main = new Intent(FireMapActivity.this, MainActivity.class);
//                main.putExtra("refresh", true);
//                startActivity(main);
                toKnowAction();
                break;
        }
    }

    private void initMap() {
        // 地图初始化
        mBaiduMap = mMapView.getMap();
        mMapView.showZoomControls(false);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(0);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mMapView.showZoomControls(false);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

    }

    private void getData(Context context) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", placeId + "");
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
//        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        map.put(ActValue.PlaceType, "1");//场所类型（1，烟感场所；2 巡检场所）
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getFireControlQueryPlace(map), new RxSubscriber<FirePlaceBean>(context) {
            @Override
            protected void onSuccess(FirePlaceBean firePlaceBean) {
                LogUtils.e("firePlaceBean" + firePlaceBean);
                showPlaceData(firePlaceBean.getData().getListPlace().get(0));
            }

            @Override
            protected void onOtherError(String code) {
                super.onOtherError(code);
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
            }
        });
    }

    private void showPlaceData(FirePlaceBean.ListPlaceBean placeData) {
        //定义Maker坐标点
        point = new LatLng(Double.parseDouble(placeData.getLat()), Double.parseDouble(placeData.getLng()));
        this.placeData = placeData;
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.fire_location);

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions overlayOptions = new MarkerOptions()
                .position(point)
                .icon(bitmap);

        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(overlayOptions);
        setLocationMap(point.latitude, point.longitude, 15.0f);

        LatLng pointLocation = new LatLng(mCurrentLat, mCurrentLon);
        double distance = DistanceUtil.getDistance(point, pointLocation);
//        tvFireDistance.setText(distance+"米");
        tvFirePlaceName.setText(placeData.getPlaceName());
        tvFirePlaceAddress.setText(placeData.getPlaceAddress());
        tvFireAlarmDate.setText("发生时间：" + fireDate);
    }

    /**
     * 设置定位的地图位置
     *
     * @param latitude
     * @param longitude
     * @param zoom
     */
    private void setLocationMap(double latitude, double longitude, float zoom) {
        LatLng ll = new LatLng(latitude, longitude);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(zoom);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            // map view 销毁后不在处理新接收的位置
            if (bdLocation == null || mMapView == null) {
                return;
            }
            mCurrentLat = bdLocation.getLatitude();
            mCurrentLon = bdLocation.getLongitude();
            LogUtils.e("mCurrentLat:" + mCurrentLat + ",mCurrentLon:" + mCurrentLon);
            mCurrentAccracy = bdLocation.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
          /*  if (isFirstLoc) {
                isFirstLoc = false;
                setLocationMap(bdLocation.getLatitude(),bdLocation.getLongitude(),18.0f);
            }*/
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        MediaPlayerAlert.getInstance().setRelease();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);

        if (!MediaPlayerAlert.getInstance().getMediaPlayer(this).isPlaying()) {
            MediaPlayerAlert.getInstance().getMediaPlayer(this).start();
        }
//        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
//            //播放音频
//            mediaPlayer.start();
//        }
    }


    @Override
    protected void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        // 退出时销毁定位
        mLocClient.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtils.e("FireMapActivity ondestroy");
        EventBus.getDefault().unregister(this);
//        if (mediaPlayer != null) {
//            //释放mediaPlayer
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
