package com.geek.workbench.service.application;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.workbench.dict.TerminalType;
import com.geek.workbench.dto.application.ApplicationVersionDto;
import com.geek.workbench.entity.application.ApplicationVersionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述:  应用版本服务层
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ApplicationVersionService extends AppBaseDataService<ApplicationVersionEntity, Long> {

    /**
     * 描述:  版本升级
     * @createDate: 2021/10/18 22:06
     * @author: gaojian
     * @modify:
     * @param applicationVersionEntity
     * @return: void
     */
    void upgrade(ApplicationVersionEntity applicationVersionEntity);

    /**
     * 描述:  查询应用最大版本号
     * @createDate: 2021/10/18 21:28
     * @author: gaojian
     * @modify:
     * @param applicationVersionEntity
     * @return:
     */
    Integer queryMaxVersionNum(ApplicationVersionEntity applicationVersionEntity);
	
    /**
     * 描述:  根据应用id删除版本信息
     * @createDate: 2021/10/21 13:26
     * @author: gaojian
     * @modify:
     * @param appId
     * @return: void
     */
    void deleteVersionByAppId(Long appId);

    /**
     * 描述:  查询版本信息
     * @createDate: 2021/10/21 16:45
     * @author: gaojian
     * @modify:
     * @param applicationVersionDto
     * @param pageable
     * @return: Page<>
     */
    Page<Map<String,Object>> queryVersion(ApplicationVersionDto applicationVersionDto, Pageable pageable);
    
    /**
     * 描述:  
     * @createDate: 2021/10/21 18:37
     * @author: gaojian
     * @modify:
     * @param versionId
     * @param versionId
     * @return: com.geek.workbench.entity.application.ApplicationBaseConfigEntity
     */
    ApplicationVersionEntity getVersionInfo(Long versionId);

    /**
     * 描述: 删除版本信息
     * @createDate: 2021/10/22 9:07
     * @author: gaojian
     * @modify:
     * @param list
     * @return: void
     */
    void deleteVersion(List<AppBaseIdParam> list);

    /**
     * 描述:  最新版应用信息
     * @createDate: 2021/10/26 11:49
     * @author: gaojian
     * @modify:
     * @param appId
     * @param appType
     * @return: com.geek.workbench.entity.application.ApplicationVersionEntity
     */
    ApplicationVersionEntity queryAppPackageName(Long appId, TerminalType appType);

    /**
     * 获取应用版本
     * @param map
     * @return
     */
    List<ApplicationVersionEntity> getCacheByAppId(ConcurrentHashMap map);
}

