package com.geek.system.pbs.api.oldapi;

import com.fosung.framework.web.http.ResponseParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Desc
 * @Author zyy
 * @Date 2021/5/10 20:03
 */
@FeignClient(name = "CloudAnnounceApi", url = "${app.pbs.client.url}")
@RequestMapping("/{source}/api/cloud/announce")
public interface CloudAnnounceApi {

    @PostMapping("/add")
    ResponseParam add(@PathVariable("source") String source, @RequestBody Map<String, Object> param);

    @PostMapping("/delete")
    ResponseParam delete(@PathVariable("source") String source, @RequestParam("announceId") Long announceId);
}
