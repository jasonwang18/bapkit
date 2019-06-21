package com.supcon.mes.mbap.beans;

import com.supcon.common.com_http.BaseEntity;

/**
 * @Author xushiyun
 * @Create-time 5/23/19
 * @Pageage com.supcon.mes.mbap.beans
 * @Project mbap
 * @Email ciruy.victory@gmail.com
 * @Related-classes
 * @Desc
 */
public abstract class BaseMultiStageEntity extends BaseEntity {
    protected Integer type;
    protected Boolean visible = true;
    
    public Boolean getVisible() {
        return visible;
    }
    
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
}
