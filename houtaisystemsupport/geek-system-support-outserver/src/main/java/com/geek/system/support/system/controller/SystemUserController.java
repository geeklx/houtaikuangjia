package com.geek.system.support.system.controller;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.id.snowflake.AppIDGenerator;
import com.fosung.framework.common.json.JsonMapper;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.common.util.UtilTree;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.framework.web.mvc.config.session.common.AppUserInfo;
import com.geek.system.support.system.dict.AuthStatus;
import com.geek.system.support.system.dict.RedisConstant;
import com.geek.system.support.system.dict.ShelvesType;
import com.geek.system.support.system.dict.UserStatus;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.entity.sys.ReturnTreeData;
import com.geek.system.support.system.entity.sys.SysUserEntity;
import com.geek.system.support.system.service.pbs.PbsUserService;
import com.geek.system.support.system.service.sys.SysOrgService;
import com.geek.system.support.system.service.sys.SysProjectService;
import com.geek.system.support.system.service.sys.SysResourceService;
import com.geek.system.support.system.service.sys.SysUserService;
import com.geek.system.support.system.util.*;
import com.geek.system.support.system.vo.SysResourceMenuVo;
import com.geek.system.support.system.vo.SysUserDetailVo;
import com.geek.system.support.system.util.PassWordUtil;
import com.geek.system.support.system.util.UserConstant;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping(value = "/api/system/user")
public class SystemUserController extends AppIBaseController {




    @Autowired
    private PbsUserService pbsUserService;

    @Autowired
    private SysUserService sysUserService ;

    @Autowired
    private SysOrgService sysOrgService;

    @Value("${app.globalParams.headImgUrl:http://119.188.115.252:8090/resource-handle/uploads/image/2022-02-14/3532192544572062147.png}")
    private String headImgUrl;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SysProjectService sysProjectService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 根据用户id查询用户信息
     *
     * @param userid
     * @return
     */
    @PostMapping("query/by/userid")
    public ResponseParam queryByUserId(@RequestParam("userid") String userid) {
        String redisKey = RedisConstant.SYSTEM_USER_USERID_INFO+userid;
        SysUserDetailVo sysUserDetailVo = new SysUserDetailVo();
        String sysUserStr = stringRedisTemplate.opsForValue().get(redisKey);
        if (UtilString.isBlank(sysUserStr)){
            Map<String,Object> searchParam = Maps.newHashMap();
            searchParam.put("id",NumberUtil.parseLong(userid));
            sysUserDetailVo = sysUserService.getAuthUserDetails(searchParam);
            if (sysUserDetailVo != null) {
                stringRedisTemplate.opsForValue().set(redisKey,JsonMapper.toJSONString(sysUserDetailVo),RedisConstant.EXPIRES_HOURS,TimeUnit.HOURS);
            }
        }else {
            sysUserDetailVo =JsonMapper.parseObject(sysUserStr,SysUserDetailVo.class);
        }
        Map<String,Object> map = UtilDTO.toDTO(sysUserDetailVo,null,getDtoCallbackHandler());
        return ResponseParam.success().data(map);
    }

    /**
     * 根据身份证号查询用户
     *
     * @param identifyId
     * @return
     */
    @PostMapping("query/by/identifyid")
    public ResponseParam queryByIdentifyId(@RequestParam("identifyId")String identifyId,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode)throws Exception {
        SysUserDetailVo sysUserDetailVo = new SysUserDetailVo();
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("idCard", PassWordUtil.encrypt(identifyId));
        if(UtilString.isNotBlank(projectCode)){
            searchParam.put("projectCode",projectCode);
        }
        sysUserDetailVo = sysUserService.getAuthUserDetails(searchParam);

        Map<String,Object> result = UtilDTO.toDTO(sysUserDetailVo,null,getDtoCallbackHandler());
        return ResponseParam.success().data(result);
    }

