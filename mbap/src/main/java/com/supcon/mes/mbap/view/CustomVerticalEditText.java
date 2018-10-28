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

import com.jakewharton.rxbinding2.view.RxView;
import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.listener.ICustomView;
import com.supcon.mes.mbap.listener.OnContentCheckListener;
import com.supcon.mes.mbap.listener.OnTextListener;
import com.supcon.mes.mbap.utils.KeyboardUtil;
import com.supcon.mes.mbap.utils.TextHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.supcon.mes.mbap.MBapConstant.KEY_RADIO;

/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */

public class CustomVerticalEditText extends BaseLinearLayout implements View.OnTouchListener, View.OnClickListener, ICustomView {

    TextView customEditText;
    ImageView customEditDelete;
    EditText customEditInput;
    ImageView customEditIcon;
    private String mText, mKey, mContent;
    private String mHint;
    private String mGravity;
    private OnTextListener mTextListener;

    private float mTextSize, mKeyTextSize, mContentTextSize;
    private int mPadding;
    private int deleteIconResId, maxLength, maxLine;
    private int mTextColor, mHintColor;
    private boolean isNecessary, isEditable, isBold, isEnable;
    private int mTextHeight, mTextWidth;
    private boolean isEditIconVisible = true;
    private OnContentCheckListener mOnContentCheckListener;

    public CustomVerticalEditText(Context context) {
        super(context);
    }

    public CustomVerticalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Typeface newFont = MBapApp.fontType();
//            customEditText.setTypeface(newFont);
//            customEditInput.setTypeface(newFont);
//        }
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_vertical_edittext;
    }

    @Override
    protected void initView() {
        super.initView();
        customEditText = findViewById(R.id.customEditText);
        customEditDelete = findViewById(R.id.customEditDelete);
        customEditInput = findViewById(R.id.customEditInput);
        customEditIcon =  findViewById(R.id.customEditIcon);

        if (!TextUtils.isEmpty(mText)) {
            customEditText.setText(mText);
            customEditText.setVisibility(View.VISIBLE);
        }

        if(!TextUtils.isEmpty(mKey)){
            customEditText.setText(mKey);
            customEditText.setVisibility(View.VISIBLE);
        }



        if (!TextUtils.isEmpty(mHint)) {
            customEditInput.setHint(mHint);
        }

        if (deleteIconResId != 0) {
            customEditDelete.setImageResource(deleteIconResId);
        }

        if (maxLength != 0) {
            customEditInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }



        if (maxLine != 0) {
            customEditInput.setMaxLines(maxLine);
        }

        if (mPadding != 0)
            customEditInput.setPadding(mPadding, mPadding, mPadding, mPadding);

        if (!TextUtils.isEmpty(mGravity)) {

            int gravity = Gravity.NO_GRAVITY;

            if (mGravity.contains("center_horizontal")) {
                gravity = Gravity.CENTER_HORIZONTAL;
            } else if (mGravity.contains("center_vertical")) {
                gravity = Gravity.CENTER_VERTICAL;
            } else if (mGravity.contains("center")) {
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

        if (mPadding != 0) {
            customEditInput.setPadding(mPadding, mPadding, mPadding, mPadding);

        }

        if (isNecessary)
            setNecessary(isNecessary);
        if (mTextHeight != -1) {
            setKeyHeight(mTextHeight);
        }

        if (mTextWidth != -1) {
            setKeyWidth(mTextWidth);
        }

        if (mHintColor != 0) {
            setHintColor(mHintColor);
        }

        setEditable(isEditable);

        if (isBold)
            setContentTextStyle(Typeface.BOLD);

        setEnabled(isEnable);
    }


    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomVerticalEditText);
            mText = array.getString(R.styleable.CustomVerticalEditText_text);
            mKey = array.getString(R.styleable.CustomVerticalEditText_key);
            mContent = array.getString(R.styleable.CustomVerticalEditText_content);
            mHint = array.getString(R.styleable.CustomVerticalEditText_edit_hint);
            mGravity = array.getString(R.styleable.CustomVerticalEditText_gravity);
            mTextSize = array.getInt(R.styleable.CustomVerticalEditText_text_size, 0);
            mKeyTextSize = array.getInt(R.styleable.CustomVerticalEditText_key_size, 0);
            mContentTextSize = array.getInt(R.styleable.CustomVerticalEditText_content_size, 0);
            deleteIconResId = array.getResourceId(R.styleable.CustomEditText_edit_delete, 0);
            maxLength = array.getDimensionPixelSize(R.styleable.CustomEditText_edit_maxLength, 0);
            maxLine = array.getInt(R.styleable.CustomEditText_edit_maxLine, 0);
            mPadding = array.getDimensionPixelSize(R.styleable.CustomVerticalEditText_padding, 0);
            mTextColor = array.getColor(R.styleable.CustomVerticalEditText_text_color, 0);
            isNecessary = array.getBoolean(R.styleable.CustomVerticalEditText_necessary, false);
            isEditable = array.getBoolean(R.styleable.CustomVerticalEditText_editable, true);
            mTextHeight = array.getDimensionPixelSize(R.styleable.CustomVerticalEditText_text_height, -1);
            mTextWidth = array.getDimensionPixelSize(R.styleable.CustomVerticalEditText_text_width, -1);
            mHintColor = array.getColor(R.styleable.CustomVerticalEditText_edit_hint_color, 0);
            isBold = array.getBoolean(R.styleable.CustomVerticalEditText_bold, false);
            isEnable = array.getBoolean(R.styleable.CustomVerticalEditText_enable, true);
            isEditIconVisible = array.getBoolean(R.styleable.CustomVerticalEditText_icon_visible, true);
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
                if (!TextUtils.isEmpty(s.toString()) && isEditable) {
                    customEditDelete.setVisibility(View.VISIBLE);
                } else {
                    customEditDelete.setVisibility(View.INVISIBLE);
                }

                if (mTextListener != null) {
                    mTextListener.onText(s.toString());
                }
            }
        });

        customEditDelete.setOnClickListener(v -> setContent(""));

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
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
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
        customEditDelete.setImageResource(resId);
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

        ViewGroup.LayoutParams lp2 =  customEditDelete.getLayoutParams();
        lp2.height = height;
        customEditDelete.setLayoutParams(lp2);
    }

    @Override
    public boolean isEmpty() {
        return TextUtils.isEmpty(getContent());
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
