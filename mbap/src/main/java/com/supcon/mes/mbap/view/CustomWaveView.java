package com.supcon.mes.mbap.view;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.supcon.mes.mbap.R;


/**
 * Created by wangshizhan on 2018/6/29
 * Email:wangshizhan@supcom.com
 */
public class CustomWaveView extends View {

    private int  width = 0;
    private int height = 0;
    private int baseLine = 0;// 基线，用于控制水位上涨的，这里是写死了没动，你可以不断的设置改变。
    private Paint mPaint;
    private TextPaint mTextPaint;

    private int waveHeight = 30;// 波浪的最高度
    private int waveWidth ;//波长
    private float offset = 0f;//偏移量

    private int waveDoration;
    private int waveColor;

    private String text;
    private int textColor;
    private int textSize;

    public CustomWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomWaveView);
            waveHeight = array.getInt(R.styleable.CustomWaveView_wave_height, 30);
            waveColor  = array.getColor(R.styleable.CustomWaveView_wave_color, getResources().getColor(R.color.customBlue4));
            waveDoration = array.getInt(R.styleable.CustomWaveView_wave_duration, 2000);

            text = array.getString(R.styleable.CustomWaveView_wave_text);
            textSize = array.getDimensionPixelSize(R.styleable.CustomWaveView_wave_text_size, getResources().getDimensionPixelSize(R.dimen.fontSize_18sp));
            textColor = array.getColor(R.styleable.CustomWaveView_text_color, getResources().getColor(R.color.white));

            array.recycle();
        }

        initView();
    }

    /**
     * 不断的更新偏移量，并且循环。
     */
    private void updateXControl(){
        //设置一个波长的偏移
        ValueAnimator mAnimator = ValueAnimator.ofFloat(0, waveWidth);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float animatorValue = (float)animation.getAnimatedValue() ;
                offset = animatorValue;//不断的设置偏移量，并重画
                if(isEnabled())
                    postInvalidate();
            }
        });
        mAnimator.setDuration(waveDoration);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(getPath(),mPaint);

        if(!TextUtils.isEmpty(text)){


            Paint.FontMetricsInt fm = mTextPaint.getFontMetricsInt();
            canvas.drawText(text,
                    getWidth() / 2 - mTextPaint.measureText(text) / 2,
                    getHeight() - waveHeight, mTextPaint);
        }
    }
    //初始化paint，没什么可说的。
    private void initView(){
        mPaint = new Paint();
        mPaint.setColor(waveColor);
        mPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTypeface(Typeface.create(new String(), Typeface.BOLD));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();//获取屏幕宽度
        height = getMeasuredHeight();//获取屏幕高度
        waveWidth = width;
        baseLine = height/2;
        updateXControl();
    }

    /**
     * 核心代码，计算path
     * @return
     */
    private Path getPath(){
        int itemWidth = waveWidth/2;//半个波长
        Path mPath = new Path();
        mPath.moveTo(-itemWidth * 3, baseLine);//起始坐标
        //核心的代码就是这里
        for (int i = -3; i < 2; i++) {
            int startX = i * itemWidth;
            mPath.quadTo(
                    startX + itemWidth/2 + offset,//控制点的X,（起始点X + itemWidth/2 + offset)
                    getWaveHeigh( i ),//控制点的Y
                    startX + itemWidth + offset,//结束点的X
                    baseLine//结束点的Y
            );//只需要处理完半个波长，剩下的有for循环自已就添加了。
        }
        //下面这三句话很重要，它是形成了一封闭区间，让曲线以下的面积填充一种颜色，大家可以把这3句话注释了看看效果。
        mPath.lineTo(width,height);
        mPath.lineTo(0,height);
        mPath.close();
        return  mPath;
    }
    //奇数峰值是正的，偶数峰值是负数
    private int getWaveHeigh(int num){
        if(num % 2 == 0){
            return baseLine + waveHeight;
        }
        return baseLine - waveHeight;
    }


    public void setText(String text) {
        this.text = text;
        invalidate();
    }

}

