package com.geek.workbench.rest.util;

import com.geek.workbench.util.CacheConstants;

/**
 * @Description redis 工具
 * @Author gaojian
 * @Date 2021/11/18 9:09
 * @Version V1.0
 */
public class RedisUtils {

    /**
     * 描述:  为防止redis缓存雪崩，获取随机 1 - 8 的过期时间
     * @createDate: 2021/11/3 16:51
     * @author: gaojian
     * @modify:
     * @param
     * @return: int
     */
    public static int getRandomTime(){

        int getRandomTime =  (int) (Math.random() * CacheConstants.EXPIRES_HOURS) + 1;
        return getRandomTime;
    }
}
