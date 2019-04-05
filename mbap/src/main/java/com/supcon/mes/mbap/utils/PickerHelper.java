package com.supcon.mes.mbap.utils;

import android.app.Activity;
import android.content.Context;

import com.supcon.mes.mbap.utils.controllers.DatePickController;
import com.supcon.mes.mbap.utils.controllers.SinglePickController;

/**
 * Created by wangshizhan on 2018/10/18
 * Email:wangshizhan@supcom.com
 */
public class PickerHelper {

    private static SinglePickController mSinglePickController;
    private static DatePickController mDatePickController;

    public static SinglePickController getSinglePickController(Context context) {
        mSinglePickController = new SinglePickController<String>((Activity) context);
        mSinglePickController.textSize(18);
        return mSinglePickController;
    }

    public static DatePickController getDatePickController(Context context) {
        mDatePickController = new DatePickController((Activity) context);
        mDatePickController.textSize(18);
        mDatePickController.setCycleDisable(false);
        return mDatePickController;
    }
}
