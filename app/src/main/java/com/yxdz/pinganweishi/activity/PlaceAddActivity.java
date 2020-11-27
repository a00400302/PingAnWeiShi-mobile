package com.yxdz.pinganweishi.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.leaf.library.StatusBarUtil;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISCameraConfig;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.api.FireApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.FireAreaBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.zyyoona7.wheel.WheelView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PlaceAddActivity extends BaseHeadActivity implements View.OnClickListener {


    private EditText place_name;
    private EditText area;
    private TextView select_area;
    private EditText map;
    private ImageView select_map;
    private EditText tyep;
    private TextView select_type;
    private Button add_button;
    private int placeCode = 1;
    private List<FireAreaBean.DataBean.ListAreaBean> listArea;
    private View add_Contact;
    private LinearLayout contacts;
    private TitleBarView toolbar;
    private ArrayList<Object> strings;
    private List<String> stringList = Arrays.asList("公司企业",
            "工厂企业",
            "小作坊",
            "小商铺",
            "小娱乐场所",
            "娱乐场所",
            "出租屋",
            "宾饭馆",
            "学校",
            "其他");
    private double mapLat;
    private double mapLnt;
    private double lastClickTime;
    private List<HashMap<String, EditText>> contactslist = new ArrayList<HashMap<String, EditText>>();
    private int contactsCount = 0;
    private ImageView select_picture;
    private int REQUEST_CAMERA_CODE = 321;
    private String base64Image = "";

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_place_add;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        toolbar = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this, R.color.primarystart), ContextCompat.getColor(this, R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        toolbar.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, toolbar);
        place_name = findViewById(R.id.place_name);
        place_name.setOnClickListener(this);
        area = findViewById(R.id.area);
        area.setOnClickListener(this);
        select_area = findViewById(R.id.select_area);
        select_area.setOnClickListener(this);
        map = findViewById(R.id.map);
        map.setOnClickListener(this);
        select_map = findViewById(R.id.select_map);
        select_map.setOnClickListener(this);
        tyep = findViewById(R.id.tyep);
        tyep.setOnClickListener(this);
        select_type = findViewById(R.id.select_type);
        select_type.setOnClickListener(this);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(this);
        setStrings();
        add_Contact = findViewById(R.id.add_Contact);
        add_Contact.setOnClickListener(this);
        contacts = findViewById(R.id.contacts);

        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        select_picture = findViewById(R.id.select_picture);
        select_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ISCameraConfig config = new ISCameraConfig.Builder()
                        .needCrop(true) // 裁剪
                        .cropSize(1, 1, 200, 200)
                        .build();

                ISNav.getInstance().toCameraActivity(PlaceAddActivity.this, config, REQUEST_CAMERA_CODE);
            }
        });


    }

    @Override
    protected void setTitlebarView() {

    }

    public void setStrings() {
        final Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getFireControlSmokeAreaList(map), new RxSubscriber<FireAreaBean>(PlaceAddActivity.this) {
            @Override
            protected void onSuccess(FireAreaBean fireAreaBean) {
                if (fireAreaBean.getCode() == 0) {
                    listArea = fireAreaBean.getData().getListArea();
                    strings = new ArrayList<>();
                    for (FireAreaBean.DataBean.ListAreaBean listAreaBean : listArea) {
                        strings.add(listAreaBean.getName());
                    }
                } else {
                    ToastUtils.showShort(PlaceAddActivity.this, "区域列表获取失败");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_area:
                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(PlaceAddActivity.this);
                final AlertDialog alertDialog1 = alertDialogBuilder1.create();
                alertDialog1.show();
                alertDialog1.getWindow().setContentView(R.layout.place_type_dialog);
                final WheelView<Object> wheelView1 = alertDialog1.getWindow().findViewById(R.id.wheelview);
                TextView check1 = alertDialog1.getWindow().findViewById(R.id.check);
                wheelView1.setData(strings);
                check1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        area.setText(wheelView1.getSelectedItemData().toString());
                        area.setTextColor(Color.BLACK);
                        alertDialog1.dismiss();
                    }
                });
                break;
            case R.id.select_map:
                LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
                boolean isGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetwork = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (!isGPS && !isNetwork) {
                    Toast.makeText(this, "请开启定位服务！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    startActivityForResult(new Intent(this, MapActivity.class), 123);
                }
                break;
            case R.id.select_type:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlaceAddActivity.this);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
//                alertDialog.getWindow().setLayout(900, 710);
                alertDialog.getWindow().setContentView(R.layout.place_type_dialog);
                final WheelView<String> wheelView = alertDialog.getWindow().findViewById(R.id.wheelview);
                TextView check = alertDialog.getWindow().findViewById(R.id.check);
                wheelView.setData(stringList);
                wheelView.setSelectedItemPosition(placeCode - 1);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tyep.setText(wheelView.getSelectedItemData());
                        alertDialog.dismiss();
                    }
                });
                break;
            case R.id.add_button:
                checkVoid();
                break;
            case R.id.add_Contact:
                addContacts();
                break;
        }
    }

    private void addContacts() {
        if (contactsCount <= 9) {
            contactsCount += 1;
            final LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            EditText name = new EditText(this);
            name.setSingleLine(true);
            name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            EditText phone = new EditText(this);
            phone.setSingleLine(true);
            phone.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            phone.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            name.setHint("姓名");
            phone.setHint("请输入手机号码");

            TextView delete = new TextView(this);
            delete.setText("删除");
            linearLayout.addView(name);
            linearLayout.addView(phone);
            linearLayout.addView(delete);
            contacts.addView(linearLayout);
            HashMap<String, EditText> hashMap = new HashMap<>();
            hashMap.put("name", name);
            hashMap.put("phone", phone);
            contactslist.add(hashMap);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contacts.removeView(linearLayout);
                    contactslist.remove(contactsCount - 1);
                    contactsCount -= 1;
                }
            });
        }
    }

    public void checkVoid() {
        if (TextUtils.isEmpty(place_name.getText().toString())) {
            ToastUtils.showShort(this, "请输入场所名称");
            return;
        }

        if (TextUtils.isEmpty(area.getText().toString())) {
            ToastUtils.showShort(this, "请选择场所区域");
            return;
        }
        if (TextUtils.isEmpty(map.getText().toString())) {
            ToastUtils.showShort(this, "请选择详细地址");
            return;
        }
        if (TextUtils.isEmpty(tyep.getText().toString())) {
            ToastUtils.showShort(this, "请选择场所类型");
            return;
        }
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= 2000) {

            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime;

            Log.d("PlaceAddActivity", "testApi");
            final Map<String, Object> map = new HashMap<>();
            map.put("placeName", place_name.getText().toString());
            if (mapLat != 0 && mapLnt != 0) {
                map.put("lat", new BigDecimal(mapLat).setScale(4, BigDecimal.ROUND_HALF_UP));
                map.put("lng", new BigDecimal(mapLnt).setScale(4, BigDecimal.ROUND_HALF_UP));
            } else {
                ToastUtils.showShort(this, "地图位置错误，请重新选择");
                return;
            }
            map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
            map.put("placeAddress", this.map.getText());

            map.put("administrativeRegions", area.getText());
//                        map.put("mapAddress", sematicDescription);
            map.put("mapAddress", this.map.getText());
            int i = stringList.indexOf(tyep.getText().toString());
            if (i == -1) {
                map.put("placeType", placeCode);
            } else {
                map.put("placeType", i + 1);
            }

//                map.put("organCode", yycode.getText().toString());
            i = strings.indexOf(area.getText().toString());
            if (i == -1) {
                map.put("areaId", "");
            } else {
                map.put("areaId", listArea.get(i).getAreaId());
            }

            String attrString = "";
            for (HashMap hashMap : contactslist) {
                EditText name = (EditText) hashMap.get("name");
                EditText phone = (EditText) hashMap.get("phone");
                String nameString = name.getText().toString();
                String phoneString = phone.getText().toString();
                if (nameString.isEmpty() || phoneString.isEmpty()) {
                    return;
                } else {
                    attrString += nameString + "-" + phoneString + ",";
                }
            }

            map.put("arr", attrString);
            map.put("placeimgurl", base64Image);
            if (!area.getText().toString().equals("")) {
                RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).uploadPlace(map), new RxSubscriber<DefaultBean>(PlaceAddActivity.this) {
                    @Override
                    protected void onSuccess(DefaultBean yxdzInfo) {
                        if (yxdzInfo.getCode() == 0) {
                            Log.e("PlaceAddActivitytest", "map:" + map);
                            ToastUtils.showShort(PlaceAddActivity.this, "添加成功");
                            finish();
                        } else {
                            ToastUtils.showShort(PlaceAddActivity.this, "添加失败");
                        }
                    }
                });
            } else {
                ToastUtils.showShort(PlaceAddActivity.this, "请滑动地图重新定位");
            }
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == 123) {
            map.setText(intent.getStringExtra("mapName"));
            mapLat = intent.getDoubleExtra("mapLat", 0);
            mapLnt = intent.getDoubleExtra("mapLnt", 0);
        }
        if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK && intent != null) {
            String path = intent.getStringExtra("result"); // 图片地址
            select_picture.setImageBitmap(BitmapFactory.decodeFile(path));


            MultipartBody.Builder builder = new MultipartBody.Builder();
            //图片压缩
            File file = null;
            try {
                file = new Compressor(this).compressToFile(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            final RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("file ", file.getName(), imageBody);//file 后台接收图片流的参数名
            List<MultipartBody.Part> parts = builder.build().parts();
            RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).photo(parts), new RxSubscriber<DefaultBean>(PlaceAddActivity.this) {
                @Override
                protected void onSuccess(DefaultBean yxdzInfo) {
                    if (yxdzInfo.getCode() == 0) {
                        Log.e("PlaceAddActivitytest", "map:" + map);
                        ToastUtils.showShort(PlaceAddActivity.this, "添加成功");
                        base64Image = yxdzInfo.getData();
                    } else {
                        ToastUtils.showShort(PlaceAddActivity.this, "添加失败");
                    }
                }
            });
        }
    }
}




