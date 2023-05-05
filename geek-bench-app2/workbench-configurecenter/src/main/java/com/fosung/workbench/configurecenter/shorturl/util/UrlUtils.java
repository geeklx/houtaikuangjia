package com.fosung.workbench.configurecenter.shorturl.util;

import java.io.UnsupportedEncodingException;

/**
 * @Description
 * @Author gaojian
 * @Date 2022/1/17 10:32
 * @Version V1.0
 */
public class UrlUtils {

    /**
     * 描述:  编译
     * @createDate: 2022/1/17 10:32
     * @author: gaojian
     * @modify:
     * @param str
     * @return: java.lang.String
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 描述:  解译
     * @createDate: 2022/1/17 10:32
     * @author: gaojian
     * @modify:
     * @param str
     * @return: java.lang.String
     */
    public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
