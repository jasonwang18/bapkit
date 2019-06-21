package com.supcon.mes.mbap.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

/**
 * Created by wangshizhan on 2019/6/21
 * Email:wangshizhan@supcom.com
 */
public class MyRadioButton extends CompoundButton {
    public MyRadioButton(Context context) {
        super(context);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {

        if(isChecked()){
            return true;
        }

        return super.performClick();
    }
}
