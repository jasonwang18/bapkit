package com.supcon.mes.mbap.utils.controllers;

import android.app.Activity;

import com.supcon.common.view.base.controller.BaseController;
import com.supcon.common.view.view.picker.WheelPicker;
import com.supcon.common.view.view.picker.widget.WheelView;

/**
 * Created by wangshizhan on 2017/12/7.
 * Email:wangshizhan@supcon.com
 */

 public abstract class BasePickerController<P extends WheelPicker> extends BaseController {

    protected boolean isCycleDisable = true;
    protected boolean isCancelOutside = false;
    protected boolean isDividerVisible = true;
    protected boolean isSecondVisible = false;

    protected int textSize = WheelView.TEXT_SIZE;
    protected int textColorNormal = WheelView.TEXT_COLOR_NORMAL;
    protected int textColorFocus = WheelView.TEXT_COLOR_FOCUS;

    protected Activity activity;

    public BasePickerController(Activity activity){
        this.activity = activity;

    }

    public void setSecondVisible(boolean secondVisible) {
        isSecondVisible = secondVisible;
    }

    public void setCycleDisable(boolean cycleDisable){

        isCycleDisable = cycleDisable;
    }

    public void setCanceledOnTouchOutside(boolean flag){
        isCancelOutside = flag;
    }

    public boolean isCancelOutside() {
        return isCancelOutside;
    }

    public boolean isCycleDisable() {
        return isCycleDisable;
    }

    public void setDividerVisible(boolean isDividerVisible){
        this.isDividerVisible = isDividerVisible;
    }

    public void textSize(int textSize){
        this.textSize = textSize;
    }

    public void textColor(int textColorNormal, int textColorFocus){
        this.textColorNormal = textColorNormal;
        this.textColorFocus = textColorFocus;
    }

    public boolean isDividerVisible() {
        return isDividerVisible;
    }

    public boolean isSecondVisible() {
        return isSecondVisible;
    }

    public abstract P create();

}
