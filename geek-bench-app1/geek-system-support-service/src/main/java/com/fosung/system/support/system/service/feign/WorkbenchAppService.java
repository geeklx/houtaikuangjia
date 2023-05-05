package com.fosung.system.support.system.service.feign;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.Map;

@FeignClient(name = "workbenchadmin")
@Component
public interface WorkbenchAppService {

    @RequestLine("POST")
    Map<String,Object> getWorkbenchApp(URI uri);
}