    /**
     * 根据身份证号查询用户
     *
     * @param
     * @return
     */
    @PostMapping("query/by/hash")
    public ResponseParam queryByHash(@RequestParam("hash")String hash,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode) {
        SysUserDetailVo sysUserDetailVo = new SysUserDetailVo();
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("hash",hash);
        if(UtilString.isNotBlank(projectCode)){
            searchParam.put("projectCode",projectCode);
        }
        sysUserDetailVo = sysUserService.getAuthUserDetails(searchParam);

        Map<String,Object> result = UtilDTO.toDTO(sysUserDetailVo,null,getDtoCallbackHandler());
        return ResponseParam.success().data(result);
    }

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    @PostMapping("query/by/username")
    public ResponseParam queryByUserName( @RequestParam(value = "userName") String userName,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode) {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("userName",userName);
        if(UtilString.equals("admin",userName)){
            projectCode=null;
        }
        if(UtilString.isNotBlank(projectCode)){
            searchParam.put("projectCode",projectCode);
        }
        SysUserDetailVo sysUserDetailVo = sysUserService.getAuthUserDetails(searchParam);


        Map<String,Object> result = UtilDTO.toDTO(sysUserDetailVo,null,getDtoCallbackHandler());
        return ResponseParam.success().data(result);
    }

