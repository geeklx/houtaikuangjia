package com.fosung.workbench.configurecenter.controller;

import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.dto.common.StatisticalDto;
import com.fosung.workbench.service.common.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 统计服务控制层
 * @Author gaojian
 * @Date 2021/10/15 11:08
 * @Version V1.0
 */
@RestController
@RequestMapping(value = StatisticalController.BASE_PATH)
public class StatisticalController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/workbench/statistical" ;
    
    /**
     * 描述:  注入统计服务
     * @createDate: 2021/10/15 11:33
     * @author: gaojian
     */
    @Autowired
    private StatisticalService statisticalService;

    /**
     * 描述: 安装应用统计信息
     * @createDate: 2021/10/14 16:58
     * @author: gaojian
     * @modify:
     * @param params
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/install/app")
    public ResponseParam installApp(@RequestBody StatisticalDto params){

        // 1. 调用增加安装app统计方式
        statisticalService.installApp(params);

        // 2. 调用获取字典选项的方法
        return ResponseParam.success();
    }

    /**
     * 描述: 下载应用统计信息
     * @createDate: 2021/10/14 16:58
     * @author: gaojian
     * @modify:
     * @param params
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/download/app")
    public ResponseParam downloadApp(@RequestBody StatisticalDto params){

        // 1. 调用增加安装app统计方式
        statisticalService.downloadApp(params);

        // 2. 调用获取字典选项的方法
        return ResponseParam.success();
    }
}
