package com.geek.workbench.service.third.party;

import com.alibaba.fastjson.JSONObject;
import com.geek.workbench.dto.terminal.TerminalThirdPartyCommonConfigDto;
import com.geek.workbench.entity.terminal.TerminalThirdPartyConfigEntity;

/**
 * @Description 第三方服务接口
 * @Author gaojian
 * @Date 2022/2/28 16:46
 * @Version V1.0
 */
public interface ThirdPartyService {

    /**
     * 描述: 获取配置内容
     * @createDate: 2022/2/28 16:47
     * @author: gaojian
     * @modify:
     * @param jsonObject
     * @return: com.geek.workbench.dto.terminal.TerminalThirdPartyCommonConfigDto
     */
    TerminalThirdPartyCommonConfigDto getConfigInfo(JSONObject jsonObject);

    /**
     * 描述:  获取配置内容
     * @createDate: 2022/3/1 9:02
     * @author: gaojian
     * @modify:
     * @param entity
     * @return: com.geek.workbench.dto.terminal.TerminalThirdPartyCommonConfigDto
     */
    TerminalThirdPartyCommonConfigDto getConfigInfo(TerminalThirdPartyConfigEntity entity);

    /**
     * 描述:  校验配置信息
     * @createDate: 2022/3/1 14:50
     * @author: gaojian
     * @modify:
     * @param terminalThirdPartyCommonConfigDto
     * @return: void
     */
    void checkConfigInfo(TerminalThirdPartyCommonConfigDto terminalThirdPartyCommonConfigDto);
}
