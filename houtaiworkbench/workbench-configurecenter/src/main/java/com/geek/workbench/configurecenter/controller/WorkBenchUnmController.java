package com.geek.workbench.configurecenter.controller;
import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.AppBean.UmsBean;
import com.geek.workbench.service.terminal.TerminalBasicService;
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
 * @className WorkBenchUnmController
 * @description: 对接统一消息
 * @date 2021/11/2317:01
 */
@RestController
@RequestMapping(value = WorkBenchUnmController.BASE_PATH)
public class WorkBenchUnmController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/workbench" ;

    @Autowired
    private TerminalBasicService terminalBasicService;

    /**
     *
     * 获取配置信息
     * @param servletRequest
     * @author lhm
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/terminal/bind")
    public ResponseParam bind(@RequestBody UmsBean umsBean, HttpServletRequest servletRequest){
        if(null == umsBean.getTerminalUniqueId() ){
            return ResponseParam.fail().message("terminalUniqueId不能为空");
        }
        ConcurrentHashMap map =  terminalBasicService.terminalBind(umsBean,servletRequest);
        if(null == map.get("msg")){
            Map result = (HashMap)map.get("objectResult");
            if(Boolean.valueOf(result.get("success").toString())){
                result.put("msg","统一消息绑定成功");
            }
            return ResponseParam.success().message("获取配置成功").data(result);
        }else{
            return ResponseParam.fail().message(map.get("msg").toString());
        }
    }

    /**
     * 获取统一消息未读信息数量
     * @param servletRequest
     * @return
     */
    @RequestMapping("/ums/unreadcount")
    public ResponseParam unreadcount(HttpServletRequest servletRequest){
        ConcurrentHashMap map =  terminalBasicService.getUnreadCount(servletRequest);
        if(null == map.get("msg")){
            JSONObject object = (JSONObject)map.get("unreadResult");
            Map result = new HashMap();
            if(Boolean.valueOf(object.get("success").toString())){
                result.put("msg","获取统一消息成功");
                result.put("count",Integer.valueOf(object.get("data").toString()));
            }
            return ResponseParam.success().message("获取配置成功").data(result);
        }else{
            return ResponseParam.fail().message(map.get("msg").toString());
        }
    }
}
