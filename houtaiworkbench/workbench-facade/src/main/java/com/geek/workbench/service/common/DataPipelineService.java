package com.geek.workbench.service.common;

import com.geek.workbench.dto.pipeline.MandateParams;

/**
 * @Description 数据同步服务接口
 * @Author gaojian
 * @Date 2021/10/15 8:25
 * @Version V1.0
 */
public interface DataPipelineService {

    /**
     * 描述:  同步授权信息
     * @createDate: 2021/10/15 8:27
     * @author: gaojian
     * @modify:
     * @param mandateParams
     * @return: void
     */
    void pullPermission(MandateParams mandateParams);
}
