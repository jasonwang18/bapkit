package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConfig;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.listener.ICustomView;
import com.supcon.mes.mbap.listener.OnTextListener;
import com.supcon.mes.mbap.utils.KeyboardUtil;
import com.supcon.mes.mbap.utils.TextHelper;

import static com.supcon.mes.mbap.MBapConstant.KEY_RADIO;

/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */

public class CustomEditText extends BaseRelativeLayout implements View.OnTouchListener, View.OnClickListener, ICustomView{

    TextView customEditText;

    ImageView customDeleteIcon;
    ImageView customEditIcon;
    EditText customEditInput;

    private String mText, mKey, mContent;
    private String mHint;
    private String mGravity;
    private OnTextListener mTextListener;

    private int mTextSize, mPadding, mKeyTextSize, mContentTextSize;
    private int deleteIconResId, maxLength, maxLine;
    private int mTextColor, mHintColor;
    private boolean isNecessary = false, isEditable = true, isBold = false, isEnable;
    private boolean isEditIconVisible = true;
    private int mTextHeight, mTextWidth;

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_edittext;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Typeface newFont = MBapApp.fontType();
//            customEditText.setTypeface(newFont);
//            customEditInput.setTypeface(newFont);
//        }
    }

    @Override
    protected void initView() {
        super.initView();
        customEditText = findViewById(R.id.customEditText);
        customDeleteIcon = findViewById(R.id.customDeleteIcon);
        customEditInput = findViewById(R.id.customEditInput);
        customEditIcon =  findViewById(R.id.customEditIcon);

        if(!TextUtils.isEmpty(mText)){
            customEditText.setText(mText);
            customEditText.setVisibility(View.VISIBLE);
        }

        if(!TextUtils.isEmpty(mKey)){
            customEditText.setText(mKey);
            customEditText.setVisibility(View.VISIBLE);
        }

        if(!TextUtils.isEmpty(mHint)){
            customEditInput.setHint(mHint);
        }

        if(deleteIconResId!=0){
            customDeleteIcon.setImageResource(deleteIconResId);
        }

        if(maxLength!=0){
            customEditInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }


        if(maxLine!=0){
            customEditInput.setMaxLines(maxLine);
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

            setInputGravity(gravity);

        }

        if(mPadding!=0)
            customEditInput.setPadding(mPadding, 0, 0,0);

        if(mHintColor != 0){
            setHintColor(mHintColor);
        }

        if(isNecessary)
            setNecessary(isNecessary);

        if(mTextWidth != -1){
            setKeyWidth(mTextWidth);
        }

        if(mTextHeight != -1){
            setKeyHeight(mTextHeight);
        }


        setEditable(isEditable);

        if(isBold)
            setContentTextStyle(Typeface.BOLD);

        setEnabled(isEnable);
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText);
            mText = array.getString(R.styleable.CustomEditText_text);
            mKey = array.getString(R.styleable.CustomEditText_key);
            mContent = array.getString(R.styleable.CustomEditText_content);
            mHint = array.getString(R.styleable.CustomEditText_edit_hint);
            mGravity = array.getString(R.styleable.CustomEditText_gravity);
            mTextSize = array.getInt(R.styleable.CustomEditText_text_size, 0);
            deleteIconResId = array.getResourceId(R.styleable.CustomEditText_edit_delete, 0);
            mKeyTextSize = array.getInt(R.styleable.CustomEditText_key_size, 0);
            mContentTextSize = array.getInt(R.styleable.CustomEditText_content_size, 0);
            maxLength = array.getInt(R.styleable.CustomEditText_edit_maxLength, 0);
            maxLine = array.getInt(R.styleable.CustomEditText_edit_maxLine, 0);
            mPadding = (int) array.getDimension(R.styleable.CustomEditText_padding, 5);
            mTextColor = array.getColor(R.styleable.CustomEditText_text_color, 0);
            mHintColor = array.getColor(R.styleable.CustomEditText_edit_hint_color, 0);
            isNecessary = array.getBoolean(R.styleable.CustomEditText_necessary, false);
            isEditable = array.getBoolean(R.styleable.CustomEditText_editable, true);
            mTextHeight =(int) array.getDimension(R.styleable.CustomEditText_text_height, -1);
            mTextWidth =(int) array.getDimension(R.styleable.CustomEditText_text_width, -1);
            isBold = array.getBoolean(R.styleable.CustomEditText_bold, false);
            isEnable = array.getBoolean(R.styleable.CustomEditText_enable, true);
            isEditIconVisible = array.getBoolean(R.styleable.CustomEditText_icon_visible, true);
            array.recycle();
        }
    }



    @Override
    protected void initListener() {
        super.initListener();

        customEditInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString()) && isEditable){
                    customDeleteIcon.setVisibility(View.VISIBLE);
                }
                else{
                    customDeleteIcon.setVisibility(View.INVISIBLE);
                }

                if(mTextListener!=null){
                    mTextListener.onText(s.toString());
                }
            }
        });

        customDeleteIcon.setOnClickListener(v -> setContent("")
        );

        customEditInput.setOnTouchListener(this);

        customEditIcon.setOnClickListener(v -> {
            KeyboardUtil.editTextRequestFocus(customEditInput);
        });

        setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        if(!TextUtils.isEmpty(mContent)){
            customEditInput.setText(mContent);
            customEditInput.setVisibility(View.VISIBLE);
        }


        if (mTextColor != 0)
            customEditText.setTextColor(mTextColor);

        if (mTextSize != 0) {
            customEditInput.setTextSize(mTextSize);
        }

        if(mKeyTextSize!=0){
            customEditText.setTextSize(mKeyTextSize);
        }

        if(mContentTextSize!=0){
            customEditInput.setTextSize(mContentTextSize);
        }
    }

    @Override
    public void onClick(View v) {
        if(isEditable)
            KeyboardUtil.editTextRequestFocus(customEditInput);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.customEditInput && canVerticalScroll(customEditInput))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }

    /**
     * EditText竖直方向是否可以滚动
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if(!enabled)
            setEditable(false);

        if (enabled) {
            customEditText.setAlpha(1);
            customEditInput.setAlpha(1);
        } else {
            customEditText.setAlpha(0.5f);
            customEditInput.setAlpha(0.5f);
        }

    }

    @Override
    public void setEditable(boolean editable) {
        isEditable = editable;
        customEditInput.setEnabled(editable);
        if(editable){
            customEditText.setTextColor(getResources().getColor(R.color.textColorblack));
            customEditInput.setTextColor(mTextColor!=0?mTextColor:getResources().getColor(R.color.editableTextColor));
            customEditText.setOnClickListener(this);
        }
        else{
            customEditText.setTextColor(getResources().getColor(R.color.notEditableTextColor));
            customEditInput.setTextColor(getResources().getColor(R.color.notEditableTextColor));
            customEditText.setOnClickListener(null);
        }

        if(isEditIconVisible && editable){
            customEditIcon.setVisibility(VISIBLE);
        }
        else{
            customEditIcon.setVisibility(GONE);
        }

//        if(editable){
//            customEditInput.setFocusable(true);
//        }
//        else{
//            customEditInput.setFocusable(false);
//            customEditInput.setPadding(0,0, DisplayUtil.dip2px(8, getContext()),0);
//        }
    }

    @Override
    public EditText editText() {
        return customEditInput;
    }

    @Override
    public TextView contentView() {
        return null;
    }

    @Override
    public TextView keyView() {
        return customEditText;
    }

    @Override
    public void setContentGravity(int gravity) {
        customEditInput.setGravity(gravity);
    }


    @Override
    public void setNecessary(boolean isNecessary){
        TextHelper.setRequired(isNecessary, customEditText);
    }

    @Override
    public boolean isNecessary() {
        return isNecessary;
    }

    @Override
    public boolean isEditable() {
        return isEditable;
    }

    @Override
    public boolean isEmpty() {
        return TextUtils.isEmpty(getInput());
    }

    public void setTextStyle(int textStyle){
        customEditText.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setContentTextStyle(int textStyle){
        customEditInput.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    @Override
    public void setTextFont(Typeface newFont) {
        customEditText.setTypeface(newFont);
        customEditInput.setTypeface(newFont);
    }

    @Override
    public void setKeyTextSize(int textSize) {
        customEditText.setTextSize(textSize);
    }

    @Override
    public void setContentTextSize(int textSize) {
        customEditInput.setTextSize(textSize);
    }

    @Override
    public void setKeyTextColor(int color) {
        customEditText.setTextColor(color);
    }

    @Override
    public void setContentTextColor(int color) {
        customEditInput.setTextColor(color);
    }

    @Override
    public void setContentPadding(int left, int top, int right, int bottom) {
        customEditInput.setPadding(left, top, right, bottom);
    }

    @Override
    public void setKeyTextStyle(int textStyle) {
        customEditText.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    @Override
    public void setEditIcon(int resId) {
        //no use
    }

    @Override
    public void setClearIcon(int resId) {
        customDeleteIcon.setImageResource(resId);
    }

    @Override
    public void setKeyWidth(int width) {
        ViewGroup.LayoutParams lp = customEditText.getLayoutParams();
        lp.width = width;
        customEditText.setLayoutParams(lp);
    }

    @Override
    public void setKeyHeight(int height) {
        ViewGroup.LayoutParams lp = customEditText.getLayoutParams();
        lp.height = height;
        customEditText.setLayoutParams(lp);

        ViewGroup.LayoutParams lp2 =  customDeleteIcon.getLayoutParams();
        lp2.height = height;
        customDeleteIcon.setLayoutParams(lp2);
    }

    @Override
    public void setKey(String key) {
        customEditText.setText(key);
    }

    @Override
    public void setKey(int keyResId) {
        customEditText.setText(keyResId);
    }

    @Override
    public String getKey() {
        return customEditText.getText().toString();
    }

    @Override
    public String getContent() {
        return customEditInput.getText().toString();
    }

    @Override
    public void setContent(String content) {
        customEditInput.setText(content);
        if(!TextUtils.isEmpty(content))
            customEditInput.setSelection(content.length());
    }

    @Override
    public void setContent(int contentResId) {
        setInput(getResources().getString(contentResId));
    }


    public void setTextListener(OnTextListener textListener) {
        mTextListener = textListener;
    }


    public void setImeOptions(int imeOptions){
        customEditInput.setImeOptions(imeOptions);
    }

    public TextView textView(){
        return customEditText;
    }

    public void setText(String text){
        customEditText.setText(text);
    }

    public String getText(){
        return customEditText.getText().toString();
    }

    public void setInput(String input){
            setContent(input);
    }

    public void setHint(String hint){
        customEditInput.setHint(hint);
    }

    public void setHintColor(int color){
        customEditInput.setHintTextColor(color);
    }

    public String getInput(){
        return customEditInput.getText().toString();
    }

    public void setInputTextSize(int textSize){
        customEditInput.setTextSize(textSize);
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        customEditInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public void setInputGravity(int gravity){
        customEditInput.setGravity(gravity);
    }

    public void setInputType(int type){
        customEditInput.setInputType(type);
    }
}
