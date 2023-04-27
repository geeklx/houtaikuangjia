package com.geek.workbench.rest.controller.front;

import com.fosung.framework.common.secure.auth.AppUserDetails;
import com.fosung.framework.common.secure.auth.AppUserDetailsDefault;
import com.fosung.framework.web.http.AppLoginBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 登录用户信息
 * @Author : liupeng
 * @Date : 2018-10-31
 * @Modified By
 */
@Slf4j
@RestController
public class UserController extends AppLoginBaseController {

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
                if( appUserDetails instanceof AppUserDetailsDefault){
                    resultParams.put("roleList" , ((AppUserDetailsDefault)appUserDetails).getUserAuths() ) ;
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
}
