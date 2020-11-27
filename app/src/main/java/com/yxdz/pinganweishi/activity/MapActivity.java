package com.yxdz.pinganweishi.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.hardware.SensorManager;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.leaf.library.StatusBarUtil;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.adapter.MapAdapter;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.MapBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class MapActivity extends BaseHeadActivity {

    private TitleBarView toolbar;
    private MapView map_control_mapView;
    private View map_reload;
    private EditText map_search;
    private View map_clear_cancel;
    private RecyclerView map_recycler_view;
    private BaiduMap mBaiduMap;
    private boolean isFirstLocation = true;
    private SensorManager mSensorManager;
    private LocationClient mLocClient;
    private GeoCoder geoCoder;
    private String sematicDescription;
    private LatLng settingLocation;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private LatLng cenpt;
    private PoiSearch mPoiSearch;
    private SuggestionSearch mSuggestionSearch;
    private String city;
    private ArrayList<MapBean> mapBeans;
    private MapAdapter mapAdapter;
    private double lat;
    private String name;
    private double lnt;
    private TextWatcher watcher;
    private View map_clear_search;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_map;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        toolbar = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this, R.color.primarystart), ContextCompat.getColor(this, R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        toolbar.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, toolbar);
        initView();
        initData();
    }


    private void initView() {
        map_control_mapView = findViewById(R.id.map_control_mapView);
        map_reload = findViewById(R.id.map_reload);
        map_search = findViewById(R.id.map_search);
        map_clear_cancel = findViewById(R.id.map_clear_cancel);
        map_recycler_view = findViewById(R.id.map_recycler_view);
        map_clear_search = findViewById(R.id.map_clear_search);

    }

    private void initData() {
        initMap();
        map_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUserMapCenter();
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(cenpt);
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        });
        watcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                } else {
                }
            }

            public void afterTextChanged(Editable editable) {
                if (!map_search.getText().toString().equals("")) {
                    mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                            .city(city)
                            .keyword(map_search.getText().toString()));
                }

            }
        };
        map_search.addTextChangedListener(watcher);

        map_clear_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("mapLat", lat);
                intent.putExtra("mapName", name);
                intent.putExtra("mapLnt", lnt);
                setResult(123, intent);
                finish();
            }
        });


        map_clear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map_search.removeTextChangedListener(watcher);
                map_search.setText("");
                map_search.addTextChangedListener(watcher);
            }
        });
    }

    @Override
    protected void setTitlebarView() {

    }

    private void initMap() {

        mBaiduMap = map_control_mapView.getMap();
        map_control_mapView.showZoomControls(false);
        //获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(0);


        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        map_control_mapView.showZoomControls(false);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(new MyLocationListenner());


        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationPoiList(true);
        mLocClient.setLocOption(option);
        mLocClient.start();


        geoCoder = GeoCoder.newInstance();
        mPoiSearch = PoiSearch.newInstance();


        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                List<PoiInfo> allPoi = poiResult.getAllPoi();
                if (allPoi != null) {
                    for (PoiInfo poiInfo : allPoi) {
                        Log.d("test", "onGetPoiResult: " + poiInfo.getName());
                    }
                }

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }

            //废弃
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        });
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {


            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult != null) {
                    sematicDescription = reverseGeoCodeResult.getSematicDescription();
                    if (reverseGeoCodeResult.getAddressDetail() != null) {
                        city = reverseGeoCodeResult.getAddressDetail().city;
                        Log.d("PlaceAddActivity", "reverseGeoCodeResult.getAddressDetail():" + reverseGeoCodeResult.getAddressDetail());
                        List<PoiInfo> poiList = reverseGeoCodeResult.getPoiList();
                        mapBeans = new ArrayList<>();
                        for (PoiInfo poiInfo : poiList) {
                            Log.d("test", "onGetReverseGeoCodeResult: " + poiInfo.getName());
                            MapBean mapBean = new MapBean();
                            mapBean.setName(poiInfo.getAddress());
                            mapBean.setLat(poiInfo.location.latitude);
                            mapBean.setLnt(poiInfo.location.longitude);
                            mapBeans.add(mapBean);
                        }
                        mapAdapter = new MapAdapter(mapBeans);
                        mapAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                name = mapBeans.get(position).getName();
                                map_search.removeTextChangedListener(watcher);
                                map_search.setText(name);
                                map_search.addTextChangedListener(watcher);
                                lat = mapBeans.get(position).getLat();
                                lnt = mapBeans.get(position).getLnt();
                                setLocation(lat, lnt);
                            }
                        });
                        map_recycler_view.setLayoutManager(new LinearLayoutManager(MapActivity.this));
                        map_recycler_view.setAdapter(mapAdapter);
                    }
                }
            }
        });


        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                //处理sug检索结果
                if (!suggestionResult.getAllSuggestions().isEmpty()) {
                    mapBeans = new ArrayList<>();
                    for (SuggestionResult.SuggestionInfo allSuggestion : suggestionResult.getAllSuggestions()) {
                        if (allSuggestion.getPt() != null) {
                            MapBean mapBean = new MapBean();
                            mapBean.setName(allSuggestion.getKey());
                            mapBean.setLat(allSuggestion.getPt().latitude);
                            mapBean.setLnt(allSuggestion.getPt().longitude);
                            mapBeans.add(mapBean);
                        }
                    }
                    mapAdapter = new MapAdapter(mapBeans);
                    mapAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
//                            name = mapBeans.get(position).getName();
//                            lat = mapBeans.get(position).getLat();
//                            lnt = mapBeans.get(position).getLnt();
//                            setLocation(lat, lnt);

                            name = mapBeans.get(position).getName();
                            map_search.removeTextChangedListener(watcher);
                            map_search.setText(name);
                            map_search.addTextChangedListener(watcher);
                            lat = mapBeans.get(position).getLat();
                            lnt = mapBeans.get(position).getLnt();
                            setLocation(lat, lnt);
                        }
                    });
                    map_recycler_view.setLayoutManager(new LinearLayoutManager(MapActivity.this));
                    map_recycler_view.setAdapter(mapAdapter);
                }
            }
        });
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {


            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                settingLocation = mapStatus.target;
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(settingLocation));
            }
        });


    }

    private void setUserMapCenter() {
        cenpt = new LatLng(mCurrentLat, mCurrentLon);
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    private void setLocation(double lat, double lon) {
        cenpt = new LatLng(lat, lon);
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    public class MyLocationListenner extends BDAbstractLocationListener {

        private MyLocationData locData;


        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || map_control_mapView == null) {
                return;
            }
            mCurrentLat = bdLocation.getLatitude();
            mCurrentLon = bdLocation.getLongitude();
            LogUtils.e("mCurrentLat:" + mCurrentLat + ",mCurrentLon:" + mCurrentLon);
            mCurrentAccracy = bdLocation.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(mCurrentDirection).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            city = bdLocation.getCity();

            if (isFirstLocation) {
                isFirstLocation = false;
                setUserMapCenter();
            }
        }


    }
}
