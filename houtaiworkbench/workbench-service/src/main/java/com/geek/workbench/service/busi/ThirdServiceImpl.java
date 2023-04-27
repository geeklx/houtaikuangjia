package com.geek.workbench.service.busi;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.config.AppProperties;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.AppBean.ThirdInit;
import com.geek.workbench.entity.project.ProjectBasicEntity;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import com.geek.workbench.service.feign.VideoMeetingService;
import com.geek.workbench.service.project.ProjectBasicService;
import com.geek.workbench.service.terminal.TerminalBasicService;
import com.geek.workbench.util.AppHeaderResolution;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lihuiming
 * @className ThirdServiceImpl
 * @description: 三方服务
 * @date 2022/1/517:18
 */
@Service
@Slf4j
public class ThirdServiceImpl implements ThirdService{
    //获取腾讯认证
    private String txSuffix = "/gwapi/newim/api/im/auth/sign";
    //获取容联认证
    private String rlSuffix = "/gwapi/newim/api/im/auth/rluser";
    @Autowired
    protected AppProperties appProperties;
    @Autowired
    protected TerminalBasicService terminalBasicService;
    @Autowired
    protected ProjectBasicService projectBasicService;

    /**
     * 描述:  视频会议服务
     * @createDate: 2022/6/6 11:12
     * @author: gaojian
     */
    @Autowired
    private VideoMeetingService videoMeetingService;

    @Override
    public   Map  getTxSign(HttpServletRequest servletRequest, ThirdInit thirdInit){
        Map map = new HashMap();
        ConcurrentHashMap<String, Object>  txMap = new ConcurrentHashMap();
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
        List<TerminalBasicEntity> terminals = terminalBasicService.getCacheTerminal(headerMessage.getPackageName(),headerMessage.getTerminalType().name());
        TerminalBasicEntity terminal = new TerminalBasicEntity();
        for (TerminalBasicEntity terminalBasicEntity : terminals) {
            if(terminalBasicEntity.getTerminalType().equals(headerMessage.getTerminalType())){
                terminal = terminalBasicEntity;
            }
        }
        if(null != terminal) {
            ProjectBasicEntity projectBasicEntity = projectBasicService.get(terminal.getProjectId());
            thirdInit.setAppCode(projectBasicEntity.getProjectCode());
        }
        //获取前缀路径
        Map<String, Object> globalParams = this.appProperties.getGlobalParams();
        String commonUrl = globalParams.get("umsUrl").toString();
        //获取用户信息地址 传递header及参数
        String treeUrl = commonUrl  + txSuffix;
        log.info("调用即时通讯地址："+ treeUrl);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("appCode", thirdInit.getAppCode()+"_tx"));
        nvps.add(new BasicNameValuePair("userId", thirdInit.getUserId()));
        nvps.add(new BasicNameValuePair("userName", thirdInit.getUserName()));
        nvps.add(new BasicNameValuePair("headUrl", thirdInit.getHeadUrl()));
        if(null != thirdInit.getPhoneNum()) {
            nvps.add(new BasicNameValuePair("phoneNum", thirdInit.getPhoneNum()));
        }
        if(null != thirdInit.getGender()) {
            nvps.add(new BasicNameValuePair("gender", thirdInit.getGender()));
        }
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(treeUrl);
        JSONObject txResult = new JSONObject();
            try {
                //获取返回数据
                httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
                httpPost.addHeader("ContentType", "application/json;charset=UTF-8");
                HttpResponse httpResponse = httpclient.execute(httpPost);
                log.info("httpResponse:"+httpResponse);
                if (httpResponse != null) {
                    HttpEntity resEntity = httpResponse.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity, "utf-8");
                        log.info("result:"+result);
                        txResult = JSONObject.parseObject(result);
                        if(null !=txResult && null !=txResult.get("data") ){
                            map.put("txResult", txResult.get("data"));
                        }
                    }
                }
            } catch (Exception e) {
                map.put("msg", "接口调用异常！");
                return map;
            }
            return map;
    }
    @Override
    public   Map  getRlSign(HttpServletRequest servletRequest,ThirdInit thirdInit){
        Map map = new HashMap();
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
        List<TerminalBasicEntity> terminals = terminalBasicService.getCacheTerminal(headerMessage.getPackageName(),headerMessage.getTerminalType().name());
        TerminalBasicEntity terminal = new TerminalBasicEntity();
        for (TerminalBasicEntity terminalBasicEntity : terminals) {
            if(terminalBasicEntity.getTerminalType().equals(headerMessage.getTerminalType())){
                terminal = terminalBasicEntity;
            }
        }
        if(null != terminal) {
            ProjectBasicEntity projectBasicEntity = projectBasicService.get(terminal.getProjectId());
            thirdInit.setAppCode(projectBasicEntity.getProjectCode());
        }
        //获取前缀路径
        Map<String, Object> globalParams = this.appProperties.getGlobalParams();
        String commonUrl = globalParams.get("umsUrl").toString();
        //获取用户信息地址 传递header及参数
        String treeUrl = commonUrl  + rlSuffix;
        log.info("即时通讯地址:"+treeUrl);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("appCode", thirdInit.getAppCode()+"_rl"));
        nvps.add(new BasicNameValuePair("userId", thirdInit.getUserId()));
        nvps.add(new BasicNameValuePair("userName", thirdInit.getUserName()));
        nvps.add(new BasicNameValuePair("headUrl", thirdInit.getHeadUrl()));
        if(null != thirdInit.getPhoneNum()) {
            nvps.add(new BasicNameValuePair("phoneNum", thirdInit.getPhoneNum()));
        }
        if(null != thirdInit.getGender()) {
            nvps.add(new BasicNameValuePair("gender", thirdInit.getGender()));
        }
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(treeUrl);
        httpPost.addHeader("ContentType", "application/json;charset=UTF-8");
        JSONObject rlResult = new JSONObject();
        try {
            //获取返回数据
            httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            log.info("httpResponse:"+httpResponse);
            if (httpResponse != null) {
                HttpEntity resEntity = httpResponse.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, "utf-8");
                    rlResult = JSONObject.parseObject(result);
                    log.info("rlResult:"+rlResult);
                    if(null !=rlResult && null !=rlResult.get("data") ){
                        map.put("rlResult", rlResult.get("data"));
                    }
                }
            }
        } catch (Exception e) {
            map.put("msg", "接口调用异常！");
            return map;
        }
        return map;
    }

    @Override
    public   Map getVideo(HttpServletRequest servletRequest, ThirdInit thirdInit){
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

        // 调用视频会议获取应用视频会议配置信息
        JSONObject params = new JSONObject();
        params.put("appCode",terminalBasic.getPackageName());
        params.put("userId",thirdInit.getUserId());
        try {
            ResponseParam responseParam = videoMeetingService.initLogin(params);
            Boolean success = (Boolean) responseParam.get("success");
            if(success && responseParam.containsKey("data")){
                Object data = responseParam.get("data");
                JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(data));
                Map detailMap = new HashMap(16);
                detailMap.putAll(result.getJSONObject("config"));
                detailMap.put("userId",result.getString("userId"));
                detailMap.put("userName",thirdInit.getUserName());
                map.put("meeting",detailMap);
                return map;
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        map.put("msg", "接口调用异常！");
        return map;
    }

}
