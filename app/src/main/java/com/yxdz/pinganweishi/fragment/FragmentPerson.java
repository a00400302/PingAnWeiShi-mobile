package com.yxdz.pinganweishi.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.leaf.library.StatusBarUtil;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.UserInfoBean;
import com.yxdz.pinganweishi.person.AboutActivity;
import com.yxdz.pinganweishi.person.FeedbackActivity;
import com.yxdz.pinganweishi.person.FireMessageActivity;
import com.yxdz.pinganweishi.person.PersonDetailAty;
import com.yxdz.pinganweishi.person.PlaceEditActivity;
import com.yxdz.pinganweishi.person.PushSettingActivity;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.update.UpdateVersionUtil;



import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * 消防
 */
public class FragmentPerson extends Fragment  implements View.OnClickListener {
    private View view;
    public TitleBarView toolbar;
    private ImageView person_icon;
    private TextView person_name;
    private TextView person_type;
    private TextView person_account;
    private UserInfoBean userInfoBean;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //引用创建好的xml布局
        view = inflater.inflate(R.layout.main_fragment_person, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(getContext(),R.color.primarystart), ContextCompat.getColor(getContext(),R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        toolbar.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(getActivity(), toolbar);
//        toolbar.setTitleBarText(" ");
        toolbar.getBarlayout().setVisibility(View.GONE);
        initView();
        initData();
        return view;
    }

    private void initData() {
        userInfoBean = UserInfoBean.getInstance();
        person_icon.setImageDrawable(getResources().getDrawable(R.mipmap.logo));
//        if (userInfoBean != null && !TextUtils.isEmpty(userInfoBean.getUserPic())) {
//            GlideUtils.displayCropCircle(getActivity(), person_icon, YuXinUrl.HTTPS + userInfoBean.getUserPic());
//
//        }
        if (userInfoBean !=null){
            person_name.setText(userInfoBean.getName());
            person_account.setText(userInfoBean.getAccount());
            switch (userInfoBean.getUserType()){
                case 100:
                    person_type.setText("普通用户");
                    break;
                case 99:
                    person_type.setText("安装人员");
                    break;
                case 101:
                    person_type.setText("巡检员");
                    break;
                case 110:
                    person_type.setText("企业法人");
                    break;
                case 111:
                    person_type.setText("企业单位");
            }
        }
    }

    private void initView() {
        person_icon = view.findViewById(R.id.person_icon);
        person_name = view.findViewById(R.id.person_name);
        person_type = view.findViewById(R.id.person_type);
        person_account = view.findViewById(R.id.person_account);
        ImageView person_edit = view.findViewById(R.id.person_edit);
        person_edit.setOnClickListener(this);
        LinearLayout person_place = view.findViewById(R.id.person_place);
        person_place.setOnClickListener(this);
        LinearLayout person_push = view.findViewById(R.id.person_push);
        person_push.setOnClickListener(this);
        LinearLayout person_history = view.findViewById(R.id.person_history);
        person_history.setOnClickListener(this);
        LinearLayout person_submit = view.findViewById(R.id.person_submit);
        person_submit.setOnClickListener(this);
        LinearLayout person_update = view.findViewById(R.id.person_update);
        person_update.setOnClickListener(this);
        LinearLayout person_share = view.findViewById(R.id.person_share);
        person_share.setOnClickListener(this);
        LinearLayout person_about = view.findViewById(R.id.person_about);
        person_about.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.person_edit:

                Intent person = new Intent(getActivity(), PersonDetailAty.class);
                person.putExtra("UserInfoBean", UserInfoBean.getInstance());
                getActivity().finish();
                startActivityForResult(person, 1);
                break;
            case R.id.person_place:
                startActivity(new Intent(getActivity(), PlaceEditActivity.class));
                break;
            case R.id.person_push:
                startActivity(new Intent(getActivity(), PushSettingActivity.class));
                break;
            case R.id.person_history:
                startActivity(new Intent(getActivity(), FireMessageActivity.class));
                break;
            case R.id.person_submit:
                Intent feedback = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(feedback);
                break;
            case R.id.person_update:
                getSDPermission();
                break;
            case R.id.person_share:
                showShare();
                break;
            case R.id.person_about:
                Intent about = new Intent(getActivity(), AboutActivity.class);
                startActivity(about);
                break;
        }
    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("瓶安卫士分享");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("https://android.myapp.com/myapp/detail.htm?apkName=com.yxdz.pinganweishi&ADTAG=mobile");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("瓶安卫士，一款方便监控报警的app,下载链接：https://android.myapp.com/myapp/detail.htm?apkName=com.yxdz.pinganweishi&ADTAG=mobile");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setUrl("https://android.myapp.com/myapp/detail.htm?apkName=com.yxdz.pinganweishi&ADTAG=mobile");
//        oks.setImageUrl("https://www.pgyer.com/sD6Z");
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.icon_logo);
        oks.setImageData(bitmap);
        // 启动分享GUI
        oks.show(getActivity());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            //修改头像后刷新
            initData();
        } else {
        }
//        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }


    private void getSDPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.REQUEST_INSTALL_PACKAGES
                    /* Manifest.permission.ACCESS_FINE_LOCATION,
                     Manifest.permission.CALL_PHONE,
                     Manifest.permission.READ_LOGS,
                     Manifest.permission.READ_PHONE_STATE,
                     Manifest.permission.READ_EXTERNAL_STORAGE,
                     Manifest.permission.SET_DEBUG_APP,
                     Manifest.permission.SYSTEM_ALERT_WINDOW,
                     Manifest.permission.GET_ACCOUNTS,
                     Manifest.permission.WRITE_APN_SETTINGS*/};
            ActivityCompat.requestPermissions(getActivity(), mPermissionList, 333);
            //其中123是requestcode，可以根据这个code判断，用户是否同意了授权。如果没有同意，可以根据回调进行相应处理：
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //分享
//                new ShareCommon(getActivity(), null, null, null);
            } else {
                ToastUtils.showShort(getActivity(), "没有读写权限");
            }

        } else if (requestCode == 333) {
            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Object o = SPUtils.getInstance().getString( ConstantUtils.TOKEN, "");
                new UpdateVersionUtil(getActivity(), view.findViewById(R.id.person_layout), true,true,o);
            } else {
                ToastUtils.showShort(getActivity(), "没有读写权限");
            }

        }
    }



}
