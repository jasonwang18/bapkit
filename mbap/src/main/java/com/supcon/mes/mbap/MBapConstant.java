package com.supcon.mes.mbap;

/**
 * Created by wangshizhan on 2017/8/11.
 * Email:wangshizhan@supcon.com
 */

public interface MBapConstant {

    int UNHANDLE = 0;
    int HANDLE = 1;
    int CONTENT_CLEAN = -1;

    interface SPKey{

        String LOGIN = "login";
        String IP = "ip";
        String PORT = "port";
        String URL = "url";
        String USER_NAME = "username";
        String PWD  = "pwd";
        String JSESSIONID = "JSESSIONID";
        String CASTGC  = "CASTGC";

        String WORKS = "works";

        String URL_ENABLE = "URL_ENABLE";
        String QR_CODE_ENABLE = "QR_CODE_ENABLE";
        String OFFLINE_ENABLE = "OFFLINE_ENABLE";

    }

    interface Cache{

        String WORDS_CACHE = "wordsCache";

    }




}
