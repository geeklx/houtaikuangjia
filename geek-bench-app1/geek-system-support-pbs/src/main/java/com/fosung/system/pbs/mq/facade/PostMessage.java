package com.fosung.system.pbs.mq.facade;

import lombok.Data;

/**
 * @Desc
 * @Author zyy
 * @Date 2021/2/1 14:10
 */
@Data
public class PostMessage {

    /**
     * ID
     */
    private String id;

    /**
     * 职务名称
     */
    private String positionInfoName;

    /**
     * 职务编码
     */
    private String positionInfoCode;

    /**
     * 身份信息
     */
    private String identityCode;

    /**
     * 人员id
     */
    private Long userId;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 操作类型 addPost-新增职务  deleteOrg-删除职务
     */
    private String operateType;

    /**
     * 对应工单服务生产者消息记录id
     */
    private Long uniqueId;

    /**
     * 消息创建时间
     */
    private Long bornTimestamp;
}
