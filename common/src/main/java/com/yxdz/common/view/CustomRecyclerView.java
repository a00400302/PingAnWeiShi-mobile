package com.yxdz.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.CustomFooterViewCallBack;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yxdz.common.R;

public class CustomRecyclerView extends XRecyclerView {


    public CustomRecyclerView(Context context) {
        super(context);
        initView();
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        final View footer = View.inflate(getContext(), R.layout.common_recyclerview_footer, null);
        footer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        setFootView(footer, new CustomFooterViewCallBack() {
            @Override
            public void onLoadingMore(View yourFooterView) {
                ((TextView) footer.findViewById(R.id.common_recyclerview_footer_text)).setText("正在加载...");
                footer.findViewById(R.id.common_recyclerview_footer_progressbar).setVisibility(View.VISIBLE);
                footer.setVisibility(VISIBLE);
            }

            @Override
            public void onLoadMoreComplete(View yourFooterView) {
                ((TextView) footer.findViewById(R.id.common_recyclerview_footer_text)).setText("加载完成");
                footer.setVisibility(GONE);
            }

            @Override
            public void onSetNoMore(View yourFooterView, boolean noMore) {
                ((TextView) footer.findViewById(R.id.common_recyclerview_footer_text)).setText("没有了~");
                footer.findViewById(R.id.common_recyclerview_footer_progressbar).setVisibility(View.GONE);

                if (noMore) {
                    footer.setVisibility(VISIBLE);
                } else {
                    footer.setVisibility(GONE);
                }
            }
        });

        footer.setVisibility(GONE);
    }

    @Override
    public void setLoadingMoreEnabled(boolean enabled) {
        super.setLoadingMoreEnabled(enabled);
    }
}