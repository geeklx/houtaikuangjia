package com.fosung.workbench.configurecenter.shorturl.init;

import com.fosung.workbench.configurecenter.shorturl.property.ShortUrlProperty;
import com.fosung.workbench.entity.shorturl.NumberSenderEntity;
import com.fosung.workbench.entity.shorturl.ShortUrlMappingEntity;
import com.fosung.workbench.service.shorturl.NumberSenderService;
import com.fosung.workbench.service.shorturl.ShortUrlMappingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description 容器启动初始化
 * @Author gaojian
 * @Date 2022/1/17 16:55
 * @Version V1.0
 */
@Component
@Slf4j
public class DataRecoveryRunner implements CommandLineRunner {

    /**
     * 描述:  永久
     * @createDate: 2022/1/17 11:07
     * @author: gaojian
     */
    private static final Integer PERMANENT = -1;

    /**
     * 描述:  发号器持久化服务
     * @createDate: 2022/1/17 17:41
     * @author: gaojian
     */
    @Autowired
    private NumberSenderService numberSenderService;

    /**
     * 描述:  映射持久化服务
     * @createDate: 2022/1/17 17:41
     * @author: gaojian
     */
    @Autowired
    private ShortUrlMappingService shortUrlMappingService;

    /**
     * 描述:  配置属性信息
     * @createDate: 2022/1/17 17:41
     * @author: gaojian
     */
    @Autowired
    private ShortUrlProperty shortUrlProperty;

    /**
     * 描述:  redis服务
     * @createDate: 2022/1/17 17:41
     * @author: gaojian
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 描述:  初始化发号器信息
     * @createDate: 2022/1/17 17:33
     * @author: gaojian
     * @modify:
     * @param
     * @return: void
     */
    public void initNumberSender(){

        // 1. 查询是否有发号器持久化值
        Map<String,Object> searchParams = new HashMap<>(8);
        searchParams.put("numberSenderKey",shortUrlProperty.getNumberSenderKey());
        List<NumberSenderEntity> list = numberSenderService.queryAll(searchParams);

        // 2. 如果有发号器持久化值则使用发号器持久化值
        if(list != null && list.size() > 0){
            stringRedisTemplate.opsForValue().set(shortUrlProperty.getNumberSenderKey(),list.get(0).getNumber().toString());
        }else{
            NumberSenderEntity numberSenderEntity = new NumberSenderEntity();
            numberSenderEntity.setNumberSenderKey(shortUrlProperty.getNumberSenderKey());
            numberSenderEntity.setNumber(Long.valueOf(shortUrlProperty.getNumberSenderInit()));
            numberSenderService.save(numberSenderEntity);
            stringRedisTemplate.opsForValue().set(shortUrlProperty.getNumberSenderKey(),shortUrlProperty.getNumberSenderInit());
        }
    }

    /**
     * 描述:  初始化映射关系
     * @createDate: 2022/1/17 17:33
     * @author: gaojian
     * @modify:
     * @param
     * @return: void
     */
    public void initMappingRedis(){

        // 1. 查询全部
        Map<String,Object> searchParams = new HashMap<>(8);
        searchParams.put("shortUrl",shortUrlProperty.getPrefix().replace("/","//"));
        List<ShortUrlMappingEntity> list = shortUrlMappingService.queryAll(searchParams);

        // 2. 循环信息
        Map<String,String> permanent = new HashMap<>(1024);
        Date nowTime = new Date();
        list.forEach(shortUrlMappingEntity -> {

            // 2.1 永久的
            if(PERMANENT.equals(shortUrlMappingEntity.getValidTime())){
                permanent.put(shortUrlProperty.getShortUrlKeyPrefix() + ":" + shortUrlMappingEntity.getShortUrl()
                        ,shortUrlMappingEntity.getLongUrl());
            }else{

                // 2.2 未过期的
                Date expire = DateUtils.addSeconds(shortUrlMappingEntity.getStartTime(),shortUrlMappingEntity.getValidTime());
                if(nowTime.before(expire)){
                    Long remainSeconds = Duration.between(nowTime.toInstant(),expire.toInstant()).getSeconds();
                    Boolean result =stringRedisTemplate.opsForValue().setIfAbsent(
                            shortUrlProperty.getShortUrlKeyPrefix() + ":" + shortUrlMappingEntity.getShortUrl(),
                            shortUrlMappingEntity.getLongUrl());
                    if(result){
                        stringRedisTemplate.expire(shortUrlProperty.getShortUrlKeyPrefix() + ":" + shortUrlMappingEntity.getShortUrl(),
                                remainSeconds,TimeUnit.SECONDS);
                    }
                }
            }
        });

        stringRedisTemplate.opsForValue().multiSetIfAbsent(permanent);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {

        // 1. 初始化发号器信息
        initNumberSender();

        // 1. 初始化映射关系到Redis
        initMappingRedis();
        log.info("容器初始化完毕");
    }
}