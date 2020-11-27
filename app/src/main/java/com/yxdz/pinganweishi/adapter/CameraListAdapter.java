package com.yxdz.pinganweishi.adapter;


import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.CameraBean;
import com.yxdz.pinganweishi.bean.CameraListBean;
import com.yxdz.pinganweishi.interfac.OnItemClickListener;
import com.yxdz.pinganweishi.utils.GlideUtils;
import com.yxkj.yunshicamera.realplay.util.SdkInitTool;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CameraListAdapter extends RecyclerView.Adapter<CameraListAdapter.ViewHolder> {

    private ArrayList<ViewHolder> holders = new ArrayList<ViewHolder>();
    private final Context cameraListActivity;
    private final List<CameraListBean.DataBean.CameraBean> cameraBeans;


    public CameraListAdapter(Context cameraListActivity, List<CameraListBean.DataBean.CameraBean> cameraBeans) {
        this.cameraListActivity = cameraListActivity;
        this.cameraBeans = cameraBeans;
        Log.d("CameraListAdapter", "cameraBeans:" + cameraBeans);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.camera_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        holders.add(viewHolder);


        if (cameraBeans.get(i).getCameraImg() != null) {
            GlideUtils.displayDefault2(cameraListActivity, viewHolder.cameraImage, cameraBeans.get(i).getCameraImg());
            viewHolder.cameraStatus.setVisibility(View.GONE);
        }
        if (cameraBeans.get(i).getStatus() == 1) {
            viewHolder.cameraImage.setImageResource(R.mipmap.module_icon01);
            viewHolder.cameraStatus.setVisibility(View.GONE);
            viewHolder.play_button.setVisibility(View.VISIBLE);
        } else {
            viewHolder.cameraImage.setImageResource(R.mipmap.camera_bg_black);
            viewHolder.cameraStatus.setVisibility(View.VISIBLE);
            viewHolder.play_button.setVisibility(View.GONE);
        }
        viewHolder.cameraName.setText(cameraBeans.get(i).getCameraName());
        viewHolder.code.setText(cameraBeans.get(i).getValidateCode());
        viewHolder.SNcode.setText(cameraBeans.get(i).getDeviceSerial());


        viewHolder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cameraBeans.get(i).getStatus() == 1) {
                    onItemClickListener.onItemClick(v, i);
                } else {
                    Toast.makeText(cameraListActivity, "设备不在线", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return cameraBeans.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView play_button;
        private ImageView cameraImage;
        private ImageView cameraStatus;
        private TextView cameraName;
        private TextView code;
        private TextView SNcode;

        private CardView item_layout;

        public ViewHolder(View view) {
            super(view);
            cameraImage = view.findViewById(R.id.cameraImage);
            cameraStatus = view.findViewById(R.id.cameraStatus);
            cameraName = view.findViewById(R.id.cameraName);
            code = view.findViewById(R.id.code);
            SNcode = view.findViewById(R.id.SNcode);
            item_layout = view.findViewById(R.id.item_layout);
            play_button = view.findViewById(R.id.play_button);
        }

    }


    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }








}
