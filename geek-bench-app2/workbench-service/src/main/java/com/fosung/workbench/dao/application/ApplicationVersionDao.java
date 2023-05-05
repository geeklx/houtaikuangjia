package com.fosung.workbench.dao.application;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.workbench.dict.TerminalType;
import com.fosung.workbench.dto.application.ApplicationVersionDto;
import com.fosung.workbench.entity.application.ApplicationVersionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * 描述:  应用版本信息持久化接口
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ApplicationVersionDao extends AppJPABaseDao<ApplicationVersionEntity, Long>{

    /**
     * 描述:  获取应用最大版本号
     * @createDate: 2021/10/18 21:42
     * @author: gaojian
     * @modify:
     * @param applicationVersionEntity
     * @return: java.lang.Integer
     */
    @MybatisQuery
    Integer getMaxVersionNum(ApplicationVersionEntity applicationVersionEntity);

    /**
     * 描述:  修改应用为不是最新版本
     * @createDate: 2021/10/20 16:23
     * @author: gaojian
     * @modify:
     * @param applicationVersionEntity
     * @return: java.lang.Integer
     */
    @MybatisQuery
    Integer updateAllIsNotNewVersion(ApplicationVersionEntity applicationVersionEntity);

    /**
     * 描述:  根据应用id删除应用版本信息
     * @createDate: 2021/10/21 13:36
     * @author: gaojian
     * @modify:
     * @param appId
     * @return: java.lang.Integer
     */
    @MybatisQuery
    Integer deleteAllByAppId(@Param("appId") Long appId);

    /**
     * 描述:  根据版本id删除所有配置信息
     * @createDate: 2021/10/21 13:36
     * @author: gaojian
     * @modify:
     * @param id
     * @return: java.lang.Integer
     */
    @MybatisQuery
    Integer deleteAllConfigById(@Param("id")Long id);

    /**
     * 描述:  查询版本信息
     * @createDate: 2021/10/21 16:49
     * @author: gaojian
     * @modify:
     * @param pageable
     * @param applicationVersionDto
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @MybatisQuery
    MybatisPage<Map<String,Object>> queryVersion(@Param("params") ApplicationVersionDto applicationVersionDto,Pageable pageable);

    /**
     * 描述:  查询应用最新版本信息
     * @createDate: 2021/10/26 11:52
     * @author: gaojian
     * @modify:
     * @param appId
     * @param appType
     * @param isNewVersion
     * @return: com.fosung.workbench.entity.application.ApplicationVersionEntity
     */
    ApplicationVersionEntity getFirstByAppIdAndAppTypeAndIsNewVersion(Long appId, TerminalType appType, Boolean isNewVersion);
}