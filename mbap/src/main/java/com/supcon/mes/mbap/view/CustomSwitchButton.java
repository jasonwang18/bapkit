package com.supcon.mes.mbap.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.R;

/**
 * Created by wangshizhan on 2017/8/21.
 * Email:wangshizhan@supcon.com
 */

public class CustomSwitchButton extends BaseLinearLayout implements View.OnClickListener{


    public static final int SWITCH_ON = 1;
    public static final int SWITCH_OFF = 0;

    private int switchStatus = 0, oldSwitchStatus = -1;

    TextView customSwitchOn;
    TextView customSwitchOff;
    TextView customSwitchBg;
    LinearLayout customSwitchLayout;
    RelativeLayout customSwitchAllLayout;

    private boolean isSwitchOn = true;

    private boolean isEnable = true;
    private String[] values;

    private OnSwitchListener mOnSwitchListener;

    private final int ANIMATION_DURATION = 300;

    private Drawable onBg, offBg;
    private int onColor, offColor;


    public CustomSwitchButton(Context context) {
        super(context);
    }

    public CustomSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom_switchbtn;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = MBapApp.fontType();
            customSwitchOn.setTypeface(newFont);
            customSwitchOff.setTypeface(newFont);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        customSwitchOn = findViewById(R.id.customSwitchOn);
        customSwitchOff = findViewById(R.id.customSwitchOff);
        customSwitchBg = findViewById(R.id.customSwitchBg);
        customSwitchLayout = findViewById(R.id.customSwitchLayout);
        customSwitchAllLayout = findViewById(R.id.customSwitchAllLayout);

        if(SWITCH_ON == switchStatus){
            isSwitchOn = true;
            switchStatus = SWITCH_ON;
        }
        else if(SWITCH_OFF == switchStatus){
            isSwitchOn = false;
            switchStatus = SWITCH_OFF;
        }

