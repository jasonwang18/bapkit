package com.supcon.mes.mbap.demo;

import android.view.View;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.mes.mbap.beans.GalleryBean;
import com.supcon.mes.mbap.view.CustomAdView;

import java.util.ArrayList;
import java.util.List;

public class AdTestActivity extends BaseActivity {

    private CustomAdView customAdView;
    private List<GalleryBean> ads;

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
        customAdView = findViewById(R.id.customAdView);

        ads = new ArrayList<>();
        GalleryBean galleryBean = new GalleryBean();
        galleryBean.resId = R.drawable.pic_wms;
        ads.add(galleryBean);
        galleryBean = new GalleryBean();
        galleryBean.resId = R.drawable.pic_wom;
        ads.add(galleryBean);
        galleryBean = new GalleryBean();
        galleryBean.resId = R.drawable.pic_eam;
        ads.add(galleryBean);


        customAdView.setGalleryBeans(ads);

    }


    @Override
    protected void initListener() {
        super.initListener();

        customAdView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                ToastUtils.show(context,"position：" + ads.indexOf(obj));
            }
        });
    }
}
