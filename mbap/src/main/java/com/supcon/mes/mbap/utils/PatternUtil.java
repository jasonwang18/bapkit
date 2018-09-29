package com.supcon.mes.mbap.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangshizhan on 2017/10/16.
 * Email:wangshizhan@supcon.com
 */

public class PatternUtil {

    public static final String IP_CHECK = "^(\\d|[1-9]\\d|1\\d{2}|2[0-5][0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-5][0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-5][0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-5][0-5])$";
//    private static final String PORT_CHECK = "([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-5]{2}[0-3][0-5])";
    public static final String PORT_CHECK = "^((\\d{0,4})|([1-5]\\d{1,4})|(6[0-4]\\d{1,3})|(65[0-4]\\d{1,2})|(655[0-2]\\d)|(6553[0-5]))$";
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9_\\u4e00-\\u9fa5\\-]{1,}$";
//    public static final String URL_PATTERN = "^((http://)|(https://))?([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}(/)";
    public static final String URL_PATTERN = "^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$";

    public static boolean checkInput(String input, String pattern){

        if(TextUtils.isEmpty(input)){
            return false;
        }

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);

        while (m.find()) {
            return true;
        }

        return false;
    }

    public static boolean checkIP(String ip){

        if(TextUtils.isEmpty(ip)){
            return false;
        }

        if(ip.length()<7 || ip.length()>15){
            return false;
        }

        String[] strings = ip.split("\\.");
        for(String s: strings){
            if(s.length()>3){
                return false;
            }

            try {
                int ipSingle = Integer.valueOf(s);
                if(ipSingle > 255 || ipSingle == 0){
                    return false;
                }
            }
            catch (NumberFormatException e){
                return false;
            }
        }

        Pattern p = Pattern.compile(IP_CHECK);
        Matcher m = p.matcher(ip);

        while (m.find()) {
            return true;
        }

        return false;
    }

    public static boolean checkPort(String port){

        if(TextUtils.isEmpty(port)){
            return false;
        }

        Pattern p = Pattern.compile(PORT_CHECK);
        Matcher m = p.matcher(port);

        while (m.find()) {
            return true;
        }

        return false;
    }

    public static boolean checkUserName(String userName){

        if(TextUtils.isEmpty(userName)){
            return false;
        }

        Pattern p = Pattern.compile(USERNAME_PATTERN);
        Matcher m = p.matcher(userName);

        while (m.find()) {
            return true;
        }
        return false;
    }

}
