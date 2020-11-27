package com.yxdz.update;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.yxdz.common.utils.YuXinUrl;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.update.api.UpdateApi;
import com.yxdz.update.bean.UpdateBean;
import com.yxdz.update.down.BaseRequest;
import com.yxdz.update.down.DownloadInfo;
import com.yxdz.update.down.DownloadListener;
import com.yxdz.update.down.DownloadManager;
import com.yxdz.update.utils.Constants;
import com.yxdz.update.utils.SPUtils;
import com.yxdz.update.utils.UpdatePath;
import com.yxdz.update.view.UpdateVersionDialog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huang on 2018/6/1.
 */

public class UpdateVersionUtil implements UpdateVersionDialog.UpdateVersionInterface {
    private Context mContext;
    private View view;

    private String versionDownloadString;
    private String apkName;

    private UpdateVersionDialog dialog;
    private NotificationManager mNotificationManager;
    private Notification showNotif;
    private RemoteViews remoteViews;

    private PackageManager packageManager;
    private PackageInfo packageInfo;

    private int NOTIFY_ID = 8989;
    private ImageView imageUpdate;
    //是否需要取消按钮
    private boolean type;
    //是否是点击事件进入
    private boolean isClickFlag;

    //服务器的版本号
    private int versionCode;
    private Object token;

    /**
     * @param mContext
     * @param view
     * @param type     true:显示取消按钮  false：不显示取消按钮
     */
    public UpdateVersionUtil(Context mContext, View view, boolean type,Object token) {
        this.token = token;
        //////////////////////////////初始化
        initData(mContext,view,type);
    }


    /**
     * @param mContext
     * @param view
     * @param type     true:显示取消按钮  false：不显示取消按钮
     */
    public UpdateVersionUtil(Context mContext, View view, boolean type,boolean isClickFlag,Object token) {
        this.isClickFlag=isClickFlag;
        this.token = token;
        Log.d("zhu","isClickFlag:"+isClickFlag);
        initData(mContext,view,type);
    }

    public void initData(Context mContext, View view, boolean type){
        UpdatePath.setAppPath(Constants.UPDATE_PATH);
        this.type = type;
        init(mContext, view);
    }


    public UpdateVersionUtil(Context mContext, View view, ImageView view1) {
        imageUpdate = view1;
        init(mContext, view);
    }

