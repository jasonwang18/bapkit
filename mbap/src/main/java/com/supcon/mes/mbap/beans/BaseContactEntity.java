package com.supcon.mes.mbap.beans;

import com.supcon.common.com_http.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseContactEntity extends BaseEntity {
    public Boolean isSelected;
    public Boolean isLast;
    public BaseContactEntity fatherDepartment;
    public List<BaseContactEntity> subDepartment = new ArrayList<>();
}
