package com.supcon.mes.mbap.utils.controllers;

import android.app.Activity;

import com.supcon.common.view.base.controller.BaseController;
import com.supcon.common.view.view.picker.DateTimePicker;
import com.supcon.mes.mbap.utils.DateUtil;

/**
 * Created by wangshizhan on 2017/11/28.
 * Email:wangshizhan@supcon.com
 */

public class DatePickController extends BasePickerController<DateTimePicker> {

    private String[] dateStrs;
    private DateTimePicker.OnYearMonthDayTimePickListener mListener;

    public DatePickController(Activity activity) {
        super(activity);
    }


    public DatePickController date(long date){
        parseTime(date);
        return this;
    }

    public DatePickController listener(DateTimePicker.OnYearMonthDayTimePickListener listener){
        this.mListener = listener;
        return this;

    }


    public void show(long time){

        parseTime(time);
        show();

    }

    private void parseTime(long time) {

        dateStrs = DateUtil.dateFormat(time, "yyyy MM dd HH mm ss").split(" ");
        if(Integer.valueOf(dateStrs[0]) < 2017 || Integer.valueOf(dateStrs[0])>2025){
            dateStrs[0] = "2018";
        }

    }

    public void show(){
        DateTimePicker dateTimePicker = new DateTimePicker(activity, DateTimePicker.HOUR_24);
        dateTimePicker.setDateRangeStart(2017, 1, 1);
        dateTimePicker.setDateRangeEnd(2025, 11, 11);
        dateTimePicker.setTimeRangeStart(0, 0);
        dateTimePicker.setTimeRangeEnd(23, 59);

//        dateTimePicker.setTopLineColor(0x99FF0000);
//        dateTimePicker.setLabelTextColor(0xFFFF0000);

        dateTimePicker.setDividerVisible(isDividerVisible);
//        if(isDividerVisible)
//            dateTimePicker.setDividerColor(0xFFFF0000);
        dateTimePicker.setCycleDisable(isCycleDisable);
        dateTimePicker.setCanceledOnTouchOutside(isCancelOutside);
        dateTimePicker.setTextSize(textSize);
        dateTimePicker.setTextColor(textColorFocus, textColorNormal);

        if(isSecondVisible)
            dateTimePicker.setSelectedItem(Integer.valueOf(dateStrs[0]),
                    Integer.valueOf(dateStrs[1]), Integer.valueOf(dateStrs[2]),
                    Integer.valueOf(dateStrs[3]), Integer.valueOf(dateStrs[4]), Integer.valueOf(dateStrs[5]));
        else
            dateTimePicker.setSelectedItem(Integer.valueOf(dateStrs[0]),
                    Integer.valueOf(dateStrs[1]), Integer.valueOf(dateStrs[2]),
                    Integer.valueOf(dateStrs[3]), Integer.valueOf(dateStrs[4]));
        if(mListener!=null){
            dateTimePicker.setOnDateTimePickListener(mListener);
        }
        dateTimePicker.show();
    }

    @Override
    public DateTimePicker create(){

        DateTimePicker dateTimePicker = new DateTimePicker(activity, DateTimePicker.HOUR_24);
        dateTimePicker.setDateRangeStart(2017, 1, 1);
        dateTimePicker.setDateRangeEnd(2025, 11, 11);
        dateTimePicker.setTimeRangeStart(0, 0);
        dateTimePicker.setTimeRangeEnd(23, 59);

//        dateTimePicker.setTopLineColor(0x99FF0000);
//        dateTimePicker.setLabelTextColor(0xFFFF0000);

        dateTimePicker.setDividerVisible(isDividerVisible);
//        if(isDividerVisible)
//            dateTimePicker.setDividerColor(0xFFFF0000);
        dateTimePicker.setCycleDisable(isCycleDisable);
        dateTimePicker.setCanceledOnTouchOutside(isCancelOutside);
        dateTimePicker.setTextSize(textSize);
        dateTimePicker.setTextColor(textColorFocus, textColorNormal);

        if(isSecondVisible)
            dateTimePicker.setSelectedItem(Integer.valueOf(dateStrs[0]),
                    Integer.valueOf(dateStrs[1]), Integer.valueOf(dateStrs[2]),
                    Integer.valueOf(dateStrs[3]), Integer.valueOf(dateStrs[4]), Integer.valueOf(dateStrs[5]));
        else
            dateTimePicker.setSelectedItem(Integer.valueOf(dateStrs[0]),
                    Integer.valueOf(dateStrs[1]), Integer.valueOf(dateStrs[2]),
                    Integer.valueOf(dateStrs[3]), Integer.valueOf(dateStrs[4]));

        if(mListener!=null){
            dateTimePicker.setOnDateTimePickListener(mListener);
        }

        return dateTimePicker;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