    private void init(Context mContext, View view) {
        this.mContext = mContext;
        this.view = view;
        //创建APK下载路径
        File f = new File(UpdatePath.getDownPath());
        if (!f.exists()) {
            f.mkdirs();
            Log.e("zhu", "不存在=" + UpdatePath.getDownPath());
        } else {
            Log.e("zhu", "存在=" + UpdatePath.getDownPath());
        }


        packageManager = mContext.getPackageManager();
        try {
            packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (imageUpdate != null) {
            Log.e("bin", "kaishi");
//            imageUpdate.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_rotate));
        }

        requestData();
    }


    private void requestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("type", 1);
        RetrofitHelper.subscriber(RetrofitHelper.Https(UpdateApi.class).getUpdate(map), new RxSubscriber<UpdateBean>(mContext) {
            @Override
            protected void onSuccess(UpdateBean updateBean) {
                if (updateBean.getData() == null){
                    Toast.makeText(mContext, "当前版本为最新版本", Toast.LENGTH_SHORT).show();
                    return;
                }
                versionCode = updateBean.getData().getVerCode();
                Log.e("zhu", "updateBean=" + updateBean);
                if (updateBean.getData().getVerCode() > getVersionCode(mContext)) {
                    //每次强制询问更新
                    if ("2".equals(updateBean.getData().getMust())) {
                        update(updateBean);
                    } else {
                        if (isClickFlag) {
                            update(updateBean);
                            return;
                        }
                        //询问更新
                        if ((Boolean) SPUtils.get(mContext, Constants.UPDATE_CANCLE_FLAG, false)) {
                            update(updateBean);
                        }
                    }
                } else {
                    if (isClickFlag) {
                        Toast.makeText(mContext, "当前版本为最新版本", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /////////////////////////////模拟数据

//        UpdataVersionBean updataVersionBean = new UpdataVersionBean();
//        updataVersionBean.setVersionSize("10.0M");
//        updataVersionBean.setVersionCode("29");
//        updataVersionBean.setVersionName("1.1.1");
//        updataVersionBean.setVersionDate("2018-06-01 12:00:00");
////        updataVersionBean.setVersionDownload("http://openbox.mobilem.360.cn/url/r/k/std_1527824309");
//        updataVersionBean.setVersionDownload("http://openbox.mobilem.360.cn/url/r/k/std_1527834103");
//
//        List<UpdataBodyBean> updataBodyBeanList = new ArrayList<>();
//        UpdataBodyBean data = new UpdataBodyBean();
//        data.setBodyId("1");
//        data.setBodyIntroduct("修复一些bug");
//        updataBodyBeanList.add(data);
//        updataVersionBean.setVersionBody(updataBodyBeanList);
//
//        versionCode = Integer.parseInt(updataVersionBean.getVersionCode());
//        update(updataVersionBean);
//        List<UpdataBodyBean> beanList = new ArrayList<>();
//        for (int i = 0; i < updataVersionBean.getVersionBody().size(); i++) {
//            UpdataBodyBean updataBodyBean = new UpdataBodyBean();
//            updataBodyBean.setBodyId(updataVersionBean.getVersionBody().get(i).getBodyId());
//            updataBodyBean.setBodyIntroduct(updataVersionBean.getVersionBody().get(i).getBodyIntroduct());
//            beanList.add(updataBodyBean);
//        }
//        updataVersionBean.setVersionBody(beanList);

    }

    private UpdateCallback updateCallback;

    public interface UpdateCallback {
        void isUpdate(boolean isUpdate);
    }

    public void setUpdateCallback(UpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }

    private void cancel() {
        if (imageUpdate != null) {
            imageUpdate.clearAnimation();
        }
    }



    private void update(UpdateBean updateBean) {

        dialog = new UpdateVersionDialog(mContext, type);
        dialog.setUpdateVersion(this);
        dialog.getVersionText().setText(updateBean.getData().getVerCode()+"");
        dialog.getVersionNumText().setText(updateBean.getData().getVerName());
        dialog.getTimeText().setText(updateBean.getData().getCreateTime());

        versionDownloadString = YuXinUrl.SERVER + updateBean.getData().getPath();
        String[] split = versionDownloadString.split("/");
        apkName = split[split.length - 1];

        ////////////////////////////////////////////////模拟apkName

        dialog.getBodyText().setText(updateBean.getData().getContent());


        //升级说明(更新内容)

        if (isApkInLocal()) {
            dialog.getOkBtn().setText("安装");
        } else {
            dialog.getOkBtn().setText("下载");
        }

        dialog.show();
        cancel();


    }

//    private void update(UpdataVersionBean updataVersionBean) {
//
//        dialog = new UpdateVersionDialog(mContext, type);
//        dialog.setUpdateVersion(this);
//        dialog.getVersionText().setText(updataVersionBean.getVersionName());
//        dialog.getVersionNumText().setText(updataVersionBean.getVersionCode());
//        dialog.getSizeText().setText(updataVersionBean.getVersionSize());
//        dialog.getTimeText().setText(updataVersionBean.getVersionDate());
//
//        versionDownloadString = updataVersionBean.getVersionDownload();
//        String[] split = versionDownloadString.split("/");
//        apkName = split[split.length - 1];
//
//
//        ////////////////////////////////////////////////模拟apkName
//        apkName = "20180601.apk";
//
//        //升级说明(更新内容)
//        String body = "";
//        for (int i = 0; i < updataVersionBean.getVersionBody().size(); i++) {
//            body = body + updataVersionBean.getVersionBody().get(i).getBodyIntroduct() + "\n";
//        }
//        dialog.getBodyText().setText(body);
//
//        if (isApkInLocal()) {
//            dialog.getOkBtn().setText("安装");
//        } else {
//            dialog.getOkBtn().setText("下载");
//        }
//
//        dialog.show();
//        cancel();
//
//
//    }

    @Override
    public void updateOk() {

        Log.e("zhu", "下载链接是 ====" + versionDownloadString + "分割结果：" + apkName);
        if (dialog.getOkBtn().getText().toString().equals("安装")) {
            installApk(UpdatePath.getDownPath() + "" + apkName, mContext);
            return;
        }

        DownloadManager.getInstance().addTask(apkName, versionDownloadString, new BaseRequest(versionDownloadString), new DownloadListener() {

            @Override
            public void onProgress(DownloadInfo downloadInfo) {
                notifyBar(downloadInfo.getTotalLength(), downloadInfo.getDownloadLength());
                Log.e("zhu", "getTotalLength() ====" + downloadInfo.getTotalLength() + ",getDownloadLength======：" + downloadInfo.getDownloadLength());
            }

            @Override
            public void onFinish(DownloadInfo downloadInfo) {
                Log.e("zhu", "下载完成，准备安装的apk路径是" + "/" + UpdatePath.getDownPath() + apkName);
                installApk(UpdatePath.getDownPath() + "" + apkName, mContext);
                mNotificationManager.cancel(NOTIFY_ID);//下载成功去除掉消息通知
            }

            @Override
            public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
                Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                mNotificationManager.cancel(NOTIFY_ID);//下载成功去除掉消息通知
                Log.e("zhu", "下载失败，onError==" + errorMsg + "+" + e);
            }
        });
        dialog.dismiss();
    }

    /**
     * 通知栏下载进度
     */
    private void notifyBar(long total, long current) {
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        showNotif = new Notification(R.mipmap.update_app_logo, "正在下载", System.currentTimeMillis());
//        showNotif.flags = Notification.FLAG_AUTO_CANCEL;
//        remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.update_notify_download);
//        remoteViews.setTextViewText(R.id.update_notify_text, "正在下载");
//        remoteViews.setProgressBar(R.id.update_notify_progress, (int) total, (int) current, false);
//        showNotif.contentView = remoteViews;

//        mNotificationManager.notify(NOTIFY_ID, showNotif);


        final String CHANNEL_ID = "update_id_1";
        final String CHANNEL_NAME = "update_name_1";
//        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            //只在Android O之上需要渠道
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，
            //通知才能正常弹出
            mNotificationManager.createNotificationChannel(notificationChannel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID);
            builder.setSmallIcon(R.mipmap.update_app_logo)
                    .setContentTitle("通知标题")
                    .setContentText("通知内容")
                    .setAutoCancel(true);
//            showNotif = builder.build();
            builder.setOnlyAlertOnce(true);
//////////////////////////////////////////////////////////////

//            final Notification.Builder builder = mContext.getNotificationBuilder();
            remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.update_notify_download);
            remoteViews.setTextViewText(R.id.update_notify_text, "正在下载");
            remoteViews.setProgressBar(R.id.update_notify_progress, (int) total, (int) current, false);
            builder.setCustomContentView(remoteViews);
            showNotif = builder.build();

        } else {
            showNotif = new Notification(R.mipmap.update_app_logo, "正在下载", System.currentTimeMillis());
            showNotif.flags = Notification.FLAG_AUTO_CANCEL;
            remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.update_notify_download);
            remoteViews.setTextViewText(R.id.update_notify_text, "正在下载");
            remoteViews.setProgressBar(R.id.update_notify_progress, (int) total, (int) current, false);
            showNotif.contentView = remoteViews;
        }


        mNotificationManager.notify(NOTIFY_ID, showNotif);

    }

    /**
     * 安装apk
     */
    public static boolean installApk(String mPath, Context context) {
        if (!judgeFileExist(mPath, context)) {
            return false;
        }
        // 4.本地apk是正常的，正确安装
//        Log.e("安装路径:", mPath);
//        Uri uri = Uri.parse("file://" + mPath);
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        mContext.startActivity(intent);
        // LogUtil.e(LogUtil.Can, "手动安装成功");
///////////////////////////////////////////////////////////////
//        File file = new File(mPath);
//
//        Intent intent = new Intent();
//        intent.setAction(android.content.Intent.ACTION_VIEW);
//        Uri uri;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);
//            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//        } else {
//            uri = Uri.fromFile(file);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        }

//        File file = new File(mPath);
//
//        Intent intent = new Intent();
//        intent.setAction(android.content.Intent.ACTION_VIEW);
//        Uri uri;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);
//            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//        } else {
//            uri = Uri.fromFile(file);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        }


        /////////////////////
        File file = new File(mPath);
        final String authority = "com.yxdz.update.fileprovider";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, authority, file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
//        if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
        context.startActivity(intent);
//        }


//        Log.e("zhu", "报名==" + context.getApplicationContext().getPackageName());
//        File file = new File(mPath);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName(), file);
//            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//        } else {
//            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
//            context.startActivity(intent);
//        }
        return true;
    }

    /**
     * 判断apk是否可以安装
     */
    public static Boolean judgeFileExist(String mPath, Context mContext) {
        if (TextUtils.isEmpty(mPath)) {
            return false;
        }
        // LogUtil.e("路径："+mPath);
        // 1.先判断本地的文件是否存在
        File f = new File(mPath.toString());
        if (!f.exists()) {
            Log.e("安装文件不存在", "进来了");
            return false;
        }
        // 2.存在文件获取安装包的apk的信息判断是否正常可以用
        ApplicationInfo appInfo = null;
        PackageManager pm = mContext.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(mPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            appInfo = info.applicationInfo;
        }
        // 3.表示本地的apk不可以用，直接删掉当前文件里面的apk
        if (info == null && appInfo == null) {
            File f1 = new File(mPath.toString());
            if (f1.exists()) {
                f1.delete();
            }
            return false;
        }
        return true;
    }

    //获取本地是否有APK
    private boolean isApkInLocal() {
        PackageInfo mPackageInfo = packageManager.getPackageArchiveInfo(UpdatePath.getDownPath() + /*"/" +*/ apkName, PackageManager.GET_ACTIVITIES);
        boolean isVersion = mPackageInfo != null && mPackageInfo.packageName.equals(mContext.getPackageName());

        //若本地有和服务器相同名字的apk，mPackageInfo是不为空的
        if (mPackageInfo != null && mPackageInfo.packageName.equals(mContext.getPackageName()) && mPackageInfo.versionCode < versionCode) {
            //判断本地的包名是否和此应用包名一致，并且版本号低于下载的apk版本号，返回false去下载apk
            return false;
        }

        if (isVersion) {
            String versionName = mPackageInfo.versionName;
//            如果指定的数与参数相等返回0。
//            如果指定的数小于参数返回 -1。
//            如果指定的数大于参数返回 1。
            int compare = versionName.compareTo(packageInfo.versionName);
            return compare > 0;
        } else {
            if (mPackageInfo == null) {
                File f = new File(UpdatePath.getDownPath() + "/" + apkName);
                if (f != null && f.exists()) {
                    f.delete();
                }
            }
            return false;
        }
    }

    /**
     * 获取软件版本号
     */
    public int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            String pkgname = context.getPackageName();
            versionCode = context.getPackageManager()
                    .getPackageInfo(pkgname, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

}