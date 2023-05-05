package com.fosung.workbench.service.feign;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.web.http.ResponseParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lihuiming
 * @className GetLoginService
 * @description: 获取肥城项目登录接口
 * @date 2022/2/2817:42
 */
@FeignClient(value = "${app.gwapi.name:server}",url = "${app.dtxz.url:http://t-gw-proxy.xczx-jn.com/}")
@Component
public interface GetLoginService {
    @PostMapping("/cloud_sys/api/login")
    ResponseParam loginXz(@RequestBody JSONObject jsonObject);
}
