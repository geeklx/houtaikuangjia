package com.fosung.workbench.service.shorturl;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.entity.shorturl.NumberSenderEntity;

/**
 * @Description 发号器信息持久化服务
 * @Author gaojian
 * @Date 2022/1/17 11:25
 * @Version V1.0
 */
public interface NumberSenderService extends AppBaseDataService<NumberSenderEntity, Long> {

    /**
     * 描述:  更新发号器值
     * @createDate: 2022/1/17 17:18
     * @author: gaojian
     * @modify:
     * @param numberSenderEntity
     * @return: void
     */
    void updateNumber(NumberSenderEntity numberSenderEntity);
}
