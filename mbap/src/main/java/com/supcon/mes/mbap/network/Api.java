package com.supcon.mes.mbap.network;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.supcon.common.view.App;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.SharedPreferencesUtils;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConstant;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by baixiaokang on 16/3/9.
 */
public class Api {
    public Retrofit retrofit;
    public ApiService service;
    private Interceptor mHeadInterceptor;//自定义的head拦截器，模块单独调试的时候用
    private boolean isDebug = true;
    private final int DEFAULT_TIME_OUT_SECONDS = 30;
    private int mTimeOut = DEFAULT_TIME_OUT_SECONDS;

    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
    }

    public static Api getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //构造方法私有
    private Api() {
        build();
    }

    private void build(){
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.i(message);
            }
        });
        logInterceptor.setLevel(isDebug?HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE);

        File cacheFile = new File(App.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(mTimeOut, TimeUnit.SECONDS)
                .connectTimeout(mTimeOut, TimeUnit.SECONDS)
                .writeTimeout(mTimeOut, TimeUnit.SECONDS)
                .addInterceptor(mHeadInterceptor == null?new HeadInterceptor():mHeadInterceptor)
                .addInterceptor(logInterceptor)
                .addInterceptor(new LoginInterceptor())
//                .addInterceptor(new NoNetworkInterceptor())
//                .addInterceptor(new TimeoutInterceptor())
//                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();

        boolean isUrlEnabled = SharedPreferencesUtils.getParam(MBapApp.getAppContext(), MBapConstant.SPKey.URL_ENABLE, false);

        if(isUrlEnabled && TextUtils.isEmpty(MBapApp.getUrl()) || !isUrlEnabled && TextUtils.isEmpty(MBapApp.getIp())){
            LogUtil.e("缓存的url或ip为空！");
            MBapApp.setIp("192.168.90.9");
            MBapApp.setPort("8080");
            SharedPreferencesUtils.setParam(MBapApp.getAppContext(), MBapConstant.SPKey.URL_ENABLE, false);
        }

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(isUrlEnabled?"http://"+MBapApp.getUrl()+"/":"http://"+ MBapApp.getIp()+":"+ MBapApp.getPort()+"/")
                .build();
        service = retrofit.create(ApiService.class);
    }

    public void rebuild(){
        build();
    }

    public void setTimeOut(int timeOut){
        this.mTimeOut = timeOut;
        rebuild();
    }

    public void setDebug(boolean isDebug){
        this.isDebug = isDebug;
        rebuild();
    }

    /**
     * 模块单独调试的时候用来导入自定义的HeadInterceptor
     * @param headInterceptor
     */
    public void setHeadInterceptor(Interceptor headInterceptor) {
        mHeadInterceptor = headInterceptor;
    }
}