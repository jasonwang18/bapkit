package com.supcon.mes.mbap.demo;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.mes.mbap.beans.GalleryBean;
import com.supcon.mes.mbap.view.CustomGalleryView;

import java.util.ArrayList;
import java.util.List;

import static com.supcon.mes.mbap.adapter.GalleryAdapter.FILE_TYPE_VIDEO;

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
        galleryBean.resId = R.drawable.ic_pic_default;
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);

        GalleryBean galleryBean2 = new GalleryBean();
        galleryBean2.resId = R.drawable.ic_pic_default;
        galleryBean2.fileType = FILE_TYPE_VIDEO;

        customGalleryView.setGalleryBeans(galleryBeans);

        CustomGalleryView customGalleryView2 = findViewById(R.id.galleryView2);
        customGalleryView2.addGalleryBean(galleryBean2);
//        customGalleryView2.addGalleryBean(galleryBean2);
    }
}
