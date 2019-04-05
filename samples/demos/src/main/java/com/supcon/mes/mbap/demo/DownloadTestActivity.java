package com.supcon.mes.mbap.demo;

import android.os.Handler;

import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.mes.mbap.utils.DateUtil;
import com.supcon.mes.mbap.view.CustomDownloadView;

public class DownloadTestActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_download;
    }

    @Override
    protected void initView() {
        super.initView();
        CustomDownloadView customDownloadView = findViewById(R.id.customDownloadView);
        CustomDownloadView customDownloadView2 = findViewById(R.id.customDownloadView2);



        findViewById(R.id.downloadBtn).setOnClickListener(v -> {
                    customDownloadView.startLoader();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            customDownloadView.stopLoader(true);
                            customDownloadView.setDownloadInfo("下载成功5条数据", true);
                        }
                    }, 3000);
                }
        );

        findViewById(R.id.downloadBtn2).setOnClickListener(v -> {
            customDownloadView2.startLoader();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    customDownloadView2.stopLoader(false);
                    customDownloadView2.setDownloadInfo("没有可以下载的数据", false);
                }
            }, 3000);
        }
        );

        customDownloadView.setDownloadInfo("下载成功了", true);
        customDownloadView.setDownloadDate("更新时间："+DateUtil.dateTimeFormat(System.currentTimeMillis()));


        customDownloadView2.setDownloadInfo("下载失败了", false);
        customDownloadView2.setDownloadDate("更新时间："+DateUtil.dateTimeFormat(System.currentTimeMillis()));
    }
}
