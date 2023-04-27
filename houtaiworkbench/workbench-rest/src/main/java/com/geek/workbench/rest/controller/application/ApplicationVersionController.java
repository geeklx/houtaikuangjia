package com.geek.workbench.rest.controller.application;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.common.AppMessageContent;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.dto.application.ApplicationVersionDto;
import com.geek.workbench.entity.application.ApplicationBasicEntity;
import com.geek.workbench.entity.application.ApplicationVersionEntity;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import com.geek.workbench.service.application.ApplicationBasicService;
import com.geek.workbench.service.application.ApplicationVersionService;
import com.geek.workbench.service.terminal.TerminalBasicService;
import com.geek.workbench.util.ApkUtil;
import com.geek.workbench.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * 描述:  应用版本控制层
 * @createDate: 2021/10/23 23:11
 * @author: gaojian
 */
@RestController
@RequestMapping(value=ApplicationVersionController.BASE_PATH)
public class ApplicationVersionController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/application/version" ;

    /**
     * 描述:  注入版本服务
     * @createDate: 2021/10/27 8:43
     * @author: gaojian
     */
    @Autowired
    private ApplicationVersionService applicationVersionService ;

    /**
     * 描述:  注入应用基本信息服务
     * @createDate: 2021/10/27 8:43
     * @author: gaojian
     */
    @Autowired
    private ApplicationBasicService applicationBasicService;

    /**
     * 描述:  注入终端基本信息服务
     * @createDate: 2021/10/27 8:43
     * @author: gaojian
     */
    @Autowired
    private TerminalBasicService terminalBasicService;

    /**
     * 描述:  解析 apk 信息
     * @createDate: 2021/10/26 11:31
     * @author: gaojian
     * @modify:
     * @param file
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("parse/apk")
    public ResponseParam parseApk(@RequestParam("file") MultipartFile file,
                                  @RequestParam(value = "appId" ,required = false) Long appId,
                                  @RequestParam(value = "terminalId" ,required = false) Long terminalId){

        // 1. 文件转换
        File result = null;
        try {
            result = FileUtil.multipartFileToFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(MessageContent.FILE_DISPOSE_ERROR);
        }
        if( result == null ){
            throw new AppException(MessageContent.FILE_DISPOSE_ERROR);
        }

        // 2. 判断 apk 解析内容
        Map<String, Object> params = ApkUtil.parseApk(result);
        if(result.exists()){
            result.delete();
        }
        if( params == null || !params.containsKey(GlobalVariableKey.APP_PACKAGE_NAME)){
            throw new AppException(MessageContent.APP_PACKAGE_NAME_IS_NULL);
        }

        // 3. 校验包名是否一致
        if(appId != null){
            ApplicationBasicEntity applicationBasicEntity = applicationBasicService.get(appId);
            if(applicationBasicEntity != null
                    && params != null
                    && !StringUtils.equals(applicationBasicEntity.getPackageName(),params.get(GlobalVariableKey.APP_PACKAGE_NAME).toString())){
                throw new AppException(MessageContent.APP_PACKAGE_NAME_INCONFORMITY);
            }
        }

        // 4. 终端id
        if(terminalId != null){
            TerminalBasicEntity terminalBasicEntity = terminalBasicService.get(terminalId);
            if(terminalBasicEntity != null
                    && params != null
                    && !StringUtils.equals(terminalBasicEntity.getPackageName(),params.get(GlobalVariableKey.APP_PACKAGE_NAME).toString())){
                throw new AppException(MessageContent.APP_PACKAGE_NAME_INCONFORMITY);
            }
        }
        return ResponseParam.success().data(params);
    }

    /**
     * 描述:  查询应用字典
     * @createDate: 2021/10/27 14:44
     * @author: gaojian
     * @modify:
     * @param 
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("query/application")
    @OptLog(optModule="应用中心-应用版本",optName="查询应用信息",optType= OptLogConst.QUERY)
    public ResponseParam queryApplication(){

        // 1. 查询应用列表
        Map<String,Object> searchParams = new HashMap<>(8);
        List<ApplicationBasicEntity> list = applicationBasicService.queryAll(searchParams);
        List<Map<String,Object>> result = UtilDTO.toDTO(list
                ,Arrays.asList(GlobalVariableKey.ID,GlobalVariableKey.APP_NAME,GlobalVariableKey.APP_TYPE)
                ,getDtoCallbackHandlerDict());

        // 2. 返回信息
        return ResponseParam.success().data(result);
    }

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query/exist")
    @OptLog(optModule="应用中心-应用版本",optName="查询应用是否有对应的版本",optType=OptLogConst.QUERY)
    public ResponseParam queryExist(@RequestBody ApplicationVersionDto applicationVersionDto){

        // 1. 获取查询参数
        Map<String, Object> searchParam =  new HashMap<>(8);
        Assert.notNull(applicationVersionDto.getAppId(), AppMessageContent.APP_ID_IS_NULL);
        Assert.notNull(applicationVersionDto.getAppType(), AppMessageContent.APP_TYPE_IS_NULL);
        searchParam.put(GlobalVariableKey.APP_ID,applicationVersionDto.getAppId());
        searchParam.put(GlobalVariableKey.APP_TYPE,applicationVersionDto.getAppType());

        // 2. 校验本类型的版本是否存在
        List<ApplicationVersionEntity> list = applicationVersionService.queryAll(searchParam);
        if(list != null && list.size() > 0){
            return ResponseParam.success().message(MessageContent.APP_VERSION_IS_EXIST);
        }

        // 3. 返回结果信息
        return ResponseParam.success().message(null);
    }

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="应用中心-应用版本",optName="应用版本查询",optType=OptLogConst.QUERY)
	public ResponseParam query(@RequestBody ApplicationVersionDto applicationVersionDto){

        // 1. 执行分页查询
        Pageable pageable = MybatisPageRequest.of(applicationVersionDto.getPageNum(),applicationVersionDto.getPageSize());
        Page<Map<String,Object>> applicationVersionPage = applicationVersionService.queryVersion(applicationVersionDto,pageable);

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
    @PostMapping("query/all")
    @OptLog(optModule="应用中心-应用版本",optName="应用所有版本查询",optType=OptLogConst.QUERY)
    public ResponseParam queryAll(@RequestBody ApplicationVersionDto applicationVersionDto){

        // 1. 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(applicationVersionDto, null);

        // 2. 执行分页查询 根据版本号倒序
        String[] sorts = {"versionNum:desc"};
        List<ApplicationVersionEntity> result = applicationVersionService.queryAll(searchParam ,
                sorts) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> applicationVersionList = UtilDTO.toDTO(result,
                null , getDtoCallbackHandler()) ;

        // 4. 返回结果信息
        return ResponseParam.success()
                .datalist( applicationVersionList) ;
    }

    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule="应用中心-应用版本",optName="查询应用版本详细信息",optType= OptLogConst.GET)
    public ResponseParam get(@RequestBody AppBaseIdParam param){

        // 1. 查询应用版本信息
        ApplicationVersionEntity applicationVersionEntity = applicationVersionService.getVersionInfo(param.getId());

        // 2. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( applicationVersionEntity ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param applicationVersion
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule="应用中心-应用版本",optName="保存应用版本信息",optType= OptLogConst.SAVE)
    public ResponseParam save(@RequestBody ApplicationVersionEntity  applicationVersion) {

        //id不为空，进行更新操作，否则进行添加
        if(applicationVersion.getId() != null){
			//由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(applicationVersion, Arrays.asList("id")).keySet();
            //按照字段更新对象
			applicationVersionService.update( applicationVersion , updateFields ) ;

			return ResponseParam.updateSuccess() ;
        }else{
			applicationVersionService.save(applicationVersion);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule="应用中心-应用版本",optName="删除应用版本",optType= OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

 	    // 1. 判断数组是否为空
 	    if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        applicationVersionService.deleteVersion(list);
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

    public DTOCallbackHandler getDtoCallbackHandlerDict() {

        //创建转换接口类
        DTOCallbackHandler dtoCallbackHandler = new DTOCallbackHandler() {
            @Override
            public void doHandler(Map<String, Object> dtoMap, Class<?> itemClass) {
                String id = dtoMap.containsKey(GlobalVariableKey.ID) ? dtoMap.get(GlobalVariableKey.ID).toString() : "";
                dtoMap.remove(GlobalVariableKey.ID);
                dtoMap.put(GlobalVariableKey.DICT_VALUE,id);
                String appName = dtoMap.containsKey(GlobalVariableKey.APP_NAME) ? dtoMap.get(GlobalVariableKey.APP_NAME).toString() : "";
                dtoMap.remove(GlobalVariableKey.APP_NAME);
                dtoMap.put(GlobalVariableKey.DICT_LABEL,appName);
            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }

}