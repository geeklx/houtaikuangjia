package com.fosung.system.support.system.service.mq;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fosung.system.support.mq.BusinessSourceEnum;
import com.fosung.system.support.system.entity.sys.SysOrgEntity;
import com.fosung.system.support.system.entity.sys.SysUserEntity;
import com.fosung.system.support.system.entity.sys.SysUserPostScopeEntity;
import com.fosung.system.support.system.service.sys.SysUserPostScopeService;
import com.fosung.system.support.system.service.sys.SysUserService;
import com.fosung.system.support.system.util.HttpClientUtil;
import com.fosung.system.support.system.util.PassWordUtil;
import com.fosung.system.support.system.util.SystemProperties;
import com.google.common.collect.Maps;
import dm.jdbc.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.fosung.system.support.mq.MQMessageConstant.*;

/**
 * 工单
 *
 * @author liuke
 * @date 2022/2/28 13:43
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "app.rocketmq", ignoreInvalidFields = true)
public class SysMQProducerService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserPostScopeService sysUserPostScopeService;

    @Autowired
    private SystemProperties systemProperties;

    private String sendUrl;

    public boolean sendProducerMessage(Map<String, String> paramMap) {
        try {
            if(systemProperties.isEnableMq()){
                log.info("开始发送信息到工单系统......" + JSON.toJSONString(paramMap));
                String result = HttpClientUtil.doPost(sendUrl, paramMap);
                log.info("发送信息到工单系统完成,发送结果......" + JSONUtils.toJSONString(result));
            }
        } catch (Exception e) {
            log.info("发送工单失败：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 过滤消息标识
     *
     * @param operateType
     * @param message
     * @param tag
     * @return
     */
    public boolean sendSsoMQMessage(String operateType, JSONObject message, String tag) {
        message.put("businesSource", BusinessSourceEnum.SSO.name());
        return sendMQMessage(operateType, message, tag);
    }

    public JSONObject buildSysUserMessage(SysUserEntity sysUser, String operateType) {
        JSONObject message = new JSONObject();
        if(!systemProperties.isEnableMq()){
            return message;
        }
        message.put("id", sysUser.getId());
        String idCard = sysUser.getIdCard();
        message.put("idCard", idCard);
        if (StringUtil.isNotEmpty(idCard)) {
            String desHash = sysUser.getIdCard();
            try {
                message.put("idCard", StringUtil.isNotEmpty(desHash) ? PassWordUtil.decrypt(desHash) : null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        message.put("telephone", sysUser.getTelephone() == null ? "" : sysUser.getTelephone());
        message.put("realName", sysUser.getRealName());
        message.put("political", sysUser.getPolitical() != null ? sysUser.getPolitical() : null);
        message.put("businesSource", sysUser.getSource());
        message.put("headUrl", sysUser.getHeadImgUrl());
        message.put("sex", sysUser.getSex());
        message.put("identity", sysUser.getIdentityId());
        message.put("nation", sysUser.getNation() != null ? sysUser.getNation() : null);
        message.put("education", sysUser.getEducation() != null ? sysUser.getEducation() : null);
        message.put("orgId", sysUser.getOrgId() + "");
        message.put("orgCode", sysUser.getOrgCode());
        message.put("orgName", sysUser.getOrgName());
        message.put("contactDetails", sysUser.getContactDetails());
        if(sysUser.getStatus()!=null){
            message.put("userStatus", sysUser.getStatus().name());
        }
        message.put("operateType", operateType);
        return message;
    }

    public boolean sendMQMessage(String operateType, JSONObject message, String tag) {
        if(!systemProperties.isEnableMq()){
            return true;
        }
        Map<String, String> paramMap = Maps.newHashMap();

        Map<String, Object> messageMap = Maps.newHashMap();

        paramMap.put("topic", TOPIC);
        paramMap.put("tag", tag);
        messageMap.put("operateType", operateType);
        messageMap.put("message", message);
        paramMap.put("message", JSONUtils.toJSONString(messageMap));
        // 发送mq消息通知容联
        return sendProducerMessage(paramMap);
    }

    public boolean sendOrgMessage(SysOrgEntity sysOrg, String type) {
        if(!systemProperties.isEnableMq()){
            return true;
        }
        Map<String, String> mqInfoMap = Maps.newHashMap();
        mqInfoMap.put("topic", TOPIC);
        mqInfoMap.put("tag", ORG_TAG);


        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("operateType", type);

        Map<String, Object> dataMap = Maps.newHashMap();

        dataMap.put("id", sysOrg.getId().toString());
        dataMap.put("name", sysOrg.getOrgName());
        dataMap.put("orgCode", sysOrg.getOrgCode());
        dataMap.put("parentId", sysOrg.getParentId()==null?null:sysOrg.getParentId().toString());
        dataMap.put("levelType", sysOrg.getLevelType());
        dataMap.put("level", sysOrg.getLevel());
        dataMap.put("sort", "1");
        dataMap.put("orgName", sysOrg.getDtOrgName());
        dataMap.put("outCode", sysOrg.getDtOrgCode());
        paramMap.put("message", dataMap);
        mqInfoMap.put("message", JSONUtils.toJSONString(paramMap));
        return sendProducerMessage(mqInfoMap);
    }

    /**
     * 发送修改用户消息
     *
     * @param sysUserEntity
     * @param type
     * @return boolean
     * @author liuke
     * @date 2022/2/28 15:56
     */
    public boolean sendUserEditMessage(SysUserEntity sysUserEntity, String type) {
        if(!systemProperties.isEnableMq()){
            return true;
        }
        Map<String, String> mqInfoMap = Maps.newHashMap();
        mqInfoMap.put("topic", TOPIC);
        mqInfoMap.put("tag", USER_TAG);


        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("operateType", type);

        Map<String, Object> dataMap = Maps.newHashMap();

        dataMap.put("realName", sysUserEntity.getRealName());
        dataMap.put("id", sysUserEntity.getId());
        dataMap.put("authStatus", sysUserEntity.getAuthStatus().getRemark());
        dataMap.put("telephone", sysUserEntity.getTelephone());
        dataMap.put("userId", sysUserEntity.getId());
        dataMap.put("orgId", sysUserEntity.getOrgId());
        dataMap.put("identity", sysUserEntity.getIdentityId());
        mqInfoMap.put("message", JSONUtils.toJSONString(paramMap));

        return sendProducerMessage(mqInfoMap);
    }

    /**
     * 描述: 人员职位关联信息
     * @param sysUserEntity
     * @param sysUserPostScopeEntity
     * @param operateType
     * @return boolean
     * @author fuhao
     * @date 2022/3/14 13:39
     **/
    public boolean sendUserPostMessage(SysUserEntity sysUserEntity,SysUserPostScopeEntity sysUserPostScopeEntity ,String operateType) {
        if(!systemProperties.isEnableMq()){
            return true;
        }
        return sendUserPostMessage(sysUserEntity,sysUserPostScopeEntity,operateType,null);
    }

    /**
     * 描述: 人员职位关联信息
     * @param sysUserEntity
     * @param sysUserPostScopeEntity
     * @param type
     * @return boolean
     * @author fuhao
     * @date 2022/3/1 10:48
     **/
    public boolean sendUserPostMessage(SysUserEntity sysUserEntity,SysUserPostScopeEntity sysUserPostScopeEntity ,String operateType,Integer type) {
        if(!systemProperties.isEnableMq()){
            return true;
        }
        Map<String, String> mqInfoMap = Maps.newHashMap();
        mqInfoMap.put("topic", TOPIC);
        mqInfoMap.put("tag", POST_TAG);
        // 操作类型
        mqInfoMap.put("operateType", operateType);
        Map<String, Object> dataMap = Maps.newHashMap();
        if(type == null){
            if (StringUtils.equals(sysUserPostScopeEntity.getOrgId().toString(),sysUserEntity.getOrgId().toString())) {
                dataMap.put("type", 0);
            }else {
                dataMap.put("type", 1);
            }
        }else {
            dataMap.put("type", type);
        }

        dataMap.put("id", sysUserEntity.getId());
        dataMap.put("orgId", sysUserPostScopeEntity.getOrgId());
        dataMap.put("orgCode", sysUserPostScopeEntity.getOrgCode());
        dataMap.put("post", sysUserPostScopeEntity.getPostCode());
        dataMap.put("createDatetime", sysUserPostScopeEntity.getCreateDatetime());
        dataMap.put("realName", sysUserEntity.getRealName());
        dataMap.put("userId", sysUserEntity.getId());
        dataMap.put("positionInfoName", sysUserPostScopeEntity.getPostName());
        dataMap.put("headUrl",sysUserEntity.getHeadImgUrl());
        mqInfoMap.put("message", JSONUtils.toJSONString(dataMap));
        return sendProducerMessage(mqInfoMap);


    }
}
