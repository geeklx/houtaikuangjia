package com.fosung.workbench.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *
 * 缓存构建
 *
 * @author liuke
 * @date  2021/10/15 14:01
 * @version
*/
@Slf4j
public class CacheUtil {

    public static CacheBuilder getInstance(){
        return CacheBuilder.newBuilder()
                //.expireAfterWrite(4, TimeUnit.HOURS)

                // 本地缓存5分钟 30秒
                .expireAfterWrite(30, TimeUnit.SECONDS)
                //设置缓存的移除通知
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        log.info(notification.getKey() + " was removed, cause is " + notification.getCause());
                    }
                });
    }

}
