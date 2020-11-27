package com.yxdz.pinganweishi.view;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bm.library.PhotoView;
import com.google.android.material.tabs.TabLayout;
import com.leaf.library.StatusBarUtil;
import com.yxdz.common.utils.YuXinUrl;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.utils.GlideUtils;

import java.util.ArrayList;

/**
 * 图片浏览
 */
public class PictureBrowseAty extends BaseHeadActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    //    private ViewGroup viewGroup;
    private ImageView tips[];
    //    private String[] picture;
    private ArrayList<String> picture;

    private int position;
    private TitleBarView toolbar;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_picture_browse_aty);
//    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_picture_browse_aty;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        initView();
        initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this,R.color.primarystart), ContextCompat.getColor(this,R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR,colors);
        toolbar.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, toolbar);
        viewPager = findViewById(R.id.picture_browse_viewpager);
        tabLayout = findViewById(R.id.picture_browse_tablayout);
        toolbar.setTitleBarText("图片");
//        viewGroup = findViewById(R.id.picture_browse_viewgroup);

    }

    private void initData() {

        Bundle bundle = this.getIntent().getExtras();
        //传过来的图片数据
//        picture = bundle.getStringArray("picture");
        picture = bundle.getStringArrayList("pictureList");
        //传过来的图片位置
        position = bundle.getInt("position");

        tips = new ImageView[picture.size()];
        if (picture.size() != 1) {
            tipsViewGroup();
        }

        ViewAdapter adapter = new ViewAdapter();
        viewPager.setAdapter(adapter);

        for (int i = 0; i < picture.size(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(R.layout.picture_browse_tab);
            tabLayout.addTab(tab);
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//适合很多tab

        setTabBack(position);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int arg0) {
                setImageBackground(arg0);
                tabLayout.getTabAt(arg0).select();
                setTabBack(arg0);

            }
        });
        viewPager.setCurrentItem(position);
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItemsIndex
     */
    private void setImageBackground(int selectItemsIndex) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItemsIndex) {
                tips[i].setBackgroundResource(R.mipmap.radio_choseed);
            } else {
                tips[i].setBackgroundResource(R.mipmap.radio_unchose);
            }
        }
    }

    private void setTabBack(int position) {

        for (int a = 0; a < picture.size(); a++) {

            ImageView imageView = tabLayout.getTabAt(a).getCustomView().findViewById(R.id.brief_figure_tab_image);

            if (a == position) {

                imageView.setImageResource(R.mipmap.radio_choseed);
            } else {
                imageView.setImageResource(R.mipmap.radio_unchose);
            }
        }

    }

    /**
     * 将点点加入到ViewGroup中
     */
    private void tipsViewGroup() {

        for (int i = 0; i < tips.length; i++) {
            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置每个小圆点距离左边的间距
            margin.setMargins(30, 0, 0, 0);
            ImageView imageView = new ImageView(getBaseContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(8, 8));

            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.radio_choseed);
            } else {
                tips[i].setBackgroundResource(R.mipmap.radio_unchose);
            }
//            viewGroup.addView(imageView, margin);
        }
    }

    class ViewAdapter extends PagerAdapter {

        @Override

        public int getCount() {
            // TODO Auto-generated method stub

            return picture.size();
        }

        @Override

        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);

        }

        //设置ViewPager指定位置要显示的view
        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    500);

            PhotoView imageView = new PhotoView(PictureBrowseAty.this);
            imageView.enable();

//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageView.setLayoutParams(margin);

            //加载网络数据图片
            GlideUtils.displayDefault(PictureBrowseAty.this, imageView, picture.get(position));

            container.addView(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
            return imageView;
        }

    }


    @Override
    protected void setTitlebarView() {
//        titleBarView.setVisibility(View.GONE);
    }
}
