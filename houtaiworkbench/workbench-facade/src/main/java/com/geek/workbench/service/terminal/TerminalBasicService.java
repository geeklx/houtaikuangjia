package com.geek.workbench.service.terminal;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.AppBean.UmsBean;
import com.geek.workbench.dto.terminal.TerminalBasicDto;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface TerminalBasicService extends AppBaseDataService<TerminalBasicEntity, Long> {

    /**
     * 描述: 终端信息分页查询
     * @param terminalBasicDto
     * @param
     * @return org.springframework.data.domain.Page<com.geek.workbench.entity.terminal.TerminalBasicEntity>
     * @author fuhao
     * @date 2021/10/26 14:31
     **/
    Page<TerminalBasicEntity> queryTerminalBasicInfoByPage(TerminalBasicDto terminalBasicDto);

    /**
     * 描述: 获取终端下拉框
     * @param searchParam
     * @return java.util.List<com.geek.workbench.entity.terminal.TerminalBasicEntity>
     * @author fuhao
     * @date 2021/10/26 14:31
     **/
    List<TerminalBasicEntity> queryTerminalOptions(Map<String,Object> searchParam);

    List<TerminalBasicEntity> getCacheTerminal(String packageName,String terminalType);

    /**
     * 描述: 保存终端id信息
     * @param terminalBasicEntity
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/10/26 14:30
     **/
    ResponseParam saveInfo(TerminalBasicEntity terminalBasicEntity);

    /**
     * 描述: 删除所有关于此终端id的信息
     * @param list
     * @return void
     * @author fuhao
     * @date 2021/10/26 14:30
     **/
    void deleteAllTerminal(List<AppBaseIdParam> list);

    /**
     * 描述: 查询终端树结构
     * @author fuhao
     * @date 2021/10/28 14:37
     **/
    List<TerminalBasicEntity> queryTerminalTree();

    /**
     * 根据包名和终端类型查询
     * @param packageName
     * @return
     */
      TerminalBasicEntity  getCacheTerminalByNameAndType(String packageName,String terminalType);

    /**
     * 調用統一消息綁定終端接口
     * @param umsBean
     * @param servletRequest
     * @return
     */
      ConcurrentHashMap terminalBind(UmsBean umsBean, HttpServletRequest servletRequest);
    /**
     * 獲取未讀消息數量
     * @param servletRequest
     * @return
     */
      ConcurrentHashMap getUnreadCount(HttpServletRequest servletRequest);

    /**
     * 获取登录人员信息
     * @param terminalBasic
     * @param token
     * @return
     */
      ConcurrentHashMap getUserInfo(TerminalBasicEntity terminalBasic,String token);
}

