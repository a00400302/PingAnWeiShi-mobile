package com.yxdz.pinganweishi.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.adapter.BindlistAdapter;
import com.yxdz.pinganweishi.adapter.CameraListAdapter;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.BindDeviceBean;
import com.yxdz.pinganweishi.bean.CameraListBean;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.DoorLockBindBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;
import com.yxdz.pinganweishi.interfac.OnOKClickListener;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxkj.yunshicamera.realplay.EZRealPlayActivity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CameraListActivity extends BaseHeadActivity implements XRecyclerView.LoadingListener, RetryNetWorkImpl {

    private SwipeRecyclerView cameraList;
    private int placeId;
    private LinearLayout noDataLayout;
    private List<CameraListBean.DataBean.CameraBean> listPlace = new ArrayList<>();
    private CameraListAdapter cameraListAdapter;
    private SwipeRefreshLayout refreshLayout;
    private int createType;
    private TitleBarView titleBarView;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_camera_list;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this,R.color.primarystart), ContextCompat.getColor(this,R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);
        createType = getIntent().getIntExtra("createType", 1);
        initView();
        initData(this);
    }


    private void initView() {
        cameraList = findViewById(R.id.cameraList);
        noDataLayout = findViewById(R.id.fire_no_data);

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(CameraListActivity.this);
            }
        });
        refreshLayout.setColorSchemeResources(R.color.fire_control_theme);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CameraListActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cameraList.setLayoutManager(linearLayoutManager);


        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(CameraListActivity.this)
                        .setText("删除")
                        .setBackground(android.R.color.holo_red_light)
                        .setWidth(230)
                        .setTextColor(Color.WHITE)
                        .setTextSize(20)
                        .setHeight(LinearLayout.LayoutParams.MATCH_PARENT);

                SwipeMenuItem bindItem = new SwipeMenuItem(CameraListActivity.this)
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(CameraListActivity.this)
                            .setTitle("是否删除此设备")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final Map<String, Object> map = new HashMap<>();
                                    map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                                    map.put("id", listPlace.get(position).getId());
                                    RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).deleteCamera(map), new RxSubscriber<DefaultBean>(CameraListActivity.this) {
                                        @Override
                                        protected void onSuccess(DefaultBean cameraListBean) {
                                            if (cameraListBean.getCode() == 0) {
                                                Toast.makeText(CameraListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                            }
                                            initData(CameraListActivity.this);
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
                    RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).deviceBindList(map), new RxSubscriber<BindDeviceBean>(CameraListActivity.this) {

                        @Override
                        protected void onSuccess(BindDeviceBean bindDeviceBean) {
                            if (bindDeviceBean.getCode() == 0) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CameraListActivity.this);
                                alertDialogBuilder.setView(R.layout.bind_camera_custom_dialog);
                                final AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                                RecyclerView bindList = alertDialog.getWindow().findViewById(R.id.bindlist);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CameraListActivity.this);
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                bindList.setLayoutManager(linearLayoutManager);
                                final BindlistAdapter bindlistAdapter = new BindlistAdapter(CameraListActivity.this, bindDeviceBean.getData().getDeviceBindList());
                                bindlistAdapter.setOnItemClickListener(new OnOKClickListener() {
                                    @Override
                                    public void onOkClick(List<BindDeviceBean.DataBean.DeviceBindListBean> bindDeviceBeans) {
                                        StringBuilder deviceIds = new StringBuilder();
                                        for (BindDeviceBean.DataBean.DeviceBindListBean bindDeviceBean : bindDeviceBeans) {
                                            if (bindDeviceBean.getChecked() == 1) {
                                                deviceIds.append(bindDeviceBean.getId()).append(",");
                                            }
                                        }


                                        final Map<String, Object> map = new HashMap<>();
                                        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                                        map.put("id", listPlace.get(position).getId());
                                        map.put("deviceIds", deviceIds.toString());
                                        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).updateCamera(map), new RxSubscriber<DefaultBean>(CameraListActivity.this) {

                                            @Override
                                            protected void onSuccess(DefaultBean uploadPlaceBean) {
                                                if (uploadPlaceBean.getCode() == 0) {
                                                    Toast.makeText(CameraListActivity.this, "修改绑定成功", Toast.LENGTH_SHORT).show();
                                                    alertDialog.dismiss();
                                                } else {
                                                    Toast.makeText(CameraListActivity.this, uploadPlaceBean.getData(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }

                                    @Override
                                    public void onOkLockClick(List<DoorLockBindBean.DeviceBindListBean> bindDeviceBeans) {

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
        cameraList.setOnItemMenuClickListener(mItemMenuClickListener);
        if (createType != 3) {
            cameraList.setSwipeMenuCreator(mSwipeMenuCreator);
        }
    }

    private void initData(final CameraListActivity cameraListActivity) {
        placeId = getIntent().getIntExtra("placeId", 0);

        final Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        map.put("placeId", placeId);
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getCameraList(map), new RxSubscriber<CameraListBean>(this) {
            @Override
            protected void onSuccess(final CameraListBean cameraListBean) {
                netErrorView.removeNetErrorView();
                listPlace.clear();
                listPlace.addAll(cameraListBean.getData().getCameraList());
                if (cameraListBean.getData().getCameraList() != null && cameraListBean.getData().getCameraList().size() > 0) {
                    //有数据
                    cameraList.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    if (cameraListAdapter == null) {
                        cameraListAdapter = new CameraListAdapter(cameraListActivity, listPlace);
                        cameraListAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(CameraListActivity.this, EZRealPlayActivity.class);
                                intent.putExtra("DeviceSerial", listPlace.get(position).getDeviceSerial());
                                intent.putExtra("CameraName", listPlace.get(position).getCameraName());
                                intent.putExtra("mVerifyCode", listPlace.get(position).getValidateCode());
                                startActivity(intent);
                            }
                        });
                        cameraList.setAdapter(cameraListAdapter);
                    } else {
                        cameraListAdapter.notifyDataSetChanged();
                    }
//                    cameraList.refreshComplete();
                } else {
                    //无数据
                    cameraList.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
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
                //无网络
                cameraList.setVisibility(View.GONE);
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
        titleBarView.setTitleBarText("摄像头列表");
        if (createType != 3) {
            titleBarView.getRightImageView().setVisibility(View.VISIBLE);
            titleBarView.getRightImageView().setImageResource(R.mipmap.add_wh);
            titleBarView.getRightImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CameraListActivity.this);
                    alertDialogBuilder.setView(R.layout.add_camera_custom_dialog);
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    alertDialog.setCancelable(false);

                    final TextView cameraName = alertDialog.getWindow().findViewById(R.id.cameraName);

                    final TextView cameraCode = alertDialog.getWindow().findViewById(R.id.cameraCode);

                    final TextView cameraDeviceSn = alertDialog.getWindow().findViewById(R.id.cameraDeviceSn);

                    alertDialog.getWindow().findViewById(R.id.cancel_add).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.getWindow().findViewById(R.id.add_text).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (cameraName.getText().toString().equals("")) {
                                Toast.makeText(CameraListActivity.this, "字段不能为空", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (cameraCode.getText().toString().equals("")) {
                                Toast.makeText(CameraListActivity.this, "字段不能为空", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (cameraDeviceSn.getText().toString().equals("")) {
                                Toast.makeText(CameraListActivity.this, "字段不能为空", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            final Map<String, Object> map = new HashMap<>();
                            map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                            map.put("placeId", placeId);
                            map.put("deviceSerial", cameraDeviceSn.getText().toString());
                            map.put("validateCode", cameraCode.getText().toString());
                            map.put("cameraName", cameraName.getText().toString());
                            RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).addCamera(map), new RxSubscriber<DefaultBean>(CameraListActivity.this) {
                                @Override
                                protected void onSuccess(DefaultBean cameraListBean) {
                                    Toast.makeText(CameraListActivity.this, cameraListBean.getData(), Toast.LENGTH_SHORT).show();
//                                cameraList.refresh();
                                    initData(CameraListActivity.this);
                                }
                            });
                            alertDialog.dismiss();
                        }
                    });
                }
            });
        }


    }

    @Override
    public void onRefresh() {
        initData(null);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void retryNetWork() {
        initData(this);
    }
}


