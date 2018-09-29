package com.supcon.mes.mbap.view;

import android.content.Context;
import android.util.AttributeSet;

import com.supcon.common.view.base.view.BaseRelativeLayout;

/**
 * Created by wangshizhan on 2018/2/7.
 * Email:wangshizhan@supcon.com
 */

public class CustomSearchTitleBar extends BaseRelativeLayout {

    public CustomSearchTitleBar(Context context) {
        super(context);
    }

    public CustomSearchTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return 0;
    }
}
