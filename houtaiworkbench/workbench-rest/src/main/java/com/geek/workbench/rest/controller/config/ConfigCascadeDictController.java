package com.geek.workbench.rest.controller.config;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.dto.config.ConfigCascadeDto;
import com.geek.workbench.entity.config.ConfigCascadeEntity;
import com.geek.workbench.service.config.ConfigCascadeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 描述:  配置信息控制层
 * @createDate: 2021/10/13 20:56
 * @author: gaojian
 */
@RestController
@RequestMapping(value=ConfigCascadeDictController.BASE_PATH)
public class ConfigCascadeDictController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/config/cascade" ;

    /**
     * 注入配置级联服务
     */
    @Autowired
    private ConfigCascadeService configCascadeService ;

    /**
     * 描述:  记录查询
     * @createDate: 2021/10/28 17:33
     * @author: gaojian
     * @modify:
     * @param configCascadeDto
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody ConfigCascadeDto configCascadeDto){

        // 1. 获取查询参数
        if(configCascadeDto.getParentId() == null){
            configCascadeDto.setParentId(GlobalVariableKey.PRATENT_ID);
        }
        if(StringUtils.isBlank(configCascadeDto.getConfigType())){
            configCascadeDto.setConfigType(GlobalVariableKey.CONFIG_TYPE);
        }
        Map<String, Object> searchParam =  UtilDTO.toDTO(configCascadeDto, null);

        // 2. 执行查询
        String[] sorts = {"num:asc"};
        List<ConfigCascadeEntity> configCascadeEntities = configCascadeService.queryAll(searchParam,sorts) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> list = UtilDTO.toDTO(configCascadeEntities,
                null , getDtoCallbackHandler()) ;

        // 4. 返回结果信息
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
                Object configLabel = dtoMap.get("configLabel");
                Object configValue = dtoMap.get("configValue");
                Object id = dtoMap.get("id");
                dtoMap.clear();
                dtoMap.put(GlobalVariableKey.ID,id);
                dtoMap.put(GlobalVariableKey.DICT_LABEL,configLabel);
                dtoMap.put(GlobalVariableKey.DICT_VALUE,configValue);
            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }

}