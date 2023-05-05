package com.fosung.workbench.configurecenter.shorturl.service.impl;

import com.fosung.framework.common.exception.AppException;
import com.fosung.workbench.configurecenter.shorturl.property.ShortUrlProperty;
import com.fosung.workbench.configurecenter.shorturl.service.ShortUrlService;
import com.fosung.workbench.configurecenter.shorturl.storage.StorageService;
import com.fosung.workbench.configurecenter.shorturl.util.ScaleConvert;
import com.fosung.workbench.configurecenter.shorturl.util.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Description 默认短链接服务
 * @Author gaojian
 * @Date 2022/1/17 8:55
 * @Version V1.0
 */
@Service
public class DefaultShortUrlService implements ShortUrlService {

    /**
     * 描述:  永久
     * @createDate: 2022/1/17 11:07
     * @author: gaojian
     */
    private static final Long PERMANENT = -1L;

    /**
     * 描述:  短url配置
     * @createDate: 2022/1/17 8:59
     * @author: gaojian
     */
    @Autowired
    private ShortUrlProperty shortUrlProperty;

    /**
     * 描述:  注入 redis 服务
     * @createDate: 2022/1/17 8:59
     * @author: gaojian
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 描述:  持久化服务
     * @createDate: 2022/1/17 10:55
     * @author: gaojian
     */
    @Autowired
    private StorageService storageService;

    /**
     * 描述: 长网址转短网址
     *
     * @param longUrl
     * @createDate: 2022/1/17 8:49
     * @author: gaojian
     * @modify:
     * @return: java.lang.String
     */
    @Override
    public String convertShortUrl(String longUrl) {

        return convertShortUrl(longUrl,shortUrlProperty.getDefaultSaveKeySecond(),TimeUnit.SECONDS);
    }

    /**
     * 描述:  长网址转短网址 并设置有效期
     *
     * @param longUrl
     * @param timeOut
     * @param timeUnit
     * @createDate: 2022/1/17 8:50
     * @author: gaojian
     * @modify:
     * @return: java.lang.String
     */
    @Override
    public String convertShortUrl(String longUrl, Long timeOut, TimeUnit timeUnit) {

        // 1. 判断url在过去指定时间内是否已映射过
        String encoderLongUrl = UrlUtils.getURLEncoderString(longUrl);
        if(!PERMANENT.equals(shortUrlProperty.getMaxLongUrlLength().longValue())
                && encoderLongUrl.length() > shortUrlProperty.getMaxLongUrlLength()){
            throw new AppException("URL超过服务能处理的最大长度，最大长度为：" + shortUrlProperty.getMaxLongUrlLength());
        }
        String longUrlKey = shortUrlProperty.getLongUrlKeyPrefix() + ":" + encoderLongUrl;
        String shortUrl = stringRedisTemplate.opsForValue().get(longUrlKey);

        // 2. 短url为空则说明未执行过，不为空则说明指定时间内执行过则直接返回
        if(StringUtils.isNotBlank(shortUrl)){
            stringRedisTemplate.expire(longUrlKey,shortUrlProperty.getRepetitionSecond(),TimeUnit.SECONDS);
            return shortUrl;
        }

        // 3. 获取 redis 发号器的值
        Long number = stringRedisTemplate.opsForValue().increment(shortUrlProperty.getNumberSenderKey(),shortUrlProperty.getNumberSenderStep());
        String shortCode = ScaleConvert.to62RadixString(number);

        // 4. 保存映射关系到redis中
        shortUrl = shortUrlProperty.getPrefix() + shortCode;
        if(PERMANENT.equals(timeOut)){
            stringRedisTemplate.opsForValue().set(shortUrlProperty.getShortUrlKeyPrefix() + ":" +  shortUrl , longUrl);
        }else{
            stringRedisTemplate.opsForValue().set(shortUrlProperty.getShortUrlKeyPrefix() + ":" +  shortUrl , longUrl,timeOut,timeUnit);
        }
        stringRedisTemplate.opsForValue().set(shortUrlProperty.getLongUrlKeyPrefix() + ":" + encoderLongUrl , shortUrl , shortUrlProperty.getRepetitionSecond(),TimeUnit.SECONDS);

        // 5. 保存映射关系到数据库中，进行持久化存储 -1 标识永久存储
        Long seconds = timeUnit.toSeconds(timeOut);
        storageService.saveMapping(shortUrl,longUrl,new Date(),seconds);
        return shortUrl;
    }

    /**
     * 描述:  根据短网址获取长网址
     *
     * @param shortCode
     * @createDate: 2022/1/17 8:53
     * @author: gaojian
     * @modify:
     * @return: java.lang.String
     */
    @Override
    public String getLongUrlByShortUrl(String shortCode) {

        // 1. 根据短 url 从 redis 中取出长 url
        String shortUrl = shortUrlProperty.getPrefix() + shortCode;
        String longUrl = stringRedisTemplate.opsForValue().get(shortUrlProperty.getShortUrlKeyPrefix() + ":" +  shortUrl);
        return longUrl;
    }
}
