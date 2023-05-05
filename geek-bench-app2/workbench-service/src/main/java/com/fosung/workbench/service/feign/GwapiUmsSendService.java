package com.fosung.workbench.service.feign;

import com.fosung.cloud.ums.rest.api.UmsSendApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @Description 网关统一消息服务
 * @Author gaojian
 * @Date 2022/2/21 9:17
 * @Version V1.0
 *
 * 通过继承重写注解信息改为调用网关服务
 */
@FeignClient(
        name = "${app.gwapi.name:workbench-server}",
        url = "${app.gwapi.url:http://10.7.211.201:5001}",
        path = "/gwapi/umsapi/api/ums/send"
)
@Component
public interface GwapiUmsSendService extends UmsSendApi {

}
