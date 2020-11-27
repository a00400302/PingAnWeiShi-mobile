package com.yxdz.pinganweishi.adapter;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.InspectionEquipmentBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;

import java.util.List;


/**
 * 巡检设备列表适配器
 */
public class InspectionDeviceAdapter extends RecyclerView.Adapter<InspectionDeviceAdapter.ViewHolder> {

    private Context context;
    private List<InspectionEquipmentBean.ListFirefightingEquipmenForMobleBean> listFirefightingEquipmenForMobileBeanList;

    public InspectionDeviceAdapter(Context context, List<InspectionEquipmentBean.ListFirefightingEquipmenForMobleBean> listFirefightingEquipmenForMobileBeanList) {
        this.context = context;
        this.listFirefightingEquipmenForMobileBeanList = listFirefightingEquipmenForMobileBeanList;
    }


    @Override
    public InspectionDeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inspection_device_recyclerview_item, viewGroup, false);

        return new InspectionDeviceAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {


        viewHolder.tvTitle.setText("" + listFirefightingEquipmenForMobileBeanList.get(i).getEquipmentName());
        // 1、合格正常 2、不合格异常
        if (listFirefightingEquipmenForMobileBeanList.get(i).getIsQualified() == 1) {
            viewHolder.tvState.setText("" + "合格");
            viewHolder.tvState.setTextColor(context.getResources().getColor(R.color.txt_color_green));
        } else if (listFirefightingEquipmenForMobileBeanList.get(i).getIsQualified() == 2) {
            viewHolder.tvState.setText("" + "不合格");
            viewHolder.tvState.setTextColor(Color.RED);
        }

        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listFirefightingEquipmenForMobileBeanList != null ? listFirefightingEquipmenForMobileBeanList.size() : 0;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素18824743766
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvState;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.inspection_device_recyclerview_title);
            tvState = view.findViewById(R.id.inspection_device_recyclerview_state);

        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 显示设备类型
     *
     * @param type
     */
    private String setDeviceType(int type) {
        String string = "";
        switch (type) {
            case 1://1 : 自动火灾报警系统（包括烟感、温感、报警按钮、警铃、广播、报警模块、电话等）
                string = "自动火灾报警系统";
                break;
            case 2://2 :  消防水灭火系统（喷淋、消火栓、湿式报警阀、干式报警阀等）
                string = "消防水灭火系统";
                break;
            case 3://3 : 气体灭火系统（七氟丙烷、二氧化碳）
                string = "气体灭火系统";
                break;
            case 4://4 : 应急照明灯与疏散指灯
                string = "应急照明灯与疏散指灯";
                break;
            case 5://5 :  灭火器（手提式灭火器、推车式灭火器）
                string = "灭火器";
                break;
            case 6://6 : 防排烟系统
                string = "防排烟系统";
                break;
            case 7://7 : 以及其他附属设施（水泵、阀门、防火卷帘门、防火门，疏散通道、应急通道等）
                string = "其他附属设施";
                break;
        }
        return string;

    }

}

