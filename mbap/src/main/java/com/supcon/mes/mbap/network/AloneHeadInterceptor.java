package com.supcon.mes.mbap.network;

import android.content.Context;

import com.supcon.mes.mbap.MBapApp;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by wangshizhan on 2018/1/26.
 * Email:wangshizhan@supcon.com
 * 模块单独调试时，需要从整合APP（Shell）获取必要信息
 */

public class AloneHeadInterceptor implements Interceptor {

    private Context hostContext = null;//其他APP的context

    public AloneHeadInterceptor(Context context){
        hostContext = context;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl url = chain.request().url();
        if(url.encodedPath().contains("/cas/mobile/logon")){
            return chain.proceed(chain.request());
        }

        return chain.proceed(chain.request().newBuilder()
                .addHeader("Cookie", MBapApp.getCooki(hostContext))
                .addHeader("Authorization", MBapApp.getAuthorization(hostContext))
                .addHeader("USER_AGENT", "Linux; U; Android")
                .addHeader("Content-Type", "application/json")
                .build());
    }
}
