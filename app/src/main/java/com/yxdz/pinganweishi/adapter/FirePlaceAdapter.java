package com.yxdz.pinganweishi.adapter;

import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxdz.pinganweishi.bean.FirePlaceBean.ListPlaceBean;

import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;

import java.util.List;


/**
 * 场所列表
 */
public class FirePlaceAdapter extends RecyclerView.Adapter<FirePlaceAdapter.ViewHolder> {

    private Context context;
    private List<ListPlaceBean> listPlace;

    public FirePlaceAdapter(Context context, List<ListPlaceBean> listPlace) {
        this.context = context;
        this.listPlace = listPlace;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fire_recyclerview_item, viewGroup, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        if (listPlace.get(i).getStatus() == 2) {
            viewHolder.icon.setImageResource(R.mipmap.fire_alarm_red);
            viewHolder.status.setText("警报");
            viewHolder.status.setTextColor(Color.parseColor("#FF5555"));
        } else {
            viewHolder.icon.setImageResource(R.mipmap.normal_gr);
            viewHolder.status.setText("正常");
            viewHolder.status.setTextColor(Color.parseColor("#49C0AE"));
        }

        switch (listPlace.get(i).getCreateType()) {
            case 1:
                viewHolder.createType.setText("系统");
                break;
            case 2:
                viewHolder.createType.setText("自建");
                break;
            case 3:
                viewHolder.createType.setText("他人");
                break;
        }

        viewHolder.tvTitle.setText(listPlace.get(i).getPlaceName());
        viewHolder.tvAddress.setText("场所地址：" + listPlace.get(i).getPlaceAddress());
        switch (listPlace.get(i).getPlaceType()) {
            case 1:
                viewHolder.tvPlace.setText("场所类别：公司企业");
                break;
            case 2:
                viewHolder.tvPlace.setText("场所类别：工厂企业");
                break;
            case 3:
                viewHolder.tvPlace.setText("场所类别：小作坊");
                break;
            case 4:
                viewHolder.tvPlace.setText("场所类别：小商铺");
                break;
            case 5:
                viewHolder.tvPlace.setText("场所类别：小娱乐场所");
                break;
            case 6:
                viewHolder.tvPlace.setText("场所类别：娱乐场所");
                break;
            case 7:
                viewHolder.tvPlace.setText("场所类别：出租屋");
                break;
            case 8:
                viewHolder.tvPlace.setText("场所类别：宾饭馆");
                break;
            case 9:
                viewHolder.tvPlace.setText("场所类别：学校");
                break;
            case 10:
                viewHolder.tvPlace.setText("场所类别：其他");
                break;
        }

        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, i);
                }
            });
        }
//        viewHolder.cameraList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOnCameraClicklistener.onItemClick(v,i);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return listPlace != null ? listPlace.size() : 0;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        TextView tvTitle;
        TextView tvAddress;
        TextView tvPlace;
        TextView createType;
        TextView status;
//        public ImageView cameraList;


        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.fire_recyclerview_icon);
            tvTitle = view.findViewById(R.id.fire_recyclerview_title);
            tvAddress = view.findViewById(R.id.fire_recyclerview_address);
            tvPlace = view.findViewById(R.id.fire_recyclerview_place);
            createType = view.findViewById(R.id.createType);
            status = view.findViewById(R.id.status);
//            cameraList = view.findViewById(R.id.cameraList);

        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnCameraClicklistener;

    public void setOnCameraClicklistener(OnItemClickListener onItemClickListener) {
        mOnCameraClicklistener = onItemClickListener;
    }


}
