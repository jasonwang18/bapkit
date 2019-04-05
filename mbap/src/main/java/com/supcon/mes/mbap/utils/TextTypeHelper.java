package com.supcon.mes.mbap.utils;

import android.graphics.Typeface;
import android.os.Build;
import android.widget.TextView;

import com.supcon.mes.mbap.MBapApp;

/**
 * Created by wangshizhan on 2018/1/15.
 * Email:wangshizhan@supcon.com
 */

public class TextTypeHelper {

    public static void setCustomFont(TextView textView){

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Typeface newFont = Typeface.createFromAsset(MBapApp.getAppContext().getAssets(), "fonts/custom.ttf");
            textView.setTypeface(newFont);
        }

    }

}
