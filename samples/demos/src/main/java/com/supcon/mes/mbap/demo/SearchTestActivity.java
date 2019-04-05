package com.supcon.mes.mbap.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jakewharton.rxbinding2.view.RxView;
import com.supcon.mes.mbap.utils.StatusBarUtils;

import io.reactivex.functions.Consumer;

public class SearchTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_test);
//        StatusBarUtils.hideStatusBar(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.equipmentThemeColor);
        initView();
    }

    private void initView() {
        RxView.clicks(findViewById(R.id.leftBtn)).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                SearchTestActivity.this.finish();
            }
        });
    }
}
