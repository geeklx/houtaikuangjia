package com.fosung.system.support.system.controller.sys;
import java.util.*;
import java.util.stream.Collectors;

import com.fosung.framework.common.util.UtilTree;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.system.support.system.anno.SysLog;
import com.fosung.system.support.system.dict.OptLogType;
import com.fosung.system.support.system.dto.sys.SysRoleDto;
import com.fosung.system.support.system.dto.sys.SysRoleResourceDto;
import com.fosung.system.support.system.entity.sys.*;
import com.fosung.system.support.system.service.sys.SysRoleScopService;
import com.fosung.system.support.system.service.sys.SysRoleService;
import com.fosung.system.support.system.service.sys.SysUserRoleScopService;
import com.fosung.system.support.system.service.sys.SysUserRoleService;
import com.fosung.system.support.system.util.RoleConstant;
import com.google.api.client.util.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(value=SysRoleController.BASE_PATH)
public class SysRoleController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/sysrole" ;

    @Autowired
    private SysRoleService sysRoleService ;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserRoleScopService sysUserRoleScopService;
    @Autowired
    private SysRoleScopService sysRoleScopService;

    /**
     * 角色记录分页查询
     * @return
     * @throws Exception
     *
     */
    @PostMapping("/query")
	public ResponseParam query(@RequestBody SysRoleDto sysRoleDto){
        if(sysRoleDto.getProjectId() == null){
            return ResponseParam.success()
                    .datalist(Lists.newArrayList()) ;
        }
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysRoleDto, null);
        //执行分页查询

        Pageable pageable = MybatisPageRequest.of(sysRoleDto.getPageNum(),sysRoleDto.getPageSize());
        Page<SysRoleEntity> sysRolePage = sysRoleService.queryPage(searchParam ,pageable ) ;
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysRoleList = UtilDTO.toDTO(sysRolePage.getContent(),
                null , getDtoCallbackHandler()) ;
        return ResponseParam.success()
                .pageParam( sysRolePage )
                .datalist( sysRoleList) ;
    }

    /**
     * 角色记录查询不分页
     * @return
     * @throws Exception
     * @param dataType treeData为tree类型 default为默认类型
     */
    @PostMapping("/queryall/{dataType}")
    public ResponseParam queryTree(@PathVariable(value = "dataType") String dataType,@RequestBody SysRoleDto sysRoleDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysRoleDto, null);

        //执行查询语句
        List<SysRoleEntity> sysRoleEntities = sysRoleService.queryAll(searchParam);

        //当数据类型为1时转换为tree
        List<Map<String, Object>> sysRoleList = UtilDTO.toDTO(sysRoleEntities,
                null , getDtoCallbackHandler());
        if("treedata".equals(dataType)){
            sysRoleList= UtilTree.getTreeData(sysRoleList,"id","parentId","children",false);

        }
        return ResponseParam.success()
                .datalist( sysRoleList) ;
    }
    /**
     * 根据用户查询角色
     * @return
     * @throws Exception
     * @param dataType treeData为tree类型 default为默认类型
     */
    @PostMapping("/query/userid/{dataType}")
    public ResponseParam queryTree( @RequestParam(value = "userId",required = false) Long userId,
                                   @PathVariable(value = "dataType") String dataType){
        //执行数据查询
        List<SysRoleEntity> sysRoleEntities = sysRoleService.queryRoleListByUserId(userId);

        //当数据类型为1时转换为tree
        List<Map<String, Object>> sysRoleList = UtilDTO.toDTO(sysRoleEntities,
                null , getDtoCallbackHandler()) ;
        if("treedata".equals(dataType)){
            sysRoleList= UtilTree.getTreeData(sysRoleList,"id","parentId","children",false);

        }
        return ResponseParam.success()
                .datalist( sysRoleList) ;
    }
    /**
     * 获取角色详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询角色表
        SysRoleEntity sysRole = sysRoleService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysRole ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存角色实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param sysRole
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @SysLog(optModule = "角色管理",optName = "保存",optType = OptLogType.SAVE)
    public ResponseParam save(@RequestBody SysRoleEntity  sysRole) {
        //id不为空，进行更新操作，否则进行添加
        if(sysRole.getId() != null){
            SysRoleEntity sysRoleEntity = sysRoleService.get(sysRole.getId());
            if(!sysRoleEntity.getRoleName().equals(sysRole.getRoleName())){
                // 判断在此项目下角色名称是否存在
                HashMap<String, Object> checkRoleName = new HashMap<>();
                checkRoleName.put("projectId",sysRole.getProjectId());
                checkRoleName.put("roleName",sysRole.getRoleName());
                checkRoleName.put("del",false);
                if(sysRoleService.isExist(checkRoleName)){
                    return ResponseParam.fail().message(RoleConstant.CHECK_ROLE_NAME);
                }
            }
            if(!sysRoleEntity.getRoleCode().equals(sysRole.getRoleCode())){
                // 判断在此项目下角色名称是否存在
                HashMap<String, Object> checkRoleCode = new HashMap<>();
                checkRoleCode.put("projectId",sysRole.getProjectId());
                checkRoleCode.put("roleCode",sysRole.getRoleCode());
                checkRoleCode.put("del",false);
                if(sysRoleService.isExist(checkRoleCode)){
                    return ResponseParam.fail().message(RoleConstant.CHECK_ROLE_CODE);
                }
            }
            //由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(sysRole, Arrays.asList("id")).keySet();
            updateFields.remove("del");
            updateFields.remove("roleCode");
            updateFields.remove("status");
			//按照字段更新对象
			sysRoleService.update( sysRole , updateFields ) ;
			return ResponseParam.updateSuccess() ;
        }else{
            // 判断在此项目下角色名称是否存在
            HashMap<String, Object> checkRoleName = new HashMap<>();
            checkRoleName.put("projectId",sysRole.getProjectId());
            checkRoleName.put("roleName",sysRole.getRoleName());
            checkRoleName.put("del",false);
            if(sysRoleService.isExist(checkRoleName)){
                return ResponseParam.fail().message(RoleConstant.CHECK_ROLE_NAME);
            }
            if(StringUtils.isNotBlank(sysRole.getRoleCode())){
                // 角色编码唯一性校验
                HashMap<String, Object> checkRoleCode = new HashMap<>();
                checkRoleCode.put("projectId",sysRole.getProjectId());
                checkRoleCode.put("roleCode",sysRole.getRoleCode());
                checkRoleCode.put("del",false);
                if(sysRoleService.isExist(checkRoleCode)){
                    return ResponseParam.fail().message(RoleConstant.CHECK_ROLE_CODE);
                }
            }else {
                sysRole.setRoleCode(RoleConstant.ROLE_CODE_PREFIX + System.currentTimeMillis());
            }
            sysRoleService.save(sysRole);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 描述: 批量开启/停用
     * @param sysRoles
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/26 11:47
     **/
    @PostMapping("update/status")
    @SysLog(optModule = "角色管理",optName = "批量启用/禁用",optType = OptLogType.BATCH_ENABLED)
    public ResponseParam batchUpdateStatus(@RequestBody List<SysRoleEntity>  sysRoles) {
        //id不为空，进行更新操作，否则进行添加
        if(sysRoles!=null){
            sysRoles.forEach(role -> {
                if(role.getId() != null){
                    //按照字段更新对象
                    sysRoleService.update( role , Arrays.asList("status")) ;
                }
            });
            return ResponseParam.updateSuccess() ;
        }
        return ResponseParam.updateFail();
    }

    /**
     * 描述: 查询全部角色加是否绑定判断
     * @param sysRoleDto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/27 10:34
     **/
    @PostMapping("queryrole")
    public ResponseParam queryAllRole(@RequestBody SysRoleDto sysRoleDto) {
        List<SysRoleEntity> sysRole = sysRoleService.queryAllRole(sysRoleDto);
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysRoleList = UtilDTO.toDTO(sysRole,
                null , getDtoCallbackHandler()) ;
        return ResponseParam.success().datalist(sysRoleList);
    }

    /**
     * 描述: 角色绑定角色范围
     * @param sysRoleScop
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/27 15:11
     **/
    @PostMapping("bindrolescope")
    @SysLog(optModule = "角色管理",optName = "管理范围",optType = OptLogType.SAVE)
    public ResponseParam bindRoleScope(@RequestBody SysRoleDto sysRoleScop) {
        sysRoleService.saveInfo(sysRoleScop);
        return ResponseParam.saveSuccess();
    }

    /**
     * 描述: 获取角色绑定权限信息
     * @param sysRoleResourceDto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/29 15:52
     **/
    @PostMapping("query/rolepower")
    public ResponseParam queryRolePower(@RequestBody SysRoleResourceDto sysRoleResourceDto){
        List<SysProjectApp> sysRoleResource = sysRoleService.queryRolePower(sysRoleResourceDto);
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> roleList = UtilDTO.toDTO(sysRoleResource,
                null , getDtoCallbackHandler()) ;
        return ResponseParam.success().datalist(roleList);
    }

    /**
     * 描述: 获取角色绑定应用
     * @param sysRoleResourceDto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/29 15:58
     **/
