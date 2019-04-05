package com.supcon.mes.mbap.demo;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.mes.mbap.adapter.FilterAdapter;
import com.supcon.mes.mbap.adapter.GalleryAdapter;
import com.supcon.mes.mbap.beans.FilterBean;
import com.supcon.mes.mbap.beans.GalleryBean;
import com.supcon.mes.mbap.utils.StatusBarUtils;
import com.supcon.mes.mbap.view.CustomAdView;
import com.supcon.mes.mbap.view.CustomListWidget;

import java.util.ArrayList;
import java.util.List;

public class ListWidgetTestActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_list_widget;
    }

    @Override
    protected void onInit() {
        super.onInit();


    }

    @Override
    protected void initView() {
        super.initView();
//        CustomListWidget<GalleryBean> listWidget = findViewById(R.id.listWidget);
//
//        List<GalleryBean> ads = new ArrayList<>();
//        GalleryBean galleryBean = new GalleryBean();
//        galleryBean.resId = R.drawable.pic_wms;
//        ads.add(galleryBean);
//        galleryBean = new GalleryBean();
//        galleryBean.resId = R.drawable.pic_wom;
//        ads.add(galleryBean);
//        galleryBean = new GalleryBean();
//        galleryBean.resId = R.drawable.pic_eam;
//        ads.add(galleryBean);
//
//
//        listWidget.setAdapter(new GalleryAdapter(context));
//        listWidget.setData(ads);

        CustomListWidget<FilterBean> listWidget = findViewById(R.id.listWidget);

        List<FilterBean> filterBeans = new ArrayList<>();
        FilterBean filterBean = new FilterBean();
        filterBean.name = "345457646";
        filterBeans.add(filterBean);
        filterBean = new FilterBean();
        filterBean.name = "54654464";
        filterBeans.add(filterBean);
        filterBean = new FilterBean();
        filterBean.name = "567567567";
        filterBeans.add(filterBean);

        listWidget.setAdapter(new FilterAdapter<FilterBean>(context));
        listWidget.setData(filterBeans);
    }
}
