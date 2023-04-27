package com.geek.system.support.system.controller;

import com.alibaba.fastjson.JSON;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.common.util.UtilTree;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.entity.sys.ReturnTreeData;
import com.geek.system.support.system.entity.sys.SysOrgEntity;
import com.geek.system.support.system.service.pbs.PbsOrgService;
import com.geek.system.support.system.service.sys.SysOrgService;
import com.geek.system.support.system.service.sys.SysProjectService;
import com.geek.system.support.system.util.CodeGenerateTool;
import com.geek.system.support.util.StringTool;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/system/org")
public class SystemOrgController extends AppIBaseController {



    @Autowired
    private SysOrgService sysOrgService;

    @Autowired
    private SysProjectService sysProjectService;

    @Autowired
    private PbsOrgService pbsOrgService;


    /**
     * 查询父节点下所有子节点
     *
     * @param parentId
     * @return
     */
    @RequestMapping("allsonorg")
    public ResponseParam queryAllSonOrgs(@RequestParam("parentId") Long parentId) {
        List<ReturnTreeData> returnTreeDataList = sysOrgService.queryOrgsByParentId(parentId);
        List<Map<String,Object>> lists= UtilDTO.toDTO(returnTreeDataList,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(lists);
    }


    /**
     * 根据名称查询子节点
     *
     * @param parentId
     * @return
     */
    @PostMapping("suborg/byname")
    public ResponseParam querySubOrgByName(@RequestParam("parentId") String parentId,@RequestParam("orgName") String orgName) {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("parentId",parentId);
        searchParam.put("orgNameL",orgName);
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        List<Map<String,Object>> lists= UtilDTO.toDTO(sysOrgEntities,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(lists);
    }
    /**
     * 根据code查询下级子节点
     *
     * @param orgCode
     * @return
     */
    @PostMapping("/suborg/code/{project}")
    public ResponseParam querySubOrgsByCode(@PathVariable("project") String project,@RequestParam("orgCode") String orgCode) {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("orgCodeEq",orgCode);
        searchParam.put("projectId",sysProjectService.getProjectId(project));
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            Map<String,Object> childSearchParam = Maps.newHashMap();
            childSearchParam.put("parentId",sysOrgEntities.get(0).getId());
            List<SysOrgEntity> chileSysOrgEntities = sysOrgService.queryAll(childSearchParam);
            List<Map<String,Object>> lists= UtilDTO.toDTO(chileSysOrgEntities,null,getDtoCallbackHandler());
            return ResponseParam.success().datalist(lists);
        }
        return ResponseParam.success().datalist(Lists.newArrayList());

    }
    /**
     * 查询组织机构信息
     *
     * @param orgId
     * @return
     */
    @PostMapping("info")
    public ResponseParam queryOrgInfo(@RequestParam("orgId") String orgId) {
        SysOrgEntity sysOrgEntity = sysOrgService.get(Long.valueOf(orgId));
        Map<String,Object> map = UtilDTO.toDTO(sysOrgEntity,null,getDtoCallbackHandler());
        return ResponseParam.success().data(map);
    }
    /**
     * 查询用户所属的组织机构
     *
     * @param userId
     * @return
     */
    @PostMapping("user")
    public ResponseParam queryUserOrg(@RequestParam("userId") String userId) {
        SysOrgEntity sysOrgEntity = sysOrgService.getOrgByUserId(Long.valueOf(userId));
        Map<String,Object> map = UtilDTO.toDTO(sysOrgEntity,null,getDtoCallbackHandler());
        return ResponseParam.success().data(map);
    }

    /**
     *查询下级所有节点包含子节点
     *
     * @param parentId
     * @author liuke
     * @date 2022/5/17 10:16
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("query/all/childorg")
    public ResponseParam queryAllChildOrg(@RequestParam("parentId") Long parentId) {
        Set<SysOrgEntity> sysOrgEntities = sysOrgService.getOrgsByParentId(parentId);
        for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
            //设置排序默认值，否则排序时报错
            if(sysOrgEntity.getNum()==null){
                sysOrgEntity.setNum(9999);
            }
        }
        List<SysOrgEntity> orgEntities = sysOrgEntities.stream().sorted(Comparator.comparing(SysOrgEntity::getNum)).collect(Collectors.toList());
        List<Map<String,Object>> results = UtilDTO.toDTO(orgEntities,null,getDtoCallbackHandler());
        results= UtilTree.getTreeData(results,"id","parentId","children",false);

        return ResponseParam.success().datalist(results);
    }

    /**
     *根据项目id查询组织目录树
     *
     * @param orgType (administration 行政)
     * @author liuke
     * @date 2021/12/8 9:22
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/org/tree/{project}")
    public ResponseParam queryAllOrgTreeByprojectCode(@PathVariable("project") String project,@RequestParam("orgType") String orgType){
        List<ReturnTreeData> returnTreeData = sysOrgService.queryOrgTree(sysProjectService.getProjectId(project),orgType);
        List<Map<String,Object>> result = UtilDTO.toDTO(returnTreeData,getDtoCallbackHandler());
        result = UtilTree.getTreeData(result,"id","parentId","children",false,true);
        return ResponseParam.success().datalist(result);
    }

    /**
     *根据项目id查询组织目录树
     *
     * @param projectId
     * @author liuke
     * @date 2021/12/8 9:22
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/all/tree")
    public ResponseParam queryAllOrgTree(@RequestParam Long projectId,@RequestParam String orgType){
        List<ReturnTreeData> returnTreeData = sysOrgService.queryOrgTree(projectId,orgType);
        List<Map<String,Object>> result = UtilDTO.toDTO(returnTreeData,getDtoCallbackHandler());
        result = UtilTree.getTreeData(result,"id","parentId","children",false,true);
        return ResponseParam.success().datalist(result);
    }

    /**
     *根据组织id获取低级目录树
     *
     * @param orgId
     * @author liuke
     * @date 2021/12/8 9:51
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/tree/byid")
    public ResponseParam queryOrgTreeByOrgId(@RequestParam("orgId") Long orgId){
        List<ReturnTreeData> returnTreeData = sysOrgService.queryReturnOrgTree(orgId);
        List<Map<String,Object>> result = UtilDTO.toDTO(returnTreeData,getDtoCallbackHandler());
        result = UtilTree.getTreeData(result,"id","parentId","children",false,true);
        return ResponseParam.success().datalist(result);
    }

    /**
     *懒加载查询组织
     *
     * @param orgId
     * @param projectCode
     * @param orgType
     * @author liuke
     * @date 2022/1/14 10:08
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/allorglazylist")
    ResponseParam queryAllOrgLazy(@RequestParam(value = "orgId",required = false,defaultValue = "") String orgId,@RequestParam("projectCode") String projectCode,@RequestParam("orgType") String orgType){
        Long projectId = sysProjectService.getProjectId(projectCode);
        List<ReturnTreeData> list = sysOrgService.queryOrgTreeLazy(orgId,projectId,orgType);
        return ResponseParam.success().datalist(list);
    }

    /**
     *根据组织code，级别类型查询下级组织
     *
     * @param project
     * @author liuke
     * @date 2022/2/23 15:27
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/queryOrg/page/{project}")
    ResponseParam queryByCodeAndLevelType(@PathVariable("project") String project,@RequestBody Map<String,Object> map){
        Long projectId = sysProjectService.getProjectId(project);
        map.put("projectId",projectId);
        if(map.get("orgNameL")!=null){
            map.put("orgNameL", StringTool.escapeQueryChars(map.get("orgNameL").toString()));
        }
        if(map.get("orgCode")!=null){
            map.put("orgCode", StringTool.escapeQueryChars(map.get("orgCode").toString()));
        }
        if(map.get("parentId")!=null){
            map.put("parentId", Long.valueOf(map.get("parentId").toString()));
        }
        Pageable pageable = MybatisPageRequest.of(Integer.valueOf(map.get("pageNum").toString()),Integer.valueOf(map.get("pageSize").toString()));
        Page<SysOrgEntity> sysOrgs = pbsOrgService.queryByPage(map,pageable);
        List<Map<String, Object>> sysOrgList = UtilDTO.toDTO(sysOrgs.getContent(),null,
                null , getDtoCallbackHandler()) ;
        return ResponseParam.success().pageParam(sysOrgs).datalist(sysOrgList);
    }

    /**
     *根据组织code，级别类型查询下级组织
     *
     * @param project
     * @author liuke
     * @date 2022/2/23 15:27
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/queryOrg/all/{project}")
    List<Map<String,Object>> queryAllByCodeAndLevelType(@PathVariable("project") String project,@RequestBody Map<String,Object> map){
        Long projectId = sysProjectService.getProjectId(project);
        map.put("projectId",projectId);
        List<SysOrgEntity> sysOrgs = sysOrgService.queryAll(map);
        List<Map<String, Object>> sysOrgList = UtilDTO.toDTO(sysOrgs,null,
                null , getDtoCallbackHandler()) ;
        return sysOrgList;
    }


    /**
     *查询用户管理范围
     *
     * @param project
     * @param roleCode
     * @param userId
     * @author liuke
     * @date 2022/3/31 15:58
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/scop/org/{project}")
    List<Map<String, Object>> queryScopOrgByUser(@PathVariable("project") String project, @RequestParam("roleCode") String roleCode, @RequestParam("userId") Long userId){
        Map<String,Object> map = Maps.newHashMap();
        map.put("projectId",sysProjectService.getProjectId(project));
        map.put("userId",userId);
        map.put("roleCode",roleCode);
        return sysOrgService.queryScopOrgByUser(map);
    }

    /**
     *查询用户管理范围组织树
     *
     * @param project
     * @param roleCode
     * @param userId
     * @author liuke
     * @date 2022/3/31 15:58
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/scop/orgtree/{project}")
    List<Map<String, Object>> queryScopOrgTreeByUser(@PathVariable("project") String project, @RequestParam("roleCode") String roleCode, @RequestParam("userId") Long userId){
        Map<String,Object> map = Maps.newHashMap();
        map.put("projectId",sysProjectService.getProjectId(project));
        map.put("userId",userId);
        map.put("roleCode",roleCode);
        List<Map<String,Object>> list = Lists.newArrayList();
        Map<String,Object> searchmap = Maps.newHashMap();
        searchmap.put("projectId",sysProjectService.getProjectId(project));
        for (Map<String, Object> stringObjectMap : sysOrgService.queryScopOrgByUser(map)) {
            searchmap.put("orgCode",stringObjectMap.get("orgCode"));
            List<Map<String, Object>> sysOrgList = UtilDTO.toDTO(sysOrgService.queryAll(searchmap),null,
                    null , getDtoCallbackHandler()) ;
            list.addAll(sysOrgList);
        }
        return UtilTree.getTreeData(list,"id","parentId","children",true);
    }

    /**
     *根据组织级别查询组织
     *
     * @param project
     * @param parentId
     * @param levelType
     * @param orgType
     * @author liuke
     * @date 2022/4/6 9:21
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "query/org/level/{project}")
    public List<Map<String,Object>> queryOrgBylevel(@PathVariable("project") String project,
                                                    @RequestParam("parentId") Long parentId,
                                                    @RequestParam("levelType") String levelType,
                                                    @RequestParam("orgType") String orgType){
        Map<String,Object> param = Maps.newHashMap();
        param.put("parentId",parentId);
        param.put("levelType",levelType);
        param.put("orgType",orgType);
        param.put("projectId",sysProjectService.getProjectId(project));
        return pbsOrgService.queryOrgBylevel(param);

    }

    /**
     *根据组织级别查询组织总数
     *
     * @param project
     * @param parentId
     * @param levelType
     * @param orgType
     * @author liuke
     * @date 2022/4/6 9:21
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "query/orgcount/level/{project}")
    public int queryOrgCountBylevel(@PathVariable("project") String project,
                                                    @RequestParam("parentId") Long parentId,
                                                    @RequestParam("levelType") String levelType,
                                                    @RequestParam("orgType") String orgType){
        Map<String,Object> param = Maps.newHashMap();
        param.put("parentId",parentId);
        param.put("levelType",levelType);
        param.put("orgType",orgType);
        param.put("projectId",sysProjectService.getProjectId(project));
        return pbsOrgService.queryOrgCountBylevel(param);

    }

    /**
     *批量删除
     *
     * @param orgIds
     * @author liuke
     * @date 2022/5/5 10:30
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "delord/{project}")
    public ResponseParam delOrg(@PathVariable("project") String project,@RequestParam("orgIds") Long[] orgIds){
        Long projectId = sysProjectService.getProjectId(project);
        Map<String,Object> map = Maps.newHashMap();
        map.put("projectId",projectId);
        map.put("ids",orgIds);
        pbsOrgService.deleteByIds(map);
        return ResponseParam.deleteSuccess();
    };

    /**
     *新增组织接口
     *
     * @param project
     * @author liuke
     * @date 2022/5/5 10:45
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "saveord/{project}")
    public ResponseParam saveOrg(@PathVariable("project") String project,@RequestBody List<Map<String,Object>> sysOrgMap){

        List<SysOrgEntity> sysOrgs = Lists.newArrayList();
        for (Map<String, Object> map : sysOrgMap) {
            sysOrgs.add(JSON.parseObject(JSON.toJSONString(map),SysOrgEntity.class));
        }
        //获取租户信息
        Long projectId = sysProjectService.getProjectId(project);

        List<SysOrgEntity> parentIsnull = Lists.newArrayList();
        for (SysOrgEntity sysOrg : sysOrgs) {
            //校验组织编码
            if(UtilString.isNotBlank(sysOrg.getOrgCode())){
                if(sysOrg.getOrgCode().length()% CodeGenerateTool.CODE_LENGTH !=0){
                    return ResponseParam.fail().message("组织编码长度不正确");
                }
            }
            if(sysOrg.getParentId()==null){
                parentIsnull.add(sysOrg);
            }
            sysOrg.setProjectId(projectId);
            sysOrg.setSource("pbs");
            sysOrg.setSourceName("pbs录入");
        }
        if(parentIsnull.size()>1){
            return ResponseParam.fail().message("只能存在一个组织目录");
        }else if(parentIsnull.size()==1){
            HashMap<String, Object> existParam = Maps.newHashMap();
            existParam.put("projectId",projectId);
            existParam.put("parentIdIsNull","132");
            existParam.put("orgType",parentIsnull.get(0).getOrgType()==null?"administration":parentIsnull.get(0).getOrgType());
            existParam.put("del",false);
            if(sysOrgService.isExist(existParam)){
                return ResponseParam.fail().message("只能存在一个组织目录");
            }
        }

        pbsOrgService.saveinfo(sysOrgs);
        return ResponseParam.saveSuccess() ;
    };

    /**
     *修改组织编码信息
     *
     * @param project
     * @param sysOrg
     * @author liuke
     * @date 2022/5/5 10:53
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "updateord/{project}")
    public ResponseParam updateOrg(@PathVariable("project") String project,@RequestBody Map<String,Object> org){
        SysOrgEntity sysOrg = JSON.parseObject(JSON.toJSONString(org),SysOrgEntity.class);
        //校验组织编码
        if(UtilString.isNotBlank(sysOrg.getOrgCode())){
            if(sysOrg.getOrgCode().length()% CodeGenerateTool.CODE_LENGTH !=0){
                return ResponseParam.fail().message("组织编码长度不正确");
            }
        }
        //获取租户信息
        Long projectId = sysProjectService.getProjectId(project);
        sysOrg.setProjectId(projectId);

        SysOrgEntity sysOrgEntity = sysOrgService.get(sysOrg.getId());
        if(sysOrg.getOrgName()!=null&&!sysOrg.getOrgName().equals(sysOrgEntity.getOrgName())){
            HashMap<String, Object> existParam = Maps.newHashMap();
            existParam.put("projectId",sysOrg.getProjectId());
            existParam.put("orgName",sysOrg.getOrgName());
            existParam.put("parentId",sysOrg.getParentId());
            existParam.put("del",false);
            if(sysOrgService.isExist(existParam)){
                return ResponseParam.fail().message("同一节点下组织名称不允许重复！");
            }
        }
        //由请求参数中获取需要更新的字段
        Map<String,Object> map = UtilDTO.toDTO(sysOrg,null,null,getDtoCallbackHandler());
        Set<String> cloums = Sets.newHashSet();
        for (String s : map.keySet()) {
            if(map.get(s)!=null){
                cloums.add(s);
            }
        }
        //按照字段更新对象
        sysOrgService.update( sysOrg , cloums ) ;

        return ResponseParam.updateSuccess() ;
    };


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

