package com.fosung.workbench.service.third.party;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.exception.AppException;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.dto.terminal.TerminalThirdPartyCommonConfigDto;
import com.fosung.workbench.dto.third.party.TerminalSearchConfigDto;
import com.fosung.workbench.entity.terminal.TerminalThirdPartyConfigEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Description 搜索服务
 * @Author gaojian
 * @Date 2022/2/28 16:46
 * @Version V1.0
 */
@Service("unisearch")
public class UniSearchService implements ThirdPartyService{

    /**
     * 描述: 获取配置内容
     *
     * @param jsonObject
     * @createDate: 2022/2/28 16:47
     * @author: gaojian
     * @modify:
     * @return: com.fosung.workbench.dto.terminal.TerminalThirdPartyCommonConfigDto
     */
    @Override
    public TerminalThirdPartyCommonConfigDto getConfigInfo(JSONObject jsonObject) {

        // 获取统一搜索相关配置内容
        TerminalSearchConfigDto result = JSONObject.parseObject(jsonObject.toJSONString(), TerminalSearchConfigDto.class);
        return result;
    }

    /**
     * 描述:  获取配置内容
     *
     * @param entity
     * @createDate: 2022/3/1 8:54
     * @author: gaojian
     * @modify:
     * @return: com.alibaba.fastjson.JSONObject
     */
    @Override
    public TerminalThirdPartyCommonConfigDto getConfigInfo(TerminalThirdPartyConfigEntity entity) {

        // 获取统一搜索相关配置内容
        String json = entity.getConfigInfo();
        TerminalSearchConfigDto searchConfigDto;
        if(StringUtils.isBlank(json)){
            searchConfigDto = new TerminalSearchConfigDto();
        }else{
            searchConfigDto = JSONObject.parseObject(json,TerminalSearchConfigDto.class);
        }
        return searchConfigDto;
    }

    /**
     * 描述:  校验配置信息
     *
     * @param terminalThirdPartyCommonConfigDto
     * @createDate: 2022/3/1 14:50
     * @author: gaojian
     * @modify:
     * @return: void
     */
    @Override
    public void checkConfigInfo(TerminalThirdPartyCommonConfigDto terminalThirdPartyCommonConfigDto) {

        // 1. 对象强制转换
        if( terminalThirdPartyCommonConfigDto == null ){
            throw new AppException(MessageContent.PARAMS_IS_NULL);
        }
        TerminalSearchConfigDto terminalSearchConfigDto = (TerminalSearchConfigDto) terminalThirdPartyCommonConfigDto;

        // 2. 执行参数校验
        if(StringUtils.isBlank(terminalSearchConfigDto.getKnowledgeCategoryName())){
            throw new AppException("知识分类名称不能为空！");
        }

        if(StringUtils.isBlank(terminalSearchConfigDto.getKnowledgeCategoryCode())){
            throw new AppException("知识分类编码不能为空！");
        }
    }
}
