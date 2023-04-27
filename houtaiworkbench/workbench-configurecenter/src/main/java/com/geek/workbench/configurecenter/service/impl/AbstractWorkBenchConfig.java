package com.geek.workbench.configurecenter.service.impl;

import com.fosung.framework.common.exception.AppException;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.dto.config.AppSearchParamDto;
import com.geek.workbench.service.terminal.TerminalBasicService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Description
 * @Author gaojian
 * @Date 2021/10/15 9:53
 * @Version V1.0
 */
public abstract class AbstractWorkBenchConfig {

    @Autowired
    private TerminalBasicService terminalBasicService;

    /**
     * 描述:  校验用户id参数是否存在
     * @createDate: 2021/10/15 9:54
     * @author: gaojian
     * @modify:
     * @param
     * @return: void
     */
    public String checkUserId(Map<String, Object> param){

        Object userId = param.get(GlobalVariableKey.USER_ID);
        if( userId == null ){
            throw new AppException(MessageContent.USER_ID_IS_NULL);
        }
        return (String) userId;
    }

    /**
     * 描述:  校验终端id参数是否存在
     * @createDate: 2021/10/15 9:54
     * @author: gaojian
     * @modify:
     * @param
     * @return: void
     */
    public Long checkTerminalId(Map<String, Object> param){

        Object terminalId = param.get(GlobalVariableKey.TERMINAL_ID);
        if( terminalId == null ){
            throw new AppException(MessageContent.TERMINAL_IS_NOT_EXIST);
        }
        return Long.valueOf(terminalId.toString());
    }

    /**
     *校验保存我的app参数
     *
     * @param appSearchParamDto
     * @author liuke
     * @date 2021/10/27 9:34
     * @return java.lang.Boolean
     */
    public Boolean checkMyAppParam(AppSearchParamDto appSearchParamDto){
        if(appSearchParamDto.getTerminalId()==null||appSearchParamDto.getUserId()==null){
            throw new AppException("缺少参数");
        }else {
            return true;
        }

    }
}
