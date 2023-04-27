package com.geek.system.pbs.api.impl;

import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.pbs.api.SystemOrgApi;
import com.geek.system.pbs.dto.entity.AppSysOrgEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public abstract class AbstractSystemOrgApi extends AppIBaseController implements SystemOrgApi {
    @Override
    public ResponseParam queryAllSonOrgs(Long parentId) {
        return null;
    }

    @Override
    public ResponseParam querySubOrgByName(String parentId, String orgName) {
        return null;
    }

    @Override
    public ResponseParam querySubOrgsByCode(@PathVariable("project") String project,String orgCode) {
        return null;
    }

    @Override
    public ResponseParam queryOrgInfo(String orgId) {
        return null;
    }

    @Override
    public ResponseParam queryUserOrg(String userId) {
        return null;
    }

    @Override
    public ResponseParam queryAllChildOrg(Long parentId) {
        return null;
    }
    @Override
    public ResponseParam queryAllOrgTree(Long projectId,@RequestParam String orgType){
        return null;
    }

    @Override
    public ResponseParam queryAllOrgTreeByprojectCode(@PathVariable("project") String project, @RequestParam("orgType") String orgType){
        return null;
    }

    @Override
    public ResponseParam queryOrgTreeByOrgId(Long projectId){
        return null;
    }

    @Override
    public ResponseParam queryAllOrgLazy( String orgId,String projectCode, String orgType){
        return null;
    }


    @Override
    public ResponseParam queryByCodeAndLevelType(@PathVariable("projectCode") String projectCode, @RequestBody Map<String,Object> map){
        return null;
    }

    @Override
    public List<Map<String,Object>> queryAllByCodeAndLevelType(@PathVariable("project") String project,@RequestBody Map<String,Object> map){
        return null;
    }

    @Override
    public List<Map<String, Object>> queryScopOrgByUser(@PathVariable("project") String project, @RequestParam("roleCode") String roleCode, @RequestParam("userId") Long userId){
        return null;
    }

    @Override
    public List<Map<String, Object>> queryScopOrgTreeByUser(@PathVariable("project") String project, @RequestParam("roleCode") String roleCode, @RequestParam("userId") Long userId){
        return null;
    }

    @Override
    public List<Map<String,Object>> queryOrgBylevel(@PathVariable("project") String project,
                                                    @Param("parentId") Long parentId,
                                                    @Param("levelType") String levelType,
                                                    @Param("orgType") String orgType){
        return null;
    }

    @Override
    public int queryOrgCountBylevel(@PathVariable("project") String project,
                                    @Param("parentId") Long parentId,
                                    @Param("levelType") String levelType,
                                    @Param("orgType") String orgType){
        return 0;
    }

    @Override
    public ResponseParam delOrg(@PathVariable("project") String project,@RequestParam("orgIds") Long[] orgIds){
        return null;
    }

    @Override
    public ResponseParam saveOrg(@PathVariable("project") String project, @RequestBody List<AppSysOrgEntity> sysOrgs){
        return null;
    }

    @Override
    public ResponseParam updateOrg(@PathVariable("project") String project,@RequestBody AppSysOrgEntity sysOrg){
        return null;
    }

}
