package com.geek.workbench.service.feign;

import com.fosung.framework.web.http.ResponseParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description 菜单获取服务
 * @Author gaojian
 * @Date 2021/10/14 16:24
 * @Version V1.0
 */
@FeignClient(value = "${app.gwapi.name:server}",url = "${app.pbs.client.url:http://10.7.211.36:6253/}")
@Component
public interface MenuService {

    /**
     * 描述:  查询用户菜单
     * @createDate: 2021/10/14 16:09
     * @author: gaojian
     * @modify:
     * @param userId
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/sys/api/cloud/user/queryResourceByAppCodeAndUserIdAndRole")
    ResponseParam queryData(@RequestParam("appCode") String appCode,@RequestParam("userId") String userId,@RequestParam("roleId") String roleId);
}
