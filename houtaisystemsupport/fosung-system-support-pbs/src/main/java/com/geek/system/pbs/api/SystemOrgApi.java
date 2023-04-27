package com.geek.system.pbs.api;

import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.pbs.dto.entity.AppSysOrgEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "SystemOrgApi", url = "${app.pbs.client.url}")
@RequestMapping(value = "/api/system/org")
public interface SystemOrgApi {



    /**
     * 查询所有子节点
     *
     * @param parentId
     * @return
     */
    @RequestMapping("allsonorg")
    ResponseParam queryAllSonOrgs(@RequestParam("parentId") Long parentId);


    /**
     * 根据名称查询子节点
     *
     * @param parentId
     * @return
     */
    @PostMapping("suborg/byname")
    ResponseParam querySubOrgByName(@RequestParam("parentId") String parentId, @RequestParam("orgName") String orgName);
    /**
     * 根据code查询下级子节点
     *
     * @param orgCode
     * @return
     */
    @PostMapping("/suborg/code/{project}")
    ResponseParam querySubOrgsByCode(@PathVariable("project") String project,@RequestParam String orgCode) ;
    /**
     * 查询组织机构信息
     *
     * @param orgId
     * @return
     */
    @PostMapping("info")
    ResponseParam queryOrgInfo(@RequestParam("orgId") String orgId);
    /**
     * 查询用户所属的组织机构
     *
     * @param userId
     * @return
     */
    @PostMapping("user")
    ResponseParam queryUserOrg(@RequestParam("userId") String userId);

    @PostMapping("query/all/childorg")
    ResponseParam queryAllChildOrg(@RequestParam("parentId") Long parentId);

    /**
     *查询组织目录树
     *
     * @author liuke
     * @date 2021/11/5 9:57
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/all/tree")
    ResponseParam queryAllOrgTree(Long projectCode,@RequestParam String orgType);

    /**
     *根据项目编码查询组织目录树
     *
     * @param orgType (administration 行政)
     * @author liuke
     * @date 2021/12/8 9:22
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/org/tree/{project}")
    ResponseParam queryAllOrgTreeByprojectCode(@PathVariable("project") String project,@RequestParam("orgType") String orgType);

    @PostMapping("/tree/byid")
    ResponseParam queryOrgTreeByOrgId(Long orgId);

    @PostMapping("/allorglazylist")
    ResponseParam queryAllOrgLazy(@RequestParam(value = "orgId",required = false,defaultValue = "") String orgId,@RequestParam("projectCode") String projectCode,@RequestParam("orgType") String orgType);

    /**
     *分页查询组织
     * map值
     * ("appId","appId:EQ");
     * 	("id","id:EQ");
     * 	("ids","id:IN");
     * 	("del","del:EQ");
     * 	("orgName","orgName:EQ");
     * 	("orgNameL","orgName:LIKE");
     * 	("orgCode","orgCode:RLIKE");
     * 	("parentId","parentId:EQ");
     * 	("hasChildren","hasChildren:EQ");
     * 	("level","level:EQ");
     * ("status","status:EQ");
     * 	("orgType","orgType:EQ");
     * 	("parentIdIsNull","parentId:ISNULL");
     * 	("projectId","projectId:EQ");
     * 	("projectIds","projectId:IN");
     * 	("parentIds","parentId:IN");
     * 	("levelType","levelType:EQ");
     * 	("levelTypes","levelType:IN");
     * 	("orgCodeEq","orgCode:EQ");
     * 	("leaf","leaf:EQ");
     * 	("dtOrgId","dtOrgId:EQ");
     * 	pageNum  --必需
     * 	pageSize  --必需
     *
     * @param source
     * @param map
     * @author liuke
     * @date 2022/3/18 14:13
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/queryOrg/page/{projectCode}")
    ResponseParam queryByCodeAndLevelType(@PathVariable("projectCode") String projectCode, @RequestBody Map<String,Object> map);

    /**
     *分页查询组织
     * map值
     * ("appId","appId:EQ");
     * 	("id","id:EQ");
     * 	("ids","id:IN");
     * 	("del","del:EQ");
     * 	("orgName","orgName:EQ");
     * 	("orgNameL","orgName:LIKE");
     * 	("orgCode","orgCode:RLIKE");
     * 	("parentId","parentId:EQ");
     * 	("hasChildren","hasChildren:EQ");
     * 	("level","level:EQ");
     * ("status","status:EQ");
     * 	("orgType","orgType:EQ");
     * 	("parentIdIsNull","parentId:ISNULL");
     * 	("projectId","projectId:EQ");
     * 	("projectIds","projectId:IN");
     * 	("parentIds","parentId:IN");
     * 	("levelType","levelType:EQ");
     * 	("levelTypes","levelType:IN");
     * 	("orgCodeEq","orgCode:EQ");
     * 	("leaf","leaf:EQ");
     * 	("dtOrgId","dtOrgId:EQ");
     *
     * @param map
     * @author liuke
     * @date 2022/3/18 14:13
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "/queryOrg/all/{project}")
    List<Map<String,Object>> queryAllByCodeAndLevelType(@PathVariable("project") String project,@RequestBody Map<String,Object> map);
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
    List<Map<String, Object>> queryScopOrgByUser(@PathVariable("project") String project, @RequestParam("roleCode") String roleCode, @RequestParam("userId") Long userId);

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
    List<Map<String, Object>> queryScopOrgTreeByUser(@PathVariable("project") String project, @RequestParam("roleCode") String roleCode, @RequestParam("userId") Long userId);
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
    List<Map<String,Object>> queryOrgBylevel(@PathVariable("project") String project,
                                                    @RequestParam("parentId") Long parentId,
                                                    @RequestParam("levelType") String levelType,
                                                    @RequestParam("orgType") String orgType);

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
    int queryOrgCountBylevel(@PathVariable("project") String project,
                                    @RequestParam("parentId") Long parentId,
                                    @RequestParam("levelType") String levelType,
                                    @RequestParam("orgType") String orgType);

    /**
     *批量删除
     *
     * @param orgIds
     * @author liuke
     * @date 2022/5/5 10:30
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "delord/{project}")
    ResponseParam delOrg(@PathVariable("project") String project,@RequestParam("orgIds") Long[] orgIds);

    /**
     *新增组织接口
     *
     * @param project
     * @param sysOrgs
     * @author liuke
     * @date 2022/5/5 10:45
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "saveord/{project}")
    ResponseParam saveOrg(@PathVariable("project") String project,@RequestBody List<AppSysOrgEntity> sysOrgs);


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
    ResponseParam updateOrg(@PathVariable("project") String project,@RequestBody AppSysOrgEntity sysOrg);
}

