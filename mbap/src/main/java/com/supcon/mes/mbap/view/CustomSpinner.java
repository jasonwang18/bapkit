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
import com.supcon.mes.mbap.utils.TextHelper;


/**
 * Created by wangshizhan on 2017/8/21.
 * Email:wangshizhan@supcon.com
 */

public class CustomSpinner extends BaseLinearLayout implements View.OnClickListener, ICustomView {

    private String mText, mHint, mContent, mKey;

    private float mTextSize, mKeyTextSize, mContentTextSize;
    private String mGravity;
    private int mPadding;
    private int mTextWidth;
    TextView customSpinnerText;
    TextView customSpinner;
    ImageView customSpinnerIcon;
    ImageView customDeleteIcon;

    private boolean isNecessary, isEditable, isEnable;
    private int mTextColor;
    private boolean isBold;
    private boolean isIntercept;

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
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Typeface newFont = MBapApp.fontType();
//            customSpinnerText.setTypeface(newFont);
//            customSpinner.setTypeface(newFont);
//        }

    }

    @Override
    protected void initView() {
        super.initView();
        customSpinnerText = findViewById(R.id.customSpinnerText);
        customSpinner = findViewById(R.id.customSpinner);
        customSpinnerIcon = findViewById(R.id.customSpinnerIcon);
        customDeleteIcon = findViewById(R.id.customDeleteIcon);
        if (!TextUtils.isEmpty(mText)) {
            customSpinnerText.setText(mText);
            customSpinnerText.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(mKey)) {
            customSpinnerText.setText(mKey);
            customSpinnerText.setVisibility(View.VISIBLE);
        }

        if (mTextSize != 0) {
            customSpinner.setTextSize(mTextSize);
        }

        if (mKeyTextSize != 0) {
            customSpinnerText.setTextSize(mKeyTextSize);
        }

        if (mContentTextSize != 0) {
            customSpinner.setTextSize(mContentTextSize);
        }

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

            customSpinner.setGravity(gravity);
        }

        if (mPadding != 0) {
            setSpinnerPadding(mPadding);
        }


        if (mTextWidth != -1)
            setKeyWidth(mTextWidth);

        if (isNecessary)
            setNecessary(isNecessary);
        setEditable(isEditable);

        if (!isBold)
            setContentTextStyle(Typeface.NORMAL);

        setEnabled(isEnable);
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomSpinner);
            mText = array.getString(R.styleable.CustomSpinner_text);
            mKey = array.getString(R.styleable.CustomSpinner_key);
            mHint = array.getString(R.styleable.CustomSpinner_content_hint);
            mContent = array.getString(R.styleable.CustomSpinner_content_value);
            mTextSize = array.getInt(R.styleable.CustomSpinner_text_size, 0);
            mKeyTextSize = array.getInt(R.styleable.CustomSpinner_key_size, 0);
            mContentTextSize = array.getInt(R.styleable.CustomSpinner_content_size, 0);
            mGravity = array.getString(R.styleable.CustomSpinner_gravity);
            mPadding = array.getDimensionPixelSize(R.styleable.CustomSpinner_padding, 0);
            isNecessary = array.getBoolean(R.styleable.CustomSpinner_necessary, false);
            isEditable = array.getBoolean(R.styleable.CustomSpinner_editable, true);
            mTextColor = array.getColor(R.styleable.CustomSpinner_text_color, 0);
            mTextWidth = array.getDimensionPixelSize(R.styleable.CustomSpinner_text_width, -1);
            isBold = array.getBoolean(R.styleable.CustomSpinner_bold, true);
            isEnable = array.getBoolean(R.styleable.CustomSpinner_enable, true);
            array.recycle();
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
//        customSpinner.setOnClickListener(this);
        customSpinnerIcon.setOnClickListener(this);
