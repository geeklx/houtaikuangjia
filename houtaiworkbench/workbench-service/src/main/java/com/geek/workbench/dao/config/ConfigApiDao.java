package com.geek.workbench.dao.config;

import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.dto.config.TerminalConfigApiDto;
import com.geek.workbench.entity.config.ConfigApiEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 描述:  接口配置持久化接口
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ConfigApiDao extends AppJPABaseDao<ConfigApiEntity, Long>{

    /**
     * 描述:  查询接口分组信息通过接口分类
     * @createDate: 2021/10/24 11:53
     * @author: gaojian
     * @modify:
     * @param apiCategory
     * @param pageable
     * @return: com.fosung.framework.dao.config.mybatis.page.MybatisPage<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @MybatisQuery
    MybatisPage<Map<String,Object>> queryGroupInfoAllByApiCategory(@Param("apiCategory")String apiCategory, Pageable pageable);

    /**
     * 描述:  根据接口组主键删除接口
     * @createDate: 2021/10/24 14:52
     * @author: gaojian
     * @modify:
     * @param apiGroupId
     * @return: java.lang.Integer
     */
    @MybatisQuery
    Integer updateAllByApiGroupId(@Param("apiGroupId") Long apiGroupId);

    /**
     * 描述:  删除接口绑定信息
     * @createDate: 2021/10/24 15:12
     * @author: gaojian
     * @modify:
     * @param apiGroupId
     * @return: java.lang.Integer
     */
    @MybatisQuery
    Integer deleteApiBindInfo(@Param("apiGroupId") Long apiGroupId);

    /**
     * 描述:  根据组编码和接口编码获取接口信息
     * @createDate: 2021/10/24 15:41
     * @author: gaojian
     * @modify:
     * @param apiGroupId
     * @param apiCode
     * @return: com.fosung.workbench.entity.config.ConfigApiEntity
     */
    ConfigApiEntity getFirstByApiGroupIdAndApiCode(Long apiGroupId,String apiCode);
    @MybatisQuery
    List<ConfigApiEntity> queryTree(@Param("params") TerminalConfigApiDto configApiDto);
}