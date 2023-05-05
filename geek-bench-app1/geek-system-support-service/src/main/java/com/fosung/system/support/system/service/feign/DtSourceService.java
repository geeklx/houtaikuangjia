package com.fosung.system.support.system.service.feign;

import com.fosung.framework.web.http.ResponseParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 *
 * 获取灯塔组织用户信息
 *
 * @author liuke
 * @date  2022/2/8 13:59
 * @version
*/
@FeignClient(value = "dt",url = "${app.pbs.client.url:http://172.18.223.229:8999/}")
@Component
public interface DtSourceService {


    @PostMapping("/dt/api/cloud/user/get/identifyId")
    Map<String,Object> queryDtUserDetail(@RequestParam("identifyId") String identifyId);

    @PostMapping("/dt/api/cloud/org/root")
    List<Map<String,Object>> queryDtRootOrg();

    @PostMapping("/dt/api/cloud/org/suborg")
    List<Map<String,Object>> queryDtSubOrg(@RequestParam("parentId") String parentId);
}
