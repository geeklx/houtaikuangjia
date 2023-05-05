package com.fosung.workbench.service.config;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.dto.config.ConfigApiDto;
import com.fosung.workbench.dto.config.TerminalConfigApiDto;
import com.fosung.workbench.entity.config.ConfigApiEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * 描述:  接口配置服务层
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ConfigApiService extends AppBaseDataService<ConfigApiEntity, Long> {

    /**
     * 描述:  保存接口信息
     * @createDate: 2021/10/24 10:20
     * @author: gaojian
     * @modify:
     * @param ConfigApiDto
     * @return: void
     */
    void saveApiInfo(ConfigApiDto ConfigApiDto);
    
    /**
     * 描述:  修改接口信息
     * @createDate: 2021/10/24 15:40
     * @author: gaojian
     * @modify:
     * @param configApiDto
     * @return: void
     */
    void updateApiInfo(ConfigApiDto configApiDto);

    /**
     * 描述:  查询信息
     * @createDate: 2021/10/24 11:49
     * @author: gaojian
     * @modify:
     * @param configApiDto
     * @return: org.springframework.data.domain.Page<java.util.Map<java.lang.String,java.lang.Object>>
     */
    Page<Map<String,Object>> queryInfo(ConfigApiDto configApiDto);

    /**
     * 描述:  接口删除
     * @createDate: 2021/10/24 14:49
     * @author: gaojian
     * @modify:
     * @param list
     * @return: void
     */
    void deleteInfo(List<ConfigApiDto> list);

    /**
     * 描述:  获取接口配置信息
     * @createDate: 2021/10/24 14:57
     * @author: gaojian
     * @modify:
     * @param configApiDto
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,Object> getInfo(ConfigApiDto configApiDto);

    List<ConfigApiEntity> configApiCache(Long terminalId);

    /**
     * 获取目录树信息
     * @return
     */
    List<ConfigApiEntity> queryTree(TerminalConfigApiDto configApiDto);

}

