package com.yxdz.pinganweishi.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.bean.ContactsBean;

import java.util.List;

public class EditContactsAdapter extends RecyclerView.Adapter<EditContactsAdapter.ViewHolder> {

    public final List<ContactsBean.DataBean> listContacts;
    private final Context context;
    private final int placeId;
    private ViewHolder viewHolder;
    private int type = 1;

    public EditContactsAdapter(Context context, List<ContactsBean.DataBean> listContacts, int id) {
        this.context = context;
        this.listContacts = listContacts;
        this.placeId = id;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contacts_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if (type == 0) {
            viewHolder.deleteBtn.setVisibility(View.VISIBLE);
            viewHolder.callBtn.setVisibility(View.GONE);
        } else {
            viewHolder.deleteBtn.setVisibility(View.GONE);
            viewHolder.callBtn.setVisibility(View.VISIBLE);
        }
        this.viewHolder = viewHolder;
        viewHolder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + listContacts.get(i).getPhone());
                intent.setData(data);
                context.startActivity(intent);
            }
        });


        viewHolder.call_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.isDelFlag.isChecked()) {
                    viewHolder.isDelFlag.setChecked(false);
                    listContacts.get(i).setDelFlag(0);
                } else {
                    viewHolder.isDelFlag.setChecked(true);
                    listContacts.get(i).setDelFlag(1);
                }
            }
        });


        viewHolder.contactsName.setText(listContacts.get(i).getNickname());
        viewHolder.contactsNumber.setText(listContacts.get(i).getPhone());
    }


    public void StatusChange(int type) {
        this.type = type;
    }

    @Override
    public int getItemCount() {
        return listContacts != null ? listContacts.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout callBtn;
        private final RadioButton isDelFlag;
        private final TextView contactsNumber;
        private final TextView contactsName;
        private final View deleteBtn;
        private final View call_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactsName = itemView.findViewById(R.id.contacts_name);
            contactsNumber = itemView.findViewById(R.id.contacts_number);
            isDelFlag = itemView.findViewById(R.id.isDelFlag);
            callBtn = itemView.findViewById(R.id.call_btn);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
            call_layout = itemView.findViewById(R.id.call_layout);
        }
    }
}
