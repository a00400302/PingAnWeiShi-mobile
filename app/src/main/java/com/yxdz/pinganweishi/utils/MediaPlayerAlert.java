package com.yxdz.pinganweishi.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;


import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.base.MyApplication;

import java.io.IOException;

public class MediaPlayerAlert {
    /**
     * 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载
     */
    private static MediaPlayerAlert instance = null;
    private MediaPlayer mediaPlayer;

    public static MediaPlayerAlert getInstance() {
        if (instance == null) {
            synchronized (MediaPlayerAlert.class) {
                if (instance == null) {
                    instance = new MediaPlayerAlert();
                }
            }
        }
        return instance;
    }

    public MediaPlayer getMediaPlayer(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            Uri uri = Uri.parse("android.resource://" + MyApplication.getAppContext().getPackageName() + "/" + R.raw.fire_alarm);
            try {
                mediaPlayer.setDataSource(context, uri);//设置播放音频文件的路径
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();//mp就绪
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mediaPlayer;
    }

    public void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void setRelease() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}
