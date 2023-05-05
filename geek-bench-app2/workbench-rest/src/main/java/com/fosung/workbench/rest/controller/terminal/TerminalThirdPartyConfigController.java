package com.fosung.workbench.rest.controller.terminal;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilMap;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.terminal.TerminalNavigationTurnUrlDto;
import com.fosung.workbench.entity.terminal.TerminalNavigationTurnUrl;
import com.fosung.workbench.entity.terminal.TerminalThirdPartyConfigEntity;
import com.fosung.workbench.service.terminal.TerminalThirdPartyConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 终端第三方配置控制层
 * @Author gaojian
 * @Date 2022/2/28 16:04
 * @Version V1.0
 */
@RestController
@RequestMapping(value=TerminalThirdPartyConfigController.BASE_PATH)
public class TerminalThirdPartyConfigController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/third/party/config" ;

    /**
     * 描述:  终端第三方配置服务
     * @createDate: 2022/2/28 16:07
     * @author: gaojian
     */
    @Autowired
    private TerminalThirdPartyConfigService terminalThirdPartyConfigService;

    /**
     * 获取详情数据。
     */
    @PostMapping("get")
    @OptLog(optModule = "终端第三方配置",optName = "获取详情",optType = OptLogConst.GET)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){

        // 1. 查询终端第三方配置信息
        if(param.getId() == null ){
            return ResponseParam.fail()
                    .message( "未获取到主键信息！" );
        }
        TerminalThirdPartyConfigEntity terminalThirdPartyConfigEntity = terminalThirdPartyConfigService.get(param.getId()) ;
        if(terminalThirdPartyConfigEntity == null || terminalThirdPartyConfigEntity.getId() == null){
            return ResponseParam.fail()
                    .message( "未获取到相关信息！" );
        }

        // 2. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( terminalThirdPartyConfigEntity ,null , null ) ;
        Map<String, Object> configInfo = terminalThirdPartyConfigService.getConfigInfo(terminalThirdPartyConfigEntity);
        dtoObject.putAll(configInfo);
        dtoObject.remove("configInfo");
        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @PostMapping("save")
    @OptLog(optModule = "终端第三方配置",optName = "保存信息",optType = OptLogConst.SAVE)
    public ResponseParam save(@RequestBody JSONObject jsonObject) {

        // 1. 1id不为空，进行更新操作，否则进行添加
        Long id = jsonObject.getLong("id");
        if(id != null){

            // 2. 执行更新
            terminalThirdPartyConfigService.updateInfo(jsonObject);
            return ResponseParam.updateSuccess() ;
        }else{

            // 3. 执行新增
            terminalThirdPartyConfigService.saveInfo(jsonObject);
            return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
    @PostMapping("delete")
    @OptLog(optModule = "终端第三方配置",optName = "删除",optType = OptLogConst.DELETE)
    @Deprecated
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

        // 1. 非空判断
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        terminalThirdPartyConfigService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
        return ResponseParam.deleteSuccess() ;
    }

    /**
     * 获取详情数据。
     */
    @PostMapping("update/status")
    @OptLog(optModule = "终端第三方配置",optName = "修改状态",optType = OptLogConst.UPDATE)
    public ResponseParam updateStatus(@RequestBody TerminalThirdPartyConfigEntity terminalThirdPartyConfigEntity){

        // 1. 修改信息
        if(terminalThirdPartyConfigEntity.getStatusType() == null ||
         terminalThirdPartyConfigEntity.getId() == null){
            return ResponseParam.fail()
                    .data(null).message("未获取到必要参数信息!");
        }
        terminalThirdPartyConfigService.update(terminalThirdPartyConfigEntity, Arrays.asList("statusType"));

        // 2. 返回信息
        return ResponseParam.success()
                .data(null).message("修改状态信息成功!");
    }

    /**
     * 配置信息查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "终端第三方配置",optName = "非分页查询",optType = OptLogConst.QUERY)
    public ResponseParam query(@RequestBody TerminalThirdPartyConfigEntity terminalThirdPartyConfigEntity){

        // 1. 获取查询参数
        if(terminalThirdPartyConfigEntity.getTerminalId() == null ){
            return ResponseParam.success()
                    .datalist( new ArrayList<>() ) ;
        }
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalThirdPartyConfigEntity, null);

        // 2. 执行查询
        List<TerminalThirdPartyConfigEntity> result = terminalThirdPartyConfigService.queryAll(searchParam);

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> list = UtilDTO.toDTO(result,
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .datalist( list) ;
    }

    /**
     * 获取PO到DTO转换的接口。主要用于在前端展示数据之前首先将数据格式处理完成。
     * @return
     */
    public DTOCallbackHandler getDtoCallbackHandler() {

        // 创建转换接口类
        DTOCallbackHandler dtoCallbackHandler = new DTOCallbackHandler() {
            @Override
            public void doHandler(Map<String, Object> dtoMap, Class<?> itemClass) {
                Object object = JSONObject.parseObject(JSONObject.toJSONString(dtoMap),itemClass);
                Map<String, Object> configInfo = terminalThirdPartyConfigService.getConfigInfo((TerminalThirdPartyConfigEntity) object);
                dtoMap.putAll(configInfo);
                dtoMap.remove("configInfo");
            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }

}
