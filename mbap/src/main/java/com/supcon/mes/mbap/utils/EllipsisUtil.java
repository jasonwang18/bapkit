package com.supcon.mes.mbap.utils;

import android.text.Layout;
import android.widget.TextView;

import com.supcon.common.view.util.LogUtil;

/**
 * Created by wangshizhan on 2018/12/12
 * Email:wangshizhan@supcom.com
 */
public class EllipsisUtil {

    public static boolean isEllipsisEnable(TextView view){

        Layout l = view.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0) {
                if (l.getEllipsisCount(lines - 1) > 0) {
                    return true;
                }
                else{
                    return false;
                }
            }
        } else {
            LogUtil.d( "Layout is null");
        }


        return false;
    }

}
