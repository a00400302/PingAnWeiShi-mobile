package com.yxdz.pinganweishi.adapter;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.pinganweishi.MqttClient;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.DoorLockListBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class DoorLockListAdapter extends RecyclerView.Adapter<DoorLockListAdapter.ViewHolder> {

    private ArrayList<ViewHolder> holders = new ArrayList<ViewHolder>();
    private final Context cameraListActivity;
    private final List<DoorLockListBean.DoorListBean> doorListBeans;

    public DoorLockListAdapter(Context cameraListActivity, List<DoorLockListBean.DoorListBean> doorListBeans) {
        this.cameraListActivity = cameraListActivity;
        this.doorListBeans = doorListBeans;
        Log.d("CameraListAdapter", "doorListBeans:" + doorListBeans);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doorlock_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        holders.add(viewHolder);

        viewHolder.lockName.setText(doorListBeans.get(i).getDoorLockName());
        viewHolder.lockSNcode.setText(doorListBeans.get(i).getDoorLockSn());
        if (doorListBeans.get(i).getStatus() == 1) {
            viewHolder.lockStatus.setText("在线");
            viewHolder.lockStatus.setTextColor(Color.GREEN);
        } else {
            viewHolder.lockStatus.setText("休眠");
            viewHolder.lockStatus.setTextColor(Color.GRAY);
            viewHolder.doorOpen.setBackgroundColor(Color.GRAY);
        }

        viewHolder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, i);
            }
        });
        viewHolder.doorOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doorListBeans.get(i).getStatus() == 1){
                    MqttClient.getInstance(cameraListActivity).sendMessage(MqttClient.TOPIC_CONTROL+doorListBeans.get(i).getDoorLockSn(),"CHENGYI0401030D01");
                    Toast.makeText(cameraListActivity, (doorListBeans.get(i).getDoorLockName() + "设备已开锁"), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(cameraListActivity, "设备处于休眠状态", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (doorListBeans.get(i).isShow != false){
             viewHolder.doorOpen.setBackgroundColor(Color.WHITE);
        }
    }


    @Override
    public int getItemCount() {
        return doorListBeans.size();
    }

    public void showDoor(int postation) {
        doorListBeans.get(postation).isShow = true;
        doorListBeans.get(postation).setStatus(1);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView lockName;
        TextView lockSNcode;
        TextView lockStatus;
        ImageView doorOpen;
        private CardView item_layout;

        public ViewHolder(View view) {
            super(view);
            item_layout = view.findViewById(R.id.item_layout);
            lockName = view.findViewById(R.id.lockName);
            lockSNcode = view.findViewById(R.id.lockSNcode);
            lockStatus = view.findViewById(R.id.lockStatus);
            doorOpen = view.findViewById(R.id.doorOpen);
        }

    }


    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
