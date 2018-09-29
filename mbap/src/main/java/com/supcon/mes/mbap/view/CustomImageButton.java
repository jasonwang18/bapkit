package com.supcon.mes.mbap.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageButton;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.supcon.mes.mbap.R;

/**
 * Created by wangshizhan on 2018/5/8.
 * Email:wangshizhan@supcon.com
 */

public class CustomImageButton extends AppCompatImageButton {

    String mText;
    int mTextColor;
    float mTextSize;

    public CustomImageButton(Context context) {
        super(context);
    }

    public CustomImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(attrs);
    }

    public CustomImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(attrs);
    }


    protected void initAttributeSet(AttributeSet attrs) {

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomImageButton);
            mText= array.getString(R.styleable.CustomImageButton_text);
            mTextSize =  array.getDimensionPixelSize(R.styleable.CustomImageButton_text_size_float, 22);
            mTextColor = array.getColor(R.styleable.CustomImageButton_text_color, getResources().getColor(R.color.white));
            array.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(TextUtils.isEmpty(mText)){
            return;
        }
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(mTextColor);
        paint.setTextSize(mTextSize);
        paint.setAntiAlias(true);
        canvas.drawText(mText, canvas.getWidth()/2, (canvas.getHeight()/2)+12, paint);
    }

    public void setText(String text){
        this.mText = text;
        invalidate();
    }

    public void setColor(int color){
        this.mTextColor = color;
        invalidate();

    }

    public void setTextSize(float textsize){
        this.mTextSize = textsize;
        invalidate();

    }

    public String getText() {
        return mText;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public int getTextColor() {
        return mTextColor;
    }
}
