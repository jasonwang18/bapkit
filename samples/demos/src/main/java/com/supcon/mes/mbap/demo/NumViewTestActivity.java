package com.supcon.mes.mbap.demo;

import android.annotation.SuppressLint;
import android.text.InputType;

import com.supcon.common.com_http.util.RxSchedulers;
import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.mbap.listener.OnTextListener;
import com.supcon.mes.mbap.view.CustomNumView;
import com.supcon.mes.mbap.view.CustomTab;
import com.supcon.mes.mbap.view.CustomWaveView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class NumViewTestActivity extends BaseActivity {

    CustomWaveView waveView;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_num_view;
    }

    @Override
    protected void initView() {
        super.initView();
        CustomNumView customNumView = findViewById(R.id.numView);
        customNumView.getNumViewInput().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        customNumView.setTextListener(new OnTextListener() {
            @Override
            public void onText(String text) {
                LogUtil.d(text);
            }
        });


        waveView = findViewById(R.id.waveView);

        waveView.setText("5678");
    }


    @SuppressLint("CheckResult")
    @Override
    protected void onResume() {
        super.onResume();


        Flowable.timer(1000, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        waveView.setText("2378");
                    }
                });
    }
}
