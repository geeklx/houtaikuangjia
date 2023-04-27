package com.geek.workbench.rest.controller.application;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.common.AppMessageContent;
import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.dto.application.ApplicationBasicDto;
import com.geek.workbench.entity.application.ApplicationBaseConfigEntity;
import com.geek.workbench.entity.application.ApplicationBasicEntity;
import com.geek.workbench.service.application.ApplicationBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述:  应用基本信息控制层
 * @createDate: 2021/10/23 23:11
 * @author: gaojian
 */
@RestController
@RequestMapping(value=ApplicationBasicController.BASE_PATH)
public class ApplicationBasicController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/application/basic" ;

    /**
     * 描述:  应用基本信息服务层
     * @createDate: 2021/10/23 23:11
     * @author: gaojian
     */
    @Autowired
    private ApplicationBasicService applicationBasicService;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="应用中心-应用管理",optName="查询应用基本信息-分页",optType= OptLogConst.QUERY)
	public ResponseParam query(@RequestBody ApplicationBasicDto applicationBasicDto){

        // 1. 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(applicationBasicDto, null);

        // 2. 执行分页查询
        String[] sorts = {"createDatetime:desc"};
        Page<ApplicationBasicEntity> applicationBasicPage = applicationBasicService.queryByPage(searchParam ,
                applicationBasicDto.getPageNum() ,
                applicationBasicDto.getPageSize(),
                sorts) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> applicationBasicList = UtilDTO.toDTO(applicationBasicPage.getContent(),
                null , getDtoCallbackHandler()) ;

        // 4. 返回结果信息
        return ResponseParam.success()
                .pageParam( applicationBasicPage )
                .datalist( applicationBasicList) ;
    }

    /**
     * 记录查询全部
     * @return
     * @throws Exception
     */
    @PostMapping("query/all")
    @OptLog(optModule="应用中心-应用管理",optName="查询应用基本信息-不分页",optType= OptLogConst.QUERY)
    public ResponseParam queryAll(@RequestBody ApplicationBasicDto applicationDto){

        // 1. 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(applicationDto, null);

        // 2. 执行查询
        List<ApplicationBasicEntity> applications = applicationBasicService.queryAll(searchParam ) ;

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
    @OptLog(optModule="应用中心-应用管理",optName="获取应用基本信息",optType= OptLogConst.GET)
    public ResponseParam get(@RequestBody AppBaseIdParam param){

        // 1. 查询应用基本信息表
        ApplicationBasicEntity applicationBasic = applicationBasicService.get(param.getId()) ;
        Assert.notNull(applicationBasic, AppMessageContent.APP_IS_NOT_EXIST);

        // 2. 获取应用配置信息
        List<ApplicationBaseConfigEntity> list = applicationBasicService.getApplicationConfig(applicationBasic.getId());
        applicationBasic.setAppConfigs(list);

        // 3. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( applicationBasic ,null , getDtoCallbackHandler() ) ;

        // 4. 返回结果信息
        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 获取详情数据。
     */
    @PostMapping("get/init")
    @OptLog(optModule="应用中心-应用管理",optName="获取应用基本信息",optType= OptLogConst.GET)
    public ResponseParam getInit(@RequestBody AppBaseIdParam param){

        // 1. 查询应用基本信息表
        ApplicationBasicEntity applicationBasic = applicationBasicService.get(param.getId()) ;
        Assert.notNull(applicationBasic, AppMessageContent.APP_IS_NOT_EXIST);

        // 2. 获取应用初始化配置信息
        List<ApplicationBaseConfigEntity> list = applicationBasicService.getInitApplicationConfig(applicationBasic.getId());
        applicationBasic.setAppConfigs(list);

        // 3. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( applicationBasic ,null , getDtoCallbackHandler() ) ;

        // 4. 返回结果信息
        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param applicationBasicEntity
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule="应用中心-应用管理",optName="保存应用基本信息",optType= OptLogConst.SAVE)
    public ResponseParam save(@RequestBody ApplicationBasicEntity  applicationBasicEntity) {

        // 1. id不为空，进行更新操作，否则进行添加
        if(applicationBasicEntity.getId() != null){

			// 2. 由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(applicationBasicEntity, Arrays.asList("id")).keySet();
            ApplicationBasicEntity applicationBasic = UtilBean.copyBean(applicationBasicEntity,ApplicationBasicEntity.class);

            // 3. 按照字段更新对象
			applicationBasicService.update( applicationBasic , updateFields ) ;
			return ResponseParam.updateSuccess().data(applicationBasic) ;
        }else{

            // 4. 执行新增应用
			applicationBasicService.saveApplicationBasic(applicationBasicEntity);
			return ResponseParam.saveSuccess().data(applicationBasicEntity) ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule="应用中心-应用管理",optName="删除应用信息",optType= OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

 	    // 1. 非空判断
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        applicationBasicService.deleteApp(list);
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
                dtoMap.remove("externalId");
                dtoMap.remove("projectId");
            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }

}