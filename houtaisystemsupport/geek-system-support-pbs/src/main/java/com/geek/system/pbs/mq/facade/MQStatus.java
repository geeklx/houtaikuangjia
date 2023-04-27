package com.geek.system.pbs.mq.facade;

import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@AppDict("mqConsumeStatus")
@Getter
public enum MQStatus implements AppRuntimeDict {
    CONSUMER_FAIL("消费失败"),
    SUCCESS("消费成功");

    public String remark;
}
