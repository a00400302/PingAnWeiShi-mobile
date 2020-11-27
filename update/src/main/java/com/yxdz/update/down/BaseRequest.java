package com.yxdz.update.down;

import android.os.Message;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by huang on 2018/6/1.
 */

public class BaseRequest implements Runnable {
    private DownloadInfo downloadInfo;
    private DownloadUIHandler mDownloadUIHandler;    //下载UI回调
    protected String taskKey;
    private Request mRequest;
    private okhttp3.Call okCall;
    private long lastRefreshUiTime;
    private boolean isPause=true;                         //当前任务是暂停还是停止， true 暂停， false 停止

    public BaseRequest(String taskKey) {
        this.taskKey = taskKey;
        mDownloadUIHandler = DownloadManager.getInstance().getHandler();

    }

    public String getUrl() {
        return taskKey;
    }



    private void startDown() {


    }

    @Override
    public void run() {
        isPause = false;
        downloadInfo = DownloadManager.getInstance().getDownloadInfo(taskKey);
        File downloadFile = new File(DownloadManager.getInstance().getTargetFolder(), downloadInfo.getFileName() + ".tmp");
        String fileSize = null;
        if (downloadFile.exists()) {
            downloadInfo.setDownloadLength(downloadFile.length());
            fileSize = "bytes=" + downloadFile.length() + "-";
        }
        //创建 OkHttp 对象相关
        OkHttpClient client = new OkHttpClient();
        //如果有临时文件,则在下载的头中添加下载区域
        if (!TextUtils.isEmpty(fileSize)) {
            mRequest = new Request.Builder().url(taskKey).header("Range", fileSize).build();
        } else {
            mRequest = new Request.Builder().url(taskKey).build();
        }
//        Retrofit.Builder
        try {
            okCall = client.newCall(mRequest);
            //设置输出流.
            OutputStream outPutStream;
            //检测是否支持断点续传
            okhttp3.Response response = okCall.execute();
            okhttp3.ResponseBody responseBody = response.body();
            String responeRange = response.headers().get("Content-Range");
            if (responeRange == null || !responeRange.contains(Long.toString(downloadFile.length()))) {
                //最后的标记为 true 表示下载的数据可以从上一次的位置写入,否则会清空文件数据.
                outPutStream = new FileOutputStream(downloadFile, false);
            } else {
                outPutStream = new FileOutputStream(downloadFile, true);
            }
            //获取流对象，准备进行读写文件
            long totalLength = response.body().contentLength()+downloadInfo.getDownloadLength();
            if (downloadInfo.getTotalLength() == 0) {
                downloadInfo.setTotalLength(totalLength);
            }
            InputStream inputStream = responseBody.byteStream();
            //如果有下载过的历史文件,则把下载总大小设为 总数据大小+文件大小 . 否则就是总数据大小
//            if (TextUtils.isEmpty(fileSize)) {
////            bean.setTotalSize(responseBody.contentLength());
//            } else {
////            bean.setTotalSize(responseBody.contentLength() + downloadFile.length());
//            }

            int length;
            //设置缓存大小
            byte[] buffer = new byte[1024];
            long total =+downloadInfo.getDownloadLength();
            //开始写入文件
            while ((length = inputStream.read(buffer)) != -1) {
                outPutStream.write(buffer, 0, length);
                total += length;
                downloadInfo.setDownloadLength(total);
                float progress = total * 1.0f / downloadInfo.getTotalLength() *100.0f;
                downloadInfo.setProgress(progress);
                downloadInfo.setState(DownloadManager.DOWNLOADING);
                long curTime = System.currentTimeMillis();
                //每200毫秒刷新一次数据
                if (curTime - lastRefreshUiTime >= 1000 || progress == 1.0f) {
                    postMessage(null, null);
                    lastRefreshUiTime = System.currentTimeMillis();
                }
            }
            //清空缓冲区
            outPutStream.flush();
            outPutStream.close();
            inputStream.close();
            downloadInfo.setState(DownloadManager.FINISH); //下载完成
            downloadFile.renameTo(new File(DownloadManager.getInstance().getTargetFolder(), downloadInfo.getFileName()));
            postMessage(null,null);
        } catch (Exception e){
            e.printStackTrace();
            isPause = true;
            downloadInfo.setState(DownloadManager.ERROR);
            postMessage(e.getMessage(),e);
        }

    }

    private void postMessage(String errorMsg, Exception e) {
        DownloadUIHandler.MessageBean messageBean = new DownloadUIHandler.MessageBean();
        messageBean.downloadInfo = downloadInfo;
        messageBean.errorMsg = errorMsg;
        messageBean.e = e;
        Message msg = mDownloadUIHandler.obtainMessage();
        msg.obj = messageBean;
        mDownloadUIHandler.sendMessage(msg);
    }
    public void pause() {
        okCall.cancel();
        if (downloadInfo.getState() == DownloadManager.WAITING) {
            //如果是等待状态的,因为该状态取消不会回调任何方法，需要手动触发
            downloadInfo.setNetworkSpeed(0);
            downloadInfo.setState(DownloadManager.PAUSE);
            postMessage(null, null);
        } else {
            isPause = true;
        }
    }

}
