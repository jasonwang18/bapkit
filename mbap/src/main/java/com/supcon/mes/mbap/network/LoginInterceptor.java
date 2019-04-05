package com.supcon.mes.mbap.network;

import android.text.TextUtils;
import android.util.Log;

import com.supcon.mes.mbap.beans.BAPErrorEntity;
import com.supcon.mes.mbap.utils.GsonUtil;
import com.supcon.mes.mbap.utils.XmlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;
import okio.Buffer;

/**
 * Created by wangshizhan on 2017/8/9.
 */

public class LoginInterceptor extends BaseInterceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());

        Buffer buffer = getBuffer(response);
        long size = buffer.size();
        Log.w("LoginInterceptor", "buffer size:" + size);
        if (size > 1048576L) {
            return response;
        }

        String content = readContent(response, buffer);

        if (content.contains("true") && content.contains("JSEESIONID") && content.contains("/cinfo")) {
            Log.w("LoginInterceptor", "登陆成功");
            getHeaders(response, content);

            buffer.clear();

            JSONObject jsonObject = new JSONObject();
            JSONObject resultObject = new JSONObject();
            try {
                resultObject.put("cname", XmlUtil.getStringByTag(content, "NAME"));
                resultObject.put("cid", XmlUtil.getStringByTag(content, "ID"));
                jsonObject.put("success", true);
                jsonObject.put("result", resultObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            buffer.write(jsonObject.toString().getBytes());

            buffer.flush();
            return response;
        } else if (content.contains("true") && content.contains("cinfo/")) {
            Log.w("LoginInterceptor", "登陆失败！");

            buffer.clear();

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("success", false);
                jsonObject.put("errMsg", "登录失败,请检查是否超过最大并发用户数");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            buffer.write(jsonObject.toString().getBytes());
            buffer.flush();
            return response;
        }
        /*else if(content.contains("HTTP Status 401")){

            Log.w("LoginInterceptor", "未登陆");

            buffer.clear();

            JSONObject jsonObject = new JSONObject();
            JSONObject resultObject = new JSONObject();
            try {
                jsonObject.put("success", false);
                jsonObject.put("errMsg", "HTTP Status 401");
                jsonObject.put("result", resultObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            buffer.write(jsonObject.toString().getBytes());

            buffer.flush();

            return response;
        }*/
        else if (content.contains("loginBody")) {

            Log.w("LoginInterceptor", "登出成功");

            buffer.clear();

            JSONObject jsonObject = new JSONObject();
            JSONObject resultObject = new JSONObject();
            try {
                jsonObject.put("success", true);
                jsonObject.put("result", resultObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            buffer.write(jsonObject.toString().getBytes());

            buffer.flush();

            return response;
        } else if (content.contains("actionErrors")) {//BAP接口错误

            BAPErrorEntity bapErrorEntity = GsonUtil.gsonToBean(content, BAPErrorEntity.class);
            buffer.clear();
            try {
                JSONObject jsonObject = new JSONObject();

//                JSONObject resultObject = new JSONObject(bapErrorEntity.toString());
                jsonObject.put("success", bapErrorEntity.success);
                jsonObject.put("errMsg", TextUtils.isEmpty(bapErrorEntity.exceptionMsg) ? "接口错误！" : bapErrorEntity.exceptionMsg);
//                jsonObject.put("result", null);
                buffer.write(jsonObject.toString().getBytes());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            buffer.flush();

            return response;

        } else if (content.contains("系统发生异常，请联系管理员") || content.contains("系统发生异常或正在启动，请稍后再试")) {
            Log.w("LoginInterceptor", "服务器错误");

            buffer.clear();

            String errorDetail = XmlUtil.getStringByTag(content, "details");
            if (!TextUtils.isEmpty(errorDetail) && errorDetail.contains("\n")) {
                String[] errors = errorDetail.split("\n\t");
                errorDetail = errors.length > 2 ? errors[2] : "系统发生异常，请联系管理员";
                errorDetail = errorDetail.trim();
            }


            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("success", false);
                jsonObject.put("errMsg", errorDetail);
                jsonObject.put("result", null);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            buffer.write(jsonObject.toString().getBytes());

            buffer.flush();
            return response.newBuilder()
                    .code(500)
                    .build();
        }

        return response;
    }

}
