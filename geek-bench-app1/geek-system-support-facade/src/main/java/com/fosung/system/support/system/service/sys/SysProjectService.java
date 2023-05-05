package com.fosung.system.support.system.service.sys;

import java.util.List;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.system.support.system.entity.sys.SysApplicationEntity;
import com.fosung.system.support.system.entity.sys.SysProjectConfigEntity;
import com.fosung.system.support.system.entity.sys.SysProjectEntity;
import com.fosung.framework.common.support.service.AppBaseDataService;

public interface SysProjectService extends AppBaseDataService<SysProjectEntity, Long> {

    /**
     * 描述: 项目关联应用
     * @param id
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/25 15:33
     **/
    List<SysApplicationEntity> queryApp(Long id);

    /**
     * 描述: 项目绑定应用
     * @param sysProjectApp
     * @return void
     * @author fuhao
     * @date 2021/11/25 19:09
     **/
    void bindApp(SysProjectEntity sysProjectApp);

    /**
     * 描述: 保存项目信息
     * @param sysProject
     * @return void
     * @author fuhao
     * @date 2021/11/26 9:55
     **/
    void saveInfo(SysProjectEntity sysProject);

    /**
     * 描述: 项目级联删除
     * @param list
     * @return void
     * @author fuhao
     * @date 2021/12/3 10:39
     **/
    void deleteInfo(List<AppBaseIdParam> list);

    /**
     * 根据项目编码获取项目id
     * @param projectCode
     * @return
     */
    Long getProjectId(String projectCode);

    /**
     * 获取项目级别配置
     * @param projectId
     * @return
     */
    SysProjectConfigEntity getProjectConfig(Long projectId);

    /**
     * 移除过期redis
     * @param projectId
     */
    void removeConfigRedis(Long projectId);

}

