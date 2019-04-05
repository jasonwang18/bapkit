package com.supcon.mes.mbap.demo;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.mbap.beans.LinkEntity;
import com.supcon.mes.mbap.beans.Transition;
import com.supcon.mes.mbap.utils.GsonUtil;
import com.supcon.mes.mbap.view.CustomPopTransation;
import com.supcon.mes.mbap.view.CustomWorkFlowView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 2018/1/2.
 * Email:wangshizhan@supcon.com
 */

public class TransitionTestActivity extends BaseActivity {

    CustomPopTransation customTransition;
    String testJson = "[{\"requiredStaff\":true,\"outCome\":\"Link746\",\"outComeType\":\"submit\",\"transitionDesc\":\"提交\",\"selectType\":0}" +
            ",{\"requiredStaff\":false,\"outCome\":\"Link713\",\"outComeType\":\"cancel\",\"transitionDesc\":\"作废\",\"selectType\":0}" +
            ",{\"requiredStaff\":false,\"outCome\":\"Link713\",\"outComeType\":\"cancel\",\"transitionDesc\":\"作废66\",\"selectType\":0}" +
            ",{\"requiredStaff\":true,\"outCome\":\"Link713\",\"outComeType\":\"cancel\",\"transitionDesc\":\"委托\",\"selectType\":0}" +
            ",{\"requiredStaff\":false,\"outCome\":\"Link713\",\"outComeType\":\"cancel\",\"transitionDesc\":\"作废555555555555\",\"selectType\":0}" +
            ",{\"requiredStaff\":true,\"outCome\":\"Link713\",\"outComeType\":\"cancel\",\"transitionDesc\":\"作废44444fgjdfkgdkfkdkdkdkfkdkfdk44444444444444444444\",\"selectType\":0}" +
            "]";
    CustomWorkFlowView customWorkFlow;

    @Override
    protected void onInit() {
        super.onInit();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_transition;
    }

    @Override
    protected void initView() {
        super.initView();
        customTransition = findViewById(R.id.customTransition);
        customWorkFlow = findViewById(R.id.customWorkFlow);
        List<Transition> list = GsonUtil.jsonToList(testJson, Transition.class);

        customTransition.setTransitions(list);
        List<LinkEntity> linkEntities = new ArrayList<>();
        LinkEntity linkEntity = new LinkEntity();
        linkEntity.description = "作废";
        linkEntities.add(linkEntity);
        linkEntity = new LinkEntity();
        linkEntity.description = "提交";
        linkEntities.add(linkEntity);
        customWorkFlow.setLinks(linkEntities.toString());
    }

    @Override
    protected void initListener() {
        super.initListener();

        customTransition.setOnChildViewClickListener((childView, action, obj) -> {
            String tag = (String) childView.getTag();
            LogUtil.w("tag:"+tag);

            switch (tag){

                case "submitBtn":
//                    onLoading("提交："+customTransition.currentTransition().transitionDesc);
//                    new Handler().postDelayed(() -> onLoadSuccess(), 2000);
                    break;

                case "save":
//                    onLoading("保存");
//                    new Handler().postDelayed(() -> onLoadFailed(""), 2000);
                    break;

                case "saveLocal":
//                    onLoading("保存本地");
                    break;

                case "cancel":
//                    onLoading("选择作废");
                    break;

                case "submit":
//                    onLoading("选择提交");
                    break;
            }

//            new Handler().postDelayed(() -> onLoadSuccess(), 2000);

        });
    }
}
