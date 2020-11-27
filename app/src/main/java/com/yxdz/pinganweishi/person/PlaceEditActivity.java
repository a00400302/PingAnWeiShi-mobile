package com.yxdz.pinganweishi.person;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.yxdz.pinganweishi.activity.PlaceAddActivity;
import com.yxdz.pinganweishi.adapter.PlaceEditAdapter;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.FirePlaceBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlaceEditActivity extends BaseHeadActivity implements XRecyclerView.LoadingListener, RetryNetWorkImpl {


    CustomRecyclerView placeEditList;
    private PlaceEditAdapter placeEditAdapter;
    private CardView deleteLayout;
    private TextView cancel;
    private TextView delete;
    private RadioButton selectAll;
    private LinearLayout selectAllLayout;
    private String areaId = "";


    private LinearLayout noDataLayout;
    private List<FirePlaceBean.ListPlaceBean> listPlace = new ArrayList<>();
    private TitleBarView titleBarView;


    @Override
    public int getLayoutResource() {
        return R.layout.activity_place_edit;
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
        initData(this, null);
    }

    private void initData(Context context, final String areaId) {
        this.areaId = areaId;
        Map<String, Object> map = new HashMap<>();
//        if (!TextUtils.isEmpty(areaId)) {
//            map.put(ActValue.AreaId, areaId);
//        }
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        map.put("placeType", "1");//场所类型（1，烟感场所；2 巡检场所）
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getFireControlQueryPlace(map), new RxSubscriber<FirePlaceBean>(context) {
            @Override
            protected void onSuccess(FirePlaceBean firePlaceBean) {
                netErrorView.removeNetErrorView();
                listPlace.clear();
                listPlace.addAll(firePlaceBean.getData().getListPlace());
                if (firePlaceBean.getData().getListPlace() != null && firePlaceBean.getData().getListPlace().size() > 0) {
                    //有数据
                    placeEditList.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    if (placeEditAdapter == null) {
                        placeEditAdapter = new PlaceEditAdapter(PlaceEditActivity.this, listPlace);
                        placeEditList.setAdapter(placeEditAdapter);
                        placeEditAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(PlaceEditActivity.this, PlaceDetailActivity.class);
                                intent.putExtra("listPlaceBean", (Serializable) listPlace.get(position));
                                startActivityForResult(intent, 123);
                            }
                        });
                    } else {
                        placeEditAdapter.notifyDataSetChanged();
                    }
                    placeEditList.refreshComplete();
                } else {
                    //无数据
                    placeEditList.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                //message：链接超时
                LogUtils.e("onFailure=" + message);
                placeEditList.setPullRefreshEnabled(false);
                placeEditList.setLoadingMoreEnabled(false);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //网络错误e：java.net.ConnectException
                LogUtils.e("onError=" + e);
                placeEditList.refreshComplete();
                placeEditList.loadMoreComplete();
            }

            @Override
            public void onNetError() {
                super.onNetError();
                //无网络
                placeEditList.setVisibility(View.GONE);
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
    protected void onResume() {
        super.onResume();
        initData(this, areaId);
    }

    private void initView() {
        noDataLayout = findViewById(R.id.fire_no_data);
        placeEditList = findViewById(R.id.place_edit_list);
        selectAll = findViewById(R.id.select_all_btn);
        deleteLayout = findViewById(R.id.delete_layout);
        cancel = findViewById(R.id.cancel_delete);
        delete = findViewById(R.id.delete_btn);
        selectAllLayout = findViewById(R.id.select_all_layout);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        placeEditList.setLayoutManager(linearLayoutManager);
        placeEditList.setLoadingListener(this);
        placeEditList.setLoadingMoreEnabled(false);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLayout.setVisibility(View.GONE);
                titleBarView.getRightImageView().setVisibility(View.VISIBLE);
                titleBarView.setTitleBarText("场所列表");
                placeEditAdapter.statusChange(1);
                placeEditAdapter.notifyDataSetChanged();
                selectAllLayout.setVisibility(View.GONE);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlaceEditActivity.this);
                alertDialogBuilder.setView(R.layout.delete_dialog);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                alertDialog.getWindow().findViewById(R.id.cancel_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.getWindow().findViewById(R.id.add_text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        placeEditAdapter.delete();
                        deleteLayout.setVisibility(View.GONE);
                        titleBarView.getRightImageView().setVisibility(View.VISIBLE);
                        titleBarView.setTitleBarText("场所列表");
                        placeEditAdapter.statusChange(1);
                        placeEditAdapter.notifyDataSetChanged();
                        selectAllLayout.setVisibility(View.GONE);
                        alertDialog.dismiss();
                        if (placeEditAdapter.listPlace.isEmpty()) {
                            noDataLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        selectAllLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectAll.isChecked()) {
                    selectAll.setChecked(false);
                    placeEditAdapter.unSelectAll();
                    placeEditAdapter.statusChange(3);
                    placeEditAdapter.notifyDataSetChanged();
                } else {
                    selectAll.setChecked(true);
                    placeEditAdapter.selectAll();
                    placeEditAdapter.statusChange(3);
                    placeEditAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("场所列表");
        titleBarView.getRightImageView().setImageResource(R.mipmap.add_wh);

        final EasyPopup mCirclePop;
        mCirclePop = EasyPopup.create()
                .setContentView(this, R.layout.custom_plaec_dialog)
//                .setAnimationStyle(R.style.RightPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                .setFocusAndOutsideEnable(true)
                .apply();


        mCirclePop.findViewById(R.id.deviceslayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlaceEditActivity.this, PlaceAddActivity.class));
                mCirclePop.dismiss();
            }
        });

        mCirclePop.findViewById(R.id.placelayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (placeEditAdapter != null) {
                    deleteLayout.setVisibility(View.VISIBLE);
                    titleBarView.getRightImageView().setVisibility(View.GONE);
                    titleBarView.setTitleBarText("删除场所");

                    placeEditAdapter.statusChange(0);
                    placeEditAdapter.notifyDataSetChanged();
                    selectAllLayout.setVisibility(View.VISIBLE);
                    selectAll.setChecked(false);
                    mCirclePop.dismiss();
                } else {
                    Toast.makeText(PlaceEditActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }
            }
        });


        titleBarView.setRightImageViewOnClickListener(new TitleBarView.RightImageViewOnClickListener() {
            @Override
            public void onRightOnClick(View v) {
                mCirclePop.showAtAnchorView(titleBarView.getRightImageView(), YGravity.BELOW, XGravity.ALIGN_RIGHT, 0, 10);
            }
        });
    }


    @Override
    public void onRefresh() {
        initData(null, "refresh");
    }

    @Override
    public void onLoadMore() {
        placeEditList.setNoMore(true);
    }

    @Override
    public void retryNetWork() {
        initData(this, areaId);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        placeEditList.refresh();
    }
}