        if(values!=null && values.length == 2) {
            customSwitchOn.setText(values[0]);
            customSwitchOff.setText(values[1]);
        }
        updateView();
    }

    private void updateView() {


        if(oldSwitchStatus ==switchStatus){
            //no need to switch
            return;
        }

        if(isSwitchOn){
            customSwitchOn.setTextColor(getResources().getColor(R.color.deviceWhite));
            customSwitchOff.setTextColor(Color.LTGRAY);
            customSwitchBg.setX(DisplayUtil.dip2px(0, getContext())+customSwitchLayout.getX());
            customSwitchBg.setBackground(getResources().getDrawable(R.drawable.sh_switchon_orange));
            customSwitchAllLayout.setBackgroundResource(R.drawable.sh_switch_on);
        }
        else{
            customSwitchOn.setTextColor(Color.LTGRAY);
            customSwitchOff.setTextColor(getResources().getColor(R.color.deviceWhite));
            customSwitchBg.setX(DisplayUtil.dip2px(40, getContext())+customSwitchLayout.getX());
            customSwitchBg.setBackground(getResources().getDrawable(R.drawable.sh_switchoff_gray));
            customSwitchAllLayout.setBackgroundResource(R.drawable.sh_switch_off);
        }



        oldSwitchStatus = switchStatus;
    }

    public void setOnSwitchListener(OnSwitchListener onSwitchListener) {
        mOnSwitchListener = onSwitchListener;
    }

    private void startOffAnimation() {


//        ValueAnimator colorAnim = ObjectAnimator.ofInt(customSwitchBg,
//                "backgroundColor",
//                getResources().getColor(R.color.bgSwitchOn),
//                getResources().getColor(R.color.bgSwitchOff));
//        colorAnim.setDuration(ANIMATION_DURATION);
//        colorAnim.setEvaluator(new ArgbEvaluator());
//        colorAnim.setRepeatMode(ValueAnimator.REVERSE);

        ValueAnimator transAnim = ObjectAnimator.ofFloat(customSwitchBg, "x",
                customSwitchOn.getX()+customSwitchLayout.getX(), customSwitchOff.getX()+customSwitchLayout.getX());
        transAnim.setDuration(ANIMATION_DURATION);
        transAnim.setRepeatMode(ValueAnimator.REVERSE);
        transAnim.setInterpolator(new DecelerateInterpolator());
//        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//
//                int c = (int) animation.getAnimatedValue();
//
//                if(c == getResources().getColor(R.color.bgSwitchOff)){
//                    updateView();
//                }
//
//            }
//        });

//        colorAnim.start();
//        LogUtil.i("customSwitchBg:"+ customSwitchBg.getX()+" customSwitchLayout:"+customSwitchLayout.getX());

//        LogUtil.i("customSwitchOn:"+ customSwitchOn.getX()+" customSwitchOff:"+customSwitchOff.getX());

        transAnim.addUpdateListener(animation -> {
            float c = (float) animation.getAnimatedValue();

            if(c == customSwitchOff.getX()+customSwitchLayout.getX()){
                updateView();
            }
        });
        transAnim.start();

    }

    private void startOnAnimation() {

//        ValueAnimator colorAnim = ObjectAnimator.ofInt(customSwitchBg,
//                "backgroundColor",
//                getResources().getColor(R.color.bgSwitchOff),
//                getResources().getColor(R.color.bgSwitchOn));
//        colorAnim.setDuration(ANIMATION_DURATION);
//        colorAnim.setEvaluator(new ArgbEvaluator());
//        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
//        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//
//                int c = (int) animation.getAnimatedValue();
//
//                if(c == getResources().getColor(R.color.bgSwitchOn)){
//                    updateView();
//                }
//            }
//        });

        ValueAnimator transAnim = ObjectAnimator.ofFloat(customSwitchBg, "x",
                customSwitchOff.getX()+customSwitchLayout.getX(), customSwitchOn.getX()+customSwitchLayout.getX());
        transAnim.setDuration(ANIMATION_DURATION);
        transAnim.setRepeatMode(ValueAnimator.REVERSE);
        transAnim.setInterpolator(new DecelerateInterpolator());

//        LogUtil.i("customSwitchBg:"+ customSwitchBg.getX()+" customSwitchLayout:"+customSwitchLayout.getX());

//        LogUtil.i("customSwitchOn:"+ customSwitchOn.getX()+" customSwitchOff:"+customSwitchOff.getX());

        transAnim.addUpdateListener(animation -> {
            float c = (float) animation.getAnimatedValue();

            if(c == customSwitchOn.getX()+customSwitchLayout.getX()){
                updateView();
            }
        });

//        colorAnim.start();
        transAnim.start();
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);

        if(attrs!=null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomSwitchButton);
            String switchStat = array.getString(R.styleable.CustomSwitchButton_switch_status);
            isEnable = array.getBoolean(R.styleable.CustomSwitchButton_switch_enable, true);
            String switchValue = array.getString(R.styleable.CustomSwitchButton_switch_value);

            if(switchValue!=null){
                values = switchValue.split("\\|");
            }

            if("off".equals(switchStat)){
                switchStatus = SWITCH_OFF;
                isSwitchOn = false;
            }
            else if("on".equals(switchStat)){
                switchStatus = SWITCH_ON;
                isSwitchOn = true;
            }
            array.recycle();
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        customSwitchOn.setOnClickListener(this);
        customSwitchOff.setOnClickListener(this);

    }

    public void switchOn(){
        isSwitchOn = true;
        switchStatus = SWITCH_ON;
        if(switchStatus != oldSwitchStatus){
            startOnAnimation();
        }
    }

    public void switchOff(){
        isSwitchOn = false;
        switchStatus = SWITCH_OFF;
        if(switchStatus != oldSwitchStatus){
            startOffAnimation();
        }
    }

    public void setSwitchStatus(int status){

        switchStatus = status;
        isSwitchOn = (SWITCH_ON == status);
        updateView();

    }


    public void setEnable(boolean enable) {
        isEnable = enable;

        if(customSwitchOn == null ){
            return;
        }
        if(isEnable){
            customSwitchOn.setAlpha(1.0f);
            customSwitchOff.setAlpha(1.0f);
        }
        else {
            customSwitchOn.setAlpha(0.4f);
            customSwitchOff.setAlpha(0.4f);
        }
    }

    @Override
    public void onClick(View v) {
        if(!isEnable){
            return;
        }

        isSwitchOn = !isSwitchOn;


        if(isSwitchOn) {

            switchOn();
            onChildViewClick(this, 1);
        }
        else{
            switchOff();
            onChildViewClick(this, 0);
        }
        if(mOnSwitchListener!=null)
            mOnSwitchListener.onSwitchChanged(isSwitchOn);


    }

    public TextView getOn() {
        return customSwitchOn;
    }

    public TextView getOff() {
        return customSwitchOff;
    }

    public interface OnSwitchListener{

        void onSwitchChanged(boolean isSwichOn);

    }

}
