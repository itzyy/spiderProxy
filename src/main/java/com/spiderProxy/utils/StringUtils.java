package com.spiderProxy.utils;

/**
 * Created by Zouyy on 2017/10/13.
 */
public class StringUtils {

    /**
     * 判断是否为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        if(str!=null && str.length()>0){
            return true;
        }else{
            return false;
        }
    }
}
