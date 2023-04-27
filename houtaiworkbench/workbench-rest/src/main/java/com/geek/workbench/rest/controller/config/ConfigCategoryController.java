package com.geek.workbench.rest.controller.config;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.dto.config.ConfigCategoryDto;
import com.geek.workbench.entity.config.ConfigCategory;
import com.geek.workbench.service.config.ConfigCategoryService;
import com.geek.workbench.util.CustomUtilBean;
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
 * 描述:  目录树配置控制层
 * @createDate: 2021/10/13 20:54
 * @author: gaojian
 */
@Deprecated
@RestController
@RequestMapping(value=ConfigCategoryController.BASE_PATH)
public class ConfigCategoryController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/config/category" ;

    /**
     * 描述:  注入目录树配置信息服务
     * @createDate: 2021/10/13 20:49
     * @author: gaojian
     */
    @Autowired
    private ConfigCategoryService configCategoryService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="目录树配置",optName="查询目录树配置-分页",optType = OptLogConst.QUERY)
    public ResponseParam query(@RequestBody ConfigCategoryDto configCategoryDto){

        // 1. 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(configCategoryDto, null);

        // 2. 执行分页查询
        Page<ConfigCategory> configCategoryPage = configCategoryService.queryByPage(searchParam ,
                configCategoryDto.getPageNum(),
                configCategoryDto.getPageSize()) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> list = UtilDTO.toDTO(configCategoryPage.getContent(),
                null , getDtoCallbackHandler()) ;

        // 4. 返回查询结果
        return ResponseParam.success()
                .pageParam( configCategoryPage )
                .datalist( list) ;
    }

    /**
     * 记录查询全部
     * @return
     * @throws Exception
     */
    @PostMapping("query/all")
    @OptLog(optModule="目录树配置",optName="查询目录树配置-不分页",optType = OptLogConst.QUERY)
    public ResponseParam queryAll(@RequestBody ConfigCategoryDto configCategoryDto){

        // 1. 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(configCategoryDto, null);

        // 2. 执行查询
        List<ConfigCategory> categories = configCategoryService.queryAll(searchParam ) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> list = UtilDTO.toDTO(categories,
                null , getDtoCallbackHandler()) ;

        // 4. 返回查询结果
        return ResponseParam.success()
                .datalist( list) ;
    }

    /**
     * 获取详情数据。
     */
    @PostMapping("get")
    @OptLog(optModule="目录树配置",optName="获取目录树配置详情",optType = OptLogConst.GET)
    public ResponseParam get(@RequestBody AppBaseIdParam param){

        // 1. 主键信息非空判断
        Assert.notNull(param.getId(), MessageContent.ID_IS_NULL);

        // 2. 根据主键查询目录树信息
        ConfigCategory configCategory = configCategoryService.get(param.getId()) ;

        // 3. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( configCategory ,null , getDtoCallbackHandler() ) ;
        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param configCategory
     * @return
     * @throws Exception
     */
    @PostMapping("save")
    @OptLog(optModule="目录树配置",optName="保存目录树配置",optType = OptLogConst.SAVE)
    public ResponseParam save(@Validated @RequestBody ConfigCategory configCategory) {

        // 1. id不为空，进行更新操作，否则进行添加
        if(configCategory.getId() != null){

            // 2. 按照字段更新对象
            configCategoryService.update(configCategory, CustomUtilBean.getNotNullPropertyNames(configCategory) ) ;
            return ResponseParam.updateSuccess() ;
        }else{

            // 4. 执行保存目录信息
            configCategoryService.save(configCategory);
            return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
    @PostMapping("delete")
    @OptLog(optModule="目录树配置",optName="删除目录树配置",optType = OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

        // 1. 非空判断
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        configCategoryService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));

        // TODO 逻辑删除其他相关信息
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