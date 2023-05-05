package com.fosung.workbench.service.common;

import com.fosung.workbench.dto.common.StatisticalDto;

/**
 * @Description 统计服务
 * @Author gaojian
 * @Date 2021/10/15 11:26
 * @Version V1.0
 */
public interface StatisticalService {

    /**
     * 描述:  安装APP数量统计
     * @createDate: 2021/10/15 11:31
     * @author: gaojian
     * @modify:
     * @param params
     * @return: void
     */
    void installApp(StatisticalDto params);

    /**
     * 描述:  下载APP数量统计
     * @createDate: 2021/10/15 11:31
     * @author: gaojian
     * @modify:
     * @param params
     * @return: void
     */
    void downloadApp(StatisticalDto params);
}
