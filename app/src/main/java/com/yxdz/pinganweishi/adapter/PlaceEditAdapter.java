package com.yxdz.pinganweishi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.common.utils.SPUtils;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.FirePlaceBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;
import com.yxdz.pinganweishi.person.PlaceEditActivity;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceEditAdapter extends RecyclerView.Adapter<PlaceEditAdapter.ViewHolder> {


    private Context context;
    public ArrayList<Boolean> booleans = new ArrayList<Boolean>();
    public List<FirePlaceBean.ListPlaceBean> listPlace;
    private int type = 1;


    public PlaceEditAdapter(PlaceEditActivity context, List<FirePlaceBean.ListPlaceBean> listPlace) {
        this.context = context;
        this.listPlace = listPlace;
        Log.d("PlaceEditAdapter", "booleans.size():" + booleans.size());
        Log.d("PlaceEditAdapter", "listPlace.size():" + listPlace.size());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_edit_item, viewGroup, false);
        return new ViewHolder(view);
    }


    public void loadBooleans() {
        for (FirePlaceBean.ListPlaceBean bean : listPlace) {
            booleans.add(false);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        if (type != 3) {
            booleans.clear();
            for (FirePlaceBean.ListPlaceBean bean : listPlace) {
                booleans.add(false);
            }
        }
        if (type == 0 || type == 3) {
            viewHolder.select.setVisibility(View.VISIBLE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.select.isChecked()) {
                        viewHolder.select.setChecked(false);
                        booleans.set(i, false);

                    } else {
                        viewHolder.select.setChecked(true);
                        booleans.set(i, true);
                    }
                }
            });
        } else {
            viewHolder.select.setVisibility(View.GONE);
            if (mOnItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v, i);
                    }
                });
            }
        }

        Log.e("PlaceEditAdapter", "booleans.size():" + booleans.size());
        viewHolder.select.setChecked(booleans.get(i));
        if (booleans.get(i)) {
            viewHolder.select.setChecked(true);
        } else {
            viewHolder.select.setChecked(false);
        }

        viewHolder.tvTitle.setText(listPlace.get(i).getPlaceName());
        viewHolder.tvAddress.setText("场所地址：" + listPlace.get(i).getPlaceAddress());
        switch (listPlace.get(i).getPlaceType()) {
            case 1:
                viewHolder.place.setText("场所类别：公司企业");
                break;
            case 2:
                viewHolder.place.setText("场所类别：工厂企业");
                break;
            case 3:
                viewHolder.place.setText("场所类别：小作坊");
                break;
            case 4:
                viewHolder.place.setText("场所类别：小商铺");
                break;
            case 5:
                viewHolder.place.setText("场所类别：小娱乐场所");
                break;
            case 6:
                viewHolder.place.setText("场所类别：娱乐场所");
                break;
            case 7:
                viewHolder.place.setText("场所类别：出租屋");
                break;
            case 8:
                viewHolder.place.setText("场所类别：宾饭馆");
                break;
            case 9:
                viewHolder.place.setText("场所类别：学校");
                break;
            case 10:
                viewHolder.place.setText("场所类别：其他");
                break;
        }

        switch (listPlace.get(i).getCreateType()) {
            case 1:
                viewHolder.tvPlace.setText("系统");
                break;
            case 2:
                viewHolder.tvPlace.setText("自建");

                break;
            case 3:
                viewHolder.tvPlace.setText("他人");
                break;
        }
        if (listPlace.get(i).getCreateType() == 3) {
            viewHolder.select.setVisibility(View.GONE);
        }


//        switch (listPlace.get(i).getPlaceType()) {
//            case 1:
//                viewHolder.tvPlace.setText("公司企业");
//                break;
//            case 2:
//                viewHolder.tvPlace.setText("工厂企业");
//                break;
//            case 3:
//                viewHolder.tvPlace.setText("小作坊");
//                break;
//            case 4:
//                viewHolder.tvPlace.setText("小商铺");
//                break;
//            case 5:
//                viewHolder.tvPlace.setText("小娱乐场所");
//                break;
//            case 6:
//                viewHolder.tvPlace.setText("娱乐场所");
//                break;
//            case 7:
//                viewHolder.tvPlace.setText("出租屋");
//                break;
//            case 8:
//                viewHolder.tvPlace.setText("宾饭馆");
//                break;
//            case 9:
//                viewHolder.tvPlace.setText("学校");
//                break;
//            case 10:
//                viewHolder.tvPlace.setText("其他");
//                break;
//        }
    }

    @Override
    public int getItemCount() {
        return listPlace != null ? listPlace.size() : 0;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final RadioButton select;
        private final TextView tvTitle;
        private final TextView tvAddress;
        private final TextView tvPlace;
        private final TextView place;

        public ViewHolder(View view) {
            super(view);
            select = view.findViewById(R.id.select_btn);
            tvTitle = view.findViewById(R.id.placeName);
            tvAddress = view.findViewById(R.id.placeDetail);
            tvPlace = view.findViewById(R.id.placeType);
            place = view.findViewById(R.id.place);

        }
    }


    public void selectAll() {
        booleans.clear();
        for (FirePlaceBean.ListPlaceBean bean : listPlace) {
            if (bean.getCreateType() != 3) {
                booleans.add(true);
            } else {
                booleans.add(false);
            }
        }
    }


    public void unSelectAll() {
        booleans.clear();
        for (FirePlaceBean.ListPlaceBean bean : listPlace) {
            booleans.add(false);
        }
    }

    public void delete() {
        Log.d("PlaceEditAdapter", "listPlace.size():" + listPlace.size());
        Log.d("PlaceEditAdapter", "booleans.size():" + booleans.size());
        for (final boolean b : booleans) {
            if (b) {
                if (listPlace.get(booleans.indexOf(b)).getCreateType() != 3) {
                    Map<String, Object> map = new HashMap<>();
                    map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                    map.put("placeId", listPlace.get(booleans.indexOf(b)).getId());
                    RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).deletePlace(map), new RxSubscriber<DefaultBean>(context) {
                        @Override
                        protected void onSuccess(DefaultBean defaultBean) {

                        }
                    });
                    listPlace.remove(booleans.indexOf(b));
                    notifyDataSetChanged();
                }
            }
        }
        booleans.clear();
        loadBooleans();
    }


    public void statusChange(int type) {
        this.type = type;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


}

