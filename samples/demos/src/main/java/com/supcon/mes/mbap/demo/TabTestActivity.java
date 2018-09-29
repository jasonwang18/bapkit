package com.supcon.mes.mbap.demo;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.mes.mbap.view.CustomTab;

public class TabTestActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_tab;
    }

    @Override
    protected void initView() {
        super.initView();
        CustomTab customTab = findViewById(R.id.customTab);
        customTab.addTab("未确认");
        customTab.addTab("已确认");
        customTab.setCurrentTab(0);

    }
}
