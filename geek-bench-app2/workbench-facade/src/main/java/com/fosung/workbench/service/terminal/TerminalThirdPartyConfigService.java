package com.fosung.workbench.service.terminal;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.dto.terminal.TerminalThirdPartyCommonConfigDto;
import com.fosung.workbench.entity.terminal.TerminalThirdPartyConfigEntity;

import java.util.Map;

/**
 * @Description 终端三方配置服务
 * @Author gaojian
 * @Date 2022/2/28 15:56
 * @Version V1.0
 */
public interface TerminalThirdPartyConfigService extends AppBaseDataService<TerminalThirdPartyConfigEntity, Long> {

    /**
     * 描述:  保存第三方配置信息
     * @createDate: 2022/2/28 16:14
     * @author: gaojian
     * @modify:
     * @param jsonObject
     * @return: void
     */
    void saveInfo(JSONObject jsonObject);

    /**
     * 描述:  修改第三方配置信息
     * @createDate: 2022/2/28 16:14
     * @author: gaojian
     * @modify:
     * @param jsonObject
     * @return: void
     */
    void updateInfo(JSONObject jsonObject);

    /**
     * 描述:  获取配置信息
     * @createDate: 2022/3/1 8:52
     * @author: gaojian
     * @modify:
     * @param terminalThirdPartyConfigEntity
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,Object> getConfigInfo(TerminalThirdPartyConfigEntity terminalThirdPartyConfigEntity);


}
