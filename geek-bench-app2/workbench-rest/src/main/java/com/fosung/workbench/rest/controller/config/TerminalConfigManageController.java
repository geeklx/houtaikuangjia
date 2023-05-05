package com.fosung.workbench.rest.controller.config;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.config.ConfigCascadeDto;
import com.fosung.workbench.dto.config.ConfigManageDto;
import com.fosung.workbench.entity.config.TerminalConfigManageEntity;
import com.fosung.workbench.service.config.TerminalConfigManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述:  配置管理控制层
 * @createDate: 2021/10/13 20:56
 * @author: gaojian
 */
@RestController
@RequestMapping(value= TerminalConfigManageController.BASE_PATH)
public class TerminalConfigManageController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/config/manage" ;

    /**
     * 注入配置管理服务
     */
    @Autowired
    private TerminalConfigManageService terminalConfigManageService ;

    /**
     * 记录分页查询
     * @param configManageDto
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="终端管理-运行配置",optName="查询配置信息",optType= OptLogConst.QUERY)
	public ResponseParam query(@RequestBody ConfigManageDto configManageDto){

        // 1. 获取查询条件
        Assert.notNull(configManageDto.getTerminalId(),MessageContent.TERMINAL_ID_IS_NULL);
        Map<String, Object> searchParam =  UtilDTO.toDTO(configManageDto, null);

        // 2. 执行查询
        List<TerminalConfigManageEntity> list = terminalConfigManageService.queryAll(searchParam) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> configManageList = UtilDTO.toDTO(list,
                null , getDtoCallbackHandler()) ;

        // 4. 返回分页查询结构
        return ResponseParam.success()
                .datalist( configManageList) ;
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
    @OptLog(optModule="终端管理-运行配置",optName="获取配置信息",optType= OptLogConst.GET)
    public ResponseParam get(@RequestBody ConfigCascadeDto configCascadeDto){

        // 1. 主键信息非空判断
        Assert.notNull(configCascadeDto.getId(), MessageContent.ID_IS_NULL);

        // 2. 查询配置管理表
        TerminalConfigManageEntity configManage = terminalConfigManageService.get(configCascadeDto.getId()) ;

        // 3. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( configManage ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalConfigManageEntity
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule="终端管理-运行配置",optName="保存配置信息",optType= OptLogConst.SAVE)
    public ResponseParam save(@Valid @RequestBody TerminalConfigManageEntity terminalConfigManageEntity) {

        // 1. id不为空，进行更新操作，否则进行添加
        if(terminalConfigManageEntity.getId() != null){

			// 2. 由请求参数中获取需要更新的字段
            Set<String> updateFields = UtilDTO.toDTOExcludeFields(terminalConfigManageEntity, Arrays.asList("id","configType","terminalId")).keySet();

			// 3. 按照字段更新对象
            terminalConfigManageService.update( terminalConfigManageEntity , updateFields ) ;
			return ResponseParam.updateSuccess() ;
        }else{

            terminalConfigManageService.save(terminalConfigManageEntity);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule="终端管理-运行配置",optName="删除配置信息",optType= OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

        // 1. 非空判断
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        terminalConfigManageService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
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