package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.view.custom.ICustomView;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.constant.ViewAction;
import com.supcon.mes.mbap.utils.EllipsisUtil;
import com.supcon.mes.mbap.utils.TextHelper;


/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */

public class CustomVerticalTextView extends BaseLinearLayout implements View.OnClickListener, ICustomView {


    TextView customKey;
    TextView customValue;
    ImageView customEdit;
    ImageView customDeleteIcon;
    private String mKey, mText, mHint,mValue, mGravity;
    private int mTextSize, mKeyTextSize, mContentTextSize;
    private int mKeyHeight, mKeyWidth, mTextWidth, mTextHeight;
    private int mTextKeyColor, mTextValueColor;
    private boolean isNecessary, isEditable, isEnable;
    private int iconRes ;
    private int maxLines = 0;
    private boolean isBold;
    private boolean isIntercept;


    public CustomVerticalTextView(Context context) {
        super(context);
    }

    public CustomVerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Typeface newFont = MBapApp.fontType();
//            customKey.setTypeface(newFont);
//            customValue.setTypeface(newFont);
//        }
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_vertical_text;
    }

    @Override
    protected void initView() {
        super.initView();
        customKey = findViewById(R.id.customKey);
        customValue = findViewById(R.id.customValue);
        customEdit =findViewById(R.id.customEdit);
        customDeleteIcon = findViewById(R.id.customDeleteIcon);
        if(!TextUtils.isEmpty(mKey)){
            customKey.setText(mKey);
            customKey.setVisibility(View.VISIBLE);
        }

        if(!TextUtils.isEmpty(mText)){
            customKey.setText(mText);
            customKey.setVisibility(View.VISIBLE);
        }

        if(!TextUtils.isEmpty(mHint)){
            customValue.setHint(mHint);
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

        if(mKeyHeight!=-1)
            setKeyHeight(mKeyHeight);

        if(mKeyWidth!=-1){
            setKeyWidth(mKeyWidth);
        }

        if(mTextHeight!=-1){
            setKeyHeight(mTextHeight);
        }

        if(mTextWidth!=-1){
            setKeyWidth(mTextWidth);
        }

        if(isNecessary)
            setNecessary(isNecessary);
        setEditable(isEditable);

        if(iconRes!=0){
            customEdit.setImageResource(iconRes);
        }

        if(maxLines!=0){
            customValue.setMaxLines(maxLines);
        }

        if(!isBold)
            setContentTextStyle(Typeface.NORMAL);

        setEnabled(isEnable);
    }


    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomVerticalTextView);
            mKey=               array.getString(R.styleable.CustomVerticalTextView_key);
            mText =             array.getString(R.styleable.CustomVerticalTextView_text);
            mHint=             array.getString(R.styleable.CustomVerticalTextView_content_hint);
            mValue=             array.getString(R.styleable.CustomVerticalTextView_content_value);
            mGravity =          array.getString(R.styleable.CustomVerticalTextView_gravity);
            mTextSize =         array.getInt(R.styleable.CustomVerticalTextView_text_size, 0);
            mKeyTextSize =      array.getInt(R.styleable.CustomVerticalTextView_key_size, 0);
            mContentTextSize =  array.getInt(R.styleable.CustomVerticalTextView_content_size, 0);
            mTextKeyColor =     array.getColor(R.styleable.CustomVerticalTextView_key_color, 0);
            mTextValueColor =   array.getColor(R.styleable.CustomVerticalTextView_content_color, 0);
            mKeyWidth =         array.getDimensionPixelSize(R.styleable.CustomVerticalTextView_key_width, -1);
            mKeyHeight =        array.getDimensionPixelSize(R.styleable.CustomVerticalTextView_key_height, -1);
            mTextWidth =        array.getDimensionPixelSize(R.styleable.CustomVerticalTextView_text_width, -1);
            mTextHeight=        array.getDimensionPixelSize(R.styleable.CustomVerticalTextView_text_height, -1);
            isNecessary =       array.getBoolean(R.styleable.CustomVerticalTextView_necessary, false);
            isEditable =        array.getBoolean(R.styleable.CustomVerticalTextView_editable, false);
            iconRes =           array.getResourceId(R.styleable.CustomVerticalTextView_icon_res, 0);
            maxLines =          array.getInt(R.styleable.CustomVerticalTextView_max_lines, 0);
            isBold =            array.getBoolean(R.styleable.CustomVerticalTextView_bold, true);
            isEnable =          array.getBoolean(R.styleable.CustomVerticalTextView_enable, true);
            array.recycle();
        }
    }


    @Override
    protected void initListener() {
        super.initListener();
        customEdit.setOnClickListener(this);
        customValue.setOnClickListener(this);
        customValue.setOnLongClickListener(v -> {
            CustomContentTextDialog.showContent(getContext(), customValue.getText().toString());
            return true;
        });

        customDeleteIcon.setOnClickListener(v -> {
            setContent("");
            onChildViewClick(CustomVerticalTextView.this, ViewAction.CONTENT_CLEAN.value(), "");

        });

    }

    @Override
    protected void initData() {
        super.initData();
        if(!TextUtils.isEmpty(mValue)){
//            customValue.setText(mValue);
            setContent(mValue);
        }

        if(mTextKeyColor!= 0){
            customKey.setTextColor(mTextKeyColor);
        }

        if(mTextValueColor!= 0){
            customValue.setTextColor(mTextValueColor);
        }


        if(mTextSize!=0){
            setValueTextSize(mTextSize);
        }

        if(mKeyTextSize != 0){
//            customKey.setTextSize(mTextSize);
            setKeyTextSize(mKeyTextSize);
        }

        if(mContentTextSize != 0)
            setContentTextSize(mContentTextSize);
    }

    @Override
    public void setKey(int keyResId) {
        customKey.setText(keyResId);
    }

    @Override
    public void setKey(String text){
        customKey.setText(text);
    }

    @Override
    public String getKey() {
        return customKey.getText().toString().trim();
    }

    @Override
    public String getContent() {
        return customValue.getText().toString();
    }

    @Override
    public void setContent(String content) {
        customValue.setText(content);
        if(TextUtils.isEmpty(content) || !isEditable){
            customDeleteIcon.setVisibility(GONE);
        }
        else {
            customDeleteIcon.setVisibility(VISIBLE);
        }
    }

    @Override
    public void setContent(int contentResId) {
        setValue(getResources().getString(contentResId));
    }

    @Override
    public void setKeyWidth(int width){
        ViewGroup.LayoutParams lp = customKey.getLayoutParams();
        lp.width = width;
        customKey.setLayoutParams(lp);
    }

    @Override
    public void setKeyHeight(int height){
        ViewGroup.LayoutParams lp = customKey.getLayoutParams();
        lp.height = height;
        customKey.setLayoutParams(lp);

        if(height == 0){
            customEdit.setVisibility(GONE);
        }
        else {
            ViewGroup.LayoutParams lp2 = customEdit.getLayoutParams();
            lp2.height = height;
            customEdit.setLayoutParams(lp2);
        }
    }

    @Override
    public EditText editText() {
        return null;
    }

    @Override
    public TextView contentView() {
        return customValue;
    }

    @Override
    public TextView keyView() {
        return customKey;
    }

    @Override
    public void setContentGravity(int gravity) {
        customValue.setGravity(gravity);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if(!enabled)
            setEditable(false);

        if (enabled) {
            customKey.setAlpha(1);
            customValue.setAlpha(1);
        } else {
            customKey.setAlpha(0.5f);
            customValue.setAlpha(0.5f);
        }
    }

    @Override
    public void setEditable(boolean isEditable){
        this.isEditable = isEditable;
        if(isEditable){
            customEdit.setVisibility(VISIBLE);
            customValue.setOnClickListener(this);
//            customKey.setTextColor(mTextKeyColor!=0?mTextKeyColor:getResources().getColor(R.color.textColorblack));
//            customValue.setTextColor(mTextValueColor!=0?mTextValueColor:getResources().getColor(R.color.editableTextColor));
        }
        else{
            customEdit.setVisibility(GONE);
            customValue.setOnClickListener(null);
//            customKey.setTextColor(mTextKeyColor!=0?mTextKeyColor:getResources().getColor(R.color.notEditableTextColor));
//            customValue.setTextColor(mTextValueColor!=0?mTextValueColor:getResources().getColor(R.color.notEditableTextColor));
        }
    }

    @Override
    public void setIntercept(boolean isIntercept) {
        this.isIntercept = isIntercept;
    }

    @Override
    public boolean isIntercept() {
        return isIntercept;
    }

    @Override
    public boolean isEmpty() {
        return TextUtils.isEmpty(getContent());
    }

    @Override
    public void setNecessary(boolean isNecessary){
        TextHelper.setRequired(isNecessary, customKey);
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
    public void setInputType(int type) {
        //no use
    }

    public void setTextStyle(int textStyle){
        customKey.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setContentTextStyle(int textStyle){
        customValue.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    @Override
    public void setTextFont(Typeface newFont) {
        customKey.setTypeface(newFont);
        customValue.setTypeface(newFont);
    }

    @Override
    public void setKeyTextSize(int textSize) {
        customKey.setTextSize(textSize);
    }

    @Override
    public void setContentTextSize(int textSize) {
        customValue.setTextSize(textSize);
    }

    @Override
    public void setKeyTextColor(int color) {
        customKey.setTextColor(color);
    }

    @Override
    public void setContentTextColor(int color) {
        customValue.setTextColor(color);
    }

    @Override
    public void setContentPadding(int left, int top, int right, int bottom) {
        customValue.setPadding(left, top, right, bottom);
    }

    @Override
    public void setKeyTextStyle(int textStyle) {
        customKey.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    @Override
    public void setEditIcon(int resId) {
        customEdit.setImageResource(resId);
    }

    @Override
    public void setClearIcon(int resId) {
        customDeleteIcon.setImageResource(resId);
    }

    public void setIconRes(int iconRes){
        if(iconRes!=0){
            customEdit.setImageResource(iconRes);
        }
    }

    public void setValueTextSize(int textSize){
        customValue.setTextSize(textSize);
    }

    @Override
    public void onClick(View v) {

        if(isEditable)
            onChildViewClick(this, 0);

    }

    public TextView getCustomValue() {
        return customValue;
    }

    public void setValue(String value){
        setContent(value);
    }

    public void setKeyColor(int color){
        customKey.setTextColor(color);
    }

    public void setValueColor(int color){
        customValue.setTextColor(color);
    }

    public String getValue() {
        return customValue.getText().toString().trim();
    }

    public void setInputGravity(int gravity){
        customValue.setGravity(gravity);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!isEditable()&& !EllipsisUtil.isEllipsisEnable(customValue)) {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }
}
