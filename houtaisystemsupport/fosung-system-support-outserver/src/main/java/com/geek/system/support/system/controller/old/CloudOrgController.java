package com.geek.system.support.system.controller.old;

import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.common.util.UtilTree;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.entity.sys.SysOrgEntity;
import com.geek.system.support.system.service.sys.SysOrgService;
import com.geek.system.support.system.service.sys.SysProjectService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * 组织查询对外类
 *
 * @author liuke
 * @date  2022/2/23 15:20
 * @version
*/
@RestController
@RequestMapping("/{source}/api/cloud/org")
public class CloudOrgController {

    @Autowired
    private SysOrgService sysOrgService;

    @Autowired
    private SysProjectService sysProjectService;

    /**
     *查询根组织机构
     *
     * @param source
     * @author liuke
     * @date 2022/2/23 15:22
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/root")
    List<Map<String, Object>> queryRootOrgs(@PathVariable("source") String source) {
        List<Map<String,Object>> lists = Lists.newArrayList();
        Long projectId = sysProjectService.getProjectId(source);
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("projectId",projectId);
        searchParam.put("parentIdIsNull","12");
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
            }
        }
        return lists;
    }


    /**
     *
     * 组织信息保存接口（id为空时新增，id不为空时修改该组织）
     *
     * @author liuke
     * @date  2022/2/23 15:22
     * @version
    */
    @PostMapping(value = "/save")
    ResponseParam save(@PathVariable("source") String source, @RequestBody Map<String, Object> orgDto) {
        return null;
    }

