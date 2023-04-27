package com.geek.system.support.system.controller;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.entity.sys.AuthClientEntity;
import com.geek.system.support.system.entity.sys.SysOrgEntity;
import com.geek.system.support.system.entity.sys.SysUserEntity;
import com.geek.system.support.system.service.sys.AuthClientService;
import com.geek.system.support.system.service.sys.SysOrgService;
import com.geek.system.support.system.service.sys.SysProjectService;
import com.geek.system.support.system.service.sys.SysUserService;
import com.geek.system.support.system.util.PassWordUtil;
import com.geek.system.support.system.util.UserConstant;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/system/auth")
public class SystemAuthController extends AppIBaseController {


    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysProjectService sysProjectService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysOrgService sysOrgService;

    @Autowired
    private AuthClientService authClientService;

    /**
     *获取登录信息
     *
     * @param username
     * @param password
     * @param logonChannelType
     * @author liuke
     * @date 2021/4/28 9:22
     * @return com.fosung.cloud.pbs.rest.client.dto.CloudAuthUser
     */
    @PostMapping("login")
    public ResponseParam loginByUserNameAndPassWord(@RequestParam String username,@RequestParam String password,@RequestParam String logonChannelType) {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("userName",username);
        //只查询可用状态账号信息
        searchParam.put("status","0");
        Map map = Maps.newHashMap();
        List<SysUserEntity> sysUserEntitys = sysUserService.queryAll(searchParam);
        if(!UtilCollection.sizeIsEmpty(sysUserEntitys)){
             map = UtilDTO.toDTO(sysUserEntitys.get(0),null,getDtoCallbackHandler());
        }
        return ResponseParam.success().data(map);

    }


