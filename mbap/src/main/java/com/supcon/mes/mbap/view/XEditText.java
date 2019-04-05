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

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.listener.OnContentCheckListener;
import com.supcon.mes.mbap.listener.OnTextListener;

/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */

public class XEditText extends BaseLinearLayout implements View.OnTouchListener{

    TextView customEditText, customCheckInfo;
    ImageView customEditDelete;
    EditText customEditInput;
    View line;

    private String mText, mErrorMsg;
    private String mHint;
    private String mGravity;
    private OnTextListener mTextListener;

    private int mTextSize, mPadding;
    private int deleteIconResId, maxLength, maxLine;
    private int mTextColor, mHintColor;
    private boolean isNecessary, isEditable, isLineVisible ;
    private int mTextHeight, mTextWidth;
    private OnContentCheckListener mOnContentCheckListener;
    private Comparable<String> mContentComparable;

    public XEditText(Context context) {
        super(context);
    }

    public XEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customEditText.setTypeface(newFont);
            customEditInput.setTypeface(newFont);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.v_x_edittext;
    }

    @Override
    protected void initView() {
        super.initView();
        customEditText = findViewById(R.id.customEditText);
        customEditDelete = findViewById(R.id.customEditDelete);
        customEditInput = findViewById(R.id.customEditInput);
        customCheckInfo = findViewById(R.id.customCheckInfo);
        line = findViewById(R.id.line);

        if(!TextUtils.isEmpty(mText)){
            customEditText.setText(mText);
            customEditText.setVisibility(View.VISIBLE);
        }

        if(!TextUtils.isEmpty(mHint)){
            customEditInput.setHint(mHint);
        }

        if(deleteIconResId!=0){
            customEditDelete.setImageResource(deleteIconResId);
        }

        if(maxLength!=0){
            customEditInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }

        if(mTextSize != 0){
            customEditInput.setTextSize(mTextSize);
//            customEditText.setTextSize(mTextSize);
        }

        if(maxLine!=0){
            customEditInput.setMaxLines(maxLine);
        }

        if(mPadding!=0)
            customEditInput.setPadding(mPadding, mPadding, mPadding,mPadding);

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

        if(mPadding!=0){
            customEditInput.setPadding(mPadding, mPadding, mPadding,mPadding);

        }

        if(mTextColor!=0)
            customEditInput.setTextColor(mTextColor);

        if(mTextHeight != -1){
            setTextHeight(mTextHeight);
        }

        if(mTextWidth != -1){
            setTextWidth(mTextWidth);
        }

        if(mHintColor != 0){
            setHintColor(mHintColor);
        }

        setEditable(isEditable);

        if(isLineVisible){
            line.setVisibility(VISIBLE);
        }
        else {
            line.setVisibility(INVISIBLE);
        }
    }


    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.XEditText);
            mText = array.getString(R.styleable.XEditText_text);
            mErrorMsg = array.getString(R.styleable.XEditText_error_msg);
            mHint = array.getString(R.styleable.XEditText_edit_hint);
            mGravity = array.getString(R.styleable.XEditText_gravity);
            mTextSize = array.getInt(R.styleable.XEditText_text_size, 0);
            deleteIconResId = array.getResourceId(R.styleable.CustomEditText_edit_delete, R.drawable.ic_delete);
            maxLength = array.getDimensionPixelSize(R.styleable.CustomEditText_edit_maxLength, 0);
            maxLine = array.getInt(R.styleable.CustomEditText_edit_maxLine, 0);
            mPadding = array.getDimensionPixelSize(R.styleable.XEditText_padding, 0);
            mTextColor = array.getColor(R.styleable.XEditText_text_color, 0);
            isNecessary = array.getBoolean(R.styleable.XEditText_necessary, false);
            isEditable = array.getBoolean(R.styleable.XEditText_editable, true);
            mTextHeight = array.getDimensionPixelSize(R.styleable.XEditText_text_height, -1);
            mTextWidth = array.getDimensionPixelSize(R.styleable.XEditText_text_width, -1);
            mHintColor = array.getColor(R.styleable.XEditText_edit_hint_color, 0);
            isLineVisible = array.getBoolean(R.styleable.XEditText_line_visible, true);
            array.recycle();
        }
    }

    public void setOnContentCheckListener(OnContentCheckListener onContentCheckListener) {
        mOnContentCheckListener = onContentCheckListener;
    }

    public void check(){
        if(mOnContentCheckListener!=null){
            if(!mOnContentCheckListener.isCheckPass(getInput())){
                customCheckInfo.setText(mOnContentCheckListener.createCheckInfo());
                customCheckInfo.setVisibility(VISIBLE);
                line.setBackgroundResource(R.color.customRed);
            }
            else{
                customCheckInfo.setVisibility(GONE);
                line.setBackgroundResource(R.color.customBlue4);
            }


        }
    }

    public EditText editText() {
        return customEditInput;
    }

    public TextView textView(){
        return customEditText;
    }

    public void setNecessary(boolean isNecessary){

        this.isNecessary = isNecessary;

    }

    public void setTextWidth(int width){
        ViewGroup.LayoutParams lp = customEditText.getLayoutParams();
        lp.width = width;
        customEditText.setLayoutParams(lp);

    }

    public void setTextHeight(int height){
        ViewGroup.LayoutParams lp =  customEditText.getLayoutParams();
        lp.height = height;
        customEditText.setLayoutParams(lp);
    }

    public void setInputTextSize(int textSize){
        customEditInput.setTextSize(textSize);
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
                    customEditDelete.setVisibility(View.VISIBLE);
                    if(isNecessary){
                        customCheckInfo.setVisibility(GONE);
                        line.setBackgroundResource(R.color.customBlue4);
                    }

                    if(mContentComparable!= null &&mContentComparable.compareTo(s.toString()) != 1){
                        customCheckInfo.setVisibility(VISIBLE);
                        customCheckInfo.setText(mErrorMsg);
                        line.setBackgroundResource(R.color.customRed);
                    }
                    else{
                        customCheckInfo.setVisibility(GONE);
                        line.setBackgroundResource(R.color.customBlue4);
                    }
                }
                else{
                    customEditDelete.setVisibility(View.INVISIBLE);

                    if(isNecessary){
                        customCheckInfo.setVisibility(VISIBLE);
                        customCheckInfo.setText("必填");
                        line.setBackgroundResource(R.color.customRed);
                    }
                }

                if(mTextListener!=null){
                    mTextListener.onText(s.toString());
                }




            }
        });

        customEditDelete.setOnClickListener(v -> customEditInput.getText().clear());

        customEditInput.setOnTouchListener(this);

    }

    public  void setComparable(Comparable<String> comparable){
        mContentComparable = comparable;
    }

    public void setErrorMsg(String mErrorMsg) {
        this.mErrorMsg = mErrorMsg;

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


    public void setText(String text){
        if(!TextUtils.isEmpty(text)) {
            customEditText.setVisibility(VISIBLE);
            customEditText.setText(text);
        }
    }

    public String getText(){
        return customEditText.getText().toString();
    }

    public void setInput(String input){
        customEditInput.setText(input);
        if(!TextUtils.isEmpty(input))
            customEditInput.setSelection(input.length());
    }

    public void setHint(String hint){
        customEditInput.setHint(hint);
    }

    public String getInput(){
        return customEditInput.getText().toString();
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        customEditInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public void setHintColor(int color){
        customEditInput.setHintTextColor(color);
    }

    public void setInputGravity(int gravity){
        customEditInput.setGravity(gravity);
    }

    public void setInputType(int type){
        customEditInput.setInputType(type);
    }


    public void setTextListener(OnTextListener textListener) {
        mTextListener = textListener;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
        if(isEditable){
            customEditInput.setFocusable(true);
            setNecessary(isNecessary);

        }
        else{
            customEditInput.setFocusable(false);
            customEditInput.setPadding(0,0, DisplayUtil.dip2px(8, getContext()),0);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setEditable(enabled);
    }

    public void setImeOptions(int imeOptions){
        customEditInput.setImeOptions(imeOptions);
    }

}
