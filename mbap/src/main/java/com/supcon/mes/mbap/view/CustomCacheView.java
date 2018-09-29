package com.supcon.mes.mbap.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.supcon.mes.mbap.R;

/**
 * Environment: hongruijun
 * Created by Xushiyun on 2018/3/5.
 */

public class CustomCacheView extends CustomArrowView {
    private TextView cacheSize;
    public CustomCacheView(Context context) {
        super(context);
    }

    public CustomCacheView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_cache;
    }

    @Override
    protected void initView() {
        super.initView();
        cacheSize = findViewById(R.id.cacheSize);
    }

    public void setCacheSize(String size) {
        this.cacheSize.setText(size);
    }
}
