package com.geek.system.support.system.controller.old;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.mq.MQMessageConstant;
import com.geek.system.support.system.dict.*;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.entity.sys.SysOrgEntity;
import com.geek.system.support.system.entity.sys.SysPostEntity;
import com.geek.system.support.system.entity.sys.SysUserEntity;
import com.geek.system.support.system.entity.sys.SysUserPostScopeEntity;
import com.geek.system.support.system.service.mq.SysMQProducerService;
import com.geek.system.support.system.service.sys.*;
import com.geek.system.support.system.service.sys.*;
import com.geek.system.support.system.util.PassWordUtil;
import com.geek.system.support.system.util.UserConstant;
import com.geek.system.support.system.dict.AuthStatus;
import com.geek.system.support.system.dict.ShelvesType;
import com.geek.system.support.system.dict.SysMqUserTypeConstant;
import com.geek.system.support.system.dict.UserStatus;
import com.google.api.client.util.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{source}/api/cloud/user")
@SuppressWarnings("unchecked")
public class CloudUserController extends AppIBaseController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysProjectService sysProjectService;
    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private SysPostService sysPostService;
    @Autowired
    private SysUserPostScopeService sysUserPostScopeService;
    @Autowired
    private SysMQProducerService sysMQProducerService;


    /**
     * 根据用户id查询用户信息
     *
     * @param source
     * @param userid
     * @return
     */
    @PostMapping(value = "/get")
    Map<String, Object> queryByUserId(@PathVariable("source") String source, @RequestParam(name = "userid") String userid) {
        if (StringUtils.isBlank(userid)) {
            return null;
        }
        // 查询用户信息
        SysUserEntity sysUserEntity = sysUserService.get(Long.parseLong(userid));
        // 转换肥城项目字段
        sysUserEntity.setHeadUrl(sysUserEntity.getHeadImgUrl() == null ? null : sysUserEntity.getHeadImgUrl());
        sysUserEntity.setContactDetails(sysUserEntity.getTelephone() == null ? null : sysUserEntity.getTelephone());
        // 查询用户下任职信息
        List<SysUserPostScopeEntity> posts = sysUserService.queryUserBindPost(Long.parseLong(userid));
        if (UtilCollection.isNotEmpty(posts)) {
            // 转换肥城项目字段
            for (SysUserPostScopeEntity post : posts) {
                post.setPositionInfoCode(post.getPostCode());
                post.setPositionInfoName(post.getPostName());
            }
            sysUserEntity.setUserPosts(posts);
        }
        Map<String, Object> result = UtilDTO.toDTO(sysUserEntity, null, getDtoCallbackHandler());
        return result;
    }

    /**
     * 根据身份证号查询用户
     *
     * @param identifyId
     * @return
     */
    @PostMapping(value = "/get/identifyId")
    Map<String, Object> queryByIdentifyId(@PathVariable("source") String source,
                                          @RequestParam(name = "identifyId") String identifyId) {
        if (StringUtils.isBlank(identifyId) || StringUtils.isBlank(source)) {
            return null;
        }

        try {
            // 通过身份证查询用户信息
            HashMap<String, Object> userParams = Maps.newHashMap();
            userParams.put("projectCode", source);
            userParams.put("idCard", PassWordUtil.encrypt(identifyId));
            userParams.put("del", false);
            List<SysUserEntity> sysUserEntities = sysUserService.queryAll(userParams);
            // 返回用户对象
            if (UtilCollection.isNotEmpty(sysUserEntities)) {
                SysUserEntity sysUserEntity = sysUserEntities.get(0);
                sysUserEntity.setHeadUrl(sysUserEntity.getHeadImgUrl());
                sysUserEntity.setContactDetails(sysUserEntity.getFixedTelephone());
                Map<String, Object> result = UtilDTO.toDTO(sysUserEntity, null, getDtoCallbackHandler());
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    @PostMapping(value = "/get/querybyusername")
    ResponseParam queryByUserName(@PathVariable("source") String source,
                                  @RequestParam(name = "userName") String userName) {
        return null;
    }


    /**
     * 查询组织机构下的用户
     *
     * @param source
     * @param orgId
     * @return
     */
    @PostMapping(value = "/list")
    List<Map<String, Object>> queryOrgUsers(@PathVariable("source") String source, @RequestParam(name = "orgId") String orgId) {
        if (StringUtils.isBlank(orgId) || StringUtils.isBlank(source)) {
            return Lists.newArrayList();
        }
        HashMap<String, Object> userParams = Maps.newHashMap();
        userParams.put("projectCode", source);
        userParams.put("orgId", orgId);
        userParams.put("del", false);
        List<SysUserEntity> sysUserEntities = sysUserService.queryAll(userParams,new String[]{"createDatetime_desc"});
        sysUserEntities.forEach(sysUserEntity -> {
            sysUserEntity.setHeadUrl(sysUserEntity.getHeadImgUrl());
            sysUserEntity.setContactDetails(sysUserEntity.getFixedTelephone());
        });
        List<Map<String, Object>> result = UtilDTO.toDTO(sysUserEntities, null, getDtoCallbackHandler());
        return result;
    }


    /**
     * 分页查询组织机构下的用户(返回值不带有分页信息,只需要纯数据集合)
     *
     * @param source
     * @param user   组织ID\组织code\分页数据
     * @return
     * @author lwq
     */
    @PostMapping(value = "/queryPageUserByOrg")
    List<Map<String, Object>> queryPageUserByOrg(@PathVariable("source") String source, @RequestBody Map<String, Object> user) {
        return null;
    }


    /**
     * 查询组织机构下的用户数量
     *
     * @param source
     * @param user:  orgId   hasChild  excludeUserId
     * @return
     */
    @PostMapping(value = "/userCount")
    Integer queryCountOrgUsers(@PathVariable("source") String source, @RequestBody Map<String, Object> user) {
        return null;
    }


    /**
     * 根据姓名和身份证号查询认证用户信息
     *
     * @param realName
     * @param idCard
     * @return
     */
    @PostMapping("/query")
    Map<String, Object> queryByPhoneAndIDCard(@PathVariable("source") String source,
                                              @RequestParam("realName") String realName, @RequestParam("idCard") String idCard) {
        if (StringUtils.isBlank(realName) || StringUtils.isBlank(source) || StringUtils.isBlank(idCard)) {
            return null;
        }
        HashMap<String, Object> userParams = Maps.newHashMap();
        userParams.put("projectCode", source);
        userParams.put("realName", realName);
        try {
            userParams.put("idCard", PassWordUtil.encrypt(idCard));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userParams.put("authStatus", AuthStatus.AUTH);
        userParams.put("del", false);
        List<SysUserEntity> sysUserEntities = sysUserService.queryAll(userParams);
        // 返回用户对象
        if (UtilCollection.isNotEmpty(sysUserEntities)) {
            SysUserEntity sysUserEntity = sysUserEntities.get(0);
            sysUserEntity.setHeadUrl(sysUserEntity.getHeadImgUrl() == null ? null : sysUserEntity.getHeadImgUrl());
            sysUserEntity.setContactDetails(sysUserEntity.getFixedTelephone() == null ? null : sysUserEntity.getFixedTelephone());

            Map<String, Object> result = UtilDTO.toDTO(sysUserEntity, null, getDtoCallbackHandler());
            return result;
        }
        return null;
    }


    /**
     * 批量查询用户
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/query/ids", produces = "application/json")
    List<Map<String, Object>> queryByUserIds(@PathVariable("source") String source, @RequestBody ArrayList<String> ids) {
        if (UtilCollection.sizeIsEmpty(ids)) {
            return Lists.newArrayList();
        }
        HashMap<String, Object> userParams = Maps.newHashMap();
        userParams.put("ids", ids);
        List<SysUserEntity> sysUserEntities = sysUserService.queryAll(userParams);
        sysUserEntities.forEach(sysUserEntity -> {
            sysUserEntity.setHeadUrl(sysUserEntity.getHeadImgUrl());
            sysUserEntity.setContactDetails(sysUserEntity.getFixedTelephone());
        });
        List<Map<String, Object>> result = UtilDTO.toDTO(sysUserEntities, null, getDtoCallbackHandler());
        return result;
    }


    /**
     * 根据组织批量查询本级及直属子级的所有用户
     *
     * @param orgId 组织ID
     * @return
     */
    @RequestMapping(value = "/queryUserByOrd")
    List<Map<String, Object>> queryUserByOrd(@PathVariable("source") String source,
                                             @RequestParam(name = "orgId") String orgId) {
        if (StringUtils.isBlank(source) || StringUtils.isBlank(orgId)) {
            return null;
        }
        // 查询下级组织
        HashMap<String, Object> orgParams = Maps.newHashMap();
        orgParams.put("parentId", orgId);
        orgParams.put("del", false);
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(orgParams);
        List<Long> orgIds = sysOrgEntities.stream().map(map -> map.getId()).collect(Collectors.toList());
        orgIds.add(Long.parseLong(orgId));
        // 查询本级及下级组织的人员
        HashMap<String, Object> userParams = Maps.newHashMap();
        userParams.put("orgIds", orgIds);
        List<SysUserEntity> sysUserEntities = sysUserService.queryAll(userParams);
        sysUserEntities.forEach(sysUserEntity -> {
            sysUserEntity.setHeadUrl(sysUserEntity.getHeadImgUrl());
            sysUserEntity.setContactDetails(sysUserEntity.getFixedTelephone());
        });
        List<Map<String, Object>> result = UtilDTO.toDTO(sysUserEntities, null, getDtoCallbackHandler());
        return result;
    }


    /**
     * 处理用户新增工单
     *
     * @param userDto
     * @return
     */
    @PostMapping(value = "/save")
    ResponseParam save(@PathVariable("source") String source, @RequestBody Map<String, Object> userDto) {
        if (UtilCollection.sizeIsEmpty(userDto) || StringUtils.isBlank(source)) {
            return ResponseParam.fail().message("参数不能为空");
        }
        try {
            // 通过租户编码查询租户id
            Long projectId = sysProjectService.getProjectId(source);
            // 拼装用户基本信息参数
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserEntity.setRealName(userDto.get("realName") == null ? null : (String) userDto.get("realName"));
            sysUserEntity.setTelephone(userDto.get("telephone") == null ? null : (String) userDto.get("telephone"));
            sysUserEntity.setUserName(userDto.get("telephone") == null ? null : (String) userDto.get("telephone"));
            sysUserEntity.setFixedTelephone(userDto.get("telephone") == null ? null : (String) userDto.get("telephone"));
            sysUserEntity.setPolitical(userDto.get("political") == null ? null : (String) userDto.get("political"));
            sysUserEntity.setSource(userDto.get("businesSource") == null ? null : (String) userDto.get("businesSource"));
            sysUserEntity.setOrgId(userDto.get("orgId") == null ? null : Long.parseLong((String) userDto.get("orgId")));
            sysUserEntity.setIdCard(userDto.get("idCard") == null ? null : PassWordUtil.encrypt((String) userDto.get("idCard")));
            sysUserEntity.setHeadImgUrl(userDto.get("headUrl") == null ? null : (String) userDto.get("headUrl"));
            sysUserEntity.setSex(userDto.get("sex") == null ? null : (String) userDto.get("sex"));
            sysUserEntity.setNation(userDto.get("nation") == null ? null : (String) userDto.get("nation"));
            sysUserEntity.setEducation(userDto.get("education") == null ? null : (String) userDto.get("education"));

            // 如果选择政治面貌是党员需要请求灯塔验证
            String political = userDto.get("political") == null ? null : (String) userDto.get("political");
            if ("PARTY".equals(political)) {
                Map<String, Object> checkDtUser = sysUserService.checkDtUser(userDto.get("idCard") == null ? null : (String) userDto.get("idCard"));
                if (checkDtUser != null) {
                    Boolean success = (Boolean) checkDtUser.get("success");
                    if (!success) {
                        return ResponseParam.fail().message("非党员");
                    }
                } else {
                    return ResponseParam.fail().message("非党员");
                }
            }
            if (StringUtils.equals((String) userDto.get("operateType"), "addUser")) {
                // 获取组织信息
                SysOrgEntity orgInfo = sysOrgService.get(sysUserEntity.getOrgId());
                if (orgInfo != null) {
                    sysUserEntity.setOrgName(orgInfo.getOrgName());
                    sysUserEntity.setOrgCode(orgInfo.getOrgCode());
                }
                sysUserEntity.setProjectCode(source);
                sysUserEntity.setProjectId(projectId);
                sysUserEntity.setDel(Boolean.FALSE);
                sysUserEntity.setShelvesType(ShelvesType.role);
                sysUserEntity.setAuthStatus(AuthStatus.NOT_AUTH);
                // 默认密码
                sysUserEntity.setPassword("123456");
                // 检验身份证号唯一
                HashMap<String, Object> idCardParams = Maps.newHashMap();
                idCardParams.put("projectId", projectId);
                idCardParams.put("idCard", PassWordUtil.encrypt((String) userDto.get("idCard")));
                idCardParams.put("del", false);
                boolean idCardExist = sysUserService.isExist(idCardParams);
                if (idCardExist) {
                    return ResponseParam.fail().message(UserConstant.CHECK_ID_CARD);
                }
                // 注册手机号激活状态唯一校验
                HashMap<String, Object> telephoneParams = Maps.newHashMap();
                telephoneParams.put("projectId", projectId);
                telephoneParams.put("telephone", userDto.get("telephone"));
                telephoneParams.put("del", false);
                boolean telephoneStatusExist = sysUserService.isExist(telephoneParams);
                if (telephoneStatusExist) {
                    return ResponseParam.fail().message(UserConstant.CHECK_TELEPHONE);
                }
                SysUserEntity user = sysUserService.save(sysUserEntity);
//                // 添加默认职务:如果组织类型是村居或者社区默认是村民，其他为储备干部
//                SysUserPostScopeEntity userPostScopeEntity = new SysUserPostScopeEntity();
//                userPostScopeEntity.setOrgName(orgInfo.getOrgName());
//                userPostScopeEntity.setOrgCode(orgInfo.getOrgCode());
//                userPostScopeEntity.setOrgId(orgInfo.getId());
//                userPostScopeEntity.setProjectId(projectId);
//                userPostScopeEntity.setUserId(user.getId());
//                if(StringUtils.equals(orgInfo.getLevelType().name(), LevelType.CITY_VILLAGE.name()) ||
//                        StringUtils.equals(orgInfo.getLevelType().name(), LevelType.VILLAGE.name())){
//                    // 通过职务编码获取职务信息
//                    HashMap<String, Object> postParams = Maps.newHashMap();
//                    postParams.put("projectId",projectId);
//                    postParams.put("postCode","ORDINARY_VILLAGER");
//                    List<SysPostEntity> sysPostEntities = sysPostService.queryAll(postParams);
//                    if(UtilCollection.isNotEmpty(sysPostEntities)){
//                        SysPostEntity sysPostEntity = sysPostEntities.get(0);
//                        userPostScopeEntity.setPostId(sysPostEntity.getId());
//                        userPostScopeEntity.setPostCode(sysPostEntity.getPostCode());
//                        userPostScopeEntity.setPostName(sysPostEntity.getPostName());
//                        userPostScopeEntity.setIdentify(sysPostEntity.getIdentify());
//                    }
//                }else {
//                    // 通过职务编码获取职务信息
//                    HashMap<String, Object> postParams = Maps.newHashMap();
//                    postParams.put("projectId",projectId);
//                    postParams.put("postCode","CHUBEIGANBU");
//                    List<SysPostEntity> sysPostEntities = sysPostService.queryAll(postParams);
//                    if(UtilCollection.isNotEmpty(sysPostEntities)){
//                        SysPostEntity sysPostEntity = sysPostEntities.get(0);
//                        userPostScopeEntity.setPostId(sysPostEntity.getId());
//                        userPostScopeEntity.setPostCode(sysPostEntity.getPostCode());
//                        userPostScopeEntity.setPostName(sysPostEntity.getPostName());
//                        userPostScopeEntity.setIdentify(sysPostEntity.getIdentify());
//                    }
//                }
//                // 保存用户岗位信息
//                sysUserPostScopeService.save(userPostScopeEntity);
                // 拼装返回信息
                user.setHeadUrl(user.getHeadImgUrl());
                user.setContactDetails(user.getTelephone());
                Map<String, Object> result = UtilDTO.toDTO(user,
                        Arrays.asList("id", "realName", "telephone", "headUrl", "orgId", "hash", "orgName", "orgCode", "contactDetails"),
                        getDtoCallbackHandler());
                return ResponseParam.success().data(result);
            } else if (StringUtils.equals((String) userDto.get("operateType"), "updateUser")) {
                sysUserEntity.setId(userDto.get("id") == null ? null : Long.parseLong((String) userDto.get("id")));
                if (sysUserEntity.getId() == null) {
                    return ResponseParam.fail().message("用户id不能为空!");
                }
                /**
                 * 描述: 进行组织职务判断
                 * 1、 如果原来这个人的组织类型是CITY_VILLAGE或VILLAGE不修改组织；
                 * 2、 如果原来这个人的组织类型不是CITY_VILLAGE或VILLAGE，并且这次修改的组织类型是CITY_VILLAGE或VILLAGE则修改组织；
                 * 3、 如果修改了组织需要同时把这个人的职务所属修改了并且发mq同步；
                 **/
                // 获取人员信息
                SysUserEntity userInfo = sysUserService.get(sysUserEntity.getId());
                // 获取组织信息
                SysOrgEntity orgInfo = sysOrgService.get(sysUserEntity.getOrgId());
                if (orgInfo != null) {
                    sysUserEntity.setOrgName(orgInfo.getOrgName());
                    sysUserEntity.setOrgCode(orgInfo.getOrgCode());
                }
                // 执行修改操作
                sysUserService.update(sysUserEntity,Arrays.asList("realName", "nation", "headImgUrl", "fixedTelephone", "political", "education", "orgId", "orgName", "orgCode"));
                // 发送人员mq
                JSONObject jsonObject = sysMQProducerService.buildSysUserMessage(userInfo, SysMqUserTypeConstant.updateUser.name());
                sysMQProducerService.sendMQMessage(SysMqUserTypeConstant.updateUser.name(), jsonObject, MQMessageConstant.USER_TAG);
                // 拼装返回信息
                SysUserEntity returnUserInfo = sysUserService.get(sysUserEntity.getId());
                returnUserInfo.setHeadUrl(returnUserInfo.getHeadImgUrl());
                returnUserInfo.setContactDetails(returnUserInfo.getTelephone());
                Map<String, Object> result = UtilDTO.toDTO(returnUserInfo,
                        Arrays.asList("id", "realName", "telephone", "headUrl", "orgId", "hash", "orgName", "orgCode", "contactDetails"),
                        getDtoCallbackHandler());
                return ResponseParam.success().data(result);
//                // 判断新传入的组织所属组织是否跟之前存在的所属组织相等
//                if (!StringUtils.equals(userInfo.getOrgId().toString(), sysUserEntity.getOrgId().toString())) {
//                    // 查询原有组织的类型
//                    SysOrgEntity oldOrgInfo = sysOrgService.get(userInfo.getOrgId());
//                    if (!StringUtils.equals(oldOrgInfo.getLevelType().name(), "CITY_VILLAGE") && !StringUtils.equals(oldOrgInfo.getLevelType().name(), "VILLAGE")) {
//                        // 获取新组织类型
//                        SysOrgEntity newOrgInfo = sysOrgService.get(sysUserEntity.getOrgId());
//                        if (StringUtils.equals(newOrgInfo.getLevelType().name(), "CITY_VILLAGE") || StringUtils.equals(newOrgInfo.getLevelType().name(), "VILLAGE")) {
//                            sysUserEntity.setOrgId(newOrgInfo.getId());
//                            sysUserEntity.setOrgCode(newOrgInfo.getOrgCode());
//                            sysUserEntity.setOrgName(newOrgInfo.getOrgName());
//                            sysUserService.update(sysUserEntity, Arrays.asList("realName", "nation", "headImgUrl", "fixedTelephone", "political", "education", "orgId", "orgName", "orgCode"));
//                            /**
//                             * 判断任职信息
//                             **/
//                            // 1. 先将之前的组织任职信息（所属组织）删除，再新增为任职组织信息
//                            HashMap<String, Object> userPostParams1 = Maps.newHashMap();
//                            userPostParams1.put("orgId",oldOrgInfo.getId());
//                            userPostParams1.put("userId", sysUserEntity.getId());
//                            List<SysUserPostScopeEntity> sysUserPostScopes1 = sysUserPostScopeService.queryAll(userPostParams1, new String[]{"createDatetime:asc"});
//                            for (int i = 0; i < sysUserPostScopes1.size(); i++) {
//                                // 删除所属组织信息(mq)
//                                if (i == sysUserPostScopes1.size()-1) {
//                                    sysMQProducerService.sendUserPostMessage(userInfo, sysUserPostScopes1.get(i), "delComPos", 0);
//                                } else {
//                                    sysMQProducerService.sendUserPostMessage(userInfo, sysUserPostScopes1.get(i), "delPos", 0);
//                                }
//                                // 将所属组织变为任职
//                                if(i == 0){
//                                    sysMQProducerService.sendUserPostMessage(userInfo, sysUserPostScopes1.get(i), "addComPos", 1);
//                                }else {
//                                    sysMQProducerService.sendUserPostMessage(userInfo, sysUserPostScopes1.get(i), "addPost", 1);
//                                }
//                            }
//                            // 2. 查询新组织（所属组织）是否是原任职组织，如果新所属组织是原任职组织则删掉原任职组织，再新增为所属组织，如果不是则不操作
//                            HashMap<String, Object> userPostParams2 = Maps.newHashMap();
//                            userPostParams2.put("orgId",sysUserEntity.getOrgId());
//                            userPostParams2.put("userId",sysUserEntity.getId());
//                            List<SysUserPostScopeEntity> sysUserPostScopes2 = sysUserPostScopeService.queryAll(userPostParams2, new String[]{"createDatetime:asc"});
//                            if(UtilCollection.isNotEmpty(sysUserPostScopes2)){
//                                for (int i = 0; i < sysUserPostScopes2.size(); i++) {
//                                    // 删除之前的任职组织
//                                    if (i == sysUserPostScopes1.size()-1) {
//                                        sysMQProducerService.sendUserPostMessage(userInfo, sysUserPostScopes2.get(i), "delComPos", 1);
//                                    } else {
//                                        sysMQProducerService.sendUserPostMessage(userInfo, sysUserPostScopes2.get(i), "delPos", 1);
//                                    }
//                                    // 将原任职组织新增为所属组织
//                                    if(i == 0){
//                                        sysMQProducerService.sendUserPostMessage(userInfo, sysUserPostScopes2.get(i), "addComPos", 0);
//                                    }else {
//                                        sysMQProducerService.sendUserPostMessage(userInfo, sysUserPostScopes2.get(i), "addPost", 0);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }else {
//                    sysUserService.update(sysUserEntity,Arrays.asList("realName", "nation", "headImgUrl", "fixedTelephone", "political", "education", "orgId", "orgName", "orgCode"));
//                    JSONObject jsonObject = sysMQProducerService.buildSysUserMessage(sysUserEntity, SysMqUserTypeConstant.updateUser.name());
//                    sysMQProducerService.sendMQMessage(SysMqUserTypeConstant.updateUser.name(), jsonObject, MQMessageConstant.USER_TAG);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除用户
     *
     * @param userDto
     * @return
     */
    @PostMapping("/delete")
    ResponseParam delete(@PathVariable("source") String source, @RequestBody Map<String, Object> userDto) {
        if (UtilCollection.sizeIsEmpty(userDto) || StringUtils.isBlank(source)) {
            return ResponseParam.fail().message("参数不能为空");
        }

        try {
            // 查询租户下该身份证存在的用户
            HashMap<String, Object> userParams = Maps.newHashMap();
            userParams.put("idCard", userDto.get("idCard") == null ? null : PassWordUtil.encrypt((String) userDto.get("idCard")));
            List<SysUserEntity> sysUserEntities = sysUserService.queryAll(userParams);
            // 删除用户信息
            sysUserService.delete(sysUserEntities.stream().map(map -> map.getId()).collect(Collectors.toList()));
            return ResponseParam.deleteSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseParam.deleteFail();
    }


    /**
     * 更新用户状态
     *
     * @param idCard
     * @param status
     * @return
     */
    @PostMapping(value = "/update/status")
    ResponseParam updateStatus(@PathVariable("source") String source, @RequestParam("idCard") String idCard,
                               @RequestParam("status") String status) {
        if (StringUtils.isBlank(source) || StringUtils.isBlank(idCard) || StringUtils.isBlank(status)) {
            return ResponseParam.fail().message("参数不能为空");
        }

        try {
            // 查询租户下存在的用户
            HashMap<String, Object> userParams = Maps.newHashMap();
            userParams.put("projectCode", source);
            userParams.put("idCard", PassWordUtil.encrypt(idCard));
            userParams.put("del", false);
            List<SysUserEntity> sysUserEntities = sysUserService.queryAll(userParams);
            // 修改状态
            sysUserEntities.forEach(user -> {
                if (status.equals(UserStatus.INVALID.name())) {
                    user.setStatus(UserStatus.INVALID);
                } else if (status.equals(UserStatus.VALID.name())) {
                    user.setStatus(UserStatus.VALID);
                }
                sysUserService.update(user, Arrays.asList("status"));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseParam.updateSuccess();
    }


    /**
     * 根据组织、角色、人员名称 分页查询
     *
     * @param source
     * @param user
     * @return
     */
    @PostMapping("/page")
    ResponseParam queryPage(@PathVariable("source") String source, @RequestBody Map<String, Object> user) {
        return null;
    }


    /**
     * 根据组织和角色查询上级组织对应角色的用户;用于工作流程节点动态获取节点所属用户
     *
     * @param user 当前组织id，roleIds[] 角色ID
     * @return
     */
    @PostMapping("/org/role")
    ResponseParam queryByOrgAndRole(@PathVariable("source") String source, @RequestBody Map<String, Object> user) {
        return null;
    }


    /**
     * 保存用户信息--全国资源库使用
     *
     * @param cloudUserDto
     * @return
     */
    @PostMapping("/saveResourceUser")
    ResponseParam saveResourceUser(@PathVariable("source") String source, @RequestBody Map<String, Object> cloudUserDto) {
        return null;
    }


    /**
     * 根据灯塔userId查询济南用户
     *
     * @param source
     * @param ids    灯塔党组织下的党员id
     * @return
     */
    @PostMapping("/queryDTUserByOutId")
    ResponseParam queryUserByDTUserId(@PathVariable("source") String source, @RequestBody List<String> ids) {
        if (UtilCollection.sizeIsEmpty(ids)) {
            return ResponseParam.fail().message("ID不能为空！");
        }
        // 查询用户信息
        HashMap<String, Object> userParams = Maps.newHashMap();
        userParams.put("dtUserIds", ids);
        List<SysUserEntity> sysUserEntities = sysUserService.queryAll(userParams);
        // 转换肥城字段
        sysUserEntities.forEach(user -> {
            user.setHeadUrl(user.getHeadImgUrl());
            user.setContactDetails(user.getTelephone());
            user.setOutUserId(user.getDtUserId());
        });
        List<Map<String, Object>> result = UtilDTO.toDTO(sysUserEntities, null, getDtoCallbackHandler());
        return ResponseParam.success().datalist(result);
    }


    /**
     * 查询用户
     *
     * @param source
     * @param sysUserDto 过滤条件
     * @return
     */
    @PostMapping("/queryUser")
    List<Map<String, Object>> queryUser(@PathVariable("source") String source, @RequestBody Map<String, Object> sysUserDto) {
        if (StringUtils.isBlank(source) || UtilCollection.sizeIsEmpty(sysUserDto)) {
            return null;
        }

        try {
            // 查询租户下存在的用户
            HashMap<String, Object> userParams = Maps.newHashMap();
            userParams.put("projectCode", source);
            userParams.put("orgId", sysUserDto.get("orgId") == null ? null : Long.parseLong((String) sysUserDto.get("orgId")));
            userParams.put("idCard", sysUserDto.get("idCard") == null ? null : PassWordUtil.encrypt((String) sysUserDto.get("idCard")));
            userParams.put("telephone", sysUserDto.get("telephone") == null ? null : (String) sysUserDto.get("telephone"));
            userParams.put("realNameOrTelephoneLike", sysUserDto.get("realNameOrTelephoneLike") == null ? null : (String) sysUserDto.get("realNameOrTelephoneLike"));
            userParams.put("realName", sysUserDto.get("realName") == null ? null : (String) sysUserDto.get("realName"));
            userParams.put("party", sysUserDto.get("party") == null ? null : (Boolean) sysUserDto.get("party"));
            List<SysUserEntity> sysUserEntities = sysUserService.queryAllUserInfo(userParams);
            // 转换肥城字段
            sysUserEntities.forEach(user -> {
                user.setHeadUrl(user.getHeadImgUrl());
                user.setContactDetails(user.getTelephone());
            });
            List<Map<String, Object>> result = UtilDTO.toDTO(sysUserEntities, null, getDtoCallbackHandler());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 添加任职
     *
     * @param source
     * @param userPostDto
     * @return
     */
    @PostMapping(value = "/savePost")
    ResponseParam savePost(@PathVariable("source") String source, @RequestBody Map<String, Object> userPostDto) {
        if (StringUtils.isBlank(source) || UtilCollection.sizeIsEmpty(userPostDto)) {
            return ResponseParam.fail().message("参数不能为空");
        }
        try {
            addUserPost(source, userPostDto);
            return ResponseParam.saveSuccess();
        }catch (Exception e){
            return ResponseParam.saveFail().message(e.getMessage());
        }

    }

    /**
     * 描述: 新增用户任职关系
     *
     * @param source
     * @param userPostDto
     * @return void
     * @author fuhao
     * @date 2022/2/17 16:09
     **/
    public void addUserPost(String source, Map<String, Object> userPostDto) throws Exception {

        // 用户id
        Long userId = userPostDto.get("userId") == null ? null : Long.parseLong((String) userPostDto.get("userId"));
        // 获取人员信息
        SysUserEntity sysUserEntity = sysUserService.get(userId);
        if(sysUserEntity==null){
            throw new RuntimeException("用户不存在");
        }
        // 通过组织id查询组织id
        Long orgId = Long.parseLong((String) userPostDto.get("orgId"));
        SysOrgEntity sysOrgEntity = sysOrgService.get(orgId);
        // 判断是否是首次任职
        HashMap<String, Object> userPostMap = Maps.newHashMap();
        userPostMap.put("userId",userId);
        userPostMap.put("orgId",orgId);
        List<SysUserPostScopeEntity> sysUserPostScopeEntities = sysUserPostScopeService.queryAll(userPostMap);
        String type= "";
        if(UtilCollection.sizeIsEmpty(sysUserPostScopeEntities)){
            // 首次任职
            type = "addComPos";
        }else {
            // 非首次任职
            type = "addPost";
        }
        // 通过租户编码查询租户id
        Long projectId = sysProjectService.getProjectId(source);
        String orgName = sysOrgEntity.getOrgName();
        String orgCode = sysOrgEntity.getOrgCode();
        // 通过任职编码获取任职信息
        HashMap<String, Object> userParams = Maps.newHashMap();
        userParams.put("projectId", projectId);
        userParams.put("postCode", (String) userPostDto.get("post"));
        userParams.put("del", false);
        List<SysPostEntity> sysPostEntities = sysPostService.queryAll(userParams);
        Long postId = null;
        String postCode = null;
        String postName = null;
        String identify = null;
        if (UtilCollection.isNotEmpty(sysPostEntities)) {
            SysPostEntity sysPostEntity = sysPostEntities.get(0);
            postId = sysPostEntity.getId();
            postCode = sysPostEntity.getPostCode();
            postName = sysPostEntity.getPostName();
            identify = sysPostEntity.getIdentify();
        }
        // 灯塔党组织
        String dtOrgId = userPostDto.get("outOrgId") == null ? null : (String) userPostDto.get("outOrgId");
        SysUserPostScopeEntity sysUserPostScopeEntity = new SysUserPostScopeEntity(userId, orgId, orgCode, orgName, postId, postCode, postName, projectId, identify, dtOrgId);
        SysUserPostScopeEntity userPost = sysUserPostScopeService.save(sysUserPostScopeEntity);
        // 发送mq消息

        sysMQProducerService.sendUserPostMessage(sysUserEntity,userPost,type);
    }


    /**
     * 删除任职
     *
     * @param source
     * @param userPostDto
     * @return
     */
    @PostMapping("/deletePost")
    ResponseParam deletePost(@PathVariable("source") String source, @RequestBody Map<String, Object> userPostDto) {
        if (StringUtils.isBlank(source) || UtilCollection.sizeIsEmpty(userPostDto)) {
            return ResponseParam.fail().message("参数不能为空");
        }
        try {
            delUserPost(source, userPostDto);
            return ResponseParam.deleteSuccess();
        }catch (Exception e){
            return ResponseParam.deleteFail().message(e.getMessage());
        }

    }

    /**
     * 描述: 删除用户任职关系
     *
     * @param source
     * @param userPostDto
     * @return void
     * @author fuhao
     * @date 2022/2/17 16:09
     **/
    public void delUserPost(String source, Map<String, Object> userPostDto) throws Exception{
        // 通过租户编码查询租户id
        Long projectId = sysProjectService.getProjectId(source);
        // 拼装查询参数
        HashMap<String, Object> userPostParams = Maps.newHashMap();
        userPostParams.put("projectId", projectId);
        userPostParams.put("userId", userPostDto.get("userId"));
        userPostParams.put("orgId", userPostDto.get("orgId"));
        userPostParams.put("postCode", userPostDto.get("post"));
        List<SysUserPostScopeEntity> sysUserPostScopeEntities = sysUserPostScopeService.queryAll(userPostParams);
        if(UtilCollection.sizeIsEmpty(sysUserPostScopeEntities)){
            return;
        }
        // 获取人员信息
        SysUserEntity sysUserEntity = sysUserService.get(sysUserPostScopeEntities.get(0).getUserId());
        if(sysUserEntity==null){
            throw new RuntimeException("用户不存在");
        }
        sysUserPostScopeService.delete(sysUserPostScopeEntities.stream().map(map -> map.getId()).collect(Collectors.toList()));
        // 判断是否是最后一次删除并发送mq信息
        HashMap<String, Object> userPostMap = Maps.newHashMap();
        userPostMap.put("userId",userPostDto.get("userId"));
        userPostMap.put("orgId",userPostDto.get("orgId"));
        List<SysUserPostScopeEntity> userPost = sysUserPostScopeService.queryAll(userPostMap);

        String type = "";
        if(UtilCollection.sizeIsEmpty(userPost)){
            type = "delComPos";
        }else {
            type = "delPos";
        }
        sysMQProducerService.sendUserPostMessage(sysUserEntity,sysUserPostScopeEntities.get(0),type);
    }


    /**
     * 批量操作职务
     *
     * @param source
     * @param userPostDto
     * @return
     */
    @PostMapping(value = "/batchOperatePost")
    ResponseParam batchOperatePost(@PathVariable("source") String source, @RequestBody Map<String, Object> userPostDto) {
        if (StringUtils.isBlank(source) || UtilCollection.sizeIsEmpty(userPostDto)) {
            return ResponseParam.fail().message("参数不能为空");
        }
        // 新增数据集合
        try {
            List<Map<String, Object>> addPost = userPostDto.get("addPost") == null ? Lists.newArrayList() : (List<Map<String, Object>>) userPostDto.get("addPost");
            if (UtilCollection.isNotEmpty(addPost)) {
                for (Map<String, Object> post : addPost) {
                    addUserPost(source, post);
                }
            }
            // 删除数据集合
            List<Map<String, Object>> deletePost = userPostDto.get("deletePost") == null ? Lists.newArrayList() : (List<Map<String, Object>>) userPostDto.get("deletePost");
            if (UtilCollection.isNotEmpty(deletePost)) {
                for (Map<String, Object> post : deletePost) {
                    delUserPost(source, post);
                }
            }
            return ResponseParam.updateSuccess();
        }catch (Exception e){
            return ResponseParam.updateFail().message(e.getMessage());
        }
    }


    /**
     * 根据组织和职务分页查询人员
     *
     * @param source
     * @param sysUserDto
     * @return
     */
    @PostMapping("/queryByOrgAndPost")
    ResponseParam queryByOrgAndPost(@PathVariable("source") String source, @RequestBody Map<String, Object> sysUserDto) {
        if (StringUtils.isBlank(source) || UtilCollection.sizeIsEmpty(sysUserDto)) {
            return ResponseParam.fail().message("参数不能为空");
        }
        // 根据组织和职务分页查询人员
        Page<SysUserEntity> sysUser = sysUserService.queryUserByOrgAndPost(sysUserDto, MybatisPageRequest.of((Integer) sysUserDto.get("pageNum"), (Integer) sysUserDto.get("pageSize")));
        List<SysUserEntity> sysUserEntities = sysUser.getContent();
        // 转换肥城项目字段
        sysUserEntities.forEach(sysUserEntity -> {
            sysUserEntity.setHeadUrl(sysUserEntity.getHeadImgUrl());
            sysUserEntity.setContactDetails(sysUserEntity.getFixedTelephone());
        });
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysUserList = UtilDTO.toDTO(sysUserEntities,
                null, getDtoCallbackHandler());

        return ResponseParam.success()
                .pageParam(sysUser)
                .datalist(sysUserList);
    }


    /**
     * 分页查询用户
     *
     * @param source
     * @param sysUserDto 过滤条件
     * @return
     */
    @PostMapping("/queryPageUser")
    ResponseParam queryPageUser(@PathVariable("source") String source, @RequestBody Map<String, Object> sysUserDto) {
        if (StringUtils.isBlank(source) || UtilCollection.sizeIsEmpty(sysUserDto)) {
            return ResponseParam.fail().message("参数不能为空");
        }
        // 分页查询用户
        Page<SysUserEntity> sysUser = sysUserService.queryUserByOrgAndPost(sysUserDto, MybatisPageRequest.of((Integer) sysUserDto.get("pageNum"), (Integer) sysUserDto.get("pageSize")));
        List<SysUserEntity> sysUserEntities = sysUser.getContent();
        // 转换肥城项目字段
        sysUserEntities.forEach(sysUserEntity -> {
            sysUserEntity.setHeadUrl(sysUserEntity.getHeadImgUrl());
            sysUserEntity.setContactDetails(sysUserEntity.getFixedTelephone());
        });
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysUserList = UtilDTO.toDTO(sysUserEntities,
                null, getDtoCallbackHandler());

        return ResponseParam.success()
                .pageParam(sysUser)
                .datalist(sysUserList);
    }


    /**
     * 分页查询组织下任职和所属的人员
     *
     * @param source
     * @param sysUserDto
     * @return
     */
    @PostMapping("/queryPageUserByOrgAndPost")
    ResponseParam queryPageUserByOrgAndPost(@PathVariable("source") String source, @RequestBody Map<String, Object> sysUserDto) {
        if (StringUtils.isBlank(source) || UtilCollection.sizeIsEmpty(sysUserDto)) {
            return ResponseParam.fail().message("参数不能为空");
        }
        // 分页查询组织下任职和所属的人员
        Page<SysUserEntity> sysUser = sysUserService.queryUserByOrgAndPost(sysUserDto, MybatisPageRequest.of((Integer) sysUserDto.get("pageNum"), (Integer) sysUserDto.get("pageSize")));
        List<SysUserEntity> sysUserEntities = sysUser.getContent();
        // 转换肥城项目字段
        sysUserEntities.forEach(sysUserEntity -> {
            sysUserEntity.setHeadUrl(sysUserEntity.getHeadImgUrl());
            sysUserEntity.setContactDetails(sysUserEntity.getFixedTelephone());
        });
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysUserList = UtilDTO.toDTO(sysUserEntities,
                null, getDtoCallbackHandler());

        return ResponseParam.success()
                .pageParam(sysUser)
                .datalist(sysUserList);
    }


    /**
     * 查询组织下的任职人员
     *
     * @param source
     * @param orgIds
     * @param postCode 可为空
     * @return
     */
    @PostMapping("/queryUserByPost")
    List<Map<String, Object>> queryUserByPost(@PathVariable("source") String source,
                                              @RequestParam("orgIds") Long[] orgIds,
                                              @RequestParam("postCode") String postCode) {
        if (StringUtils.isBlank(source) || orgIds == null || orgIds.length == 0 || StringUtils.isBlank(postCode)) {
            return null;
        }
        // 查询组织下的任职人员
        HashMap<String, Object> searchParams = Maps.newHashMap();
        searchParams.put("orgIds", Arrays.asList(orgIds));
        searchParams.put("postCode", postCode);
        List<SysUserEntity> sysUserEntities = sysUserService.queryUserByPost(searchParams);
        // 转换肥城项目字段
        sysUserEntities.forEach(sysUserEntity -> {
            sysUserEntity.setHeadUrl(sysUserEntity.getHeadImgUrl());
            sysUserEntity.setContactDetails(sysUserEntity.getFixedTelephone());
        });
        List<Map<String, Object>> result = UtilDTO.toDTO(sysUserEntities,
                null, getDtoCallbackHandler());
        return result;
    }


    /**
     * 根据用户id查询用户所有基本信息（用户基本信息，角色信息，资源信息，岗位信息，组织信息）
     *
     * @param userId
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/4/23 9:27
     */
    @PostMapping("/getuserdetails")
    ResponseParam getUserDetailByUserId(@PathVariable("source") String source, @RequestParam("userId") Long userId) {
        return null;
    }


    /**
     * 根据用户id查询用户有的系统app
     *
     * @param source
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/4/27 14:40
     */
    @PostMapping("/getplateformappsbyuserid")
    ResponseParam getPlateFormAppsByUserId(@PathVariable("source") String source, @RequestParam("userId") Long userId) {
        return null;
    }


    /**
     * 查询app下所有用户
     *
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/4/27 16:46
     */
    @PostMapping("getusersbyappid")
    ResponseParam getUsersByAppId(@PathVariable("source") String source, @RequestParam("appId") Long appId) {
        return null;
    }


    /**
     * 根据用户名 应用编码获取资源
     *
     * @param source
     * @param appCode
     * @param userId
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/4/30 9:27
     */
    @PostMapping("queryResourceByAppCodeAndUserId")
    ResponseParam queryResourceByAppCodeAndUserId(@PathVariable("source") String source, @RequestParam("appCode") String appCode, @RequestParam("userId") String userId) {
        return null;
    }


    /**
     * 根据用户名角色 应用编码获取资源
     *
     * @param source
     * @param appCode
     * @param userId
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/4/30 9:27
     */
    @PostMapping("queryResourceByAppCodeAndUserIdAndRole")
    ResponseParam queryResourceByAppCodeAndUserIdAndRole(@PathVariable("source") String source, @RequestParam("appCode") String appCode, @RequestParam("userId") String userId, @RequestParam("roleId") String roleId) {
        return null;
    }


    /**
     * 根据组织查询本级及下级任职相关信息
     *
     * @param orgCode
     * @return
     */
    @PostMapping("/queryPostByOrg")
    List<Map<String, Object>> queryPostByOrg(@PathVariable("source") String source,
                                             @RequestParam("orgCode") String orgCode,
                                             @RequestParam(value = "levelTypes", required = false) String levelTypes) {
        if (StringUtils.isBlank(source) || StringUtils.isBlank(orgCode)) {
            return null;
        }
        List<String> levelTypeList = Lists.newArrayList();
        if (StringUtils.isBlank(levelTypes)) {
            String levelType = "CITY_VILLAGE,VILLAGE";
            String[] split = levelType.split(",");
            levelTypeList = Arrays.asList(split);
        } else {
            String[] split = levelTypes.split(",");
            levelTypeList = Arrays.asList(split);
        }
        // 查询所有子节点
        HashMap<String, Object> orgParams = Maps.newHashMap();
        orgParams.put("orgCode", orgCode);
        orgParams.put("levelTypes", levelTypeList);
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(orgParams);
        // 通过所有组织节点id查询存在的人员
        List<Long> orgIds = sysOrgEntities.stream().map(map -> map.getId()).collect(Collectors.toList());
        HashMap<String, Object> userParams = Maps.newHashMap();
        userParams.put("orgIds", orgIds);
        userParams.put("del", false);
        List<SysUserEntity> sysUserEntities = sysUserService.queryAll(userParams);
        // 通过userId查询用户任职关系
        List<Long> userIds = sysUserEntities.stream().map(map -> map.getId()).collect(Collectors.toList());
        HashMap<String, Object> userPostParams = Maps.newHashMap();
        userPostParams.put("userIds", userIds);
        List<SysPostEntity> sysPostEntities = sysUserPostScopeService.queryPostByUserIds(userParams);
        List<Map<String, Object>> result = UtilDTO.toDTO(sysPostEntities,
                null, getDtoCallbackHandler());
        return result;
    }

    /**
     * 获取PO到DTO转换的接口。主要用于在前端展示数据之前首先将数据格式处理完成。
     *
     * @return
     */
    public DTOCallbackHandler getDtoCallbackHandler() {

        //创建转换接口类
        DTOCallbackHandler dtoCallbackHandler = new DTOCallbackHandler() {
            @Override
            public void doHandler(Map<String, Object> dtoMap, Class<?> itemClass) {

            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler, true);
    }

}

