package com.supcon.mes.mbap.constant;

/**
 * Created by wangshizhan on 2019/1/15
 * Email:wangshizhan@supcom.com
 */
public enum ListType {

    CONTENT(0),
    TITLE(1),
    HEADER(2);


    private int value;
    ListType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
