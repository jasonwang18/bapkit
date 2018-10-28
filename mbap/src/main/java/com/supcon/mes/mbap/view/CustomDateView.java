package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.listener.ICustomView;
import com.supcon.mes.mbap.utils.TextHelper;

import static com.supcon.mes.mbap.MBapConstant.KEY_RADIO;

/**
 * Created by wangshizhan on 2017/8/21.
 * Email:wangshizhan@supcon.com
 */

public class CustomDateView extends BaseLinearLayout implements View.OnClickListener, ICustomView{

    private String  mText, mContent, mKey;
    private float mTextSize, mKeyTextSize, mContentTextSize;
    TextView customDateText;
    TextView customDateInput;
    ImageView customDateIcon;
    ImageView customDeleteIcon;
    private int mTextWidth;

    private String mGravity;
    private int mPadding;
    private int mTextColor;

    private boolean isNecessary, isEditable, isEnable;
    private boolean isBold;

    public CustomDateView(Context context) {
        super(context);
    }

    public CustomDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_date;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Typeface newFont = MBapApp.fontType();
//            customDateText.setTypeface(newFont);
//        }
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

        if(!TextUtils.isEmpty(mKey)) {
            customDateText.setText(mKey);
            customDateText.setVisibility(VISIBLE);
        }


        if(mTextSize!=0) {
            customDateInput.setTextSize(mTextSize);
        }

        if(mKeyTextSize!=0){
            customDateText.setTextSize(mKeyTextSize);
        }

        if(mContentTextSize!= 0){
            customDateInput.setTextSize(mContentTextSize);
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



        if(mTextWidth!=-1)
            setKeyWidth(mTextWidth);
        if(isNecessary)
            setNecessary(isNecessary);
        setEditable(isEditable);

        if(isBold)
            setContentTextStyle(Typeface.BOLD);

        setEnabled(isEnable);
    }

    @Override
    protected void initListener() {
        super.initListener();
//        customDateInput.setOnClickListener(this);
        customDateIcon.setOnClickListener(this);
//        customDateText.setOnClickListener(this);
        customDeleteIcon.setOnClickListener(v -> {
            setContent("");
            onChildViewClick(CustomDateView.this, -1, customDateInput.getText().toString());
        });

    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomDateView);
            mText = array.getString(R.styleable.CustomDateView_text);
            mKey = array.getString(R.styleable.CustomDateView_key);
            mContent = array.getString(R.styleable.CustomDateView_content);
            mTextSize = array.getInt(R.styleable.CustomDateView_text_size, 0);
            mKeyTextSize = array.getInt(R.styleable.CustomDateView_key_size, 0);
            mContentTextSize = array.getInt(R.styleable.CustomDateView_content_size, 0);
            mGravity = array.getString(R.styleable.CustomDateView_gravity);
            mPadding =  array.getDimensionPixelSize(R.styleable.CustomDateView_padding, 0);
            isNecessary = array.getBoolean(R.styleable.CustomDateView_necessary, false);
            isEditable = array.getBoolean(R.styleable.CustomDateView_editable, true);
            mTextWidth =  array.getDimensionPixelSize(R.styleable.CustomDateView_text_width, -1);
            mTextColor = array.getColor(R.styleable.CustomDateView_text_color, 0);
            isBold = array.getBoolean(R.styleable.CustomDateView_bold, false);
            isEnable =  array.getBoolean(R.styleable.CustomDateView_enable, true);
            array.recycle();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        if(!TextUtils.isEmpty(mContent)) {
            setDate(mContent);
        }

        if(mTextColor!=0) {
            setDateColor(mTextColor);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(!enabled)
            setEditable(false);

        if (enabled) {
            customDateText.setAlpha(1);
            customDateInput.setAlpha(1);
        } else {
            customDateText.setAlpha(0.5f);
            customDateInput.setAlpha(0.5f);
        }

    }

