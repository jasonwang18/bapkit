package com.supcon.mes.mbap.demo;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.mes.mbap.view.CustomCircleTextImageView;
import com.supcon.mes.mbap.view.CustomRoundTextImageView;

import java.util.List;

/**
 * Created by wangshizhan on 2018/1/2.
 * Email:wangshizhan@supcon.com
 */

public class CircleTextImageViewTestActivity extends BaseActivity {



    @Override
    protected int getLayoutID() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView() {
        super.initView();
        CustomRoundTextImageView customCircleTextImageView = findViewById(R.id.roundImageView);

        customCircleTextImageView.setText("海藻酸的AMRFGh");

    }
}
