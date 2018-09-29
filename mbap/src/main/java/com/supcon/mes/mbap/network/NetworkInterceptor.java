package com.supcon.mes.mbap.network;

import android.util.Log;

import com.supcon.common.view.App;
import com.supcon.common.view.util.NetWorkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by wangshizhan on 2017/8/9.
 * 没有网络时，直接返回统一的错误提示 “网络断开，请重新联网”
 * 拦截网络超时，防止onError错误
 *
 */

public class NetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean isNetworkAvaliable = false;
        if (NetWorkUtil.isWifiConnected(App.getAppContext())) {
            isNetworkAvaliable = true;
        }
        Response response = chain.proceed(request);

        if(isNetworkAvaliable){
            return response;
        }
        Log.d("Okhttp", "no network");

        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        buffer.clear();
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = response.body().contentType();
        if (contentType != null) {
            try {
                contentType.charset(charset);
            } catch (UnsupportedCharsetException e) {
                return response;
            }
        }

        JSONObject jsonObject = new JSONObject();
        JSONObject resultObject = new JSONObject();
        try {
            jsonObject.put("success","false");
            jsonObject.put("result",resultObject);
            jsonObject.put("errorMsg", "网络断开，请重新联网");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        buffer.write(jsonObject.toString().getBytes());

        buffer.flush();


        return response;
    }


}
