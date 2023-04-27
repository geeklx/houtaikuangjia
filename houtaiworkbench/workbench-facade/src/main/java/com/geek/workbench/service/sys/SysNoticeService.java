package com.geek.workbench.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.workbench.entity.sys.SysNoticeEntity;

/**
 * @author lihuiming
 * @className: NoticeService
 * @description: 消息通知服务
 * @date 2022/5/615:24
 */

public interface SysNoticeService extends AppBaseDataService<SysNoticeEntity, Long> {
    /**
     * 保存公共信息
     * @param sysNoticeEntity
     */
      void saveInfo(SysNoticeEntity sysNoticeEntity);

    /**
     * 发送公告信息
     * @param sysNoticeEntity
     */
      void  sendNoticeMessage(SysNoticeEntity sysNoticeEntity);
     /**
       * 获取详情信息
       * @param sysNoticeEntity
     */
      void  getNoticeDetailMessage(SysNoticeEntity sysNoticeEntity);

}
