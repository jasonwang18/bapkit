package com.supcon.mes.mbap.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.supcon.common.view.util.DisplayUtil;

/**
 * 设置recyclerview 间距
 * Created by wangshizhan on 2017/8/18.
 * Email:wangshizhan@supcon.com
 */

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int num;

    public GridSpaceItemDecoration(int space, int num) {
        this.space = space;
        this.num = num;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildAdapterPosition(view) != 0) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = DisplayUtil.dip2px(space, SystemUtil.getActivityFromView(view));
//            outRect.right = DisplayUtil.dip2px(space, SystemUtil.getActivityFromView(view));
//            outRect.bottom = DisplayUtil.dip2px(space, view.getContext());
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) %num==0) {
                outRect.left = 0;
            }

            if (parent.getChildLayoutPosition(view) >= num) {
                outRect.top = DisplayUtil.dip2px(space, SystemUtil.getActivityFromView(view));
//                outRect.bottom = DisplayUtil.dip2px(space, SystemUtil.getActivityFromView(view));
            }
        }
    }

}
