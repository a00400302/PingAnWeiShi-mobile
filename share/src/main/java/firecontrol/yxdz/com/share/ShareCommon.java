package firecontrol.yxdz.com.share;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class ShareCommon {

    private Context context;

    /**
     * @param context
     * @param url         分享链接
     * @param title       分享标题
     * @param description 分享描述
     */
    public ShareCommon(Context context, String url, String title, String description) {
        share(context, url, title, description);
    }

    public void share(Context context, String url, String title, String description) {
        this.context = context;
        if (TextUtils.isEmpty(url)) {
            url = "https://www.baidu.com/";
        }
        if (TextUtils.isEmpty(title)) {
            title = context.getString(R.string.share_app_name);
        }
        if (TextUtils.isEmpty(description)) {
            description = context.getString(R.string.share_about_app);
        }

        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(new UMImage(context, R.mipmap.share_app_logo));
        web.setDescription(description);//描述
        new ShareAction(((Activity) context)).withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL)
                .setCallback(shareListener)
                .open();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(context, "成功了", Toast.LENGTH_SHORT).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();

        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context, "取消了", Toast.LENGTH_SHORT).show();

        }
    };
}
