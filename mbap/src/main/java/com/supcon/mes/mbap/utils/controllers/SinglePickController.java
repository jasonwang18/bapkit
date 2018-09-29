package com.supcon.mes.mbap.utils.controllers;

import android.app.Activity;

import com.supcon.common.view.base.controller.BaseController;
import com.supcon.common.view.view.picker.SinglePicker;
import com.supcon.common.view.view.picker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 2017/11/28.
 * Email:wangshizhan@supcon.com
 */

public class SinglePickController<T> extends BasePickerController<SinglePicker<T>> {

    private List<T> mList = new ArrayList<>();
    SinglePicker.OnItemPickListener<T> mListener;


    public SinglePickController(Activity activity) {
        super(activity);
    }

    @Override
    public SinglePicker<T> create() {
        SinglePicker<T> picker = new SinglePicker<>(activity, mList);
        picker.setCanceledOnTouchOutside(isCancelOutside);
        picker.setCycleDisable(isCycleDisable);
        picker.setDividerVisible(isDividerVisible);
        picker.setTextSize(textSize);
        picker.setTextColor(textColorFocus, textColorNormal);
        picker.setSelectedIndex(0);
        if(mListener!=null){
            picker.setOnItemPickListener(mListener);
        }
        return picker;
    }


    public SinglePickController(Activity activity, List<T> list) {
        super(activity);
        this.mList = list;
    }


    public SinglePickController list(List<T> list){

        if(mList.size() != 0){
            mList.clear();
        }
        mList.addAll(list);

        return this;
    }

    public SinglePickController listener(SinglePicker.OnItemPickListener listener){

        mListener = listener;

        return this;

    }

    public void show(T t){
        SinglePicker<T> picker = new SinglePicker<>(activity, mList);
        picker.setCanceledOnTouchOutside(isCancelOutside);
        picker.setCycleDisable(isCycleDisable);
        picker.setTextSize(textSize);
        picker.setTextColor(textColorFocus, textColorNormal);
        picker.setSelectedItem(t);
        if(mListener!=null){
            picker.setOnItemPickListener(mListener);
        }
        picker.show();
    }

    public void show(int currentPosition){
        SinglePicker<T> picker = new SinglePicker<>(activity, mList);
        picker.setCanceledOnTouchOutside(isCancelOutside);
        picker.setCycleDisable(isCycleDisable);
        picker.setDividerVisible(isDividerVisible);
        picker.setTextSize(textSize);
        picker.setTextColor(textColorFocus, textColorNormal);
        picker.setSelectedIndex(currentPosition);
        if(mListener!=null){
            picker.setOnItemPickListener(mListener);
        }
        picker.show();
    }

    public void show(){

        show(0);

    }

    @Override
    public void onDestroy() {

    }
}
