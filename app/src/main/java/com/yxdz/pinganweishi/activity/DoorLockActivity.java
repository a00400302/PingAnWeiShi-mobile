package com.yxdz.pinganweishi.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leaf.library.StatusBarUtil;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.load.RetryNetWorkImpl;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.MqttClient;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.adapter.DoorLockBindlistAdapter;
import com.yxdz.pinganweishi.adapter.DoorLockListAdapter;
import com.yxdz.pinganweishi.api.LockApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.BindDeviceBean;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.DoorLockBindBean;
import com.yxdz.pinganweishi.bean.DoorLockListBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;
import com.yxdz.pinganweishi.interfac.OnOKClickListener;
import com.yxdz.pinganweishi.scan.CaptureActivity;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoorLockActivity extends BaseHeadActivity implements XRecyclerView.LoadingListener, RetryNetWorkImpl {
    private SwipeRecyclerView lockList;
    private int placeId;
    private EasyPopup mCirclePop;
    private LinearLayout noDataLayout;
    private List<DoorLockListBean.DoorListBean> listPlace = new ArrayList<>();
    private DoorLockListAdapter doorLockListAdapter;
    private SwipeRefreshLayout refreshLayout;
    private Topic[] topics;
    private UTF8Buffer[] utf8Buffers;
    private ArrayList<Integer> idlist = new ArrayList<>();
    BroadcastReceiver MQTT = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {
            for (DoorLockListBean.DoorListBean doorListBean : listPlace) {
                if ((MqttClient.TOPIC_DEVICE + doorListBean.getDoorLockSn()).equals(intent.getStringExtra("topic"))) {
                    doorLockListAdapter.showDoor(listPlace.indexOf(doorListBean));
                    idlist.add(listPlace.indexOf(doorListBean));
                }
            }

        }
    };
    private int createType;
    private TitleBarView titleBarView;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_door_lock;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this, R.color.primarystart), ContextCompat.getColor(this, R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);
        createType = getIntent().getIntExtra("createType", 1);
        initView();
        initData(this);
    }

    private void initView() {
        lockList = findViewById(R.id.lockList);
        noDataLayout = findViewById(R.id.fire_no_data);

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(DoorLockActivity.this);
            }
        });
        refreshLayout.setColorSchemeResources(R.color.fire_control_theme);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoorLockActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lockList.setLayoutManager(linearLayoutManager);
