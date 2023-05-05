package com.fosung.workbench.util;

import com.fosung.framework.common.util.UtilDigest;
import com.fosung.framework.common.util.UtilString;
import com.mzlion.easyokhttp.request.TextBodyRequest;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.Assert;

/**
 * 对接网关 生成 headers
 *
 * @Author DBin
 * @Date 2021/9/24 11:18
 */
public class UnifiedRequestUtils {

    /**
     * 描述:  版本号
     * @createDate: 2021/10/14 14:55
     * @author: gaojian
     */
    private static String signVersion = "V1";

    public static TextBodyRequest formatHeadersReturn(String accessKey, String accessSecret, TextBodyRequest httpRequest){

        if (UtilString.isBlank(accessKey) || UtilString.isBlank(accessSecret)) {
            return httpRequest;
        }

        //时间戳
        long timestamp = System.currentTimeMillis();
        Assert.isTrue((timestamp + "").length() == 13, "时间戳长度不正确");

        //生成6位随机字符串
        String randomString = RandomStringUtils.randomAlphanumeric(6);

        /**
         * 签名生成规则：
         * 时间戳13位 + 随机字符串6位 + accessSecret 拼接经MD5加密
         * 后前缀增加签名版本号两位（V1），
         * 将得到的字符串所有字符转换为大写，作为签名参数。
         */
        String str = timestamp + randomString + accessSecret;
        String md5Str = UtilDigest.encodeMD5(str);

        String signature = (signVersion + md5Str).toUpperCase();

        httpRequest.header("X-CA-KEY",accessKey);
        httpRequest.header("X-CA-TIMESTAMP",String.valueOf(timestamp));
        httpRequest.header("X-CA-SIGNATURE",signature);
        httpRequest.header("X-CA-NONCE",randomString);
        return httpRequest;
    }

    public static void formatHeaders(String accessKey, String accessSecret, TextBodyRequest httpRequest){

        if (UtilString.isBlank(accessKey) || UtilString.isBlank(accessSecret)) {
            return;
        }

        //时间戳
        long timestamp = System.currentTimeMillis();
        Assert.isTrue((timestamp + "").length() == 13, "时间戳长度不正确");

        //生成6位随机字符串
        String randomString = RandomStringUtils.randomAlphanumeric(6);
        /**
         * 签名生成规则：
         * 时间戳13位 + 随机字符串6位 + accessSecret 拼接经MD5加密
         * 后前缀增加签名版本号两位（V1），
         * 将得到的字符串所有字符转换为大写，作为签名参数。
         */
        String str = timestamp + randomString + accessSecret;
        String md5Str = UtilDigest.encodeMD5(str);

        String signature = (signVersion + md5Str).toUpperCase();

        httpRequest.header("X-CA-KEY",accessKey);
        httpRequest.header("X-CA-TIMESTAMP",String.valueOf(timestamp));
        httpRequest.header("X-CA-SIGNATURE",signature);
        httpRequest.header("X-CA-NONCE",randomString);
    }
}
