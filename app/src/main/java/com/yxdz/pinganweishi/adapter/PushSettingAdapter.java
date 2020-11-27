package com.yxdz.pinganweishi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.common.utils.SPUtils;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.FirePlaceBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.ConstantUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushSettingAdapter extends RecyclerView.Adapter<PushSettingAdapter.ViewHolder> {

    private final Context context;
    public List<FirePlaceBean.ListPlaceBean> listPlace;

    public PushSettingAdapter(Context context, List<FirePlaceBean.ListPlaceBean> listPlace) {
        this.context = context;
        this.listPlace = listPlace;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.push_setting_recyclerview_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PushSettingAdapter.ViewHolder viewHolder, final int i) {
        if(listPlace.get(i).getSmsSwitch() ==1){
            viewHolder.isMessage.setChecked(true);
        }else{
            viewHolder.isMessage.setChecked(false);
        }
        if(listPlace.get(i).getCallSwitch() ==1){
            viewHolder.isCall.setChecked(true);
        }else{
            viewHolder.isCall.setChecked(false);
        }



        viewHolder.placeName.setText(listPlace.get(i).getPlaceName());
        viewHolder.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, Object> map = new HashMap<>();
                map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                map.put("id", listPlace.get(i).getId());
                if (viewHolder.isCall.isChecked())
                    map.put("callSwitch", 1);
                else
                    map.put("callSwitch", 0);
                if (viewHolder.isMessage.isChecked())
                    map.put("smsSwitch", 1);
                else
                    map.put("smsSwitch", 0);


                RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).setPushSetting(map), new RxSubscriber<DefaultBean>() {
                    @Override
                    protected void onSuccess(DefaultBean firePlaceBean) {
                        Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPlace != null ? listPlace.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView placeName;
        Button submitButton;
        SwitchCompat isCall;
        SwitchCompat isMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeName);
            submitButton = itemView.findViewById(R.id.submitButton);
            isCall = itemView.findViewById(R.id.isCall);
            isMessage = itemView.findViewById(R.id.isMessage);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
