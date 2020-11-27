package com.yxdz.http.http;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonSyntaxException;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.LoadingDialog;
import com.yxdz.http.R;
import com.yxdz.http.bean.YxdzInfo;
import com.yxdz.http.other.HttpCode;
import com.yxdz.http.utils.NetWorkUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;
import rx.Subscriber;


/**
 * @param <T> 网络请求总线处理
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;

//    private LoadOptions mLoadOptions;
//    private AlLoadingImpl mAlLoading;
//    private LoadingDialogView mDialog;
//    private AlHeadBackView mAlHeadView;

    private LoadingDialog loadingView;

    public RxSubscriber() {
    }

    /**
     * 需要转圈的处理
     *
     * @param context
     */
    public RxSubscriber(Context context) {
        if (context != null) {
            mContext = context;
            loadingView = new LoadingDialog(context, null);
        }
    }

    /**
     * 需要转圈的处理
     *
     * @param context
     * @param text    转圈提示的文字
     */
    public RxSubscriber(Context context, String text) {//需要转圈的处理

        if (context != null) {
            mContext = context;
            loadingView = new LoadingDialog(context, text);
        }
    }

//    public RxSubscriber(LoadOptions loadOptions) {
//
//        this.mLoadOptions = loadOptions;
//
//        this.mAlLoading = loadOptions.getLoadingView();
//
//        this.mContext = loadOptions.getLoadingView().getContext();
//    }

