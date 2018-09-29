package com.supcon.mes.mbap.utils;

/**
 * Created by wangshizhan on 2017/8/21.
 * Email:wangshizhan@supcon.com
 */

public class XmlUtil {

    public static String getStringByTag(String xml, String tag){

        String[] temp = {"<"+tag+">", "</"+tag+">"};


        int startIndex = xml.indexOf(temp[0])+temp[0].length();
        int endIndex = xml.indexOf(temp[1]);

        if(startIndex >= endIndex){

            return "";

        }

        return xml.substring(startIndex,endIndex);


    }


}
