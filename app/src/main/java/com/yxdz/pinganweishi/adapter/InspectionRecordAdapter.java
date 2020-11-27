package com.yxdz.pinganweishi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.InspectionRecordBean;
import com.yxdz.pinganweishi.utils.GlideUtils;
import com.yxdz.pinganweishi.view.PictureBrowseAty;

import java.util.ArrayList;
import java.util.List;

/**
 * 巡检记录适配器
 */
public class InspectionRecordAdapter extends RecyclerView.Adapter<InspectionRecordAdapter.ViewHolder> {

    String timeHolder = "";
    boolean isOnRefresh;
    private Context context;
    private List<InspectionRecordBean.ListBean> eventBeanList = new ArrayList<>();

    public InspectionRecordAdapter(Context context, List<InspectionRecordBean.ListBean> eventBeanList) {
        this.context = context;
        this.eventBeanList = eventBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inspection_record_layout_item, viewGroup, false);

        return new ViewHolder(view);

    }

    public void setOnRefresh(boolean onRefresh) {
        isOnRefresh = onRefresh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
//        //模拟数据
//        if (eventBeanList.get(position).isDisplayTime()) {
//            viewHolder.tvTime.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.tvTime.setVisibility(View.GONE);
//        }
        if (isOnRefresh) {
            timeHolder = null;
        }
        String time = splitTime(eventBeanList.get(position).getCreateTime(), 0);
        if (time.equals(timeHolder) && timeHolder != null) {
            viewHolder.record_time_layout.setVisibility(View.GONE);
        } else {
            viewHolder.record_time_layout.setVisibility(View.VISIBLE);
            viewHolder.record_time.setText(time);
            timeHolder = time;
            isOnRefresh = false;
        }


//        if (eventBeanList.get(position).getType() == 1) {
        viewHolder.record_title.setText("巡检");
//        } else {
//            viewHolder.record_title.setText("整改");
//        }


        viewHolder.record_time_detail.setText(splitTime(eventBeanList.get(position).getCreateTime(), 1));
        viewHolder.record.setText(eventBeanList.get(position).getRemark());


        if (!TextUtils.isEmpty(eventBeanList.get(position).getEquipmentPic1())) {
            GlideUtils.displayDefault(context, viewHolder.inspection_dialog_icon_one, eventBeanList.get(position).getEquipmentPic1());
            viewHolder.record_image_layout.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(eventBeanList.get(position).getEquipmentPic2())) {
            GlideUtils.displayDefault(context, viewHolder.inspection_dialog_icon_two, eventBeanList.get(position).getEquipmentPic2());
        }
        if (!TextUtils.isEmpty(eventBeanList.get(position).getEquipmentPic3())) {
            GlideUtils.displayDefault(context, viewHolder.inspection_dialog_icon_three, eventBeanList.get(position).getEquipmentPic3());
        }


        String pic = "" + eventBeanList.get(position).getEquipmentPic1() + "," + eventBeanList.get(position).getEquipmentPic2() + "," + eventBeanList.get(position).getEquipmentPic3();
        String spit[] = pic.split(",");

        final ArrayList<String> pictureList = new ArrayList<>();


        for (int i = 0; i < spit.length; i++) {
            if (!TextUtils.isEmpty(spit[i])) {
                pictureList.add(spit[i]);
            }
        }

        viewHolder.inspection_dialog_icon_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PictureBrowseAty.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("pictureList", pictureList);
                bundle.putInt("position", 1);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        viewHolder.inspection_dialog_icon_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PictureBrowseAty.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("pictureList", pictureList);
                bundle.putInt("position", 1);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        viewHolder.inspection_dialog_icon_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PictureBrowseAty.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("pictureList", pictureList);
                bundle.putInt("position", 1);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventBeanList != null ? eventBeanList.size() : 0;
    }

    private String splitTime(String time, int pos) {
        String[] splitTime = time.split(" ");
        if (splitTime.length >= 2) {
            return splitTime[pos];
        } else {
            return "";
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView record_time;
        TextView record_title;
        TextView record_time_detail;
        TextView record;
        LinearLayout record_image_layout;
        LinearLayout record_time_layout;
        ImageView inspection_dialog_icon_one;
        ImageView inspection_dialog_icon_two;
        ImageView inspection_dialog_icon_three;


        public ViewHolder(View view) {
            super(view);
            record_time = view.findViewById(R.id.record_time);
            record_title = view.findViewById(R.id.record_title);
            record_time_detail = view.findViewById(R.id.record_time_detail);
            record = view.findViewById(R.id.record);
            record_image_layout = view.findViewById(R.id.record_image_layout);
            record_time_layout = view.findViewById(R.id.record_time_layout);
            inspection_dialog_icon_one = view.findViewById(R.id.inspection_dialog_icon_one);
            inspection_dialog_icon_two = view.findViewById(R.id.inspection_dialog_icon_two);
            inspection_dialog_icon_three = view.findViewById(R.id.inspection_dialog_icon_three);
        }
    }

}
