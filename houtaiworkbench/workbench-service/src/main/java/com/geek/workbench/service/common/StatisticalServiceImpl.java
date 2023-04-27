package com.geek.workbench.service.common;

import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.dto.common.StatisticalDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description 统计服务实现类
 * @Author gaojian
 * @Date 2021/10/15 11:31
 * @Version V1.0
 */
@Service
public class StatisticalServiceImpl implements StatisticalService {

    /**
     * 描述:  递增值
     * @createDate: 2021/10/15 13:50
     * @author: gaojian
     * @modify:
     */
    private static final Long INCREASE_VALUE = 1L;

    /**
     * 描述:  注入redis服务
     * @createDate: 2021/10/15 13:44
     * @author: gaojian
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 描述:  安装APP数量统计
     *
     * @param params
     * @createDate: 2021/10/15 11:31
     * @author: gaojian
     * @modify:
     * @return: void
     */
    @Override
    public void installApp(StatisticalDto params) {

        String key = GlobalVariableKey.INSTALL_REDIS_PATH_PREFIX + ":" +
                (StringUtils.isBlank(params.getType()) ? "ALL" : params.getType()) + ":" + params.getAppId();
        stringRedisTemplate.opsForValue().increment(key, INCREASE_VALUE);
    }

    /**
     * 描述:  下载APP数量统计
     *
     * @param params
     * @createDate: 2021/10/15 11:31
     * @author: gaojian
     * @modify:
     * @return: void
     */
    @Override
    public void downloadApp(StatisticalDto params) {

        String key = GlobalVariableKey.DOWNLOAD_REDIS_PATH_PREFIX + ":" +
                (StringUtils.isBlank(params.getType()) ? "ALL" : params.getType()) + ":" + params.getAppId();
        stringRedisTemplate.opsForValue().increment(key, INCREASE_VALUE);
    }

}
