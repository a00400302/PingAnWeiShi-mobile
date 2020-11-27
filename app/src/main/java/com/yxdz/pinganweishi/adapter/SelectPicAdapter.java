package com.yxdz.pinganweishi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.SelectPicBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;
import com.yxdz.pinganweishi.utils.GlideUtils;

import java.util.List;

/**
 * 不合格时选择3张图片
 */
public class SelectPicAdapter extends BaseAdapter {

    private Context context;
    private List<SelectPicBean> selectPicBeanList;

    public SelectPicAdapter(Context context, List<SelectPicBean> selectPicBeanList) {
        this.context = context;
        this.selectPicBeanList = selectPicBeanList;
    }

    @Override
    public int getCount() {
        return selectPicBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.select_pic_item, null);
            viewHolder.icon = convertView.findViewById(R.id.select_pic_icon);
            viewHolder.tvDelete = convertView.findViewById(R.id.select_pic_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        if (TextUtils.isEmpty(selectPicBeanList.get(position).getIcon()) || selectPicBeanList.get(position).getIcon() == null) {
//            //最后一个数据
//            viewHolder.tvDelete.setVisibility(View.GONE);
//            viewHolder.icon.setImageResource(R.mipmap.inspection_device_add_pic);
////            GlideUtils.displayDefault(context, viewHolder.icon, "");
//        } else {
//            viewHolder.tvDelete.setVisibility(View.VISIBLE);
//            GlideUtils.displayDefault(context, viewHolder.icon, selectPicBeanList.get(position).getIcon());
//        }

        if (!TextUtils.isEmpty(selectPicBeanList.get(position).getIcon()) && selectPicBeanList.get(position).getIcon() != null) {
            viewHolder.tvDelete.setVisibility(View.VISIBLE);
            GlideUtils.displayDefault(context, viewHolder.icon, selectPicBeanList.get(position).getIcon());
        } else if (selectPicBeanList.get(position).getIcon() == null) {
            //最后一个数据
            viewHolder.tvDelete.setVisibility(View.GONE);
            viewHolder.icon.setImageResource(R.mipmap.inspection_device_add_pic);
        }


        if (mOnItemClickListener != null && selectPicBeanList.size() == position + 1) {
            viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }

        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicBeanList.remove(selectPicBeanList.get(position));
                //不够三个就再加一个+
                if (selectPicBeanList.size() < 3 && !TextUtils.isEmpty(selectPicBeanList.get(selectPicBeanList.size() - 1).getIcon())) {
                    SelectPicBean selectPicBean = new SelectPicBean();
                    selectPicBeanList.add(selectPicBean);
                }
                notifyDataSetChanged();
            }
        });


        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView tvDelete;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

}
