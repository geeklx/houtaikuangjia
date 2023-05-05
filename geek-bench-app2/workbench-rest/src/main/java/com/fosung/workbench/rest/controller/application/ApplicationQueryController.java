package com.fosung.workbench.rest.controller.application;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.system.pbs.api.SystemSysApi;
import com.fosung.workbench.common.GlobalVariableKey;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.application.ApplicationQueryDto;
import com.fosung.workbench.service.application.ApplicationQueryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 终端应用查询控制层
 * @Author gaojian
 * @Date 2021/10/22 19:21
 * @Version V1.0
 */
@RestController
@RequestMapping(value=ApplicationQueryController.BASE_PATH)
public class ApplicationQueryController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/application/query" ;

    /**
     * 描述:  依赖注入终端应用查询服务
     * @createDate: 2021/10/23 23:01
     * @author: gaojian
     */
    @Autowired
    private ApplicationQueryService applicationQueryService ;

    /**
     * 描述:  依赖注入字典服务
     * @createDate: 2021/10/23 23:01
     * @author: gaojian
     */
    @Autowired
    private SystemSysApi systemSysApi;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="应用中心-应用查询",optName="查询终端应用信息",optType= OptLogConst.QUERY)
    public ResponseParam query(@RequestBody ApplicationQueryDto applicationQueryDto){

        // 1. 执行分页查询
        Page<Map<String,Object>> applicationVersionPage = applicationQueryService.queryApplication(applicationQueryDto);

        // 2. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> applicationVersionList = UtilDTO.toDTO(applicationVersionPage.getContent(),
                null , getDtoCallbackHandler()) ;

        // 3. 返回结果信息
        return ResponseParam.success()
                .pageParam( applicationVersionPage )
                .datalist( applicationVersionList) ;
    }

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("organ")
    @OptLog(optModule="应用中心-应用查询",optName="查询组织信息",optType= OptLogConst.QUERY)
    public ResponseParam queryOrgan(@RequestBody JSONObject jsonObject){

        // 1. 非空判断
        if( !jsonObject.containsKey(GlobalVariableKey.TERMINAL_ID)
                || StringUtils.isBlank(jsonObject.getString(GlobalVariableKey.TERMINAL_ID))){
            return ResponseParam.success().datalist(new ArrayList<>());
        }

        // 2. 调用查询组织的方法
        if(jsonObject.containsKey(GlobalVariableKey.PARENT_ID)
                && "0".equals(jsonObject.getString(GlobalVariableKey.PARENT_ID))){
            jsonObject.put(GlobalVariableKey.PARENT_ID,"");
        }
        return applicationQueryService.queryOrgan(jsonObject);
    }

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("user")
    @OptLog(optModule="应用中心-应用查询",optName="查询人员信息",optType= OptLogConst.QUERY)
    public ResponseParam queryUser(@RequestBody ApplicationQueryDto applicationQueryDto){

        // 1. 非空判断
        if(applicationQueryDto.getTerminalId() == null){
            return ResponseParam.success().datalist(new ArrayList<>());
        }
        Assert.notNull(applicationQueryDto.getRequestType(), MessageContent.REQUEST_TYPE_IS_NULL);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(GlobalVariableKey.TERMINAL_ID,applicationQueryDto.getTerminalId());
        jsonObject.put(GlobalVariableKey.PARENT_ID,applicationQueryDto.getParentId());
        if(jsonObject.containsKey(GlobalVariableKey.PARENT_ID)
                && "0".equals(jsonObject.getString(GlobalVariableKey.PARENT_ID))){
            jsonObject.put(GlobalVariableKey.PARENT_ID,"");
        }
        return applicationQueryService.queryUser(applicationQueryDto.getTerminalId(),jsonObject);

        // 2. 调用查询组织的方法
//        if("0".equals(applicationQueryDto.getRequestType())){
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put(GlobalVariableKey.TERMINAL_ID,applicationQueryDto.getTerminalId());
//            jsonObject.put(GlobalVariableKey.PARENT_ID,applicationQueryDto.getParentId());
//            return applicationQueryService.queryUser(applicationQueryDto.getTerminalId(),jsonObject);
//        }
//
//        // 3. 调用查询人员的方法
//        if("1".equals(applicationQueryDto.getRequestType())){
//            Map<String,Object> searchParams = new HashMap<>(8);
//            searchParams.put(GlobalVariableKey.ORGAN_ID,applicationQueryDto.getParentId());
//            return applicationQueryService.queryUser(applicationQueryDto.getTerminalId(),searchParams);
//        }

//        return ResponseParam.fail().message(MessageContent.REQUEST_TYPE_IS_NULL);
    }

    /**
     * 地域查询
     * @return
     * @throws Exception
     */
    @PostMapping("regional")
    @OptLog(optModule="应用中心-应用查询",optName="查询地域信息",optType= OptLogConst.QUERY)
    public ResponseParam queryRegional(@RequestBody ApplicationQueryDto applicationQueryDto){

        // 1. 执行分页查询
        List<Map<String,Object>> list = applicationQueryService.queryRegional(applicationQueryDto);

        // 2. 返回结果信息
        return ResponseParam.success()
                .datalist( list) ;
    }


    /**
     * 身份查询
     * @return
     * @throws Exception
     */
    @PostMapping("identity")
    @OptLog(optModule="应用中心-应用查询",optName="查询身份信息",optType= OptLogConst.QUERY)
    public ResponseParam queryIdentity(){

        // 1. 查询身份字典信息
        return  systemSysApi.querySysTemDict(GlobalVariableKey.IDENTITY);

    }

    /**
     * 获取PO到DTO转换的接口。主要用于在前端展示数据之前首先将数据格式处理完成。
     * @return
     */
    public DTOCallbackHandler getDtoCallbackHandler() {

        //创建转换接口类
        DTOCallbackHandler dtoCallbackHandler = new DTOCallbackHandler() {
            @Override
            public void doHandler(Map<String, Object> dtoMap, Class<?> itemClass) {

            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }
}