    /**
     *删除组织接口 id 是组织id
     *
     * @param source
     * @param orgId
     * @author liuke
     * @date 2022/2/23 15:22
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/delete")
    ResponseParam delete(@PathVariable("source") String source, @RequestParam("orgId") String orgId){
        return null;
    }

    /**
     *获取上级组织
     *
     * @param source
     * @param orgId
     * @author liuke
     * @date 2022/2/23 15:23
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/query/parentOrgs")
    List<Map<String, Object>> parentOrgs(@PathVariable("source") String source,
                              @RequestParam("orgId") String orgId){
        return null;
    }

    /**
     *开放省内安全中心查询获取组织管理范围
     *
     * @param source
     * @param parentId
     * @author liuke
     * @date 2022/2/23 15:23
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping(value = "/queryOrgInfo/{parentId}")
    List<Map<String, Object>> querySimpleDataOrgs(@PathVariable("source") String source,
                                    @PathVariable(value = "parentId") String parentId){
        return null;
    }


    /**
     *开放省内安全中心查询获取组织管理范围
     *
     * @param source
     * @param parentId
     * @author liuke
     * @date 2022/2/23 15:23
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/suborg")
    List<Map<String, Object>> querySubOrgs(@PathVariable("source") String source, @RequestParam(value = "parentId", name = "parentId") String parentId){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("parentId",parentId);
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam,new String[]{"num","id"});
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
            }
        }
        return lists;
    }

    /**
     *批量查询子节点
     *
     * @param source
     * @param parentIds
     * @author liuke
     * @date 2022/2/23 15:23
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/querySubOrg")
    Set<Map<String, Object>> batchQuerySubOrg(@PathVariable("source") String source, @RequestParam(value = "parentIds", name = "parentIds") ArrayList<String> parentIds){
        Set<Map<String,Object>> list = Sets.newHashSet();
        for (String parentId : parentIds) {
            SysOrgEntity sysOrgEntity = sysOrgService.get(Long.valueOf(parentId));
            Map<String,Object> map = parseSysOrgEntutyToMap(sysOrgEntity);
            if(sysOrgEntity!=null){
                List<Map<String,Object>> sonlist = Lists.newArrayList();
                Map<String,Object> searchParam = Maps.newHashMap();
                searchParam.put("parentId",parentId);
                List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
                if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
                    for (SysOrgEntity sysOrg : sysOrgEntities) {
                        sonlist.add(parseSysOrgEntutyToMap(sysOrg));
                    }
                }
                map.put("children",sonlist);
            }
            if(!map.isEmpty()){
                list.add(map);
            }
        }
        return list;

    }

    /**
     *分页查询下级组织
     *
     * @param source
     * @param orgLevelType
     * @param parentId
     * @param orgName
     * @param pageSize
     * @param pageNum
     * @author liuke
     * @date 2022/2/23 15:23
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/suborg/page")
    ResponseParam subOrgPage(@PathVariable("source") String source,
                             @RequestParam(value = "orgLevelType", name = "orgLevelType", required = false) String orgLevelType,
                             @RequestParam(value = "parentId", name = "parentId") String parentId,
                             @RequestParam(value = "orgName", name = "orgName", required = false) String orgName,
                             @RequestParam(value = "pageSize", name = "pageSize", defaultValue = "10") Integer pageSize,
                             @RequestParam(value = "pageNum", name = "pageNum", defaultValue = "1") Integer pageNum){
        Map<String, Object> searchParam =  Maps.newHashMap();
        searchParam.put("parentId",parentId);
        searchParam.put("levelType",orgLevelType);
        searchParam.put("orgNameL",orgName);
        //执行分页查询
        String[] order = new String[]{"num"};
        Page<SysOrgEntity> sysOrgEntities = sysOrgService.queryByPage(searchParam , pageNum , pageSize,order);
        List<Map<String,Object>> resultList = Lists.newArrayList();
        for (SysOrgEntity sysOrgEntity : sysOrgEntities.getContent()) {
            resultList.add(parseSysOrgToSysOrg(sysOrgEntity));
        }
        return ResponseParam.success().pageParam(sysOrgEntities).data(resultList);
    }

    /**
     *更新类型
     *
     * @param source
     * @param orgId
     * @param orgLevelType
     * @author liuke
     * @date 2022/2/23 15:24
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/update/level")
    ResponseParam updateLevel(@PathVariable("source") String source, @RequestParam("orgId") String orgId,
                              @RequestParam("orgLevelType") String orgLevelType){
        return null;
    }

    /**
     *批量更新组织类型
     *
     * @param source
     * @param orgIds
     * @param orgLevelType
     * @author liuke
     * @date 2022/2/23 15:24
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/update/levels")
    ResponseParam updateLevels(@PathVariable("source") String source,
                               @RequestParam("orgIds") String[] orgIds,
                               @RequestParam("orgLevelType") String orgLevelType){
        return null;
    }

    /**
     *查询下级组织
     *
     * @param source
     * @param parentId
     * @param orgName
     * @author liuke
     * @date 2022/2/23 15:24
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/subOrgByName")
    List<Map<String, Object>> querySubOrgByName(@PathVariable("source") String source,
                                     @RequestParam(value = "parentId", name = "parentId") String parentId,
                                     @RequestParam(value = "orgName", name = "orgName", required = false) String orgName){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("parentId",parentId);
        searchParam.put("orgNameL",orgName);
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
            }
        }
        return lists;
    }

    /**
     *根据code查询下级子节点
     *
     * @param source
     * @param orgCode
     * @author liuke
     * @date 2022/2/23 15:24
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/suborg/code")
    List<Map<String, Object>> querySubOrgsByCode(@PathVariable("source") String source,
                                      @RequestParam(value = "orgCode", name = "orgCode") String orgCode){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Map<String,Object> param = Maps.newHashMap();
        param.put("orgCodeEq",orgCode);
        List<SysOrgEntity> sysOrgs = sysOrgService.queryAll(param);
        if(UtilCollection.sizeIsEmpty(sysOrgs)){
            return lists;
        }
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("parentId",sysOrgs.get(0).getId());
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
            }
        }
        return lists;
    }

    /**
     *查询组织机构信息
     *
     * @param source
     * @param orgId
     * @author liuke
     * @date 2022/2/23 15:25
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping(value = "/info")
    Map<String, Object> queryOrgInfo(@PathVariable("source") String source, @RequestParam(value = "orgId", name = "orgId") String orgId){
        if("null".equals(orgId)||"undefined".equals(orgId)){
            return Maps.newHashMap();
        }
        SysOrgEntity sysOrgEntity = sysOrgService.get(Long.valueOf(orgId));
        if(sysOrgEntity== null){
            return Maps.newHashMap();
        }
        return parseSysOrgEntutyToMap(sysOrgEntity);
    }

    /**
     *批量查询组织机构信息
     *
     * @param source
     * @param orgIds
     * @author liuke
     * @date 2022/2/23 15:25
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/queryOrgInfo")
    List<Map<String, Object>> batchQueryOrgInfo(@PathVariable("source") String source, @RequestParam(value = "orgIds", name = "orgIds") ArrayList<String> orgIds){
        Map<String,Object> searchParam = Maps.newHashMap();
        List<Map<String,Object>> lists = Lists.newArrayList();
        searchParam.put("ids",orgIds);
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
            }
        }
        return lists;
    }

    /**
     *根据code查询机构信息
     *
     * @param source
     * @param orgCode
     * @author liuke
     * @date 2022/2/23 15:25
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping(value = "/code")
    Map<String, Object> queryOrgByCode(@PathVariable("source") String source, @RequestParam(value = "orgCode", name = "orgCode") String orgCode){
        Map<String,Object> param = Maps.newHashMap();
        param.put("orgCodeEq",orgCode);
        List<SysOrgEntity> sysOrgs = sysOrgService.queryAll(param);
        if(UtilCollection.sizeIsEmpty(sysOrgs)){
            return null;
        }else {
            return parseSysOrgEntutyToMap(sysOrgs.get(0));
        }
    }

    /**
     *查询用户所属的组织机构
     *
     * @param source
     * @param userId
     * @author liuke
     * @date 2022/2/23 15:25
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping(value = "/user")
    Map<String, Object> queryUserOrg(@PathVariable("source") String source, @RequestParam(value = "userid", name = "userid") String userId){
        SysOrgEntity sysOrgEntity = sysOrgService.getOrgByUserId(Long.valueOf(userId));
        if(sysOrgEntity!=null){
            return parseSysOrgEntutyToMap(sysOrgEntity);
        }else {
            return null;
        }
    }

    /**
     *根据名字模糊匹配下级组织
     *
     * @param source
     * @param orgCode
     * @param orgName
     * @author liuke
     * @date 2022/2/23 15:25
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/search/suborg")
    List<Map<String, Object>> searchSubOrg(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode,
                                @RequestParam("orgName") String orgName){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Long projectId = sysProjectService.getProjectId(source);
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("projectId",projectId);
        searchParam.put("orgNameL",orgName);
        searchParam.put("orgCode",orgCode);
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
            }
        }
        return lists;
    }

    /**
     *获取所有上级，返回树形结构数据
     *
     * @param source
     * @param orgId
     * @author liuke
     * @date 2022/2/23 15:25
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/query/allParentOrg")
    List<Map<String, Object>> getAllParentOrg(@PathVariable("source") String source, @RequestParam("orgId") String orgId){
        List<Map<String,Object>> lists = Lists.newArrayList();
        List<SysOrgEntity> sysOrgEntities = sysOrgService.getParentOrgs(Long.valueOf(orgId) );
        for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
            lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
        }
        lists= UtilTree.getTreeData(lists,"orgId","parentId","children",false);
        return lists;
    }

    /**
     *批量获取所有上级，返回树形结构数据
     *
     * @param source
     * @param orgIds
     * @author liuke
     * @date 2022/2/23 15:26
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/queryAllParentOrgByIds")
    List<Map<String, Object>> queryAllParentOrgByIds(@PathVariable("source") String source, @RequestParam("orgIds") ArrayList<Long> orgIds){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Set<SysOrgEntity> sysOrgEntities = Sets.newHashSet();
        for (Long orgId : orgIds) {
            sysOrgEntities.addAll(sysOrgService.getParentOrgs(Long.valueOf(orgId) ));
        }
        for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
            lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
        }
        lists= UtilTree.getTreeData(lists,"orgId","parentId","children",false);
        return lists;
    }

    /**
     *根据orgID获取所有最下级组织
     *
     * @param source
     * @param orgId
     * @author liuke
     * @date 2022/2/23 15:26
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/query/leaf/org")
    List<Map<String, Object>> leafOrgs(@PathVariable("source") String source, @RequestParam("orgId") String orgId){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Set<SysOrgEntity> sysOrgEntities = sysOrgService.getOrgsByParentId(Long.valueOf(orgId));
        sysOrgEntities=sysOrgEntities.stream().filter(map ->map.getLeaf()).collect(Collectors.toSet());
        for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
            lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
        }
        return lists;
    }

    /**
     *分页查询最下级组织
     *
     * @param source
     * @param orgId
     * @param pageNum
     * @param pageSize
     * @author liuke
     * @date 2022/2/23 15:26
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/query/leaf/page")
    ResponseParam queryLeafPage(@PathVariable("source") String source, @RequestParam("orgId") String orgId,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        List<Map<String,Object>> lists = new ArrayList<>();
        SysOrgEntity sysOrgEntity = sysOrgService.get(Long.valueOf(orgId));
        Map<String,Object> map = Maps.newHashMap();
        map.put("orgCode",sysOrgEntity.getOrgCode());
        map.put("leaf",true);
        Page<SysOrgEntity> page = sysOrgService.queryByPage(map,pageNum,pageSize,new String[]{"num"});
        for (SysOrgEntity orgEntity : page.getContent()) {
            lists.add(parseSysOrgToSysOrg(orgEntity));
        }
        Map<String,Object> resultMap = Maps.newHashMap();
        resultMap.put("pageable",page.getPageable());
        resultMap.put("last",page.isLast());
        resultMap.put("totalElements",page.getTotalElements());
        resultMap.put("totalPages",page.getTotalPages());
        resultMap.put("number",page.getNumber());
        resultMap.put("size",page.getSize());
        resultMap.put("sort",page.getSort());
        resultMap.put("numberOfElements",page.getNumberOfElements());
        resultMap.put("first",page.isFirst());
        resultMap.put("content",lists);
        return ResponseParam.success().data(resultMap);
    }

    /**
     *查询最下级组织根据name和code匹配
     *
     * @param source
     * @param orgCode
     * @param orgName
     * @param flag
     * @param pageNum
     * @param pageSize
     * @author liuke
     * @date 2022/2/23 15:26
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/query/leaf")
    ResponseParam queryLeafByNameAndCode(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode,
                                         @RequestParam(value = "orgName", required = false) String orgName,
                                         @RequestParam(value = "pageFlag") boolean flag,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Map<String,SysOrgEntity> parentMap = Maps.newHashMap();
        List<Map<String,Object>> pageData= Lists.newArrayList();
        Map<String,Object> map = Maps.newHashMap();
        map.put("orgCode",orgCode);
        map.put("orgNameL",orgName);
        map.put("leaf",true);
        if(!flag){


            List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(map);
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                Map<String,Object> submap = Maps.newHashMap();
                String townnameCode = sysOrgEntity.getOrgCode().substring(0,30);
                String countynameCode = sysOrgEntity.getOrgCode().substring(0,24);
                String citynameCode = sysOrgEntity.getOrgCode().substring(0,18);
                submap.put("code",sysOrgEntity.getOrgCode());
                submap.put("townname",getOrgByCode(townnameCode,parentMap).getOrgName());
                submap.put("townid",getOrgByCode(townnameCode,parentMap).getId());
                submap.put("countyname",getOrgByCode(countynameCode,parentMap).getOrgName());
                submap.put("countyid",getOrgByCode(countynameCode,parentMap).getId());
                submap.put("cityname",getOrgByCode(citynameCode,parentMap).getOrgName());
                submap.put("cityid",getOrgByCode(citynameCode,parentMap).getId());
                submap.put("id",sysOrgEntity.getId());
                submap.put("mgareaname","");
                submap.put("mgareaid","");
                pageData.add(submap);
            }
            Map<String,Object>  pageDataMap = Maps.newHashMap();
            pageDataMap.put("pageData",pageData);
            return ResponseParam.success().data(pageDataMap);
        }else {
            Page<SysOrgEntity> page = sysOrgService.queryByPage(map,pageNum,pageSize,new String[]{"num"});
            for (SysOrgEntity sysOrgEntity : page.getContent()) {
                Map<String,Object> submap = Maps.newHashMap();
                String townnameCode = sysOrgEntity.getOrgCode().substring(0,30);
                String countynameCode = sysOrgEntity.getOrgCode().substring(0,24);
                String citynameCode = sysOrgEntity.getOrgCode().substring(0,18);
                submap.put("code",sysOrgEntity.getOrgCode());
                submap.put("townname",getOrgByCode(townnameCode,parentMap).getOrgName());
                submap.put("townid",getOrgByCode(townnameCode,parentMap).getId());
                submap.put("countyname",getOrgByCode(countynameCode,parentMap).getOrgName());
                submap.put("countyid",getOrgByCode(countynameCode,parentMap).getId());
                submap.put("cityname",getOrgByCode(citynameCode,parentMap).getOrgName());
                submap.put("cityid",getOrgByCode(citynameCode,parentMap).getId());
                submap.put("id",sysOrgEntity.getId());
                submap.put("mgareaname","");
                submap.put("mgareaid","");
                pageData.add(submap);
            }
            Map<String,Object>  pageDataMap = Maps.newHashMap();
            pageDataMap.put("pageData",pageData);
            pageDataMap.put("total",page.getTotalElements());
            return ResponseParam.success().data(pageDataMap);
        }
    }



    /**
     *模糊查询所有村级组织
     *
     * @param source
     * @param orgName
     * @param leaf
     * @author liuke
     * @date 2022/2/23 15:26
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/queryByName")
    List<Map<String, Object>> queryVillageOrg(@PathVariable("source") String source,
                                   @RequestParam(value = "orgName", required = false) String orgName,
                                   @RequestParam(value = "leaf") Boolean leaf){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Long projectId = sysProjectService.getProjectId(source);
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("projectId",projectId);
        searchParam.put("orgNameL",orgName);
        searchParam.put("leaf",leaf);
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
            }
        }
        return lists;
    }

    /**
     *根据组织类型和code查询下级组织
     *
     * @param source
     * @param orgCode
     * @param orgLevel
     * @author liuke
     * @date 2022/2/23 15:26
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/query")
    ResponseParam queryByCodeAndLevel(@PathVariable("source") String source,
                                      @RequestParam(value = "orgCode") String orgCode, @RequestParam(value = "orgLevel") String orgLevel){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Map<String,Object> param = Maps.newHashMap();
        param.put("orgCodeEq",orgCode);
        List<SysOrgEntity> sysOrgs = sysOrgService.queryAll(param);
        if(UtilCollection.sizeIsEmpty(sysOrgs)){
            return ResponseParam.fail().message("组织不存在");
        }
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("parentId",sysOrgs.get(0).getId());
        searchParam.put("levelType",orgLevel);
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                lists.add(parseSysOrgToSysOrg(sysOrgEntity));
            }
        }
        return ResponseParam.success().data(lists);
    }

    /**
     *根据组织名称分页查询
     *
     * @param source
     * @param map
     * @author liuke
     * @date 2022/2/23 15:26
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/name")
    ResponseParam queryByNamePage(@PathVariable("source") String source, @RequestBody Map<String, Object> map){
        return null;
    }

    /**
     *查询上级id
     *
     * @param source
     * @param param
     * @author liuke
     * @date 2022/2/23 15:27
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/parent")
    ResponseParam getParentId(@PathVariable("source") String source, @RequestBody Map<String, Object> param){
        if(param.get("id")==null){
            return null;
        }
        param.put("id",Long.valueOf(param.get("id").toString()));
        SysOrgEntity sysOrgEntity = sysOrgService.queryParentOrgById(param);
        if(sysOrgEntity==null){
            return ResponseParam.fail().message("组织不存在");
        }
        return ResponseParam.success().data(sysOrgEntity);
    }

    /**
     *根据组织code直接查询所有下级组织数量包含本级
     *
     * @param source
     * @param orgCode
     * @author liuke
     * @date 2022/2/23 15:27
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/countByOrgCode")
    ResponseParam countByOrgCode(@PathVariable("source") String source, @RequestParam(value = "orgCode") String orgCode){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Map<String,Object> param = Maps.newHashMap();
        param.put("orgCodeEq",orgCode);
        List<SysOrgEntity> sysOrgs = sysOrgService.queryAll(param);
        if(UtilCollection.sizeIsEmpty(sysOrgs)){
            return ResponseParam.success().data(0);
        }
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("parentId",sysOrgs.get(0).getId());
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        return ResponseParam.success().data(sysOrgEntities.size()+1);
    }

    /**
     *根据组织code，级别类型分类查询下级数量
     *
     * @param source
     * @param orgCode
     * @author liuke
     * @date 2022/2/23 15:27
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/countGroupLevelType")
    ResponseParam countGroupLevelType(@PathVariable("source") String source, @RequestParam(value = "orgCode") String orgCode){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Map<String,Object> param = Maps.newHashMap();
        param.put("orgCodeEq",orgCode);
        List<SysOrgEntity> sysOrgs = sysOrgService.queryAll(param);
        if(UtilCollection.sizeIsEmpty(sysOrgs)){
            return ResponseParam.success().data(0);
        }
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("parentId",sysOrgs.get(0).getId());
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        Map<String,List<SysOrgEntity>> map = sysOrgEntities.stream().collect(Collectors.groupingBy(SysOrgEntity::getLevelType));
        for (String levelType : map.keySet()) {
            Map<String,Object> res = Maps.newHashMap();
            res.put("levelType",levelType);
            res.put("count",map.get(levelType).size());
            lists.add(res);
        }
        return ResponseParam.success().data(lists);

    }

    /**
     *根据组织code，级别类型查询下级组织
     *
     * @param source
     * @param orgCode
     * @param levelType
     * @author liuke
     * @date 2022/2/23 15:27
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/queryByCodeAndLevelType")
    ResponseParam queryByCodeAndLevelType(@PathVariable("source") String source,
                                          @RequestParam(value = "orgCode") String orgCode,
                                          @RequestParam(value = "levelType") String levelType){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Map<String,Object> param = Maps.newHashMap();
        param.put("orgCodeEq",orgCode);
        List<SysOrgEntity> sysOrgs = sysOrgService.queryAll(param);
        if(UtilCollection.sizeIsEmpty(sysOrgs)){
            return ResponseParam.fail().message("组织不存在");
        }
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("parentId",sysOrgs.get(0).getId());
        searchParam.put("levelType",levelType);
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam,new String[]{"num"});
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                Map<String,Object> map = Maps.newHashMap();
                map.put("parentName",sysOrgs.get(0).getOrgName());
                map.put("code",sysOrgEntity.getOrgCode());
                map.put("level",sysOrgEntity.getLevel());
                map.put("levelType",sysOrgEntity.getLevelType());
                map.put("name",sysOrgEntity.getOrgName());
                map.put("leaf",sysOrgEntity.getLeaf());
                map.put("orgid",sysOrgEntity.getId());
                map.put("parentId",sysOrgEntity.getParentId());
                lists.add(map);
            }
        }
        return ResponseParam.success().data(lists);
    }

    /**
     *
     * 查询下级所有节点
     * @author liuke
     * @date 2022/2/23 15:27
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/queryAllSubOrg")
    ResponseParam queryAllSubOrg(@PathVariable("source") String source, @RequestParam(value = "orgCode") String orgCode){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Map<String,Object> param = Maps.newHashMap();
        param.put("orgCodeEq",orgCode);
        List<SysOrgEntity> sysOrgs = sysOrgService.queryAll(param);
        if(UtilCollection.sizeIsEmpty(sysOrgs)){
            return ResponseParam.fail().message("组织不存在");
        }
        Set<SysOrgEntity> sysOrgEntities = sysOrgService.getOrgsByParentId(sysOrgs.get(0).getId());
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
            }
        }
        return ResponseParam.success().data(lists);
    }

    /**
     *查询下级所有节点
     *
     * @param source
     * @param orgCode
     * @author liuke
     * @date 2022/2/23 15:27
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/queryAllSubSysOrg")
    List<Map<String, Object>> queryAllSubSysOrg(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode){
        List<Map<String,Object>> lists = Lists.newArrayList();
        Map<String,Object> param = Maps.newHashMap();
        param.put("orgCodeEq",orgCode);
        List<SysOrgEntity> sysOrgs = sysOrgService.queryAll(param);
        if(UtilCollection.sizeIsEmpty(sysOrgs)){
            return Lists.newArrayList();
        }
        Set<SysOrgEntity> sysOrgEntities = sysOrgService.getOrgsByParentId(sysOrgs.get(0).getId());
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
            }
        }
        return lists;
    }

    /**
     *获取所有上级
     *
     * @param source
     * @param orgId
     * @author liuke
     * @date 2022/2/23 15:28
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/queryAllParentOrg")
    List<Map<String, Object>> queryAllParentOrg(@PathVariable("source") String source, @RequestParam("orgId") String orgId){
        List<Map<String,Object>> lists = Lists.newArrayList();
        List<SysOrgEntity> sysOrgEntities = sysOrgService.getParentOrgs(Long.valueOf(orgId) );
        for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
            if(sysOrgEntity.getId()!=Long.valueOf(orgId)){
                lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
            }
        }
        lists= UtilTree.getTreeData(lists,"orgId","parentId","children",false);
        return lists;
    }


    /**
     *分页查询 orgCode下带有人员的组织,且统计出组织中的人数
     *
     * @param source
     * @param orgCode
     * @param pageNum
     * @param pageSize
     * @author liuke
     * @date 2022/2/23 15:28
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping(value = "/queryHaveUserOrg")
    ResponseParam queryHaveUserOrg(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return null;
    }

    /**
     *组织树通过组织名称搜索，以树的形式返回
     *
     * @param source
     * @param rootId
     * @param name
     * @author liuke
     * @date 2022/2/23 15:28
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/queryTreeNodeByName")
    ResponseParam queryTreeNodeByName(@PathVariable("source") String source, @RequestParam("rootId") Long rootId, @RequestParam(value = "name",required = false,defaultValue = "") String name){
        List<Map<String,Object>> lists = Lists.newArrayList();
        SysOrgEntity sysOrg = sysOrgService.get(rootId);
        Long projectId = sysProjectService.getProjectId(source);
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("projectId",projectId);
        searchParam.put("orgNameL",name);
        searchParam.put("orgCode",sysOrg.getOrgCode());
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        Set<SysOrgEntity> sysOrgEntitieList = Sets.newHashSet();
        if(UtilString.isNotEmpty(name)){
            for (SysOrgEntity org : sysOrgEntities) {
                sysOrgEntitieList.addAll(sysOrgService.getParentOrgs(org.getId() ,Long.valueOf(rootId)));
            }
            sysOrgEntitieList.add(sysOrgService.get(Long.valueOf(rootId)));
        }else {
            sysOrgEntitieList.addAll(sysOrgEntities);
        }
        for (SysOrgEntity sysOrgEntity : sysOrgEntitieList) {
            lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
        }
        lists= UtilTree.getTreeData(lists,"orgId","parentId","children",false);
        return ResponseParam.success().data(lists);
    }

    /**
     *查询所有上级组织id包含本级，并按组织等级由大到小顺序排序
     *
     * @param source
     * @param orgId
     * @param rootId
     * @author liuke
     * @date 2022/2/23 15:28
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/queryAllParentId")
    ResponseParam queryAllParentId(@PathVariable("source") String source, @RequestParam("orgId") Long orgId, @RequestParam("rootId") Long rootId){

        List<Long> ids = sysOrgService.getParentOrgs(orgId ,Long.valueOf(rootId)).stream().map(SysOrgEntity::getId).collect(Collectors.toList());
        return ResponseParam.success().data(ids);
    }

    /**
     *根据组织id和级别类型查询上级组织
     *
     * @param source
     * @param orgId
     * @param levelType
     * @author liuke
     * @date 2022/2/23 15:28
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/queryByLevelType")
    List<Map<String, Object>> queryByLevelType(@PathVariable("source") String source,
                                    @RequestParam("orgId") Long orgId,
                                    @RequestParam(required = false, value = "levelType",defaultValue = "COUNTRY") String levelType){
        List<Map<String, Object>> lists = Lists.newArrayList();
        List<SysOrgEntity> sysOrgEntities = sysOrgService.getParentOrgs(orgId,levelType);
        if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
            }
        }
        return lists;
    }

    /**
     *批量根据组织id和级别类型查询上级组织
     *
     * @param source
     * @param orgIds
     * @param levelType
     * @author liuke
     * @date 2022/2/23 15:29
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/queryAllParentOrgByLevelType")
    List<Map<String, Object>> queryAllParentOrgByLevelType(@PathVariable("source") String source,
                                                @RequestParam("orgIds") ArrayList<Long> orgIds,
                                                @RequestParam(required = false, value = "levelType") String levelType){
        Set<Map<String, Object>> lists = Sets.newHashSet();
        for (Long orgId : orgIds) {
            List<SysOrgEntity> sysOrgEntities = sysOrgService.getParentOrgs(orgId,levelType);
            if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
                for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                    lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
                }
            }
        }
        return Lists.newArrayList(lists);
    }

    /**
     *获取直接上级组织
     *
     * @param source
     * @param orgId
     * @author liuke
     * @date 2022/2/23 15:29
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping(value = "/getUpOrg")
    Map<String, Object> getUpOrg(@PathVariable("source") String source, @RequestParam("orgId") Long orgId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("id",Long.valueOf(orgId));
        SysOrgEntity sysOrgEntity = sysOrgService.queryParentOrgById(param);

        return parseSysOrgEntutyToMap(sysOrgEntity);
    }

    /**
     *查询组织信息
     *
     * @param source
     * @param cloudOrg
     * @author liuke
     * @date 2022/2/23 15:29
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping(value = "/querySysOrg")
    List<Map<String, Object>> querySysOrg(@PathVariable("source") String source, @RequestBody Map<String, Object> cloudOrg){
        List<Map<String, Object>> lists = Lists.newArrayList();
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("id",cloudOrg.get("id"));
        searchParam.put("orgNameL",cloudOrg.get("name"));
        searchParam.put("orgCodeEq",cloudOrg.get("code"));
        searchParam.put("level",cloudOrg.get("level"));
        searchParam.put("levelType",cloudOrg.get("levelType"));
        searchParam.put("leaf",cloudOrg.get("leaf"));
        searchParam.put("parentId",cloudOrg.get("parentId"));
        searchParam.put("dtOrgId",cloudOrg.get("outId"));
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam);
        if(UtilCollection.sizeIsEmpty(sysOrgEntities)){
            return lists;
        }else {
            for (SysOrgEntity sysOrgEntity : sysOrgEntities) {
                lists.add(parseSysOrgEntutyToMap(sysOrgEntity));
            }
            return lists;
        }
    }


    /**
     * 系统管理-根据节点查询当前组织下所有组织信息
     *
     * @param source
     * @param orgId
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/4/27 15:54
     */
    @PostMapping(value = "/queryallchildorg")
    ResponseParam queryAllChildOrg(@PathVariable("source") String source, @RequestParam("orgId") Long orgId){
        return null;
    }

