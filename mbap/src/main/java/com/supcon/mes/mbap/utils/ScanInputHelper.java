package com.supcon.mes.mbap.utils;

import android.text.TextUtils;

import com.supcon.common.view.util.LogUtil;
import com.supcon.mes.mbap.utils.cache.CacheUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wangshizhan on 2017/9/26.
 * Email:wangshizhan@supcon.com
 */

public class ScanInputHelper {

    public static final String WORDS_CACHE = "wordsCache";

    private static Set<String> words = new HashSet<>();

    static {

        String cache = CacheUtil.getString(WORDS_CACHE);
        if(!TextUtils.isEmpty(cache)) {
            String strings[] = cache.split(" ");
            words.addAll(Arrays.asList(strings));
        }
    }

    public static Set<String> getWords() {
        return words;
    }

    public static String[] getWordsArray() {
        return words.size()==0?new String[]{}:words.toArray(new String[words.size()]);
    }

    public static synchronized void addWord(String word){

        if(words.add(word)){
            CacheUtil.putString(WORDS_CACHE, setToString());
        }

    }


    public static List<String> findWords(String word){

        List<String> results = new ArrayList<>();

        for(String s: words){
            if (s.equals(word) || s.contains(word) ){
                results.add(s);
            }
        }

        return results;
    }


    private static String setToString(){

        StringBuilder sb = new StringBuilder();

        for(String s :words){
            sb.append(s);
            sb.append(" ");
        }
//        LogUtil.d("words:"+sb.toString().trim());
        return sb.toString().trim();
    }
}
