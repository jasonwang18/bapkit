package com.supcon.mes.mbap.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;

import com.supcon.mes.mbap.R;

/**
 * Environment: hongruijun
 * Created by Xushiyun on 2018/4/18.
 */

public class ViewUtil {
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            final int ol = ((ViewGroup.MarginLayoutParams) v.getLayoutParams()).leftMargin;
            final int or = ((ViewGroup.MarginLayoutParams) v.getLayoutParams()).rightMargin;
            final int ot = ((ViewGroup.MarginLayoutParams) v.getLayoutParams()).topMargin;
            final int ob = ((ViewGroup.MarginLayoutParams) v.getLayoutParams()).bottomMargin;
            p.setMargins(l == -1 ? ol : l, t == -1 ? ot : t, r == -1 ? or : r, b == -1 ? ob : b);
            v.requestLayout();
        }
    }

    public static int getMarginLeft(View v) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            final int ol = ((ViewGroup.MarginLayoutParams) v.getLayoutParams()).leftMargin;
            return ol;
        }
        return -1;
    }


    public static void setMarginLeft(View v, int l) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            final int ol = ((ViewGroup.MarginLayoutParams) v.getLayoutParams()).leftMargin;
            final int or = ((ViewGroup.MarginLayoutParams) v.getLayoutParams()).rightMargin;
            final int ot = ((ViewGroup.MarginLayoutParams) v.getLayoutParams()).topMargin;
            final int ob = ((ViewGroup.MarginLayoutParams) v.getLayoutParams()).bottomMargin;
            p.setMargins(l == -1 ? ol : l, ot, or, ob);
            v.requestLayout();
        }
    }

    public static void setPaddings(View v, int l, int t, int r, int b) {
        final int ol = v.getPaddingLeft();
        final int or = v.getPaddingRight();
        final int ot = v.getPaddingTop();
        final int ob = v.getPaddingBottom();
        v.setPadding(l == -1 ? ol : l, t == -1 ? ot : t, r == -1 ? or : r, b == -1 ? ob : b);
    }


    public static int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static void setPaddingRight(View v, int paddingInPx) {
        v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), paddingInPx, v.getPaddingBottom());
    }

    public static void setPaddingLeft(View v, int paddingInPx) {
        v.setPadding(paddingInPx, v.getPaddingTop(), v.getPaddingRight(), v.getPaddingBottom());
    }

    public static void setPaddingTop(View v, int paddingInPx) {
        v.setPadding(v.getPaddingLeft(), paddingInPx, v.getPaddingRight(), v.getPaddingBottom());
    }

    public static void addPaddingTop(View v, int paddingTopPlus) {
        v.setPadding(v.getPaddingLeft(), v.getPaddingTop() + paddingTopPlus, v.getPaddingRight(), v.getPaddingBottom());
    }

    public static void setBrightness(View view, float percent) {
        view.setAlpha(percent);
    }

    //交替式设置可见性
    public static void setVisibility(View view1, View view2, boolean checkExpression) {
        if(checkExpression) {
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
        }
        else {
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
        }
    }
    //交替式设置可见性
    public static void setVisibility(View[]views1, View[] views2, boolean checkExpression) {
        if(checkExpression){
            setVisibility(views1, true);
            setVisibility(views2, false);
        }
        else {
            setVisibility(views1, false);
            setVisibility(views2, true);
        }
    }

    public static void setVisibility(View[] views, boolean visibility)
    {
        for(View view:views){
            view.setVisibility(visibility?View.VISIBLE:View.GONE);
        }
    }

    public static void clickBinder(ViewGroup rootView, ViewGroup subView)
    {
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subView.callOnClick();
            }
        });
    }

    public static GradientDrawable genGradientDrawable(int... colors) {
        if(colors.length == 1) {
            colors = new int[]{colors[0], R.color.white};
        }
        final GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        return gradientDrawable;
    }
}
