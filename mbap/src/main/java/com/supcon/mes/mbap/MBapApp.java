package com.supcon.mes.mbap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.supcon.common.view.App;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.SharedPreferencesUtils;
import com.supcon.mes.mbap.utils.cache.CacheUtil;

import java.lang.reflect.Field;

/**
 * Created by wangshizhan on 2017/11/24.
 * Email:wangshizhan@supcon.com
 */

public class MBapApp extends App{


    private static boolean isLogin = false;
    private static String userName = "";
    private static String password = "";
    private static String ip = "";
    private static String port = "";
    private static String url = "";

    private static Typeface newFontType;
    private static boolean isAlone = false;

    @Override
    public void onCreate() {
        super.onCreate();


        ip =  SharedPreferencesUtils.getParam(this, MBapConstant.SPKey.IP, "");
        LogUtil.i("default ip:"+ip);

        port =  SharedPreferencesUtils.getParam(this, MBapConstant.SPKey.PORT, "");
        LogUtil.i("default port:"+port);

        if(!isLogin){
            isLogin = SharedPreferencesUtils.getParam(this, MBapConstant.SPKey.LOGIN, false);
        }

        if(isLogin){
            userName = SharedPreferencesUtils.getParam(this, MBapConstant.SPKey.USER_NAME, "");
            password = SharedPreferencesUtils.getParam(this, MBapConstant.SPKey.PWD, "");
        }

        LogUtil.i("userName:"+userName);

        CacheUtil.init();

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            initFonts();
        }
        else{
            createTextType();
        }

    }

    private void createTextType() {
        newFontType = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");

    }


    public static Typeface fontType(){
        return newFontType;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private void initFonts() {

        Typeface mTypeface = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");

        try {
            Field field = Typeface.class.getDeclaredField("DEFAULT");
            field.setAccessible(true);
            field.set(null, mTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void exitApp() {
        if (getAppContext().store.size() > 1) {
            for (Activity activity : getAppContext().store) {
                activity.finish();
            }
        } else {
            System.exit(0);
        }
    }

    public static void setIsAlone(boolean isAlone){
        MBapApp.isAlone = isAlone;
    }

    public static boolean isIsAlone() {
        return isAlone;
    }

    public static String getCooki(){

        return "JSESSIONID="+ SharedPreferencesUtils.getParam(getAppContext(), MBapConstant.SPKey.JSESSIONID, "")+
                "; CASTGC="+ SharedPreferencesUtils.getParam(getAppContext(), MBapConstant.SPKey.CASTGC, "");

    }

    public static String getAuthorization(){

        return "CASTGC "+ SharedPreferencesUtils.getParam(getAppContext(), MBapConstant.SPKey.CASTGC, "");

    }

    public static String getCooki(Context context){

        if(context!=null){
            return "JSESSIONID="+ SharedPreferencesUtils.getParam(context, MBapConstant.SPKey.JSESSIONID, "")+
                    "; CASTGC="+ SharedPreferencesUtils.getParam(context, MBapConstant.SPKey.CASTGC, "");
        }

        return getCooki();

    }

    public static String getAuthorization(Context context){

        if(context!=null){
            return "CASTGC "+ SharedPreferencesUtils.getParam(context, MBapConstant.SPKey.CASTGC, "");
        }

        return getAuthorization();
    }


    public static String getUserName(){
        if(TextUtils.isEmpty(userName)){
            userName = SharedPreferencesUtils.getParam(getAppContext(), MBapConstant.SPKey.USER_NAME, "");
        }

        return userName;

    }


    public static String getPassword(){
        if(TextUtils.isEmpty(password)){
            password = SharedPreferencesUtils.getParam(getAppContext(), MBapConstant.SPKey.PWD, "");
        }

        return password;

    }

    public static void setUserName(String userName) {
        MBapApp.userName = userName;
        SharedPreferencesUtils.setParam(getAppContext(), MBapConstant.SPKey.USER_NAME, userName);
    }

    public static void setPassword(String password) {
        MBapApp.password = password;
        SharedPreferencesUtils.setParam(getAppContext(), MBapConstant.SPKey.PWD, password);
    }

    public static String getIp() {
        if(TextUtils.isEmpty(ip)){
            ip = SharedPreferencesUtils.getParam(getAppContext(), MBapConstant.SPKey.IP, "");
        }

        return ip;
    }

    public static void setIp(String ip) {
        MBapApp.ip = ip;
        SharedPreferencesUtils.setParam(getAppContext(), MBapConstant.SPKey.IP, ip);
    }

    public static String getPort() {
        if(TextUtils.isEmpty(port)){
            port = SharedPreferencesUtils.getParam(getAppContext(), MBapConstant.SPKey.PORT, "");
        }
        return port;
    }

    public static void setPort(String port) {
        MBapApp.port = port;
        SharedPreferencesUtils.setParam(getAppContext(), MBapConstant.SPKey.PORT, port);
    }

    public static String getUrl() {
        if(TextUtils.isEmpty(url)){
            url = SharedPreferencesUtils.getParam(getAppContext(), MBapConstant.SPKey.URL, "");
        }
        return url;
    }

    public static void setUrl(String url) {
        MBapApp.url = url;
        SharedPreferencesUtils.setParam(getAppContext(), MBapConstant.SPKey.URL, url);
    }

    public static void setIsLogin(boolean isLogin) {
        MBapApp.isLogin = isLogin;
        SharedPreferencesUtils.setParam(getAppContext(), MBapConstant.SPKey.LOGIN, isLogin);
    }

    public static boolean isIsLogin() {
        isLogin = SharedPreferencesUtils.getParam(getAppContext(), MBapConstant.SPKey.LOGIN, false);
        return isLogin;
    }

}
