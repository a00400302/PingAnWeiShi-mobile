package com.yxdz.pinganweishi.person;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.leaf.library.StatusBarUtil;
import com.mob.pushsdk.MobPush;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.utils.NoDoubleClick;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.utils.YuXinUrl;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.bean.YxdzInfo;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.MainActivity;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.api.LoginApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.UploadImageBean;
import com.yxdz.pinganweishi.bean.UserInfoBean;
import com.yxdz.pinganweishi.ui.login.ChangePwdActivity;
import com.yxdz.pinganweishi.ui.login.LoginActivity;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 用户详情
 */
public class PersonDetailAty extends BaseHeadActivity implements View.OnClickListener {

    private static final int REQUEST_LIST_CODE = 110;
    private LinearLayout iconLayout;
    private LinearLayout changePwdLayout;
    private ImageView ivIcon;
    private TextView tvName;
    private TextView tvSex;
    private TextView tvPhone;
    private TitleBarView titleBarView;
    private View logout;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_person_detail_aty;
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
        initData();
    }

    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("帐号信息");
        titleBarView.setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDetailAty.this.finish();
                startActivity(new Intent(PersonDetailAty.this, MainActivity.class));
            }
        });
    }

    private void initView() {
        iconLayout = findViewById(R.id.person_detail_icon_layout);
        changePwdLayout = findViewById(R.id.person_detail_change_pwd);
        ivIcon = findViewById(R.id.person_detail_icon);
        tvName = findViewById(R.id.person_detail_name);
        tvSex = findViewById(R.id.person_detail_sex);
        tvPhone = findViewById(R.id.person_detail_phone);
        logout = findViewById(R.id.logout);

        iconLayout.setOnClickListener(this);
        logout.setOnClickListener(this);
        changePwdLayout.setOnClickListener(this);

    }

    private void initData() {
        UserInfoBean userInfoBean = (UserInfoBean) getIntent().getSerializableExtra("UserInfoBean");
        ivIcon.setImageDrawable(getResources().getDrawable(R.mipmap.logo));
//        GlideUtils.displayCropCircle(this, ivIcon, YuXinUrl.HTTPS + userInfoBean.getUserPic());

        if (!TextUtils.isEmpty(userInfoBean.getSex()) && userInfoBean.getSex().equals("1")) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }

        tvName.setText("" + userInfoBean.getName());
        tvPhone.setText("" + userInfoBean.getAccount());

//        // 自定义图片加载器
//        ISNav.getInstance().init(new ImageLoader() {
//            @Override
//            public void displayImage(Context context, String path, ImageView imageView) {
//                // TODO 在这边可以自定义图片加载库来加载 ImageView，例如 Glide、Picasso、ImageLoader 等
//                Glide.with(context).load(path).into(imageView);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        if (NoDoubleClick.isFastDoubleClick(500)) {
            return;
        }
        switch (v.getId()) {
//            case R.id.person_detail_icon_layout://头像
//                // 自由配置选项
//                ISListConfig config = new ISListConfig.Builder()
//                        // 是否多选
//                        .multiSelect(false)
//                        // “确定”按钮背景色
//                        .btnBgColor(Color.GRAY)
//                        // “确定”按钮文字颜色
//                        .btnTextColor(Color.BLUE)
//                        // 使用沉浸式状态栏
//                        .statusBarColor(getResources().getColor(R.color.primary))
//                        // 标题
//                        .title("图片")
//                        // 标题文字颜色
//                        .titleColor(Color.WHITE)
//                        // TitleBar 背景色
//                        .titleBgColor(getResources().getColor(R.color.primary))
//                        // 裁剪大小。needCrop 为 true 的时候配置
//                        .cropSize(1, 1, 200, 200)
//                        .needCrop(true)
//                        // 最大选择图片数量
//                        .maxNum(9)
//                        .build();
//
//                // 跳转到图片选择器
//                ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
//                break;
            case R.id.person_detail_change_pwd://修改密码——记得原密码
                Intent forget = new Intent(PersonDetailAty.this, ChangePwdActivity.class);
                startActivity(forget);
                break;
            case R.id.logout:
                clearData();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调4
        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");

            for (String path : pathList) {
                LogUtils.e("url===" + path);
            }

            uploadImage(pathList.get(0));
        }
    }

    /**
     * 上传图片
     *
     * @param imgPath
     */
    private void uploadImage(String imgPath) {

        File file = new File(imgPath);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .addFormDataPart("token",
                        "" + SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));//ParamKey.TOKEN 自定义参数key常量类，即参数名
