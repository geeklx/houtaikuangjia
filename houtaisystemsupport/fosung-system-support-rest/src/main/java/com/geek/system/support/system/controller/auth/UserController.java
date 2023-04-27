package com.geek.system.support.system.controller.auth;

import com.fosung.framework.common.secure.auth.AppUserDetails;
import com.fosung.framework.common.secure.auth.AppUserDetailsDefault;
import com.fosung.framework.common.secure.auth.support.UserAuth;
import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.http.AppLoginBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.dto.sys.UserAuthNew;
import com.geek.system.support.system.entity.sys.SysProjectConfigEntity;
import com.geek.system.support.system.entity.sys.SysRoleAppEntity;
import com.geek.system.support.system.service.sys.SysProjectService;
import com.geek.system.support.system.service.sys.SysRoleService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
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
    private SysProjectService sysProjectService;

    @Override
    public ResponseEntity<ResponseParam> userInfo() {
        AppUserDetails appUserDetails = appUserDetailsService.getAppUserDetails();

        Map<String,Object> resultParams = Maps.newHashMap() ;
        if( !appProperties.getMockUser().getEnable() ){
            if( appUserDetails!=null ){
                // 返回部分用户信息
                resultParams.put("id", appUserDetails.getUserId()) ;
                resultParams.put("name", appUserDetails.getUserRealName()) ;
                resultParams.put("telephone", appUserDetails.getTelephone()) ;
                resultParams.put("orgId", appUserDetails.getOrgId()) ;
                resultParams.put("orgCode", appUserDetails.getOrgCode()) ;
                resultParams.put("orgName", appUserDetails.getOrgName()) ;
                resultParams.put("avatar",appUserDetails.getAvatar());
                //resultParams.put("roleId",(Long) ((AppUserDetailsDefault) appUserDetails).getUserProperties().get("roleId"));
                resultParams.put("appCode",appCode);
                List<SysRoleAppEntity> appEntities = sysRoleService.queryRoleByApp(appCode,Long.parseLong(((AppUserDetailsDefault)appUserDetails).getUserProperties().get("projectId").toString()));
                Set<Long> roleIds = appEntities.stream().map(SysRoleAppEntity::getRoleId).collect(Collectors.toSet());
                //resultParams.put("userId",appUserDetails.getUserId());
                if( appUserDetails instanceof AppUserDetailsDefault){
                    List<UserAuth> userAuths = ((AppUserDetailsDefault) appUserDetails).getUserAuths();
                    List<UserAuthNew> userAuthNews = new ArrayList<>();
                    userAuths.forEach(user -> {
                        if(roleIds.contains(Long.valueOf(((UserAuthNew)user).getRoleId()))||appUserDetails.getUsername().equals("admin")){
                            UserAuthNew userAuthNew = UtilBean.copyBean(user, UserAuthNew.class);
                            userAuthNew.setUserId(appUserDetails.getUserId());
                            userAuthNew.setAppCode(appCode);
                            userAuthNews.add(userAuthNew);
                        }
                    });
                    resultParams.put("roleList" , userAuthNews ) ;
                    resultParams.put("projectId",((AppUserDetailsDefault)appUserDetails).getUserProperties().get("projectId"));
                }

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


    @Override
    public ResponseParam configure() {
        Map<String,Object> globalParams = appProperties.getGlobalParams() ;
        String projectId = "";
        try {
            AppUserDetails appUserDetails = appUserDetailsService.getAppUserDetails();
            projectId =((AppUserDetailsDefault)appUserDetails).getUserProperties().get("projectId").toString();
        }catch (Exception e){
        }
        if(UtilString.isBlank(projectId)){
            return ResponseParam.success().data( globalParams ) ;
        }else {
            SysProjectConfigEntity sysProjectConfigEntity = sysProjectService.getProjectConfig(Long.valueOf(projectId));
            if(!Objects.isNull(sysProjectConfigEntity)&&UtilString.isNotBlank(sysProjectConfigEntity.getTheme())){
                globalParams.put("theme",sysProjectConfigEntity.getTheme());
            }else {
                globalParams.put("theme","");
            }
            return ResponseParam.success().data( globalParams ) ;
        }
    }

}