//    @PostMapping("query/rolebindapp")
//    public ResponseParam roleBindApp(@RequestBody SysRoleResourceDto sysRoleResourceDto){
//        List<SysApplicationEntity> sysRoleResource = sysRoleService.queryRoleBindApp(sysRoleResourceDto);
//        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
//        List<Map<String, Object>> roleList = UtilDTO.toDTO(sysRoleResource,
//                null , getDtoCallbackHandler()) ;
//        return ResponseParam.success().datalist(roleList);
//    }

    /**
     * 描述: 查询角色绑定资源
     * @param sysRoleResourceDto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/29 16:40
     **/
    @PostMapping("query/rolebindresource/{dataType}")
    public ResponseParam queryRoleBindResource(@RequestBody SysRoleResourceDto sysRoleResourceDto,@PathVariable(value = "dataType") String dataType){
        List<SysResourceEntity> sysRoleResource = sysRoleService.queryRoleBindResource(sysRoleResourceDto);
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> roleList = UtilDTO.toDTO(sysRoleResource,
                Arrays.asList("id","menuName","parentId","menuType","level") , getDtoCallbackHandler()) ;
        if("treedata".equals(dataType)){
            roleList= UtilTree.getTreeData(roleList,"id","parentId","children",false);

        }
        return ResponseParam.success().datalist(roleList);
    }



    /**
     * 删除角色信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @SysLog(optModule = "角色管理",optName = "删除",optType = OptLogType.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        List<Long> ids = list.stream().map(map -> map.getId()).collect(Collectors.toList());
        // 执行删除
        sysRoleService.delete(ids);
        List<String> tableNames = Arrays.asList("sys_role_app", "sys_role_resource", "sys_role_scop", "sys_user_role", "sys_user_role_scop");
        ids.forEach(id -> {
            tableNames.forEach(tableName -> {
                // 删除用户角色关联表
                sysRoleService.deleteByRoleId(id,tableName);
            });
        });
        return ResponseParam.deleteSuccess() ;
    }

    /**
     * 描述: 角色资源绑定
     * @param sysRoleResourceDto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/29 16:40
     **/
    @PostMapping("bindresources")
    @SysLog(optModule = "角色管理",optName = "权限设置",optType = OptLogType.SAVE)
    public ResponseParam bindResources(@RequestBody SysRoleResourceDto sysRoleResourceDto){
        if(sysRoleResourceDto.getRoleId() == null){
            return ResponseParam.fail().message(RoleConstant.CHECK_ID);
        }
        // 先删除角色下已绑定的资源和应用
        sysRoleService.delBindResource(sysRoleResourceDto);
        sysRoleService.delBindApp(sysRoleResourceDto);
        // 重新保存
        List<SysRoleResourceEntity> roleResourceEntities = sysRoleResourceDto.getBindScopes();
        roleResourceEntities.forEach(roleResource ->{
            List<Long> resources = roleResource.getResources();
            if(roleResource.getCheckFlag()){
                roleResource.setProjectId(sysRoleResourceDto.getProjectId());
                roleResource.setRoleId(sysRoleResourceDto.getRoleId());
                if(UtilCollection.isNotEmpty(resources)){
                    sysRoleService.bindResource(roleResource);
                }
                sysRoleService.bindApp(roleResource);
            }
        });

 	    return ResponseParam.saveSuccess();
    }


    /**
     *查询灯塔党组织
     *
     * @param parentId
     * @author liuke=
     * @date 2022/2/9 10:35
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/dtrole")
    public ResponseParam querDtRole(@RequestParam(name = "parentId",required = false,defaultValue = "") String parentId){
        List<Map<String,Object>> list = sysRoleService.queryDtRoleList(parentId);
        if (list==null){
            return ResponseParam.fail().message("获取党组织失败");
        }else {
            return ResponseParam.success().datalist(list).message("获取党组织成功");
        }
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