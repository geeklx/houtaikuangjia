package com.geek.workbench.service.terminal;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.workbench.entity.terminal.TerminalImageConfigEntity;

import java.util.List;

/**
 * @Description 终端图片配置服务
 * @Author gaojian
 * @Date 2021/10/24 10:25
 * @Version V1.0
 */
public interface TerminalImageConfigService extends AppBaseDataService<TerminalImageConfigEntity, Long> {


    List<TerminalImageConfigEntity> getCacheTerminalImgByTerminal(Long terminalId);

    /**
     * 描述:  保存信息
     * @createDate: 2021/10/25 18:37
     * @author: gaojian
     * @modify:
     * @param list
     * @return: void
     */
    void saveInfo(List<TerminalImageConfigEntity> list);

    List<TerminalImageConfigEntity> getCacheTerminalGuideImgByTerminal(Long terminalId);

}

