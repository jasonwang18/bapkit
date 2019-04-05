package com.supcon.mes.mbap.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.supcon.mes.mbap.R;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class CustomPopupWindow extends PopupWindow {
    private Context context;
    private View view;
    private List<String> contentList;
    private LinearLayout contentView;
    private LayoutInflater layoutInflater;
    private int offsetX;
    private int offsetY;
    private View anchorView;
    private OnToggleListener onToggleListener;


    public CustomPopupWindow(Context context) {
        this(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @SuppressLint("ClickableViewAccessibility")
    public CustomPopupWindow(Context context, int width, int height) {
        super(context);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        setWidth(width);
        setHeight(height);
        setFocusable(true);
        setOutsideTouchable(true);
        setTouchable(true);
        view = LayoutInflater.from(context).inflate(R.layout.ly_custom_popup_window, null);
        setContentView(view);
        contentView = view.findViewById(R.id.contentView);
        setBackgroundDrawable(new BitmapDrawable());
        contentList = new ArrayList<>();

    }
    public CustomPopupWindow onToggleListener(OnToggleListener onToggleListener) {
        this.onToggleListener = onToggleListener;
        return this;
    }

    public void setOnToggleListener(OnToggleListener onToggleListener) {
        this.onToggleListener = onToggleListener;
    }

    @Override
    public void showAsDropDown(View anchor) {
        onToggleListener.onToggle();
        super.showAsDropDown(anchor,offsetX,offsetY);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        onToggleListener.onToggle();
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        onToggleListener.onToggle();
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    @Override
    public void dismiss() {
        onToggleListener.onToggle();
        super.dismiss();
    }

    public CustomPopupWindow bindAnchorView(View view) {
        anchorView = view;
        return this;
    }

    public CustomPopupWindow offsetX(int x) {
        offsetX = x;
        return this;
    }
    public CustomPopupWindow offsetY(int y) {
        offsetY = y;
        return this;
    }

    public CustomPopupWindow bindClickListener(String... content) {
        for(String title:content) {
            bindClickListener(title, null);
        }
        return this;
    }

    @SuppressLint("NewApi")
    public <V extends View,T>CustomPopupWindow bindClickListener(@LayoutRes int lyRes, T entity, View.OnClickListener onClickListener, BiConsumer<V, T> biConsumer) {
        if (contentList.size() > 1)
            contentView.addView(createView(R.layout.ly_half_line));
        V linearLayout = createView(lyRes);
        biConsumer.accept(linearLayout, entity);
        contentView.addView(linearLayout);
        return this;
    }

    @SuppressLint("ResourceType")
    private <VIEW extends View>VIEW createView(@LayoutRes int lyRes) {
        LinearLayout view = new LinearLayout(context);
        layoutInflater.inflate(lyRes, view);
        return (VIEW) view;
    }

    public CustomPopupWindow bindClickListener(String content, View.OnClickListener onClickListener) {
        contentList.add(content);
        if (contentList.size() > 1)
            contentView.addView(createView(R.layout.ly_half_line));
        contentView.addView(createView(content, R.layout.ly_popup_item, onClickListener));
        return this;
    }

    private LinearLayout createView(String content, int resId, View.OnClickListener onClickListener) {
        LinearLayout view = createView(resId, onClickListener);
        view.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView textView = view.findViewById(R.id.contentTv);
        textView.setText(content);
        return view;
    }

    private LinearLayout createView(int resId, View.OnClickListener onClickListener) {
        LinearLayout view = createView(resId);
        view.setOnClickListener(onClickListener);
        return view;
    }

//    private LinearLayout createView(int resId) {
//        LinearLayout view = new LinearLayout(context);
//        layoutInflater.inflate(resId, view);
//        return view;
//    }

    public CustomPopupWindow show() {
        if(anchorView==null) {
            throw new RuntimeException("Please call method bindAnchorView first!");
        }
        showAsDropDown(anchorView);
        return this;
    }



    public interface OnToggleListener {
        void onToggle();
    }
}
