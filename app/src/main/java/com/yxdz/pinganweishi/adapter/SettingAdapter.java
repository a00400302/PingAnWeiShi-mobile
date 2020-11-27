package com.yxdz.pinganweishi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.activity.SettingActivity;
import com.yxdz.pinganweishi.bean.ServerBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {


    private List<ServerBean.DataBean> serverBeans;
    private Context context;
    private OnItemClickListener listener;


    public SettingAdapter(List<ServerBean.DataBean> serverBeans, SettingActivity context) {
        this.serverBeans = serverBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.server_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.server_name.setText(serverBeans.get(position).getServerName());
        holder.server_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serverBeans.size();
    }

    public void setOnliceListener(OnItemClickListener onliceListener) {
        this.listener = onliceListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView server_name;
        private final CardView server_select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            server_select = itemView.findViewById(R.id.server_select);
            server_name = itemView.findViewById(R.id.server_name);
        }
    }
}
