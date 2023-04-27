package com.geek.system.pbs.mq;


import com.alibaba.fastjson.JSONObject;
import com.geek.system.pbs.mq.facade.PersonnelMessage;

public interface PersonnelMessageHandleService {

    /**
     * @描述 根据人员消息处理数据
     * @参数 [message] 对象消息
     * @返回值 无 失败抛出异常
     * @创建人 chenxh
     * @创建时间 2020-11-04
     */
    void handlePersonnelInfo(PersonnelMessage message);
}
