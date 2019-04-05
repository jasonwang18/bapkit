package com.supcon.mes.mbap.utils.controllers;

import android.app.Activity;

import com.supcon.common.view.view.picker.DatePicker;
import com.supcon.mes.mbap.utils.DateUtil;

import static com.supcon.common.view.view.picker.DateTimePicker.YEAR_MONTH_DAY;

/**
 * Created by wangshizhan on 2017/11/28.
 * Email:wangshizhan@supcon.com
 */

public class DateOnlyPickController extends BasePickerController<DatePicker> {

    private String[] dateStrs;
    private DatePicker.OnYearMonthDayPickListener mListener;

    public DateOnlyPickController(Activity activity) {
        super(activity);
    }


    public DateOnlyPickController date(long date){
        parseTime(date);
        return this;
    }

    public DateOnlyPickController listener(DatePicker.OnYearMonthDayPickListener listener){
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
        DatePicker datePicker = new DatePicker(activity, YEAR_MONTH_DAY);
        datePicker.setRangeStart(2017, 1, 1);
        datePicker.setRangeEnd(2025, 11, 11);

        datePicker.setDividerVisible(isDividerVisible);

        datePicker.setCycleDisable(isCycleDisable);
        datePicker.setCanceledOnTouchOutside(isCancelOutside);
        datePicker.setTextSize(textSize);
        datePicker.setTextColor(textColorFocus, textColorNormal);

        if(isSecondVisible)
            datePicker.setSelectedItem(Integer.valueOf(dateStrs[0]),
                    Integer.valueOf(dateStrs[1]), Integer.valueOf(dateStrs[2]),
                    Integer.valueOf(dateStrs[3]), Integer.valueOf(dateStrs[4]), Integer.valueOf(dateStrs[5]));
        else
            datePicker.setSelectedItem(Integer.valueOf(dateStrs[0]),
                    Integer.valueOf(dateStrs[1]), Integer.valueOf(dateStrs[2]),
                    Integer.valueOf(dateStrs[3]), Integer.valueOf(dateStrs[4]), 0);
        if(mListener!=null){
            datePicker.setOnDatePickListener(mListener);
        }
        datePicker.show();
    }

    @Override
    public DatePicker create(){

        DatePicker datePicker = new DatePicker(activity, YEAR_MONTH_DAY);
        datePicker.setRangeStart(2017, 1, 1);
        datePicker.setRangeEnd(2025, 11, 11);

        datePicker.setDividerVisible(isDividerVisible);

        datePicker.setCycleDisable(isCycleDisable);
        datePicker.setCanceledOnTouchOutside(isCancelOutside);
        datePicker.setTextSize(textSize);
        datePicker.setTextColor(textColorFocus, textColorNormal);

        if(isSecondVisible)
            datePicker.setSelectedItem(Integer.valueOf(dateStrs[0]),
                    Integer.valueOf(dateStrs[1]), Integer.valueOf(dateStrs[2]),
                    Integer.valueOf(dateStrs[3]), Integer.valueOf(dateStrs[4]), Integer.valueOf(dateStrs[5]));
        else
            datePicker.setSelectedItem(Integer.valueOf(dateStrs[0]),
                    Integer.valueOf(dateStrs[1]), Integer.valueOf(dateStrs[2]),
                    Integer.valueOf(dateStrs[3]), Integer.valueOf(dateStrs[4]), 0);

        if(mListener!=null){
            datePicker.setOnDatePickListener(mListener);
        }

        return datePicker;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
