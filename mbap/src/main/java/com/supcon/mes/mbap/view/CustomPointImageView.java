package com.supcon.mes.mbap.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;

/**
 * Created by wangshizhan on 2017/10/17.
 * Email:wangshizhan@supcon.com
 */

public class CustomPointImageView extends BaseLinearLayout {

    TextView customHWPoint;

    ImageView customHWIcon;

    TextView customHWNum;


    public CustomPointImageView(Context context) {
        super(context);
    }

    public CustomPointImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customHWPoint.setTypeface(newFont);
            customHWNum.setTypeface(newFont);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_point_imageview;
    }

    @Override
    protected void initView() {
        super.initView();
        customHWPoint = findViewById(R.id.customHWPoint);
        customHWIcon = findViewById(R.id.customHWIcon);
        customHWNum = findViewById(R.id.customHWNum);
    }

    public void showRedPoint(){

        customHWPoint.setVisibility(View.VISIBLE);
    }

    public void hideRedPoint(){

        customHWPoint.setVisibility(View.GONE);

    }

    public void setIcon(int resId){
        customHWIcon.setImageResource(resId);
    }

    public void showNum(int num){

        if(num > 99){
            num = 99;
        }

        if(customHWNum.getVisibility() != View.VISIBLE){
            customHWNum.setVisibility(View.VISIBLE);
        }

        startPropertyAnim(num);

    }

    public void hideNum(){

        customHWNum.setVisibility(View.GONE);
    }

    // 动画实际执行
    private void startPropertyAnim(int num) {

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                2f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                2f, 1f);
//        ObjectAnimator.ofPropertyValuesHolder(customHWNum, pvhX, pvhY,pvhZ).setDuration(1000).start();

        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(customHWNum, pvhX, pvhY,pvhZ).setDuration(500);
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float value = (Float) animation.getAnimatedValue();
//            }
//
//        });

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                customHWNum.setText(String.valueOf(num));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

}
