package com.yxdz.pinganweishi.person;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.leaf.library.StatusBarUtil;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.utils.RegexUtils;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.bean.YxdzInfo;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.api.LoginApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseHeadActivity implements View.OnClickListener {

    private EditText etSuggestion;
    private EditText etPhone;
    private Button btnSubmit;
    private TitleBarView titleBarView;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feedback);
//        initView();
//        initData();
//    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_feedback;
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
//        initData();
    }

    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("意见反馈");
    }

    private void initView() {
        etSuggestion = findViewById(R.id.feedback_suggestion);
        etPhone = findViewById(R.id.feedback_phone);
        btnSubmit = findViewById(R.id.feedback_submit);
        btnSubmit.setOnClickListener(this);

        etSuggestion.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;//监听前的文本
            private int editStart;//光标开始位置
            private int editEnd;//光标结束位置

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制
                editStart = etSuggestion.getSelectionStart();
                editEnd = etSuggestion.getSelectionEnd();

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback_submit:
                if (TextUtils.isEmpty(etSuggestion.getText().toString().trim())) {
                    ToastUtils.showShort(FeedbackActivity.this, "请输入反馈意见");
                    return;
                }
                if (!RegexUtils.isMobilePhoneNumber(etPhone.getText().toString().trim())) {
                    ToastUtils.showShort(FeedbackActivity.this, "请输入正确的手机号码");
                    return;
                }

                submitData();

                break;
        }
    }

    private void submitData() {
        Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
//        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        map.put(ActValue.Title, etPhone.getText().toString().trim());
        map.put(ActValue.Content, etSuggestion.getText().toString().trim());

        RetrofitHelper.subscriber(RetrofitHelper.Https(LoginApi.class).getSuggesionFeedbackReq(map),
                new RxSubscriber<YxdzInfo>(FeedbackActivity.this, "正在提交...") {
                    @Override
                    protected void onSuccess(YxdzInfo yxdzInfo) {
                        ToastUtils.showShort(FeedbackActivity.this, "提交成功");
                        finish();
                    }

                    @Override
                    protected void onOtherError(String code) {
                        super.onOtherError(code);
                        AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(),code);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {



            // ContentProvider展示数据类似一个单个数据库表
// ContentResolver实例带的方法可实现找到指定的ContentProvider并获取到ContentProvider的数据
            ContentResolver reContentResolverol = getContentResolver();
// URI,每个ContentProvider定义一个唯一的公开的URI,用于指定到它的数据集
            Uri contactData = data.getData();
// 查询就是输入URI等参数,其中URI是必须的,其他是可选的,如果系统能找到URI对应的ContentProvider将返回一个Cursor对象.
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
// 获得DATA表中的名字
            String linkname = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//            etName.setText(linkname);
// 条件为联系人ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
// 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            String phonenumber = "";
            while (phone.moveToNext()) {
                phonenumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim().replace(" ", "");
            }
            etPhone.setText(linkname + "," + phonenumber);


        }
    }

    public void initData() {
        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(FeedbackActivity.this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FeedbackActivity.this,
                        new String[]{android.Manifest.permission.READ_CONTACTS}, CONTACT_OK);
            }
        }
    }

    private final int CONTACT_OK = 119;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CONTACT_OK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    ToastUtils.showShort(FeedbackActivity.this, "没有读取通讯录权限");
                }
                break;
            default:
                break;
        }

    }
}