//                        "" + UserInfoBean.getInstance().getToken());//ParamKey.TOKEN 自定义参数key常量类，即参数名
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), imageBody);//file 后台接收图片流的参数名
        List<MultipartBody.Part> partList = builder.build().parts();
        MultipartBody.Part parts = builder.build().part(0);
//            Map<String, String> map = new HashMap<>();
//            map.put("FunName", "ict_uploadpicture");
//            map.put("path", "/uploadNews");
//            map.put("appfile", fileNameByTimeStamp);
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).uploadPhoto(partList), new RxSubscriber<UploadImageBean>(this, "正在上传...") {
            @Override
            protected void onSuccess(UploadImageBean uploadImageBean) {
                uploadImageUrl(uploadImageBean.getFilePath());
            }

            @Override
            protected void onOtherError(String code) {
                super.onOtherError(code);
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
            }
        });

    }

    /**
     * 上传图片url路径——0.0后台多此一举
     *
     * @param filePath
     */
    private void uploadImageUrl(final String filePath) {
        Map<String, Object> map = new HashMap<>();
//        map.put(ActValue.Token, "" + SPUtils.get(PersonDetailAty.this, ConstantUtils.TOKEN, ""));
        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        map.put(ActValue.UserPic, filePath);
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).uploadPhotoUrl(map), new RxSubscriber<YxdzInfo>() {
            @Override
            protected void onSuccess(YxdzInfo yxdzInfo) {
                LogUtils.e(YuXinUrl.HTTPS + filePath);
                //头像显示
//                GlideUtils.displayCropCircle(PersonDetailAty.this, ivIcon, YuXinUrl.HTTPS + filePath);
                //改缓存数据(头像url路径)
                UserInfoBean.getInstance().setUserPic(filePath);
                ToastUtils.showShort(PersonDetailAty.this, "上传成功");
                setResult(1);

            }

            @Override
            protected void onOtherError(String code) {
                super.onOtherError(code);
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
            }
        });

    }

    private void clearData() {

        Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        map.put(ActValue.Id, UserInfoBean.getInstance().getId());
        RetrofitHelper.subscriber(RetrofitHelper.Https(LoginApi.class).loginout(map), new RxSubscriber<YxdzInfo>(this) {
            @Override
            protected void onSuccess(YxdzInfo yxdzInfo) {
                //清除保存的帐号
                if (!((Boolean) SPUtils.getInstance().getBoolean(ConstantUtils.IS_REMEMBER_ACCOUNT, false))) {
                    //如果没有记住帐号，就清空帐号
                    SPUtils.getInstance().put(ConstantUtils.LOGIN_ACCOUNT, "");
                }
                SPUtils.getInstance().put(ConstantUtils.LOGIN_PASSWORD, "");
                //清除本地用户数据
                UserInfoBean.getInstance().clearUserInfo();
                MobPush.stopPush();

                startActivity(new Intent(PersonDetailAty.this, LoginActivity.class));
                PersonDetailAty.this.finish();
            }


            @Override
            protected void onOtherError(String code) {
                super.onOtherError(code);
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
            }

        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PersonDetailAty.this, MainActivity.class));
        this.finish();

        super.onBackPressed();
    }
}
