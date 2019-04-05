package com.supcon.mes.mbap.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.supcon.common.com_http.BaseEntity;

import java.util.List;

/**
 * Created by wangshizhan on 2018/12/5
 * Email:wangshizhan@supcom.com
 */
public class WorkFlowVar extends BaseEntity {

    @Expose(deserialize = false, serialize = false)
    public String type;

    @Expose(deserialize = false, serialize = false)
    public String dec;

    @SerializedName("operateType")
    public String operateType;

    @SerializedName("workFlowVar.comment")
    public String comment;

    @SerializedName("workFlowVar.outcome")
    public String outCome;

    @SerializedName("workFlowVar.outcomeMapJson")
    public List<WorkFlowEntity> outcomeMapJson;

}
