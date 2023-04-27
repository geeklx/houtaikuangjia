package com.geek.workbench.dao.project;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.geek.workbench.entity.project.ProjectManagerEntity;

/**
 * 描述:  项目管理员持久化接口
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ProjectManagerDao extends AppJPABaseDao<ProjectManagerEntity,Long> {

    /**
     * 描述:  根据项目主键和用户主键查询项目管理员信息
     * @createDate: 2021/10/13 20:44
     * @author: gaojian
     * @modify:
     * @param projectId
     * @param userId
     * @param del
     * @return: com.fosung.workbench.entity.project.ProjectManagerEntity
     */
    ProjectManagerEntity getFirstByProjectIdAndUserIdAndDel(Long projectId,String userId,Boolean del);

    /**
     * 描述:  根据项目id删除管理员信息
     * @createDate: 2021/10/19 8:45
     * @author: gaojian
     * @modify:
     * @param projectId
     * @return: java.lang.Integer
     */
    Integer deleteAllByProjectId(Long projectId);
}
