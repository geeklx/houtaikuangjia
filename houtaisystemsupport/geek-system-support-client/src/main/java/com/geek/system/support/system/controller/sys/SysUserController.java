package com.geek.system.support.system.controller.sys;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.common.util.UtilTree;
import com.geek.system.support.mq.MQMessageConstant;
import com.geek.system.support.system.anno.SysLog;
import com.geek.system.support.system.controller.auth.SysIBaseController;
import com.geek.system.support.system.dict.OptLogType;
import com.geek.system.support.system.dict.ShelvesType;
import com.geek.system.support.system.dict.SysMqUserTypeConstant;
import com.geek.system.support.system.dto.sys.CityDto;
import com.geek.system.support.system.dto.sys.ShelvesUserRoleDto;
import com.geek.system.support.system.dto.sys.SysUserDto;
import com.geek.system.support.system.dto.sys.SysUserRoleScopDto;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.service.config.DTOCallbackHandlerWithSysDict;
import com.geek.system.support.system.service.mq.SysMQProducerService;
import com.geek.system.support.system.service.sys.*;
import com.geek.system.support.system.util.*;
import com.geek.system.support.system.service.sys.*;
import com.geek.system.support.system.vo.SysResourceMenuVo;
import com.geek.system.support.system.vo.SysUserDetailVo;
import com.geek.system.support.system.util.*;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import org.springframework.web.bind.annotation.*;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value=SysUserController.BASE_PATH)
@SuppressWarnings("unchecked")
public class SysUserController extends SysIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/sysuser" ;

    @Value("${app.globalParams.headImgUrl:http://119.188.115.252:8090/resource-handle/uploads/image/2022-02-14/3532192544572062147.png}")
    private String headImgUrl;

    @Autowired
    private SysUserService sysUserService ;


    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysUserResourceService sysUserResourceService;

    @Autowired
    private SysProjectService sysProjectService;

    @Autowired
    private SysUserRoleScopService sysUserRoleScopService;

    @Autowired
    private SysMQProducerService sysMQProducerService;

    @Value("${app.faceUsers}")
    private String faceUsers;

    @Autowired
    private DTOCallbackHandlerWithSysDict handlerWithSysDict;


    /**
     * 用户记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    public ResponseParam query(@RequestBody SysUserDto sysUserDto) throws Exception {
        if(StringUtils.isNoneBlank(sysUserDto.getIdCard())){
            sysUserDto.setIdCard(PassWordUtil.encrypt(sysUserDto.getIdCard()));
        }
        //获取查询参数

        Map<String, Object> searchParam =  UtilDTO.toDTO(sysUserDto, null);

        //执行分页查询
        String[] order = new String[]{"createDatetime_desc"};
        Page<SysUserEntity> sysUserPage = sysUserService.queryByPage(searchParam , sysUserDto.getPageNum() , sysUserDto.getPageSize(),order) ;
        List<SysUserEntity> content = sysUserPage.getContent();
        // 身份证与手机号脱敏
        Boolean telephone = false;
        if(!UtilCollection.sizeIsEmpty(content)){
            telephone = sysProjectService.getProjectConfig(content.get(0).getProjectId()).isTelephoneDesensitization();
        }
        Boolean finalTelephone = telephone;
        content.forEach(user->{
            String idCard = null;
            try {
                idCard = PassWordUtil.decrypt(user.getIdCard());
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setShelvesType(user.getShelvesType() == null ? ShelvesType.role : user.getShelvesType());
            user.setIdCard(SysCommonUtil.formatIdcard(idCard));
            if(finalTelephone){
                user.setTelephone(user.getTelephone() != null ? SysCommonUtil.formatTelephone(user.getTelephone()) : null);
            }
            if(user.getHeadImgUrl()==null||headImgUrl.equals(user.getHeadImgUrl())){
                user.setFaceStatus("未采集");
            }else{
                user.setFaceStatus("已采集");
            }
            // 查询用户角色
            List<String> roles = sysUserService.queryUserBindRole(user.getId());
            if(UtilCollection.isNotEmpty(roles)){
                String role = StringUtils.join(roles, ",");
                user.setRoleName(role);
            }
            // 查询用户岗位
            List<SysUserPostScopeEntity> sysUserPostScopeEntities = sysUserService.queryUserBindPost(user.getId());
            if(UtilCollection.isNotEmpty(sysUserPostScopeEntities)){
                List<String> posts = sysUserPostScopeEntities.stream().map(SysUserPostScopeEntity::getPostName).collect(Collectors.toList());
                String post = StringUtils.join(posts, ",");
                user.setPost(post);
            }
        });

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysUserList = UtilDTO.toDTO(content,
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( sysUserPage )
                .datalist( sysUserList ) ;
    }



    /**
     * 用户记录查询
     * @return
     * @throws Exception
     */
    @PostMapping("queryall")
    public ResponseParam queryAll(@RequestBody SysUserDto sysUserDto) throws Exception {
        // 身份证加密查询
        if(StringUtils.isNoneBlank(sysUserDto.getIdCardL())){
            sysUserDto.setIdCardL(PassWordUtil.encrypt(sysUserDto.getIdCardL()));
        }
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysUserDto, null);
        //执行查询
        List<SysUserEntity> sysUserEntities = sysUserService.queryAll(searchParam);
        Boolean telephone = false;
        if(!UtilCollection.sizeIsEmpty(sysUserEntities)){
            telephone = sysProjectService.getProjectConfig(sysUserEntities.get(0).getProjectId()).isTelephoneDesensitization();
        }
        Boolean finalTelephone = telephone;
        // 身份证与手机号脱敏
        sysUserEntities.forEach(user->{
            String idCard = PassWordUtil.desDecrypt(user.getIdCard());
            user.setIdCard(SysCommonUtil.formatIdcard(idCard));
            if(finalTelephone) {
                user.setTelephone(user.getTelephone() != null ? SysCommonUtil.formatTelephone(user.getTelephone()) : null);
            }
            if(user.getHeadImgUrl()==null||headImgUrl.equals(user.getHeadImgUrl())){
                user.setFaceStatus("未采集");
            }else{
                user.setFaceStatus("已采集");
            }
        });
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysUserList = UtilDTO.toDTO(sysUserEntities,
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .datalist( sysUserList) ;
    }
    /**
     * 获取用户详情数据。
     */
    @PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param) throws Exception {
        // 查询用户表
        SysUserEntity sysUser = sysUserService.get(param.getId()) ;
        // 人脸采集状态
        if(sysUser.getHeadImgUrl()==null||headImgUrl.equals(sysUser.getHeadImgUrl())){
            sysUser.setFaceStatus("未采集");
        }else{
            sysUser.setFaceStatus("已采集");
        }
        // 身份证与手机号脱敏
        sysUser.setIdCard(PassWordUtil.decrypt(sysUser.getIdCard()));
        if(sysProjectService.getProjectConfig(sysUser.getProjectId()).isTelephoneDesensitization()){
            sysUser.setTelephone(sysUser.getTelephone() != null ? SysCommonUtil.formatTelephone(sysUser.getTelephone()) : null);
        }
        // 查询用户绑定角色信息
        List<String> roles = sysUserService.queryUserBindRole(sysUser.getId());
        if(UtilCollection.isNotEmpty(roles)){
            String role = StringUtils.join(roles, ",");
            sysUser.setRoleName(role);
        }
        // 查询用户岗位
        List<SysUserPostScopeEntity> posts = sysUserService.queryUserBindPost(sysUser.getId());
        sysUser.setPosts(posts);
        // 通过容联获取人脸同步状态
        if(sysProjectService.getProjectConfig(sysUser.getProjectId()).isFaceCheck()){
            String rlFace = connRlFace(Long.toString(param.getId()));
            sysUser.setRlFaceStatus(rlFace);
        }else {
            sysUser.setRlFaceStatus(sysUser.getFaceStatus());
        }
        // 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysUser ,null , getDtoCallbackHandler() ) ;
        dtoObject.put("posts",UtilDTO.toDTO( posts ,null , getDtoCallbackHandler() ));

        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 描述: 通过用户id查询容联人脸同步状态
     * @param userId
     * @return java.lang.String
     * @author fuhao
     * @date 2022/2/14 17:03
     **/
    public String connRlFace(String userId){
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userId",userId);
        JSONObject jsonObject = HttpClientUtils.httpPost(faceUsers, jsonParam);
        if((Boolean) jsonObject.get("success")){
            JSONArray datalist = (JSONArray) jsonObject.get("datalist");
            String jsonStr = JSONObject.toJSONString(datalist);
            List<Map> list = JSONObject.parseArray(jsonStr,  Map.class);
            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);
                String status = (String) map.get("state");
                switch(status){
                    case "SUCCESS":
                        return "同步成功";
                    case "PENDING":
                        return "进行中";
                    case "ERROR":
                        return "同步失败";
                    case "NOT":
                        return "同步失败";
                    default:
                        return "同步失败";

                }
            }
        }
        return "同步失败";
    }

    /**
     * 保存用户实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param sysUser
     * @return
     * @throws Exception
     */
    @PostMapping("save")
    @SysLog(optModule = "用户管理",optName = "保存",optType = OptLogType.SAVE)
    public ResponseParam save(@RequestBody SysUserEntity  sysUser) throws Exception {
        if(sysUser.getProjectId() == null){
            return ResponseParam.fail().message(ProjectConstant.CHECK_ID);
        }
        //id不为空，进行更新操作，否则进行添加
        if(sysUser.getId() != null){
            SysUserEntity user = sysUserService.get(sysUser.getId());
            if(sysUser.getTelephone()!=null&&!UtilString.equals(user.getTelephone(),sysUser.getTelephone())){
                Map<String,Object> telParam = Maps.newHashMap();
                telParam.put("telephone",sysUser.getTelephone());
                telParam.put("projectId",sysUser.getProjectId());
                telParam.put("del",false);
                if(sysUserService.isExist(telParam)){
                    return ResponseParam.saveFail().message(UserConstant.CHECK_TELEPHONE);
                }
            }
            sysUserService.saveInfo( sysUser) ;
            return ResponseParam.updateSuccess() ;
        }else{
            if(StringUtils.equals(sysUser.getUserName(),"admin")){
                return ResponseParam.saveFail().message("admin账号不允许创建");
            }
            // 判断用户名是否存在
            Map<String,Object> userNameParam = Maps.newHashMap();
            userNameParam.put("projectId",sysUser.getProjectId());
            userNameParam.put("userName",sysUser.getUserName());
            userNameParam.put("del",false);
            if(sysUserService.isExist(userNameParam)){
                return ResponseParam.saveFail().message(UserConstant.CHECK_USER_NAME);
            }
            if(UtilString.isNotBlank(sysUser.getIdCard())&&!IdCardUtil.isIdCard(sysUser.getIdCard())){
                return ResponseParam.fail().message(UserConstant.CHECK_ILLEGAL_ID_CARD);
            }
            // 判断用户身份证是否存在
            if(UtilString.isNotBlank(sysUser.getIdCard())){
                Map<String,Object> idCardParam = Maps.newHashMap();
                idCardParam.put("projectId",sysUser.getProjectId());
                idCardParam.put("idCard",PassWordUtil.encrypt(sysUser.getIdCard()));
                idCardParam.put("del",false);
                if(sysUserService.isExist(idCardParam)){
                    return ResponseParam.saveFail().message(UserConstant.CHECK_ID_CARD);
                }
            }
            // 判断手机号是否存在
            Map<String,Object> telParam = Maps.newHashMap();
            telParam.put("telephone",sysUser.getTelephone());
            telParam.put("projectId",sysUser.getProjectId());
            telParam.put("del",false);
            if(sysUserService.isExist(telParam)){
                return ResponseParam.saveFail().message(UserConstant.CHECK_TELEPHONE);
            }
            // 查询租户编码
            SysProjectEntity sysProjectEntity = sysProjectService.get(sysUser.getProjectId());
            sysUser.setProjectCode(sysProjectEntity.getProjectCode());
            sysUserService.saveInfo(sysUser);
            return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 校验身份证
     * @param sysUser
     * @return
     * @throws Exception
     */
    @PostMapping("checksysuser")
    public ResponseParam checkIdCard(@RequestBody SysUserEntity  sysUser) throws Exception {
        if(sysUser.getProjectId() == null){
            return ResponseParam.fail().message(ProjectConstant.CHECK_ID);
        }
        //id不为空，进行更新操作，否则进行添加
        if(StringUtils.equals(sysUser.getUserName(),"admin")){
            return ResponseParam.saveFail().message("admin账号不允许创建");
        }
        // 判断用户名是否存在
        Map<String,Object> userNameParam = Maps.newHashMap();
        userNameParam.put("projectId",sysUser.getProjectId());
        userNameParam.put("userName",sysUser.getUserName());
        userNameParam.put("del",false);
        if(sysUserService.isExist(userNameParam)){
            return ResponseParam.saveFail().message(UserConstant.CHECK_USER_NAME);
        }
        if(UtilString.isNotBlank(sysUser.getIdCard())&&!IdCardUtil.isIdCard(sysUser.getIdCard())){
            return ResponseParam.fail().message(UserConstant.CHECK_ILLEGAL_ID_CARD);
        }
        // 判断用户身份证是否存在
        if(UtilString.isNotBlank(sysUser.getIdCard())){
            Map<String,Object> idCardParam = Maps.newHashMap();
            idCardParam.put("projectId",sysUser.getProjectId());
            idCardParam.put("idCard",PassWordUtil.encrypt(sysUser.getIdCard()));
            idCardParam.put("del",false);
            if(sysUserService.isExist(idCardParam)){
                return ResponseParam.saveFail().message(UserConstant.CHECK_ID_CARD);
            }
        }
        // 判断手机号是否存在
        Map<String,Object> telParam = Maps.newHashMap();
        telParam.put("telephone",sysUser.getTelephone());
        telParam.put("projectId",sysUser.getProjectId());
        telParam.put("del",false);
        if(sysUserService.isExist(telParam)){
            return ResponseParam.saveFail().message(UserConstant.CHECK_TELEPHONE);
        }
        return ResponseParam.success() ;
    }

    /**
     * 校验身份证
     * @param sysUser
     * @return
     * @throws Exception
     */
    @PostMapping("checksysuseridcard")
    public ResponseParam checkSysUserIdCard(@RequestBody SysUserEntity  sysUser) throws Exception {
        if(sysUser.getProjectId() == null){
            return ResponseParam.fail().message(ProjectConstant.CHECK_ID);
        }
        if(UtilString.isNotBlank(sysUser.getIdCard())&&!IdCardUtil.isIdCard(sysUser.getIdCard())){
            return ResponseParam.fail().message(UserConstant.CHECK_ILLEGAL_ID_CARD);
        }
        // 判断用户身份证是否存在
        if(UtilString.isNotBlank(sysUser.getIdCard())){
            if (sysUser.getId()!=null){
                SysUserEntity sysUserEntity = sysUserService.get(sysUser.getId());
                if(sysUserEntity!=null && UtilString.equals(PassWordUtil.encrypt(sysUser.getIdCard()),sysUserEntity.getIdCard())){
                    return ResponseParam.success();
                }
            }
            Map<String,Object> idCardParam = Maps.newHashMap();
            idCardParam.put("projectId",sysUser.getProjectId());
            idCardParam.put("idCard",PassWordUtil.encrypt(sysUser.getIdCard()));
            idCardParam.put("del",false);
            if(sysUserService.isExist(idCardParam)){
                return ResponseParam.saveFail().message(UserConstant.CHECK_ID_CARD);
            }
        }
        return ResponseParam.success() ;
    }
    /**
     * 描述: 校验身份证是否合法
     * @param dto
     * @return com.geek.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2022/1/27 15:05
     **/
    @PostMapping("check/idcard")
    public ResponseParam checkIdCard(@RequestBody SysUserDto dto) {
        boolean idCard = IdCardUtil.isIdCard(dto.getIdCard());
        if(!idCard){
            return ResponseParam.fail().message(UserConstant.CHECK_ILLEGAL_ID_CARD) ;
        }
        return ResponseParam.success().message(UserConstant.CHECK_LEGAL_ID_CARD) ;
    }

    /**
     * 删除用户信息
     * @param list
     * @return
     */
    @PostMapping("delete")
    @SysLog(optModule = "用户管理",optName = "删除",optType = OptLogType.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        List<Long> ids = list.stream().map(map -> map.getId()).collect(Collectors.toList());
        for (Long id : ids) {
            SysUserEntity sysUserEntity = sysUserService.get(id);
            // 发送mq消息
            JSONObject jsonObject = sysMQProducerService.buildSysUserMessage(sysUserEntity, SysMqUserTypeConstant.deleteUser.name());
            sysMQProducerService.sendMQMessage(SysMqUserTypeConstant.deleteUser.name(), jsonObject, MQMessageConstant.USER_TAG);
        }
        sysUserService.delete(ids);

        return ResponseParam.deleteSuccess() ;
    }

    /**
     * 描述: 批量启动/禁用
     * @param sysUser
     * @return com.geek.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/25 10:22
     **/
    @PostMapping("update/status")
    @SysLog(optModule = "用户管理",optName = "批量启用/禁用",optType = OptLogType.BATCH_ENABLED)
    public ResponseParam batchUpdateStatus(@RequestBody List<SysUserEntity>  sysUser) {
        if(sysUser!=null){
            sysUser.forEach(user -> {
                if(user.getId() != null){
                    //按照字段更新对象
                    sysUserService.update( user , Arrays.asList("status")) ;
                }
            });
            return ResponseParam.updateSuccess() ;
        }
        return ResponseParam.updateFail();
    }

    /**
     * 描述: 用户管理获取管理范围
     * @param sysUserRoleScopDto
     * @return com.geek.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/12/4 15:25
     **/
    @PostMapping("query/orgscope")
    public ResponseParam managerScope(@RequestBody SysUserRoleScopDto sysUserRoleScopDto) {
        List<SysOrgEntity> sysOrgEntities = sysUserService.managerScope(sysUserRoleScopDto);
        //当数据类型为treedata时转换为tree
        List<Map<String, Object>> sysOrgList = UtilDTO.toDTO(sysOrgEntities,
                null , getDtoCallbackHandler()) ;
        List<Map<String, Object>> resultSysOrg= UtilTree.getTreeData(sysOrgList,"id","parentId","children",false);

        return ResponseParam.success().datalist(resultSysOrg);
    }


    /**
     * 描述: 用户绑定管理范围
     * @param sysUserRoleScopDto
     * @return com.geek.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/12/6 9:16
     **/
    @PostMapping("save/bindorgscope")
    public ResponseParam bindManagerScope(@RequestBody SysUserRoleScopDto sysUserRoleScopDto) {
        sysUserService.bindManagerScope(sysUserRoleScopDto);
        return ResponseParam.saveSuccess();
    }


    /**
     * 描述: 重置密码
     * @param sysUser
     * @return com.geek.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/30 16:39
     **/
    @PostMapping("update/repassword")
    @SysLog(optModule = "用户管理",optName = "重置密码",optType = OptLogType.UPDATE)
    public ResponseParam rePassword(@RequestBody SysUserEntity  sysUser) {
        if(sysUser.getId()!=null){
            sysUserService.update( sysUser , Arrays.asList("password")) ;
            return ResponseParam.updateSuccess() ;
        }
        return ResponseParam.updateFail().message(UserConstant.CHECK_ID);
    }

    /**
     * 描述: 查询用户管理角色范围
     * @param sysRoleDto
     * @return com.geek.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/12/2 17:01
     **/
    @PostMapping("query/rolescope")
    public ResponseParam queryRoleScope(@RequestBody SysUserRoleScopDto sysRoleDto) {
        if(sysRoleDto.getRoleId()==null || sysRoleDto.getProjectId() == null){
            return ResponseParam.fail().message("角色id与租户id不能为空！");
        }
        sysRoleDto.setShelvesType(sysRoleDto.getShelvesType() == null ? ShelvesType.role : sysRoleDto.getShelvesType());
        if(sysRoleDto.getShelvesType().name().equals(ShelvesType.role.name())){
            List<Map<String,Object>> sysRoleScopEntities = sysUserService.queryUserRoleScope(sysRoleDto);
            return ResponseParam.success().datalist(sysRoleScopEntities);
        }else {
            SysRoleScopEntity sysRoleScopEntities = sysUserService.queryUserResourceScope(sysRoleDto);
            return ResponseParam.success().data(sysRoleScopEntities);
        }
    }

    /**
     * 描述: 查询用户管理角色范围
     * @param sysRoleDto
     * @return com.geek.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/12/2 17:01
     **/
    @PostMapping("query/onlyrolescope")
    public ResponseParam queryOnlyRoleScope(@RequestBody SysUserRoleScopDto sysRoleDto) {
        if(sysRoleDto.getRoleId()==null || sysRoleDto.getProjectId() == null){
            return ResponseParam.fail().message("角色id与租户id不能为空！");
        }
        sysRoleDto.setShelvesType(sysRoleDto.getShelvesType() == null ? ShelvesType.role : sysRoleDto.getShelvesType());
        List<Map<String,Object>> sysRoleScopEntities = sysUserService.queryOnlyUserRoleScope(sysRoleDto);
        return ResponseParam.success().datalist(sysRoleScopEntities);

    }



    /**
     *为用户新增一个角色
     *
     * @param sysUserRoleEntity
     * @author liuke
     * @date 2021/4/15 16:25
     * @return com.geek.framework.web.http.ResponseParam
     */
    @RequestMapping("bindrole")
    public ResponseParam userBindRole(@RequestBody SysUserRoleEntity sysUserRoleEntity){
        sysUserRoleService.save(sysUserRoleEntity);
        return ResponseParam.saveSuccess();
    }
    /**
     *移除已经绑定的角色
     *
     * @param list
     * @author liuke
     * @date 2021/4/15 16:31
     * @return com.geek.framework.web.http.ResponseParam
     */
    @RequestMapping("removerole")
    public ResponseParam userReBindRole(@RequestBody List<AppBaseIdParam> list){
        if(UtilCollection.sizeIsEmpty(list)){
            return ResponseParam.deleteFail();
        }
        sysUserRoleService.delete(list.stream().map(map->map.getId()).collect(Collectors.toList()));
        return ResponseParam.deleteSuccess();
    }
    /**
     *为用户重新授权角色
     *
     * @param sysUserRoleEntitys
     * @author liuke
     * @date 2021/4/16 9:36
     * @return com.geek.framework.web.http.ResponseParam
     */
    @RequestMapping("rebindrole")
    public ResponseParam userBindRole(@RequestBody List<SysUserRoleEntity> sysUserRoleEntitys){
        if(UtilCollection.sizeIsEmpty(sysUserRoleEntitys)){
            return ResponseParam.saveFail();
        }
        sysUserRoleService.reBindRole(sysUserRoleEntitys);
        return ResponseParam.saveSuccess();
    }

    /**
     *根据用户ID获取用户基本信息
     *
     * @param userId
     * @author liuke
     * @date 2021/4/19 14:21
     * @return com.geek.framework.web.http.ResponseParam
     */
    @PostMapping("getuserdetails")
    public ResponseParam getUserDetail(@RequestParam("userId") Long userId){

        SysUserDetailVo sysUserDetailVo = sysUserService.getUserDetails(userId);
        return ResponseParam.success().data(sysUserDetailVo);
    }

    @PostMapping("getresourcemenu")
    public ResponseParam queryResourceByAppCodeAndUserIdAndRole(String userId,String roleId) {
        Map<String,Object> searchParams = Maps.newHashMap();
        searchParams.put("userId",Long.valueOf(userId));
        searchParams.put("roleId",Long.valueOf(roleId));
        List<SysResourceMenuVo> sysResourceMenuVos = sysResourceService.selectByUserIdAndAppCode(searchParams);
        List<Map<String,Object>> resultLists = UtilDTO.toDTO(sysResourceMenuVos,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(resultLists);
    }

    /**
     * 描述: 保存授权
     * @param sysUserRoleScopDto
     * @return com.geek.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/12/16 17:22
     **/
    @PostMapping("save/shelves")
    @SysLog(optModule = "用户管理",optName = "授权",optType = OptLogType.SAVE)
    public ResponseParam saveShelves(@RequestBody SysUserRoleScopDto sysUserRoleScopDto) {
        if(sysUserRoleScopDto.getUserId() == null){
            return ResponseParam.fail().message(UserConstant.CHECK_ID);
        }
        try {
            sysUserService.saveShelves(sysUserRoleScopDto);
            return ResponseParam.saveSuccess();
        }catch (Exception e){
            return ResponseParam.saveFail().message(e.getMessage());
        }

    }

    /**
     * 描述: 批量授权
     * @param projectId
     * @param file
     * @return com.geek.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2022/1/17 14:23
     **/
    @PostMapping("batch/save/shelves")
    @SysLog(optModule = "用户管理",optName = "批量授权",optType = OptLogType.IMPORT)
    public ResponseParam batchSaveShelves(@RequestParam("projectId") Long projectId,@RequestParam("file") MultipartFile file) throws Exception {
        if(projectId == null){
            return ResponseParam.fail().message("租户id不能为空");
        }
        HashSet<String> list = sysUserService.batchSaveShelves(projectId, file);
        if(list == null){
            return ResponseParam.success().message("导入成功");
        }
        return ResponseParam.success().datalist(list).message("导入失败");
    }

    /**
     *
     * 批量授权
     *
     * @author liuke
     * @date  2022/6/9 8:59
     * @version
    */
    @PostMapping("batch/shelves")
    @SysLog(optModule = "用户管理",optName = "批量授权",optType = OptLogType.SAVE)
    public ResponseParam batchShelves(@RequestBody ShelvesUserRoleDto shelvesUserRoleDto){

        try {
            sysUserService.batchShelves(shelvesUserRoleDto);
            return ResponseParam.success().message("授权成功");
        }catch (Exception e){
            return ResponseParam.fail().message(e.getMessage());
        }

    }

    /**
     *批量保存用户
     *
     * @param file
     * @author liuke
     * @date 2022/3/28 16:19
     * @return com.geek.framework.web.http.ResponseParam
     */
    @PostMapping("batch/save/users")
    public ResponseParam batchSaveShelves(@RequestParam("file") MultipartFile file) throws Exception {
        HashSet<String> list = sysUserService.batchSaveUsers(file);
        if(list == null){
            return ResponseParam.success().message("导入成功");
        }
        return ResponseParam.success().datalist(list).message("导入失败");
    }

    /**
     * 描述: 批量撤销授权
     * @param sysUserRoleScopDto
     * @return com.geek.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2022/1/17 15:36
     **/
    @PostMapping("batch/del/shelves")
    @SysLog(optModule = "用户管理",optName = "批量撤销授权",optType = OptLogType.DELETE)
    public ResponseParam batchDelShelves(@RequestBody List<SysUserRoleScopDto> sysUserRoleScopDto) {
        // 批量撤销授权
        sysUserService.batchDelShelves(sysUserRoleScopDto);
        return ResponseParam.success().message("已撤销");
    }



    /**
     * 描述: 获取城市下拉框
     * @param
     * @return java.util.List<com.geek.system.support.system.entity.sys.SysCityEntity>
     * @author fuhao
     * @date 2022/1/14 11:16
     **/
    @PostMapping("cityinfo")
    public ResponseParam getCityInfo(@RequestBody CityDto dto) {
        List<SysCityEntity> citys = new ArrayList<>();
        if(dto.getProvince() != null){
            citys = sysUserService.getCityInfoByProvince(dto.getProvince());
        }else if (dto.getCode() != null){
            String code = StringUtils.substring(dto.getCode(),0,7);
            citys = sysUserService.getCityInfoByCode(code);
        }else {
            citys = sysUserService.getCityProvinceInfo();
        }


        List<Map<String,Object>> resultLists = UtilDTO.toDTO(citys,Arrays.asList("cityName","cityCode","cityProvince"),getDtoCallbackHandler());
        return ResponseParam.success().datalist(resultLists);
    }


    /**
     *
     * 校验党员
     * @author liuke
     * @date 2022/2/8 14:20
     * @return com.geek.framework.web.http.ResponseParam
     */
    @PostMapping("checkuser")
    public ResponseParam CheckDtUser(@RequestParam("idCard") String idCard,@RequestParam(value = "projectId" ,defaultValue = "0",required = false) Long projectId ) {
        SysProjectConfigEntity config = sysProjectService.getProjectConfig(projectId==0?getLoginProjectId():projectId);
        if(config!=null&&config.isCheckParty()){
            Map<String,Object> map = sysUserService.checkDtUser(idCard);
            if(map==null){

                return ResponseParam.fail().message("党员信息校验失败");
            }else {
                return ResponseParam.success().data(map).message("校验成功");
            }
        }else {
            Map<String,Object> map = Maps.newHashMap();
            map.put("id",null);
            map.put("realName",null);
            map.put("outId",null);
            map.put("outName",null);
            return ResponseParam.success().data(map).message("校验成功");
        }

    }

    /**
     * 获取PO到DTO转换的接口。主要用于在前端展示数据之前首先将数据格式处理完成。
     * @return
     */
    public DTOCallbackHandler getDtoCallbackHandler() {

        //创建转换接口类

        return getDTOCallbackHandlerProxy(handlerWithSysDict,true);
    }

}