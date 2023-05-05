package com.fosung.system.pbs.mq;

import com.fosung.system.pbs.mq.facade.PostMessage;

public interface PostMessageHandleService {

    /**
     * @描述 根据职务同步消息处理数据
     * @参数 [message] 对象消息
     * @返回值 无 失败抛出异常
     * @创建人 zyy
     * @创建时间 2021-02-01
     */
    void handleOrgInfo(PostMessage message);
}
