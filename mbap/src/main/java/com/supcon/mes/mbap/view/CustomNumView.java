package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConfig;
import com.supcon.mes.mbap.R;
import com.supcon.mes.mbap.listener.OnTextListener;
import com.supcon.mes.mbap.utils.TextHelper;

import java.math.BigDecimal;

/**
 * Created by wangshizhan on 2018/6/28
 * Email:wangshizhan@supcom.com
 */
public class CustomNumView extends BaseLinearLayout {

    ImageView numViewAdd, numViewMinus;
    TextView numViewText;
    AppCompatEditText numViewInput;

    String mText;
    int mTextSize;
    int mTextColor, mTextWidth;
    boolean isEditable, isNecessary,isFloat;
    double mNum;
    float max;
    Drawable addIcon, minusIcon;
    OnTextListener mTextListener;
    int precision;

    public CustomNumView(Context context) {
        super(context);
    }

    public CustomNumView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_num;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            numViewText.setTypeface(newFont);
            numViewInput.setTypeface(newFont);
        }
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomNumView);
            mText = array.getString(R.styleable.CustomNumView_text);
            mNum = array.getInteger(R.styleable.CustomNumView_num, -1);
            max = array.getFloat(R.styleable.CustomNumView_max,  -1);
            mTextSize = array.getInt(R.styleable.CustomNumView_text_size, 0);
            isNecessary = array.getBoolean(R.styleable.CustomNumView_necessary, false);
            isEditable = array.getBoolean(R.styleable.CustomNumView_editable, true);
            mTextColor = array.getColor(R.styleable.CustomNumView_text_color, 0);
            mTextWidth = array.getDimensionPixelSize(R.styleable.CustomNumView_text_width, -1);
            addIcon    = array.getDrawable(R.styleable.CustomNumView_add_icon_res);
            minusIcon  = array.getDrawable(R.styleable.CustomNumView_minus_icon_res);
            isFloat = array.getBoolean(R.styleable.CustomNumView_is_float, false);
            precision = array.getInt(R.styleable.CustomNumView_precision,0);

            array.recycle();
        }

    }

    @Override
    protected void initView() {
        super.initView();
        numViewAdd = findViewById(R.id.numViewAdd);
        numViewMinus = findViewById(R.id.numViewMinus);
        numViewText = findViewById(R.id.numViewText);
        numViewInput = findViewById(R.id.numViewInput);


        if(mNum!=-1){
//            numViewInput.setText(String.valueOf(mNum));
            setNum(mNum);
        }

        if(!TextUtils.isEmpty(mText)){
            numViewText.setText(mText);
        }

        if(mTextSize!=0){
            numViewText.setTextSize(mTextSize);
            numViewInput.setTextSize(mTextSize);
        }

        if(mTextWidth!=0){
            setTextWidth(mTextWidth);
        }

        if(mTextColor!=0){
            numViewText.setTextColor(mTextColor);
            numViewInput.setTextColor(mTextColor);
        }

        if(isNecessary)
            setNecessary(isNecessary);
        setEditable(isEditable);

        if(addIcon!=null){
            numViewAdd.setImageDrawable(addIcon);
        }

        if(minusIcon!=null){
            numViewMinus.setImageDrawable(minusIcon);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();

        numViewAdd.setOnClickListener(v -> {
            if(mNum<max || max == -1)
                mNum = new BigDecimal(mNum).add(new BigDecimal(1)).setScale(precision,BigDecimal.ROUND_HALF_UP).doubleValue();
//                mNum++;
//            numViewInput.setText(String.valueOf(mNum));
            setNum(mNum);
        });

        numViewMinus.setOnClickListener(v -> {
            mNum = new BigDecimal(String.valueOf(mNum)).subtract(new BigDecimal(1)).setScale(precision,BigDecimal.ROUND_HALF_UP).doubleValue();
//            mNum -- ;
            if(mNum < 0){
                mNum = 0;
            }
//            numViewInput.setText(String.valueOf(mNum));
            setNum(mNum);
        });

        numViewInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString()) && isEditable && (s.toString().indexOf(".") != 0)){
                    mNum = Double.valueOf(s.toString());
                }
//                else{
//                    mNum = 0;
//                    setNum(mNum);
//                }

                if(mTextListener!=null){
                    mTextListener.onText(s.toString());
                }
            }
        });
    }

    public void setNum(double mNum) {
//        this.mNum = mNum;
//        numViewInput.setText(String.valueOf(mNum));
        this.mNum = new BigDecimal(mNum).setScale(precision,BigDecimal.ROUND_HALF_UP).doubleValue();
        numViewInput.setText(String.format("%s",new BigDecimal(mNum).setScale(precision,BigDecimal.ROUND_HALF_UP)));
        numViewInput.setSelection(numViewInput.getText().length());
    }

    public double getNum() {
        return mNum;
    }

    public void add(int num){
        mNum += num;
        setNum(mNum);
    }

    public void minus(int num){
        mNum -= num;

        if(mNum < 0){
            mNum = 0;
        }
        setNum(mNum);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setEditable(enabled);
    }


    public void setEditable(boolean editable) {
        isEditable = editable;
        if(!isEditable){
            numViewAdd.setVisibility(INVISIBLE);
            numViewMinus.setVisibility(INVISIBLE);
//            numViewInput.setFocusable(false);
        }
        else{
            numViewAdd.setVisibility(VISIBLE);
            numViewMinus.setVisibility(VISIBLE);
//            numViewInput.setFocusable(true);
        }
    }

    public void setTextWidth(int width){
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) numViewText.getLayoutParams();
        lp.width = width;
        numViewText.setLayoutParams(lp);

    }

    public void setTextStyle(int textStyle){
        numViewText.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setContentTextStyle(int textStyle){
        numViewInput.setTypeface(Typeface.defaultFromStyle(textStyle));
    }

    public void setText(String text){
        numViewText.setText(text);
    }

    public void setText(int resId){
        numViewText.setText(resId);
    }

    public void setTextColor(int color){

        numViewText.setTextColor(color);
    }



    public void setNecessary(boolean isNecessary){

//        if(isNecessary){
//            numViewText.setTextColor(getResources().getColor(R.color.customRed));
//        }
//        else {
//            numViewText.setTextColor(getResources().getColor(MBapConfig.NECESSARY_FALSE_COLOR));
//        }
        TextHelper.setRequired(isNecessary, numViewText);
    }

    public void setTextListener(OnTextListener textListener) {
        mTextListener = textListener;
    }

    public TextView getNumViewInput() {
        return numViewInput;
    }

    public void setPrecision(int precision){
        this.precision = precision;
    }

}
