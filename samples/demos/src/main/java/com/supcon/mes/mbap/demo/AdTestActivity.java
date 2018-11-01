package com.supcon.mes.mbap.demo;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.mes.mbap.beans.GalleryBean;
import com.supcon.mes.mbap.view.CustomAdView;

import java.util.ArrayList;
import java.util.List;

public class AdTestActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_ad;
    }

    @Override
    protected void onInit() {
        super.onInit();


    }

    @Override
    protected void initView() {
        super.initView();
        CustomAdView customAdView = findViewById(R.id.customAdView);

        List<GalleryBean> ads = new ArrayList<>();
        GalleryBean galleryBean = new GalleryBean();
        galleryBean.resId = R.drawable.ic_default_pic;
        ads.add(galleryBean);
        galleryBean = new GalleryBean();
        galleryBean.resId = R.drawable.ic_default_pic;
        ads.add(galleryBean);
        galleryBean = new GalleryBean();
        galleryBean.resId = R.drawable.ic_default_pic;
        ads.add(galleryBean);


        customAdView.setGalleryBeans(ads);

    }
}
