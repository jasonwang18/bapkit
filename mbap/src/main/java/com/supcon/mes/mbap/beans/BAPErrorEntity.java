package com.supcon.mes.mbap.beans;

import com.supcon.common.com_http.BaseEntity;

import java.util.List;

/**
 * Created by wangshizhan on 2018/5/4.
 * Email:wangshizhan@supcon.com
 */

public class BAPErrorEntity extends BaseEntity{

    public boolean success;
    public List<String> actionErrors;
    public String exceptionMsg;
    public Object items;


}
