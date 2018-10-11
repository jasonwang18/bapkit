package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConfig;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.utils.TextHelper;

/**
 * Created by wangshizhan on 2017/8/21.
 * Email:wangshizhan@supcon.com
 */

public class CustomVerticalDateView extends BaseLinearLayout implements View.OnClickListener{

    private String  mText, mContent;
    private float mTextSize;
    TextView customDateText;
    TextView customDateInput;
    ImageView customDateIcon;
    ImageView customDeleteIcon;
    private String mGravity;
    private int mPadding;
    private int mTextHeight;
    private int mTextColor;

    private boolean isNecessary, isEditable;
    private boolean isBold;

    public CustomVerticalDateView(Context context) {
        super(context);
    }

    public CustomVerticalDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_vertical_date;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customDateText.setTypeface(newFont);
            customDateInput.setTypeface(newFont);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        customDateText = findViewById(R.id.customDateText);
        customDateInput = findViewById(R.id.customDateInput);
        customDateIcon = findViewById(R.id.customDateIcon);
        customDeleteIcon = findViewById(R.id.customDeleteIcon);

        if(!TextUtils.isEmpty(mText)) {
            customDateText.setText(mText);
            customDateText.setVisibility(VISIBLE);
        }

        if(!TextUtils.isEmpty(mContent)) {
            setDate(mContent);
//            customDateInput.setText(mContent);
        }

        if(mTextSize!=0) {
            customDateInput.setTextSize(mTextSize);
//            customDateText.setTextSize(mTextSize);
        }

        if(!TextUtils.isEmpty(mGravity)){

            int gravity = Gravity.NO_GRAVITY;

            if(mGravity.contains("center_horizontal")){
                gravity = Gravity.CENTER_HORIZONTAL;
            }
            else if(mGravity.contains("center_vertical")){
                gravity = Gravity.CENTER_VERTICAL;
            }
            else if(mGravity.contains("center")){
                gravity = Gravity.CENTER;
            }

            if (mGravity.contains("top")) {
                gravity = gravity | Gravity.TOP;
            }

            if (mGravity.contains("left")) {
                gravity = gravity | Gravity.LEFT;
            }

            if (mGravity.contains("right")) {
                gravity = gravity | Gravity.RIGHT;
            }

            if (mGravity.contains("bottom")) {
                gravity = gravity | Gravity.BOTTOM;
            }

            customDateInput.setGravity(gravity);
        }

        if(mPadding != 0){
            setDatePadding(mPadding);
        }

        if(mTextHeight!=-1){
            setTextHeight(mTextHeight);
        }

        if(mTextColor!=0) {
            customDateText.setTextColor(mTextColor);
            customDateInput.setTextColor(mTextColor);
        }

        if(isNecessary)
            setNecessary(isNecessary);
        setEditable(isEditable);

        if(isBold)
            setContentTextStyle(Typeface.BOLD);
    }

    @Override
    protected void initListener() {
        super.initListener();
//        customDateInput.setOnClickListener(this);
        customDateIcon.setOnClickListener(this);
//        customDateText.setOnClickListener(this);


        customDeleteIcon.setOnClickListener(v -> {
            setDate("");
            onChildViewClick(CustomVerticalDateView.this, -1, customDateInput.getText().toString());
        });
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomVerticalDateView);
            mText = array.getString(R.styleable.CustomVerticalDateView_text);
            mContent = array.getString(R.styleable.CustomVerticalDateView_content);
            mTextSize = array.getInt(R.styleable.CustomVerticalDateView_text_size, 0);
            mGravity = array.getString(R.styleable.CustomVerticalDateView_gravity);
            mPadding = array.getDimensionPixelSize(R.styleable.CustomVerticalDateView_padding, 0);
            isNecessary = array.getBoolean(R.styleable.CustomVerticalDateView_necessary, false);
            isEditable = array.getBoolean(R.styleable.CustomVerticalDateView_editable, true);
            mTextHeight =array.getDimensionPixelSize(R.styleable.CustomVerticalDateView_text_height, -1);
            mTextColor = array.getColor(R.styleable.CustomVerticalDateView_text_color, 0);
            isBold = array.getBoolean(R.styleable.CustomVerticalDateView_bold, false);
            array.recycle();
        }
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setEditable(enabled);

    }

    public void setEditable(boolean editable) {
        isEditable = editable;
        if(editable){
            customDateInput.setOnClickListener(this);
            customDateIcon.setVisibility(VISIBLE);
        }
        else {
            customDateIcon.setVisibility(GONE);
            customDateInput.setOnClickListener(null);
        }
    }

    public void setDate(String date){
        customDateInput.setText(date);
        if(TextUtils.isEmpty(date) || !isEditable){
            customDeleteIcon.setVisibility(GONE);
        }
        else {
            customDeleteIcon.setVisibility(VISIBLE);
        }
    }

    public String getDate(){
        CharSequence result =  customDateInput.getText();
        return TextUtils.isEmpty(result)?"":result.toString().trim();

    }

    public void setTextHeight(int height){
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) customDateText.getLayoutParams();
        lp.height = height;
        customDateText.setLayoutParams(lp);

        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) customDateIcon.getLayoutParams();
        lp2.height = height;
        customDateIcon.setLayoutParams(lp2);
    }

    public void setDatePadding(int padding){
        customDateInput.setPadding(mPadding, 0, 0,0);
    }

    public void setText(String text){
        customDateText.setText(text);
    }

    public void setText(int resId){
        customDateText.setText(resId);
    }

    public void setTextColor(int color){
        customDateText.setTextColor(color);
    }

    public void setDateColor(int color){
        customDateInput.setTextColor(color);
    }


    public void setNecessary(boolean isNecessary){
//        if(isNecessary){
//            customDateText.setTextColor(getResources().getColor(R.color.customRed));
//        }
//        else {
//            customDateText.setTextColor(getResources().getColor(MBapConfig.NECESSARY_FALSE_COLOR));
//        }
        TextHelper.setRequired(isNecessary, customDateText);
    }

    public void setTextStyle(int textStyle){
        customDateText.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setContentTextStyle(int textStyle){
        customDateInput.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setDateTextSize(int textSize){
        customDateInput.setTextSize(textSize);
    }

    @Override
    public void onClick(View v) {
        if(isEditable)
            onChildViewClick(CustomVerticalDateView.this, 0, customDateInput.getText().toString());
    }

    public TextView getCustomDateInput() {
        return customDateInput;
    }
}
