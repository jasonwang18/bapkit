package com.supcon.mes.mbap.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置recyclerview 间距
 * Created by wangshizhan on 2017/8/18.
 * Email:wangshizhan@supcon.com
 */

public class NomalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public NomalSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.top = space;
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
    }

}
