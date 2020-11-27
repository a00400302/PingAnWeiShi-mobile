package com.yxdz.pinganweishi.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.leaf.library.StatusBarUtil;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.bean.YxdzInfo;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.adapter.SelectPicAdapter;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.api.InspectionApi;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.SelectPicBean;
import com.yxdz.pinganweishi.bean.UserInfoBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class InspectionSubmitDialog extends Activity implements View.OnClickListener {

    private static final int REQUEST_CODE = 110;
    private Context context;
    private TextView tvPic;
    private TextView tvRemark;
    private LinearLayout radioGroup;
    private ImageView radioButton1, radioButton2;
    private Button btnSubmit;
    private int isQualified = 2;//0整改报告，1合格，2不合格
    private GridView gridView;
    private List<SelectPicBean> selectPicBeanList = new ArrayList<>();
    private SelectPicAdapter selectPicAdapter;
    private int type;//1企业，2巡检
    private UserInfoBean userInfoBean;
    private TitleBarView titleBarView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        View view = View.inflate(context, R.layout.inspection_detail_dialog, null);
        setContentView(view);
        titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this, R.color.primarystart), ContextCompat.getColor(this, R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);

        gridView = view.findViewById(R.id.inspection_device_gridview);
        radioGroup = view.findViewById(R.id.inspection_device_radiogroup);
        radioButton1 = view.findViewById(R.id.inspection_device_radiobutton1);
        radioButton2 = view.findViewById(R.id.inspection_device_radiobutton2);
        tvPic = view.findViewById(R.id.inspection_device_tv);
        tvRemark = view.findViewById(R.id.inspection_device_remark);

        btnSubmit = view.findViewById(R.id.inspection_device_submit);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);

        btnSubmit.setOnClickListener(this);


        radioButton2.setImageResource(R.mipmap.radio_choseed);


        userInfoBean = UserInfoBean.getInstance();
        //登录类型(1：系统超级管理员， 2：系统普通用户 3：消防的巡检员， 4：消防企业人员)
//        if (getIntent().getIntExtra("type", 1) == 1) {
//            企业——0整改报告
//        type = 1;//0整改报告，t1合格，2不合格
        titleBarView.setTitleBarText("巡检报告");
//        } else {
        type = 2;//0整改报告，1合格，2不合格
