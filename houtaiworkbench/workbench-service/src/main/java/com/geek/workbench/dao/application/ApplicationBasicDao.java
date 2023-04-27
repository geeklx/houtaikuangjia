package com.geek.workbench.dao.application;


import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.dict.AppType;
import com.geek.workbench.dto.terminal.TerminalApplicationConfigDto;
import com.geek.workbench.entity.application.ApplicationBasicEntity;
import com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述:  应用基本信息持久化接口
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ApplicationBasicDao extends AppJPABaseDao<ApplicationBasicEntity, Long>{
   /* @MybatisQuery
    MybatisPage<ApplicationBasicEntity> queryByPage(@Param("params") Map<String,Object> searchParam, Pageable pageable);

    @MybatisQuery
    List<ApplicationBasicEntity> queryNewApp(@Param("params") TerminalApplicationConfigurationInfoDto terminalApplicationConfigDto);  */

    /**
     * 功能描述: 查询最新app
     *
     * @return
     * @author fuhao
     * @date 2021/10/22 14:38
     */
    @MybatisQuery
    List<TerminalApplicationConfigEntity> queryNewApp(@Param("params") TerminalApplicationConfigDto terminalApplicationConfigDto);

    /**
     * 终端应用查询分类
     * @param terminalApplicationConfigDto
     * @return
     * @author fuhao
     * @date 2021/10/22 14:38
     */
    @MybatisQuery
    List<ApplicationBasicEntity> queryCategory(@Param("params") TerminalApplicationConfigDto terminalApplicationConfigDto);

    /**
     * 描述:  根据应用编码查询应用基本信息
     * @createDate: 2021/10/21 12:36
     * @author: gaojian
     * @modify:
     * @param appCode
     * @return: com.fosung.workbench.entity.application.ApplicationBasicEntity
     */
    ApplicationBasicEntity queryFirstByAppCodeAndDel(String appCode,Boolean del);
    
    /**
     * 描述:  根据包名和应用类型查询信息
     * @createDate: 2021/11/3 10:46
     * @author: gaojian
     * @modify:
     * @param packageName
     * @param appType
     * @return: com.fosung.workbench.entity.application.ApplicationBasicEntity
     */
    ApplicationBasicEntity queryFirstByPackageNameAndAppTypeAndDel(String packageName, AppType appType, Boolean del);

    /**
     * 描述:  删除应用配置信息
     * @createDate: 2021/10/21 13:17
     * @author: gaojian
     * @modify:
     * @param appId
     * @return: void
     */
    @MybatisQuery
    void deleteAppConfigByAppId(@Param("appId") Long appId);
}