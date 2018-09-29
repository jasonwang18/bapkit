package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;

/**
 * Created by wangshizhan on 2017/8/21.
 * Email:wangshizhan@supcon.com
 */

public class CustomArrowView extends BaseLinearLayout {

    private String text;

    private int iconRes;

    ImageView customIcon;
    TextView customValue;
    ImageView customArrow;


    public CustomArrowView(Context context) {
        super(context);
    }

    public CustomArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_arrowview;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customValue.setTypeface(newFont);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        customIcon = findViewById(R.id.customIcon);
        customValue = findViewById(R.id.customValue);
        customArrow = findViewById(R.id.customArrow);

        if(!TextUtils.isEmpty(text)){
            customValue.setText(text);
        }

        if(iconRes!=0){
            customIcon.setImageResource(iconRes);
        }

    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomArrowView);
            iconRes = array.getResourceId(R.styleable.CustomArrowView_arrow_icon, R.drawable.ic_user);
            text = array.getString(R.styleable.CustomArrowView_arrow_text);
            array.recycle();
        }
    }



    public void setText(String text){
        customValue.setText(text);
    }

    public void setText(int resId){
        customValue.setText(resId);
    }

    public void setTextColor(int color){

        customValue.setTextColor(color);

    }

    public void setIconRes(int iconRes){
        customIcon.setImageResource(iconRes);
    }


}
