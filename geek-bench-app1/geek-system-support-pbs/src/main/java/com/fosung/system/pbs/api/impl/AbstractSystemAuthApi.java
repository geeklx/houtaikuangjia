package com.fosung.system.pbs.api.impl;

import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.system.pbs.api.SystemAuthApi;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public abstract class AbstractSystemAuthApi extends AppIBaseController implements SystemAuthApi {
    @Override
    public ResponseParam loginByUserNameAndPassWord(String username, String password, String logonChannelType) {
        return null;
    }

    @Override
    public ResponseParam register(@RequestParam String realName, @RequestParam String idCard, @RequestParam String telephone,@RequestParam String password,@RequestParam(value = "projectCode",required = false) String projectCode,@RequestParam(value = "orgCode",required = false) String orgCode) {
        return null;
    }

    /**
     * 微信用户注册接口
     *
     * @param projectCode
     * @param wxOpenId
     * @param wxUnitId
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/12/09 9:52
     */
    @Override
    public ResponseParam wxRegister(String projectCode, String wxOpenId, String wxUnitId,String userName) {
        return null;
    }

    /**
     * 微信完善用户信息
     *
     * @param telephone
     * @param password
     * @param projectCode
     * @param wxOpenId
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/12/09 9:52
     */
    @Override
    public ResponseParam wxSupplement(String telephone, String password, String projectCode, String wxOpenId) {
        return null;
    }

    @Override
    public ResponseParam resetPassword(String telephone, String password,@RequestParam(value = "projectCode",required = false) String projectCode) {
        return null;
    }

    @Override
    public ResponseParam resetProjectPassword(@RequestParam("telephone") String telephone,@RequestParam("password") String password,@RequestParam("project") String project){
        return null;
    }
    @Override
    public ResponseParam updatePassword(@RequestParam("userId") String userId,@RequestParam("originPassword") String originPassword,@RequestParam("newPassword") String newPassword) {
        return null;
    }


    @Override
    public ResponseParam bindPhone( String telephone,  String userName){
        return null;
    }

    /**
     * 根据clientId查询认证信息
     * @param project
     * @param clientId
     * @return
     */
    @Override
    public Map<String,Object> getClient(@PathVariable("project") String project,
                                        @RequestParam("clientId") String clientId){
        return null;
    }
}
