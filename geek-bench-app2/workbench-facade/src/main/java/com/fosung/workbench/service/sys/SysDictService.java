package com.fosung.workbench.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.entity.sys.SysDictEntity;

/**
 * @author lihuiming
 * @className: DictService
 * @description: 字典服务
 * @date 2022/5/615:23
 */

public interface SysDictService extends AppBaseDataService<SysDictEntity, Long> {
    /**
     * 保存字典信息
     * @param sysDictEntity
     */
      void saveInfo(SysDictEntity sysDictEntity);
}
