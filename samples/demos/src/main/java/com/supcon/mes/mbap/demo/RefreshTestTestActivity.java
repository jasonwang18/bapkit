package com.supcon.mes.mbap.demo;

import com.supcon.common.com_http.util.RxSchedulers;
import com.supcon.common.view.base.activity.BaseRefreshActivity;
import com.supcon.common.view.listener.OnRefreshListener;
import com.supcon.mes.mbap.beans.GalleryBean;
import com.supcon.mes.mbap.view.CustomAdView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class RefreshTestTestActivity extends BaseRefreshActivity {

    @Override
    protected void onInit() {
        super.onInit();
        refreshController.setAutoPullDownRefresh(false);
        refreshController.setPullDownRefreshEnabled(true);

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_refresh_test;
    }

    @Override
    protected void initView() {
        super.initView();
        CustomAdView customAdView = findViewById(R.id.customAdView);

        List<GalleryBean> galleryBeans = new ArrayList<>();

        GalleryBean galleryBean = new GalleryBean();
        galleryBean.resId = R.mipmap.ic_launcher;
        galleryBean.localPath = "/storage/emulated/0/eam/quexian/20180517_165809.jpg";
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);
//        galleryBeans.add(galleryBean);


        customAdView.setGalleryBeans(galleryBeans);



    }

    @Override
    protected void initListener() {
        super.initListener();

        refreshController.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Flowable.timer(2000,  TimeUnit.MILLISECONDS)
                        .compose(RxSchedulers.io_main())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                refreshController.refreshComplete();
                            }
                        });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
