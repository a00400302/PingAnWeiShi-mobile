package com.yxdz.pinganweishi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.GateBean;

import java.util.List;

public class GateAdapter extends RecyclerView.Adapter<GateAdapter.ViewHolder> {


    List<GateBean.DataBean> list;

    public GateAdapter(Context context, List<GateBean.DataBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gate_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fire_warning.setText(list.get(position).getWarning());
        holder.record_time_detail.setText(list.get(position).getCreateTime());
        holder.gate_power.setText("" + list.get(position).getPower());
        holder.gate_isconnected.setText(list.get(position).getBand());
        holder.gate_voltage.setText("" + list.get(position).getVoltage());
        switch (list.get(position).getType()) {
            case 17:
                holder.record_title.setText("阀关闭事件");
                holder.power_layout.setVisibility(View.GONE);
                holder.close_layout.setVisibility(View.VISIBLE);
                break;
            case 18:
                holder.record_title.setText("阀电量更新事件");
                holder.power_layout.setVisibility(View.VISIBLE);
                holder.close_layout.setVisibility(View.GONE);
                break;
        }
        holder.gate_num.setText(list.get(position).getNum());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView fire_warning;
        private final TextView record_title;
        private final TextView record_time_detail;
        private final TextView gate_power;
        private final TextView gate_isconnected;
        private final TextView gate_voltage;
        private final TextView gate_num;
        private final LinearLayout power_layout;
        private final LinearLayout close_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            record_title = itemView.findViewById(R.id.record_title);
            record_time_detail = itemView.findViewById(R.id.record_time_detail);
            gate_power = itemView.findViewById(R.id.gate_power);
            gate_isconnected = itemView.findViewById(R.id.gate_isconnected);
            fire_warning = itemView.findViewById(R.id.fire_warning);
            gate_voltage = itemView.findViewById(R.id.gate_voltage);
            gate_num = itemView.findViewById(R.id.gate_num);
            power_layout = itemView.findViewById(R.id.power_layout);
            close_layout = itemView.findViewById(R.id.close_layout);
        }
    }
}
