package com.fosung.workbench.service.feign;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.web.http.ResponseParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lihuiming
 * @className: GwapiUmsBindService
 * @description: 统一消息绑定接口
 * @date 2022/3/115:44
 */
@FeignClient(value = "${app.gwapi.name:server}",url = "${app.globalParams.umsUrl:http://10.7.211.201:5001}", path = "/gwapi/umsapi/api/ums")
@Component
public interface GwapiUmsBindService {
    @PostMapping("/bind")
    ResponseParam umsbind(@RequestBody JSONObject jsonObject);
}
