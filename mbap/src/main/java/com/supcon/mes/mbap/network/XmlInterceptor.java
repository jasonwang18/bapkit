package com.supcon.mes.mbap.network;

import android.text.TextUtils;
import android.util.Log;

import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.SharedPreferencesUtils;
import com.supcon.mes.mbap.MBapApp;
import com.supcon.mes.mbap.MBapConstant;
import com.supcon.mes.mbap.utils.XmlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by wangshizhan on 2017/8/9.
 */

public class XmlInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;

        try {
            response = chain.proceed(chain.request());
        } catch (IOException e) {
            e.printStackTrace();
            if(e.toString().contains("java.net.SocketTimeoutException")){

                Request request2 = chain.request().newBuilder()
                        .build();

                Response response2 = chain.proceed(request2);

                return response2.newBuilder()
                        .code(999)
                        .build();

            }
            return response;
        }

        if(response.code() == 500){
            Buffer buffer = getBuffer(response);
            buffer.clear();

            JSONObject jsonObject = new JSONObject();
            JSONObject resultObject = new JSONObject();
            try {
                jsonObject.put("success", "false");
                jsonObject.put("result", resultObject);
                jsonObject.put("errMsg", "服务器错误！");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            buffer.write(jsonObject.toString().getBytes());
            buffer.flush();
            return response;
        }

        Buffer buffer = getBuffer(response);
        String content = readContent(response, buffer);

        if (content.contains("true") && content.contains("cinfo")) {
            Log.w("XmlInterceptor", "登陆成功");
            getHeaders(response, content);

            buffer.clear();

            JSONObject jsonObject = new JSONObject();
            JSONObject resultObject = new JSONObject();
            try {
                resultObject.put("company", XmlUtil.getStringByTag(content, "NAME"));
                jsonObject.put("success", "true");
                jsonObject.put("result", resultObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            buffer.write(jsonObject.toString().getBytes());

            buffer.flush();
            return response;
        }

        if (/*content.contains("logoutFlag") && content.contains("true")*/content.contains("loginBody")) {

            Log.w("XmlInterceptor", "登出成功");

            buffer.clear();

            JSONObject jsonObject = new JSONObject();
            JSONObject resultObject = new JSONObject();
            try {
                jsonObject.put("success", "true");
                jsonObject.put("result", resultObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            buffer.write(jsonObject.toString().getBytes());

            buffer.flush();

            return response;

        }

        return response;
    }


    private Buffer getBuffer(Response response) throws IOException{
        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        return source.buffer();
    }

    private String readContent(Response response, Buffer buffer) throws UnsupportedCharsetException{
        Charset charset = UTF8;
        MediaType contentType = response.body().contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        return buffer.clone().readString(charset);
    }

    private void getHeaders(Response response, String content) {

        Headers headers = response.headers();

        String CASTGCStr = null;
        String jsessionidStr = null;


        jsessionidStr = XmlUtil.getStringByTag(content, "JSEESIONID");

        List<String> cookies = headers.values("Set-Cookie");

        for(int i = 0; i < cookies.size(); i++){

            String cookie = cookies.get(i);

/*            if(cookie.contains(Constant.SPKey.JSESSIONID) && cookie.contains(";") && cookie.contains("=")){

                String tmp[] = cookie.split(";");
                if(tmp[0].contains("Constant.SPKey.JSESSIONID")){
                    jsessionidStr = tmp[0].split("=")[1];
                }

//                jsessionidStr = cookie.split("=")[1];
            }
            else */if(cookie.contains(MBapConstant.SPKey.CASTGC)){

                String temp = cookie.split(";")[0];
                if(!TextUtils.isEmpty(temp) && temp.contains(MBapConstant.SPKey.CASTGC) && temp.contains("=")){
                    CASTGCStr = temp.split("=")[1];
                }

            }

        }


        LogUtil.d("jsessionidStr:"+jsessionidStr);
        LogUtil.d("CASTGC:"+CASTGCStr);


        SharedPreferencesUtils.setParam(MBapApp.getAppContext(), MBapConstant.SPKey.JSESSIONID, jsessionidStr);
        SharedPreferencesUtils.setParam(MBapApp.getAppContext(), MBapConstant.SPKey.CASTGC, CASTGCStr);
    }


}
