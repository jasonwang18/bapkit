package com.supcon.mes.mbap.demo;

import android.os.Handler;
import android.view.View;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.mes.mbap.beans.FilterBean;
import com.supcon.mes.mbap.view.CustomFilterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 2018/1/2.
 * Email:wangshizhan@supcon.com
 */

public class FilterTestActivity extends BaseActivity {

    CustomFilterView mCustomFilterView, mCustomFilterViewDate;

    @Override
    protected void onInit() {
        super.onInit();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_filter;
    }

    @Override
    protected void initView() {
        super.initView();
        mCustomFilterView = findViewById(R.id.filter);
        mCustomFilterViewDate = findViewById(R.id.filterDate);

        List<FilterBean> list = new ArrayList<>();
        for(int i = 0; i< 10;i++) {
            FilterBean filterBean = new FilterBean();
            filterBean.name = "物品zhg34534534534534eadasd" + list.size();
            list.add(filterBean);
            filterBean = new FilterBean();
            filterBean.name = "物品";
            list.add(filterBean);
            filterBean = new FilterBean();
            filterBean.name = "物品123";
            list.add(filterBean);
            filterBean = new FilterBean();
            filterBean.name = "物品544";
            list.add(filterBean);
            filterBean = new FilterBean();
            filterBean.name = "物品";
            list.add(filterBean);
            filterBean = new FilterBean();
            filterBean.name = "物品123";
            list.add(filterBean);
            filterBean = new FilterBean();
            filterBean.name = "物品544";
            list.add(filterBean);
            filterBean = new FilterBean();
            filterBean.name = "物品4565466666";
            list.add(filterBean);
            filterBean = new FilterBean();
            filterBean.name = "物品123";
            list.add(filterBean);
            filterBean = new FilterBean();
            filterBean.name = "物品544";
            list.add(filterBean);
            filterBean = new FilterBean();
            filterBean.name = "物品123";
            list.add(filterBean);
            filterBean = new FilterBean();
            filterBean.name = "物品544";
            list.add(filterBean);

        }

        mCustomFilterView.setData(list);

        list.clear();
        for(int i = 0; i< 5;i++) {
            FilterBean filterBean = new FilterBean();
            filterBean.name = "日期" + list.size();
            list.add(filterBean);
        }

        mCustomFilterViewDate.setData(list);

        findViewById(R.id.rightBtn).setVisibility(View.VISIBLE);
        findViewById(R.id.rightBtn).setOnClickListener(v -> {

            onLoading("正在加载1123");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onLoadSuccess();
                }
            }, 5000);

        });

        findViewById(R.id.leftBtn).setOnClickListener(v -> {

            onLoadSuccess();

        });


    }
}
