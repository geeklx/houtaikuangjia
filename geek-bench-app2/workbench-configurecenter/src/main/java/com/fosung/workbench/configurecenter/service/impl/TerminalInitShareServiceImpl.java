package com.fosung.workbench.configurecenter.service.impl;

import com.fosung.framework.common.util.UtilString;
import com.fosung.workbench.common.GlobalVariableKey;
import com.fosung.workbench.configurecenter.service.WorkBenchConfigureService;
import com.fosung.workbench.dict.TerminalCommonConfigType;
import com.fosung.workbench.dto.config.AppSearchParamDto;
import com.fosung.workbench.entity.terminal.TerminalConfigCommonEntity;
import com.fosung.workbench.service.terminal.TerminalConfigCommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 获取AK/SK配置信息
 * @author liuke
 * @date  2021/10/9 16:25
 * @version 
*/
@Service
public class TerminalInitShareServiceImpl extends AbstractWorkBenchConfig  {

    /**
     * serviceKey
     */
    private final static String CONFIG_KEY = "terminalInitShares";

    @Autowired
    private TerminalConfigCommonService terminalConfigCommonService;

    /**
     * 描述:  获取配置信息
     * @createDate: 2021/10/15 10:21
     * @author: liuke
     * @modify:
     * @param
     * @param servletRequest
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> getConfigDetailMessage(AppSearchParamDto appSearchParamDto, HttpServletRequest servletRequest) throws Exception{

        // 1. 终端主键非空判断
        Long terminalId = appSearchParamDto.getTerminalId();

        List<TerminalConfigCommonEntity> list = terminalConfigCommonService.getTerminalConfigCommonByTerminalId(terminalId);
        list.stream().filter(map -> UtilString.equals(map.getConfigType(),TerminalCommonConfigType.share.name())  ).collect(Collectors.toList());

        // 3. 组装返回参数值
        Map<String,Object> result = new HashMap<>(8);
        for(TerminalConfigCommonEntity terminalConfigInfo : list ){
            if(StringUtils.equals(terminalConfigInfo.getConfigCode(), GlobalVariableKey.SHARE_ANDROID_HOSTS)){
                result.put(GlobalVariableKey.SHARE_ANDROID_HOSTS,terminalConfigInfo.getConfigValue());
                continue;
            }
            if(StringUtils.equals(terminalConfigInfo.getConfigCode(), GlobalVariableKey.SHARE_ANDROID_URL)){
                result.put(GlobalVariableKey.SHARE_ANDROID_URL,terminalConfigInfo.getConfigValue());
                continue;
            }
            if(StringUtils.equals(terminalConfigInfo.getConfigCode(), GlobalVariableKey.SHARE_ANDROID_URL_SCHEMES)){
                result.put(GlobalVariableKey.SHARE_ANDROID_URL_SCHEMES,terminalConfigInfo.getConfigValue());
                continue;
            }
            if(StringUtils.equals(terminalConfigInfo.getConfigCode(), GlobalVariableKey.SHARE_BOTTOM_URL)){
                result.put(GlobalVariableKey.SHARE_BOTTOM_URL,terminalConfigInfo.getConfigValue());
                continue;
            }
            if(StringUtils.equals(terminalConfigInfo.getConfigCode(), GlobalVariableKey.SHARE_CHANNEL)){
                result.put(GlobalVariableKey.SHARE_CHANNEL,terminalConfigInfo.getConfigValue());
                continue;
            }
            if(StringUtils.equals(terminalConfigInfo.getConfigCode(), GlobalVariableKey.SHARE_CLIENT_URL)){
                result.put(GlobalVariableKey.SHARE_CLIENT_URL,terminalConfigInfo.getConfigValue());
                continue;
            }
            if(StringUtils.equals(terminalConfigInfo.getConfigCode(), GlobalVariableKey.SHARE_DOMAIN)){
                result.put(GlobalVariableKey.SHARE_DOMAIN,terminalConfigInfo.getConfigValue());
                continue;
            }
            if(StringUtils.equals(terminalConfigInfo.getConfigCode(), GlobalVariableKey.SHARE_IOS_URL)){
                result.put(GlobalVariableKey.SHARE_IOS_URL,terminalConfigInfo.getConfigValue());
                continue;
            }
            if(StringUtils.equals(terminalConfigInfo.getConfigCode(), GlobalVariableKey.SHARE_IOS_URL_SCHEMES)){
                result.put(GlobalVariableKey.SHARE_IOS_URL_SCHEMES,terminalConfigInfo.getConfigValue());
                continue;
            }

        }
        return result;
    }
}
