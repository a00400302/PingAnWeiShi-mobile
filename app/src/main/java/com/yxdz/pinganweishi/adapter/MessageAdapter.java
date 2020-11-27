package com.yxdz.pinganweishi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.SmokeMessageBean;
import java.util.List;


/**
 * 消息中心
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private List<SmokeMessageBean> messageBeanList;

    public MessageAdapter(Context context, List<SmokeMessageBean> messageBeanList) {
        this.context = context;
        this.messageBeanList = messageBeanList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.person_message_layout_item, viewGroup, false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //模拟数据

        viewHolder.message_state.setImageResource(R.mipmap.fire_alarm_small_red);
        viewHolder.message_title.setText(messageBeanList.get(position).getEventDescribe());

        viewHolder.message_place_location.setText(messageBeanList.get(position).getLocation());
        viewHolder.message_device_name.setText(messageBeanList.get(position).getSn());
        viewHolder.message_start_time.setText(messageBeanList.get(position).getCreateTime().replace("T", "  "));
        if (messageBeanList.get(position).getDealTime() != null) {
            viewHolder.message_deal_time.setText(messageBeanList.get(position).getDealTime().replace("T", "  "));
        } else {
            viewHolder.message_deal_time.setText("未处理");
        }

        if (messageBeanList.get(position).getResult() != null) {
            switch (messageBeanList.get(position).getResult()) {
                case "1":
                    viewHolder.message_deal_result.setText("火警误报");
                    break;
                case "2":
                    viewHolder.message_deal_result.setText("火警测试");
                    break;
                case "3":
                    viewHolder.message_deal_result.setText("真实火警");
                    break;
            }
        }else{
            viewHolder.message_deal_result.setText("未处理");
        }


        viewHolder.message_place_name.setText(messageBeanList.get(position).getPlaceName());


    }


    @Override
    public int getItemCount() {
        return messageBeanList != null ? messageBeanList.size() : 0;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素18824743766
    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final ImageView message_state;
        private final TextView message_title;
        private final TextView message_device_name;
        private final TextView message_place_name;
        private final TextView message_place_location;
        private final TextView message_start_time;
        private final TextView message_deal_time;
        private final TextView message_deal_result;

        public ViewHolder(View view) {
            super(view);
            message_state = view.findViewById(R.id.message_state);
            message_title = view.findViewById(R.id.message_title);
            message_device_name = view.findViewById(R.id.message_device_name);
            message_place_name = view.findViewById(R.id.message_place_name);
            message_place_location = view.findViewById(R.id.message_place_location);
            message_start_time = view.findViewById(R.id.message_start_time);
            message_deal_time = view.findViewById(R.id.message_deal_time);
            message_deal_result = view.findViewById(R.id.message_deal_result);

        }
    }

}
