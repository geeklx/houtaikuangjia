package com.fosung.workbench.service.feign;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.web.http.ResponseParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description 视频会议服务接口
 * @Author gaojian
 * @Date 2022/6/6 11:08
 * @Version V1.0
 */
@FeignClient(value = "${app.gwapi.name:server}",url = "${app.globalParams.umsUrl:http://10.7.211.201:5001}", path = "/gwapi/videomeeting/api")
@Component
public interface VideoMeetingService  {

    /**
     * 描述:  视频会议初始化接口
     * @createDate: 2022/6/6 11:11
     * @author: gaojian
     * @modify:
     * @param jsonObject
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/init/login")
    ResponseParam initLogin(@RequestBody JSONObject jsonObject);
}
