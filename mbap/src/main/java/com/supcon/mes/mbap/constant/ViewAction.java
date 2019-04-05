package com.supcon.mes.mbap.constant;

/**
 * Created by wangshizhan on 2019/1/15
 * Email:wangshizhan@supcom.com
 */
public enum ViewAction {

     CONTENT_CLEAN(-1),
     NORMAL(0),
     CONTENT_COPY (1);

     private int value;

     ViewAction(int value){
         this.value = value;
     }

    public int value() {
        return value;
    }
}
