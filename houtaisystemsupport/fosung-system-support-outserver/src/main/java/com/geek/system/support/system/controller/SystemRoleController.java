package com.geek.system.support.system.controller;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.dto.sys.ShelvesUserRoleDto;
import com.geek.system.support.system.dto.sys.SysRoleDto;
import com.geek.system.support.system.entity.sys.SysRoleEntity;
import com.geek.system.support.system.service.pbs.PbsRoleService;
import com.geek.system.support.system.service.sys.SysProjectService;
import com.geek.system.support.system.service.sys.SysRoleService;
import com.geek.system.support.system.service.sys.SysUserRoleService;
import com.geek.system.support.system.service.sys.SysUserService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/system/role")
public class SystemRoleController extends AppIBaseController {



    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysProjectService sysProjectService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PbsRoleService pbsRoleService;

    @RequestMapping("list")
    public ResponseParam getRole() {
        Map<String,Object> searchParam = Maps.newHashMap();
        List<SysRoleEntity> sysRoleEntities = sysRoleService.queryAll(searchParam,new String[]{"num:asc","createDatetime:asc"});
        List<Map<String,Object>> results = UtilDTO.toDTO(sysRoleEntities,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(results);
    }

    @RequestMapping("list/param/{project}")
    public ResponseParam getRoleByParam(@PathVariable("project") String project, @RequestBody SysRoleDto sysRoleDto) {
        Long projectId = sysProjectService.getProjectId(project);
        sysRoleDto.setProjectId(projectId);
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysRoleDto, null);
        List<SysRoleEntity> sysRoleEntities = sysRoleService.queryAll(searchParam,new String[]{"num:asc","createDatetime:asc"});
        List<Map<String,Object>> results = UtilDTO.toDTO(sysRoleEntities,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(results);
    }

    /**
     * 查询角色下的人员
     *
     * @param roleCode
     * @param appId
     * @return com.fosung.framework.web.http.ResponseParam
     * @author gaojian
     * @date 2021/11/1 14:19
     */
    @RequestMapping("list/user")
    public ResponseParam getRoleUser(@RequestParam("appId") Long appId, @RequestParam("roleCode") String roleCode) {

        List<Map<String,Object>> list = sysUserRoleService.queryUserByRoleCode(appId,roleCode);
        return ResponseParam.success().datalist(list);
    }

    @RequestMapping("list/by/app")
    public ResponseParam getRoleByApp(Long appId) {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("appId",appId);
        List<SysRoleEntity> sysRoleEntities = sysRoleService.queryAll(searchParam,new String[]{"num:asc","createDatetime:asc"});
        List<Map<String,Object>> results = UtilDTO.toDTO(sysRoleEntities,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(results);
    }

    /**
     *
     * 批量授权
     *
     * @author liuke
     * @version
     */
    @PostMapping("batch/shelves/{project}")
    public ResponseParam batchShelves(@PathVariable("project") String project, @RequestBody ShelvesUserRoleDto shelvesUserRoleDto){
        Long projectId = sysProjectService.getProjectId(project);
        if(projectId==0l){
            return ResponseParam.fail().message("租户不存在");
        }
        shelvesUserRoleDto.setProjectId(projectId);
        try {
            sysUserService.batchShelves(shelvesUserRoleDto);
            return ResponseParam.success().message("授权成功");
        }catch (Exception e){
            return ResponseParam.fail().message(e.getMessage());
        }

    }

    /**
     *
     * 批量授权
     *
     * @author liuke
     * @version
     */
    @PostMapping("query/appbyrole/{project}")
    public ResponseParam queryAppByRole(@PathVariable("project") String project, @RequestParam("roleId") Long roleId){
        Long projectId = sysProjectService.getProjectId(project);
        if(projectId==0l){
            return ResponseParam.fail().message("租户不存在");
        }
        Map<String,Object> map = Maps.newHashMap();
        map.put("roleId",roleId);
        map.put("projectId",projectId);
        List<Map<String,Object>> lists = pbsRoleService.queryAppByRole(map);
        return ResponseParam.success().datalist(lists);

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
