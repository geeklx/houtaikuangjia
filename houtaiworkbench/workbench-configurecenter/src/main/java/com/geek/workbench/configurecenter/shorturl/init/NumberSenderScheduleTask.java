package com.geek.workbench.configurecenter.shorturl.init;

import com.geek.workbench.configurecenter.shorturl.property.ShortUrlProperty;
import com.geek.workbench.configurecenter.shorturl.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Description 发号器值更新任务
 * @Author gaojian
 * @Date 2022/1/17 16:59
 * @Version V1.0
 */
@Component
@EnableScheduling
@Slf4j
public class NumberSenderScheduleTask {

    /**
     * 描述:  持久化服务
     * @createDate: 2022/1/17 17:02
     * @author: gaojian
     */
    @Autowired
    private StorageService storageService;

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
     * 描述: 更新发号器任务
     * @createDate: 2022/1/17 17:00
     * @author: gaojian
     */
    @Scheduled(cron = "0/10 * * * * ?")
    private void updateNumberSenderTasks() {

        // 1. 更新发号器值
        String number = stringRedisTemplate.opsForValue().get(shortUrlProperty.getNumberSenderKey());
        storageService.saveNumberSender(shortUrlProperty.getNumberSenderKey(),Long.valueOf(number));
        log.info("发号器值更新定时任务执行: " + LocalDateTime.now() + ", 值为：" + number);
    }
}
