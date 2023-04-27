package com.geek.system.pbs.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.framework.web.mvc.config.session.common.AppUserInfo;
import com.geek.system.pbs.api.SystemUserApi;
import com.geek.system.pbs.dto.entity.AppSysUser;
import com.geek.system.pbs.dto.entity.SysAppUserRole;
import com.geek.system.pbs.dto.resp.SysUserEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractSystemUserApi extends AppIBaseController implements SystemUserApi {
    @Override
    public ResponseParam queryByUserId(String userid) {
        return null;
    }

    @Override
    public ResponseParam queryByIdentifyId(String identifyId,String projectCode) {
        return null;
    }

    @Override
    public ResponseParam queryByUserName(String userName,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode) {
        return null;
    }

    @Override
    public ResponseParam queryByTelephone(String userName,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode) {
        return null;
    }
    @Override
    public ResponseParam queryByWxCode(String wxcode,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode) {
        return null;
    }


    @Override
    public ResponseParam queryOrgUsers(String orgId) {
        return null;
    }

    @Override
    public ResponseParam queryByPhoneAndIDCard(String realName, String idCard) {
        return null;
    }

    @Override
    public ResponseParam queryUserByOrd(String orgId) {
        return null;
    }

//    @Override
//    public ResponseParam queryPageUserByOrg(SysUserDto user) {
//        return null;
//    }

    @Override
    public ResponseParam queryByUserIds(ArrayList<String> ids) {
        return null;
    }

    @Override
    public ResponseParam queryCountOrgUsers(String orgId) {
        return null;
    }

    @Override
    public ResponseParam getUserDetailByUserId(Long userId) {
        return null;
    }

    @Override
    public ResponseParam getPlateFormAppsByUserId(Long userId) {
        return null;
    }

    @Override
    public ResponseParam getUsersByAppId(Long appId) {
        return null;
    }

    @Override
    public ResponseParam queryResourceByAppCodeAndUserIdAndRole(@RequestParam String appCode,@RequestParam Long userId,@RequestParam String roleId) {
        return null;
    }

    /**
     * 根据应用编码与角色id查询资源
     *
     * @param appCode
     * @param roleId
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2022/6/28 14:05
     */
    @Override
    public ResponseParam queryResourceByAppCodeAndRole(String appCode, String roleId) {
        return null;
    }

    /**
     * 根据应用编码与角色id查询app
     *
     * @param appCode
     * @param roleId
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2022/6/28 14:05
     */
    @Override
    public ResponseParam queryAppByAppCodeAndRole(String appCode, String roleId) {
        return null;
    }

    @Override
    public ResponseParam queryResourceByAppCodeAndUserId(String appCode, String userId) {
        return null;
    }
    @Override
    public ResponseParam queryUser(){
        return null;
    }

    @Override
    public ResponseParam queryMailListByOrgId( Long orgId){
        return null;
    }

    @Override
    public ResponseParam queryAllMailListByOrgId( Map<String, Object> map ){
        return null;
    }
    @Override
    public ResponseParam queryAllMailLazy(@RequestParam("orgId") String orgId,@RequestParam("projectCode") String projectCode,@RequestParam("orgType") String orgType){
        return null;
    }

    @Override
    public ResponseParam saveUserById(JSONObject user){
        return null;
    }

    @Override
    public AppUserInfo getUserInfo(@RequestParam String idCardHash,@RequestParam String clientId){
        return null;
    }

    @Override
    public ResponseParam queryUserByRoleCode(String roleCode){
        return null;
    }

    @Override
    public ResponseParam queryUserByRoleCodes(String project,String[] roleCodes){
        return null;
    }

    @Override
    public List<SysUserEntity> queryAllByProjectId(@RequestParam("projectCode") String projectCode) {
        return null;
    }

    @Override
    public List<SysUserEntity> queryAllUser(String projectCode, Map<String, Object> map){
        return null;
    }

    @Override
    public List<Map<String,Object>> queryAllManagerUser(String projectCode, Map<String, Object> map){
        return null;
    }
    @Override
    public ResponseParam queryWorkerByScopOrg( String realName,ArrayList<Long> orgIds, int pageNum, int pageSize){
        return null;
    }

    @Override
    public ResponseParam queryWorkerCountByScopOrg(@RequestParam("orgIds") ArrayList<Long> orgIds){
        return null;
    }

    @Override
    public List<Map<String, Object>> queryUser(@PathVariable("project") String project, @RequestBody Map<String, Object> sysUserDto){
        return null;
    }

    @Override
    public ResponseParam delUserByPhone(@PathVariable("project") String project,@RequestParam("telephones") String[] telephones){
        return  null;
    }

    @Override
    public ResponseParam delUser(@PathVariable("project") String project,@RequestParam("userIds") Long[] userIds){
        return null;
    }

    @Override
    public ResponseParam saveUser(@PathVariable("project") String project,@RequestBody List<AppSysUser> sysUsers){
        return null;
    }


    @Override
    public ResponseParam updateUser(@PathVariable("project") String project,@RequestBody AppSysUser sysUser){
        return null;
    }

    @Override
    public ResponseParam queryPageManagerUser(@PathVariable("projectCode") String projectCode,@RequestBody Map<String,Object> map){
        return null;
    }
    @Override
    public List<SysAppUserRole> queryUserRole(@PathVariable("project") String project, @RequestParam("userId") Long userId, @RequestParam("appCode") String appCode){
        return null;
    }


}
