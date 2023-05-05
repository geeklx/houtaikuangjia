package com.fosung.workbench.service.project;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.entity.project.ProjectBasicEntity;

import java.util.List;
import java.util.Map;

/**
 * 描述:  项目信息服务层
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ProjectBasicService extends AppBaseDataService<ProjectBasicEntity, Long> {

    /**
     * 描述:  查询项目为选项信息 超级管理员可以查看全部；项目管理员查询自己管理的项目
     * @createDate: 2021/10/13 18:12
     * @author: gaojian
     * @modify:
     * @param
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> queryProjectOption();

    /**
     * 描述:  查询全部项目
     * @createDate: 2021/10/27 19:36
     * @author: gaojian
     * @modify:
     * @param
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> queryAllProjectOption();

    /**
     * 描述:  保存项目信息
     * @createDate: 2021/10/13 19:17
     * @author: gaojian
     * @modify:
     * @param projectBasicEntity
     * @return:
     */
    void saveInfo(ProjectBasicEntity projectBasicEntity);

    /**
     * 描述:  修改项目信息
     * @createDate: 2021/10/13 19:17
     * @author: gaojian
     * @modify:
     * @param projectBasicEntity
     * @return:
     */
    void updateInfo(ProjectBasicEntity projectBasicEntity);

    /**
     * 描述:  删除项目信息
     * @createDate: 2021/10/19 8:43
     * @author: gaojian
     * @modify:
     * @param list
     * @return: void
     */
    void deleteInfo(List<AppBaseIdParam> list);

    /**
     * 描述:  根据项目主键顺序查询项目信息
     * @createDate: 2021/10/27 18:29
     * @author: gaojian
     * @modify:
     * @param list
     * @return: java.util.List<com.fosung.workbench.entity.project.ProjectBasicEntity>
     */
    List<ProjectBasicEntity> queryProjectInfoByIds(List<Long> list);
	
}

