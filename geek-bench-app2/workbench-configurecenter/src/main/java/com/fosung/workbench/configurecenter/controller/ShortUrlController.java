package com.fosung.workbench.configurecenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.configurecenter.shorturl.service.ShortUrlService;
import com.fosung.workbench.dto.shorturl.ShortUrlDto;
import com.netflix.hystrix.strategy.concurrency.HystrixLifecycleForwardingRequestVariable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description 短 URL 控制层
 *
 * @Author gaojian
 * @Date 2022/1/17 11:38
 * @Version V1.0
 */
@RestController
@RequestMapping(value = ShortUrlController.BASE_PATH)
public class ShortUrlController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/short/url" ;

    /**
     * 描述:  短 URL 服务
     * @createDate: 2022/1/17 11:40
     * @author: gaojian
     */
    @Autowired
    private ShortUrlService shortUrlService;

    /**
     * 描述:  
     * @createDate: 2022/1/17 11:39
     * @author: gaojian
     * @modify:
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/convert")
    public ResponseParam convert(@Validated @RequestBody ShortUrlDto shortUrlDto){

        // 1. 获取短URL
        String shortUrl;
        if(shortUrlDto.getValidityTime() == null){
            shortUrl = shortUrlService.convertShortUrl(shortUrlDto.getLongUrl());
        }else{
            shortUrl = shortUrlService.convertShortUrl(shortUrlDto.getLongUrl(),
                    shortUrlDto.getValidityTime(),
                    TimeUnit.SECONDS);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("shortUrl",shortUrl);
        return ResponseParam.success().message("获取信息成功").data(jsonObject);
    }

    /**
     * 描述:  短网址重定向到长网址
     * @createDate: 2022/1/17 16:31
     * @author: gaojian
     * @modify:
     * @param request
     * @return: org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping(value = "/{code}" , method = {RequestMethod.GET,RequestMethod.POST})
    public RedirectView shortUrlRedirectLongUrl(@PathVariable String code,
                                                @RequestBody(required = false) JSONObject jsonObject,
                                                RedirectAttributes redirectAttributes,
                                                HttpServletRequest request){

        // 1. 获取URL
        String longUrl = shortUrlService.getLongUrlByShortUrl(code);
        if(StringUtils.isBlank(longUrl)){
            throw new AppException("未获取到映射的URL地址");
        }

        // 2. 重定向url
        RedirectView redirectView = new RedirectView(longUrl);
        redirectView.setServletContext(request.getServletContext());
        redirectAttributes.addAllAttributes(jsonObject);
        return redirectView;
    }
}
