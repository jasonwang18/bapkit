package com.supcon.mes.mbap.listener;

/**
 * Created by wangshizhan on 2018/5/30.
 * Email:wangshizhan@supcon.com
 * 用于对必填项的检查工作，设置这个监听意味着必填
 */
public interface OnContentCheckListener {


    /**
     * 检查输入是否符合规则
     * @return 校验是否通过
     */
    boolean isCheckPass(String content);

    /**
     * 生成校验结论信息
     * @return 校验结论信息
     */
    String createCheckInfo();

}