//        DefaultItemDecoration itemDecoration = new DefaultItemDecoration(Color.GRAY,-1,1);

        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(DoorLockActivity.this)
                        .setText("删除")
                        .setBackground(android.R.color.holo_red_light)
                        .setWidth(230)
                        .setTextColor(Color.WHITE)
                        .setTextSize(20)
                        .setHeight(LinearLayout.LayoutParams.MATCH_PARENT);

                SwipeMenuItem bindItem = new SwipeMenuItem(DoorLockActivity.this)
                        .setText("绑定")
                        .setBackground(android.R.color.holo_orange_light)
                        .setWidth(230)
                        .setTextSize(20)
                        .setTextColor(Color.WHITE)
                        .setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
                rightMenu.addMenuItem(bindItem);
                rightMenu.addMenuItem(deleteItem);
            }
        };


        OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, final int position) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                int menuPosition = menuBridge.getPosition();
                if (menuPosition == 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DoorLockActivity.this)
                            .setTitle("是否删除此设备")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final Map<String, Object> map = new HashMap<>();
                                    map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                                    map.put("id", listPlace.get(position).getId());
                                    RetrofitHelper.subscriber(RetrofitHelper.Https(LockApi.class).deleteDoorLock(map), new RxSubscriber<DefaultBean>(DoorLockActivity.this) {
                                        @Override
                                        protected void onSuccess(DefaultBean cameraListBean) {
                                            if (cameraListBean.getCode() == 0) {
                                                Toast.makeText(DoorLockActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                            }
                                            initData(DoorLockActivity.this);
                                        }
                                    });
                                }
                            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }

                if (menuPosition == 0) {
                    final Map<String, Object> map = new HashMap<>();
                    map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                    map.put("id", listPlace.get(position).getId());
                    map.put("placeId", listPlace.get(position).getPlaceId());
                    RetrofitHelper.subscriber(RetrofitHelper.Https(LockApi.class).deviceBindDoorLockList(map), new RxSubscriber<DoorLockBindBean>(DoorLockActivity.this) {

                        @Override
                        protected void onSuccess(DoorLockBindBean bindDeviceBean) {
                            if (bindDeviceBean.getCode() == 0) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DoorLockActivity.this);
                                alertDialogBuilder.setView(R.layout.bind_camera_custom_dialog);
                                final AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                                RecyclerView bindList = alertDialog.getWindow().findViewById(R.id.bindlist);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoorLockActivity.this);
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                bindList.setLayoutManager(linearLayoutManager);
                                final DoorLockBindlistAdapter bindlistAdapter = new DoorLockBindlistAdapter(DoorLockActivity.this, bindDeviceBean.getData().getDeviceBindList());
                                bindlistAdapter.setOnItemClickListener(new OnOKClickListener() {

                                    @Override
                                    public void onOkClick(List<BindDeviceBean.DataBean.DeviceBindListBean> bindDeviceBeans) {

                                    }

                                    @Override
                                    public void onOkLockClick(List<DoorLockBindBean.DeviceBindListBean> bindDeviceBeans) {
                                        String deviceIds = "";
                                        for (DoorLockBindBean.DeviceBindListBean bindDeviceBean : bindDeviceBeans) {
                                            if (bindDeviceBean.getChecked() == 1) {
                                                deviceIds += bindDeviceBean.getId() + ",";
                                            }
                                        }

                                        final Map<String, Object> map = new HashMap<>();
                                        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                                        map.put("id", listPlace.get(position).getId());
                                        map.put("deviceIds", deviceIds);
                                        RetrofitHelper.subscriber(RetrofitHelper.Https(LockApi.class).updateDoorLock(map), new RxSubscriber<DefaultBean>(DoorLockActivity.this) {

                                            @Override
                                            protected void onSuccess(DefaultBean uploadPlaceBean) {
                                                if (uploadPlaceBean.getCode() == 0) {
                                                    Toast.makeText(DoorLockActivity.this, "修改绑定成功", Toast.LENGTH_SHORT).show();
                                                    alertDialog.dismiss();
                                                } else {
                                                    Toast.makeText(DoorLockActivity.this, uploadPlaceBean.getData(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }

                                });

                                bindList.setAdapter(bindlistAdapter);

                                alertDialog.getWindow().findViewById(R.id.cancel_add).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });
                                alertDialog.getWindow().findViewById(R.id.add_text).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        bindlistAdapter.submit();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        };
        lockList.setOnItemMenuClickListener(mItemMenuClickListener);
        if (createType != 3) {
            lockList.setSwipeMenuCreator(mSwipeMenuCreator);
        }

//        lockList.addItemDecoration(itemDecoration);


        mCirclePop = EasyPopup.create()
                .setContentView(this, R.layout.custom_dialog)
//                .setAnimationStyle(R.style.RightPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                .setFocusAndOutsideEnable(true)
                .apply();
    }

    private void initData(final Context context) {
        placeId = getIntent().getIntExtra("placeId", 0);
        final Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        map.put("placeId", placeId);
        RetrofitHelper.subscriber(RetrofitHelper.Https(LockApi.class).doorLockList(map), new RxSubscriber<DoorLockListBean>(this) {
            @Override
            protected void onSuccess(final DoorLockListBean doorLockListBean) {
                ArrayList<Topic> topicslist = new ArrayList<Topic>();
                ArrayList<UTF8Buffer> buffers = new ArrayList<UTF8Buffer>();
                for (DoorLockListBean.DoorListBean doorListBean : doorLockListBean.getData().getDoorLockList()) {
                    Topic topic = new Topic(MqttClient.TOPIC_DEVICE + doorListBean.getDoorLockSn(), QoS.AT_MOST_ONCE);
                    Topic control = new Topic(MqttClient.TOPIC_CONTROL + doorListBean.getDoorLockSn(), QoS.AT_MOST_ONCE);
                    topicslist.add(topic);
                    topicslist.add(control);
                    buffers.add(new UTF8Buffer(MqttClient.TOPIC_DEVICE + doorListBean.getDoorLockSn()));
                    buffers.add(new UTF8Buffer(MqttClient.TOPIC_CONTROL + doorListBean.getDoorLockSn()));

                }
                utf8Buffers = buffers.toArray(new UTF8Buffer[buffers.size()]);
                topics = topicslist.toArray(new Topic[topicslist.size()]);
                if (MqttClient.getInstance(DoorLockActivity.this).isConnect) {
                    MqttClient.getInstance(DoorLockActivity.this).subscribe(topics);
                }

                netErrorView.removeNetErrorView();
                listPlace.clear();
                listPlace.addAll(doorLockListBean.getData().getDoorLockList());
                if (doorLockListBean.getData().getDoorLockList() != null && doorLockListBean.getData().getDoorLockList().size() > 0) {
                    //有数据
                    lockList.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    if (doorLockListAdapter == null) {
                        doorLockListAdapter = new DoorLockListAdapter(context, listPlace);
                        doorLockListAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
//                                Intent intent = new Intent(DoorLockActivity.this, DoorInfoActivity.class);
//                                intent.putExtra("DeviceSerial", (Serializable) listPlace.get(position));
//                                startActivity(intent);
                            }
                        });
                        lockList.setAdapter(doorLockListAdapter);
                    } else {
                        doorLockListAdapter.notifyDataSetChanged();
                    }
                    if (idlist.size() > 0) {
                        for (Integer integer : idlist) {
                            doorLockListAdapter.showDoor(integer);
                        }

                    }
                } else {
                    //无数据
                    lockList.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }


                context.registerReceiver(MQTT, new IntentFilter("MQTT_DOOR_LOOK"));

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


                LogUtils.e("onError=" + e);
            }

            @Override
            public void onNetError() {
                super.onNetError();
                //无网络
                lockList.setVisibility(View.GONE);
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
        titleBarView.setTitleBarText("电子锁列表");
        if (createType != 3)
            titleBarView.getRightImageView().setImageResource(R.mipmap.add_wh);

        mCirclePop.findViewById(R.id.deviceslayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.dismiss();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DoorLockActivity.this);
                alertDialogBuilder.setView(R.layout.add_lock_custom_dialog);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                alertDialog.setCancelable(false);

                final TextView lockName = alertDialog.getWindow().findViewById(R.id.lockName);


                final TextView lockDeviceSn = alertDialog.getWindow().findViewById(R.id.lockDeviceSn);

                alertDialog.getWindow().findViewById(R.id.cancel_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.getWindow().findViewById(R.id.add_text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lockName.getText().toString().equals("")) {
                            Toast.makeText(DoorLockActivity.this, "字段不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (lockDeviceSn.getText().toString().equals("")) {
                            Toast.makeText(DoorLockActivity.this, "字段不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        final Map<String, Object> map = new HashMap<>();
                        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                        map.put("placeId", placeId);
                        map.put("doorLockSn", lockDeviceSn.getText().toString());
                        map.put("doorLockName", lockName.getText().toString());
                        RetrofitHelper.subscriber(RetrofitHelper.Https(LockApi.class).addDoorLock(map), new RxSubscriber<DefaultBean>(DoorLockActivity.this) {
                            @Override
                            protected void onSuccess(DefaultBean cameraListBean) {
                                Toast.makeText(DoorLockActivity.this, cameraListBean.getData(), Toast.LENGTH_SHORT).show();
                                refreshLayout.setRefreshing(true);
                                initData(DoorLockActivity.this);
                            }
                        });
                        alertDialog.dismiss();
                    }
                });
            }
        });
        mCirclePop.findViewById(R.id.placelayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(DoorLockActivity.this)
                        .setCaptureActivity(CaptureActivity.class)//打开自定义的View
                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                        .setPrompt("请对准二维码")// 设置提示语
                        .setCameraId(0)// 选择摄像头,可使用前置或者后置
                        .setBeepEnabled(true)// 是否开启声音,扫完码之后会"哔"的一声
                        .setBarcodeImageEnabled(true)// 扫完码之后生成二维码的图片
                        .initiateScan();// 初始化扫码
                mCirclePop.dismiss();
            }
        });

        titleBarView.setRightImageViewOnClickListener(new TitleBarView.RightImageViewOnClickListener() {
            @Override
            public void onRightOnClick(View v) {
                mCirclePop.showAtAnchorView(titleBarView.getRightImageView(), YGravity.BELOW, XGravity.ALIGN_RIGHT, 0, 10);
                return;
            }
        });
    }


    @Override
    public void onRefresh() {
        initData(null);
    }

    @Override
    public void onLoadMore() {
        //        lockList.setNoMore(true);
    }

    @Override
    public void retryNetWork() {
        initData(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DoorLockActivity.this);
                alertDialogBuilder.setView(R.layout.add_lock_custom_dialog);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                final EditText lockName = alertDialog.getWindow().findViewById(R.id.lockName);


                final EditText lockDeviceSn = alertDialog.getWindow().findViewById(R.id.lockDeviceSn);

                alertDialog.getWindow().findViewById(R.id.cancel_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                try {
                    JSONObject jsonObject = new JSONObject(data.getStringExtra(Intents.Scan.RESULT));
                    lockDeviceSn.setText(jsonObject.getString("ID"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertDialog.getWindow().findViewById(R.id.add_text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lockName.getText().toString().equals("")) {
                            Toast.makeText(DoorLockActivity.this, "字段不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (lockDeviceSn.getText().toString().equals("")) {
                            Toast.makeText(DoorLockActivity.this, "字段不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        final Map<String, Object> map = new HashMap<>();
                        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                        map.put("placeId", placeId);
                        map.put("doorLockSn", lockDeviceSn.getText().toString());
                        map.put("doorLockName", lockName.getText().toString());
                        RetrofitHelper.subscriber(RetrofitHelper.Https(LockApi.class).addDoorLock(map), new RxSubscriber<DefaultBean>(DoorLockActivity.this) {
                            @Override
                            protected void onSuccess(DefaultBean cameraListBean) {
                                Toast.makeText(DoorLockActivity.this, cameraListBean.getData(), Toast.LENGTH_SHORT).show();
                                refreshLayout.setRefreshing(true);
                                initData(DoorLockActivity.this);
                            }
                        });
                        alertDialog.dismiss();
                    }
                });
            }
        }
    }


    @Override
    protected void onDestroy() {
        MqttClient.getInstance(this).unSubscribe(utf8Buffers);
        unregisterReceiver(MQTT);
        super.onDestroy();
    }


}
