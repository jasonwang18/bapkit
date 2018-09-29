package com.supcon.mes.mbap.demo;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.mes.mbap.beans.GalleryBean;
import com.supcon.mes.mbap.view.CustomGalleryView;

import java.util.ArrayList;
import java.util.List;

public class GalleryTestActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void initView() {
        super.initView();
        CustomGalleryView customGalleryView = findViewById(R.id.galleryView);

        List<GalleryBean> galleryBeans = new ArrayList<>();

        GalleryBean galleryBean = new GalleryBean();
        galleryBean.resId = R.drawable.ic_default_pic;
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);


        customGalleryView.setGalleryBeans(galleryBeans);


    }
}
