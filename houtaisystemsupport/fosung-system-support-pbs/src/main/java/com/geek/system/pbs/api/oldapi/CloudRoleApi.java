package com.geek.system.pbs.api.oldapi;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.fosung.framework.web.http.ResponseParam;

import java.util.Map;

@FeignClient(name = "CloudRoleApi", url = "${app.pbs.client.url}")
@RequestMapping("/{source}/api/cloud/role")
public interface CloudRoleApi {

    @PostMapping("/list")
    ResponseParam getRole(@PathVariable("source") String source);

    /**
     *根据appId获取角色
     *
     * @param source
     * @param appId
     * @date 2021/4/28 13:49
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/listByApp")
    ResponseParam getRoleByApp(@PathVariable("source") String source,@RequestParam("appId") Long appId);

    @PostMapping("/queryRoleMenu")
    JSONObject queryRoleMenu(@PathVariable("source") String source,@RequestBody Map<String, Object> param);
}
