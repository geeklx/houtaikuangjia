package com.geek.system.support.system.service.auth;

import com.fosung.framework.common.secure.auth.AppUserDetailsDefault;
import com.fosung.framework.common.secure.auth.AppUserDetailsServiceAdaptor;
import com.fosung.framework.common.secure.auth.support.UserAuth;
import com.fosung.framework.common.util.UtilAuthentication;
import com.fosung.framework.common.util.UtilCollection;
import com.geek.system.support.system.dict.RoleType;
import com.geek.system.support.system.dict.UserStatus;
import com.geek.system.support.system.dto.sys.UserAuthNew;
import com.geek.system.support.system.entity.sys.SysUserRoleScopEntity;
import com.geek.system.support.system.service.sys.SysUserService;
import com.geek.system.support.system.vo.SysRoleAndResourceVo;
import com.geek.system.support.system.vo.SysUserDetailVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *系统管理加载用户服务
 *
 * @param
 * @author liuke
 * @date 2021/4/29 16:41
 * @return
 */
@Slf4j
@Service
@SuppressWarnings("unchecked")
public class AppSysUserDetailsService extends AppUserDetailsServiceAdaptor {


    @Autowired
    @Lazy
    private SysUserService sysUserService;

    @Override
    public void loadUserProperties( AppUserDetailsDefault appUserDetailsDefault ) {
        Map<String,Object> map = Maps.newHashMap();
        // 根据用户名获取用户
        map.put("userName",appUserDetailsDefault.getUsername());
        SysUserDetailVo sysUserDetailVo = sysUserService.getMoreAuthUserDetails(map);

        if(sysUserDetailVo==null){
            log.warn("获取用户信息失败: {}" , appUserDetailsDefault.getUsername());
            return ;
        }


        appUserDetailsDefault.setEnabled(sysUserDetailVo.getStatus().equals(UserStatus.VALID));
        // 设置认证用户详情，用户id、密码 和 真实姓名
        appUserDetailsDefault.setUserId(sysUserDetailVo.getId()) ;
        appUserDetailsDefault.setPassword(sysUserDetailVo.getPassword() ) ;
        appUserDetailsDefault.setUserRealName( sysUserDetailVo.getRealName() ) ;
        appUserDetailsDefault.setTelephone(sysUserDetailVo.getTelephone() );
        appUserDetailsDefault.setAvatar(sysUserDetailVo.getHeadImgUrl());
        appUserDetailsDefault.setOrgId(sysUserDetailVo.getOrgId()==null?"":sysUserDetailVo.getOrgId().toString());
        appUserDetailsDefault.setOrgName(sysUserDetailVo.getOrgName());
        //获取角色资源
        List<SysRoleAndResourceVo> sysRoleAndResourceVos = sysUserDetailVo.getSysRoleAndResourceVos();
        //存储角色资源信息
        Set<String> roleCodes = Sets.newHashSet();
        Set<Map<String,Object>> sysResourceEntities = Sets.newHashSet();
        //用户角色信息
        List<UserAuth> userAuths = Lists.newArrayList();
        //遍历获取角色信息和资源信息
        sysRoleAndResourceVos.forEach( sysRoleAndResourceVo -> {
            if(sysRoleAndResourceVo.getRoleType().equals(RoleType.ADMIN)||sysRoleAndResourceVo.getRoleType().equals(RoleType.superadmin)){
                sysResourceEntities.addAll((List)(sysRoleAndResourceVo.getSysResourceEntities()));
                roleCodes.add(sysRoleAndResourceVo.getRoleCode());
                userAuths.add(buildUserAuth(sysRoleAndResourceVo));
            }

        } );
        //获取权限字符串
        List<Object> perms = UtilCollection.extractToList(sysResourceEntities,"perms",Object.class);
        //保存权限字符串
        appUserDetailsDefault.setUserPermissions(perms);
        //添加用户角色授权
        appUserDetailsDefault.getAuthorities().addAll( UtilAuthentication.convertRoleToGrantedAuthority( roleCodes ) ) ;
        //设置用户角色
        appUserDetailsDefault.getUserAuths().addAll(userAuths);
        //设置其他属性
        appUserDetailsDefault.setUserProperties(new HashMap<String, Object>(){{
            put("projectId",sysUserDetailVo.getProjectId());
            if(UtilCollection.isNotEmpty(sysUserDetailVo.getSysRoleAndResourceVos())){
                put("roleId",sysUserDetailVo.getSysRoleAndResourceVos().get(0).getRoleId());
                put("roleType",sysRoleAndResourceVos.get(0).getRoleType().name());
            }
        }});


    }


    /**
     *构建UserAuth
     *
     * @param sysRoleAndResourceVo
     * @author liuke
     * @date 2021/8/5 9:51
     * @return com.fosung.framework.common.secure.auth.support.UserAuth
     */
    private UserAuth buildUserAuth(SysRoleAndResourceVo sysRoleAndResourceVo){
        UserAuthNew userAuth = new UserAuthNew();

        userAuth.setRoleCode(sysRoleAndResourceVo.getRoleCode());
        userAuth.setRoleName(sysRoleAndResourceVo.getRoleName());
        userAuth.setRoleType(sysRoleAndResourceVo.getRoleType());
        userAuth.setRoleId(sysRoleAndResourceVo.getRoleId());
        userAuth.setExt(sysRoleAndResourceVo.getId().toString());
        List<SysUserRoleScopEntity> lists = sysRoleAndResourceVo.getSysUserRoleScopEntity();
        if(CollectionUtils.isEmpty(lists)){
            return userAuth;
        }else {
            StringBuilder orgCode=new StringBuilder("");
            StringBuilder orgId=new StringBuilder("");
            StringBuilder orgName=new StringBuilder("");
            for (int i = 0; i < lists.size(); i++) {
                if(i>0){
                    orgCode.append(",");
                    orgId.append(",");
                    orgName.append(",");
                }
                orgCode.append(lists.get(i).getOrgCode()==null?"":lists.get(i).getOrgCode().toString());
                orgId.append(lists.get(i).getOrgId()==null?"":lists.get(i).getOrgId().toString());
                orgName.append(lists.get(i).getOrgName()==null?"":lists.get(i).getOrgName().toString());
            }
        }

        return userAuth;
    }



}
