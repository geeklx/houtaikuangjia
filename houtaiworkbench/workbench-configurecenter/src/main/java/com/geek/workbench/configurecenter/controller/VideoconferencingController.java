package com.geek.workbench.configurecenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.AppBean.ThirdInit;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import com.geek.workbench.service.busi.ThirdService;
import com.geek.workbench.service.terminal.TerminalBasicService;
import com.geek.workbench.util.AppHeaderResolution;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lihuiming
 * @className NetMeetingController
 * @description: 视频会议
 * @date 2021/11/3016:00
 */
@RestController
@RequestMapping(value = VideoconferencingController.BASE_PATH)
public class VideoconferencingController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/workbench" ;
    @Autowired
    private TerminalBasicService terminalBasicService;
    @Autowired
    private ThirdService thirdService;
    @RequestMapping("/meeting/init")
    public ResponseParam meetingInit(HttpServletRequest servletRequest) {
        ConcurrentHashMap map = new ConcurrentHashMap();
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
        String msg = AppHeaderResolution.checkHeader(headerMessage);
        if(!StringUtils.isEmpty(msg)){
            return ResponseParam.fail().message(msg).data(map);
        }
        //根据包名和终端类型获取包名
        TerminalBasicEntity terminalBasic = terminalBasicService.getCacheTerminalByNameAndType(headerMessage.getPackageName(), headerMessage.getTerminalType().name());
        if (null == terminalBasic) {
            return  ResponseParam.fail().message("未获取到终端信息");
        }
        ConcurrentHashMap userMap = terminalBasicService.getUserInfo(terminalBasic,headerMessage.getToken());
        JSONObject object = (JSONObject)userMap.get("userObject");
        String dataStr = object.get("data").toString();
        JSONObject data = JSONObject.parseObject(dataStr);
        if(null == data.get("userId")){
            return ResponseParam.fail().message("用户名或密码错误");
        }
        if(null == data.get("phonenum")){
            return ResponseParam.fail().message("用户缺失联系方式");
        }
        String phonenum = data.get("phonenum").toString();
        map.put("appId", GlobalVariableKey.MEETING_APP_ID);
        map.put("appToken", GlobalVariableKey.MEETING_APP_TOKEN);
        map.put("userId",phonenum.substring(3));
        map.put("userName",data.get("username").toString());
        return ResponseParam.success().message("获取信息成功").data(map);
    }
    @RequestMapping("/meeting/init/pwd")
    public ResponseParam meetingInitPwd(HttpServletRequest servletRequest) {
        ConcurrentHashMap map = new ConcurrentHashMap();
        //设置用户登录账号（VoIP帐号）
        map.put("userId", GlobalVariableKey.MEETING_USER_ID);
        //设置账号密码（VoIP密码）
        map.put("pwd", GlobalVariableKey.MEETING_PWD);
        //设置AppId
        map.put("appKey", GlobalVariableKey.MEETING_APP_ID);
        //设置登陆验证模式：VoIP验密登录方式
        map.put("authType","PASSWORD_AUTH");
        //LoginMode（强制上线：FORCE_LOGIN  默认登录：AUTO）
        map.put("mode","FORCE_LOGIN");

        return ResponseParam.success().message("获取信息成功").data(map);
    }
    @RequestMapping("third/init")
    public ResponseParam thirdInit(HttpServletRequest servletRequest,@RequestBody ThirdInit thirdInit){
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
        if(null == headerMessage.getPackageName()){
            return ResponseParam.fail().message("包名不能为空！");
        }
        Map allmap = new HashMap();
        //获取腾讯
        Map txMap = thirdService.getTxSign(servletRequest,thirdInit);
        //获取容联
        Map rlMap = thirdService.getRlSign(servletRequest,thirdInit);
        //视频会议登录
        Map videoMap = thirdService.getVideo(servletRequest,thirdInit);
        allmap.putAll(txMap);
        allmap.putAll(rlMap);
        allmap.putAll(videoMap);
        return ResponseParam.success().message("获取信息成功").data(allmap);
    }
}
