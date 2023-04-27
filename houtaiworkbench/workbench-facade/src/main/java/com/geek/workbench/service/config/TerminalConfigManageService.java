package com.geek.workbench.service.config;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.workbench.dto.other.ConfigCertificationUrlAndParam;
import com.geek.workbench.entity.config.TerminalConfigManageEntity;

/**
 * @Description 终端接口配置组服务层
 * @Author gaojian
 * @Date 2021/10/24 10:25
 * @Version V1.0
 */
public interface TerminalConfigManageService extends AppBaseDataService<TerminalConfigManageEntity, Long> {

    /**
     * 描述:  保存终端默认的管理信息
     * @createDate: 2021/11/2 17:50
     * @author: gaojian
     * @modify:
     * @param TerminalId
     * @return: void
     */
    void saveDefault(Long TerminalId);

    /**
     *
     * 查找配置緩存
     * @param id 
     * @author liuke
     * @date 2021/11/3 14:30
     * @return com.fosung.workbench.dto.other.ConfigCertificationUrlAndParam
     */
    ConfigCertificationUrlAndParam getCacheTerminal(Long id);
}

