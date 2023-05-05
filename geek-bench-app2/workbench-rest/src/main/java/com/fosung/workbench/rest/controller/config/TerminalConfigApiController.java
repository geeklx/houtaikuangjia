package com.fosung.workbench.rest.controller.config;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.dto.config.TerminalConfigApiDto;
import com.fosung.workbench.entity.config.TerminalConfigApiEntity;
import com.fosung.workbench.service.config.TerminalConfigApiService;
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
 * 描述:  终端接口配置信息控制层
 * @createDate: 2021/10/13 20:56
 * @author: gaojian
 */
@RestController
@RequestMapping(value=TerminalConfigApiController.BASE_PATH)
public class TerminalConfigApiController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/config/api" ;

    /**
     * 描述:  注入终端接口配置服务
     * @createDate: 2021/10/24 20:41
     * @author: gaojian
     */
    @Autowired
    private TerminalConfigApiService terminalConfigApiService ;

    /**
     * 查询认证信息
     * @param terminalConfigApiDto
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody TerminalConfigApiDto terminalConfigApiDto){

        // 1. 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalConfigApiDto, null);

        // 2. 执行分页查询
        Page<TerminalConfigApiEntity> terminalConfigApiEntityPage = terminalConfigApiService.queryByPage(searchParam ,
                terminalConfigApiDto.getPageNum(),
                terminalConfigApiDto.getPageSize()) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> list = UtilDTO.toDTO(terminalConfigApiEntityPage.getContent(),
                null , getDtoCallbackHandler()) ;

        // 4. 返回查询结果
        return ResponseParam.success()
                .pageParam( terminalConfigApiEntityPage )
                .datalist( list);
    }

    /**
     * 获取详情数据。在url中隐藏id值 ， 查询数据对象的id以post参数方式提交。
     */
	@PostMapping("get")
    public ResponseParam get(@RequestBody AppBaseIdParam param){

        // 1. 主键信息非空判断
        Assert.notNull(param.getId(), MessageContent.ID_IS_NULL);

        // 2. 终端接口配置查询
        TerminalConfigApiEntity terminalConfigApiEntity = terminalConfigApiService.get(param.getId()) ;

        // 3. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( terminalConfigApiEntity ,null , getDtoCallbackHandler() ) ;

        // 4. 返回结果
        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param list
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    public ResponseParam save(@Validated @RequestBody List<TerminalConfigApiEntity> list) {
 	    terminalConfigApiService.saveInfo(list);
 	    return ResponseParam.saveSuccess() ;
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

        // 1. 非空判断
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        terminalConfigApiService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));

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