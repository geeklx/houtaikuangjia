package com.geek.workbench.rest.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.secure.auth.AppUserDetails;
import com.fosung.framework.common.secure.auth.AppUserDetailsService;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.rest.util.RedisUtils;
import com.geek.workbench.service.feign.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Description 菜单服务控制层
 * @Author gaojian
 * @Date 2021/10/14 14:49
 * @Version V1.0
 */
@RestController
@RequestMapping(value= MenuController.BASE_PATH)
public class MenuController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/common/menu" ;

    /**
     * 描述:  注入菜单服务fengin接口
     * @createDate: 2021/10/14 16:18
     * @author: gaojian
     */
    @Autowired
    private MenuService menuService;

    /**
     * 描述:  应用编码
     * @createDate: 2021/11/10 14:26
     * @author: gaojian
     */
    @Value(value = "${app.customParams.appCode:fosung_workbeach}")
    private String appCode;

    /**
     * 描述:  注入登录用户详情服务类
     * @createDate: 2021/10/13 18:03
     * @author: gaojian
     */
    @Autowired
    private AppUserDetailsService appUserDetailsService ;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 描述:  根据登录用户获取菜单
     * @createDate: 2021/10/14 16:19
     * @author: gaojian
     * @modify:
     * @param
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("query")
    public ResponseParam queryMenu(@RequestBody JSONObject params){

        // 1. 获取当前登录用户信息
        AppUserDetails appUserDetails = appUserDetailsService.getAppUserDetails();
        if( appUserDetails == null){
            throw new UsernameNotFoundException(MessageContent.LOGIN_USER_IS_NULL);
        }

        // 2. 组装登录用户id参数信息
        String userId = appUserDetails.getUserId();
        String roleId = params.getString("roleId");
        if(StringUtils.isBlank(roleId)){
            throw new AppException(MessageContent.ROLE_ID_IS_NULL,"400");
        }

        // 3. 调用获取菜单选项的方法
        String redisResult = stringRedisTemplate.opsForValue().get(roleId);
        ResponseParam result;
        if(StringUtils.isBlank(redisResult)){
            result =  menuService.queryData(appCode,userId,roleId);
            stringRedisTemplate.opsForValue().set(roleId,JSONObject.toJSONString(result), RedisUtils.getRandomTime(), TimeUnit.HOURS);
        }else{
            result = JSONObject.parseObject(redisResult,ResponseParam.class);
        }
        return result;
    }
}