//        customSpinnerText.setOnClickListener(this);
//        customSpinner.setOnLongClickListener(v -> {
//            CustomContentTextDialog.showContent(getContext(), customSpinner.getText().toString());
//            return true;
//        });

        customDeleteIcon.setOnClickListener(v -> {
            setSpinner("");
            onChildViewClick(CustomSpinner.this, ViewAction.CONTENT_CLEAN.value(), "");
        });
    }

    @Override
    protected void initData() {
        super.initData();

        if (!TextUtils.isEmpty(mHint)) {
            customSpinner.setHint(mHint);
        }

        if (!TextUtils.isEmpty(mContent)) {
//            customSpinner.setText(mContent);
            setContent(mContent);
        }

        if (mTextColor != 0) {
//            customSpinnerText.setTextColor(mTextColor);
            customSpinner.setTextColor(mTextColor);
        }
    }

    public void setKeyTextStyle(int textStyle) {
        customSpinnerText.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    @Override
    public void setEditIcon(int resId) {
        customSpinnerIcon.setImageResource(resId);
    }

    @Override
    public void setClearIcon(int resId) {
        customDeleteIcon.setImageResource(resId);
    }


    @Override
    public EditText editText() {
        return null;
    }

    @Override
    public TextView contentView() {
        return customSpinner;
    }

    @Override
    public TextView keyView() {
        return customSpinnerText;
    }

    @Override
    public void setContentGravity(int gravity) {
        customSpinner.setGravity(gravity);
    }

    @Override
    public void setContentTextStyle(int textStyle) {
        customSpinner.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    @Override
    public void setKeyWidth(int width) {
        ViewGroup.LayoutParams lp = customSpinnerText.getLayoutParams();
        lp.width = width;
        customSpinnerText.setLayoutParams(lp);

    }

    @Override
    public void setKeyHeight(int height) {
        ViewGroup.LayoutParams lp = customSpinnerText.getLayoutParams();
        lp.height = height;
        customSpinnerText.setLayoutParams(lp);


        ViewGroup.LayoutParams lp2 = customSpinnerIcon.getLayoutParams();
        lp2.height = height;
        customSpinnerIcon.setLayoutParams(lp2);
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled)
            setEditable(false);

        if (enabled) {
            customSpinner.setAlpha(1);
            customSpinnerText.setAlpha(1);
        } else {
            customSpinnerText.setAlpha(0.5f);
            customSpinner.setAlpha(0.5f);
        }
    }

    @Override
    public void setEditable(boolean editable) {
        isEditable = editable;
        if (!editable) {
            customSpinnerIcon.setVisibility(View.GONE);
//            customSpinnerText.setTextColor(getResources().getColor(R.color.notEditableTextColor));
            customSpinner.setOnClickListener(this);
//            customSpinner.setTextColor(getResources().getColor(R.color.notEditableTextColor));
        } else {
            customSpinnerIcon.setVisibility(View.VISIBLE);
//            customSpinnerText.setTextColor(getResources().getColor(R.color.textColorblack));
            customSpinner.setOnClickListener(null);
//            customSpinner.setTextColor(mTextColor!=0?mTextColor:getResources().getColor(R.color.editableTextColor));
        }

    }

    @Override
    public void setNecessary(boolean isNecessary) {
        TextHelper.setRequired(isNecessary, customSpinnerText);
    }

    @Override
    public void setIntercept(boolean isIntercept) {
        this.isIntercept = isIntercept;
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
        return TextUtils.isEmpty(getContent());
    }

    @Override
    public boolean isIntercept() {
        return isIntercept;
    }

    @Override
    public void setInputType(int type) {
        customSpinner.setInputType(type);
    }

    @Override
    public void setKey(String key) {
        customSpinnerText.setText(key);
    }

    @Override
    public void setKey(int keyResId) {
        customSpinnerText.setText(keyResId);
    }

    @Override
    public String getKey() {
        return customSpinnerText.getText().toString();
    }

    @Override
    public String getContent() {
        return customSpinner.getText().toString();
    }

    @Override
    public void setContent(String content) {
        customSpinner.setText(content);
        if (TextUtils.isEmpty(content) || !isEditable) {
            customDeleteIcon.setVisibility(GONE);
        } else {
            customDeleteIcon.setVisibility(VISIBLE);
        }
    }

    @Override
    public void setContent(int contentResId) {
        setContent(getResources().getString(contentResId));
    }

    @Override
    public void setTextFont(Typeface newFont) {
        customSpinnerText.setTypeface(newFont);
        customSpinner.setTypeface(newFont);
    }

    @Override
    public void setKeyTextSize(int textSize) {
        customSpinnerText.setTextSize(textSize);
    }

    @Override
    public void setContentTextSize(int textSize) {
        customSpinner.setTextSize(textSize);
    }

    @Override
    public void setKeyTextColor(int color) {
        customSpinnerText.setTextColor(color);
    }

    @Override
    public void setContentTextColor(int color) {
        customSpinner.setTextColor(color);
    }

    @Override
    public void setContentPadding(int left, int top, int right, int bottom) {
        customSpinner.setPadding(left, top, right, bottom);
    }

    public void setText(String text) {
        setKey(text);
    }

    public void setText(int resId) {
        setKey(resId);
    }

    public void setSpinner(String value) {
        setContent(value);
    }

    public void setSpinnerTextSize(int textSize) {
        setKeyTextSize(textSize);
    }

    public String getSpinnerValue() {
        return getContent();
    }


    public void setSpinnerPadding(int padding) {
        setContentPadding(padding, 0, 0, 0);
    }

    public void setTextColor(int color) {
        setKeyTextColor(color);
    }

    @Override
    public void onClick(View v) {
        if (isEditable)
            onChildViewClick(CustomSpinner.this, 0, null);
    }

    public TextView getCustomSpinner() {
        return contentView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isEditable()) {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }
}
