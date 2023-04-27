package com.geek.system.support.system.controller.sys;
import java.util.*;
import java.util.stream.Collectors;

import com.fosung.framework.common.util.UtilMap;
import com.geek.system.support.system.anno.SysLog;
import com.geek.system.support.system.dict.AppStatus;
import com.geek.system.support.system.dict.OptLogType;
import com.geek.system.support.system.dto.sys.SysApplicationDto;
import com.geek.system.support.system.entity.sys.SysApplicationCategoryEntity;
import com.geek.system.support.system.entity.sys.SysApplicationEntity;
import com.geek.system.support.system.service.sys.SysApplicationCategoryService;
import com.geek.system.support.system.service.sys.SysApplicationService;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import org.springframework.web.bind.annotation.*;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;

@RestController
@RequestMapping(value= SysApplicationController.BASE_PATH)
public class SysApplicationController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/sysapplication" ;

    @Autowired
    private SysApplicationService sysApplicationService ;

    @Autowired
    private SysApplicationCategoryService sysApplicationCategoryService ;


    /**
     * 应用记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody SysApplicationDto pfApplicationDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(pfApplicationDto, null);
        //执行分页查询
        Page<SysApplicationEntity> pfApplicationPage = sysApplicationService.queryByPage(searchParam ,
                pfApplicationDto.getPageNum() , pfApplicationDto.getPageSize(), new String[]{"createDatetime_desc"}) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> pfApplicationList = UtilDTO.toDTO(pfApplicationPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( pfApplicationPage )
                .datalist( pfApplicationList) ;
    }

    /**
     * 查询全部应用信息
     * @return
     * @throws Exception
     */
    @PostMapping("queryall")
    public ResponseParam queryAll(@RequestBody SysApplicationDto pfApplicationDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(pfApplicationDto, null);
        //执行查询
        List<SysApplicationEntity> pfApplicationAllList = sysApplicationService.queryAll(searchParam , new String[]{"num_asc"}) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> pfApplicationList = UtilDTO.toDTO(pfApplicationAllList,
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .datalist( pfApplicationList) ;
    }

    /**
     * 查询全部应用信息
     * @return
     * @throws Exception
     */
    @PostMapping("queryallbyproject")
    public ResponseParam queryAllByProject(@RequestBody SysApplicationDto pfApplicationDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(pfApplicationDto, null);
        //执行查询
        List<SysApplicationEntity> pfApplicationAllList = sysApplicationService.selectByProjectId(pfApplicationDto.getProjectId()) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> pfApplicationList = UtilDTO.toDTO(pfApplicationAllList,
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .datalist( pfApplicationList) ;
    }

    /**
     * 获取应用详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询应用系统信息
        SysApplicationEntity pfApplication = sysApplicationService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( pfApplication ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存应用实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param pfApplication
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @SysLog(optModule = "应用系统管理",optName = "保存",optType = OptLogType.SAVE)
    public ResponseParam save(@RequestBody SysApplicationEntity pfApplication) {
        //id不为空，进行更新操作，否则进行添加
        if(pfApplication.getId() != null){
            SysApplicationEntity sysApplicationEntity = sysApplicationService.get(pfApplication.getId());
            if(!sysApplicationEntity.getAppName().equals(pfApplication.getAppName())){
                // 判断项目名称是否存在
                HashMap<String, Object> checkAppName = new HashMap<>();
                checkAppName.put("appName",pfApplication.getAppName());
                checkAppName.put("del",false);
                if(sysApplicationService.isExist(checkAppName)){
                    return ResponseParam.fail().message("应用名称已存在！");
                }
            }
            //由请求参数中获取需要更新的字段
			Set<String> updateFields = Sets.newHashSet(new String[]{"appName","appType","orgType","resourceUrl","remark","iconUrl"});
			//按照字段更新对象
            sysApplicationService.update( pfApplication , updateFields ) ;

			return ResponseParam.updateSuccess() ;
        }else{
            // 判断项目名称是否存在2
            HashMap<String, Object> checkAppName = new HashMap<>();
            checkAppName.put("appName",pfApplication.getAppName());
            checkAppName.put("del",false);
            if(sysApplicationService.isExist(checkAppName)){
                return ResponseParam.fail().message("应用名称已存在！");
            }
            if(StringUtils.isNotBlank(pfApplication.getAppCode())){
                // 租户编码唯一性校验
                HashMap<String, Object> checkAppCode = new HashMap<>();
                checkAppCode.put("appCode",pfApplication.getAppCode());
                checkAppCode.put("del",false);
                if(sysApplicationService.isExist(checkAppCode)){
                    return ResponseParam.fail().message("应用编码已存在！");
                }
            }
            sysApplicationService.save(pfApplication);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @SysLog(optModule = "应用系统管理",optName = "删除",optType = OptLogType.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        sysApplicationService.deleteInfo(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	

        return ResponseParam.deleteSuccess() ;
    }

    /**
     * 批量启用
     * @param list
     * @return
     */
    @PostMapping("enable")
    @SysLog(optModule = "应用系统管理",optName = "批量启用",optType = OptLogType.BATCH_ENABLED)
    public ResponseParam enable(@RequestBody List<SysApplicationEntity> list) {
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.fail().message("传入数据不能为空");
        }
        Set<String> updateFields = Sets.newHashSet(new String[]{"status"});
        list.stream().forEach(map ->{
            map.setStatus(AppStatus.normal);
            sysApplicationService.update(map,updateFields);
        } );
        return ResponseParam.updateSuccess().message("启用成功") ;
    }

    /**
     * 禁用应用
     * @param list
     * @return
     */
    @PostMapping("disable")
    @SysLog(optModule = "应用系统管理",optName = "批量禁用",optType = OptLogType.BATCH_ENABLED)
    public ResponseParam disable(@RequestBody List<SysApplicationEntity> list) {
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.fail().message("传入数据不能为空");
        }
        Set<String> updateFields = Sets.newHashSet(new String[]{"status"});
        list.stream().forEach(map ->{
            map.setStatus(AppStatus.stop);
            sysApplicationService.update(map,updateFields);
        } );
        return ResponseParam.updateSuccess().message("禁用成功") ;
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
                // 查询分类名称
               if(dtoMap.get("categoryId") != null){
                   SysApplicationCategoryEntity paramPfApplicationCategoryEntity = sysApplicationCategoryService.get(UtilMap.getLong(dtoMap,"categoryId"));
                   dtoMap.put("categoryName",paramPfApplicationCategoryEntity != null ?paramPfApplicationCategoryEntity.getCategoryName():"");
               }
            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }

}