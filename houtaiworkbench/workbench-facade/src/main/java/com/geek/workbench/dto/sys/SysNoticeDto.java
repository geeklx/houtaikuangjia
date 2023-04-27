package com.geek.workbench.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lihuiming
 * @className DictDto
 * @description: 公告信息
 * @date 2022/5/614:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysNoticeDto extends    AppBasePageParam  {

        /**
         * 是否删除
         */
        private Boolean del = Boolean.FALSE ;
        /**
         * 项目编号
         */
        private Long projectId;
        /**
         * 项目名称
         */
        private String projectName;
        /**
         * 公告标题
         */
        private String noticeTitle;

        /**
         * 公告摘要
         */
        private String noticeSummary;
        /**
         * 公共正文
         */
        private String noticeContent;
        /**
         * 公告状态
         */
        private String noticeStatus;
        /**
         * 发送终端
         */
        private String sendTerminal;

        /**
         * 是否跳转
         */
        private String isJump;

        /**
         * 跳转链接
         */
        private String jumpLink;

        /**
         * 图标类型
         */
        private String noticeIconType;

        /**
         * 图标
         */
        private String noticeIcon;

        /**
         * 提醒样式
         */
        //@Enumerated(EnumType.STRING)
        private String noticeStyle; //1:标题+正文 2：标题+摘要

}
