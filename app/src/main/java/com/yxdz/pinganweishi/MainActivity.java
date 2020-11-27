package com.yxdz.pinganweishi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.leaf.library.StatusBarUtil;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCustomMessage;
import com.mob.pushsdk.MobPushNotifyMessage;
import com.mob.pushsdk.MobPushReceiver;
import com.yuyh.library.imgsel.utils.LogUtils;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.pinganweishi.activity.FireMapActivity;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.EventBusBean;
import com.yxdz.pinganweishi.fragment.FragmentFire;
import com.yxdz.pinganweishi.fragment.FragmentInspection;
import com.yxdz.pinganweishi.fragment.FragmentPerson;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.update.UpdateVersionUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends BaseHeadActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public int index = 0;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private Fragment[] mFragments = new Fragment[]{new FragmentFire(), new FragmentInspection(), new FragmentPerson()};
    // 当前fragment的index
    private int currentTabIndex = 0;
    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void setContentView() {
        MqttClient.getInstance(this).connect();
        EventBus.getDefault().register(this);
        StatusBarUtil.setTransparentForWindow(this);
        initView();
    }

    @Override
    protected void setTitlebarView() {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.e("MainActivity onNewIntent");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        bottomNavigationViewEx = findViewById(R.id.bottom_navigation_view);

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_common_frame_layout, mFragments[index])
                .show(mFragments[index]).commit();
        bottomNavigationViewEx.getMenu().findItem(R.id.main_menu_firecontrol).setChecked(true);
        getSDPermission();
        initPushReceiver();
    }

    private void initPushReceiver() {
        MobPush.addPushReceiver(new MobPushReceiver() {
            @Override
            public void onCustomMessageReceive(Context context, MobPushCustomMessage mobPushCustomMessage) {
                Log.d("MyApplication", "mobPushCustomMessage:" + mobPushCustomMessage);
            }

            @Override
            public void onNotifyMessageReceive(Context context, MobPushNotifyMessage mobPushNotifyMessage) {
                Log.d("MyApplication", "mobPushNotifyMessage:" + mobPushNotifyMessage);
                Intent intent = new Intent(MainActivity.this, FireMapActivity.class);
                intent.setData(Uri.parse(mobPushNotifyMessage.getExtrasMap().get("mobpush_link_k")));
                startActivity(intent);
            }

            @Override
            public void onNotifyMessageOpenedReceive(Context context, MobPushNotifyMessage mobPushNotifyMessage) {
                Log.d("MyApplication", "mobPushNotifyMessage:" + mobPushNotifyMessage);
            }

            @Override
            public void onTagsCallback(Context context, String[] strings, int i, int i1) {
                Log.d("MyApplication", "strings:" + strings);
            }

            @Override
            public void onAliasCallback(Context context, String s, int i, int i1) {
                Log.d("MyApplication", s);
            }
        });
    }

    private void getSDPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
//                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.REQUEST_INSTALL_PACKAGES,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    /*  Manifest.permission.CALL_PHONE,
                     Manifest.permission.READ_LOGS,
                     Manifest.permission.READ_PHONE_STATE,
                     Manifest.permission.READ_EXTERNAL_STORAGE,
                     Manifest.permission.SET_DEBUG_APP,
                     Manifest.permission.SYSTEM_ALERT_WINDOW,
                     Manifest.permission.GET_ACCOUNTS,
                     Manifest.permission.WRITE_APN_SETTINGS*/};
            ActivityCompat.requestPermissions(this, mPermissionList, 222);
        }
    }

    //订阅方法，当接收到事件的时候，会调用该方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusBean eventBusBean) {
        //切换到主页面消防模块
        if ("refresh".equals(eventBusBean.getFlag())) {
            index = 0;
            if (currentTabIndex != index) {
                FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                trx.hide(mFragments[currentTabIndex]);
                if (!mFragments[index].isAdded()) {
                    trx.add(R.id.main_common_frame_layout, mFragments[index]);
                }
                trx.show(mFragments[index]).commitAllowingStateLoss();
            }
            currentTabIndex = index;
            //底部选中第一项(消防)
            bottomNavigationViewEx.setCurrentItem(index);


            //刷新消防Fragment的数据
            ((FragmentFire) mFragments[0]).initData(MainActivity.this);
            LogUtils.e("path===" + eventBusBean.getFlag() + ((FragmentFire) mFragments[0]));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_firecontrol:
                index = 0;
                break;
            case R.id.main_menu_inspection:
//                ToastUtils.showShort(MainActivity.this, "巡检");
                index = 1;
                break;
            case R.id.main_menu_person:
//                ToastUtils.showShort(MainActivity.this, "我的");
                index = 2;
                break;
        }

        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragments[currentTabIndex]);
            if (!mFragments[index].isAdded()) {
                trx.add(R.id.main_common_frame_layout, mFragments[index]);
            }
            trx.show(mFragments[index]).commit();
        }

        currentTabIndex = index;
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //Activity中相关处理代码
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    mFragments[0].onActivityResult(123, resultCode, data);
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    /**
     * 重写方法，将权限处理交由所在Fragment
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 222) {
            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Object token = SPUtils.getInstance().getString(ConstantUtils.TOKEN, "");
                new UpdateVersionUtil(this, null, true, token);
            } else {
                ToastUtils.showShort(this, "没有读写权限");
            }
        } else {
            // 获取到Activity下的Fragment
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments == null) {
                return;
            }
            // 查找在Fragment中onRequestPermissionsResult方法并调用
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    // 这里就会调用我们Fragment中的onRequestPermissionsResult方法
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        LogUtils.e("MainActivity onDestroy");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
