package com.supcon.mes.mbap.network;

import java.io.IOException;

import okhttp3.Response;
import okio.Buffer;

/**
 * Created by wangshizhan on 2017/8/9.
 */

public class TimeoutInterceptor extends BaseInterceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());;

        Buffer buffer = getBuffer(response);
        String content = readContent(response, buffer);

        if(content.contains("SocketTimeoutException")){
            return response.newBuilder()
                    .code(999)
                    .build();
        }


        return response;

    }
}
