package com.fosung.workbench.service.project;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.dto.project.ProjectManagerSaveDto;
import com.fosung.workbench.entity.project.ProjectManagerEntity;

import java.util.List;
import java.util.Map;

/**
 * 描述:  项目管理员服务层
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ProjectManagerService extends AppBaseDataService<ProjectManagerEntity, Long> {

    /**
     * 描述:  保存项目管理员
     * @createDate: 2021/10/13 20:42
     * @author: gaojian
     * @modify:
     * @param data
     * @return: void
     */
    void saveInfo(ProjectManagerSaveDto data);
    
    /**
     * 描述:  查询项目管理员
     * @createDate: 2021/11/2 8:54
     * @author: gaojian
     * @modify:
     * @param projectId
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,Object> queryInfo(Long projectId);

    /**
     * 描述:  根据项目id删除管理员信息
     * @createDate: 2021/10/19 8:45
     * @author: gaojian
     * @modify:
     * @param projectId
     * @return: java.lang.Integer
     */
    Integer deleteByProjectId(Long projectId);
}

