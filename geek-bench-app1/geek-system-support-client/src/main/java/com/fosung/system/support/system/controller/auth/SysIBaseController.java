package com.fosung.system.support.system.controller.auth;

import com.fosung.framework.common.secure.auth.AppUserDetails;
import com.fosung.framework.common.secure.auth.AppUserDetailsDefault;
import com.fosung.framework.web.http.AppIBaseController;
import com.google.common.primitives.Longs;

public abstract class SysIBaseController extends AppIBaseController{
    /**
     * 获取登录用户的id
     * @return
     */
    public Long getLoginProjectId(){
        AppUserDetails appUserDetails = appUserDetailsService != null ?
                appUserDetailsService.getAppUserDetails() : null ;
        if(appUserDetails ==null){
            return null;
        }else {
            if( appUserDetails instanceof AppUserDetailsDefault){
               if( ((AppUserDetailsDefault) appUserDetails).getUserProperties().get("projectId")!=null){
                   return Longs.tryParse(((AppUserDetailsDefault) appUserDetails).getUserProperties().get("projectId").toString());
               }
            }

        }
        return Longs.tryParse( "0") ;
    }
}
