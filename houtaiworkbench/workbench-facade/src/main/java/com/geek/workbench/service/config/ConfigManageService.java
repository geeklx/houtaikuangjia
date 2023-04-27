package com.geek.workbench.service.config;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.workbench.dto.other.ConfigCertificationUrlAndParam;
import com.geek.workbench.entity.config.ConfigManageEntity;

import java.util.List;

/**
 * 描述:  配置管理服务层
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ConfigManageService extends AppBaseDataService<ConfigManageEntity, Long> {

    /**
     * 描述:  保存信息
     * @createDate: 2021/10/28 18:16
     * @author: gaojian
     * @modify:
     * @param configManageEntity
     * @return: void
     */
    void saveInfo(ConfigManageEntity configManageEntity);
    
    /**
     * 描述:  修改信息
     * @createDate: 2021/11/4 20:21
     * @author: gaojian
     * @modify:
     * @param configManageEntity
     * @return: void
     */
    void updateInfo(ConfigManageEntity configManageEntity);

    /**
     * 描述:  删除信息
     * @createDate: 2021/11/15 11:45
     * @author: gaojian
     * @modify:
     * @param list
     * @return: void
     */
    void deleteInfo(List<AppBaseIdParam> list);

    /**
     * 根据终端id获取配置
     * @param terminalId
     * @return
     */
    List<ConfigManageEntity> queryByTerminalId(Long terminalId);

    /**
     *查找配置
     *
     * @param id
     * @author liuke
     * @date 2021/10/29 16:21
     * @return com.fosung.workbench.dto.other.ConfigCertificationUrlAndParam
     */
    ConfigCertificationUrlAndParam getCacheTerminal(Long id);
}

