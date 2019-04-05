package com.supcon.mes.mbap.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.mes.mbap.view.CustomHorizontalSearchTitleBar;
import com.supcon.mes.mbap.view.CustomPopupSearchView;

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

    @SuppressLint("CheckResult")
    private void initView() {
        CustomHorizontalSearchTitleBar customHorizontalSearchTitleBar = findViewById(R.id.titleBar);
//        customHorizontalSearchTitleBar.enableRightBtn();
        customHorizontalSearchTitleBar.popOffsetX(0).popOffsetY(-25)
                .bindNewRightBtnFunc("上传", v -> ToastUtils.show(CustomHorizontalSearchActivity.this, "点击上传"))
                .bindNewRightBtnFunc("上传", v -> ToastUtils.show(CustomHorizontalSearchActivity.this, "点击上传"))
                .bindNewRightBtnFunc("上传", v -> ToastUtils.show(CustomHorizontalSearchActivity.this, "点击上传"));
        CustomPopupSearchView customPopupSearchView =
                findViewById(R.id.customPopupSearchView);
        customPopupSearchView.onTitleSelectedListener(new CustomPopupSearchView.OnTitleSelectedListener() {
            @Override
            public void OnTitleSelected(String title) {
                ToastUtils.show(CustomHorizontalSearchActivity.this, title);
            }
        }).onSearchBtnClickListener(new CustomPopupSearchView.OnSearchBtnClickListener() {
            @Override
            public void onSearchBtnClick() {
                ToastUtils.show(CustomHorizontalSearchActivity.this,"点击了搜索按钮！");
            }
        });
        RxView.clicks(findViewById(R.id.leftBtn))
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> CustomHorizontalSearchActivity.this.finish());

    }
}