    /**
     *将SysOrgEntity转为所需map
     *
     * @param syOrgEntity
     * @author liuke
     * @date 2022/2/16 14:54
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String,Object> parseSysOrgEntutyToMap(SysOrgEntity syOrgEntity){
        if(syOrgEntity==null){
            return Maps.newHashMap();
        }
        Map<String,Object> map = new HashMap<String,Object>(){{
            put("orgId",syOrgEntity.getId());
            put("orgName",syOrgEntity.getOrgName());
            put("code",syOrgEntity.getOrgCode());
            put("level",syOrgEntity.getLevel());
            put("levelType",syOrgEntity.getLevelType());
            put("isLeaf",syOrgEntity.getLeaf());
            put("parentId",syOrgEntity.getParentId());
            put("outId",syOrgEntity.getDtOrgId());
            put("outCode",syOrgEntity.getDtOrgCode());
            put("outName",syOrgEntity.getDtOrgName());
            put("num",syOrgEntity.getNum());
            put("children",Lists.newArrayList());
        }};
        return map;
    }

    /**
     *转换为肥城类
     *
     * @param orgEntity
     * @author liuke
     * @date 2022/2/23 15:29
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String,Object> parseSysOrgToSysOrg(SysOrgEntity orgEntity){
        Map<String,Object> sonMap = Maps.newHashMap();
        sonMap.put("id",orgEntity.getId());
        sonMap.put("createUserId",orgEntity.getCreateUserId());
        sonMap.put("createDatetime",orgEntity.getCreateDatetime());
        sonMap.put("lastUpdateUserId",orgEntity.getLastUpdateUserId());
        sonMap.put("lastUpdateDatetime",orgEntity.getLastUpdateDatetime());
        sonMap.put("name",orgEntity.getOrgName());
        sonMap.put("code",orgEntity.getOrgCode());
        sonMap.put("parentId",orgEntity.getParentId());
        sonMap.put("leaf",orgEntity.getLeaf());
        sonMap.put("source",orgEntity.getSource());
        sonMap.put("sourceName",orgEntity.getSourceName());
        sonMap.put("sourceRoot",orgEntity.getParentId()==null);
        sonMap.put("outId",orgEntity.getDtOrgId());
        sonMap.put("outCode",orgEntity.getDtOrgCode());
        sonMap.put("outParentId",null);
        sonMap.put("outName",orgEntity.getOrgName());
        sonMap.put("level",orgEntity.getLevel());
        sonMap.put("levelType",orgEntity.getLevelType());
        sonMap.put("sort",orgEntity.getNum());
        return sonMap;
    }


    private SysOrgEntity getOrgByCode(String code,Map<String,SysOrgEntity> parentMap){

        if(parentMap.get(code)==null){
            Map<String,Object> map = Maps.newHashMap();
            map.put("orgCodeEq",code);
            List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(map);
            if(!UtilCollection.sizeIsEmpty(sysOrgEntities)){
                parentMap.put(code,sysOrgEntities.get(0));
                return sysOrgEntities.get(0);
            }else {
                return new SysOrgEntity();
            }
        }else {
            return parentMap.get(code);
        }
    }
}