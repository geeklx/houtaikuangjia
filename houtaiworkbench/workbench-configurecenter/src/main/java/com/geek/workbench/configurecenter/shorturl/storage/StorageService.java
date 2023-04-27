package com.geek.workbench.configurecenter.shorturl.storage;

import java.util.Date;

/**
 * @Description 持久化存储服务
 * @Author gaojian
 * @Date 2022/1/17 10:47
 * @Version V1.0
 */
public interface StorageService {

    /**
     * 描述:  持久化
     * @createDate: 2022/1/17 10:50
     * @author: gaojian
     * @modify:
     * @param shortUrl
     * @param LongUrl
     * @param startTime
     * @param validTime
     * @return: void
     */
    void saveMapping(String shortUrl, String LongUrl, Date startTime,Long validTime);

    /**
     * 描述:  保存发号器发到了多少号
     * @createDate: 2022/1/17 10:52
     * @author: gaojian
     * @modify:
     * @param numberSenderKey
     * @param number
     * @return: void
     */
    void saveNumberSender(String numberSenderKey,Long number);
}
