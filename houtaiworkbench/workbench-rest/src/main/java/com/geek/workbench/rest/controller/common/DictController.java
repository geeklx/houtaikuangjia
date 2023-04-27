package com.geek.workbench.rest.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.pbs.api.SystemSysApi;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.rest.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Description 字典服务控制层
 * @Author gaojian
 * @Date 2021/10/14 14:49
 * @Version V1.0
 */
@RestController
@RequestMapping(value= DictController.BASE_PATH)
public class DictController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/common/dict" ;


    public static final String DICT_KEY_PREFIX = "workbeach:dict:";

    /**
     * 描述:  注入字典服务fengin接口
     * @createDate: 2021/10/14 16:18
     * @author: gaojian
     */
    @Autowired
    private SystemSysApi systemSysApi;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 描述:  根据字典类别获取字典选项信息
     * @createDate: 2021/10/14 16:19
     * @author: gaojian
     * @modify:
     * @param
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("query/option/{dictType}")
    public ResponseParam queryDict(@PathVariable(value = "dictType") String dictType){

        // 1. 字典类型非空判断
        if(StringUtils.isBlank(dictType)){
            throw new AppException(MessageContent.DICT_TYPE_IS_NULL);
        }

        // 2. 调用获取字典选项的方法
        String redisResult = stringRedisTemplate.opsForValue().get(DICT_KEY_PREFIX + dictType);
        ResponseParam result;
        if(StringUtils.isBlank(redisResult)){
            result = systemSysApi.querySysTemDict(dictType);
            stringRedisTemplate.opsForValue().set(DICT_KEY_PREFIX + dictType,JSONObject.toJSONString(result), RedisUtils.getRandomTime(), TimeUnit.HOURS);
        }else{
            result = JSONObject.parseObject(redisResult,ResponseParam.class);
        }
        return result;
    }
}
