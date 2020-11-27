package com.yxdz.pinganweishi.person;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leaf.library.StatusBarUtil;
import com.yxdz.common.load.RetryNetWorkImpl;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.view.CustomRecyclerView;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.adapter.EditContactsAdapter;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.ContactsBean;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.FirePlaceBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditContactsActivity extends BaseHeadActivity implements XRecyclerView.LoadingListener, RetryNetWorkImpl {

    private CustomRecyclerView contactsList;
    private EditContactsAdapter contactsAdapter;


    private List<ContactsBean.DataBean> listPlace;
    private FirePlaceBean.ListPlaceBean bean;
    private LinearLayout noDataLayout;
    private TitleBarView titleBarView;
    private EasyPopup mCirclePop;
    private View delete_layout;
    private View cancel_delete;
    private View delete_btn;

    private boolean isEdit = false;
    private String usersString = "";


    @Override
    public int getLayoutResource() {
        return R.layout.activity_edit_contacts;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this,R.color.primarystart), ContextCompat.getColor(this,R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);
        initView();
        initData(this);
    }

    private void initView() {
        noDataLayout = findViewById(R.id.fire_no_data);
        bean = (FirePlaceBean.ListPlaceBean) getIntent().getSerializableExtra("listPlaceBean");
        contactsList = findViewById(R.id.contacts_list);
        delete_layout = findViewById(R.id.delete_layout);
        cancel_delete = findViewById(R.id.cancel_delete);
        delete_btn = findViewById(R.id.delete_btn);
        contactsList.setLoadingListener(this);
        contactsList.setLoadingMoreEnabled(false);
    }

    private void initData(Context context) {
        listPlace = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("placeId", bean.getId());
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).queryContact(map), new RxSubscriber<ContactsBean>(context) {
            @Override
            protected void onSuccess(ContactsBean contactsBean) {
                Log.d("EditContactsActivity", "contactsBean.getData():" + contactsBean.getData());
                if (contactsBean.getData() != null && contactsBean.getData().size() != 0) {
                    noDataLayout.setVisibility(View.GONE);
                    listPlace = contactsBean.getData();
//                    if (contactsAdapter == null) {
                    contactsAdapter = new EditContactsAdapter(EditContactsActivity.this, contactsBean.getData(), bean.getId());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EditContactsActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    contactsList.setLayoutManager(linearLayoutManager);
                    contactsList.setAdapter(contactsAdapter);
//                    contactsAdapter.notifyDataSetChanged();
//                    } else {
//                    contactsAdapter.notifyDataSetChanged();
//                    }
//                    contactsList.refreshComplete();
                } else {
                    noDataLayout.setVisibility(View.VISIBLE);
                    contactsList.setVisibility(View.GONE);
                    titleBarView.getTvRight().setVisibility(View.GONE);
                }
            }
        });

        cancel_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_layout.setVisibility(View.GONE);
                contactsAdapter.StatusChange(1);
                contactsAdapter.notifyDataSetChanged();
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPlace != null) {
                    for (int i = 0; i < listPlace.size(); i++) {
                        if (listPlace.get(i).getDelFlag() == 1) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("account", contactsAdapter.listContacts.get(i).getUsername());
                            map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                            map.put("type", 1);
                            map.put("placeId", bean.getId());
                            final int finalI = i;
                            RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).operatingContact(map), new RxSubscriber<DefaultBean>(EditContactsActivity.this) {
                                @Override
                                protected void onSuccess(DefaultBean defaultBean) {
                                    contactsAdapter.listContacts.remove(finalI);
                                    contactsAdapter.notifyDataSetChanged();
                                    if (contactsAdapter.listContacts.size() == 0) {
                                        noDataLayout.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    private void initContactsList(List<ContactsBean.DataBean> listPlace) {
        for (ContactsBean.DataBean datum : listPlace) {
            usersString += datum.getNickname() + "-" + datum.getPhone() + ",";
        }
    }

    @Override
    protected void setTitlebarView() {
        if (bean.getCreateType() == 3) {
            titleBarView.getTvRight().setVisibility(View.GONE);
        } else {
            mCirclePop = EasyPopup.create()
                    .setContentView(EditContactsActivity.this, R.layout.custom_plaec_dialog)
//                .setAnimationStyle(R.style.RightPopAnim)
                    //是否允许点击PopupWindow之外的地方消失
                    //允许背景变暗
                    .setBackgroundDimEnable(true)
                    //变暗的透明度(0-1)，0为完全透明
                    .setDimValue(0.4f)
                    .setFocusAndOutsideEnable(true)
                    .apply();


            mCirclePop.findViewById(R.id.deviceslayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCirclePop.dismiss();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditContactsActivity.this);
                    alertDialogBuilder.setView(R.layout.add_contacts_custom_dialog);
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    alertDialog.setCancelable(false);

                    final TextView contacts = alertDialog.getWindow().findViewById(R.id.lockName);


                    final TextView phone = alertDialog.getWindow().findViewById(R.id.lockDeviceSn);

                    alertDialog.getWindow().findViewById(R.id.cancel_add).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.getWindow().findViewById(R.id.add_text).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (contacts.getText().toString().equals("")) {
                                Toast.makeText(EditContactsActivity.this, "字段不能为空", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (phone.getText().toString().equals("")) {
                                Toast.makeText(EditContactsActivity.this, "字段不能为空", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            final Map<String, Object> map = new HashMap<>();
                            initContactsList(listPlace);
                            usersString += contacts.getText().toString() + "-" + phone.getText().toString() + ",";
                            map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                            map.put("placeId", bean.getId());
                            map.put("arr", usersString);
                            Log.d("EditContactsActivity", usersString);
                            RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).updatePlaceUser(map), new RxSubscriber<DefaultBean>(EditContactsActivity.this) {
                                @Override
                                protected void onSuccess(DefaultBean cameraListBean) {
                                    Toast.makeText(EditContactsActivity.this, cameraListBean.getData(), Toast.LENGTH_SHORT).show();
                                    initData(EditContactsActivity.this);
                                    usersString = "";
                                }

                                @Override
                                public void onError(Throwable e) {
                                    super.onError(e);
                                    usersString = "";
                                }
                            });
                            alertDialog.dismiss();
                        }
                    });
                }
            });
            mCirclePop.findViewById(R.id.placelayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete_layout.setVisibility(View.VISIBLE);
                    contactsAdapter.StatusChange(0);
                    contactsAdapter.notifyDataSetChanged();
                    mCirclePop.dismiss();
                }
            });
            titleBarView.setTitleBarText("联系人");
            titleBarView.getRightImageView().setBackground(getDrawable(R.mipmap.add_wh));
            titleBarView.setRightImageViewOnClickListener(new TitleBarView.RightImageViewOnClickListener() {
                @Override
                public void onRightOnClick(View v) {
                    mCirclePop.showAtAnchorView(titleBarView.getRightImageView(), YGravity.BELOW, XGravity.ALIGN_RIGHT, 0, 10);

                }
            });


        }
    }

    @Override
    public void onRefresh() {
        initData(null);
    }

    @Override
    public void onLoadMore() {
        contactsList.setNoMore(true);
    }


    @Override
    public void retryNetWork() {
        initData(this);
    }
}


