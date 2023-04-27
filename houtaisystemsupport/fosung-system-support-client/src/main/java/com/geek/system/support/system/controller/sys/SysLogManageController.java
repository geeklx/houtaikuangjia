package com.geek.system.support.system.controller.sys;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.secure.auth.AppUserDetails;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.controller.auth.SysIBaseController;
import com.geek.system.support.system.dict.RoleType;
import com.geek.system.support.system.dto.sys.SysLogManageDto;
import com.geek.system.support.system.entity.sys.SysLogManageEntity;
import com.geek.system.support.system.entity.sys.SysUserEntity;
import com.geek.system.support.system.service.sys.SysLogManageService;
import com.geek.system.support.system.service.sys.SysUserService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value= SysLogManageController.BASE_PATH)
public class SysLogManageController extends SysIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/syslog" ;

    @Autowired
    private SysLogManageService sysLogManageService ;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody SysLogManageDto sysLogManageDto){
        // 获取当前登陆人
        AppUserDetails loginAppUserDetails = getLoginAppUserDetails();
        HashMap<String, Object> userParams = Maps.newHashMap();
        userParams.put("userName",loginAppUserDetails.getUsername());
        userParams.put("projectId",getLoginProjectId());
        List<SysUserEntity> sysUserEntities = sysUserService.queryUserByRoleCode(userParams);
        if(UtilCollection.isNotEmpty(sysUserEntities)){
            SysUserEntity sysUserEntity = sysUserEntities.get(0);
            if(sysUserEntity.getRoleType() == null || !StringUtils.equals(sysUserEntity.getRoleType(), RoleType.superadmin.name())){
                sysLogManageDto.setOperNameEQ(loginAppUserDetails.getUsername());
            }
        }
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysLogManageDto, null);
        //执行分页查询
        String[] order = new String[]{"createDatetime_desc"};
        Page<SysLogManageEntity> sysLogManageDtoPage = sysLogManageService.queryByPage(searchParam , sysLogManageDto.getPageNum() , sysLogManageDto.getPageSize(),order) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysLogManageList = UtilDTO.toDTO(sysLogManageDtoPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( sysLogManageDtoPage )
                .datalist( sysLogManageList) ;
    }


    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询应用项目关联表
        SysLogManageEntity sysLogManageEntity = sysLogManageService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysLogManageEntity ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }


    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        sysLogManageService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
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