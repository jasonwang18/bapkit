package com.supcon.mes.mbap.demo;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.supcon.mes.mbap.MBapApp;

/**
 * Created by wangshizhan on 2018/1/2.
 * Email:wangshizhan@supcon.com
 */

public class TestApplication extends MBapApp {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

}
