package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.listener.OnChildViewClickListener;

import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
/**
 * Created by wangshizhan on 2017/8/21.
 * Email:wangshizhan@supcon.com
 */

public class CustomInputView extends BaseLinearLayout {

    private String mType, mText;

    private float mTextWidth;
    private float mTextSize ;

    private boolean isMust = true;

    TextView customText;

    TextView customValue;

    EditText customInput;

    TextView customSpinner;

    private boolean isEnable = true;

    private int themeColor;

    public void setThemeColor(int themeColor) {
        this.themeColor = themeColor;
    }


    public CustomInputView(Context context) {
        super(context);
    }

    public CustomInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_input;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customText.setTypeface(newFont);
            customValue.setTypeface(newFont);
            customInput.setTypeface(newFont);
            customSpinner.setTypeface(newFont);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        customText = findViewById(R.id.customText);
        customValue = findViewById(R.id.customValue);
        customInput = findViewById(R.id.customInput);
        customSpinner = findViewById(R.id.customSpinner);

        if(!TextUtils.isEmpty(mText)){
            customText.setText(mText);
        }

        if(mTextSize!=0){
            customValue.setTextSize(mTextSize);
            customInput.setTextSize(mTextSize);
            customSpinner.setTextSize(mTextSize);
        }

        if(mTextWidth!=0){
            setTextWidth(mTextWidth);
        }

        updateView(mType);
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomInputView);
            mType = array.getString(R.styleable.CustomInputView_type);
            mText = array.getString(R.styleable.CustomInputView_text);
            mTextSize = array.getInt(R.styleable.CustomInputView_text_size, 0);
            isMust = array.getBoolean(R.styleable.CustomInputView_necessary, true);
            mTextWidth = array.getDimensionPixelSize(R.styleable.CustomInputView_text_width, 0);
            array.recycle();
        }
    }

    public void setTextWidth(float textWidth) {
        mTextWidth = textWidth;
        ViewGroup.LayoutParams lp = customText.getLayoutParams();
        lp.width = (int) mTextWidth;
        customText.setLayoutParams(lp);
    }

    public void setType(String type){
        mType = type;
        updateView(type);
    }

    public void setMust(boolean isMust){
        this.isMust = isMust;
        updateView(mType);
    }

    public String getType() {
        return mType;
    }

    private void updateView(String type) {
        if(TextUtils.isEmpty(type) || type.equals(Type.TEXT)){
            customValue.setVisibility(View.VISIBLE);
            customInput.setVisibility(View.GONE);
            customSpinner.setVisibility(View.GONE);
        }
        else if(type.equals(Type.EDIT)){
            customValue.setVisibility(View.GONE);
            customInput.setVisibility(View.VISIBLE);
            customText.setTextColor(isMust?getResources().getColor(themeColor):getResources().getColor(R.color.textColorlightblack));
        }
        else if(type.equals(Type.SPINNER)){
            customValue.setVisibility(View.GONE);
            customSpinner.setVisibility(View.VISIBLE);
            customText.setTextColor(isMust?getResources().getColor(themeColor):getResources().getColor(R.color.textColorlightblack));
        }
    }

    public void setNecessary(boolean isNecessary){

        if(isNecessary){
            customText.setTextColor(getResources().getColor(R.color.customRed));
        }

    }

    public void setText(String text){
        customText.setText(text);
    }

    public void setText(int resId){
        customText.setText(resId);
    }

    public void setTextColor(int color){

        customText.setTextColor(color);

    }

    public void setValueColor(int color){
        customValue.setTextColor(color);
    }

    public void setSpinnerColor(int color){
        customSpinner.setTextColor(color);
    }

    public void setValue(String text){

        if(customValue.getVisibility() == View.VISIBLE) {
            customValue.setText(text);
        }
        else if(customInput.getVisibility() == View.VISIBLE){
            customInput.setText(text);
        }
    }

    public String getValue() {
        return customValue.getText().toString();

    }

    public String getInputValue() {
        return customInput.getText().toString();

    }


    public void setValue(int resId){

        if(customValue.getVisibility() == View.VISIBLE) {
            customValue.setText(resId);
        }
        else if(customInput.getVisibility() == View.VISIBLE){
            customInput.setText(resId);
        }

    }


    public void setInputType(int type){
        if(customInput.getVisibility() == View.VISIBLE){
            customInput.setInputType(type);
        }
    }

    public void setSpinner(String value){
        customSpinner.setText(value);

    }

    public String getSpinnerValue(){
        return customSpinner.getText().toString();
    }

    @Override
    public void setOnChildViewClickListener(OnChildViewClickListener onChildViewClickListener) {
        super.setOnChildViewClickListener(onChildViewClickListener);
        customSpinner.setOnClickListener(v -> {

            if(onChildViewClickListener!=null && isEnable)
                onChildViewClickListener.onChildViewClick(CustomInputView.this, 0, null);

        });
    }

    public void setEnable(boolean enable) {
        isEnable = enable;

        if(enable){

        }
        else {
            if (Type.SPINNER.equals(mType)) {
                mType = Type.TEXT;
                customValue.setText(getSpinnerValue());
                updateView(mType);
            }
            customText.setTextColor(getResources().getColor(R.color.hintColor));
        }

    }

    public TextView getCustomText() {

        return customText;
    }


    public interface Type{

        String SPINNER = "spinner";
        String TEXT = "text";
        String EDIT = "edit";

    }

}
