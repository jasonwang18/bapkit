package com.supcon.mes.mbap.network;

import com.supcon.common.view.App;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangshizhan on 2018/1/26.
 * Email:wangshizhan@supcon.com
 */

public class HttpCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetWorkUtil.isWifiConnected(App.getAppContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            LogUtil.w( "No network");
        }

        Response originalResponse = chain.proceed(request);
        if (NetWorkUtil.isNetConnected(App.getAppContext())) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
