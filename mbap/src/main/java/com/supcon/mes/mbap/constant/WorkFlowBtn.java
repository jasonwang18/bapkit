package com.supcon.mes.mbap.constant;

/**
 * Created by wangshizhan on 2019/1/15
 * Email:wangshizhan@supcom.com
 */
public enum WorkFlowBtn {

    SAVE_BTN(0),
    MIDDLE_BTN(1),
    SUBMIT_BTN(2),
    SAVE_LOCAL_BTN(3);

    private int value;

    WorkFlowBtn(int value){
        this.value = value;
    }

    public int value() {
        return value;
    }
}
