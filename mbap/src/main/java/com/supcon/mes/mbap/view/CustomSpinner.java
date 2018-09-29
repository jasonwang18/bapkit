package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConfig;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.utils.Orientation;
import com.supcon.mes.mbap.utils.TextHelper;

/**
 * Created by wangshizhan on 2017/8/21.
 * Email:wangshizhan@supcon.com
 */

public class CustomSpinner extends BaseLinearLayout implements View.OnClickListener{

    private String mText, mContent;

    private float mTextSize;
    private String mGravity;
    private int mPadding;
    private int mTextWidth;
    TextView customSpinnerText;
    TextView customSpinner;
    ImageView customSpinnerIcon;

    private boolean isNecessary, isEditable;
    private int mTextColor;
    private boolean isBold;

    public CustomSpinner(Context context) {
        super(context);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected int layoutId() {

        return R.layout.v_custom_spinner;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customSpinnerText.setTypeface(newFont);
            customSpinner.setTypeface(newFont);
        }

    }

    @Override
    protected void initView() {
        super.initView();
        customSpinnerText = findViewById(R.id.customSpinnerText);
        customSpinner = findViewById(R.id.customSpinner);
        customSpinnerIcon = findViewById(R.id.customSpinnerIcon);

        if(!TextUtils.isEmpty(mText)){
            customSpinnerText.setText(mText);
            customSpinnerText.setVisibility(View.VISIBLE);
        }
        else{
            customSpinnerText.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(mContent)){
            customSpinner.setText(mContent);
        }

        if(mTextSize!=0){
            customSpinner.setTextSize(mTextSize);
//            customSpinnerText.setTextSize(mTextSize);
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

            customSpinner.setGravity(gravity);
        }

        if(mPadding != 0){
            setSpinnerPadding(mPadding);
        }

        if(mTextColor!=0) {
            customSpinnerText.setTextColor(mTextColor);
            customSpinner.setTextColor(mTextColor);
        }

        if(mTextWidth!=-1)
            setTextWidth(mTextWidth);

        if(isNecessary)
            setNecessary(isNecessary);
        setEnabled(isEditable);

        if(isBold)
            setContentTextStyle(Typeface.BOLD);
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomSpinner);
            mText = array.getString(R.styleable.CustomSpinner_text);
            mContent = array.getString(R.styleable.CustomSpinner_content);
            mTextSize = array.getInt(R.styleable.CustomSpinner_text_size, 0);
            mGravity = array.getString(R.styleable.CustomSpinner_gravity);
            mPadding = array.getDimensionPixelSize(R.styleable.CustomSpinner_padding, 0);
            isNecessary = array.getBoolean(R.styleable.CustomSpinner_necessary, false);
            isEditable = array.getBoolean(R.styleable.CustomSpinner_editable, true);
            mTextColor = array.getColor(R.styleable.CustomSpinner_text_color, 0);
            mTextWidth = array.getDimensionPixelSize(R.styleable.CustomSpinner_text_width, -1);
            isBold = array.getBoolean(R.styleable.CustomSpinner_bold, false);
            array.recycle();
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
//        customSpinner.setOnClickListener(this);
        customSpinnerIcon.setOnClickListener(this);
//        customSpinnerText.setOnClickListener(this);
        customSpinner.setOnLongClickListener(v -> {
            CustomContentTextDialog.showContent(getContext(), customSpinner.getText().toString());
            return true;
        });
    }



    public void setTextWidth(int width){
        LayoutParams lp = (LayoutParams) customSpinnerText.getLayoutParams();
        lp.width = width;
        customSpinnerText.setLayoutParams(lp);

    }

    public void setTextStyle(int textStyle){
        customSpinnerText.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setContentTextStyle(int textStyle){
        customSpinner.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setText(String text){
        customSpinnerText.setText(text);
    }

    public void setText(int resId){
        customSpinnerText.setText(resId);
    }

    public void setTextColor(int color){

        customSpinnerText.setTextColor(color);
    }



    public void setNecessary(boolean isNecessary){

//        if(isNecessary){
//            customSpinnerText.setTextColor(getResources().getColor(R.color.customRed));
//        }
//        else {
//            customSpinnerText.setTextColor(getResources().getColor(MBapConfig.NECESSARY_FALSE_COLOR));
//        }
        TextHelper.setRequired(isNecessary, customSpinnerText);
    }

    public void setSpinnerColor(int color){
        customSpinner.setTextColor(color);
    }


    public void setSpinner(String value){
        customSpinner.setText(value);

    }

    public void setSpinnerTextSize(int textSize){
        customSpinner.setTextSize(textSize);
    }

    public String getSpinnerValue(){
        return customSpinner.getText().toString();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setEditable(enabled);
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
        if(!editable){
            customSpinnerIcon.setVisibility(View.INVISIBLE);
            customSpinner.setOnClickListener(this);
        }
        else{
            customSpinnerIcon.setVisibility(View.VISIBLE);
            customSpinner.setOnClickListener(null);
        }
    }

    public void setSpinnerPadding(int padding){
        customSpinner.setPadding(mPadding, 0, 0,0);
    }

    @Override
    public void onClick(View v) {
        if(isEditable)
            onChildViewClick(CustomSpinner.this, 0, null);
    }

    public TextView getCustomSpinner() {
        return customSpinner;
    }
}
