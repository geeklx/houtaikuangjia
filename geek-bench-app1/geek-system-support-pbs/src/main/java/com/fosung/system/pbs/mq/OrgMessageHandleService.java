package com.fosung.system.pbs.mq;


import com.fosung.system.pbs.mq.facade.OrgMessage;

public interface OrgMessageHandleService {

    /**
     * @描述 根据组织消息处理数据
     * @参数 [message] 对象消息
     * @返回值 无 失败抛出异常
     * @创建人 chenxh
     * @创建时间 2020-11-04
     */
    void handleOrgInfo(OrgMessage message);
}
