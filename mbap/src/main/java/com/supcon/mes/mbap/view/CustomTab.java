package com.supcon.mes.mbap.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;

/**
 * Created by wangshizhan on 2017/8/28.
 * Email:wangshizhan@supcon.com
 */

public class CustomTab extends BaseRelativeLayout implements View.OnClickListener {

    LinearLayout tabLayout;

    View indicator;

    int oldPosition = 0;
    int currentPosition = 0;

    boolean isIndicatorVisible;
    boolean isTabNumVisible;
    int indicatorColor;
    int stepWidth;
    int bgColor;
    int unSelectTextColor;
    int selectTextColor;
    float gap;
    Drawable unSelectBg;
    Drawable selectBg;

    private OnTabChangeListener listener;

    public CustomTab(Context context) {
        super(context);
    }

    public CustomTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnTabChangeListener(OnTabChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);

    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTab);
            isIndicatorVisible = array.getBoolean(R.styleable.CustomTab_indicator_visible, true);
            isTabNumVisible = array.getBoolean(R.styleable.CustomTab_tab_num_visible, false);
            unSelectTextColor = array.getColor(R.styleable.CustomTab_unselect_text_color, getResources().getColor(R.color.hintColor));
            selectTextColor = array.getColor(R.styleable.CustomTab_select_text_color, getResources().getColor(R.color.equipmentThemeColor));
            unSelectBg = array.getDrawable(R.styleable.CustomTab_unselect_bg_color);
            selectBg = array.getDrawable(R.styleable.CustomTab_select_bg_color);
            bgColor = array.getColor(R.styleable.CustomTab_bg_color, 0);
            indicatorColor = array.getColor(R.styleable.CustomTab_indicator_color, 0);
            gap = array.getDimension(R.styleable.CustomTab_gap, 0);
            array.recycle();
        }


    }

    @Override
    protected void initView() {
        super.initView();
        tabLayout = findViewById(R.id.tabLayout);
        indicator = findViewById(R.id.indicator);

        if (unSelectBg == null) {
            unSelectBg = getResources().getDrawable(R.drawable.bg_tab);
        }

        if (selectBg == null) {
            selectBg = getResources().getDrawable(R.drawable.bg_tab_p);
        }

        if (bgColor != 0) {
            ((RelativeLayout) tabLayout.getParent()).setBackgroundColor(bgColor);
        }


    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void updateView() {

        int size = tabLayout.getChildCount();

        if (size == 0) {
            return;
        }



        /*for(int i = 0; i < size; i++){
            TextView tab = (TextView) tabLayout.getChildAt(i);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab.getLayoutParams();
            lp.weight=1;
            tab.setLayoutParams(lp);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Typeface newFont = MBapApp.fontType();
                tab.setTypeface(newFont);
            }
        }*/

        stepWidth = DisplayUtil.getScreenWidth(getContext()) / size;

        if (isIndicatorVisible) {
            indicator.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams) indicator.getLayoutParams();
            lp3.width = stepWidth;
            indicator.setLayoutParams(lp3);
        }

        if (indicatorColor != 0) {
            indicator.setBackgroundColor(indicatorColor);
        }

    }

    @Override
    protected void initListener() {
        super.initListener();

    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_tabs;
    }

    public void addTab(String text, int color) {
        TextView tab = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
//        tab.setLayoutParams(lp);
        tab.setGravity(Gravity.CENTER);
        tab.setTextColor(color);
        tab.setText(text);
        tab.setOnClickListener(this);
        tab.setTag(tabLayout.getChildCount());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            tab.setTypeface(newFont);
        }
        lp.weight = 1;
        lp.leftMargin = (int) gap;
        tabLayout.addView(tab, lp);
        tabLayout.addView(tab);
        updateView();
    }

    public void addTab(String text) {
        TextView tab = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
//        tab.setLayoutParams(lp);
        tab.setGravity(Gravity.CENTER);
        tab.setText(text);
        tab.setTextColor(unSelectTextColor);
        tab.setBackground(unSelectBg);
        tab.setOnClickListener(this);
        tab.setTag(tabLayout.getChildCount());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            tab.setTypeface(newFont);
        }

        lp.weight = 1;
        lp.leftMargin = (int) gap;
        tabLayout.addView(tab, lp);
        updateView();
    }


    public void setIndicatorVisible(boolean indicatorVisible) {
        isIndicatorVisible = indicatorVisible;
    }

    private void setTab(int position, int color, Drawable bgColor) {

        TextView tab = (TextView) tabLayout.getChildAt(position);
        tab.setTextColor(color);
        tab.setBackground(bgColor);
    }

    public void setCurrentTab(int position) {
        oldPosition = currentPosition;
        if (isIndicatorVisible)
            startAnimation(indicator, currentPosition * stepWidth, position * stepWidth);
        else {
            setTab(oldPosition, unSelectTextColor, unSelectBg);
            setTab(position, selectTextColor, selectBg);

        }
        currentPosition = position;
    }

    public int getCurrentPosition() {
        return currentPosition;

    }

    public void setTabNum(int position, int num) {
        TextView tab = (TextView) tabLayout.getChildAt(position);
        String text = tab.getText().toString();

        if(text.contains("(")){
            text = text.split(" ")[0];
        }


        if (num > 0) {
            StringBuilder stringBuilder = new StringBuilder(text);
            stringBuilder.append(" (");
            stringBuilder.append(String.valueOf(num));
            stringBuilder.append(")");
            tab.setText(stringBuilder.toString());
        }
        else{

            tab.setText(text);

        }

    }

    private void startAnimation(View v, float start, float end) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.addUpdateListener(arg0 -> {
            float value = (float) arg0.getAnimatedValue();
            v.setX(value);

            if (value == end) {
                setTab(oldPosition, unSelectTextColor, unSelectBg);
                setTab(currentPosition, selectTextColor, selectBg);
                if (listener != null) {
                    listener.onTabChanged(currentPosition);
                }
            }

        });

        animator.start();
    }

    @Override
    public void onClick(View v) {

        int position = (int) v.getTag();
        setCurrentTab(position);
        if (listener != null) {
            listener.onTabChanged(position);
        }
    }

    public interface OnTabChangeListener {

        void onTabChanged(int current);

    }


}
