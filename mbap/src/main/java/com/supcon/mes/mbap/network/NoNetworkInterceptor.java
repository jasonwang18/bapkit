package com.supcon.mes.mbap.network;

import com.supcon.common.view.App;
import com.supcon.common.view.util.NetWorkUtil;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.utils.XmlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by wangshizhan on 2017/8/9.
 * 没有网络时，直接返回统一的错误提示 “网络断开，请重新联网”
 * 拦截网络超时，防止onError错误
 *
 */

public class NoNetworkInterceptor extends BaseInterceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        Buffer buffer = getBuffer(response);
        String content = readContent(response, buffer);

        if(content.contains("ConnectException")){
            buffer.clear();
            JSONObject jsonObject = new JSONObject();
            JSONObject resultObject = new JSONObject();
            if (NetWorkUtil.isWifiConnected(App.getAppContext())) {
                try {
                    jsonObject.put("success", "true");
                    jsonObject.put("result", resultObject);
                    jsonObject.put("errMsg", "无法连接到服务器 "+ MBapApp.getIp()+":"+MBapApp.getPort()+"!");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    jsonObject.put("success", "true");
                    jsonObject.put("result", resultObject);
                    jsonObject.put("errMsg", "WIFI已断开！");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            buffer.write(jsonObject.toString().getBytes());
            buffer.flush();
        }


        return response;

    }


}
