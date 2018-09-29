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
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConfig;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.utils.TextHelper;

/**
 * Created by wangshizhan on 2017/9/20.
 * Email:wangshizhan@supcon.com
 */

public class CustomVerticalTextView extends BaseLinearLayout implements View.OnClickListener{


    TextView customKey;
    TextView customValue;
    ImageView customEdit;

    private String mKey, mValue, mGravity;
    private int mTextSize;
    private int mKeyHeight, mKeyWidth;
    private int mTextKeyColor, mTextValueColor;
    private boolean isNecessary, isEditable;
    private int iconRes ;
    private int maxLines = 0;
    private boolean isBold;


    public CustomVerticalTextView(Context context) {
        super(context);
    }

    public CustomVerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customKey.setTypeface(newFont);
            customValue.setTypeface(newFont);
        }
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

        if(!TextUtils.isEmpty(mKey)){
            customKey.setText(mKey);
            customKey.setVisibility(View.VISIBLE);
        }

        if(!TextUtils.isEmpty(mValue)){
            customValue.setText(mValue);
        }

        if(mTextSize != 0){
//            customKey.setTextSize(mTextSize);
            customValue.setTextSize(mTextSize);
        }

        if(mTextKeyColor!= 0){
            customKey.setTextColor(mTextKeyColor);
        }

        if(mTextValueColor!= 0){
            customValue.setTextColor(mTextValueColor);
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

        if(isNecessary)
            setNecessary(isNecessary);
        setEditable(isEditable);

        if(iconRes!=0){
            customEdit.setImageResource(iconRes);
        }

        if(maxLines!=0){
            customValue.setMaxLines(maxLines);
        }

        if(isBold)
            setContentTextStyle(Typeface.BOLD);
    }


    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomVerticalTextView);
            mKey= array.getString(R.styleable.CustomVerticalTextView_text_key);
            mValue= array.getString(R.styleable.CustomVerticalTextView_text_value);
            mGravity = array.getString(R.styleable.CustomVerticalTextView_gravity);
            mTextSize = array.getInt(R.styleable.CustomVerticalTextView_text_size, 0);
            mTextKeyColor = array.getColor(R.styleable.CustomVerticalTextView_text_key_color, 0);
            mTextValueColor = array.getColor(R.styleable.CustomVerticalTextView_text_value_color, 0);
            mKeyWidth = array.getDimensionPixelSize(R.styleable.CustomVerticalTextView_text_key_width, -1);
            mKeyHeight =  array.getDimensionPixelSize(R.styleable.CustomVerticalTextView_text_key_height, -1);
            isNecessary = array.getBoolean(R.styleable.CustomVerticalTextView_necessary, false);
            isEditable = array.getBoolean(R.styleable.CustomVerticalTextView_editable, false);
            iconRes = array.getResourceId(R.styleable.CustomVerticalTextView_icon_res, 0);
            maxLines = array.getInt(R.styleable.CustomVerticalTextView_max_lines, 0);
            isBold = array.getBoolean(R.styleable.CustomVerticalTextView_bold, false);
            array.recycle();
        }
    }


    @Override
    protected void initListener() {
        super.initListener();

//        customKey.setOnClickListener(this);

//        customValue.setOnClickListener(this);
        customEdit.setOnClickListener(this);
        customValue.setOnLongClickListener(v -> {
            CustomContentTextDialog.showContent(getContext(), customValue.getText().toString());
            return true;
        });
    }


    public void setInputGravity(int gravity){
        customValue.setGravity(gravity);
    }

    public void setKey(String text){
        customKey.setText(text);
    }

    public void setValue(String text){
        customValue.setText(text);
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

    public String getKey() {
        return customKey.getText().toString().trim();
    }

    public void setKeyWidth(int width){
        ViewGroup.LayoutParams lp = customKey.getLayoutParams();
        lp.width = width;
        customKey.setLayoutParams(lp);

    }

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


    public void setIconRes(int iconRes){
        if(iconRes!=0){
            customEdit.setImageResource(iconRes);
        }
    }

    public void setValueTextSize(int textSize){
        customValue.setTextSize(textSize);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setEditable(enabled);
    }

    public void setEditable(boolean isEditable){
        this.isEditable = isEditable;
        if(isEditable){
            customEdit.setVisibility(VISIBLE);
            customValue.setOnClickListener(this);
        }
        else{
            customEdit.setVisibility(GONE);
            customValue.setOnClickListener(null);
        }

    }


    public void setNecessary(boolean isNecessary){

//        if(isNecessary){
//            customKey.setTextColor(getResources().getColor(R.color.customRed));
//        }
//        else {
//            customKey.setTextColor(getResources().getColor(MBapConfig.NECESSARY_FALSE_COLOR));
//        }
        TextHelper.setRequired(isNecessary, customKey);
    }

    public void setTextStyle(int textStyle){
        customKey.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setContentTextStyle(int textStyle){
        customValue.setTypeface(Typeface.defaultFromStyle(textStyle));
    }


    @Override
    public void onClick(View v) {

        if(isEditable)
            onChildViewClick(this, 0);

    }

    public TextView getCustomValue() {
        return customValue;
    }
}
