package com.supcon.mes.mbap.utils;

import android.content.res.TypedArray;
import android.text.TextUtils;

import com.supcon.mes.mbap.R;

/**
 * Created by wangshizhan on 2017/12/12.
 * Email:wangshizhan@supcon.com
 */

public enum Orientation {

    HORIZONTAL(0),
    VERTICAL(1);

    String key;
    int value;

    Orientation(int value){
        this.value = value;
    }

    public int value(){
        return value;
    }




}