//    /**
//     * @param alHeadView 头部有转圈
//     */
//    public RxSubscriber(AlHeadBackView alHeadView) {
//
//        this.mAlHeadView = alHeadView;
//
//        this.mAlHeadView.setAlPbar(View.VISIBLE);
//
//        this.mContext = alHeadView.getContext();
//    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStart() {
        super.onStart();
        if (loadingView != null && !loadingView.isShowing()) {
//            loadingView.show();
            loadingView.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//注意要清除 FLAG_TRANSLUCENT_STATUS flag
            loadingView.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            loadingView.getWindow().setStatusBarColor(mContext.getResources().getColor(R.color.fire_control_theme));

        }
    }


    @Override
    public void onNext(T t) {
        YxdzInfo yxdzInfo = (YxdzInfo) t;
        if (yxdzInfo.getRetcode() != null && yxdzInfo.getRetcode().equals("1")) {
            //成功:retCode ="1"
            if (loadingView != null && loadingView.isShowing()) {
                loadingView.dismiss();
            }
        } else if (yxdzInfo.getRetcode() != null && yxdzInfo.getRetcode().equals("0")) {
            //失败:retCode ="0"
            if (loadingView != null && loadingView.isShowing()) {
                loadingView.dismiss();
            }
            ToastUtils.showLong(BaseApplication.getAppContext(), "失败:" + yxdzInfo.getRetinfo());
            return;
        } else if (yxdzInfo.getRetcode() != null && yxdzInfo.getRetcode().equals("3")) {
            //失败:retCode ="3" ,用户不存在
            if (loadingView != null && loadingView.isShowing()) {
                loadingView.dismiss();
            }
            ToastUtils.showLong(BaseApplication.getAppContext(), "失败:" + yxdzInfo.getRetinfo());
            onOtherError(yxdzInfo.getRetcode());
            return;
        } else if (yxdzInfo.getRetcode() != null && yxdzInfo.getRetcode().equals("4")) {
            //失败:retCode ="4"，用户登录凭证过期
            if (loadingView != null && loadingView.isShowing()) {
                loadingView.dismiss();
            }
            ToastUtils.showLong(BaseApplication.getAppContext(), "失败:" + yxdzInfo.getRetinfo());
            onOtherError(yxdzInfo.getRetcode());
            return;
        }

//        YidontInfo<String> yidontInfo = (YidontInfo<String>) t;

//        if (yidontInfo.isYdCode()) {
//
//            if (mAlLoading != null)
//                mAlLoading.removeView();//关闭转圈
//
//        } else if (yidontInfo.isYdCodeDebug()) {//方面晓蓉调试查看信息
//
//            ComToast.S(AlApplication.getAppContext(), yidontInfo.getYdMsg());
//
//            ComLog.E(yidontInfo.getYdMsg());
//        } else if (yidontInfo.isYdCodeNoData()) {//没有数据
//
//            if (mAlLoading != null)
//                if (!mAlLoading.isCircleShow()) {
//                    mAlLoading.showNoDataDrawable();
//                }
//
//        } else if (yidontInfo.isYdCodeOtherLogin()) {//其它设备登录的情况处理
//
//            if (mAlLoading != null)
//                mAlLoading.removeView();
//
//            //清空账号
//            AlAccountUtil.getInstance().removeAccount();
//
//        } else if (yidontInfo.isYdCodeBankCard_100008()) {//没有绑定银行卡
//
//            if (mContext == null) return;
//
//            IdentityDialog payDialog = new IdentityDialog(mContext);
//
//            if (TextUtils.isEmpty(yidontInfo.getYdMsg())) {
//
//                payDialog.onShow("您的账户还未绑定银行卡\n是否绑定银行卡", "银行", DealRouter.getmClassMap().get(PersonModule.BankAddActivity_11415));
//
//            } else {
//
//                payDialog.onShow(yidontInfo.getYdMsg(),"银行", DealRouter.getmClassMap().get(PersonModule.BankAddActivity_11415));
//            }
//
//        } else if (yidontInfo.isYdCodeIdentityCard_100007()) {//没有绑定身份证
//            if (mContext == null) return;
//            IdentityDialog payDialog = new IdentityDialog(mContext);
//
//            if (TextUtils.isEmpty(yidontInfo.getYdMsg())) {
//
//                payDialog.onShow("您的账户还未进行身份认证\n是否绑定身份证", null, DealRouter.getmClassMap().get(PersonModule.RealNameAuthAty_11411));
//
//            } else {
//                payDialog.onShow(yidontInfo.getYdMsg(), null, DealRouter.getmClassMap().get(PersonModule.RealNameAuthAty_11411));
//            }
//        }
//
//        if (mAlHeadView != null) {//如果头部是转圈的处理
//
//            mAlHeadView.setAlPbar(View.GONE);
//
//            mAlHeadView.setAlNetWork(View.GONE);
//
//        }
//
//
//        if (mContext != null && yidontInfo.getYdResult() != null) {//模块里需要暂停运营提示
//            if (yidontInfo.getYdResult() != null && mContext != null) {
//
//                RealNameDialog.getInstance(mContext).setReal(yidontInfo.getYdResult()).show();
//            }
//        }


        ///////////////////////////////////////////////////////

        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {

        if (e instanceof HttpException) {
            //HTTP错误 网络错误
            HttpException httpException = (HttpException) e;

            switch (httpException.code()) {
                case HttpCode.UNAUTHORIZED:
                case HttpCode.FORBIDDEN:
                case HttpCode.NOT_FOUND:
                case HttpCode.REQUEST_TIMEOUT:
                case HttpCode.GATEWAY_TIMEOUT:
                case HttpCode.INTERNAL_SERVER_ERROR:
                case HttpCode.BAD_GATEWAY:
                case HttpCode.SERVICE_UNAVAILABLE:
                default:
                    onFailure("网络错误" + httpException.code());
                    //均视为网络错误
//                    mAlLoading.showBrokenNetworkDrawable();
                    break;
            }

        } else if (!NetWorkUtils.isNetworkConnected(mContext)) {
            //没有连接网络
            onNetError();

        } /*else if (!ComNetWorkUtils.isNetConnected(AlApplication.getAppContext())) {
            onAlError("网络异常,没有网络");

            if (mAlLoading != null) {
                mAlLoading.showBrokenNetworkDrawable();
            }
            if (mAlHeadView != null) {//头部有转圈
                mAlHeadView.setAlNetWork(View.VISIBLE);//显示
                mAlHeadView.setAlPbar(View.GONE);
            }

        } */ else if (e instanceof TimeoutException || e instanceof SocketTimeoutException
                || e instanceof ConnectException) {
            onFailure("连接超时");
        } else if (e instanceof JsonSyntaxException) {
            onFailure("Json格式出错了");
            //假如导致这个异常触发的原因是服务器的问题，那么应该让服务器知道，所以可以在这里
            //选择上传原始异常描述信息给服务器
        }
        //服务器
//        else if (e instanceof ServerException) {
//            onAlError("自定义错误:" + e.getMessage());
//        }
        //其它
        else {
            onFailure("未知错误:" + e.getMessage());
        }

    }

    @Override
    public void onCompleted() {
        if (loadingView != null && loadingView.isShowing()) {
            loadingView.dismiss();
        }
    }

    public void onFailure(String message) {
        Log.e("zhu", "网络请求异常信息：" + message);
//        ToastUtils.showShort(BaseApplication.getAppContext(), "" + message);
        if (loadingView != null && loadingView.isShowing()) {
            loadingView.dismiss();
        }
    }

    public void onNetError() {
        if (loadingView != null && loadingView.isShowing()) {
            loadingView.dismiss();
        }
        ToastUtils.showShort(BaseApplication.getAppContext(), "亲,网络不给力,请检查网络连接状态");
    }


    protected abstract void onSuccess(T t);

    protected void onOtherError(String code) {
    }

}
