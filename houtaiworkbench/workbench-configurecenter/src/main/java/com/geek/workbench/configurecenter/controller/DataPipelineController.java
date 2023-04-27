package com.geek.workbench.configurecenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.dto.pipeline.MandateParams;
import com.geek.workbench.service.common.DataPipelineService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 数据同步接口
 * @Author gaojian
 * @Date 2021/10/14 16:56
 * @Version V1.0
 */
@RestController
@RequestMapping(value = DataPipelineController.BASE_PATH)
public class DataPipelineController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/data/pipeline" ;

    @Autowired
    private DataPipelineService dataPipelineService;

    /**
     * 描述: 拉取应用信息
     * @createDate: 2021/10/14 16:58
     * @author: gaojian
     * @modify:
     * @param jsonObject
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/pull/application")
    @OptLog(optModule="数据同步",optName="应用同步",optType= OptLogConst.SAVE )
    public ResponseParam pullApplication(@RequestBody JSONObject jsonObject){


        // 2. 调用获取字典选项的方法
        return ResponseParam.success();
    }

    /**
     * 描述: 拉取授权信息
     * @createDate: 2021/10/14 16:58
     * @author: gaojian
     * @modify:
     * @param mandateParams
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/pull/permission")
    @OptLog(optModule="数据同步",optName="权限同步",optType= OptLogConst.SAVE )
    public ResponseParam pullPermission(@Validated @RequestBody MandateParams mandateParams){

        // 1. 判断数据信息
        if(StringUtils.isBlank(mandateParams.getAppId())){
            throw new AppException(MessageContent.APP_ID_IS_NULL);
        }

        // 2. 调用保存授权同步的数据
        dataPipelineService.pullPermission(mandateParams);

        // 3. 调用获取字典选项的方法
        return ResponseParam.success();
    }
}
