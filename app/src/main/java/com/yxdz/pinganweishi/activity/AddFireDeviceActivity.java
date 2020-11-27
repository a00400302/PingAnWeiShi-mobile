package com.yxdz.pinganweishi.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.leaf.library.StatusBarUtil;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.ConstantUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.yxdz.pinganweishi.activity.FireDeviceActivity.hideSoftKeyboard;

public class AddFireDeviceActivity extends BaseHeadActivity {

    private TitleBarView titleBarView;
    private EditText toolbar;
    private EditText deviceSn;
    private EditText deviceName;
    private EditText install_location;
    private EditText install_start_time;
    private EditText install_end_time;
    private EditText install_start_time2;
    private EditText install_end_time2;
    private Button setButton;
    private EditText check_time;
    private EditText human_check_time;
    private EditText resend_limit;
    private EditText check_temperture;
    private EditText check_temperture_time;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_add_fire_device;
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
        initData();
    }

    private void initData() {
        deviceSn = findViewById(R.id.deviceSn);
        deviceName = findViewById(R.id.deviceName);
        install_location = findViewById(R.id.install_location);
        install_start_time = findViewById(R.id.install_start_time);
        install_start_time.setText("00:00");
        install_start_time.setInputType(InputType.TYPE_NULL);
        install_start_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(install_start_time, 0);
                }
            }
        });
        install_end_time = findViewById(R.id.install_end_time);
        install_end_time.setText("23:59");
        install_end_time.setInputType(InputType.TYPE_NULL);
        install_end_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(install_end_time, 1);
                }
            }
        });
        install_start_time2 = findViewById(R.id.install_start_time2);
        install_start_time2.setInputType(InputType.TYPE_NULL);
        install_start_time2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(install_start_time2, 0);
                }
            }
        });
        install_end_time2 = findViewById(R.id.install_end_time2);
        install_end_time2.setInputType(InputType.TYPE_NULL);
        install_end_time2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(install_end_time2, 1);
                }
            }
        });
        setButton = findViewById(R.id.setButton);
        final Intent intent = getIntent();
        if (intent.getIntExtra("type", 1) == 0) {
            deviceSn.setText(intent.getStringExtra("deviceSn"));
        }

        check_time = findViewById(R.id.check_time);
        human_check_time = findViewById(R.id.human_check_time);
        resend_limit = findViewById(R.id.resend_limit);
        check_temperture = findViewById(R.id.check_temperture);
        check_temperture_time = findViewById(R.id.check_temperture_time);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(deviceName.getText().toString())) {
                    Toast.makeText(AddFireDeviceActivity.this, "设备名称不能为空", Toast.LENGTH_SHORT).show();
                    hideSoftKeyboard(AddFireDeviceActivity.this);
                    return;
                }

                if (!String.valueOf(check_temperture_time.getText()).isEmpty()) {
                    Integer check_temperture_time_value = new Integer(String.valueOf(check_temperture_time.getText()));
                    if (check_temperture_time_value > 5 || check_temperture_time_value < 0) {
                        Toast.makeText(AddFireDeviceActivity.this, "温度检测时间超出范围", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                if (!String.valueOf(check_time.getText()).isEmpty()) {
                    Integer check_time_value = new Integer(String.valueOf(check_time.getText()));
                    if (check_time_value > 50 || check_time_value < 0) {
                        Toast.makeText(AddFireDeviceActivity.this, "传感器检测时间超出范围", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (!String.valueOf(human_check_time.getText()).isEmpty()) {
                    Integer human_check_time_value = new Integer(String.valueOf(human_check_time.getText()));
                    if (human_check_time_value > 255 || human_check_time_value < 0) {
                        Toast.makeText(AddFireDeviceActivity.this, "人体检测时间超出范围", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (!String.valueOf(resend_limit.getText()).isEmpty()) {
                    Integer resend_limit_value = new Integer(String.valueOf(resend_limit.getText()));
                    if (resend_limit_value > 10 || resend_limit_value < 0) {
                        Toast.makeText(AddFireDeviceActivity.this, "重发间隔超出范围", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (!String.valueOf(resend_limit.getText()).isEmpty()) {
                    Integer check_temperture_value = new Integer(String.valueOf(check_temperture.getText()));
                    if (check_temperture_value < 30) {
                        Toast.makeText(AddFireDeviceActivity.this, "警报温度超出范围", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (deviceName.length() != 0 || install_location.length() != 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("placeId", intent.getIntExtra("placeId", 0));
                    map.put("deviceSn", deviceSn.getText().toString());
                    map.put("installationLocation", install_location.getText().toString());
                    map.put("deviceName", deviceName.getText().toString());
                    map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
                    map.put("startTime", install_start_time.getText().toString());
                    map.put("endTime", install_end_time.getText().toString());
                    map.put("startTime2", install_start_time2.getText().toString());
                    map.put("endTime2", install_end_time2.getText().toString());
                    map.put("ygSensitivity", check_time.getText());
                    map.put("checkTime", human_check_time.getText());
                    map.put("repeatTime", resend_limit.getText());
                    map.put("temperatureWarn", check_temperture.getText());
                    map.put("rtSensitivity", check_temperture_time.getText());

                    if (install_start_time.getText().toString().equals("")) {
                        Toast.makeText(AddFireDeviceActivity.this, "有效开始推送时间能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (install_end_time.getText().toString().equals("")) {
                        Toast.makeText(AddFireDeviceActivity.this, "有效结束推送时间能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).addFireDevice(map), new RxSubscriber<DefaultBean>(AddFireDeviceActivity.this) {
                        @Override
                        protected void onSuccess(DefaultBean contactsBean) {
                            if (contactsBean.getCode() != 0) {
                                Toast.makeText(AddFireDeviceActivity.this, contactsBean.getData(), Toast.LENGTH_SHORT).show();
                            } else {
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(AddFireDeviceActivity.this, "字段不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private void initView() {

    }

    @Override
    protected void setTitlebarView() {

    }


    private void showDatePickerDialog(final EditText editText, final int type) {
        KeyboardUtils.hideSoftInput(getWindow());
        TimePickerView pvTime = new TimePickerBuilder(AddFireDeviceActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String hours;
                String minutes;
                if (date.getMinutes() < 10) {
                    minutes = "0" + date.getMinutes();
                } else {
                    minutes = "" + date.getMinutes();
                }

                if (date.getHours() < 10) {
                    hours = "0" + date.getHours();
                } else {
                    hours = "" + date.getHours();
                }


                editText.setText(hours + ":" + minutes);
            }
        }).setType(new boolean[]{false, false, false, true, true, false}).build();
        if (type == 1) {
            pvTime.setTitleText("结束时间");
        } else {
            pvTime.setTitleText("开始时间");
        }
        pvTime.show();
    }
}
