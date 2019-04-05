package com.supcon.mes.mbap.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by wangshizhan on 2017/9/12.
 * Email:wangshizhan@supcon.com
 */

public class CustomScrollView extends ScrollView {

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private OnScrollListener onScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public interface OnScrollListener{

        void onScrollChanged(int x, int y, int oldx, int oldy);

    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);

        if(onScrollListener!=null){
            onScrollListener.onScrollChanged(x, y, oldx, oldy);
        }
    }
}