//        titleBarView.setTitleBarText("整改报告");
//        }

        if (type == 2) {
            radioGroup.setVisibility(View.GONE);
        }


        if (type == 2) {
            isQualified = 0;
        }


        SelectPicBean selectPicBean = new SelectPicBean();
        selectPicBeanList.add(selectPicBean);

        if (selectPicAdapter == null) {
            selectPicAdapter = new SelectPicAdapter(context, selectPicBeanList);
        } else {
            selectPicAdapter.notifyDataSetChanged();
        }
        gridView.setAdapter(selectPicAdapter);
        selectPicAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (TextUtils.isEmpty(selectPicBeanList.get(position).getIcon())) {
                    addPicture();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addPicture() {
        // 自定义图片加载器
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                // TODO 在这边可以自定义图片加载库来加载 ImageView，例如 Glide、Picasso、ImageLoader 等
                Glide.with(context).load(path).into(imageView);
            }
        });
        // 自由配置选项
        int[] colors = {ContextCompat.getColor(this, R.color.primarystart), ContextCompat.getColor(this, R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选
                .multiSelect(true)
                // “确定”按钮背景色
                .btnBgColor(InspectionSubmitDialog.this.getResources().getColor(R.color.primarystart))
                // “确定”按钮文字颜色
                .btnTextColor(InspectionSubmitDialog.this.getResources().getColor(R.color.white))
                // 使用沉浸式状态栏
                .statusBarColor(InspectionSubmitDialog.this.getResources().getColor(R.color.primarystart))
                // 返回图标 ResId
//                        .backResId(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar 背景色
                .titleBgColor(InspectionSubmitDialog.this.getResources().getColor(R.color.primarystart))
                // 裁剪大小。needCrop 为 true 的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(3 - selectPicBeanList.size() + 1)
                //是否记住上次的选中记录(只对多选有效)
                .rememberSelected(false)
                .build();

        //跳转到图片选择器
        ISNav.getInstance().toListActivity(this, config, REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inspection_device_radiobutton1://合格
                radioButton1.setImageResource(R.mipmap.radio_choseed);
                radioButton2.setImageResource(R.mipmap.radio_unchose);
                tvPic.setVisibility(View.GONE);
                gridView.setVisibility(View.GONE);
                isQualified = 1;
                break;
            case R.id.inspection_device_radiobutton2://不合格
                radioButton2.setImageResource(R.mipmap.radio_choseed);
                radioButton1.setImageResource(R.mipmap.radio_unchose);
                tvPic.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.VISIBLE);
                isQualified = 2;
                break;
            case R.id.inspection_device_submit:
                submitData();
                break;
        }
    }

    private void submitData() {

        //有不合格布局并且选中不合格,有一张图片，并且是+
        if ((radioGroup.getVisibility() == View.VISIBLE && isQualified == 2) && (selectPicBeanList != null && selectPicBeanList.size() == 1 && TextUtils.isEmpty(selectPicBeanList.get(0).getIcon()))) {
            ToastUtils.showShort(this, "请选择图片");
            return;
        }
        if ((radioGroup.getVisibility() == View.VISIBLE && isQualified == 2) && (selectPicBeanList == null || selectPicBeanList.size() < 1)) {
            ToastUtils.showShort(this, "请选择图片");
            return;
        }

        if (TextUtils.isEmpty(tvRemark.getText().toString().trim())) {
            ToastUtils.showShort(this, "请填写相关信息");
            return;
        }


//        UserInfoBean userInfoBean = UserInfoBean.getInstance();

//        token	string	是	数据访问token
//        id	string	是	设备的Id
//        equipmentPic1	string	否	设备的现场照片
//        equipmentPic2	string	否	设备的现场照片
//        equipmentPic3	string	否	设备的现场照片
//        isQualified	string	否	是否合格，不合格即为异常（巡检报告需要这字段，整改报告不用）
//        remark	string	否	巡检人员的备注或管理人员的整改信息

//        Map<String, Object> map = new HashMap<>();
//        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
//        map.put(ActValue.Id, getIntent().getIntExtra("id", -1));
//        map.put("remark", tvRemark.getText().toString().trim());
//
//        //登录类型(1：系统超级管理员， 2：系统普通用户 3：消防的巡检员， 4：消防企业人员)
//        if (!TextUtils.isEmpty(userInfoBean.getLoginType()) && userInfoBean.getLoginType().equals("3")) {
//            map.put("isQualified", isQualified);//0整改报告，1合格，2不合格
//        } else if (!TextUtils.isEmpty(userInfoBean.getLoginType()) && userInfoBean.getLoginType().equals("4")) {
//            //企业——0整改报告
//            map.put("isQualified", 0);//0整改报告，1合格，2不合格
//        }

        final HashMap<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        map.put("type", type);
        map.put("id", "" + getIntent().getIntExtra("id", -1));
        map.put("remark", tvRemark.getText().toString().trim());//ParamKey.TOKEN 自定义参数key常量类，即参数名
        map.put("isQualified", "" + isQualified);
        int files = 0;
//        if (isQualified == 2) {
        for (int i = 0; i < selectPicBeanList.size(); i++) {

            if (!TextUtils.isEmpty(selectPicBeanList.get(i).getIcon())) {
                try {
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    //图片压缩
                    File file = new Compressor(this).compressToFile(new File(selectPicBeanList.get(i).getIcon()));
                    files += 1;
//                    File file = new File(selectPicBeanList.get(i).getIcon());
                    final RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    builder.addFormDataPart("file", file.getName(), imageBody);//file 后台接收图片流的参数名
//                        map.put(ActValue.Token, );
                    builder.addFormDataPart("token", SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                    final int finalI = i;
                    RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).photo(builder.build().parts()), new RxSubscriber<DefaultBean>(this) {
                        @Override
                        protected void onSuccess(DefaultBean imgBean) {
                            final int a = finalI + 1;
                            map.put("equipmentPic" + a, imgBean.getData());
//                            if (a == selectPicBeanList.size()-1 || map.size() == 8) {
                            Log.d("InspectionSubmitDialog", "selectPicBeanList.size():" + selectPicBeanList.size());
                            Log.d("InspectionSubmitDialog", "a:" + a);
                            Log.d("InspectionSubmitDialog", "map.size():" + map.size());
                            if (a == selectPicBeanList.size() - 1 && selectPicBeanList.size() == 2) {
                                postImg(map);
                            }
                            if (selectPicBeanList.size() == 3) {
                                if (selectPicBeanList.get(2).getIcon() == null && a == 2) {
                                    postImg(map);
                                } else if (a == 3) {
                                    postImg(map);
                                }
                            }
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }


        if (selectPicBeanList.size() == 0 || isQualified == 1) {
            postImg(map);
        }


    }

    public void postImg(HashMap map) {
        RetrofitHelper.subscriber(RetrofitHelper.Https(InspectionApi.class).getInformation(map), new RxSubscriber<YxdzInfo>(this) {
            @Override
            protected void onSuccess(YxdzInfo yxdzInfo) {
                Intent intent = new Intent();
                // 获取用户计算后的结果
                intent.putExtra("data", true); //将计算的值回传回去
                //如果是巡检记录，回传是否合格
                intent.putExtra("isQualified", isQualified);
                //通过intent对象返回结果，必须要调用一个setResult方法，
                //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
                setResult(1, intent);
                ToastUtils.showShort(InspectionSubmitDialog.this, "提交成功");
                finish();
            }

            @Override
            protected void onOtherError(String code) {
                super.onOtherError(code);
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调4
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");

            for (String path : pathList) {
                LogUtils.e("url===" + path);
            }

            selectPicBeanList.remove(selectPicBeanList.size() - 1);
            for (int i = 0; i < pathList.size(); i++) {
                SelectPicBean selectPicBean = new SelectPicBean();
                selectPicBean.setIcon(pathList.get(i));
                selectPicBeanList.add(selectPicBean);
            }

            //不够三个就再加一个+
            if (selectPicBeanList.size() < 3) {
                SelectPicBean selectPicBean = new SelectPicBean();
                selectPicBeanList.add(selectPicBean);
            }
//            selectPicAdapter.notifyDataSetChanged();
            selectPicAdapter = new SelectPicAdapter(this, selectPicBeanList);
            gridView.setAdapter(selectPicAdapter);
            selectPicAdapter.setOnItemClickListener(new OnItemClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onItemClick(View view, int position) {

                    if (TextUtils.isEmpty(selectPicBeanList.get(position).getIcon())) {
                        addPicture();
                    }

                }
            });

//            uploadImage(pathList.get(0));
        }
    }
}
