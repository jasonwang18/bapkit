package com.supcon.mes.mbap.beans;

import com.supcon.common.com_http.BaseEntity;

/**
 * Created by wangshizhan on 2017/11/24.
 * Email:wangshizhan@supcon.com
 */

public class Transition extends BaseEntity{

    public String outCome;

    public String outComeType;

    public String transitionDesc;

    public boolean requiredStaff;

    public int selectType;
}
