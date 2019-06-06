package com.supcon.mes.mbap.beans;

import com.supcon.common.com_http.BaseEntity;

/**
 * Created by wangshizhan on 2018/7/20
 * Email:wangshizhan@supcom.com
 */
final public class LinkEntity extends BaseEntity {
    public String description; //中文描述
    public String name;  //迁移线
    public String source;
    public String destination;
    public String condition;
    public String operateType;
    public String requiredStaff;//必填
    public String selectPeople;//可选择人
}
