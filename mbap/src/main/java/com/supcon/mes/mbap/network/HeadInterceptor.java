package com.supcon.mes.mbap.network;

import com.supcon.mes.mbap.MBapApp;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by wangshizhan on 2018/1/26.
 * Email:wangshizhan@supcon.com
 */

public class HeadInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl url = chain.request().url();
        if(url.encodedPath().contains("/cas/mobile/logon") /*|| url.encodedPath().contains("/cas/logout")*/){
            return chain.proceed(chain.request());
        }

        if(url.encodedPath().contains("/services/public/") ){
            return chain.proceed(chain.request().newBuilder()
                    .addHeader("USER_AGENT", "Linux; U; Android")
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build());
        }

        return chain.proceed(chain.request().newBuilder()
                .addHeader("Cookie", MBapApp.getCooki())
                .addHeader("Authorization", MBapApp.getAuthorization())
                .addHeader("USER_AGENT", "Linux; U; Android")
                .addHeader("Content-Type", "application/json")
                .build());
    }
}
