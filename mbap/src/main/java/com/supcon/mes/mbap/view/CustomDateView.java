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
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConfig;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.utils.TextHelper;

import static com.supcon.mes.mbap.MBapConfig.REQUIRED_MARK;

/**
 * Created by wangshizhan on 2017/8/21.
 * Email:wangshizhan@supcon.com
 */

public class CustomDateView extends BaseLinearLayout implements View.OnClickListener{

    private String  mText, mContent;
    private float mTextSize;
    TextView customDateText;
    TextView customDateInput;
    ImageView customDateIcon;
    private int mTextWidth;

    private String mGravity;
    private int mPadding;
    private int mTextColor;

    private boolean isNecessary, isEditable;
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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customDateText.setTypeface(newFont);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        customDateText = findViewById(R.id.customDateText);
        customDateInput = findViewById(R.id.customDateInput);
        customDateIcon = findViewById(R.id.customDateIcon);

        if(!TextUtils.isEmpty(mText)) {
            customDateText.setText(mText);
            customDateText.setVisibility(VISIBLE);
        }

        if(!TextUtils.isEmpty(mContent)) {
            customDateInput.setText(mContent);
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

        if(mTextColor!=0) {
            customDateText.setTextColor(mTextColor);
            customDateInput.setTextColor(mTextColor);
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
    protected void initListener() {
        super.initListener();
//        customDateInput.setOnClickListener(this);
        customDateIcon.setOnClickListener(this);
//        customDateText.setOnClickListener(this);
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomDateView);
            mText = array.getString(R.styleable.CustomDateView_text);
            mContent = array.getString(R.styleable.CustomDateView_content);
            mTextSize = array.getInt(R.styleable.CustomDateView_text_size, 0);
            mGravity = array.getString(R.styleable.CustomDateView_gravity);
            mPadding =  array.getDimensionPixelSize(R.styleable.CustomDateView_padding, 0);
            isNecessary = array.getBoolean(R.styleable.CustomDateView_necessary, false);
            isEditable = array.getBoolean(R.styleable.CustomDateView_editable, true);
            mTextWidth =  array.getDimensionPixelSize(R.styleable.CustomDateView_text_width, -1);
            mTextColor = array.getColor(R.styleable.CustomDateView_text_color, 0);
            isBold = array.getBoolean(R.styleable.CustomDateView_bold, false);
            array.recycle();
        }
    }


    public void setTextWidth(int width){
        LayoutParams lp = (LayoutParams) customDateText.getLayoutParams();
        lp.width = width;
        customDateText.setLayoutParams(lp);

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


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setEditable(enabled);
    }

    public void setDate(String date){
        customDateInput.setText(date);
    }

    public String getDate(){
        CharSequence result =  customDateInput.getText();
        return TextUtils.isEmpty(result)?"":result.toString().trim();

    }

    public void setDateTextSize(int textSize){
        customDateInput.setTextSize(textSize);
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

    @Override
    public void setOnChildViewClickListener(OnChildViewClickListener onChildViewClickListener) {
        super.setOnChildViewClickListener(onChildViewClickListener);

    }

    public void setNecessary(boolean isNecessary){
        this.isNecessary = isNecessary;
        TextHelper.setRequired(isNecessary, customDateText);
    }

    public void setTextStyle(int textStyle){
        customDateText.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setContentTextStyle(int textStyle){
        customDateInput.setTypeface(Typeface.defaultFromStyle(textStyle));
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
