package com.yxdz.pinganweishi.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.client.android.Intents;
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
import com.yxdz.pinganweishi.adapter.FireDeviceAdapter;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.FirePlaceBean.ListPlaceBean;
import com.yxdz.pinganweishi.bean.FireSmokeEquipmentBean;
import com.yxdz.pinganweishi.bean.FireSmokeEquipmentBean.ListSmokeEquipmentBean;
import com.yxdz.pinganweishi.fragment.FragmentFire;
import com.yxdz.pinganweishi.scan.CaptureActivity;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.pinganweishi.view.NetErrorView;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 设备列表
 */
public class FireDeviceActivity extends BaseHeadActivity implements XRecyclerView.LoadingListener, RetryNetWorkImpl {
    public final static int FireDeviceRequestCode = 1;
    public final static int FireDeviceResultCode = 1;
    private boolean flag = false;
    private CustomRecyclerView mRecyclerView;
    private List<ListSmokeEquipmentBean> listSmokeEquipmentBeanList = new ArrayList<>();
    private FireDeviceAdapter fireDeviceAdapter;
    private TitleBarView titleBarView;

    private LinearLayout noDataLayout;
    private NetErrorView netErrorView;
    private ListPlaceBean listPlaceBean;
    private EasyPopup mCirclePop;
    private String startTimedata = "00:00";
    private String startTimedata2 = "";
    private String endTimedata = "23:59";
    private String endTimedata2 = "";

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_fire_device;
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
        initData(this);
    }

    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("探测器列表");
        titleBarView.getRightImageView().setBackground(getResources().getDrawable(R.mipmap.add_wh));
        if (listPlaceBean.getCreateType() == 3) {
            titleBarView.getRightImageView().setVisibility(View.GONE);
        } else {
            titleBarView.getRightImageView().setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        //无网络处理
        netErrorView = new NetErrorView.Builder(this, ((ViewGroup) findViewById(R.id.fire_device_layout))).setRetryNetWorkImpl(this).create();


        mRecyclerView = findViewById(R.id.fire_device_recyclerview);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FireDeviceActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.setLoadingMoreEnabled(false);

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

        noDataLayout = findViewById(R.id.fire_device_no_data);
        noDataLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPlaceBean.getCreateType() == 3) {
//                    titleBarView.getRightImageView().setVisibility(View.GONE);
                } else {
                    mCirclePop.showAtAnchorView(titleBarView.getRightImageView(), YGravity.BELOW, XGravity.ALIGN_RIGHT, 0, 10);
                }

            }
        });

        mCirclePop.findViewById(R.id.deviceslayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FireDeviceActivity.this, AddFireDeviceActivity.class);
                intent.putExtra("placeId", listPlaceBean.getId());
                intent.putExtra("type", 1);
                startActivityForResult(intent, FireDeviceResultCode);
                mCirclePop.dismiss();
            }
        });
        mCirclePop.findViewById(R.id.placelayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(FireDeviceActivity.this)
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
            }
        });
    }

    private void initData(Context context) {

        listPlaceBean = (ListPlaceBean) getIntent().getSerializableExtra("listPlaceBean");

        Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
//        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        map.put(ActValue.PlaceId, listPlaceBean.getId());
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getFireControlListSmokeEquipment(map), new RxSubscriber<FireSmokeEquipmentBean>(context) {
            @Override
            protected void onSuccess(FireSmokeEquipmentBean fireSmokeEquipmentBean) {
                LogUtils.e("fireSmokeEquipmentBean=" + fireSmokeEquipmentBean);
                netErrorView.removeNetErrorView();
                listSmokeEquipmentBeanList.clear();
                listSmokeEquipmentBeanList.addAll(fireSmokeEquipmentBean.getData().getListSmokeEquipment());
                if (listSmokeEquipmentBeanList != null && listSmokeEquipmentBeanList.size() > 0) {
                    //有数据
                    mRecyclerView.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    if (fireDeviceAdapter == null) {

                        fireDeviceAdapter = new FireDeviceAdapter(FireDeviceActivity.this, listSmokeEquipmentBeanList, listPlaceBean.getCreateType(), listPlaceBean.getLat(), listPlaceBean.getLng());
                        fireDeviceAdapter.setPlaceAddress(listPlaceBean.getPlaceAddress());
                        fireDeviceAdapter.setPlaceName(listPlaceBean.getPlaceName());
                        mRecyclerView.setAdapter(fireDeviceAdapter);
                    } else {
                        fireDeviceAdapter.setPlaceAddress(listPlaceBean.getPlaceAddress());
                        fireDeviceAdapter.setPlaceName(listPlaceBean.getPlaceName());
                        fireDeviceAdapter.notifyDataSetChanged();
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
                netErrorView.addNetErrorView();
            }

            @Override
            protected void onOtherError(String code) {
                super.onOtherError(code);
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
            }
        });


        ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition() - 1;
                AlertDialog.Builder builder = new AlertDialog.Builder(FireDeviceActivity.this)
                        .setTitle("是否删除此设备")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (listSmokeEquipmentBeanList.get(position).getWarning() == 2) {
                                    Toast.makeText(FireDeviceActivity.this, "请处理警报后重试", Toast.LENGTH_SHORT).show();
                                    mRecyclerView.refresh();
                                } else {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                                    map.put("placeId", listSmokeEquipmentBeanList.get(position).getPlaceId());
                                    map.put("deviceSn", listSmokeEquipmentBeanList.get(position).getSn());
                                    RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).deleteDevice(map), new RxSubscriber<DefaultBean>(FireDeviceActivity.this) {
                                        @Override
                                        protected void onSuccess(DefaultBean contactsBean) {
                                            mRecyclerView.refresh();
                                        }
                                    });
                                }
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mRecyclerView.refresh();
                            }
                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                mRecyclerView.refresh();
                            }
                        });
                builder.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        if (listPlaceBean.getCreateType() != 3)
            itemTouchHelper.attachToRecyclerView(mRecyclerView);


    }

    @Override
    public void onRefresh() {
        initData(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.refresh();
    }

    @Override
    public void onLoadMore() {
        mRecyclerView.setNoMore(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FireDeviceRequestCode && resultCode == FireDeviceResultCode) {
            //报警处理成功
            initData(null);
            setResult(FragmentFire.FragmentFireResultCode);
        }
        Log.d("FireDeviceActivity", "requestCode:" + requestCode);


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "已取消", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(FireDeviceActivity.this, AddFireDeviceActivity.class);
                intent.putExtra("placeId", listPlaceBean.getId());
                intent.putExtra("type", 0);
                intent.putExtra("deviceSn", data.getStringExtra(Intents.Scan.RESULT));
                startActivityForResult(intent, FireDeviceResultCode);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRecyclerView != null) {
            mRecyclerView.destroy(); // this will totally release XR's memory
            mRecyclerView = null;
        }
    }

    @Override
    public void retryNetWork() {
        initData(this);
    }


}