    @Override
    public void setEditable(boolean editable) {
        isEditable = editable;
        if(editable){
            customDateInput.setOnClickListener(this);
            customDateText.setTextColor(getResources().getColor(R.color.textColorblack));
            customDateIcon.setVisibility(VISIBLE);
            customDateInput.setTextColor(mTextColor!=0?mTextColor:getResources().getColor(R.color.editableTextColor));
        }
        else {
            customDateIcon.setVisibility(GONE);
            customDateText.setTextColor(getResources().getColor(R.color.notEditableTextColor));
            customDateInput.setOnClickListener(null);
            customDateInput.setTextColor(getResources().getColor(R.color.notEditableTextColor));
        }
    }

    @Override
    public void setNecessary(boolean isNecessary){
        TextHelper.setRequired(isNecessary, customDateText);
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
        return TextUtils.isEmpty(getDate());
    }

    @Override
    public void setInputType(int type) {
        //no use
    }

    @Override
    public void setKey(String key) {
        customDateText.setText(key);
    }

    @Override
    public void setKey(int keyResId) {
        customDateText.setText(keyResId);
    }

    @Override
    public String getKey() {
        return customDateText.getText().toString();
    }

    @Override
    public String getContent() {
        return customDateInput.getText().toString();
    }

    @Override
    public void setContent(String content) {
        customDateInput.setText(content);
        if(TextUtils.isEmpty(content) || !isEditable){
            customDeleteIcon.setVisibility(GONE);
        }
        else {
            customDeleteIcon.setVisibility(VISIBLE);
        }
    }

    @Override
    public void setContent(int contentResId) {
        setDate(getResources().getString(contentResId));
    }

    public void setTextStyle(int textStyle){
        customDateText.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setContentTextStyle(int textStyle){
        customDateInput.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    @Override
    public void setTextFont(Typeface newFont) {
        customDateText.setTypeface(newFont);
        customDateInput.setTypeface(newFont);
    }

    @Override
    public void setKeyTextSize(int textSize) {
        customDateText.setTextSize(textSize);
    }

    @Override
    public void setContentTextSize(int textSize) {
        customDateInput.setTextSize(textSize);
    }

    @Override
    public void setKeyTextColor(int color) {
        customDateText.setTextColor(color);
    }

    @Override
    public void setContentTextColor(int color) {
        customDateInput.setTextColor(color);
    }

    @Override
    public void setContentPadding(int left, int top, int right, int bottom) {
        customDateInput.setPadding(left, top, right, bottom);
    }

    @Override
    public void setKeyTextStyle(int textStyle) {
        customDateText.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    @Override
    public void setEditIcon(int resId) {
        customDateIcon.setImageResource(resId);
    }

    @Override
    public void setClearIcon(int resId) {
        customDeleteIcon.setImageResource(resId);
    }

    @Override
    public void setKeyWidth(int width) {
        ViewGroup.LayoutParams lp = customDateText.getLayoutParams();
        lp.width = width;
        customDateText.setLayoutParams(lp);
    }

    @Override
    public void setKeyHeight(int height) {
        ViewGroup.LayoutParams lp = customDateText.getLayoutParams();
        lp.height = height;
        customDateText.setLayoutParams(lp);

        ViewGroup.LayoutParams lp2 = customDateIcon.getLayoutParams();
        lp2.height = height;
        customDateIcon.setLayoutParams(lp2);
    }

    @Override
    public EditText editText() {
        return null;
    }

    @Override
    public TextView contentView() {
        return customDateInput;
    }

    @Override
    public TextView keyView() {
        return customDateText;
    }

    @Override
    public void setContentGravity(int gravity) {
        customDateInput.setGravity(gravity);
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

    public void setDateTextSize(int textSize){
        customDateInput.setTextSize(textSize);
    }

    public void setDate(String date){
        setContent(date);
    }

    public String getDate(){
        CharSequence result =  customDateInput.getText();
        return TextUtils.isEmpty(result)?"":result.toString().trim();

    }

    @Override
    public void onClick(View v) {
        if(isEditable)
            onChildViewClick(CustomDateView.this, 0, customDateInput.getText().toString());
    }

    public TextView getCustomDateInput() {
        return customDateInput;
    }
}
