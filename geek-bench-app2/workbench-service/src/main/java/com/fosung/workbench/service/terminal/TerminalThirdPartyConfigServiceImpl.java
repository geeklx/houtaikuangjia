package com.fosung.workbench.service.terminal;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.common.util.UtilMap;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.dao.terminal.TerminalThirdPartyConfigDao;
import com.fosung.workbench.dto.terminal.TerminalThirdPartyCommonConfigDto;
import com.fosung.workbench.entity.terminal.TerminalBasicEntity;
import com.fosung.workbench.entity.terminal.TerminalThirdPartyConfigEntity;
import com.fosung.workbench.service.third.party.ThirdPartyService;
import com.fosung.workbench.service.third.party.ThirdPartyServiceStrategy;
import com.fosung.workbench.util.CustomUtilBean;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description 终端第三方服务配置服务实现类
 * @Author gaojian
 * @Date 2022/2/28 15:58
 * @Version V1.0
 */
@Service
public class TerminalThirdPartyConfigServiceImpl extends
        AppJPABaseDataServiceImpl<TerminalThirdPartyConfigEntity, TerminalThirdPartyConfigDao>
        implements TerminalThirdPartyConfigService {


    /**
     * 查询条件表达式
     */
    private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
        {
            put("terminalId","terminalId:EQ");
            put("configType","configType:EQ");
            put("statusType","statusType:EQ");
        }
    };

    /**
     * 获取查询条件的表达式，用于匹配查询参数对应的查询条件，保存查询字段和数据库查询表达式的映射<br>
     *
     * @return
     */
    @Override
    public Map<String, String> getQueryExpressions() {
        return expressionMap;
    }

    /**
     * 描述:  注入三方服务策略类
     * @createDate: 2022/2/28 17:15
     * @author: gaojian
     */
    @Autowired
    private ThirdPartyServiceStrategy thirdPartyServiceStrategy;

    /**
     * 描述:  终端信息服务
     * @createDate: 2022/3/1 9:56
     * @author: gaojian
     */
    @Autowired
    private TerminalBasicService terminalBasicService;

    /**
     * 描述:  获取实体信息
     * @createDate: 2022/2/28 17:20
     * @author: gaojian
     * @modify:
     * @param jsonObject
     * @return: com.fosung.workbench.entity.terminal.TerminalThirdPartyConfigEntity
     */
    public TerminalThirdPartyConfigEntity getEntityInfo(JSONObject jsonObject){

        // 1. 获取主信息
        TerminalThirdPartyConfigEntity terminalThirdPartyConfigEntity;
        try {
            terminalThirdPartyConfigEntity = JSONObject
                    .parseObject(jsonObject.toJSONString(),TerminalThirdPartyConfigEntity.class);
        }catch (Exception e){
            throw new AppException(MessageContent.PARAMS_CONVERT_ERROR);
        }

        // 2. 判断终端id是否有效
        if( terminalThirdPartyConfigEntity == null || terminalThirdPartyConfigEntity.getTerminalId() == null){
            throw new AppException(MessageContent.TERMINAL_IS_NOT_EXIST);
        }
        TerminalBasicEntity terminalBasicEntity = terminalBasicService.get(terminalThirdPartyConfigEntity.getTerminalId());
        if( terminalBasicEntity == null){
            throw new AppException(MessageContent.TERMINAL_IS_NOT_EXIST);
        }

        // 3. 根据类型不同处理转换出不同的配置信息并设置到configInfo字段内
        ThirdPartyService thirdPartyService = thirdPartyServiceStrategy.getThirdPartyService(terminalThirdPartyConfigEntity.getConfigType());
        TerminalThirdPartyCommonConfigDto terminalThirdPartyConfigDto = thirdPartyService.getConfigInfo(jsonObject);

        // 4. 执行第三方配置信息参数校验
        thirdPartyService.checkConfigInfo(terminalThirdPartyConfigDto);
        terminalThirdPartyConfigEntity.setConfigInfo(JSONObject.toJSONString(terminalThirdPartyConfigDto));
        return terminalThirdPartyConfigEntity;
    }

    /**
     * 描述:  保存第三方配置信息
     *
     * @param jsonObject
     * @createDate: 2022/2/28 16:14
     * @author: gaojian
     * @modify:
     * @return: void
     */
    @Override
    public void saveInfo(JSONObject jsonObject) {

        // 1. 获取实体类
        TerminalThirdPartyConfigEntity terminalThirdPartyConfigEntity = getEntityInfo(jsonObject);

        // 2. 保存信息
        save(terminalThirdPartyConfigEntity);
    }

    /**
     * 描述:  修改第三方配置信息
     *
     * @param jsonObject
     * @createDate: 2022/2/28 16:14
     * @author: gaojian
     * @modify:
     * @return: void
     */
    @Override
    public void updateInfo(JSONObject jsonObject) {

        // 1. 获取实体类
        TerminalThirdPartyConfigEntity terminalThirdPartyConfigEntity = getEntityInfo(jsonObject);

        // 2. 修改信息
        update(terminalThirdPartyConfigEntity, CustomUtilBean.getNotNullPropertyNames(terminalThirdPartyConfigEntity));
    }

    /**
     * 描述:  获取配置信息
     *
     * @param terminalThirdPartyConfigEntity
     * @createDate: 2022/3/1 8:52
     * @author: gaojian
     * @modify:
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    @Override
    public Map<String, Object> getConfigInfo(TerminalThirdPartyConfigEntity terminalThirdPartyConfigEntity) {

        // 1. 获取配置信息并转换为map
        ThirdPartyService thirdPartyService = thirdPartyServiceStrategy.getThirdPartyService(terminalThirdPartyConfigEntity.getConfigType());
        TerminalThirdPartyCommonConfigDto terminalThirdPartyConfigDto = thirdPartyService.getConfigInfo(terminalThirdPartyConfigEntity);
        return CustomUtilBean.beanToMap(terminalThirdPartyConfigDto);
    }
}
