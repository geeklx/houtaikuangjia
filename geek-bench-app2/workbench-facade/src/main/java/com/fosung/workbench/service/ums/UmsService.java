package com.fosung.workbench.service.ums;

import com.fosung.workbench.dto.ums.UmsDto;

/**
 * @Description 统一消息服务
 * @Author gaojian
 * @Date 2022/2/21 9:15
 * @Version V1.0
 */
public interface UmsService {

    /**
     * 描述:  给指定用户发送信息
     * @createDate: 2022/2/21 9:32
     * @author: gaojian
     * @modify:
     * @param umsDto 信息
     * @return: void
     */
    void sendUserMsg(UmsDto umsDto);
}
