package com.fosung.system.support.system.controller.auth;

import com.fosung.framework.common.json.JsonMapper;
import com.fosung.framework.common.secure.auth.AppUserDetails;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.http.AppLoginBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.system.support.system.dict.RedisConstant;
import com.fosung.system.support.system.dto.sys.UserAuthNew;
import com.fosung.system.support.system.entity.sys.SysProjectConfigEntity;
import com.fosung.system.support.system.entity.sys.SysRoleAppEntity;
import com.fosung.system.support.system.service.sys.SysProjectService;
import com.fosung.system.support.system.service.sys.SysRoleService;
import com.fosung.system.support.system.service.sys.SysUserService;
import com.fosung.system.support.system.vo.SysRoleAndResourceVo;
import com.fosung.system.support.system.vo.SysUserDetailVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * 类的描述
 *
 * @author liuke
 * @date  2021/11/25 15:46
 * @version
*/
@Slf4j
@RestController
public class UserController extends AppLoginBaseController {

    @Value("${app.code}")
    private String appCode;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysProjectService sysProjectService;

    @Override
    public ResponseEntity<ResponseParam> userInfo() {
        AppUserDetails appUserDetails = appUserDetailsService.getAppUserDetails();

        Map<String,Object> resultParams = Maps.newHashMap() ;
        if( !appProperties.getMockUser().getEnable() ){
            if( appUserDetails!=null ){
                SysUserDetailVo sysUserDetailVo = queryByUserId(appUserDetails.getUserId());
                // 返回部分用户信息
                resultParams.put("id", sysUserDetailVo.getId()) ;
                resultParams.put("name", sysUserDetailVo.getRealName()) ;
                resultParams.put("telephone", sysUserDetailVo.getTelephone()) ;
                resultParams.put("orgId", sysUserDetailVo.getOrgId()) ;
                resultParams.put("orgCode", sysUserDetailVo.getOrgCode()) ;
                resultParams.put("orgName", sysUserDetailVo.getOrgName()) ;
                resultParams.put("avatar",sysUserDetailVo.getHeadImgUrl());
                resultParams.put("appCode",appCode);
                List<SysRoleAppEntity> appEntities = sysRoleService.queryRoleByApp(appCode,sysUserDetailVo.getProjectId());
                Set<Long> roleIds = appEntities.stream().map(SysRoleAppEntity::getRoleId).collect(Collectors.toSet());
                List<SysRoleAndResourceVo> roleAndResourceVos = sysUserDetailVo.getSysRoleAndResourceVos();
                List<UserAuthNew> userAuthNews = new ArrayList<>();
                roleAndResourceVos.forEach(rol -> {
                    if (roleIds.contains(rol.getRoleId())||sysUserDetailVo.getUserName().equals("admin")) {
                        UserAuthNew userAuthNew = new UserAuthNew();
                        userAuthNew.setRoleId(rol.getRoleId());
                        userAuthNew.setUserId(sysUserDetailVo.getId());
                        userAuthNew.setAppCode(appCode);
                        userAuthNew.setRoleName(rol.getRoleName());
                        userAuthNew.setRoleType(rol.getRoleType());
                        userAuthNew.setRoleCode(rol.getRoleCode());
                        userAuthNew.setExt(sysUserDetailVo.getProjectId().toString());
                        userAuthNews.add(userAuthNew);
                    }
                    });
                resultParams.put("roleList" , userAuthNews ) ;
                resultParams.put("projectId",sysUserDetailVo.getProjectId());


                fillUserInfoParams( resultParams , appUserDetails ) ;
                return ResponseParam.success()
                        .data( resultParams )
                        .getResponseEntity( appUserDetails!=null ? HttpStatus.OK : HttpStatus.UNAUTHORIZED ) ;
            }else {
                return ResponseParam.fail()
                        .data( resultParams )
                        .getResponseEntity( appUserDetails!=null ? HttpStatus.OK : HttpStatus.UNAUTHORIZED ) ;
            }


        }else{
            // 返回mock数据
            buildMockUserInfo( resultParams ) ;
            return ResponseParam.success()
                    .data( resultParams )
                    .getResponseEntity( HttpStatus.OK ) ;
        }
    }

    private SysUserDetailVo queryByUserId(String userid) {
        String redisKey = RedisConstant.SYSTEM_USER_USERID_INFO+userid;
        SysUserDetailVo sysUserDetailVo = new SysUserDetailVo();
        String sysUserStr = stringRedisTemplate.opsForValue().get(redisKey);
        if (UtilString.isBlank(sysUserStr)){
            Map<String,Object> searchParam = Maps.newHashMap();
            searchParam.put("id",Long.valueOf(userid));
            sysUserDetailVo = sysUserService.getAuthUserDetails(searchParam);
            if (sysUserDetailVo != null) {
                stringRedisTemplate.opsForValue().set(redisKey, JsonMapper.toJSONString(sysUserDetailVo),RedisConstant.EXPIRES_HOURS, TimeUnit.HOURS);
            }
        }else {
            sysUserDetailVo =JsonMapper.parseObject(sysUserStr,SysUserDetailVo.class);
        }
        return sysUserDetailVo;
    }

    @Override
    public ResponseParam configure() {
        Map<String,Object> globalParams = appProperties.getGlobalParams() ;
        String userId = "";
        try {
            userId =getLoginUserId();
        }catch (Exception e){
        }
        if(UtilString.isBlank(userId)){
            return ResponseParam.success().data( globalParams ) ;
        }else {

            SysProjectConfigEntity sysProjectConfigEntity = sysProjectService.getProjectConfig(queryByUserId(userId).getProjectId());
            if(!Objects.isNull(sysProjectConfigEntity)&&UtilString.isNotBlank(sysProjectConfigEntity.getTheme())){
                globalParams.put("theme",sysProjectConfigEntity.getTheme());
            }else {
                globalParams.put("theme","");
            }
            return ResponseParam.success().data( globalParams ) ;
        }
    }
}
