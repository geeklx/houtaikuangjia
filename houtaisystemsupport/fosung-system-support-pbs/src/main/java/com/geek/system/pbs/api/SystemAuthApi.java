package com.geek.system.pbs.api;

import com.fosung.framework.web.http.ResponseParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "SystemAuthApi", url = "${app.pbs.client.url}")
@RequestMapping(value = "/api/system/auth")
public interface SystemAuthApi {

    /**
     *获取登录信息
     *
     * @param username
     * @param password
     * @param logonChannelType
     * @author liuke
     * @date 2021/4/28 9:22
     * @return com.fosung.cloud.pbs.rest.client.dto.CloudAuthUser
     */
    @PostMapping("login")
    ResponseParam loginByUserNameAndPassWord(@RequestParam String username, @RequestParam String password, @RequestParam String logonChannelType);


    /**
     *用户注册接口
     *
     * @param realName
     * @param idCard
     * @param telephone
     * @param password
     * @author liuke
     * @date 2021/4/28 9:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("register")
    ResponseParam register(@RequestParam String realName, @RequestParam String idCard, @RequestParam String telephone, @RequestParam String password,@RequestParam(value = "projectCode",required = false) String projectCode,@RequestParam(value = "orgCode",required = false) String orgCode);
    /**
     *微信用户注册接口
     *
     * @author liuke
     * @date 2021/12/09 9:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("wxregister")
    ResponseParam wxRegister(@RequestParam(value = "projectCode",required = false) String projectCode,@RequestParam(value = "wxOpenId") String wxOpenId,@RequestParam(value = "wxUnitId") String wxUnitId,@RequestParam(value = "userName",required = false) String userName);
    /**
     *微信完善用户信息
     *
     * @param telephone
     * @param password
     * @author liuke
     * @date 2021/12/09 9:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("wxsupplement")
    ResponseParam wxSupplement(@RequestParam("telephone") String telephone,@RequestParam("password") String password,@RequestParam(value = "projectCode") String projectCode,@RequestParam(value = "wxOpenId") String wxOpenId);

    /**
     *重置密码
     *
     * @param telephone
     * @param password
     * @author liuke
     * @date 2021/4/28 10:15
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/reset/password")
    ResponseParam resetPassword(@RequestParam String telephone,
                                @RequestParam String password,
                                @RequestParam(value = "projectCode",required = false) String projectCode);

    /**
     *重置密码
     *
     * @param telephone
     * @param password
     * @author liuke
     * @date 2021/4/28 10:15
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/reset/propassword")
    ResponseParam resetProjectPassword(@RequestParam("telephone") String telephone,@RequestParam("password") String password,@RequestParam("project") String project);
    /**
     *更新密码
     *
     * @param userId
     * @param newPassword
     * @author liuke
     * @date 2021/4/28 10:20
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/update/password")
    ResponseParam updatePassword(@RequestParam("userId") String userId,@RequestParam("originPassword") String originPassword,@RequestParam("newPassword") String newPassword);

    /**
     *绑定手机号
     *
     * @param telephone
     * @param userName
     * @author liuke
     * @date 2021/12/9 14:03
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/bind/phone")
    ResponseParam bindPhone(@RequestParam String telephone, @RequestParam String userName);

    /**
     * 根据clientId查询认证信息
     * @param project
     * @param clientId
     * @return
     */
    @PostMapping("query/client/{project}")
    Map<String,Object> getClient(@PathVariable("project") String project,
                                        @RequestParam("clientId") String clientId);
}
