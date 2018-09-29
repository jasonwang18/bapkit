package com.supcon.mes.mbap.utils.controllers;

import android.app.Activity;

import com.supcon.common.view.view.picker.DoublePicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 2017/12/5.
 * Email:wangshizhan@supcon.com
 */

public class DoublePickerController extends BasePickerController<DoublePicker> {

    private List<String> firstList = new ArrayList<>();
    private List<String> secondList = new ArrayList<>();
    private DoublePicker.OnPickListener mListener;

    public DoublePickerController(Activity activity){
        super(activity);

    }

    @Override
    public DoublePicker create() {
        DoublePicker picker = new DoublePicker(activity, firstList, secondList);
        picker.setDividerVisible(isDividerVisible);
        picker.setCycleDisable(isCycleDisable);
        picker.setSelectedIndex(0, 0);
        if(mListener!=null)
            picker.setOnPickListener(mListener);
        return picker;
    }

    public DoublePickerController listener(DoublePicker.OnPickListener listener){

        this.mListener = listener;
        return this;
    }

    public DoublePickerController firstList(List<String> list){
        this.firstList = list;
        return this;
    }

    public DoublePickerController secondList(List<String> list){
        this.secondList = list;
        return this;
    }


    public void show(){
        show(0,0);
    }


    public void show(int firstIndex, int secondIndex){

        DoublePicker picker = new DoublePicker(activity, firstList, secondList);
        picker.setDividerVisible(isDividerVisible);
        picker.setCycleDisable(isCycleDisable);
        picker.setSelectedIndex(firstIndex, secondIndex);
        if(mListener!=null)
            picker.setOnPickListener(mListener);
        picker.show();

    }

}
