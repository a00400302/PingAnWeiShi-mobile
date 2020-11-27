package com.yxdz.pinganweishi.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yxdz.common.utils.YuXinUrl;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.InspectionRecordBean;
import com.yxdz.pinganweishi.utils.GlideUtils;

import java.util.ArrayList;

/**
 * 记录详情弹窗
 */
public class InspectionRecordDialog extends Dialog {

    private Context context;
    private InspectionRecordBean.ListBean eventBean;

    private TextView tvTitle;
    private TextView tvTime;
//    private TextView tvName;
    private TextView tvRemark;
    private ImageView iconOne;
    private ImageView iconTwo;
    private ImageView iconThree;

    public InspectionRecordDialog(@NonNull Context context, InspectionRecordBean.ListBean eventBean) {
        super(context, R.style.commom_custom_dialog);
        this.context = context;
        this.eventBean = eventBean;
    }

    public InspectionRecordDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected InspectionRecordDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.inspection_record_dialog, null);
        setContentView(view);
        tvTitle = view.findViewById(R.id.inspection_dialog_title);
        tvTime = view.findViewById(R.id.inspection_dialog_time);
//        tvName = view.findViewById(R.id.inspection_dialog_name);
        tvRemark = view.findViewById(R.id.inspection_dialog_remark);

        iconOne = view.findViewById(R.id.inspection_dialog_icon_one);
        iconTwo = view.findViewById(R.id.inspection_dialog_icon_two);
        iconThree = view.findViewById(R.id.inspection_dialog_icon_three);

        //0、巡检报告；1、整改报告；
        if (eventBean.getType() == 0) {
            tvTitle.setText("巡检记录");
        } else if (eventBean.getType() == 1) {
            tvTitle.setText("整改记录");
        }

        tvTime.setText(eventBean.getCreateTime());
//        tvName.setText("" + eventBean.getCreateBy());
        tvRemark.setText("" + eventBean.getRemark());

        if (!TextUtils.isEmpty(eventBean.getEquipmentPic1())) {
//
            GlideUtils.displayDefault(context, iconOne, YuXinUrl.SERVER + eventBean.getEquipmentPic1());
        }
        if (!TextUtils.isEmpty(eventBean.getEquipmentPic2())) {
            GlideUtils.displayDefault(context, iconTwo, YuXinUrl.SERVER + eventBean.getEquipmentPic2());
        }
        if (!TextUtils.isEmpty(eventBean.getEquipmentPic3())) {
            GlideUtils.displayDefault(context, iconThree, YuXinUrl.SERVER + eventBean.getEquipmentPic3());
        }

        String pic = "" + eventBean.getEquipmentPic1() + "," + eventBean.getEquipmentPic2() + "," + eventBean.getEquipmentPic3();
        String spit[] = pic.split(",");

        final ArrayList<String> pictureList = new ArrayList<>();


        for (int i = 0; i < spit.length; i++) {
            if (!TextUtils.isEmpty(spit[i])) {
                pictureList.add(spit[i]);
            }

        }

//        final String picture[] = {eventBean.getEquipmentPicError1(), eventBean.getEquipmentPicError2(), eventBean.getEquipmentPicError3()};

        iconOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(eventBean.getEquipmentPic1())) {
                    return;
                }
                Intent intent = new Intent(context, PictureBrowseAty.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("pictureList", pictureList);
                bundle.putInt("position", 0);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        iconTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(eventBean.getEquipmentPic2())) {
                    return;
                }
                Intent intent = new Intent(context, PictureBrowseAty.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("pictureList", pictureList);
                bundle.putInt("position", 1);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        iconThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(eventBean.getEquipmentPic3())) {
                    return;
                }
                Intent intent = new Intent(context, PictureBrowseAty.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("pictureList", pictureList);
                bundle.putInt("position", 2);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


//        "checkTime": "2018-07-24T20:11:03",
//        "equipmentPicError1": "192.168.2.115:8080//surpass/equipmentPic/俊鹏2018-07-24 20-10-579.jpg",
//        "equipmentPicError2": "",
//        "equipmentPicError3": "",
//        "firefightingEquipmentId": 1,//
//        "id": 14,//
//        "isQualified": 1,//
//        "pusher": "俊鹏",
//        "remakType": 0,//
//        "remark": "测试"

//        WindowManager windowManager = ((Activity) context).getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
//        lp.width = (int) (display.getWidth()); // 设置最大宽度
//        this.getWindow().setAttributes(lp);


        //以下内容放在onCreate中才生效
        //获取屏幕宽高

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
//        int height = display.getHeight();
        //设置弹框的高为屏幕的一半宽是屏幕的宽
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.x与lp.y表示相对于原始位置的偏移.
        //将对话框的大小按屏幕大小的百分比设置
        lp.width = (int) (width * 1); // 宽度
        this.getWindow().setAttributes(lp);
//        lp.height = (int) (height * 0.6); // 高度
//        dialogWindow.setAttributes(lp);


    }

    /**
     * 过滤时间字符串T
     */
    private String splitTimeT(String time) {
        String[] splitTime = time.split(" ");
        if (splitTime.length >= 2) {
            String s = "";
            for (int i = 0; i < splitTime.length; i++) {
                s = s + "     " + splitTime[i];
            }
            return s;
        } else {
            return "";
        }
    }

}
