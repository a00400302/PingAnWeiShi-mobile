package com.yxdz.pinganweishi.interfac;



import java.util.List;

import com.yxdz.pinganweishi.bean.BindDeviceBean;
import com.yxdz.pinganweishi.bean.DoorLockBindBean;

public interface OnOKClickListener {

    public void onOkClick(List<BindDeviceBean.DataBean.DeviceBindListBean> bindDeviceBeans);
    public void onOkLockClick(List<DoorLockBindBean.DeviceBindListBean> bindDeviceBeans);
}