    /**
     *用户注册接口
     *
     * @param realName
     * @param idCard
     * @param telephone
     * @param password
     * @author liuke
     * @date 2021/12/09 9:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("register")
    public ResponseParam register(@RequestParam("realName") String realName,@RequestParam("idCard") String idCard,@RequestParam("telephone") String telephone,@RequestParam("password") String password,@RequestParam(value = "projectCode",required = false) String projectCode,@RequestParam(value = "orgCode",required = false) String orgCode) {

        try{
            // 获取租户id
            Long projectId = sysProjectService.getProjectId(projectCode);
            Map<String,Object> telParam = Maps.newHashMap();
            telParam.put("telephone",telephone);
            telParam.put("projectId",projectId);
            telParam.put("del",false);
            if(sysUserService.isExist(telParam)){
                return ResponseParam.saveFail().message("手机号已注册");
            }
            // 判断用户身份证是否存在
            Map<String,Object> idCardParam = Maps.newHashMap();
            idCardParam.put("projectId",projectId);
            idCardParam.put("idCard", PassWordUtil.encrypt(idCard));
            idCardParam.put("del",false);
            if(sysUserService.isExist(idCardParam)){
                return ResponseParam.saveFail().message(UserConstant.CHECK_ID_CARD);
            }
            SysUserEntity sysUserEntity = new SysUserEntity();
            if(UtilString.isNotBlank(orgCode)){
                Map<String,Object> searchParam = Maps.newHashMap();
                searchParam.put("orgCodeEq",orgCode);
                List<SysOrgEntity> sysOrgs = sysOrgService.queryAll(searchParam);
                if(UtilCollection.isNotEmpty(sysOrgs)){
                    sysUserEntity.setOrgId(sysOrgs.get(0).getId());
                    sysUserEntity.setOrgCode(sysOrgs.get(0).getOrgCode());
                    sysUserEntity.setOrgName(sysOrgs.get(0).getOrgName());
                }
            }
            sysUserEntity.setUserName(telephone);
            sysUserEntity.setTelephone(telephone);
            sysUserEntity.setRealName(realName);
            sysUserEntity.setPassword(password);
            sysUserEntity.setIdCard(PassWordUtil.encrypt(idCard));
            sysUserEntity.setDel(false);
            sysUserEntity.setProjectCode(projectCode);
            sysUserEntity.setProjectId(projectId);
            sysUserService.save(sysUserEntity);
            return ResponseParam.saveSuccess().message("注册成功");
        }catch (Exception e){
            return ResponseParam.saveFail().message("注册失败");
        }
    }
    /**
     *微信用户注册接口
     *
     * @author liuke
     * @date 2021/12/09 9:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("wxregister")
    public ResponseParam wxRegister(@RequestParam(value = "projectCode",required = false) String projectCode,@RequestParam(value = "wxOpenId") String wxOpenId,@RequestParam(value = "wxUnitId",required = false) String wxUnitId,@RequestParam(value = "userName",required = false) String userName) {

        try{
            // 获取租户id
            Long projectId = sysProjectService.getProjectId(projectCode);
            Map<String,Object> telParam = Maps.newHashMap();
            telParam.put("wxOpenId",wxOpenId);
            telParam.put("projectId",projectId);
            telParam.put("del",false);
            if(sysUserService.isExist(telParam)){
                return ResponseParam.saveFail().message("微信号已注册");
            }
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserEntity.setDel(false);
            sysUserEntity.setProjectCode(projectCode);
            sysUserEntity.setProjectId(projectId);
            sysUserEntity.setWxOpenId(wxOpenId);
            sysUserEntity.setUserName(userName);
            sysUserEntity.setWxUnitId(wxUnitId);
            sysUserService.save(sysUserEntity);
            return ResponseParam.saveSuccess().message("注册成功");
        }catch (Exception e){
            return ResponseParam.saveFail().message("注册失败");
        }
    }
    /**
     *微信完善用户信息
     *
     * @param telephone
     * @param password
     * @author liuke
     * @date 2021/12/09 9:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("wxsupplement")
    public ResponseParam wxSupplement(@RequestParam("telephone") String telephone,@RequestParam("password") String password,@RequestParam(value = "projectCode") String projectCode,@RequestParam(value = "wxOpenId") String wxOpenId) {

        try{
            // 获取租户id
            Long projectId = sysProjectService.getProjectId(projectCode);
            Map<String,Object> telParam = Maps.newHashMap();
            telParam.put("wxOpenId",wxOpenId);
            telParam.put("projectId",projectId);
            telParam.put("del",false);
            List<SysUserEntity> sysUserEntities = sysUserService.queryAll(telParam);
            if(UtilCollection.sizeIsEmpty(sysUserEntities)){
                return ResponseParam.saveFail().message("微信用户不存在");
            }
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserEntity.setUserName(telephone);
            sysUserEntity.setTelephone(telephone);
            sysUserEntity.setPassword(password);
            sysUserEntity.setId(sysUserEntities.get(0).getId());
            sysUserService.update(sysUserEntity, Lists.newArrayList("userName","telephone","password","idCard"));
            return ResponseParam.saveSuccess().message("保存成功");
        }catch (Exception e){
            return ResponseParam.saveFail().message("注册失败");
        }
    }

    /**
     *重置密码
     *
     * @param telephone
     * @param password
     * @author liuke
     * @date 2021/4/28 10:15
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/reset/password")
    public ResponseParam resetPassword(@RequestParam("telephone") String telephone,@RequestParam("password") String password,@RequestParam(value = "projectCode",required = false) String projectCode) {
        Map<String, Object> searchParam = Maps.newHashMap();
        // 获取租户id
        Long projectId = sysProjectService.getProjectId(projectCode);
        searchParam.put("telephone", telephone);
        searchParam.put("projectId",projectId);
        List<SysUserEntity> sysUserEntitys = sysUserService.queryAll(searchParam);
        if (!UtilCollection.sizeIsEmpty(sysUserEntitys)) {
            SysUserEntity updateUserEntity = sysUserEntitys.get(0);
            updateUserEntity.setPassword(password);
            sysUserService.update(updateUserEntity,Sets.newHashSet("password"));
            return ResponseParam.updateSuccess();
        }else {
            return ResponseParam.updateFail().message("用户不存在");
        }
    }

    /**
     *重置密码
     *
     * @param telephone
     * @param password
     * @author liuke
     * @date 2021/4/28 10:15
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/reset/propassword")
    public ResponseParam resetProjectPassword(@RequestParam("telephone") String telephone,@RequestParam("password") String password,@RequestParam("project") String project) {
        Map<String, Object> searchParam = Maps.newHashMap();
        searchParam.put("telephone", telephone);
        searchParam.put("projectCode",project);
        List<SysUserEntity> sysUserEntitys = sysUserService.queryAll(searchParam);
        if (!UtilCollection.sizeIsEmpty(sysUserEntitys)) {
            SysUserEntity updateUserEntity = sysUserEntitys.get(0);
            updateUserEntity.setPassword(password);
            sysUserService.update(updateUserEntity,Sets.newHashSet("password"));
            return ResponseParam.updateSuccess();
        }else {
            return ResponseParam.updateFail().message("用户不存在");
        }
    }
    /**
     *更新密码
     *
     * @param userId
     * @param originPassword
     * @param newPassword
     * @author liuke
     * @date 2021/4/28 10:20
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/update/password")
    public ResponseParam updatePassword(@RequestParam("userId") String userId,@RequestParam("originPassword") String originPassword,@RequestParam("newPassword") String newPassword) {
        SysUserEntity sysUserEntity = sysUserService.get(Long.valueOf(userId));

        if (sysUserEntity!=null) {
            if(passwordEncoder.matches(originPassword,sysUserEntity.getPassword())){
                sysUserEntity.setPassword(newPassword);
                sysUserService.update(sysUserEntity, Sets.newHashSet("password"));
                return ResponseParam.updateSuccess();
            }else {
             return ResponseParam.saveFail().message("原密码不正确");
            }
        }else {
            return ResponseParam.updateFail().message("用户不存在");
        }
    }



    /**
     *绑定手机号
     *
     * @param telephone
     * @param userName
     * @author liuke
     * @date 2021/12/9 14:05
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/bind/phone")
    public ResponseParam bindPhone(@RequestParam("telephone") String telephone, @RequestParam("userName") String userName,@RequestParam(value = "projectCode" ,required = false) String projectCode){
        if(UtilString.isBlank(telephone)||UtilString.isBlank(userName)){
            return ResponseParam.fail().message("缺少参数");
        }
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("userName",userName);
        searchParam.put("projectId",sysProjectService.getProjectId(projectCode));
        List<SysUserEntity> sysUserEntities = sysUserService.queryAll(searchParam);
        if(UtilCollection.sizeIsEmpty(sysUserEntities)){
            return ResponseParam.fail().message("用户名不存在");
        }
        SysUserEntity sysUserEntity = sysUserEntities.get(0);
        sysUserEntity.setTelephone(telephone);
        sysUserService.update(sysUserEntity,Sets.newHashSet("telephone"));
        return ResponseParam.success().message("绑定成功");
    }

    /**
     * 根据clientId查询认证信息
     * @param project
     * @param clientId
     * @return
     */
    @PostMapping("query/client/{project}")
    public Map<String,Object> getClient(@PathVariable("project") String project,
                                              @RequestParam("clientId") String clientId){

        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("projectId",sysProjectService.getProjectId(project));
        searchParam.put("clientId",clientId);
        List<AuthClientEntity> authClientEntities = authClientService.queryAll(searchParam);
        Map<String,Object> map = Maps.newHashMap();
        if(UtilCollection.isNotEmpty(authClientEntities)){
            map = UtilDTO.toDTO(authClientEntities.get(0),null,null,getDtoCallbackHandler());
        }
        return map;

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
