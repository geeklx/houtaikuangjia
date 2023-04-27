package com.geek.workbench.configurecenter.service.impl;

import com.fosung.framework.common.dto.UtilDTO;

import com.geek.workbench.dto.config.AppSearchParamDto;
import com.geek.workbench.dto.other.ConfigCertificationUrlAndParam;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import com.geek.workbench.service.config.TerminalConfigManageService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 获取认证配置信息
 * @author liuke
 * @date  2021/10/9 16:25
 * @version 
*/
@Service
public class TerminalConfigCertificationServiceImpl extends AbstractWorkBenchConfig  {

    /**
     * 描述:  配置KEY
     * @createDate: 2021/10/15 9:46
     * @author: gaojian
     */
    private final static String CONFIG_KEY = "configCertification";


    @Autowired
    private TerminalConfigManageService terminalConfigManageService;


    /**
     * 获取认证配置信息
     * @param servletRequest
     * @return
     * @throws Exception
     */
    public Map<String,Object> getConfigDetailMessage(AppSearchParamDto appSearchParamDto, HttpServletRequest servletRequest) throws Exception{
        Map<String,Object> result = Maps.newHashMap();

        TerminalBasicEntity terminalBasicEntity = appSearchParamDto.getTerminalBasicEntity();
        ConfigCertificationUrlAndParam configCertification = terminalConfigManageService.getCacheTerminal(terminalBasicEntity.getId());
        if(configCertification==null){
            result = Maps.newHashMap();
        }else {
            result = UtilDTO.toDTO(configCertification,null,null,null);
            if(appSearchParamDto.getServerType()!=null){
                result =UtilDTO.toDTO( result.get(appSearchParamDto.getServerType()),null,null,null);
                result.put("serverType",appSearchParamDto.getServerType());
            }
        }
        return result;
    }
}
