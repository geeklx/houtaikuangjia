package com.fosung.workbench.rest.controller.config;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.config.ConfigApiDto;
import com.fosung.workbench.entity.config.ConfigApiEntity;
import com.fosung.workbench.service.config.ConfigApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述:  接口配置信息控制层
 * @createDate: 2021/10/13 20:56
 * @author: gaojian
 */
@Deprecated
@RestController
@RequestMapping(value=ConfigApiController.BASE_PATH)
public class ConfigApiController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/config/api" ;

    /**
     * 描述:  注入人员接口配置信息服务
     * @createDate: 2021/10/13 20:49
     * @author: gaojian
     */
    @Autowired
    private ConfigApiService configApiService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="接口配置",optName="查询接口配置-分页",optType = OptLogConst.QUERY)
    public ResponseParam query(@RequestBody ConfigApiDto configApiDto){

        // 1. 执行分页查询
        Page<Map<String,Object>> applicationPage = configApiService.queryInfo(configApiDto) ;

        // 2. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> applicationList = UtilDTO.toDTO(applicationPage.getContent(),
                null , getDtoCallbackHandler()) ;

        // 3. 返回结果信息
        return ResponseParam.success()
                .pageParam( applicationPage )
                .datalist( applicationList) ;
    }

    /**
     * 记录查询全部
     * @return
     * @throws Exception
     */
    @PostMapping("query/all")
    @OptLog(optModule="接口配置",optName="查询人员接口配置-不分页",optType = OptLogConst.QUERY)
    public ResponseParam queryAll(@RequestBody ConfigApiDto configApiDto){

        // 1. 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(configApiDto, null);

        // 2. 执行查询
        List<ConfigApiEntity> applications = configApiService.queryAll(searchParam ) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> applicationList = UtilDTO.toDTO(applications,
                null , getDtoCallbackHandler()) ;

        // 4. 返回结果信息
        return ResponseParam.success()
                .datalist( applicationList) ;
    }

    /**
     * 获取详情数据。
     */
    @PostMapping("get")
    @OptLog(optModule="人员接口配置",optName="获取人员接口配置详情",optType = OptLogConst.GET)
    public ResponseParam get(@RequestBody ConfigApiDto configApiDto){

        // 1. 主键信息非空判断
        Assert.notNull(configApiDto.getApiGroupId(), MessageContent.ID_IS_NULL);

        // 2. 查询接口配置管理
        Map<String,Object> configApiInfo = configApiService.getInfo(configApiDto) ;

        // 3. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( configApiInfo ,null , getDtoCallbackHandler() ) ;

        // 4. 返回结果信息
        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param configApiDto
     * @return
     * @throws Exception
     */
    @PostMapping("save")
    @OptLog(optModule="接口配置",optName="保存接口配置",optType = OptLogConst.SAVE)
    public ResponseParam save(@Validated @RequestBody ConfigApiDto configApiDto) {

        // 1. id不为空，进行更新操作，否则进行添加
        if(configApiDto.getApiGroupId() != null){

            // 2. 按照字段更新对象
            configApiService.updateApiInfo(configApiDto);
            return ResponseParam.updateSuccess() ;
        }else{

            // 3. 执行保存接口信息
            configApiService.saveApiInfo(configApiDto);
            return ResponseParam.saveSuccess() ;
        }

    }

    /**
     * 删除信息
     * @param list
     * @return
     */
    @PostMapping("delete")
    @OptLog(optModule="接口配置",optName="删除接口配置",optType = OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<ConfigApiDto> list) {

        // 1. 非空判断
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        configApiService.deleteInfo(list);
        return ResponseParam.deleteSuccess() ;
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
                dtoMap.remove("del");
            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }
}