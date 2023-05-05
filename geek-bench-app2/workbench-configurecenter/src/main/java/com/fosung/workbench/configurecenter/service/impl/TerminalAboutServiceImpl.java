package com.fosung.workbench.configurecenter.service.impl;

import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.workbench.common.GlobalVariableKey;
import com.fosung.workbench.dict.TerminalCommonConfigType;
import com.fosung.workbench.dto.config.AppSearchParamDto;
import com.fosung.workbench.entity.microcoder.TerminalConfigAgreementEntity;
import com.fosung.workbench.entity.terminal.TerminalBasicEntity;
import com.fosung.workbench.entity.terminal.TerminalConfigCommonEntity;
import com.fosung.workbench.entity.terminal.TerminalVersionEntity;
import com.fosung.workbench.service.microcoder.TerminalConfigAgreementService;
import com.fosung.workbench.service.terminal.TerminalConfigCommonService;
import com.fosung.workbench.service.terminal.TerminalVersionService;
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
public class TerminalAboutServiceImpl extends AbstractWorkBenchConfig{



    @Autowired
    private TerminalConfigCommonService terminalConfigCommonService;

    @Autowired
    private TerminalConfigAgreementService terminalConfigAgreementService;
    @Autowired
    private TerminalVersionService terminalVersionService;


    /**
     * 获取关于配置信息
     * @param appSearchParamDto
     * @param servletRequest
     * @return
     * @throws Exception
     */
    public Map<String,Object> getConfigDetailMessage(AppSearchParamDto appSearchParamDto, HttpServletRequest servletRequest) throws Exception{
        Map<String,Object> result = Maps.newHashMap();
        ///功能简介
        TerminalBasicEntity terminalBasicEntity = appSearchParamDto.getTerminalBasicEntity();
        //获取终端版本
        TerminalVersionEntity terminalVersion = terminalVersionService.getCacheTerminal(String.valueOf(terminalBasicEntity.getId()));
        //版本
        result.put("version","V"+ terminalVersion.getVersionName());
        //功能简介
        result.put("remark",terminalBasicEntity.getRemarkUrl()==null?"http://119.188.115.252:8090/resource-handle/8aec1712fd8246ee9a1634fedcc54034.html":terminalBasicEntity.getRemarkUrl());
        //名称
        result.put("name",terminalBasicEntity.getTerminalName());
        //图标
        result.put("img",terminalBasicEntity.getTerminalLogo());
        //获取电话
        //默认为空
        result.put("phone","");
        List<TerminalConfigCommonEntity> listcongig = terminalConfigCommonService.getTerminalConfigCommonByTerminalId(terminalBasicEntity.getId());
        // 3. 组装返回参数值
        for(TerminalConfigCommonEntity terminalConfigInfo : listcongig ){
            if(UtilString.equals(terminalConfigInfo.getConfigType(), TerminalCommonConfigType.phone.name())&&UtilString.equals(terminalConfigInfo.getConfigCode(), GlobalVariableKey.PHONE_CUSTOMER)){
              result.put("phone",terminalConfigInfo.getConfigValue());
            }
        }
        //获取协议列表
        List<TerminalConfigAgreementEntity> list = terminalConfigAgreementService.getTerminalConfigAgreements(terminalBasicEntity.getId());
        //存储协议
        if(!UtilCollection.sizeIsEmpty(list)){
            list.forEach(terminal -> {
                result.put(terminal.getAgreementType().toString(),terminal.getAgreementUrl());
            });
        }
        return result;
    }
}
