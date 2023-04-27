package com.geek.workbench.service.terminal;

import java.util.Map;

import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.AppBean.ApkBasic;
import com.geek.workbench.dto.terminal.TerminalVersionDto;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import com.geek.workbench.entity.terminal.TerminalVersionEntity;
import org.springframework.data.domain.Page;
import com.fosung.framework.common.support.service.AppBaseDataService;

public interface TerminalVersionService extends AppBaseDataService<TerminalVersionEntity, Long> {


    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalVersion
     * @return
     * @throws Exception
     * @author fuhao
     * @date 2021/10/14 9:18
     */
    ResponseParam saveInfo(TerminalVersionEntity terminalVersion);



    /**
     * 分页查询终端版本
     * @param searchParam
     * @return
     * @author fuhao
     * @date 2021/10/14 9:18
     */
    Page<TerminalVersionEntity> queryByPageTerminalVersion(Map<String, Object> searchParam, TerminalVersionDto terminalVersionDto);

    /**
     * 终端版本发布下线功能
     * @param terminalVersion
     * @return
     * @author fuhao
     * @date 2021/10/14 9:18
     */
    void updateStatus(TerminalVersionEntity terminalVersion);

    /**
     * 获取终端版本缓存
     * @param terminalId
     * @return
     */
    TerminalVersionEntity getCacheTerminal(String terminalId);

    /**
     * 获取apk信息
     * @param terminalVersion
     * @param terminalBasic
     * @return
     */
    ApkBasic getInfo(TerminalVersionEntity terminalVersion, TerminalBasicEntity terminalBasic);
}

