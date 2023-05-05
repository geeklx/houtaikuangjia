package com.fosung.workbench.dao.terminal;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.workbench.dict.ImageType;
import com.fosung.workbench.entity.terminal.TerminalImageConfigEntity;

/**
 * 描述:  终端图片配置持久化接口
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
public interface TerminalImageConfigDao extends AppJPABaseDao<TerminalImageConfigEntity, Long>{

    /**
     * 描述:  根据终端id和图片类型删除信息
     * @createDate: 2021/10/25 18:41
     * @author: gaojian
     * @modify:
     * @param terminal
     * @param imageType
     * @return: java.lang.Integer
     */
    Integer deleteAllByTerminalIdAndImageType(Long terminal, ImageType imageType);
}