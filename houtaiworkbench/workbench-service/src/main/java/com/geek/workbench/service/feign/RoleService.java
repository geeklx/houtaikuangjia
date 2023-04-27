package com.geek.workbench.service.feign;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.web.http.ResponseParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description 根据角色获取用户服务
 * @Author gaojian
 * @Date 2021/10/14 16:24
 * @Version V1.0
 */
@FeignClient(value = "${app.gwapi.name:server}",url = "${app.pbs.client.url:http://10.7.211.36:6253/}")
@Component
public interface RoleService {

    /**
     * 描述:  查询用户
     * @createDate: 2021/10/14 16:09
     * @author: gaojian
     * @modify:
     * @param jsonObject
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/sys/api/cloud/user/org/role")
    ResponseParam queryData(@RequestBody JSONObject jsonObject);

    /**
     * 描述:  查询用户
     * @createDate: 2022/1/13 14:14
     * @author: gaojian
     * @modify:
     * @param roleCode
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/api/system/user/queryuser/byrole")
    ResponseParam queryData(@RequestParam("roleCode") String roleCode);


    /**
     * 描述:  根据角色查询获取app应用资源
     * @createDate: 2021/10/14 16:09
     * @author: gaojian
     * @modify:
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/api/system/user/query/app/by/role")
    ResponseParam queryApp(@RequestParam("appCode") String appCode,@RequestParam("roleId") String roleId);
}
