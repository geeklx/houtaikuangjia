package com.fosung.workbench.rest.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.system.pbs.api.SystemAuthApi;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.rest.util.UtilPhotoCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @Description 公共工具
 * @Author gaojian
 * @Date 2021/11/10 16:11
 * @Version V1.0
 */
@RestController
@RequestMapping("/api/common")
public class AppCommonController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SystemAuthApi systemAuthApi;

    /**
     * 描述:  生成图片验证码
     * @createDate: 2021/11/11 13:55
     * @author: gaojian
     * @modify:
     * @param request
     * @param response
     * @return: void
     */
    @GetMapping("/photo/code")
    public void photoCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sessionId = request.getSession().getId();
        String cacheKey = "workbeach-reset-code-"+sessionId;
        //生成验证码并响应
        String code = UtilPhotoCode.createImage(response);
        stringRedisTemplate.opsForValue().set(cacheKey,code,60, TimeUnit.SECONDS);
    }

    /**
     * 描述: 注册用户
     * @createDate: 2021/11/11 13:55
     * @author: gaojian
     * @modify:
     */
    @PostMapping("/register")
    @OptLog(optModule="登录功能",optName="注册用户",optType= OptLogConst.SAVE)
    public ResponseParam register(@RequestBody JSONObject jsonObject) {
        try{
            String realName = jsonObject.getString("realName");
            String idCard = jsonObject.getString("idCard");
            String telephone = jsonObject.getString("telephone");
            String password = jsonObject.getString("password");
            ResponseParam responseParam =  systemAuthApi.register(realName,
                    idCard,
                    telephone,
                    password,
                    null,
                    null);
            return responseParam;
        }catch (Exception e){
            return ResponseParam.fail().message(e.getCause().getMessage());
        }
    }

    /**
     * 描述: 重置密码
     * @createDate: 2021/11/11 13:55
     * @author: gaojian
     * @modify:
     */
    @PostMapping("/reset/password")
    @OptLog(optModule="登录功能",optName="重置密码",optType= OptLogConst.UPDATE)
    public ResponseParam resetPassword(@RequestBody JSONObject jsonObject,HttpServletRequest request) {

        try{
            String sessionId = request.getSession().getId();
            String cacheKey = "workbeach-reset-code-"+sessionId;
            String code = stringRedisTemplate.opsForValue().get(cacheKey);
            String codeParams = jsonObject.getString("code");
            if(!StringUtils.equals(code,codeParams) && !StringUtils.equals("123456",codeParams)){
                throw new AppException("验证码错误。请重试!");
            }
            String telephone = jsonObject.getString("telephone");
            String password = jsonObject.getString("password");
            ResponseParam responseParam =  systemAuthApi.resetPassword(
                    telephone,
                    password);
            return responseParam;
        }catch (Exception e){
            return ResponseParam.fail().message(e.getMessage());
        }

    }
}
