package com.geek.workbench.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author lihuiming
 * @className Notice
 * @description: 消息推送-公告
 * @date 2022/4/2510:05
 */

@Entity
@Table(name="sys_notice")
@Setter
@Getter
public class SysNoticeEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity {

    /**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;
    /**
     * 项目编号
     */
    @Column(name="project_id" , nullable = false)
    private Long projectId;
    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空")
    @Size(min = 0, max = 32, message = "项目名称长度不能超过32个字符")
    @Column(name="project_name" , nullable = false)
    private String projectName;
    /**
     * 公告标题
     */
    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 200, message = "公告标题长度不能超过200个字符")
    @Column(name="notice_title" , nullable = false)
    private String noticeTitle;

    /**
     * 公告摘要
     */
    @NotBlank(message = "公告摘要不能为空")
    @Size(min = 0, max = 200, message = "公告摘要长度不能超过200个字符")
    @Column(name="notice_summary" , nullable = false)
    private String noticeSummary;
    /**
     * 公共正文
     */
    @Size(min = 0, max = 1000, message = "公告正文长度不能超过1000个字符")
    @Column(name="notice_content" , nullable = false)
    private String noticeContent;
    /**
     * 公告状态
     */
    @Column(name="notice_status" , nullable = false)
    //@Enumerated(EnumType.STRING)
    private String noticeStatus;
    /**
     * 发送终端
     */
    @Column(name="send_terminal" , nullable = false)
    private String sendTerminal;

    /**
     * 是否跳转
     */
    @Column(name="is_jump" , nullable = false)
    private String isJump;

    /**
     * 跳转链接
     */
    @Column(name="jump_link" )
    private String jumpLink;
    /**
     * 图标类型
     */
    @Column(name="notice_icon_type" )
    //@Enumerated(EnumType.STRING)
    private String noticeIconType;

    /**
     * 图标
     */
    @Column(name="notice_icon" , nullable = false)
    private String noticeIcon;

    /**
     * 提醒样式
     */
    @Column(name="notice_style" , nullable = false)
    //@Enumerated(EnumType.STRING)
    private String noticeStyle; //1:标题+正文 2：标题+摘要
    /**
     * 发送时间类型
     */
    @Column(name="send_time_type" , nullable = false)
    //@Enumerated(EnumType.STRING)
    private String sendTimeType;
    /**
     * 发送时间
     */
    @Column(name="send_time" , nullable = false)
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date sendTime;
}
