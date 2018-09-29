package com.supcon.mes.mbap.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.mes.mbap.view.CustomHorizontalSearchTitleBar;

import java.util.concurrent.TimeUnit;

/**
 * Environment: hongruijun
 * Created by Xushiyun on 2018/5/7.
 */

public class CustomHorizontalSearchActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_horizontal_search);
        initView();
    }

    private void initView() {
        RxView.clicks(findViewById(R.id.leftBtn))
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> CustomHorizontalSearchActivity.this.finish());

        CustomHorizontalSearchTitleBar customHorizontalSearchActivity = findViewById(R.id.titleBar);
        customHorizontalSearchActivity.enableRightBtn();
//        customHorizontalSearchActivity.setTitleBarColor(R.color.actionsheet_red);
//        customHorizontalSearchActivity.disableRightBtn();
        customHorizontalSearchActivity.rightBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(CustomHorizontalSearchActivity.this, "点击了RightBtn");
            }
        });
        customHorizontalSearchActivity.setCallBack(new CustomHorizontalSearchTitleBar.CallBack() {
            @Override
            public void beforeAnimation(boolean flag) {
//                ToastUtils.show(CustomHorizontalSearchActivity.this, "动画开始前");
            }

            @Override
            public void duringAnimation(boolean flag) {
//                ToastUtils.show(CustomHorizontalSearchActivity.this, "动画进行中");
            }

            @Override
            public void afterAnimation(boolean flag) {
//                ToastUtils.show(CustomHorizontalSearchActivity.this, "动画结束后",1000);
            }
        });
        customHorizontalSearchActivity.setDisplayCallBack(new CustomHorizontalSearchTitleBar.DisplayCallback() {
            @Override
            public void onShow() {
                ToastUtils.show(CustomHorizontalSearchActivity.this, "zzhankai1");
            }

            @Override
            public void onClickSearchButton() {

            }

            @Override
            public void onCancel() {

            }
        });
    }
}
