package com.geek.system.support.system.service.sys;

import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.id.IdGenerators;
import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.mvc.config.session.common.AppUserInfo;
import com.fosung.framework.web.mvc.config.session.common.AppUserRole;
import com.geek.system.support.mq.MQMessageConstant;
import com.geek.system.support.system.dao.sys.SysUserDao;
import com.geek.system.support.system.dict.*;
import com.geek.system.support.system.dto.out.CloudAppDto;
import com.geek.system.support.system.dto.sys.*;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.dto.sys.*;
import com.geek.system.support.system.entity.sys.*;
import com.geek.system.support.system.service.feign.DtSourceService;
import com.geek.system.support.system.service.mq.SysMQProducerService;
import com.geek.system.support.system.service.pbs.PbsUserService;
import com.geek.system.support.system.util.*;
import com.geek.system.support.system.dict.SysMqUserTypeConstant;
import com.geek.system.support.system.vo.SysUserDetailVo;
import com.geek.system.support.util.easyexcel.dto.UserDetailDto;
import com.geek.system.support.util.easyexcel.dto.UserInfoDto;
import com.geek.system.support.util.easyexcel.utils.UserDetailDataListener;
import com.geek.system.support.util.easyexcel.utils.UserInfoDataListener;
import com.geek.system.support.system.dict.*;
import com.geek.system.support.system.util.Constant;
import com.geek.system.support.system.util.PassWordUtil;
import com.geek.system.support.system.util.UtilDigest;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl extends AppJPABaseDataServiceImpl<SysUserEntity, SysUserDao>
        implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPostService sysPostService;

    @Autowired
    private SysOrgService sysOrgService;

    @Autowired
    private DtSourceService dtSourceService;

    @Autowired
    private SysUserRoleScopService sysUserRoleScopService;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysUserResourceService sysUserResourceService;

    @Autowired
    private SysUserPostScopeService sysUserPostScopeService;

    @Autowired
    private SysMQProducerService sysMQProducerService;

    @Resource
    TransactionDefinition transactionDefinition;

    @Resource
    PlatformTransactionManager dataSourceTransactionManager;

    @Autowired
    private SysProjectService sysProjectService;

    @Autowired
    private SysRoleAppService sysRoleAppService;

    @Autowired
    private PbsUserService pbsUserService;
    /**
     * 查询条件表达式
     */
    private Map<String, String> expressionMap = new LinkedHashMap<String, String>() {
        {
            put("id", "id:EQ");
            put("ids", "id:IN");
            put("userName", "userName:EQ");
            put("userNames", "userName:IN");
            put("userNameL", "userName:LIKE");
            put("idCard", "idCard:EQ");
            put("idCards", "idCard:IN");
            put("idCardL", "idCard:LIKE");
            put("source", "source:EQ");
            put("status", "status:EQ");
            put("orgId", "orgId:EQ");
            put("orgIds", "orgId:IN");
            put("orgCode", "orgCode:EQ");
            put("orgName", "orgName:EQ");
            put("del", "del:EQ");
            put("sex", "sex:EQ");
            put("userType", "userType:EQ");
            put("telephone", "telephone:EQ");
            put("telephones", "telephone:IN");
            put("telephoneL", "telephone:LIKE");
            put("realName", "realName:EQ");
            put("realNameL", "realName:RLIKE");
            put("startTime", "createDatetime:GTEDATE");
            put("endTime", "createDatetime:LTEDATE");
            put("projectId", "projectId:EQ");
            put("projectCode", "projectCode:EQ");
            put("hash", "hash:EQ");
            put("dtUserId", "dtUserId:EQ");
            put("dtUserIds", "dtUserId:IN");
            put("wxOpenId","wxOpenId:EQ");
        }
    };


    @Override
    public Map<String, String> getQueryExpressions() {
        return expressionMap;
    }


    /**
     * 描述: 保存前缀
     *
     * @param sysUserEntity
     * @return void
     * @author fuhao
     * @date 2022/2/28 15:33
     **/
    @Override
    public void postSaveHandler(SysUserEntity sysUserEntity) {
        super.postSaveHandler(sysUserEntity);
        // 发送mq消息
        JSONObject jsonObject = sysMQProducerService.buildSysUserMessage(sysUserEntity, SysMqUserTypeConstant.addUser.name());
        sysMQProducerService.sendMQMessage(SysMqUserTypeConstant.addUser.name(), jsonObject, MQMessageConstant.USER_TAG);

    }

    /**
     * 描述: 删除后方法处理方法
     *
     * @param id
     * @return void
     * @author fuhao
     * @date 2022/2/28 15:33
     **/
    @Override
    public void postDeleteHandler(Long id) {
        super.postDeleteHandler(id);
        SysUserEntity sysUserEntity = get(id);
        // 发送mq消息
        JSONObject jsonObject = sysMQProducerService.buildSysUserMessage(sysUserEntity, SysMqUserTypeConstant.deleteUser.name());
        sysMQProducerService.sendMQMessage(SysMqUserTypeConstant.deleteUser.name(), jsonObject, MQMessageConstant.USER_TAG);
    }

    /**
     * 给用户绑定角色
     *
     * @param userId
     * @param roleIds
     * @return void
     * @author liuke
     * @date 2021/4/8 14:00
     */
    @Override
    public void bindRole(Long userId, Set<Long> roleIds, Long projectId) {
        if (!UtilCollection.sizeIsEmpty(roleIds)) {
            List<SysUserRoleEntity> sysUserRoleEntities = new ArrayList<SysUserRoleEntity>();
            roleIds.forEach(roleId -> {
                sysUserRoleEntities.add(new SysUserRoleEntity(userId, roleId, projectId));
            });
            sysUserRoleService.saveBatch(sysUserRoleEntities);
        }
    }

    /**
     * 删除原有的重新绑定角色
     *
     * @param userId
     * @param roleIds
     * @param projectId
     * @return void
     * @author liuke
     * @date 2021/4/8 14:13
     */
    @Override
    public void rebindRole(Long userId, Set<Long> roleIds, Long projectId) {
        sysUserRoleService.deleteByUserId(userId);
        bindRole(userId, roleIds, projectId);
    }

    /**
     * 根据用户ID获取用户基本信息
     *
     * @param userId
     * @return SysUserDetailVo
     * @author liuke
     * @date 2021/4/19 16:49
     */
    @Override
    public SysUserDetailVo getUserDetails(Long userId) {
        SysUserEntity sysUserEntity = this.get(userId);
        if (sysUserEntity == null) {
            return null;
        }
        //设置用户基本信息
        SysUserDetailVo sysUserDetailVo = UtilBean.copyBean(sysUserEntity, SysUserDetailVo.class, "password");
        //设置角色资源信息
        sysUserDetailVo.setSysRoleAndResourceVos(sysRoleService.getRoleAndResourceByUser(userId));
        //设置岗位信息
        sysUserDetailVo.setSysPostEntities(sysPostService.getPostByUser(userId));
        //设置关联用户信息
        //
        // sysUserDetailVo.setPfApplicationEntities(sysUserApplicationService.getAppByUser(userId));
        //设置用户组织信息
        sysUserDetailVo.setSysOrgEntity(sysOrgService.getOrgByUserId(userId));
        return sysUserDetailVo;
    }

    /**
     * 根据userName获取用户基本信息
     *
     * @param searchParam
     * @return SysUserDetailVo
     * @author liuke
     * @date 2021/4/19 16:49
     */
    @Override
    public SysUserDetailVo getAuthUserDetails(Map<String, Object> searchParam) {

        if(searchParam.get("wxOpenId")==null&&searchParam.get("userName")==null&&searchParam.get("telephone")==null&&searchParam.get("idCard")==null&&searchParam.get("email")==null&&searchParam.get("id")==null){
            return null;
        }
        SysUserEntity sysUserEntity = entityDao.queryUserByParams(searchParam);
        if (sysUserEntity == null) {
            return null;
        }
        Long userId = sysUserEntity.getId();
        //设置用户基本信息
        SysUserDetailVo sysUserDetailVo = UtilBean.copyBean(sysUserEntity, SysUserDetailVo.class);
        //设置角色资源信息
        sysUserDetailVo.setSysRoleAndResourceVos(sysRoleService.getRoleAndResourceByUser(userId));
        //设置用户组织信息
        sysUserDetailVo.setSysOrgEntity(sysOrgService.getOrgByUserId(userId));
        return sysUserDetailVo;
    }

    /**
     * 根据userName获取多个用户基本信息
     *
     * @param searchParam
     * @return SysUserDetailVo
     * @author liuke
     * @date 2021/4/19 16:49
     */
    @Override
    public SysUserDetailVo getMoreAuthUserDetails(Map<String, Object> searchParam) {

        if(searchParam.get("wxOpenId")==null&&searchParam.get("userName")==null&&searchParam.get("telephone")==null&&searchParam.get("idCard")==null&&searchParam.get("email")==null&&searchParam.get("id")==null){
            return null;
        }
        SysUserEntity sysUserEntity = null;
        //查询符合条件的所有用户
        List<SysUserEntity> sysUserEntities = entityDao.queryMoreUserByParams(searchParam);
        if (UtilCollection.sizeIsEmpty(sysUserEntities)) {
            return null;
        }else {
            //如果只有一个用户，直接返回，不做处理
            if(sysUserEntities.size()==1){
                sysUserEntity = sysUserEntities.get(0);
                //如果多个用户，筛选出符合条件的用户
            }else {
                for (SysUserEntity userEntity : sysUserEntities) {
                    //查询当前用户的角色
                    List<SysRoleEntity> roles = sysRoleService.queryRoleListByUserId(userEntity.getId());
                    //查询当前用户是否有用户权限中心角色
                    for (SysRoleEntity role : roles) {
                        if(role.getRoleType().equals(RoleType.ADMIN)||role.getRoleType().equals(RoleType.superadmin)){
                            sysUserEntity = userEntity;
                        }
                    }
                }
            }
        }
        if(sysUserEntity==null){
            return null;
        }
        Long userId = sysUserEntity.getId();
        //设置用户基本信息
        SysUserDetailVo sysUserDetailVo = UtilBean.copyBean(sysUserEntity, SysUserDetailVo.class);
        //设置角色资源信息
        sysUserDetailVo.setSysRoleAndResourceVos(sysRoleService.getRoleAndResourceByUser(userId));
        //设置用户组织信息
        sysUserDetailVo.setSysOrgEntity(sysOrgService.getOrgByUserId(userId));
        return sysUserDetailVo;
    }

    /**
     * 遍历获取组织下所有用户
     *
     * @param orgId
     * @return java.util.List<SysUserEntity>
     * @author liuke
     * @date 2021/4/22 11:22
     */
    @Override
    public List<SysUserEntity> getSysUserByOrgId(Long orgId) {
        //获取用户所有子组织
        Set<Long> orgIds = sysOrgService.getOrgIds(orgId);
        if (UtilCollection.sizeIsEmpty(orgIds)) {
            return Lists.newArrayList();
        }
        Map<String, Object> searchParam = Maps.newHashMap();
        searchParam.put("orgIds", orgIds);
        return this.queryAll(searchParam);

    }

    /**
     * 获取当前组织下用户
     *
     * @param orgId
     * @return java.util.List<SysUserEntity>
     * @author liuke
     * @date 2021/4/22 11:22
     */
    @Override
    public List<ReturnTreeData> getCuurentUserByOrgId(Long orgId) {
        Map<String, Object> searchParam = Maps.newHashMap();
        searchParam.put("orgId", orgId);
        searchParam.put("status", UserStatus.VALID);
        List<SysUserEntity> sysUserEntities = queryAll(searchParam);
        List<ReturnTreeData> returnTreeDataList = Lists.newArrayList();
        for (SysUserEntity sysUserEntity : sysUserEntities) {
            returnTreeDataList.add(new ReturnTreeData(sysUserEntity.getId(), "", sysUserEntity.getRealName(), "user", sysUserEntity.getHeadImgUrl(), null));
        }
        return returnTreeDataList;
    }

    /**
     * 遍历获取组织下用户数量
     *
     * @param orgId
     * @return int
     * @author liuke
     * @date 2021/4/22 11:22
     */
    @Override
    public Long getSysUserCountByOrgId(Long orgId) {
        Set<Long> orgIds = sysOrgService.getOrgIds(orgId);
        if (UtilCollection.sizeIsEmpty(orgIds)) {
            return 0L;
        }
        Map<String, Object> searchParam = Maps.newHashMap();
        searchParam.put("orgIds", orgIds);
        return this.count(searchParam);

    }

    @Override
    public void preSaveHandler(SysUserEntity entity) {
        super.preSaveHandler(entity);
        //保存前将密码进行加密
        if (UtilString.isNotBlank(entity.getPassword())) {
            entity.setPassword(encodPassword(entity.getPassword()));
        }
        //生成hash值
        if (UtilString.isNotBlank(entity.getIdCard())) {
            entity.setHash(getUserNameIDHash(entity.getIdCard()));
        }

    }

    @Override
    public void preUpdateHandler(SysUserEntity entity, Collection<String> updateFields) {
        super.preUpdateHandler(entity, updateFields);
        if (updateFields.contains("password")) {
            //更新前将密码进行加密
            entity.setPassword(this.encodPassword(entity.getPassword()));
        }
        if(UtilString.isNotBlank(entity.getIdCard())&&(entity.getIdCard().length()==18||entity.getIdCard().length()==15)){
            try {
                entity.setHash(getUserNameIDHash(entity.getIdCard()));
                updateFields.add("hash");
                entity.setIdCard(PassWordUtil.encrypt(entity.getIdCard()));
            } catch (Exception e) {
                entity.setIdCard("");
            }
        }
    }

    /**
     * 更新操作后删除redis
     *
     * @param entity
     * @param updateFields
     * @return void
     * @author liuke
     * @date 2021/12/30 10:21
     */
    @Override
    public void postUpdateHandler(SysUserEntity entity, Collection<String> updateFields) {
        super.postUpdateHandler(entity, updateFields);
        //更新时删除根据hash删除的缓存
        if (UtilString.isBlank(entity.getHash())) {
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_INFO + get(entity.getId()).getHash());
        } else {
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_INFO + entity.getHash());
        }
        //更新时根据项目编码和用户名删除缓存
        if (UtilString.isBlank(entity.getProjectCode()) || UtilString.isBlank(entity.getUserName())) {
            SysUserEntity sysUserEntity = get(entity.getId());
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_USERNAME_INFO + sysUserEntity.getProjectCode() + ":" + sysUserEntity.getUserName());
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_USERNAME_INFO + ":" + sysUserEntity.getUserName());
        } else {
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_USERNAME_INFO + entity.getProjectCode() + ":" + entity.getUserName());
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_USERNAME_INFO + ":" + entity.getUserName());
        }

        //更新时根据项目编码和身份证号删除缓存
        if (UtilString.isBlank(entity.getProjectCode()) || UtilString.isBlank(entity.getUserName())) {
            SysUserEntity sysUserEntity = get(entity.getId());
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_IDCARD_INFO + sysUserEntity.getProjectCode() + ":" + sysUserEntity.getUserName());
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_IDCARD_INFO + ":" + sysUserEntity.getUserName());
        } else {
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_IDCARD_INFO + entity.getProjectCode() + ":" + entity.getUserName());
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_IDCARD_INFO + ":" + entity.getUserName());
        }

        //更新时根据项目编码和手机号删除缓存
        if (UtilString.isBlank(entity.getProjectCode()) || UtilString.isBlank(entity.getUserName())) {
            SysUserEntity sysUserEntity = get(entity.getId());
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_TELEPHONE_INFO + sysUserEntity.getProjectCode() + ":" + sysUserEntity.getUserName());
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_TELEPHONE_INFO + ":" + sysUserEntity.getUserName());
        } else {
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_TELEPHONE_INFO + entity.getProjectCode() + ":" + entity.getUserName());
            stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_TELEPHONE_INFO + ":" + entity.getUserName());
        }
        //根据用户id删除缓存
        stringRedisTemplate.delete(RedisConstant.SYSTEM_USER_USERID_INFO + entity.getId());
    }

    /**
     * 获取hash
     *
     * @param idCard 身份证
     * @return
     */
    public static String getUserNameIDHash(String idCard) {
        StringBuffer userInfo = new StringBuffer();
        userInfo.append(idCard);
        return sha1IdAndName(userInfo.toString());
    }

    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     */
    public static String sha1IdAndName(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] result = digest.digest(input.getBytes());

            return Base64.getEncoder().encodeToString(result);
        } catch (GeneralSecurityException e) {
            throw new AppException("生成hash错误");
        }
    }

    /**
     * 密码加密
     *
     * @param passowrd
     * @return java.lang.String
     * @author liuke
     * @date 2021/4/28 9:47
     */
    @Override
    public String encodPassword(String passowrd) {
        if (passowrd == null) {
            return null;
        }
        try {
            //判断是否为加密后密码
            passwordEncoder.matches(passowrd,passowrd);
            return passowrd;
        }catch (Exception e){
            return passwordEncoder.encode(passowrd);
        }
    }

    /**
     * 用户激活
     *
     * @param sysUserDto
     * @return void
     * @author liuke
     * @date 2021/9/22 14:45
     */
    @Override
    public SysUserEntity activeByPhone(SysUserDto sysUserDto) {
        //基本信息校验
        basicVerify(sysUserDto);

        if (sysUserDto.getId() == null) {
            throw new AppException("用户id不能为空");
        }
        //根据手机号查询用户
        List<SysUserEntity> sysUserEntities = this.queryAll(new HashMap<String, Object>() {{
            put("telephone", sysUserDto.getTelephone());
        }});
        //判断是否已经存在账号
        if (UtilCollection.isNotEmpty(sysUserEntities) && sysUserEntities.get(0).getAuthStatus() == AuthStatus.AUTH) {
            throw new AppException("该手机号已经被注册");
        }

        SysUserEntity sysUser = this.get(sysUserDto.getId());

        if (sysUser == null) {
            throw new AppException("获取用户信息失败");
        }

        if (sysUser.getAuthStatus() == AuthStatus.AUTH) {
            throw new AppException("你的账号已经注册激活，请前往登陆");
        }

        verifyMessageCode(sysUserDto.getTelephone(), sysUserDto.getMessageCode());

        sysUser.setAuthStatus(AuthStatus.AUTH);
        sysUser.setTelephone(sysUserDto.getTelephone());
        sysUser.setPassword(sysUserDto.getPassword());
        this.update(sysUser, Sets.newHashSet("authStatus", "telephone", "password"));
        return sysUser;
    }

    /**
     * 用户信息校验
     *
     * @param realName
     * @param idCard
     * @return
     */
    @Override
    public String verify(String realName, String idCard) {
        List<SysUserEntity> sysUserEntities = this.queryAll(new HashMap<String, Object>() {{
            put("realName", realName);
            put("idCard", idCard);
        }});
        if (UtilCollection.sizeIsEmpty(sysUserEntities)) {
            return null;
        }
        return sysUserEntities.get(0).getId().toString();
    }

    /**
     * 重置密码
     *
     * @param sysUserDto
     * @return boolean
     * @author liuke
     * @date 2021/9/22 16:31
     */
    @Override
    public boolean resetPassword(SysUserDto sysUserDto) {
        //检查预留手机号是否正确
        List<SysUserEntity> sysUserEntities = this.queryAll(new HashMap<String, Object>() {{
            put("telephone", sysUserDto.getTelephone());
        }});
        if (UtilCollection.sizeIsEmpty(sysUserEntities)) {
            throw new AppException("该手机号不存在");
        }
        //检查用户是否注册
        if (sysUserEntities.get(0).getAuthStatus() == AuthStatus.NOT_AUTH) {
            throw new AppException("该用户还未认证，请先认证");
        }
        //基础信息项校验
        basicVerify(sysUserDto);
        //校验手机验证码
        verifyMessageCode(sysUserDto.getTelephone(), sysUserDto.getMessageCode());
        //更新密码
        SysUserEntity sysUserEntity = sysUserEntities.get(0);
        sysUserEntity.setPassword(sysUserDto.getPassword());
        sysUserEntity.setLastUpdateUserId(super.getLoginUserId());
        this.update(sysUserEntity, Sets.newHashSet("password", "lastUpdateUserId"));
        return true;
    }

    /**
     * 管理端更新密码接口
     *
     * @param sysUserDto
     * @return boolean
     * @author liuke
     * @date 2021/9/22 16:50
     */
    @Override
    public boolean manageResetPassword(SysUserDto sysUserDto) {

        if (sysUserDto.getOriginPassword() == null) {
            throw new AppException("原密码不能为空");
        }
        if (UtilString.isBlank(sysUserDto.getPassword())) {
            throw new AppException("密码不能为空");
        }
        if (!UtilString.equals(sysUserDto.getPassword(), sysUserDto.getSurePassword())) {
            throw new AppException("两次密码不一致");
        }
        if (UtilString.equals(sysUserDto.getPassword(), sysUserDto.getOriginPassword())) {
            throw new AppException("新密码不能和原密码相同");
        }

        SysUserEntity sysUserEntity = this.get(sysUserDto.getId());
        if (!passwordEncoder.matches(sysUserDto.getOriginPassword(), sysUserEntity.getPassword())) {
            throw new AppException("原密码错误");
        }

        sysUserEntity.setPassword(sysUserDto.getPassword());
        sysUserEntity.setLastUpdateUserId(super.getLoginUserId());
        this.update(sysUserEntity, Sets.newHashSet("password", "lastUpdateUserId"));
        return true;
    }

    /**
     * 更新用户手机号
     *
     * @param phone
     * @param userId
     * @param code
     * @return SysUser
     * @author liuke
     * @date 2021/9/22 17:10
     */
    @Override
    public SysUserEntity updatePhone(String phone, Long userId, String code) {
        if (null == userId) {
            throw new AppException("用户id不能为空");
        }

        verifyPhone(phone);

        SysUserEntity sysUserEntity = this.get(userId);
        if (sysUserEntity == null || sysUserEntity.getStatus() == UserStatus.INVALID) {
            throw new AppException("该用户不存在或者已被禁用");
        }
        List<SysUserEntity> sysUserEntities = this.queryAll(new HashMap<String, Object>() {{
            put("telephone", phone);
        }});
        if (!UtilCollection.sizeIsEmpty(sysUserEntities) && sysUserEntities.get(0).getAuthStatus() == AuthStatus.AUTH && !sysUserEntities.get(0).getId().equals(sysUserEntity.getId())) {
            throw new AppException("该手机号已经被注册");
        }
        verifyMessageCode(phone, code);
        //更新注册手机号
        sysUserEntity.setTelephone(phone);
        this.update(sysUserEntity, Sets.newHashSet("telephone"));

        return sysUserEntity;
    }


    /**
     * 校验密码与手机号
     *
     * @return boolean
     * @author liuke
     * @date 2021/9/22 14:37
     */
    public boolean basicVerify(SysUserDto sysUserDto) {
        if (UtilString.isBlank(sysUserDto.getPassword())) {
            throw new AppException("密码不能为空");
        }
        if (!UtilString.equals(sysUserDto.getPassword(), sysUserDto.getSurePassword())) {
            throw new AppException("两次密码不一致");
        }
        verifyPhone(sysUserDto.getTelephone());
        return true;
    }

    /**
     * 校验手机号 为空 非法
     *
     * @param phone
     * @return void
     * @author liuke
     * @date 2021/9/22 14:42
     */
    private void verifyPhone(String phone) {
        if (UtilString.isBlank(phone)) {
            throw new AppException("手机号不能为空");
        }
        if (!UtilString.isValidPhone(phone)) {
            throw new AppException("手机号格式非法");
        }
    }

    /**
     * 校验手机短信验证码是否正确
     *
     * @param phone
     * @param code
     * @return
     */
    public boolean verifyMessageCode(String phone, String code) {
        if (UtilString.isBlank(code)) {
            throw new AppException("手机验证码不能为空");
        }
        String cacheMessageCode = stringRedisTemplate.opsForValue().get(Constant.SSO_PREFIX_MESSAGE_CODE + phone);
        if (UtilString.equals(cacheMessageCode, code)) {
            return true;
        } else {
            throw new AppException("短信验证码错误");
        }
    }

    /**
     * 查询全部人员
     *
     * @param searchParams
     * @return java.util.List<com.fosung.system.support.system.entity.sys.ReturnTreeData>
     * @author liuke
     * @date 2021/11/5 9:58
     */
    @Override
    public List<ReturnTreeData> queryUser(Map<String, Object> searchParams) {
        List<ReturnTreeData> data = Lists.newArrayList();
        Map<String, Object> searchParam = Maps.newHashMap();

        List<SysUserEntity> users = this.queryAll(searchParam);
        users.stream().forEach(user -> {
            ReturnTreeData returnTree = new ReturnTreeData();
            returnTree.setId(user.getId());
            returnTree.setCode(user.getSex());
            returnTree.setName(user.getUserName());
            returnTree.setType("1");
            returnTree.setImg(user.getHeadImgUrl());
            data.add(returnTree);
        });
        return data;

    }

    /**
     * 描述: 保存用户信息
     *
     * @param sysUser
     * @return void
     * @author fuhao
     * @date 2021/12/1 9:43
     **/
    @Override
    public void saveInfo(SysUserEntity sysUser) throws Exception {
        // 管理范围
        List<SysUserRoleEntity> roles = sysUser.getRoles();
        //id不为空，进行更新操作，否则进行添加
        if (sysUser.getId() != null) {
            //由请求参数中获取需要更新的字段
            Set<String> updateFields = UtilDTO.toDTOExcludeFields(sysUser, Arrays.asList("id")).keySet();
            //updateFields.remove("idCard");
            //updateFields.remove("telephone");
            updateFields.remove("shelvesType");
            updateFields.remove("source");
            updateFields.remove("password");
            // 按照字段更新对象
            this.update(sysUser, updateFields);
            // 保存用户角色关联表
            saveRoles(sysUser, roles);
            // 保存用户岗位关联关系,默认为群众
            String identity = "PEOPLE";
            if (sysUser.getPosts() != null) {
                delUserPostScope(sysUser);
                addBatchUserPostScope(sysUser);

                for (SysUserPostScopeEntity post : sysUser.getPosts()) {
                    if(post.getIdentify()!=null&&UtilString.equals("CADRE",post.getIdentify())){
                        identity = "CADRE";
                    }
                }
            }
            // 发送mq消息
            SysUserEntity sysUserEntity = this.get(sysUser.getId());
            sysUserEntity.setIdentityId(identity);
            JSONObject jsonObject = sysMQProducerService.buildSysUserMessage(sysUserEntity, SysMqUserTypeConstant.updateUser.name());
            sysMQProducerService.sendMQMessage(SysMqUserTypeConstant.updateUser.name(), jsonObject, MQMessageConstant.USER_TAG);
        } else {
            sysUser.setDel(Boolean.FALSE);
            sysUser.setShelvesType(ShelvesType.role);
            if(UtilCollection.isNotEmpty(roles)){
                sysUser.setAuthStatus(AuthStatus.AUTH);
                sysUser.setAuthTime(new Date());
            }else {
                sysUser.setAuthStatus(AuthStatus.NOT_AUTH);
            }
            sysUser.setSource("用户权限中心");
            sysUser.setIdCard(PassWordUtil.encrypt(sysUser.getIdCard()));
            // 保存用户信息
            // 保存用户岗位关联关系,默认为群众
            String identity = "PEOPLE";
            if (sysUser.getPosts() != null) {
                for (SysUserPostScopeEntity post : sysUser.getPosts()) {
                    if(post.getIdentify()!=null&&UtilString.equals("CADRE",post.getIdentify())){
                        identity = "CADRE";
                    }
                }
            }
            sysUser.setIdentityId(identity);
            SysUserEntity user = this.save(sysUser);
            // 保存用户角色关联表
            if(!UtilCollection.sizeIsEmpty(roles)){
                saveRoles(user, roles);
            }
            // 保存用户岗位关联关系
            if (sysUser.getPosts() != null) {
                delUserPostScope(user);
                addBatchUserPostScope(user);
            }
        }

    }

    /**
     * 描述: 批量新增用户任职组织关联信息
     *
     * @param sysUser
     * @return void
     * @author fuhao
     * @date 2022/2/14 15:22
     **/
    public void addBatchUserPostScope(SysUserEntity sysUser) {
        // 绑定职务
        List<SysUserPostScopeEntity> posts = sysUser.getPosts();
        List<SysUserPostScopeEntity> postInfo = Lists.newArrayList();
        posts.forEach(post -> {
            post.setUserId(sysUser.getId());
            post.setProjectId(sysUser.getProjectId());
            SysUserPostScopeEntity sysUserPostScopeEntity = UtilBean.copyBean(post, SysUserPostScopeEntity.class);
            sysUserPostScopeEntity.setId(null);
            postInfo.add(sysUserPostScopeEntity);
        });
        sysUserPostScopeService.saveBatch(postInfo);
    }

    /**
     * 描述: 删除用户下任职组织关联信息
     *
     * @param sysUser
     * @return void
     * @author fuhao
     * @date 2022/2/14 15:22
     **/
    public void delUserPostScope(SysUserEntity sysUser) {
        HashMap<String, Object> searchParam = Maps.newHashMap();
        searchParam.put("userId", sysUser.getId());
        searchParam.put("projectId", sysUser.getProjectId());
        List<SysUserPostScopeEntity> sysUserPostEntityList = sysUserPostScopeService.queryAll(searchParam);
        if (!UtilCollection.sizeIsEmpty(sysUserPostEntityList)) {
            Set<Long> ids = UtilCollection.extractToSet(sysUserPostEntityList, "id", Long.class);
            sysUserPostScopeService.delete(ids);
        }
    }

    /**
     * 描述: 保存角色信息
     *
     * @param user
     * @param roles
     * @return void
     * @author fuhao
     * @date 2022/1/5 11:03
     **/
    public void saveRoles(SysUserEntity user, List<SysUserRoleEntity> roles) {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("userId",user.getId());
        List<SysUserRoleEntity> sysUserRoleEntities = sysUserRoleService.queryAll(searchParam);
        Set<Long> roleIds = roles.stream().map(SysUserRoleEntity::getRoleId).collect(Collectors.toSet());
        Set<Long> newRoleIds = Sets.newHashSet();
        if(!UtilCollection.sizeIsEmpty(sysUserRoleEntities)){
            List<Long> sysRoleIds = sysUserRoleEntities.stream().map(SysUserRoleEntity::getRoleId).collect(Collectors.toList());
            roles = roles.stream().filter(map->!sysRoleIds.contains(map.getRoleId())).collect(Collectors.toList());

            List<Long> removeIds = sysRoleIds.stream().filter(aLong -> !roleIds.contains(aLong)).collect(Collectors.toList());
            if(!UtilCollection.sizeIsEmpty(removeIds)){

                sysUserRoleService.delete(sysUserRoleEntities.stream().filter(map -> !roleIds.contains(map.getRoleId())).map(SysUserRoleEntity::getId).collect(Collectors.toList()));
                newRoleIds = roles.stream().map(SysUserRoleEntity::getRoleId).collect(Collectors.toSet());

                HashMap<String, Object> searchBindParam = Maps.newHashMap();
                searchBindParam.put("userId", user.getId());
                searchBindParam.put("roleIds", removeIds);
                sysUserRoleScopService.deleteUserRoleScope(searchBindParam);
            }

        }
        // 先重新绑定用户角色表关系
        //rebindRole(user.getId(),roleIds,user.getProjectId());
        bindRole(user.getId(), newRoleIds, user.getProjectId());
//        HashMap<String, Object> searchParam = Maps.newHashMap();
//        searchParam.put("userId", user.getId());
//        List<SysUserRoleEntity> sysUserRoleExist = sysUserRoleService.queryAll(searchParam);
//        // 存在则删除
//        if (sysUserRoleExist != null || sysUserRoleExist.size() > 0) {
//            //执行删除
//            sysUserRoleService.delete(sysUserRoleExist.stream().map(map -> map.getId()).collect(Collectors.toList()));
//        }
        if (roles != null) {

            // 保存角色关联关系
            roles.forEach(role -> {
                role.setUserId(user.getId());
                role.setProjectId(user.getProjectId());
                // 默认添加一个角色管理范围
                if (RoleType.ADMIN.equals(role.getRoleType())) {
                    SysUserRoleScopEntity sysUserRoleScop = new SysUserRoleScopEntity();
                    sysUserRoleScop.setOrgId(user.getOrgId());
                    sysUserRoleScop.setProjectId(user.getProjectId());
                    sysUserRoleScop.setUserId(user.getId());
                    sysUserRoleScop.setRoleId(role.getRoleId());
                    sysUserRoleScopService.save(sysUserRoleScop);
                }
            });
            sysUserRoleService.saveBatch(roles);

        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
    }

    public List<SysRoleScopEntity> checkSuperAdmin(SysUserRoleScopDto sysRoleDto) {
        // 查询全部的角色
        HashMap<String, Object> searchParam = Maps.newHashMap();
        SysRoleEntity sysRoleEntity = sysRoleService.get(sysRoleDto.getRoleId());

        if (RoleType.superadmin.name().equals(sysRoleEntity.getRoleType().name())) {
            searchParam.put("projectId", sysRoleDto.getProjectId());
            List<SysRoleEntity> sysRoleEntities = sysRoleService.queryAll(searchParam);
            // 返回集合
            List<SysRoleScopEntity> allSysRoleScop = new ArrayList<>();
            sysRoleEntities.forEach(roles -> {
                if(!StringUtils.equals(roles.getRoleType().name(),RoleType.superadmin.name())){
                    SysRoleScopEntity sysRoleScop = new SysRoleScopEntity();
                    sysRoleScop.setId(roles.getId());
                    sysRoleScop.setRoleScopId(roles.getId());
                    sysRoleScop.setProjectId(roles.getProjectId());
                    sysRoleScop.setRoleName(roles.getRoleName());
                    sysRoleScop.setRoleType(roles.getRoleType().name());
                    allSysRoleScop.add(sysRoleScop);
                }
            });
            return allSysRoleScop;
        }
        return null;
    }


    /**
     * 描述: 获取当前登陆人角色下管理的角色范围
     *
     * @param sysRoleDto
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysRoleScopEntity>
     * @author fuhao
     * @date 2021/12/2 16:56
     **/
    @Override
    public List<Map<String, Object>> queryUserRoleScope(SysUserRoleScopDto sysRoleDto) {
        // 校验是不是超级管理员
        List<SysRoleScopEntity> allSysRoleScop = checkSuperAdmin(sysRoleDto);
        if (allSysRoleScop == null) {
            HashMap<String, Object> searchParam = Maps.newHashMap();
            searchParam.put("roleId", sysRoleDto.getRoleId());
            allSysRoleScop = entityDao.queryAllSysRoleScop(searchParam);
        }
        //根据应用id筛选
        if(sysRoleDto.getAppId()!=null){
            HashMap<String, Object> searchParam = Maps.newHashMap();
            searchParam.put("projectId",sysRoleDto.getProjectId());
            searchParam.put("appId",sysRoleDto.getAppId());
            List<SysRoleAppEntity> lists = sysRoleAppService.queryAll(searchParam);
            if(!UtilCollection.sizeIsEmpty(lists)){
                List<Long> ids = lists.stream().map(SysRoleAppEntity::getRoleId).collect(Collectors.toList());
                allSysRoleScop.forEach(map ->{
                    if(ids.contains(map.getRoleId())){
                        map.setNum(1);
                    }
                });
            }
            //将复合
            allSysRoleScop.stream().sorted(Comparator.comparing(SysRoleScopEntity::getNum));
        }
        // 查询角色下已经绑定的应用
        if (sysRoleDto.getUserId() != null) {
            HashMap<String, Object> queryUserRole = Maps.newHashMap();
            queryUserRole.put("userId", sysRoleDto.getUserId());
            List<SysUserRoleEntity> bindSysUserRole = sysUserRoleService.queryAll(queryUserRole);
            for (SysRoleScopEntity allRoleScope : allSysRoleScop) {
                // 获取角色下已经绑定的资源
                List<Map<String, Object>> sysUserResourceResDtos = queryRoleBindResources(allRoleScope.getRoleScopId());
                //List<Map<String, Object>> sysUserResourceResDto = UtilDTO.toDTO(sysUserResourceResDtos, (DTOCallbackHandler) null);
                allRoleScope.setRoleBindResources(sysUserResourceResDtos);
                if (bindSysUserRole == null || bindSysUserRole.size() < 1) allRoleScope.setCheckFlag(false);
                for (SysUserRoleEntity bindRoleScope : bindSysUserRole) {
                    if (Long.toString(allRoleScope.getRoleScopId()).equals(Long.toString(bindRoleScope.getRoleId()))) {
                        allRoleScope.setCheckFlag(true);
                        if (sysRoleDto.getShelvesType() != null) {
                            allRoleScope.setOrgs(queryBindScope(sysRoleDto.getUserId(), bindRoleScope.getRoleId(), sysRoleDto.getShelvesType().name()));
                            allRoleScope.setResources(queryBindResource(sysRoleDto.getUserId(), bindRoleScope.getRoleId(), sysRoleDto.getShelvesType().name()));
                        }
                        break;
                    } else {
                        allRoleScope.setCheckFlag(false);
                    }
                }
            }
        } else {
            allSysRoleScop.forEach(roleScope -> {
                roleScope.setCheckFlag(false);
            });
        }
        List<Map<String, Object>> resultLists = UtilDTO.toDTO(allSysRoleScop, (DTOCallbackHandler) null);
        return resultLists;
    }

    /**
     *
     * 查询用户角色
     *
     * @author liuke
     * @date  2022/6/9 10:22
     * @version
    */
    @Override
    public List<Map<String, Object>> queryOnlyUserRoleScope(SysUserRoleScopDto sysRoleDto) {
        // 校验是不是超级管理员
        List<SysRoleScopEntity> allSysRoleScop = checkSuperAdmin(sysRoleDto);
        if (allSysRoleScop == null) {
            HashMap<String, Object> searchParam = Maps.newHashMap();
            searchParam.put("roleId", sysRoleDto.getRoleId());
            allSysRoleScop = entityDao.queryAllSysRoleScop(searchParam);
        }
        List<Map<String, Object>> resultLists = UtilDTO.toDTO(allSysRoleScop,Sets.newHashSet("roleScopId","roleName"),null,null);
        return resultLists;
    }

    /**
     * 描述: 获取角色下绑定的资源（不可编辑）
     *
     * @param roleId
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysRoleResourceEntity>
     * @author fuhao
     * @date 2021/12/20 11:37
     **/
    public List<Map<String, Object>> queryRoleBindResources(Long roleId) {
        // 定义返回集合
        ArrayList<SysUserResourceResDto> result = new ArrayList<>();
        SysRoleResourceDto dto = new SysRoleResourceDto();
        dto.setRoleId(roleId);
        Set<Long> appIds = Sets.newHashSet();
        List<SysRoleAppEntity> sysRoleApp = Lists.newArrayList();
        List<SysRoleResourceEntity> sysRoleResource = sysRoleService.queryBindRolePower(dto);
        if (UtilCollection.sizeIsEmpty(sysRoleResource)) {
            sysRoleApp = sysRoleService.queryRoleBindApp(dto);
            appIds = sysRoleApp.stream().map(map -> map.getAppId()).collect(Collectors.toSet());
        } else {
            appIds = sysRoleResource.stream().map(map -> map.getAppId()).collect(Collectors.toSet());
        }

        for (Long appId : appIds) {
            ArrayList<SysRoleResourceEntity> resourcesResult = new ArrayList<>();
            SysUserResourceResDto sysUserResourceResDto = new SysUserResourceResDto();
            if (UtilCollection.isNotEmpty(sysRoleResource)) {
                sysRoleResource.forEach(resources -> {
                    if (Long.toString(appId).equals(Long.toString(resources.getAppId()))) {
                        sysUserResourceResDto.setResourceId(appId);
                        sysUserResourceResDto.setMenuName(resources.getAppName());
                        sysUserResourceResDto.setAppType(resources.getAppType());
                        sysUserResourceResDto.setAppType_dict(resources.getAppType().getRemark());
                        resourcesResult.add(resources);
                    }
                    sysUserResourceResDto.setRoleResources(resourcesResult);
                });
            } else {
                for (SysRoleAppEntity roleApp : sysRoleApp) {
                    if (Long.toString(appId).equals(Long.toString(roleApp.getAppId()))) {
                        sysUserResourceResDto.setResourceId(appId);
                        sysUserResourceResDto.setMenuName(roleApp.getAppName());
                        sysUserResourceResDto.setAppType(roleApp.getAppType());
                        sysUserResourceResDto.setAppType_dict(roleApp.getAppType().getRemark());
//                        SysRoleResourceEntity sysRoleResourceEntity = new SysRoleResourceEntity();
//                        sysRoleResourceEntity.setResourceId(appId);
//                        resourcesResult.add(sysRoleResourceEntity);
                    }
                }

            }
            result.add(sysUserResourceResDto);
        }
        List<Map<String, Object>> maps = UtilDTO.toDTO(result, (DTOCallbackHandler) null);
        return maps;
    }

    /**
     * 描述: 查询资源授权绑定角色
     *
     * @param sysRoleDto
     * @return com.fosung.system.support.system.entity.sys.SysRoleScopEntity
     * @author fuhao
     * @date 2021/12/20 9:42
     **/
    @Override
    public SysRoleScopEntity queryUserResourceScope(SysUserRoleScopDto sysRoleDto) {
        SysRoleScopEntity sysRoleScopEntity = new SysRoleScopEntity();
        // 获取用户下的资源
        sysRoleScopEntity.setResources(queryBindResource(sysRoleDto.getUserId(), null, sysRoleDto.getShelvesType().name()));
        // 获取用户下的管理范围
        sysRoleScopEntity.setOrgs(queryBindScope(sysRoleDto.getUserId(), null, sysRoleDto.getShelvesType().name()));
        return sysRoleScopEntity;
    }

    /**
     * 描述: 绑定资源
     *
     * @param userId
     * @param roleId
     * @param shelvesType
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserResourceEntity>
     * @author fuhao
     * @date 2021/12/7 19:05
     **/
    public List<Map<String, Object>> queryBindResource(Long userId, Long roleId, String shelvesType) {
        // 定义返回集合
        ArrayList<SysUserResourceResDto> result = new ArrayList<>();
        Map<String, Object> searchParam = Maps.newHashMap();
        searchParam.put("roleId", roleId);
        searchParam.put("shelvesType", shelvesType);
        searchParam.put("userId", userId);
        List<SysUserResourceEntity> sysUserResources = entityDao.queryBindResource(searchParam);
        if (UtilCollection.sizeIsEmpty(sysUserResources)) {
            sysUserResources.addAll(entityDao.queryBindApp(searchParam));
        }
        Set<Long> appIds = sysUserResources.stream().map(map -> map.getAppId()).collect(Collectors.toSet());
        for (Long appId : appIds) {
            ArrayList<SysUserResourceEntity> resourcesResult = new ArrayList<>();
            SysUserResourceResDto sysUserResourceResDto = new SysUserResourceResDto();
            for (SysUserResourceEntity resources : sysUserResources) {
                if (Long.toString(appId).equals(Long.toString(resources.getAppId()))) {
                    sysUserResourceResDto.setResourceId(appId);
                    sysUserResourceResDto.setMenuName(resources.getAppName());
                    sysUserResourceResDto.setAppType(resources.getAppType());
                    resourcesResult.add(resources);
                }
                sysUserResourceResDto.setUserResources(resourcesResult);
            }

            result.add(sysUserResourceResDto);
        }
        List<Map<String, Object>> resultLists = UtilDTO.toDTO(result, (DTOCallbackHandler) null);

        return resultLists;
    }

    /**
     * 描述: 查询绑定的管理范围
     *
     * @param userId
     * @param roleId
     * @param shelvesType
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserRoleScopEntity>
     * @author fuhao
     * @date 2021/12/7 18:36
     **/
    public List<SysUserRoleScopEntity> queryBindScope(Long userId, Long roleId, String shelvesType) {
        HashMap<String, Object> searchBindParam = Maps.newHashMap();
        searchBindParam.put("userId", userId);
        searchBindParam.put("roleId", roleId);
        searchBindParam.put("shelvesType", shelvesType);
        List<SysUserRoleScopEntity> sysBindUserRoleScope = sysUserRoleScopService.queryAllUserRoleScope(searchBindParam);
        return sysBindUserRoleScope;
    }

    /**
     * 描述: 查询授权管理范围
     *
     * @param sysUserRoleScopDto
     * @return List<SysOrgEntity>
     * @author fuhao
     * @date 2021/12/4 13:49
     **/
    @Override
    public List<SysOrgEntity> managerScope(SysUserRoleScopDto sysUserRoleScopDto) {
        // 查询项目下全部的组织
        HashMap<String, Object> searchAllParam = Maps.newHashMap();
        searchAllParam.put("projectId", sysUserRoleScopDto.getProjectId());
        searchAllParam.put("status", OrgStatus.VALID);
        List<SysOrgEntity> sysAllOrg = sysOrgService.queryAll(searchAllParam);
        // 通过用户、角色、查询组织
        List<SysUserRoleScopEntity> sysBindUserRoleScope = queryBindScope(sysUserRoleScopDto.getUserId(), sysUserRoleScopDto.getRoleId(), sysUserRoleScopDto.getShelvesType().name());
        for (SysOrgEntity sysOrg : sysAllOrg) {
            if (UtilCollection.isEmpty(sysBindUserRoleScope)) sysOrg.setCheckedFlag(false);
            for (SysUserRoleScopEntity sysUserRoleScope : sysBindUserRoleScope) {
                if (Long.toString(sysOrg.getId()).equals(Long.toString(sysUserRoleScope.getOrgId()))) {
                    sysOrg.setCheckedFlag(true);
                    break;
                } else {
                    sysOrg.setCheckedFlag(false);
                }
            }
        }
        return sysAllOrg;
    }

    /**
     * 描述: 绑定用户管理范围
     *
     * @param sysUserRoleScopDto
     * @return void
     * @author fuhao
     * @date 2021/12/4 15:41
     **/
    @Override
    public void bindManagerScope(SysUserRoleScopDto sysUserRoleScopDto) {


        // 重新绑定
        List<UserShelvesDto> roles = sysUserRoleScopDto.getRoles();
        if (roles != null) {
            // 删除用户下的范围
            HashMap<String, Object> searchBindParam = Maps.newHashMap();
            searchBindParam.put("userId", sysUserRoleScopDto.getUserId());
            sysUserRoleScopService.deleteUserRoleScope(searchBindParam);
            roles.forEach(role -> {
                List<SysUserRoleScopEntity> scopesNew = Lists.newArrayList();
                List<SysUserRoleScopEntity> scopes = role.getScopes();
                scopes.forEach(scope -> {
                    if (sysUserRoleScopDto.getShelvesType().name().equals(ShelvesType.role.name())) {
                        scope.setRoleId(role.getRoleId());
                    }
                    scope.setProjectId(sysUserRoleScopDto.getProjectId());
                    scope.setUserId(sysUserRoleScopDto.getUserId());
                    SysUserRoleScopEntity sysUserRoleScopEntity = UtilBean.copyBean(scope, SysUserRoleScopEntity.class);
                    scopesNew.add(sysUserRoleScopEntity);
                });
                sysUserRoleScopService.saveBatch(scopesNew);
            });
        }

    }

    /**
     * 根据idCard获取用户认证信息
     *
     * @param idCardHash
     * @param clientId
     * @return com.fosung.framework.web.mvc.config.session.common.AppUserInfo
     * @author liuke
     * @date 2021/12/15 14:17
     */
    @Override
    public AppUserInfo getUserInfo(String idCardHash, String clientId) {
        AppUserInfo appUserInfo = new AppUserInfo();
        Map<String, Object> searchParam = Maps.newHashMap();
        searchParam.put("hash", idCardHash);
        SysUserEntity sysUserDetailVo = getAppInfoDetails(searchParam, "");
        if (sysUserDetailVo != null) {
            appUserInfo.setUserId(sysUserDetailVo.getId().toString());
            appUserInfo.setName(sysUserDetailVo.getRealName());
            appUserInfo.setTelephone(sysUserDetailVo.getTelephone());
            appUserInfo.setLogo(sysUserDetailVo.getHeadImgUrl());
            appUserInfo.setIdCardHash(sysUserDetailVo.getHash());
            appUserInfo.setUsername(sysUserDetailVo.getUserName());
            setAuthoritiesAndRoles(appUserInfo);
        }
        return appUserInfo;
    }

    /**
     * 描述: 查询用户绑定的角色
     *
     * @param userId
     * @return java.util.List<java.lang.String>
     * @author fuhao
     * @date 2021/12/16 14:08
     **/
    @Override
    public List<String> queryUserBindRole(Long userId) {
        Map<String, Object> searchParam = Maps.newHashMap();
        searchParam.put("userId", userId);
        List<String> roles = entityDao.queryUserBindRole(searchParam);
        return roles;
    }

    /**
     * 根据角色编码查询用户
     *
     * @param searchParam
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserEntity>
     * @author liuke
     * @date 2022/1/13 11:38
     */
    @Override
    public List<SysUserEntity> queryUserByRoleCode(Map<String, Object> searchParam) {
        return entityDao.queryUserByRoleCode(searchParam);
    }

    /**
     * 描述: 获取城市下拉框
     *
     * @param
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysCityEntity>
     * @author fuhao
     * @date 2022/1/14 11:16
     **/
    @Override
    public List<SysCityEntity> getCityProvinceInfo() {
        return entityDao.getCityProvinceInfo();
    }

    /**
     * 描述: 通过省查市
     *
     * @param province
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysCityEntity>
     * @author fuhao
     * @date 2022/2/10 15:40
     **/
    @Override
    public List<SysCityEntity> getCityInfoByProvince(String province) {
        return entityDao.getCityInfoByProvince(province);
    }

    /**
     * 描述: 通过市查区县
     *
     * @param code
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysCityEntity>
     * @author fuhao
     * @date 2022/2/10 15:44
     **/
    @Override
    public List<SysCityEntity> getCityInfoByCode(String code) {
        return entityDao.getCityInfoByCode(code);
    }

    /**
     * 描述: 批量授权
     *
     * @param projectId
     * @param file
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2022/1/17 14:23
     **/
    @Override
    public HashSet batchSaveShelves(Long projectId, MultipartFile file) throws Exception {
        // 获取文件输入流
        InputStream inputStream = file.getInputStream();
        UserInfoDataListener listener = new UserInfoDataListener();
        // easyExcel读取文件
        ExcelReader excelReader = EasyExcel.read(inputStream, UserInfoDto.class, listener).build();
        //手动开启事务
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
            // 获取错误信息
            HashSet<String> failInfo = listener.failInfoResult;
            if (UtilCollection.isNotEmpty(failInfo)) {
                return failInfo;
            }
            // 获取合法数据集合
            HashMap<String,List> userInfo = listener.resultList;
            // 校验参数是否在数据库中合法
            HashMap<String, Object> failCheckExist = checkImportParams(userInfo, projectId);
            if (!(boolean) failCheckExist.get("result")) {
                return (HashSet<String>) failCheckExist.get("failInfo");
            }
            // 获取用户、角色信息
            List<SysUserEntity> sysUserEntities = (List<SysUserEntity>) failCheckExist.get("user");
            List<SysRoleEntity> sysRoleEntities = (List<SysRoleEntity>) failCheckExist.get("role");

            Map<String, List<SysUserEntity>> idCardGroup = sysUserEntities.stream().collect(Collectors.groupingBy(SysUserEntity::getIdCard));
            Map<String, List<SysRoleEntity>> roleNameGroup = sysRoleEntities.stream().collect(Collectors.groupingBy(SysRoleEntity::getRoleName));
            // 获取需要添加的人
            Set<String> idCards = userInfo.keySet();
            // 拼装授权信息
            for (String idCard : idCards) {
                SysUserRoleScopDto sysUserRoleScopDto = new SysUserRoleScopDto();
                // 授权类型
                sysUserRoleScopDto.setShelvesType(ShelvesType.role);
                // 租户id
                sysUserRoleScopDto.setProjectId(projectId);
                // 用户id
                List<SysUserEntity> user1 = idCardGroup.get(PassWordUtil.encrypt(idCard));
                // 管理范围
                List<SysUserRoleScopEntity> scopes = Lists.newArrayList();
//                ArrayList<SysUserRoleScopEntity> scopes = Lists.newArrayList();
                SysUserRoleScopEntity sysUserRoleScopEntity = new SysUserRoleScopEntity();
                if (!UtilCollection.sizeIsEmpty(user1)) {
                    sysUserRoleScopDto.setUserId(user1.get(0).getId());
                    sysUserRoleScopEntity.setOrgId(user1.get(0).getOrgId());
                }
                // 授权信息
                ArrayList<UserShelvesDto> roles = Lists.newArrayList();
                List<String> roleInfo = userInfo.get(idCard);
                for (String roleName : roleInfo) {
                    UserShelvesDto userShelvesDto = new UserShelvesDto();
                    List<SysRoleEntity> role1 = roleNameGroup.get(roleName);
                    if (!UtilCollection.sizeIsEmpty(role1)) {
                        userShelvesDto.setRoleId(role1.get(0).getId());
                        scopes.add(sysUserRoleScopEntity);
                        userShelvesDto.setScopes(scopes);
                        userShelvesDto.setResources(Lists.newArrayList());
                        // 拼装授权信息
                        roles.add(userShelvesDto);
                    }
                }
                sysUserRoleScopDto.setRoles(roles);
                // 授权
                saveShelves(sysUserRoleScopDto);
            }
            // 手动提交事务
            dataSourceTransactionManager.commit(transactionStatus);

//            for (UserInfoDto user : list) {
//                // 拼装授权信息
//                SysUserRoleScopDto sysUserRoleScopDto = new SysUserRoleScopDto();
//                // 授权类型
//                sysUserRoleScopDto.setShelvesType(ShelvesType.role);
//                // 租户id
//                sysUserRoleScopDto.setProjectId(projectId);
//                // 用户id
//                List<SysUserEntity> user1 = idCardGroup.get(PassWordUtil.encrypt(user.getIdCard()));
//                // 管理范围
//                ArrayList<SysUserRoleScopEntity> scopes = Lists.newArrayList();
//                SysUserRoleScopEntity sysUserRoleScopEntity = new SysUserRoleScopEntity();
//                if (!UtilCollection.sizeIsEmpty(user1)) {
//                    sysUserRoleScopDto.setUserId(user1.get(0).getId());
//                    sysUserRoleScopEntity.setOrgId(user1.get(0).getOrgId());
//                }
//
//                // 授权信息
//                ArrayList<UserShelvesDto> roles = Lists.newArrayList();
//                UserShelvesDto userShelvesDto = new UserShelvesDto();
//                // 角色id
//                List<SysRoleEntity> role1 = roleNameGroup.get(user.getRoleName());
//                if (!UtilCollection.sizeIsEmpty(role1)) {
//                    userShelvesDto.setRoleId(role1.get(0).getId());
//                }
//                scopes.add(sysUserRoleScopEntity);
//                userShelvesDto.setScopes(scopes);
//                userShelvesDto.setResources(Lists.newArrayList());
//                // 拼装授权信息
//                roles.add(userShelvesDto);
//                sysUserRoleScopDto.setRoles(roles);
//                // 授权
//                saveShelves(sysUserRoleScopDto);
//            }
//            // 手动提交事务
//            dataSourceTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            //手动回滚事务
            dataSourceTransactionManager.rollback(transactionStatus);
            throw e;
        } finally {
            excelReader.finish();
        }
        return null;
    }

    /**
     *
     * 批量授权
     *
     * @author liuke
     * @date  2022/6/9 9:07
     * @version
    */
    @Override
    public void batchShelves(ShelvesUserRoleDto shelvesUserRoleDto) throws AppException {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("ids",shelvesUserRoleDto.getUserIds());
        List<SysUserEntity> sysUserEntities = this.queryAll(searchParam);
        Map<Long, List<SysUserEntity>> idGroup = sysUserEntities.stream().collect(Collectors.groupingBy(SysUserEntity::getId));
        for (Long userId : shelvesUserRoleDto.getUserIds()) {
            SysUserRoleScopDto sysUserRoleScopDto = new SysUserRoleScopDto();
            // 授权类型
            sysUserRoleScopDto.setShelvesType(ShelvesType.role);
            // 租户id
            sysUserRoleScopDto.setProjectId(shelvesUserRoleDto.getProjectId());
            // 用户id
            List<SysUserEntity> user1 = idGroup.get(userId);
            // 管理范围
            List<SysUserRoleScopEntity> scopes = Lists.newArrayList();
//                ArrayList<SysUserRoleScopEntity> scopes = Lists.newArrayList();
            SysUserRoleScopEntity sysUserRoleScopEntity = new SysUserRoleScopEntity();
            if (!UtilCollection.sizeIsEmpty(user1)) {
                sysUserRoleScopDto.setUserId(user1.get(0).getId());
                sysUserRoleScopEntity.setOrgId(user1.get(0).getOrgId());
            }
            // 授权信息
            ArrayList<UserShelvesDto> roles = Lists.newArrayList();
            for (Long roleId : shelvesUserRoleDto.getRoleIds()) {
                UserShelvesDto userShelvesDto = new UserShelvesDto();
                userShelvesDto.setRoleId(roleId);
                scopes.add(sysUserRoleScopEntity);
                userShelvesDto.setScopes(scopes);
                userShelvesDto.setResources(Lists.newArrayList());
                // 拼装授权信息
                roles.add(userShelvesDto);
            }
            sysUserRoleScopDto.setRoles(roles);
            // 授权
            saveShelves(sysUserRoleScopDto);
        }
    }


    @Override
    public HashSet batchSaveUsers( MultipartFile file) throws Exception {
        // 获取文件输入流
        InputStream inputStream = file.getInputStream();
        UserDetailDataListener listener = new UserDetailDataListener();
        // easyExcel读取文件
        ExcelReader excelReader = EasyExcel.read(inputStream, UserDetailDto.class, listener).build();
        //手动开启事务
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
            // 获取错误信息
            HashSet<String> failInfo = listener.result;
            if (UtilCollection.isNotEmpty(failInfo)) {
                return failInfo;
            }
            List<SysUserEntity> lists = Lists.newArrayList();
            // 获取合法数据集合
            HashSet<UserDetailDto> list = listener.list;
            Set<String> orgNames = list.stream().map(UserDetailDto::getOrgName).collect(Collectors.toSet());
            Map<String,Object> param = Maps.newHashMap();
            Long sysProjectId = sysProjectService.getProjectId(list.iterator().next().getProjectCode());
            param.put("projectId",sysProjectId);
            param.put("orgNames",orgNames);
            List<SysOrgEntity> orgs = sysOrgService.queryAll(param);
            Map<String,Long> ormMaps = orgs.stream().collect(Collectors.toMap(SysOrgEntity::getOrgName,SysOrgEntity::getId));
            Map<String,String> ormCodeMaps = orgs.stream().collect(Collectors.toMap(SysOrgEntity::getOrgName,SysOrgEntity::getOrgCode));

            for (UserDetailDto userDetailDto : list) {

                SysUserEntity sysUserEntity = new SysUserEntity();
                sysUserEntity.setProjectId(sysProjectId);
                sysUserEntity.setProjectCode(userDetailDto.getProjectCode());
                sysUserEntity.setOrgName(userDetailDto.getOrgName());
                sysUserEntity.setOrgId(ormMaps.get(userDetailDto.getOrgName()));
                sysUserEntity.setOrgCode(ormCodeMaps.get(userDetailDto.getOrgName()));
                sysUserEntity.setUserName(userDetailDto.getUserName());
                sysUserEntity.setRealName(userDetailDto.getRealName());
                sysUserEntity.setTelephone(userDetailDto.getTelephone());
                sysUserEntity.setIdCard(userDetailDto.getIdCard());
                sysUserEntity.setPassword(userDetailDto.getPassword());

                sysUserEntity.setId(IdGenerators.getNextId());
                sysUserEntity.setDel(Boolean.FALSE);
                sysUserEntity.setShelvesType(ShelvesType.role);
                sysUserEntity.setAuthStatus(AuthStatus.NOT_AUTH);
                sysUserEntity.setSource("用户权限中心");
                if (UtilString.isNotBlank(sysUserEntity.getPassword())) {
                    sysUserEntity.setPassword(encodPassword(sysUserEntity.getPassword()));
                }
                //生成hash值
                if (UtilString.isNotBlank(sysUserEntity.getIdCard())) {
                    sysUserEntity.setHash(getUserNameIDHash(sysUserEntity.getIdCard()));
                    sysUserEntity.setIdCard(PassWordUtil.encrypt(sysUserEntity.getIdCard()));
                }
                lists.add(sysUserEntity);
            }
            Set<String> idCards = lists.stream().map(map -> {
                try {
                    return map.getIdCard();
                } catch (Exception e) {
                    return "";
                }
            }).collect(Collectors.toSet());
            Set<String> telephones = lists.stream().map(SysUserEntity::getTelephone).collect(Collectors.toSet());
            Set<String> userNames = lists.stream().map(SysUserEntity::getUserName).collect(Collectors.toSet());

            // 判断用户名是否存在
            Map<String,Object> userNameParam = Maps.newHashMap();
            userNameParam.put("projectId",sysProjectId);
            userNameParam.put("userNames",userNames);
            userNameParam.put("del",false);
            List<SysUserEntity> listsU = queryAll(userNameParam);
            if(!UtilCollection.sizeIsEmpty(listsU)){
                for (SysUserEntity sysUserEntity : listsU) {
                    failInfo.add("用户名"+sysUserEntity.getUserName()+"已经存在");
                }

            }

            // 判断用户身份证是否存在
            Map<String,Object> idCardParam = Maps.newHashMap();
            idCardParam.put("projectId",sysProjectId);
            idCardParam.put("idCards",idCards);
            idCardParam.put("del",false);
            List<SysUserEntity> listsCard = queryAll(userNameParam);
            if(!UtilCollection.sizeIsEmpty(listsU)){
                for (SysUserEntity sysUserEntity : listsCard) {
                    failInfo.add(sysUserEntity.getRealName()+"身份证号已经存在");
                }

            }
            // 判断手机号是否存在
            Map<String,Object> telParam = Maps.newHashMap();
            telParam.put("telephones",telephones);
            telParam.put("projectId",sysProjectId);
            telParam.put("del",false);
            List<SysUserEntity> listsTe = queryAll(userNameParam);
            if(!UtilCollection.sizeIsEmpty(listsTe)){
                for (SysUserEntity sysUserEntity : listsCard) {
                    failInfo.add("手机号"+sysUserEntity.getTelephone()+"已经存在");
                }

            }
            if (UtilCollection.isNotEmpty(failInfo)) {
                return failInfo;
            }
            //保存用户
            pbsUserService.saveinfo(lists);
            // 手动提交事务
            dataSourceTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            //手动回滚事务
            dataSourceTransactionManager.rollback(transactionStatus);
            throw e;
        } finally {
            excelReader.finish();
        }
        return null;
    }

    /**
     * 描述: 拼装参数是否在数据库中合法
     *
     * @param userInfo
     * @param projectId
     * @return java.util.HashSet
     * @author fuhao
     * @date 2022/3/7 19:31
     **/
    public HashMap<String, Object> checkImportParams(HashMap<String,List> userInfo, Long projectId) {
        try {

            // excel中的身份证信息
            Set<String> idCards = userInfo.keySet();
            ArrayList<String> newIdCards = Lists.newArrayList();
            for (String idCard : idCards) {
                newIdCards.add(PassWordUtil.encrypt(idCard));
            }
            // 查询用户信息
            Map<String, Object> userMap = Maps.newHashMap();
            userMap.put("idCards", newIdCards);
            userMap.put("projectId", projectId);
            userMap.put("del", false);
            List<SysUserEntity> sysUserEntities = this.queryAll(userMap);
            HashSet<String> failInfo = Sets.newHashSet();
            // 数据库中的身份证
            Set<String> queryIdCards = sysUserEntities.stream().map(map -> map.getIdCard()).collect(Collectors.toSet());
            for (String newIdCard : newIdCards) {
                boolean containsIdCard = queryIdCards.contains(newIdCard);
                StringBuffer checkIdCard = new StringBuffer("身份证号:");
                if (!containsIdCard) {
                    checkIdCard.append(PassWordUtil.decrypt(newIdCard));
                    checkIdCard.append(",不存在!");
                    failInfo.add(checkIdCard.toString());
                }
            }
            // excel中的角色名称
            //List<String> roleNames = list.stream().map(map -> map.getRoleName()).collect(Collectors.toList());
            HashSet<String> roleNames = Sets.newHashSet();
            Collection<List> values = userInfo.values();
            values.stream().forEach(e -> roleNames.addAll(e));
            // 查询角色信息
            Map<String, Object> roleMap = Maps.newHashMap();
            roleMap.put("roleNames", roleNames);
            roleMap.put("projectId", projectId);
            roleMap.put("del", false);
            List<SysRoleEntity> sysRoleEntities = sysRoleService.queryAll(roleMap);
            // 数据库中角色名称
            Set<String> queryRoleNames = sysRoleEntities.stream().map(map -> map.getRoleName()).collect(Collectors.toSet());
            for (String roleName : roleNames) {
                boolean containsRoleName = queryRoleNames.contains(roleName);
                StringBuffer checkRoleName = new StringBuffer("角色名称:");
                if (!containsRoleName) {
                    checkRoleName.append(roleName);
                    checkRoleName.append(",不存在!");
                    failInfo.add(checkRoleName.toString());
                }
            }
            // 拼装返回结果
            HashMap<String, Object> result = Maps.newHashMap();
            if (UtilCollection.isNotEmpty(failInfo)) {
                result.put("result", false);
                result.put("failInfo", failInfo);
            } else {
                result.put("result", true);
                result.put("user", sysUserEntities);
                result.put("role", sysRoleEntities);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
        //return null;
    }

    /**
     * 描述: 用户授权
     *
     * @param sysUserRoleScopDto
     * @return void
     * @author fuhao
     * @date 2022/1/17 14:22
     **/
    @Override
    @Transactional
    public void saveShelves(SysUserRoleScopDto sysUserRoleScopDto)throws AppException {
        // 修改用户激活状态
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setId(sysUserRoleScopDto.getUserId());
        userEntity.setAuthStatus(AuthStatus.AUTH);
        userEntity.setAuthTime(new Date());
        // 判断是否已激活
        SysUserEntity checkAuth = this.get(sysUserRoleScopDto.getUserId());
        if (checkAuth.getAuthStatus().name().equals(AuthStatus.NOT_AUTH.name())) {
            this.update(userEntity, Arrays.asList("authStatus", "authTime"));
        }
        // 修改授权类型
        userEntity.setShelvesType(sysUserRoleScopDto.getShelvesType());
        this.update(userEntity, Arrays.asList("shelvesType"));
        if (sysUserRoleScopDto.getShelvesType().name().equals(ShelvesType.role.name())) {
            // 用户绑定角色
            Long userId = sysUserRoleScopDto.getUserId();
            Set<Long> roleIds = sysUserRoleScopDto.getRoles().stream().map(map -> map.getRoleId()).collect(Collectors.toSet());
            if(sysRoleService.checkHasSysUser(Lists.newArrayList(roleIds))){
                if (!checkSameSysUser(Lists.newArrayList(userId))){
                    throw new AppException("其他项目已经使用该用户名，请更改用户名。");
                }
            }
            Long projectId = sysUserRoleScopDto.getProjectId();
            this.rebindRole(userId, roleIds, projectId);
        } else if (sysUserRoleScopDto.getShelvesType().name().equals(ShelvesType.resource.name())) {
            // 删除用户权限
            Long userId = sysUserRoleScopDto.getUserId();
            sysUserRoleService.deleteByUserId(userId);
            // 删除用户组织管理范围
            HashMap<String, Object> userRoleScope = Maps.newHashMap();
            userRoleScope.put("userId", userId);
            userRoleScope.put("roleIdIsNot", "123");
            sysUserRoleScopService.deleteRoleIdIsNot(userRoleScope);
        }
        // 保存组织范围
        this.bindManagerScope(sysUserRoleScopDto);
        // 保存资源范围
        List<UserShelvesDto> roles = sysUserRoleScopDto.getRoles();
        if (roles != null) {
            roles.forEach(role -> {
                List<SysUserResourceEntity> resources = role.getResources();
                if (UtilCollection.isNotEmpty(resources)) {
                    resources.forEach(resource -> {
                        if (sysUserRoleScopDto.getShelvesType().name().equals(ShelvesType.role.name())) {
                            resource.setRoleId(role.getRoleId());
                        }
                        resource.setUserId(sysUserRoleScopDto.getUserId());
                        resource.setProjectId(sysUserRoleScopDto.getProjectId());
                    });
                    sysUserResourceService.saveAfterDelete(resources);
                } else {
                    SysUserResourceEntity resource = new SysUserResourceEntity();
                    if (sysUserRoleScopDto.getShelvesType().name().equals(ShelvesType.role.name())) {
                        resource.setRoleId(role.getRoleId());
                    }
                    resource.setUserId(sysUserRoleScopDto.getUserId());
                    resource.setProjectId(sysUserRoleScopDto.getProjectId());
                    resources.add(resource);
                    sysUserResourceService.delUserResource(resources);
                }
            });
        }
    }

    /**
     * 描述: 批量撤销授权
     *
     * @param sysUserRoleScopDto
     * @return void
     * @author fuhao
     * @date 2022/1/17 15:53
     **/
    @Override
    public void batchDelShelves(List<SysUserRoleScopDto> sysUserRoleScopDto) {
        sysUserRoleScopDto.forEach(dto -> {
            HashMap<String, Object> delMap = Maps.newHashMap();
            delMap.put("userId", dto.getUserId());
            delMap.put("projectId", dto.getProjectId());
            List<String> deleteTableNames = new ArrayList<>();
            // 用户资源表
            deleteTableNames.add("sys_user_resource");
            // 用户角色表
            deleteTableNames.add("sys_user_role");
            // 用户角色范围表
            deleteTableNames.add("sys_user_role_scop");
            for (String tablename : deleteTableNames) {
                delMap.put("tablename", tablename);
                entityDao.deleteAllShelves(delMap);
            }
        });
    }

    @Override
    public List<SysUserPostScopeEntity> queryUserBindPost(Long id) {
        return entityDao.queryUserBindPost(id);
    }

    /**
     * 设置用户权限字符串
     *
     * @param appUserInfo
     * @return void
     * @author liuke
     * @date 2021/12/15 14:16
     */
    public void setAuthoritiesAndRoles(AppUserInfo appUserInfo) {
        List<Map<String, Object>> lists = Lists.newArrayList();
        List<SysRoleEntity> roleEntities = sysRoleService.queryRoleListByUserId(Long.valueOf(appUserInfo.getUserId()));

        if (!UtilCollection.sizeIsEmpty(roleEntities)) {
            List<AppUserRole> appUserRoles = Lists.newArrayList();
            for (SysRoleEntity sysRoleAndResourceVo : roleEntities) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("authority", "ROLE_" + sysRoleAndResourceVo.getRoleName());
                lists.add(map);
                AppUserRole appUserRole = new AppUserRole();
                appUserRole.setUserId(appUserInfo.getUserId());
                appUserRole.setRoleName(sysRoleAndResourceVo.getRoleName());
                appUserRoles.add(appUserRole);
            }
            appUserInfo.setRoles(appUserRoles);
            appUserInfo.setAuthorities(lists);
        }
    }

    /**
     * 获取用户
     *
     * @param searchParam
     * @param clientId
     * @return com.fosung.system.support.system.entity.sys.SysUserEntity
     * @author liuke
     * @date 2021/12/15 14:16
     */
    public SysUserEntity getAppInfoDetails(Map<String, Object> searchParam, String clientId) {

        List<SysUserEntity> sysUserEntities = this.queryAll(searchParam);
        if (!UtilCollection.sizeIsEmpty(sysUserEntities)) {
            return sysUserEntities.get(0);
        } else {
            return null;
        }
    }

    public Map<String, Object> checkDtUser(String id) {
        try {
            return dtSourceService.queryDtUserDetail(UtilDigest.digestIdentifyId(id));

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SysUserEntity> queryAllUserInfo(Map<String, Object> searchParam) {
        return entityDao.queryAllUserInfo(searchParam);
    }

    @Override
    public Page<SysUserEntity> queryUserByOrgAndPost(Map<String, Object> sysUserDto, PageRequest of) {
        return entityDao.queryUserByOrgAndPost(sysUserDto, of);
    }

    @Override
    public List<SysUserEntity> queryUserByPost(HashMap<String, Object> searchParams) {
        return entityDao.queryUserByPost(searchParams);
    }

    /**
     * 获取上级组织管理员
     *
     * @author liuke
     * @date 2022/2/22 13:52
     * @version
     */
    @Override
    public Set<String> queryParentOrdUserByOrgAndRole(String orgCode, String roleCode) {
        if (orgCode.length() > 6) {
            orgCode = orgCode.substring(0, orgCode.length() - 6);
        } else {
            return Sets.newHashSet();
        }
        Map<String, Object> searchParam = Maps.newHashMap();
        searchParam.put("orgCode", orgCode);
        searchParam.put("roleCode", roleCode);
        Set<String> ids = entityDao.queryParentOrdUserByOrgAndRole(searchParam);
        if (UtilCollection.sizeIsEmpty(ids) && orgCode.length() > 6) {
            ids = queryParentOrdUserByOrgAndRole(orgCode, roleCode);
        }
        return ids;
    }

    /**
     * 查询当前组织和应用下的管理员
     *
     * @author liuke
     * @date 2022/2/22 13:52
     * @version
     */
    @Override
    public Set<String> queryParentOrdUserByOrgIdAndAppId(Map<String, Object> searchParams) {
        return entityDao.queryParentOrdUserByOrgIdAndAppId(searchParams);
    }

    /**
     * 查询当前组织和应用下的管理员
     *
     * @author liuke
     * @date 2022/2/22 13:52
     * @version
     */
    @Override
    public Set<Map<String,Object>> queryParentOrdUserMapByOrgIdAndAppId(Map<String, Object> searchParams) {
        return entityDao.queryParentOrdUserMapByOrgIdAndAppId(searchParams);
    }

    /**
     * 查询当组织和应用下的管理员
     *
     * @param searchParams
     * @return java.util.Set<java.util.Map < java.lang.String, java.lang.Object>>
     * @author liuke
     * @date 2022/2/22 15:06
     */
    @Override
    public Set<Map<String, Object>> queryUsersByOrgIdsAndAppIds(Map<String, Object> searchParams) {
        return entityDao.queryUsersByOrgIdsAndAppIds(searchParams);
    }

    @Override
    public List<CloudAppDto> queryUserAppList(Long userId) {
        return entityDao.queryUserAppList(userId);
    }

    /**
     *根据角色编码和用户名查询
     *
     * @param searchParams
     * @author liuke
     * @date 2022/3/16 15:54
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysUserEntity>
     */
    @Override
    public List<SysUserEntity> queryAllUserByRoleCode(Map<String, Object> searchParams){
        return entityDao.queryAllUserByRoleCode(searchParams);
    }

    @Override
    public List<Map<String,Object>> queryManagerUsers(Map<String, Object> searchParams){
        return entityDao.queryManagerUsers(searchParams);
    }

    @Override
    public boolean checkSameSysUser(List<Long> userIds){
        List<String> lists= entityDao.querySameUserName(userIds);
        long count = lists.stream().distinct().count();
        if(lists.size()==count){
            return true;
        }else {
            return false;
        }
    }

}