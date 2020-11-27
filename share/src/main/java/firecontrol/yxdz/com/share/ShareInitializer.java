package firecontrol.yxdz.com.share;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

public class ShareInitializer {

    public static void init(Context context) {

        PlatformConfig.setWeixin("wxef7796f2335d8d37", "5ea2d5cde04f48340ef9a50b04d71ab1");
        PlatformConfig.setQQZone("1106913249", "cDWAIyw7rmKrAxGH");

        //友盟初始化
        UMShareAPI.get(context);
        //友盟
        /**
         * 参数1:上下文，必须的参数，不能为空。
         参数2:【友盟+】 AppKey，非必须参数，如果Manifest文件中已配置AppKey，该参数可以传空，则使用Manifest中配置的AppKey，否则该参数必须传入。
         参数3:【友盟+】 Channel，非必须参数，如果Manifest文件中已配置Channel，该参数可以传空，则使用Manifest中配置的Channel，否则该参数必须传入，Channel命名请详见Channel渠道命名规范。
         参数4:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机。
         参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空。
         */
//        UMConfigure.init(this, "5a12384aa40fa3551f0001d1", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        UMConfigure.init(context, "58d369f545297d2ad80017f8", "umeng_fire", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        //友盟debug
        UMConfigure.setLogEnabled(false);
    }


}
