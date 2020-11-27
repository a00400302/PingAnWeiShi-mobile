package com.yxdz.pinganweishi.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.FirePlaceBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;

import java.util.List;


/**
 * 场所列表
 */
public class InspectionPlaceAdapter extends RecyclerView.Adapter<InspectionPlaceAdapter.ViewHolder> {

    private Context context;
    private List<FirePlaceBean.ListPlaceBean> listPlace;

    public InspectionPlaceAdapter(Context context, List<FirePlaceBean.ListPlaceBean> listPlace) {
        this.context = context;
        this.listPlace = listPlace;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inspection_recyclerview_item, viewGroup, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        if (listPlace.get(i).getStatus() == 2) {
            viewHolder.icon.setImageResource(R.mipmap.unqualified_red);
        } else {
            viewHolder.icon.setImageResource(R.mipmap.qualified_gr);
        }


        viewHolder.tvTitle.setText(listPlace.get(i).getPlaceName());
//        viewHolder.tvAddress.setMovementMethod(ScrollingMovementMethod.getInstance());
        viewHolder.tvAddress.setText("场所地址：" + listPlace.get(i).getPlaceAddress());
        viewHolder.tvArea.setText("所属区域：" + listPlace.get(i).getAdministrativeRegions());
        switch (listPlace.get(i).getPlaceType()) {
            case 1:
                viewHolder.tvType.setText("场所类别：公司企业");
                break;
            case 2:
                viewHolder.tvType.setText("场所类别：工厂企业");
                break;
            case 3:
                viewHolder.tvType.setText("场所类别：小作坊");
                break;
            case 4:
                viewHolder.tvType.setText("场所类别：小商铺");
                break;
            case 5:
                viewHolder.tvType.setText("场所类别：小娱乐场所");
                break;
            case 6:
                viewHolder.tvType.setText("场所类别：娱乐场所");
                break;
            case 7:
                viewHolder.tvType.setText("场所类别：出租屋");
                break;
            case 8:
                viewHolder.tvType.setText("场所类别：宾饭馆");
                break;
            case 9:
                viewHolder.tvType.setText("场所类别：学校");
                break;
            case 10:
                viewHolder.tvType.setText("场所类别：其他");
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
    }

    @Override
    public int getItemCount() {
        return listPlace != null ? listPlace.size() : 0;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView tvTitle;
        public TextView tvAddress;
        public TextView tvType;
        public TextView tvArea;

        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.inspection_recyclerview_icon);
            tvTitle = view.findViewById(R.id.inspection_recyclerview_title);
            tvAddress = view.findViewById(R.id.inspection_recyclerview_place);
            tvType = view.findViewById(R.id.inspection_recyclerview_type);
            tvArea = view.findViewById(R.id.inspection_recyclerview_arear);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


}
