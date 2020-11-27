package com.yxdz.pinganweishi.adapter;


import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.MagnetismBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class MagnetismBeanListAdapter extends RecyclerView.Adapter<MagnetismBeanListAdapter.ViewHolder> {

    private ArrayList<ViewHolder> holders = new ArrayList<ViewHolder>();
    private final Context cameraListActivity;
    private final List<MagnetismBean.DataBean.LockListBean> doorListBeans;

    public MagnetismBeanListAdapter(Context cameraListActivity, List<MagnetismBean.DataBean.LockListBean> doorListBeans) {
        this.cameraListActivity = cameraListActivity;
        this.doorListBeans = doorListBeans;
        Log.d("CameraListAdapter", "doorListBeans:" + doorListBeans);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.magnetism_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        holders.add(viewHolder);

        viewHolder.lockName.setText(doorListBeans.get(i).getName());
//        viewHolder.lockSNcode.setText(doorListBeans.get(i).getDoorLockSn());

        viewHolder.doorOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoorClickListener.onItemClick(v,i);
            }
        });
        viewHolder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return doorListBeans.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView lockName;
        CardView doorOpen;
        RelativeLayout item_layout;

        public ViewHolder(View view) {
            super(view);
            lockName = view.findViewById(R.id.lockName);
            doorOpen= view.findViewById(R.id.doorOpen);
            item_layout= view.findViewById(R.id.item_layout);
        }

    }


    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onDoorClickListener;

    public void setonDoorClickListener(OnItemClickListener onDoorClickListener) {
        this.onDoorClickListener = onDoorClickListener;
    }


}
