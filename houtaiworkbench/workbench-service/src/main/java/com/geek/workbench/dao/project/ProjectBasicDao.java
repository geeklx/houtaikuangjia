package com.geek.workbench.dao.project;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.entity.project.ProjectBasicEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 描述:  项目基本信息持久化接口
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ProjectBasicDao extends AppJPABaseDao<ProjectBasicEntity,Long> {

    /**
     * 描述:  查询项目选项信息 超级管理员可以查看全部、项目管理员只能查看自己管理的项目
     * @createDate: 2021/10/13 17:53
     * @author: gaojian
     * @modify:
     * @return: java.util.List<java.util.Map>
     */
    @MybatisQuery
    List<Map<String,Object>> queryAllProjectOption();

    /**
     * 描述:  查询项目选项信息 超级管理员可以查看全部、项目管理员只能查看自己管理的项目
     * @createDate: 2021/10/13 17:53
     * @author: gaojian
     * @modify:
     * @param userId
     * @return: java.util.List<java.util.Map>
     */
    @MybatisQuery
    List<Map<String,Object>> queryProjectOption(@Param("userId") String userId);

    /**
     * 描述:  根据项目编码查询项目信息
     * @createDate: 2021/10/13 19:19
     * @author: gaojian
     * @modify:
     * @param projectCode
     * @param del
     * @return: com.fosung.workbench.entity.project.ProjectBasicEntity
     */
    ProjectBasicEntity getFirstByProjectCodeAndDel(String projectCode,Boolean del);

    /**
     * 描述:  根据项目主键顺序查询项目信息
     * @createDate: 2021/10/27 18:29
     * @author: gaojian
     * @modify:
     * @param list
     * @return: java.util.List<com.fosung.workbench.entity.project.ProjectBasicEntity>
     */
    @MybatisQuery
    List<ProjectBasicEntity> queryProjectInfoByIds(@Param("list") List<Long> list);

}
