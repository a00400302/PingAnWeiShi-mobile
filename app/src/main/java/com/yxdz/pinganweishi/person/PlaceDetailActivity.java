package com.yxdz.pinganweishi.person;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.leaf.library.StatusBarUtil;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.FirePlaceBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;

import java.util.HashMap;
import java.util.Map;

public class PlaceDetailActivity extends BaseHeadActivity {


    private TextView placeName;
    private TextView placeArea;
    private TextView placeDetail;
    private TextView placeStatus;

    private Button share;
    private FirePlaceBean.ListPlaceBean listPlaceBean;
    private TitleBarView titleBarView;
    private ImageView picture;
    private int REQUEST_CAMERA_CODE = 321;
    private TextView change;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_place_detail;
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

    private void initView() {
        placeName = findViewById(R.id.placeName);
        placeArea = findViewById(R.id.placeArea);
        placeDetail = findViewById(R.id.placeDetail);
        placeStatus = findViewById(R.id.placeStatus);
        share = findViewById(R.id.share_btn);
        picture = findViewById(R.id.picture);
        change = findViewById(R.id.change);

//
//        call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                Uri data = Uri.parse("tel:" + UserInfoBean.getInstance().getAccount());
//                intent.setData(data);
//                startActivity(intent);
//            }
//        });


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlaceDetailActivity.this);
                AlertDialog alertDialog = builder.setView(R.layout.edit_address).create();
                alertDialog.show();
                final EditText placeAddress = alertDialog.findViewById(R.id.address);
                Button upload = alertDialog.findViewById(R.id.upload);


                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (placeAddress.getText().toString().isEmpty()) {
                            Toast.makeText(PlaceDetailActivity.this, "请输入地址", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Map<String, Object> map = new HashMap<>();
                        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                        map.put("placeAddress", placeAddress.getText().toString());
                        map.put(ActValue.PlaceId, listPlaceBean.getId());
                        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).updatePlace(map), new RxSubscriber<DefaultBean>(PlaceDetailActivity.this) {
                            @Override
                            protected void onSuccess(DefaultBean defaultBean) {
                                netErrorView.removeNetErrorView();
                                if (defaultBean.getCode() == 0) {
                                    setResult(123);
                                    PlaceDetailActivity.this.finish();
                                }
                                Toast.makeText(PlaceDetailActivity.this, defaultBean.getData(), Toast.LENGTH_SHORT).show();
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
                                netErrorView.addNetErrorView();
                            }

                            @Override
                            protected void onOtherError(String code) {
                                super.onOtherError(code);
                                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
                            }
                        });
                    }
                });
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceDetailActivity.this, QrActivity.class);
                intent.putExtra("listPlaceBean", listPlaceBean);
                startActivity(intent);
            }
        });
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });


        findViewById(R.id.edit_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlaceDetailActivity.this);
                AlertDialog alertDialog = builder.setView(R.layout.edit_user).create();
                alertDialog.show();
                final EditText name = alertDialog.findViewById(R.id.name);
                final EditText phone = alertDialog.findViewById(R.id.phone);
                Button upload = alertDialog.findViewById(R.id.upload);


                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name.getText().toString().isEmpty()) {
                            Toast.makeText(PlaceDetailActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (phone.getText().toString().isEmpty()) {
                            Toast.makeText(PlaceDetailActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Map<String, Object> map = new HashMap<>();
                        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                        map.put("phone", phone.getText().toString());
                        map.put("userName", name.getText().toString());
                        map.put(ActValue.PlaceId, listPlaceBean.getId());
                        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).updatePlace(map), new RxSubscriber<DefaultBean>(PlaceDetailActivity.this) {
                            @Override
                            protected void onSuccess(DefaultBean defaultBean) {
                                netErrorView.removeNetErrorView();
                                setResult(123);
                                PlaceDetailActivity.this.finish();
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
                                netErrorView.addNetErrorView();
                            }

                            @Override
                            protected void onOtherError(String code) {
                                super.onOtherError(code);
                                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
                            }
                        });
                    }
                });
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void initData(Context context) {
        listPlaceBean = (FirePlaceBean.ListPlaceBean) getIntent().getSerializableExtra("listPlaceBean");
        placeArea.setText(listPlaceBean.getAdministrativeRegions());
        if (listPlaceBean.getPlaceimgurl() != null) {
            Uri imageUri = Uri.parse(listPlaceBean.getPlaceimgurl());
            Glide.with(this).load(imageUri).into(picture);
        }
        placeName.setText(listPlaceBean.getPlaceName());
        placeDetail.setText(listPlaceBean.getPlaceAddress());
        switch (listPlaceBean.getStatus()) {
            case 0:
                placeStatus.setText("正常");
                placeStatus.setTextColor(getResources().getColor(R.color.fire_control_theme));
                break;
            case 1:
                placeStatus.setText("在线");
                break;
            case 2:
                placeStatus.setTextColor(getResources().getColor(R.color.red));
                placeStatus.setText("警报");
                break;
            case 3:
                placeStatus.setText("合格");
                break;
            case 4:
                placeStatus.setText("不合格");
                break;
        }
        if (listPlaceBean.getCreateType() == 3) {
            share.setVisibility(View.GONE);
        }
        if (listPlaceBean.getCreateType() == 3) {
            findViewById(R.id.edit_user).setVisibility(View.GONE);
        }
    }


    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("场所详情");
        titleBarView.getRightImageView().setImageResource(R.mipmap.contact_wh);
        titleBarView.getRightImageView().setVisibility(View.VISIBLE);
        titleBarView.getRightImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlaceDetailActivity.this, EditContactsActivity.class);
                intent.putExtra("listPlaceBean", listPlaceBean);
                startActivity(intent);
            }
        });
    }
}