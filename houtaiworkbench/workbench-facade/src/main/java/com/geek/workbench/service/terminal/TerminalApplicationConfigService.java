package com.geek.workbench.service.terminal;

import java.util.List;
import java.util.Map;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.geek.workbench.dto.terminal.TerminalApplicationBindDto;
import com.geek.workbench.dto.terminal.TerminalApplicationConfigDto;
import com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.geek.workbench.entity.terminal.TerminalApplicationShelvesEntity;
import org.springframework.data.domain.Page;
import com.fosung.framework.common.support.service.AppBaseDataService;

public interface TerminalApplicationConfigService extends AppBaseDataService<TerminalApplicationConfigEntity, Long> {


    /**
     * 查询终端绑定应用列表
     * @param searchParam
     * @return
     * @author fuhao
     * @date 2021/10/13 13:57
     */
    Page<TerminalApplicationConfigEntity> queryTerminalAppByPage(Map<String,Object> searchParam,TerminalApplicationConfigDto terminalAppConfigDto);

    /**
     * 描述:  根据终端包名和终端类型查询终端绑定应用信息
     * @createDate: 2021/12/1 14:47
     * @author: gaojian
     * @modify:
     * @param searchParam
     * @return: java.util.List<com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity>
     */
    List<TerminalApplicationConfigEntity> queryTerminalApp(Map<String, Object> searchParam);
    /**
     * 基本配置修改操作
     * @param terminalApp
     * @author fuhao
     * @date 2021/10/13 13:57
     */
    void update(TerminalApplicationConfigEntity terminalApp);

    /**
     * 查询选择应用展示最新版本app
     * @param terminalAppConfigDto
     * @return
     * @author fuhao
     * @date 2021/10/13 13:57
     */
    //Page<TerminalApplicationConfigEntity> queryApp(TerminalApplicationConfigDto terminalAppConfigDto);
    List<TerminalApplicationConfigEntity> queryApp(TerminalApplicationConfigDto terminalAppConfigDto);

    /**
     * 终端应用选择查询分类
     * @param terminalApplicationConfigDto
     * @return
     * @author fuhao
     * @date 2021/10/13 13:57
     */
    List<Map> queryCategory(TerminalApplicationConfigDto terminalApplicationConfigDto);

    /**
     * 终端应用选择确定
     * @param terminalAppBindDto
     * @author fuhao
     * @date 2021/10/13 13:57
     */
    void saveTerminalAppBind(List<TerminalApplicationBindDto> terminalAppBindDto);

    /**
     *根据终端获取应用
     *
     * @param id
     * @author liuke
     * @date 2021/10/18 10:04
     * @return java.util.List<com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity>
     */
    List<TerminalApplicationConfigEntity> getCacheAppByTerminal(Long id);

    /**
     * 终端应用复制
     * @param terminalAppConfigDto
     * @author fuhao
     * @date 2021/10/13 13:57
     */
    void copy(TerminalApplicationConfigDto terminalAppConfigDto);

    /**
     *  通过终端应用配置id获取应用授权信息
     * @param terminalAppConfigDto
     * @author fuhao
     * @date 2021/10/13 13:57
     */
    Map<String, List<TerminalApplicationShelvesEntity>> queryShelves(TerminalApplicationConfigDto terminalAppConfigDto);

    /**
     * 描述:  根据版本主键修改终端绑定应用状态信息
     * @createDate: 2021/10/22 14:40
     * @author: gaojian
     * @modify:
     * @param terminalAppConfigDto
     * @return: void
     */
    void updateStatusByVersionId(TerminalApplicationConfigDto terminalAppConfigDto);

    /**
     * 功能描述: 获取app规则
     *
     * @param terminalApplicationConfigDto
     * @return
     * @author fuhao
     * @date 2021/10/22 16:10
     */
    TerminalApplicationConfigDto appendParams(TerminalApplicationConfigDto terminalApplicationConfigDto);

    /**
     * 描述:  根据应用id删除基础配置信息
     * @createDate: 2021/11/6 17:35
     * @author: gaojian
     * @modify:
     * @param appId
     * @return: void
     */
    void deleteByAppId(Long appId);

    /***
     * 描述: 级联删除终端应用信息
     * @param list
     * @return void
     * @author fuhao
     * @date 2021/11/10 16:58
     **/
    void deleteByConfigId(List<AppBaseIdParam> list);

    /**
     * 获取终端配置
     * @param appId
     * @return
     */
    TerminalApplicationConfigEntity getConfigById(Long appId);
}

