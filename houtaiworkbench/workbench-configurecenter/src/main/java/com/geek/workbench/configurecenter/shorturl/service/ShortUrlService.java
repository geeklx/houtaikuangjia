package com.geek.workbench.configurecenter.shorturl.service;

import java.util.concurrent.TimeUnit;

/**
 * @Description 短链接服务
 * @Author gaojian
 * @Date 2022/1/17 8:48
 * @Version V1.0
 */
public interface ShortUrlService {

    /**
     * 描述:  长网址转短网址
     * @createDate: 2022/1/17 11:12
     * @author: gaojian
     * @modify:
     * @param longUrl
     * @return: java.lang.String
     */
    String convertShortUrl(String longUrl);

    /**
     * 描述:  长网址转短网址 并设置有效期
     * @createDate: 2022/1/17 11:12
     * @author: gaojian
     * @modify:
     * @param longUrl
     * @param timeOut
     * @param timeUnit
     * @return: java.lang.String
     */
    String convertShortUrl(String longUrl, Long timeOut, TimeUnit timeUnit);

    /**
     * 描述:  根据短网址获取长网址
     * @createDate: 2022/1/17 8:53
     * @author: gaojian
     * @modify:
     * @param shortCode
     * @return: java.lang.String
     */
    String getLongUrlByShortUrl(String shortCode);
}
