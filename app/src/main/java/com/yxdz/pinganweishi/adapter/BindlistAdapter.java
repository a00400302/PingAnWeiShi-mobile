package com.yxdz.pinganweishi.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.BindDeviceBean;
import com.yxdz.pinganweishi.interfac.OnOKClickListener;

import java.util.ArrayList;
import java.util.List;

public class BindlistAdapter extends RecyclerView.Adapter<BindlistAdapter.ViewHolder> {

    List<ViewHolder> viewHolders = new ArrayList<>();
    private final Context context;
    private final List<BindDeviceBean.DataBean.DeviceBindListBean> bindDeviceBeans;


    public BindlistAdapter(Context context, List<BindDeviceBean.DataBean.DeviceBindListBean> bindDeviceBeans) {
        this.context = context;
        this.bindDeviceBeans = bindDeviceBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bindlist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolders.add(viewHolder);
        if (bindDeviceBeans.get(i).getChecked() == 1) {
            viewHolder.deciceIsBind.setChecked(true);
        } else {
            viewHolder.deciceIsBind.setChecked(false);
        }

        viewHolder.deciceIsBind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (viewHolder.deciceIsBind.isChecked()) {
                    bindDeviceBeans.get(i).setChecked(1);
                } else {
                    bindDeviceBeans.get(i).setChecked(0);
                }
            }
        });


        viewHolder.deviceName.setText(bindDeviceBeans.get(i).getEquipmentName());
        viewHolder.deviceSn.setText(bindDeviceBeans.get(i).getSn());
    }

    @Override
    public int getItemCount() {
        return bindDeviceBeans.size();
    }

    public void submit() {
        onItemClickListener.onOkClick(bindDeviceBeans);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName;
        TextView deviceSn;
        Switch deciceIsBind;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.deviceName);
            deviceSn = itemView.findViewById(R.id.deviceSn);
            deciceIsBind = itemView.findViewById(R.id.deciceIsBind);
        }
    }

    private OnOKClickListener onItemClickListener;

    public void setOnItemClickListener(OnOKClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
