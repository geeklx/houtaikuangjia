package com.geek.workbench.service.application;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.workbench.AppBean.ApkBasicDto;
import com.geek.workbench.dto.terminal.TerminalApplicationConfigDto;
import com.geek.workbench.entity.application.ApplicationBaseConfigEntity;
import com.geek.workbench.entity.application.ApplicationBasicEntity;
import com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 描述:  应用基本信息服务层
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface ApplicationBasicService extends AppBaseDataService<ApplicationBasicEntity, Long> {

  /**
   * 描述:  应用保存
   * @createDate: 2021/10/18 22:12
   * @author: gaojian
   * @modify:
   * @param applicationBasicEntity
   * @return: java.lang.String
   */
  void saveApplicationBasic(ApplicationBasicEntity applicationBasicEntity);

  List<TerminalApplicationConfigEntity> queryNewApp(TerminalApplicationConfigDto terminalApplicationConfigDto);

  // Page<TerminalApplicationConfigEntity> queryNewApp(TerminalApplicationConfigDto terminalApplicationConfigDto, Pageable pageable);

  List<ApplicationBasicEntity> queryCategory(TerminalApplicationConfigDto terminalApplicationConfigDto);

  /**
   * 描述:  删除应用信息
   * @createDate: 2021/10/21 13:14
   * @author: gaojian
   * @modify:
   * @param list
   * @return: void
   */
  void deleteApp( List<AppBaseIdParam> list);

  /**
   * 描述:  获取应用配置信息
   * @createDate: 2021/10/18 16:33
   * @author: gaojian
   * @modify:
   * @param appId
   * @return: java.util.List<com.fosung.workbench.entity.application.ApplicationBaseConfigEntity>
   */
  List<ApplicationBaseConfigEntity> getApplicationConfig(Long appId);

  /**
   * 描述:  获取初始应用配置信息
   * @createDate: 2021/10/18 16:33
   * @author: gaojian
   * @modify:
   * @param appId
   * @return: java.util.List<com.fosung.workbench.entity.application.ApplicationBaseConfigEntity>
   */
  List<ApplicationBaseConfigEntity> getInitApplicationConfig(Long appId);

  /**
   * 根据应用获取id
   * @param appId
   * @return
   */
  ApplicationBasicEntity getAppById(Long appId);

  /**
   * 应用版本升级
   * @return
   */
  Map<String,Object> upgrade(ApkBasicDto dto, HttpServletRequest servletRequest);
  Map<String,Object> upgradeApp(ApkBasicDto dto, HttpServletRequest servletRequest);
}

