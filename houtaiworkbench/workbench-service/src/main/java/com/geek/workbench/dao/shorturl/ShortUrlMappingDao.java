package com.geek.workbench.dao.shorturl;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.geek.workbench.entity.shorturl.ShortUrlMappingEntity;

/**
 * @Description 短URL映射持久化层
 * @Author gaojian
 * @Date 2022/1/17 11:23
 * @Version V1.0
 */
public interface ShortUrlMappingDao extends AppJPABaseDao<ShortUrlMappingEntity, Long> {
}