    /**
     *根据手机号获用户取详细信息
     *
     * @param telephone
     * @author liuke
     * @date 2021/12/8 14:11
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("query/by/telephone")
    public ResponseParam queryByTelephone( @RequestParam("telephone") String telephone,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode) {
        SysUserDetailVo sysUserDetailVo = new SysUserDetailVo();
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("telephone",telephone);
        if(UtilString.isNotBlank(projectCode)){
            searchParam.put("projectCode",projectCode);
        }
        sysUserDetailVo = sysUserService.getAuthUserDetails(searchParam);

        Map<String,Object> result = UtilDTO.toDTO(sysUserDetailVo,null,getDtoCallbackHandler());
        return ResponseParam.success().data(result);
    }

    /**
     *根据手机号获用户取详细信息
     *
     * @param wxcode
     * @author liuke
     * @date 2021/12/8 14:11
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("query/by/wxcode")
    public ResponseParam queryByWxCode( @RequestParam("wxcode") String wxcode,@RequestParam(value = "projectCode",required = false,defaultValue = "") String projectCode) {
        SysUserDetailVo sysUserDetailVo = new SysUserDetailVo();
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("wxOpenId",wxcode);
        if(UtilString.isNotBlank(projectCode)){
            searchParam.put("projectCode",projectCode);
        }
        sysUserDetailVo = sysUserService.getAuthUserDetails(searchParam);

        Map<String,Object> result = UtilDTO.toDTO(sysUserDetailVo,null,getDtoCallbackHandler());
        return ResponseParam.success().data(result);
    }

    /**
     * 查询组织机构下的用户
     *
     * @param orgId
     * @return
     */
    @PostMapping("query/org/users")
    public ResponseParam queryOrgUsers(@RequestParam("orgId")String orgId) {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("orgId",orgId);
        List<SysUserEntity> sysUserEntitys = sysUserService.queryAll(searchParam,new String[]{"createDatetime_desc"});
        List<Map<String,Object>> list = UtilDTO.toDTO(sysUserEntitys,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(list);
    }

    /**
     * 根据姓名和身份证号查询认证用户信息
     *
     * @param realName
     * @param idCard
     * @return
     */
    @RequestMapping("query/by/phone/and/idcard")
    public ResponseParam queryByPhoneAndIDCard(@RequestParam("realName") String realName,@RequestParam("idCard") String idCard) {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("realName",realName);
        searchParam.put("idCard",idCard);
        List<SysUserEntity> sysUserEntities = sysUserService.queryAll(searchParam);
        if(UtilCollection.sizeIsEmpty(sysUserEntities)){
            return null;
        }
        return ResponseParam.success().data(UtilDTO.toDTO(sysUserEntities.get(0),null,getDtoCallbackHandler()));
    }

    /**
     * 根据组织批量查询本级及直属子级的所有用户
     *
     * @param orgId  组织ID
     * @return
     */
    @RequestMapping("query/user/by/ord")
    public ResponseParam queryUserByOrd(@RequestParam("orgId")String orgId) {
        List<ReturnTreeData> returnTreeDataList = sysUserService.getCuurentUserByOrgId(Long.valueOf(orgId));
        List<Map<String,Object>> list = UtilDTO.toDTO(returnTreeDataList,null,getDtoCallbackHandler());
        return ResponseParam
                .success().datalist(list);
    }


//    /**
//     * 分页查询组织下任职和所属的人员
//     * @param user
//     * @return
//     */
//    @Override
//    public ResponseParam queryPageUserByOrg( @RequestBody SysUserDto user) {
//        Map<String, Object> params = Maps.newHashMap();
//        params.put("orgId", user.getOrgId());
//        Page<SysUserEntity> sysUserPage = sysUserService.queryByPage(params , user.getPageNum() , user.getPageSize());
//        List<Map<String, Object>> sysUserList = UtilDTO.toDTO(sysUserPage.getContent(),
//                null , getDtoCallbackHandler()) ;
//        return ResponseParam.success()
//                .pageParam( sysUserPage )
//                .datalist( sysUserList ) ;
//    }

    /**
     * 批量查询用户
     *
     * @param ids
     * @return
     */
    @RequestMapping("query/by/userids")
    public ResponseParam queryByUserIds(@RequestBody ArrayList<String> ids) {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("ids",ids);
        List<SysUserEntity> sysUserEntities=sysUserService.queryAll(searchParam);
        List<Map<String,Object>> list = UtilDTO.toDTO(sysUserEntities,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(list);
    }

    /**
     * 查询组织机构下的用户数量
     *
     * @return
     */
    @RequestMapping("query/count/org/users")
    public ResponseParam queryCountOrgUsers(@RequestParam("orgId") String orgId) {
         return ResponseParam.success().data(sysUserService.getSysUserCountByOrgId(Long.valueOf(orgId)));
    }


    /**
     *根据用户id查询用户所有基本信息（用户基本信息，角色信息，资源信息，岗位信息，组织信息）
     *
     * @param userId
     * @author liuke
     * @date 2021/4/23 9:27
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("get/userdetail/by/userid")
    public ResponseParam getUserDetailByUserId(@RequestParam Long userId) {
        SysUserDetailVo sysUserDetailVo=sysUserService.getUserDetails(userId);
        Map<String,Object> result = UtilDTO.toDTO(sysUserDetailVo,null,getDtoCallbackHandler());
        return ResponseParam.success().data(result);
    }



    /**
     *根据用户名 应用角色编码获取资源
     *

     * @param userId
     * @author liuke
     * @date 2021/4/30 9:27
     * @return com.fosung.framework.web.http.ResponseParam
     */

    @RequestMapping("query/resource/by/userid/and/role")
    public ResponseParam queryResourceByAppCodeAndUserIdAndRole(  @RequestParam String appCode,@RequestParam Long userId,@RequestParam String roleId) {
        Map<String,Object> searchParams = Maps.newHashMap();
        searchParams.put("appCode",appCode);
        if(UtilString.isBlank(roleId)){
            searchParams.put("roleId",null);
        }else {
            searchParams.put("roleId",Long.valueOf(roleId));
        }

        searchParams.put("userId",userId);
        List<SysResourceMenuVo> sysResourceMenuVos = sysResourceService.selectByUserIdAndAppCode(searchParams);
        List<Map<String,Object>> resultLists = UtilDTO.toDTO(sysResourceMenuVos,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(resultLists);
    }

    /**
     *根据应用编码与角色id查询资源
     *
     * @param appCode
     * @param roleId
     * @author liuke
     * @date 2022/6/28 14:05
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("query/resource/by/role")
    public ResponseParam queryResourceByAppCodeAndRole(  @RequestParam String appCode,@RequestParam String roleId) {
        Map<String,Object> searchParams = Maps.newHashMap();
        searchParams.put("appCode",appCode);
        if(UtilString.isBlank(roleId)){
            searchParams.put("roleId",null);
        }else {
            searchParams.put("roleId",Long.valueOf(roleId));
        }

        List<SysResourceMenuVo> sysResourceMenuVos = sysResourceService.selectByAppCodeAndRole(searchParams);
        List<Map<String,Object>> resultLists = UtilDTO.toDTO(sysResourceMenuVos,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(resultLists);
    }

    /**
     *根据应用编码与角色id查询app
     *
     * @param appCode
     * @param roleId
     * @author liuke
     * @date 2022/6/28 14:05
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("query/app/by/role")
    public ResponseParam queryAppByAppCodeAndRole(  @RequestParam String appCode,@RequestParam String roleId) {
        Map<String,Object> searchParams = Maps.newHashMap();
        searchParams.put("appCode",appCode);
        searchParams.put("roleId",Long.valueOf(roleId));

        List<Long> resultLists = sysResourceService.selectAppByAppCodeAndRole(searchParams);
        return ResponseParam.success().datalist(resultLists);
    }

    /**
     *根据用户名 应用编码获取资源
     *
     * @param appCode
     * @param userId
     * @author liuke
     * @date 2021/4/30 9:27
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/query/resource/by/appcode/and/userid")
    public ResponseParam queryResourceByAppCodeAndUserId(@RequestParam String appCode,@RequestParam Long userId) {
        Map<String,Object> searchParams = Maps.newHashMap();
        searchParams.put("appCode",appCode);
        searchParams.put("userId",userId);
        List<SysResourceMenuVo> sysResourceMenuVos = sysResourceService.selectByUserIdAndAppCode(searchParams);
        List<Map<String,Object>> resultLists = UtilDTO.toDTO(sysResourceMenuVos,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(resultLists);
    }

    @PostMapping("/all/user")
    public ResponseParam queryUser(){

        List<ReturnTreeData> returnTreeData = sysUserService.queryUser(Maps.newHashMap());
        List<Map<String,Object>> result = UtilDTO.toDTO(returnTreeData,getDtoCallbackHandler());
        return ResponseParam.success().datalist(result);
    }

    /**
     *更新用户信息
     *
     * @param json
     * @author liuke
     * @date 2021/12/9 13:41
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("saveuser")
    public ResponseParam saveUserById( @RequestBody JSONObject json) {
        SysUserEntity user = JSON.toJavaObject(json,SysUserEntity.class);
        if(user.getId()==null){
            return ResponseParam.fail().message("用户id不能为空");
        }
        Map<String,Object> map = UtilDTO.toDTO(user,null,getDtoCallbackHandler());
        List<String> cloums = Lists.newArrayList();
        for (String s : map.keySet()) {
            if(map.get(s)!=null){
                cloums.add(s);
            }
        }
        sysUserService.update(user,cloums);

        return ResponseParam.success().message("保存成功");
    }

    /**
     *查询通讯录接口
     *
     * @param orgId
     * @author liuke
     * @date 2021/12/6 14:23
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/maillist")
    public ResponseParam queryMailListByOrgId(@RequestParam Long orgId ){
        List<ReturnTreeData> returnTreeData = sysOrgService.queryOrgAndUserTree(orgId);
        List<Map<String,Object>> result = UtilDTO.toDTO(returnTreeData,getDtoCallbackHandler());
        result = UtilTree.getTreeData(result,"id","parentId","children",false);
        return ResponseParam.success().datalist(result);
    }

    /**
     *懒加载查询通讯录
     *
     * @param orgId
     * @param projectCode
     * @param orgType
     * @author liuke
     * @date 2022/1/14 10:08
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/allmaillazylist")
    ResponseParam queryAllMailLazy(@RequestParam(value = "orgId",required = false,defaultValue = "") String orgId,@RequestParam("projectCode") String projectCode,@RequestParam("orgType") String orgType){
        Long projectId = sysProjectService.getProjectId(projectCode);
        List<ReturnTreeData> list = sysOrgService.queryOrgAndUserTreeLazy(orgId,projectId,orgType);
        return ResponseParam.success().datalist(list);
    }

    /**
     *查询全部通讯录接口
     *redis缓存为10分钟，修改后数据有10分钟缓存
     * @param map
     * @author liuke
     * @date 2021/12/29 9:45
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/allmaillist")
    public ResponseParam queryAllMailListByOrgId(@RequestBody Map<String, Object> map ){
        String orgType = map.get("orgType").toString();
        Long projectId = map.get("projectId")==null?sysProjectService.getProjectId(map.get("projectCode").toString()):Long.valueOf(map.get("projectId").toString());
        String redisKey = RedisConstant.PROJECT_ALL_MAIL+projectId+":"+orgType;
        String returnTreeDataStr = stringRedisTemplate.opsForValue().get(redisKey);
        List<ReturnTreeData> returnTreeData = Lists.newArrayList();
        if(UtilString.isBlank(returnTreeDataStr)){
            returnTreeData = sysOrgService.queryAllOrgAndUserTree(projectId,orgType);
            if (returnTreeData != null) {
                stringRedisTemplate.opsForValue().set(redisKey, JsonMapper.toJSONString(returnTreeData),RedisConstant.EXPIRES_MINUTES, TimeUnit.MINUTES);

            }
        }else {
            returnTreeData = JsonMapper.parseArray(returnTreeDataStr,ReturnTreeData.class);
        }
        List<Map<String,Object>> result = UtilDTO.toDTO(returnTreeData,getDtoCallbackHandler());
        result = UtilTree.getTreeData(result,"id","parentId","children",false);
        return ResponseParam.success().datalist(result);
    }
    /**
     *获取会话共享用户信息
     *
     * @param searchParam
     * @author liuke
     * @date 2021/12/14 16:28
     * @return com.fosung.framework.web.mvc.config.session.common.AppUserInfo
     */
    @PostMapping("queryuserinfo")
    public AppUserInfo getUserInfo(@RequestBody Map<String,String> searchParam){
        AppUserInfo userInfo = new AppUserInfo();
        String redisKey = RedisConstant.SYSTEM_USER_INFO+searchParam.get("idcardHash");
        String userInfoStr = stringRedisTemplate.opsForValue().get(redisKey);
        if(UtilString.isBlank(userInfoStr)){
            userInfo = sysUserService.getUserInfo(searchParam.get("idcardHash"),searchParam.get("clientId"));
            if (userInfo != null) {
                stringRedisTemplate.opsForValue().set(redisKey,JsonMapper.toJSONString(userInfo),RedisConstant.EXPIRES_HOURS,TimeUnit.HOURS);

            }
        }else {
            userInfo = JsonMapper.parseObject(userInfoStr,AppUserInfo.class);
        }
        return userInfo;
    }

    @PostMapping("queryuser/byrole")
    ResponseParam queryUserByRoleCode(@RequestParam("roleCode") String roleCode){
        Map<String, Object> searchParam = Maps.newHashMap();
        searchParam.put("roleCode",roleCode);
        List<SysUserEntity> sysUserEntities = sysUserService.queryUserByRoleCode(searchParam);
        List<Map<String, Object>> lists = UtilDTO.toDTO(sysUserEntities,getDtoCallbackHandler());
        return  ResponseParam.success().datalist(lists);
    }

    /**
     *
     * 根据角色批量查询人员
     * @param roleCodes
     * @author liuke
     * @date 2022/6/22 14:08
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("queryuser/byroles/{project}")
    ResponseParam queryUserByRoleCodes(@PathVariable("project") String project,@RequestParam("roleCodes") String[] roleCodes){
        Map<String, Object> searchParam = Maps.newHashMap();
        Long projectId= sysProjectService.getProjectId(project);
        searchParam.put("projectId",Lists.newArrayList(projectId));
        searchParam.put("roleCodes",Lists.newArrayList(roleCodes));
        List<SysUserEntity> sysUserEntities = sysUserService.queryUserByRoleCode(searchParam);
        List<Map<String, Object>> lists = UtilDTO.toDTO(sysUserEntities,getDtoCallbackHandler());
        return  ResponseParam.success().datalist(lists);
    }

    /**00000000000000000000000000000000000000000000000000000000000000000000000
     *
     *
     *
     *
     * 描述: 通过租户id查询租户下全部的用户
     * @param projectCode
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserEntity>
     * @author fuhao
     * @date 2022/3/3 10:16
     **/
    @PostMapping("queryall/byproject")
    public List<Map<String, Object>> queryAllByProjectId(@RequestParam("projectCode") String projectCode) {
        HashMap<String, Object> searchParams = Maps.newHashMap();
        searchParams.put("projectCode",projectCode);
        List<SysUserEntity> sysUserEntities = sysUserService.queryAll(searchParams);
        if(UtilCollection.isNotEmpty(sysUserEntities)){
            List<Map<String, Object>> lists = UtilDTO.toDTO(sysUserEntities,getDtoCallbackHandler());
            return lists;
        }
        return null;
    }

    /**
     *根据条件查询全部用户
     *
     * @param projectCode
     * @param map
     * @author liuke
     * @date 2022/3/16 14:43
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserEntity>
     */
    @PostMapping("/queryalluser/{projectCode}")
    List<SysUserEntity> queryAllUser(@PathVariable("projectCode") String projectCode,@RequestBody Map<String,Object> map){
        map.put("projectCode",projectCode);
        if(map.get("orgId")!=null&&UtilString.isNotBlank(map.get("orgId").toString())){
            map.put("orgId",map.get("orgId").toString());
        }
        if(map.get("realName")==null){
            map.put("realName","");
        }
        //执行查询
        List<SysUserEntity> sysUserEntities = sysUserService.queryAllUserByRoleCode(map);
        return sysUserEntities;
    }

    /**
     *根据条件查询全部管理员用户
     *
     * @param projectCode
     * @param map (roleCode,realName,orgId)
     * @author liuke
     * @date 2022/3/16 14:43
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserEntity>
     */
    @PostMapping("/querymanageruser/{projectCode}")
    List<Map<String,Object>> queryAllManagerUser(@PathVariable("projectCode") String projectCode,@RequestBody Map<String,Object> map){
        map.put("projectCode",projectCode);
        if(map.get("orgId")!=null&&UtilString.isNotBlank(map.get("orgId").toString())){
            map.put("orgId",map.get("orgId").toString());
        }
        //执行查询
        List<Map<String,Object>> sysUserEntities = sysUserService.queryManagerUsers(map);
        return sysUserEntities;
    }

    /**
     *根据条件分页查询全部管理员用户
     *
     * @param projectCode
     * @param map (roleCode,realName,orgId)
     * @author liuke
     * @date 2022/3/16 14:43
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserEntity>
     */
    @PostMapping("/querymanageruserpage/{projectCode}")
    ResponseParam queryPageManagerUser(@PathVariable("projectCode") String projectCode,@RequestBody Map<String,Object> map){
        map.put("projectCode",projectCode);
        if(map.get("orgId")!=null&&UtilString.isNotBlank(map.get("orgId").toString())){
            map.put("orgId",map.get("orgId").toString());
        }
        if(map.get("pageNum")==null || map.get("pageSize")==null){
            return ResponseParam.fail().message("缺少分页信息");
        }
        Pageable pageable = MybatisPageRequest.of(Integer.parseInt(map.get("pageNum").toString()),Integer.parseInt(map.get("pageSize").toString()));
        //执行查询
        Page<Map<String,Object>> results = pbsUserService.queryPageManagerUsers(map,pageable);
        return ResponseParam.success().pageParam(results).data(results.getContent());
    }

    /**
     * 分页查询组织下工作人员
     * @return
     * @throws Exception
     */
    @PostMapping("query/worker/byorg")
    public ResponseParam queryWorkerByScopOrg(@RequestParam(value = "realName" ,required = false) String realName,
                                              @RequestParam("orgIds") ArrayList<Long> orgIds,
                                              @RequestParam("pageNum") int pageNum,
                                              @RequestParam("pageSize") int pageSize){

        //获取查询参数
        Map<String, Object> searchParam =  Maps.newHashMap();
        searchParam.put("orgIds",orgIds);
        if(StringUtils.isNotBlank(realName)){
            searchParam.put("nameOrPhone",realName);
        }
        //执行分页查询
        Pageable pageable = MybatisPageRequest.of(pageNum,pageSize);
        Page<Map<String,Object>> users = pbsUserService.queryWorkerByScopOrg(searchParam,pageable);

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysUsers = UtilDTO.toDTO(users.getContent(),null
                ,null, getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .data( users,sysUsers) ;
    }

    /**
     * 查询管理员总数
     * @return
     * @throws Exception
     */
    @PostMapping("query/countworker/byorg")
    public ResponseParam queryWorkerCountByScopOrg(@RequestParam("orgIds") ArrayList<Long> orgIds){

        //获取查询参数
        Map<String, Object> searchParam =  Maps.newHashMap();
        searchParam.put("orgIds",orgIds);
        //执行查询
        int usernum = pbsUserService.queryWorkerCountByScopOrg(searchParam);

        return ResponseParam.success().data(usernum);
    }

    /**
     *批量删除
     *
     * @param userIds
     * @author liuke
     * @date 2022/5/6 10:30
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "deluser/{project}")
    public ResponseParam delUser(@PathVariable("project") String project,@RequestParam("userIds") Long[] userIds){
        Long projectId = sysProjectService.getProjectId(project);
        Map<String,Object> map = Maps.newHashMap();
        map.put("projectId",projectId);
        map.put("ids",userIds);
        pbsUserService.deleteByIds(map);
        return ResponseParam.deleteSuccess();
    };

    /**
     *根据手机号批量删除
     *
     * @param telephones
     * @author liuke
     * @date 2022/5/6 10:30
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "deluserbytelephone/{project}")
    public ResponseParam delUserByPhone(@PathVariable("project") String project,@RequestParam("telephones") String[] telephones){
        Long projectId = sysProjectService.getProjectId(project);
        Map<String,Object> map = Maps.newHashMap();
        map.put("projectId",projectId);
        map.put("telephones",telephones);
        pbsUserService.deleteByIds(map);
        return ResponseParam.deleteSuccess();
    };

    /**
     *新增用户接口
     *
     * @param project
     * @param users
     * @author liuke
     * @date 2022/5/5 10:45
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "saveuser/{project}")
    public ResponseParam saveUser(@PathVariable("project") String project,@RequestBody List<Map<String,Object>> users){
        List<SysUserEntity> sysUsers = Lists.newArrayList();
        for (Map<String, Object> map : users) {
            sysUsers.add(JSON.parseObject(JSON.toJSONString(map),SysUserEntity.class));
        }

        List<String> ids = new ArrayList<String>(sysUsers.size());
        try {
            Long projectId = sysProjectService.getProjectId(project);
            List<String> phones = Lists.newArrayList();
            for (SysUserEntity sysUser : sysUsers) {
                if(sysUser.getTelephone()==null){
                    return ResponseParam.saveFail().message("手机号不能为空");
                }else {
                    phones.add(sysUser.getTelephone());
                }
                sysUser.setProjectId(projectId);
                sysUser.setProjectCode(project);
                sysUser.setDel(Boolean.FALSE);
                sysUser.setShelvesType(ShelvesType.role);
                if(sysUser.getAuthStatus()== null){
                    sysUser.setAuthStatus(AuthStatus.AUTH);
                }
                sysUser.setStatus(UserStatus.VALID);
                sysUser.setHeadImgUrl(headImgUrl);
                sysUser.setAuthTime(new Date());
                sysUser.setSource("用户权限中心");
                sysUser.setIdCard(PassWordUtil.encrypt(sysUser.getIdCard()));
                sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
                if(sysUser.getId()==null){
                    AppIDGenerator appIDGenerator = new AppIDGenerator();
                    sysUser.setId(appIDGenerator.getNextId());
                }
                ids.add(sysUser.getId().toString());
            }
            if(!UtilCollection.sizeIsEmpty(phones)){
                Map<String,Object> telParam = Maps.newHashMap();
                telParam.put("telephones",phones);
                telParam.put("projectId",projectId);
                telParam.put("del",false);
                if(sysUserService.isExist(telParam)){
                    return ResponseParam.saveFail().message(UserConstant.CHECK_TELEPHONE);
                }
            }
            pbsUserService.saveinfo(sysUsers);
        } catch (Exception e) {
            return ResponseParam.saveFail();
        }
        return ResponseParam.saveSuccess().datalist(ids) ;
    };

    /**
     *修改用户信息
     *
     * @param project
     * @author liuke
     * @date 2022/5/6 10:53
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping(value = "updateuser/{project}")
    public ResponseParam updateUser(@PathVariable("project") String project,@RequestBody Map<String,Object> user) throws Exception{
        SysUserEntity sysUser = JSON.parseObject(JSON.toJSONString(user),SysUserEntity.class);
        if(StringUtils.equals(sysUser.getUserName(),"admin")){
            return ResponseParam.saveFail().message("admin账号不允许创建");
        }
        // 查询租户编码
        //SysProjectEntity sysProjectEntity = sysProjectService.get(sysProjectService.getProjectId(project));
        sysUser.setProjectCode(project);
        sysUser.setProjectId(sysProjectService.getProjectId(project));
        Map<String,Object> map = UtilDTO.toDTO(sysUser,null,getDtoCallbackHandler());
        List<String> cloums = Lists.newArrayList();
        for (String s : map.keySet()) {
            if(map.get(s)!=null){
                cloums.add(s);
            }
        }
        sysUserService.update(sysUser,cloums);
        return ResponseParam.updateSuccess() ;
    };

    /**
     * 查询用户
     *
     * @param project
     * @param sysUserDto 过滤条件
     * @return
     */
    @PostMapping("/queryUser/{project}")
    List<Map<String, Object>> queryUser(@PathVariable("project") String project, @RequestBody Map<String, Object> sysUserDto) {
        if (org.apache.commons.lang.StringUtils.isBlank(project)) {
            return null;
        }

        try {
            // 查询租户下存在的用户
            HashMap<String, Object> userParams = Maps.newHashMap();
            userParams.put("projectCode", project);
            userParams.put("orgId", NumberUtil.parseLong((String) sysUserDto.get("orgId")));
            userParams.put("idCard", sysUserDto.get("idCard") == null ? null : PassWordUtil.encrypt((String) sysUserDto.get("idCard")));
            userParams.put("telephone", sysUserDto.get("telephone") == null ? null : (String) sysUserDto.get("telephone"));
            userParams.put("realNameOrTelephoneLike", sysUserDto.get("realNameOrTelephoneLike") == null ? null : (String) sysUserDto.get("realNameOrTelephoneLike"));
            userParams.put("realName", sysUserDto.get("realName") == null ? null : (String) sysUserDto.get("realName"));
            List<SysUserEntity> sysUserEntities = sysUserService.queryAllUserInfo(userParams);
            List<Map<String, Object>> result = UtilDTO.toDTO(sysUserEntities, null, getDtoCallbackHandler());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询用户下角色
     *
     * @param project
     * @return
     */
    @PostMapping("/queryUserRole/{project}")
    List<Map<String, Object>> queryUserRole(@PathVariable("project") String project, @RequestParam("userId") Long userId, @RequestParam("appCode") String appCode) {
        Map<String,Object> search = Maps.newHashMap();
        search.put("projectId",sysProjectService.getProjectId(project));
        search.put("appCode",appCode);
        search.put("userId",userId);
        return pbsUserService.queryUserRole(search);
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
