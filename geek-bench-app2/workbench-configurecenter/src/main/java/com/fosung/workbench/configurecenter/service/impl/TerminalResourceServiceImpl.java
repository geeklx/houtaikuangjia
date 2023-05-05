package com.fosung.workbench.configurecenter.service.impl;

import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.workbench.common.GlobalVariableKey;
import com.fosung.workbench.dict.TerminalCommonConfigType;
import com.fosung.workbench.dto.config.AppSearchParamDto;
import com.fosung.workbench.entity.config.TerminalConfigApiEntity;
import com.fosung.workbench.entity.microcoder.TerminalConfigAgreementEntity;
import com.fosung.workbench.entity.terminal.TerminalBasicEntity;
import com.fosung.workbench.entity.terminal.TerminalConfigCommonEntity;
import com.fosung.workbench.service.config.ConfigApiService;
import com.fosung.workbench.service.config.TerminalConfigApiService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 获取认证配置信息
 * @author liuke
 * @date  2021/10/9 16:25
 * @version 
*/
@Service
public class TerminalResourceServiceImpl extends AbstractWorkBenchConfig{



    @Autowired
    private TerminalConfigApiService terminalConfigApiService;

    @Autowired
    private ConfigApiService configApiService;



    /**
     * 获取关于配置信息
     * @param appSearchParamDto
     * @param servletRequest
     * @return
     * @throws Exception
     */
    public Map<String,Object> getConfigDetailMessage(AppSearchParamDto appSearchParamDto, HttpServletRequest servletRequest) throws Exception{
        Map<String,Object> result = Maps.newHashMap();
        // 1. 终端主键非空判断
        Long terminalId = appSearchParamDto.getTerminalId();
        List<TerminalConfigApiEntity> terminalConfigApies = terminalConfigApiService.getCacheTerminalApiByTerminal(terminalId);
        return result;
    }
}
