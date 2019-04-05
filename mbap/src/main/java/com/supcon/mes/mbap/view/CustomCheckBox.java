package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConfig;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.utils.TextHelper;

import org.w3c.dom.Text;

/**
 * Created by wangshizhan on 2017/8/21.
 * Email:wangshizhan@supcon.com
 */

public class CustomCheckBox extends BaseLinearLayout implements View.OnClickListener{

    private String mText;

    private int iconResId;
    private int mTextWidth;
    private boolean isNecessary;
    TextView customCheckBoxText;
    ImageView customCheckBoxIcon;

    private boolean isEnable = true;
    private boolean isChecked = false;

    private OnCheckedListener mOnCheckedListener;

    public CustomCheckBox(Context context) {
        super(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_checkbox;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customCheckBoxText.setTypeface(newFont);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        customCheckBoxText = findViewById(R.id.customCheckBoxText);
        customCheckBoxIcon = findViewById(R.id.customCheckBoxIcon);

        if(!TextUtils.isEmpty(mText)){
            customCheckBoxText.setText(mText);
            customCheckBoxText.setVisibility(View.VISIBLE);
        }
        else{
            customCheckBoxText.setVisibility(View.GONE);
        }


        if(mTextWidth!=0){
            setTextWidth(mTextWidth);
        }

        if(isNecessary)
            setNecessary(isNecessary);

        setEnable(isEnable);

    }

    public void setTextWidth(int width){
        LayoutParams lp = (LayoutParams) customCheckBoxText.getLayoutParams();
        lp.width = width;
        customCheckBoxText.setLayoutParams(lp);

    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomCheckBox);
            mText = array.getString(R.styleable.CustomCheckBox_text);
            mTextWidth = (int) array.getDimension(R.styleable.CustomCheckBox_text_width, 0);
            isNecessary = array.getBoolean(R.styleable.CustomCheckBox_necessary, false);
            isChecked = array.getBoolean(R.styleable.CustomCheckBox_checkbox_checked, false);
            isEnable = array.getBoolean(R.styleable.CustomCheckBox_checkbox_enable, true);
            iconResId = array.getResourceId(R.styleable.CustomCheckBox_checkbox_icon, R.drawable.ic_choose_no);
            array.recycle();
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        customCheckBoxText.setOnClickListener(this);
        customCheckBoxIcon.setOnClickListener(this);
    }

    public void setOnCheckedListener(OnCheckedListener onCheckedListener) {
        mOnCheckedListener = onCheckedListener;
    }

    public void setText(String text){
        customCheckBoxText.setText(text);
        if(customCheckBoxText.getVisibility() != VISIBLE){
            customCheckBoxText.setVisibility(View.VISIBLE);
        }
    }

    public void setText(int resId){
        customCheckBoxText.setText(resId);
        if(customCheckBoxText.getVisibility() != VISIBLE){
            customCheckBoxText.setVisibility(View.VISIBLE);
        }
    }

    public void setTextColor(int color){

        customCheckBoxText.setTextColor(color);

    }


    public void setEnable(boolean enable) {
        isEnable = enable;
        if(!isEnable){
            customCheckBoxIcon.setImageAlpha(100);
            customCheckBoxText.setAlpha(0.5f);
        }
        else{
            customCheckBoxIcon.setImageAlpha(255);
            customCheckBoxText.setAlpha(1);
        }
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        if(!isChecked){
            customCheckBoxIcon.setImageResource(R.drawable.ic_choose_no);

        }
        else{
            customCheckBoxIcon.setImageResource(R.drawable.ic_choose_yes);
        }

    }

    public void setNecessary(boolean isNecessary){

//        if(isNecessary){
//            customCheckBoxText.setTextColor(getResources().getColor(R.color.customRed));
//        }
//        else{
//            customCheckBoxText.setTextColor(getResources().getColor(MBapConfig.NECESSARY_FALSE_COLOR));
//        }
        TextHelper.setRequired(isNecessary, customCheckBoxText);
    }

    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void onClick(View v) {

        if(!isEnable){
            LogUtil.w("CustomCheckBox is not enable!");
            return;
        }

        setChecked(!isChecked);
        if(mOnCheckedListener!=null)
            mOnCheckedListener.onChecked(isChecked);

    }

    public interface OnCheckedListener{

        void onChecked(boolean isChecked);

    }

}
