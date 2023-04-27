package com.geek.workbench.configurecenter.service.impl;

import com.fosung.framework.common.util.UtilString;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.configurecenter.service.WorkBenchConfigureService;
import com.geek.workbench.dict.TerminalCommonConfigType;
import com.geek.workbench.entity.terminal.TerminalConfigCommonEntity;
import com.geek.workbench.service.terminal.TerminalConfigCommonService;
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
public class AccessSecretServiceImpl extends AbstractWorkBenchConfig implements WorkBenchConfigureService{

    /**
     * 描述:  配置KEY
     * @createDate: 2021/10/15 9:46
     * @author: gaojian
     */
    private final static String CONFIG_KEY = "accessSecret";

    /**
     * 描述:  注入终端公共配置服务
     * @createDate: 2021/10/15 9:46
     * @author: gaojian
     */
    @Autowired
    private TerminalConfigCommonService terminalConfigCommonService;

    @Override
    public String getConfigureKey() {
        return CONFIG_KEY;
    }

    /**
     * 描述:  获取配置信息
     * @createDate: 2021/10/15 10:21
     * @author: gaojian
     * @modify:
     * @param searchParam
     * @param servletRequest
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String,Object> getConfigDetailMessage(Map<String, Object> searchParam, HttpServletRequest servletRequest) throws Exception{

        // 1. 终端主键非空判断
        Long terminalId = checkTerminalId(searchParam);

        List<TerminalConfigCommonEntity> list = terminalConfigCommonService.getTerminalConfigCommonByTerminalId(terminalId);
        list.stream().filter(map -> UtilString.equals(map.getConfigType(),TerminalCommonConfigType.authorization.name())  ).collect(Collectors.toList());

        // 3. 组装返回参数值
        Map<String,Object> result = new HashMap<>(8);
        for(TerminalConfigCommonEntity terminalConfigInfo : list ){
            if(StringUtils.equals(terminalConfigInfo.getConfigCode(), GlobalVariableKey.AUTHORIZATION_AK)){
                result.put(GlobalVariableKey.AUTHORIZATION_AK,terminalConfigInfo.getConfigValue());
            }
            if(StringUtils.equals(terminalConfigInfo.getConfigCode(), GlobalVariableKey.AUTHORIZATION_SK)){
                result.put(GlobalVariableKey.AUTHORIZATION_SK,terminalConfigInfo.getConfigValue());
            }
        }
        return result;
    }
}
