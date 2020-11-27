package com.yxdz.pinganweishi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.MapBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;


import java.util.List;


public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {

    List<MapBean> mapBeans;
    private OnItemClickListener onItemClickListener;


    public MapAdapter(List<MapBean> mapBeans) {
        this.mapBeans = mapBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mapItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });
        holder.mapText.setText(mapBeans.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return mapBeans.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mapItem;
        TextView mapText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mapItem = itemView.findViewById(R.id.mapItem);
            mapText = itemView.findViewById(R.id.mapText);

        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
