package com.geek.system.support.system.controller.sys;
import java.util.*;

import com.fosung.framework.common.util.UtilTree;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.geek.system.support.system.anno.SysLog;
import com.geek.system.support.system.dict.OptLogType;
import com.geek.system.support.system.dto.sys.CommonDto;
import com.geek.system.support.system.dto.sys.SysResourceDto;
import com.geek.system.support.system.entity.sys.ReturnTreeData;
import com.geek.system.support.system.entity.sys.SysUserResourceEntity;
import com.geek.system.support.system.service.sys.SysResourceAppService;
import com.geek.system.support.system.service.sys.SysResourceService;
import com.geek.system.support.system.dict.MenuType;
import com.geek.system.support.system.entity.sys.SysResourceEntity;
import com.geek.system.support.system.service.sys.SysUserResourceService;
import com.geek.system.support.system.vo.SysResourceMenuVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
@RequestMapping(value=SysResourceController.BASE_PATH)
public class SysResourceController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/sysresource" ;

    @Autowired
    private SysResourceService sysResourceService ;

    @Autowired
    private SysResourceAppService sysResourceAppService;

    @Autowired
    private SysUserResourceService sysUserResourceService;

    /**
     * 资源记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("/query/{dataType}")
	public ResponseParam query(@RequestBody SysResourceDto sysResourceDto,@PathVariable(value = "dataType") String dataType){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysResourceDto, null);
        //执行分页查询
        Page<SysResourceEntity> sysResourcePage = sysResourceService.queryPage(searchParam , MybatisPageRequest.of(sysResourceDto.getPageNum() , sysResourceDto.getPageSize()) ) ;
        List<Map<String, Object>> sysResourceList = Lists.newArrayList();
        if(sysResourcePage!= null){
            //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
             sysResourceList = UtilDTO.toDTO(sysResourcePage.getContent(),
                    null , getDtoCallbackHandler()) ;
        }

        if("treedata".equals(dataType)){
            sysResourceList= UtilTree.getTreeData(sysResourceList,"id","parentId","children",false);

        }
        return ResponseParam.success()
                .pageParam( sysResourcePage )
                .datalist( sysResourceList) ;
    }
    /**
     * 查询全部资源记录
     * @return
     * @throws Exception
     * @param  dataType treeData为tree类型 default为默认类型
     */
    @PostMapping("queryall/{dataType}")
    public ResponseParam queryTree(@RequestBody SysResourceDto sysResourceDto,
                                   @PathVariable(value = "dataType") String dataType){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysResourceDto, null);
        List<SysResourceEntity> sysResourceEntityList = sysResourceService.queryAll(searchParam,new String[]{"num:asc","createDatetime:asc"});
        //当数据类型为1时转换为tree
        List<Map<String, Object>> sysResourceList = UtilDTO.toDTO(sysResourceEntityList,
                null , getDtoCallbackHandler()) ;
        if("treedata".equals(dataType)){
            sysResourceList= UtilTree.getTreeData(sysResourceList,"id","parentId","children",false);

        }
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段


        return ResponseParam.success()
                .datalist( sysResourceList) ;
    }

    /**
     * 按类型查询资源记录
     * @return
     * @throws Exception
     * @param  menuType menu为菜单类型  catalog 为目录类型  button 为按钮类型     --默认为菜单类型
     */
    @PostMapping("queryresourcebytype/{menuType}")
    public ResponseParam queryMenuTypeTree(@RequestBody SysResourceDto sysResourceDto,
                                   @PathVariable(value = "menuType") String menuType){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysResourceDto, null);
        searchParam.put("menuType", MenuType.getIndex(menuType));
        List<SysResourceEntity> sysResourceEntityList = sysResourceService.queryAll(searchParam);
        //当数据类型为1时转换为tree
        List<Map<String, Object>> sysResourceList = UtilDTO.toDTO(sysResourceEntityList,
                null , getDtoCallbackHandler()) ;
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        return ResponseParam.success()
                .datalist( sysResourceList) ;
    }
    /**
     * 获取资源详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询资源
        SysResourceEntity sysResource = sysResourceService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysResource ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     *根据用户id查询资源信息
     *
     * @param userId
     * @param sysResourceDto
     * @param dataType
     * @author liuke
     * @date 2021/4/19 10:39
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/querybyuserid/{dataType}")
    public ResponseParam queryByUserId(@RequestParam("userId") Long userId,
                                       @RequestBody SysResourceDto sysResourceDto,
                                       @PathVariable(value = "dataType") String dataType){
        //查询资源
        List<SysResourceEntity> sysResourceEntityList = sysResourceService.queryResourceByUserId(userId,sysResourceDto); ;

        //将实体转换为数据传输对象
        List<Map<String, Object>> dtoObjects = UtilDTO.toDTO( sysResourceEntityList ,null , getDtoCallbackHandler() ) ;
        //当传入值为1时将数据处理为树
        if("treedata".equals(dataType)){
            dtoObjects=UtilTree.getTreeData(dtoObjects,"id","parentId","children",false);
        }
        return ResponseParam.success()
                .datalist(dtoObjects);
    }
    /**
     *根据角色id查询资源信息
     *
     * @param roleId
     * @param sysResourceDto
     * @param dataType
     * @author liuke
     * @date 2021/4/19 10:38
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/querybyroleid/{dataType}")
    public ResponseParam queryByRoleId(@RequestParam("roleId") Long roleId,
                                       @RequestBody SysResourceDto sysResourceDto,
                                       @PathVariable(value = "dataType") String dataType){
        //查询资源
        List<SysResourceEntity> sysResourceEntityList = sysResourceService.queryResourceByRoleId(roleId,sysResourceDto); ;

        //将实体转换为数据传输对象
        List<Map<String, Object>> dtoObjects = UtilDTO.toDTO( sysResourceEntityList ,null , getDtoCallbackHandler() ) ;
        //当传入值为1时将数据处理为树
        if("treedata".equals(dataType)){
            dtoObjects=UtilTree.getTreeData(dtoObjects,"id","parentId","children",false);
        }
        return ResponseParam.success()
                .datalist(dtoObjects);
    }
    /**
     * 保存资源实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param sysResource
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @SysLog(optModule = "资源维护",optName = "保存",optType = OptLogType.SAVE)
    public ResponseParam save(@RequestBody SysResourceEntity  sysResource) {
        //id不为空，进行更新操作，否则进行添加
        if(sysResource.getId() != null){
			//由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(sysResource, Arrays.asList("id")).keySet(); ;
			//按照字段更新对象
			sysResourceService.update( sysResource , updateFields ) ;
			return ResponseParam.updateSuccess() ;
        }else{
			sysResourceService.save(sysResource);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除资源信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @SysLog(optModule = "资源维护",optName = "删除",optType = OptLogType.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        sysResourceService.deleteInfo(list);

        return ResponseParam.deleteSuccess() ;
    }

    /**
     * 获取资源App
     * @param app
     * @return
     */
    @PostMapping("getresourceapp")
    public ResponseParam getResourceApp(@RequestBody AppBaseIdParam app) {
        List<Map<String,Object>> list = sysResourceAppService.getWorkBenchApp(app.getId());
        if(list == null){
            return ResponseParam.fail().message("获取应用列表失败");
        }else {
            return ResponseParam.success().datalist(list) ;
        }

    }

    /**
     *查询角色下扩展资源
     *
     * @param commonDto
     * @author liuke
     * @date 2021/12/3 15:39
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/role/resourcetree")
    public ResponseParam queryRoleResourceTree(@RequestBody CommonDto commonDto){
        //查询资源
        List<ReturnTreeData> returnTreeData = sysResourceService.getAppResourceCheckHasAndNoRole(commonDto.getProjectId(),commonDto.getUserId(),commonDto.getRoleId()) ;

        //将实体转换为数据传输对象
        List<Map<String, Object>> dtoObjects = UtilDTO.toDTO( returnTreeData ,null , getDtoCallbackHandler() ) ;
        dtoObjects=UtilTree.getTreeData(dtoObjects,"id","parentId","children",false);

        return ResponseParam.success()
                .datalist(dtoObjects);
    }

    /**
     *查询用户下扩展资源
     *
     * @param commonDto
     * @author liuke
     * @date 2021/12/3 15:39
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/user/resourcetree")
    public ResponseParam queryUserResourceTree(@RequestBody CommonDto commonDto){
        //查询资源
        List<ReturnTreeData> returnTreeData = sysResourceService.getAppResourceCheckHas(commonDto.getProjectId(),commonDto.getUserId(),null) ;

        //将实体转换为数据传输对象
        List<Map<String, Object>> dtoObjects = UtilDTO.toDTO( returnTreeData ,null , getDtoCallbackHandler() ) ;
        dtoObjects=UtilTree.getTreeData(dtoObjects,"id","parentId","children",false);

        return ResponseParam.success()
                .datalist(dtoObjects);
    }

    /**
     *查询角色绑定资源
     *
     * @param commonDto
     * @author liuke
     * @date 2021/12/3 15:39
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/query/roleresource")
    public ResponseParam getRoleResourceTree(@RequestBody CommonDto commonDto){
        //查询资源
        List<ReturnTreeData> returnTreeData = sysResourceService.getRoleResource(commonDto.getProjectId(),commonDto.getAppId(),commonDto.getRoleId()) ;

        //将实体转换为数据传输对象
        List<Map<String, Object>> dtoObjects = UtilDTO.toDTO( returnTreeData ,null , getDtoCallbackHandler() ) ;
        dtoObjects=UtilTree.getTreeData(dtoObjects,"id","parentId","children",false);

        return ResponseParam.success()
                .datalist(dtoObjects);
    }
    /**
     *保存用户自定义资源
     *
     * @param sysResourceEntities
     * @author liuke
     * @date 2021/12/3 16:14
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/save/useruesource")
    @SysLog(optModule = "资源维护",optName = "用户保存自定义资源",optType = OptLogType.SAVE)
    public ResponseParam saveUserResource(@RequestBody List<SysUserResourceEntity> sysResourceEntities){
        sysUserResourceService.saveAfterDelete(sysResourceEntities);
        return ResponseParam.success().message("保存成功");
    }

    @PostMapping("query/system/resource")
    public ResponseParam queryResourceByAppCodeAndUserIdAndRole(@RequestBody SysResourceDto dto) {
        Map<String,Object> searchParams = Maps.newHashMap();
        searchParams.put("appCode",dto.getAppCode());
        searchParams.put("roleId",dto.getRoleId());
        searchParams.put("userId",dto.getUserId());
        List<SysResourceMenuVo> sysResourceMenuVos = sysResourceService.selectByUserIdAndAppCode(searchParams);
        sysResourceMenuVos.forEach(resource -> {
            if(resource.getParentId() == null){
                resource.setParentId("0");
            }
        });
        List<Map<String,Object>> resultLists = UtilDTO.toDTO(sysResourceMenuVos,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(resultLists);
    }


    /**
     * 获取PO到DTO转换的接口。主要用于在前端展示数据之前首先将数据格式处理完成。p
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