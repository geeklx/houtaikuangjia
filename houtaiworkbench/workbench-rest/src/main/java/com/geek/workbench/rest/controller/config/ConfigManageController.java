package com.geek.workbench.rest.controller.config;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.dto.config.ConfigCascadeDto;
import com.geek.workbench.dto.config.ConfigManageDto;
import com.geek.workbench.entity.config.ConfigManageEntity;
import com.geek.workbench.service.config.ConfigManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

/**
 * 描述:  配置管理控制层
 * @createDate: 2021/10/13 20:56
 * @author: gaojian
 */
@RestController
@RequestMapping(value=ConfigManageController.BASE_PATH)
public class ConfigManageController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/config/manage" ;

    /**
     * 注入配置管理服务
     */
    @Autowired
    private ConfigManageService configManageService ;

    /**
     * 记录分页查询
     * @param configManageDto
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="配置管理",optName="查询配置信息",optType= OptLogConst.QUERY)
	public ResponseParam query(@RequestBody ConfigManageDto configManageDto){

        // 1. 获取查询条件
        Map<String, Object> searchParam =  UtilDTO.toDTO(configManageDto, null);

        // 2. 执行分页查询
        Page<ConfigManageEntity> configManageEntityPage = configManageService.queryByPage(searchParam
                , configManageDto.getPageNum()
                , configManageDto.getPageSize()) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> configManageList = UtilDTO.toDTO(configManageEntityPage.getContent(),
                null , getDtoCallbackHandler()) ;

        // 4. 返回分页查询结构
        return ResponseParam.success()
                .pageParam( configManageEntityPage )
                .datalist( configManageList) ;
    }

    /**
     * 查询平台
     * @param configManageDto
     * @return
     * @throws Exception
     */
    @PostMapping("query/platform")
    @OptLog(optModule="配置管理",optName="查询平台信息",optType= OptLogConst.QUERY)
    public ResponseParam queryPlatform(@RequestBody ConfigManageDto configManageDto){

        // 1. 获取查询条件
        Map<String, Object> searchParam = new HashMap<>(8);
        searchParam.put("configType",configManageDto.getConfigType());

        // 2. 执行查询
        List<ConfigManageEntity> list = configManageService.queryAll(searchParam);
        List<Map<String,Object>> result = new ArrayList<>();
        list.forEach(configManageEntity -> {
            Map<String,Object> map = new HashMap<>(8);
            map.put(GlobalVariableKey.DICT_LABEL,configManageEntity.getConfigPlatformName());
            map.put(GlobalVariableKey.DICT_VALUE,configManageEntity.getConfigPlatform());
            result.add(map);
        });

        // 3. 返回分页查询结构
        return ResponseParam.success()
                .datalist( result) ;
    }

    /**
     * 描述:  获取详情数据
     * @createDate: 2021/10/28 18:09
     * @author: gaojian
     * @modify:
     * @param configCascadeDto
     * @return: com.fosung.framework.web.http.ResponseParam
     */
	@PostMapping("get")
    @OptLog(optModule="配置管理",optName="查询配置详情",optType= OptLogConst.GET)
    public ResponseParam get(@RequestBody ConfigCascadeDto configCascadeDto){

        // 1. 主键信息非空判断
        Assert.notNull(configCascadeDto.getId(), MessageContent.ID_IS_NULL);

        // 2. 查询配置管理表
        ConfigManageEntity configManage = configManageService.get(configCascadeDto.getId()) ;

        // 3. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( configManage ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param configManage
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule="配置管理",optName="保存配置信息",optType= OptLogConst.SAVE)
    public ResponseParam save(@Valid @RequestBody ConfigManageEntity configManage) {

        // 1. id不为空，进行更新操作，否则进行添加
        if(configManage.getId() != null){

			// 2. 按照字段更新对象
			configManageService.updateInfo( configManage);
			return ResponseParam.updateSuccess() ;
        }else{

			configManageService.saveInfo(configManage);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule="配置管理",optName="删除配置信息",optType= OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

        // 1. 非空判断
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        configManageService.deleteInfo(list);
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

            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }

}