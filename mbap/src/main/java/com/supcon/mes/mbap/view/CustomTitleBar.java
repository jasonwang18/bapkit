package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseFrameLayout;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;


/**
 * Created by wangshizhan on 2017/10/31.
 * Email:wangshizhan@supcon.com
 */

public class CustomTitleBar extends BaseFrameLayout{

    ImageButton leftBtn;

    TextView titleText;

    ImageButton rightBtn;

    boolean rightBtnVisible = false;
    int rightBtnResId;
    int bgResId;
    String title;

    private OnTitleBarListener mOnTitleBarListener;

    public CustomTitleBar(Context context) {
        super(context);
    }

    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            titleText.setTypeface(newFont);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.v_titlebar;
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);
            rightBtnResId = array.getResourceId(R.styleable.CustomTitleBar_titlebar_right_icon, R.drawable.ic_tj);
            bgResId = array.getResourceId(R.styleable.CustomTitleBar_titlebar_bg_color, R.color.bgGray);
            title = array.getString(R.styleable.CustomTitleBar_titlebar_title_text);
            rightBtnVisible = array.getBoolean(R.styleable.CustomTitleBar_titlebar_right_visible, false);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        leftBtn = findViewById(R.id.leftBtn);
        titleText = findViewById(R.id.titleText);
        rightBtn = findViewById(R.id.rightBtn);

        if(!TextUtils.isEmpty(title)){
            titleText.setText(title);
        }

        if(rightBtnVisible){
            rightBtn.setImageResource(rightBtnResId);
            rightBtn.setVisibility(VISIBLE);
        }
        rootView.setBackgroundResource(bgResId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        leftBtn.setOnClickListener(v -> {
            if(mOnTitleBarListener!=null)
                mOnTitleBarListener.onLeftBtnClick();
            });

        rightBtn.setOnClickListener(v -> {
            if(mOnTitleBarListener!=null)
                mOnTitleBarListener.onRightBtnClick();
        });
    }

    public void setTitle(String title) {
        titleText.setText(title);
    }

    public void setTitle(int resId) {
        titleText.setText(resId);
    }

    public void showRightButton(int resId){

        rightBtn.setVisibility(VISIBLE);

        if(resId != 0 && resId != -1){
            rightBtn.setImageResource(resId);
        }
    }

    public void setOnTitleBarListener(OnTitleBarListener onTitleBarListener) {
        mOnTitleBarListener = onTitleBarListener;
    }

    public interface OnTitleBarListener{

        void onLeftBtnClick();
        void onRightBtnClick();

    }


}
