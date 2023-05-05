package com.fosung.workbench.service.sys;

import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.dao.sys.SysNoticeDao;
import com.fosung.workbench.dict.NoticeStyle;
import com.fosung.workbench.dict.UmsMessageType;
import com.fosung.workbench.dto.ums.UmsDto;
import com.fosung.workbench.entity.sys.SysNoticeEntity;
import com.fosung.workbench.entity.ums.TerminalPersonEntity;
import com.fosung.workbench.service.ums.TerminalPersonService;
import com.fosung.workbench.service.ums.UmsService;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lihuiming
 * @className SysNoticeServiceImpl
 * @description: 公告消息
 * @date 2022/5/615:36
 */
@Service
public class SysNoticeServiceImpl extends AppJPABaseDataServiceImpl<SysNoticeEntity, SysNoticeDao>
        implements SysNoticeService {

    @Autowired
    private TerminalPersonService terminalPersonService;
    /**
     * 描述:  注入统一消息服务
     *
     * @createDate: 2022年2月23日09:11:49
     * @author: gaojian
     */
    @Autowired
    private UmsService umsService;
    /**
     * 查询条件表达式
     */
    private Map<String, String> expressionMap = new LinkedHashMap<String, String>() {
        {
            put("projectId", "projectId:EQ");
            put("projectName", "projectName:EQ");
            put("noticeTitle", "noticeTitle:LIKE");
            put("noticeStatus", "noticeStatus:EQ");
        }
    };

    @Override
    public Map<String, String> getQueryExpressions() {
        return expressionMap;
    }

    /**
     * 保存公共信息
     *
     * @param sysNoticeEntity
     */
    @Override
    public void saveInfo(SysNoticeEntity sysNoticeEntity) {
        Map<String, Object> searchParams = new HashMap<>(8);
        searchParams.put("projectId", sysNoticeEntity.getProjectId());
        searchParams.put("noticeTitle", sysNoticeEntity.getNoticeTitle());
        List<SysNoticeEntity> list = this.queryAll(searchParams);
        // 1. 判断公告标题是否已存在
        if (!list.isEmpty()) {
            throw new AppException(MessageContent.NOTICE_TITLE_IS_EXIST);
        }
        // 2. 保存公告信息
        save(sysNoticeEntity);
    }

    /**
     * 发送公告消息
     *
     * @param sysNoticeEntity
     */

    @Override
    public void sendNoticeMessage(SysNoticeEntity sysNoticeEntity) {
        sysNoticeEntity = get(sysNoticeEntity.getId());
        // 1. 组装参数
        UmsDto umsDto = new UmsDto();
        umsDto.setType(UmsMessageType.notice);
        umsDto.setTemplateCode("workbench-notice");
        umsDto.setMessageTitle(sysNoticeEntity.getNoticeTitle());
        String noticeContent = sysNoticeEntity.getNoticeContent();
        String noticeSummary = sysNoticeEntity.getNoticeSummary();
        //标题+内容
        if(sysNoticeEntity.getNoticeStyle().equals(NoticeStyle.titleContent)){
            if (StringUtils.isBlank(noticeContent)) {
                umsDto.setMessageContent("消息公告");
            } else if (noticeContent.length() > 20) {
                umsDto.setMessageContent(noticeContent.substring(0, 20) + "...");
            } else {
                umsDto.setMessageContent(noticeContent);
            }
            //标题+摘要
        }else if(sysNoticeEntity.getNoticeStyle().equals(NoticeStyle.titleSummary)){
            if (StringUtils.isBlank(noticeSummary)) {
                umsDto.setMessageContent("消息公告");
            } else if (noticeSummary.length() > 20) {
                umsDto.setMessageContent(noticeSummary.substring(0, 20) + "...");
            } else {
                umsDto.setMessageContent(noticeSummary);
            }
        }

        List terminalIdList = new ArrayList();
        String terminalIds = sysNoticeEntity.getSendTerminal();
        if (StringUtils.isNotBlank(terminalIds) && terminalIds.contains(",")) {
            String[] terminalIdArray = terminalIds.split(",");
            terminalIdList = Arrays.asList(terminalIdArray);
        }
        Map<String, Object> searchParams = new HashMap<>(8);
        searchParams.put("terminalId", Long.valueOf(sysNoticeEntity.getSendTerminal()));
        List<TerminalPersonEntity> list = terminalPersonService.queryAll(searchParams);
        Set<String> userIds = Sets.newHashSet();
        for (TerminalPersonEntity terminalPersonEntity : list) {
           userIds.add(terminalPersonEntity.getUserId());
        }
        umsDto.setUserIds(userIds);
        umsDto.setBusId(sysNoticeEntity.getId().toString());
        // 2. 调用消息推送接口
        umsService.sendUserMsg(umsDto);
    }


    @Override
    public void getNoticeDetailMessage(SysNoticeEntity sysNoticeEntity) {
// 1. 组装参数
        UmsDto umsDto = new UmsDto();
        umsDto.setType(UmsMessageType.notice);
        umsDto.setTemplateCode("workbench-notice");
        umsDto.setMessageTitle(sysNoticeEntity.getNoticeTitle());
        String messageContent = sysNoticeEntity.getNoticeContent();
        if (StringUtils.isBlank(messageContent)) {
            umsDto.setMessageContent("消息公告");
        } else if (messageContent.length() > 20) {
            umsDto.setMessageContent(messageContent.substring(0, 20) + "...");
        } else {
            umsDto.setMessageContent(messageContent);
        }
        List terminalIdList = new ArrayList();
        String terminalIds = sysNoticeEntity.getSendTerminal();
        if (StringUtils.isNotBlank(terminalIds) && terminalIds.contains(",")) {
            String[] terminalIdArray = terminalIds.split(",");
            terminalIdList = Arrays.asList(terminalIdArray);
        }
        Map<String, Object> searchParams = new HashMap<>(8);
        searchParams.put("terminalId", Long.valueOf(sysNoticeEntity.getSendTerminal()));
        List<TerminalPersonEntity> list = terminalPersonService.queryAll(searchParams);
        Set<String> userIds = Sets.newHashSet();
        for (TerminalPersonEntity terminalPersonEntity : list) {
            userIds.add(terminalPersonEntity.getUserId());
        }
        umsDto.setUserIds(userIds);
        umsDto.setBusId(sysNoticeEntity.getId().toString());
        // 2. 调用消息推送接口
        umsService.sendUserMsg(umsDto);
    }

}