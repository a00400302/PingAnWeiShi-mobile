package com.yxdz.pinganweishi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.activity.FireDeviceActivity;
import com.yxdz.pinganweishi.activity.FireDeviceInfoActivity;
import com.yxdz.pinganweishi.bean.FireSmokeEquipmentBean.ListSmokeEquipmentBean;

import java.util.List;

/**
 * 设备列表
 */
public class FireDeviceAdapter extends RecyclerView.Adapter<FireDeviceAdapter.ViewHolder> {

    private final int createType;
    private final double lat;
    private final double lng;
    private Context context;
    private List<ListSmokeEquipmentBean> listSmokeEquipmentBeanList;
    private String placeName;
    private String placeAddress;

    public FireDeviceAdapter(Context context, List<ListSmokeEquipmentBean> listSmokeEquipmentBeanList, int createType, String lat, String lng) {
        this.createType = createType;
        this.context = context;
        if (lat.isEmpty() || lng.isEmpty()) {
            this.lat = 0.0;
            this.lng = 0.0;
        } else {
            this.lat = Double.parseDouble(lat);
            this.lng = Double.parseDouble(lng);
        }

        this.listSmokeEquipmentBeanList = listSmokeEquipmentBeanList;
    }

    @Override
    public FireDeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fire_device_recyclerview_item, viewGroup, false);
        return new FireDeviceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FireDeviceAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.tvTitle.setText(listSmokeEquipmentBeanList.get(i).getEquipmentName() + "[" + listSmokeEquipmentBeanList.get(i).getSn() + "]");
        viewHolder.tvPos.setText(listSmokeEquipmentBeanList.get(i).getLocation());
        //iswork 0,离线，1，在线


        if (listSmokeEquipmentBeanList.get(i).getWarning() == 2) {
            viewHolder.status_image.setImageDrawable(context.getResources().getDrawable(R.mipmap.fire_alarm_small_red));
            viewHolder.tvState.setTextColor(Color.parseColor("#FF5555"));
            viewHolder.tvState.setText("报警");
        } else {
            if (listSmokeEquipmentBeanList.get(i).getIsWork() == 0) {
                viewHolder.status_image.setImageDrawable(context.getResources().getDrawable(R.mipmap.abnormal_small_ye));
                viewHolder.tvState.setTextColor(context.getResources().getColor(R.color.txt_color_yello));
                viewHolder.tvState.setText("离线");
            } else {
                viewHolder.status_image.setImageDrawable(context.getResources().getDrawable(R.mipmap.normal_small_gr));
                viewHolder.tvState.setTextColor(context.getResources().getColor(R.color.txt_color_green));
                viewHolder.tvState.setText("正常");
            }
        }


        if (listSmokeEquipmentBeanList.get(i).getDumpEnergy() != null) {
            viewHolder.bartery.setText(listSmokeEquipmentBeanList.get(i).getDumpEnergy().toString());
        }
        if (listSmokeEquipmentBeanList.get(i).getLastContact() == null) {
            viewHolder.connectTime.setText("----");
        } else {
            viewHolder.connectTime.setText(listSmokeEquipmentBeanList.get(i).getLastContact() + "");
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设备详情
                Intent intent = new Intent(context, FireDeviceInfoActivity.class);
                intent.putExtra("deviceId", listSmokeEquipmentBeanList.get(i).getId() + "");
                intent.putExtra("placeAddress", placeAddress);
                intent.putExtra("placeName", placeName);
                intent.putExtra("listSmokeEquipmentBean", listSmokeEquipmentBeanList.get(i));
                intent.putExtra("createType", createType);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                ((Activity) context).startActivityForResult(intent, FireDeviceActivity.FireDeviceRequestCode);
            }
        });

        switch (listSmokeEquipmentBeanList.get(i).getEqType()) {
            case 1:
                viewHolder.deviceType.setText("烟感");
                break;
            case 2:
                viewHolder.deviceType.setText("人体");
                break;
            case 3:
                viewHolder.deviceType.setText("人体+烟感");
                break;
            case 4:
                viewHolder.deviceType.setText("应急按钮");
                break;
            case 5:
                viewHolder.deviceType.setText("燃气");
                break;
            case 7:
                viewHolder.deviceType.setText("应急灯");
                break;
            case 8:
                viewHolder.deviceType.setText("门磁");
                break;
            case 9:
                viewHolder.deviceType.setText("读卡器");
                break;
            case 10:
                viewHolder.deviceType.setText("固件模式");
                break;
            case 11:
                viewHolder.deviceType.setText("出厂模式");
                break;
            case 13:
                viewHolder.deviceType.setText("烟感+人体+烟雾");
                break;
            case 14:
                viewHolder.deviceType.setText("人体+温感");
                break;
            case 15:
                viewHolder.deviceType.setText("烟感+温感");
                break;
            case 16:
                viewHolder.deviceType.setText("温感");
                break;
        }


    }

    @Override
    public int getItemCount() {
        return listSmokeEquipmentBeanList != null ? listSmokeEquipmentBeanList.size() : 0;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素18824743766
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvPos;
        TextView tvState;
        TextView bartery;
        TextView connectTime;
        ImageView status_image;
        TextView deviceType;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.fire_device_recyclerview_title);
            tvPos = view.findViewById(R.id.fire_device_recyclerview_pos);
            tvState = view.findViewById(R.id.fire_device_recyclerview_state);
            bartery = view.findViewById(R.id.bartery);
            status_image = view.findViewById(R.id.status_image);
            connectTime = view.findViewById(R.id.connectTime);
            deviceType = view.findViewById(R.id.deviceType);
        }
    }
}








