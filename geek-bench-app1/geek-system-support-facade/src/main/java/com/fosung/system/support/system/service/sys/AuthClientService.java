package com.fosung.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.system.support.system.entity.sys.AuthClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;

public interface AuthClientService extends AppBaseDataService<AuthClientEntity, Long> {

    Page<AuthClientEntity> queryPage(Map<String, Object> searchParam, Pageable pageable);

    Set<Map<String,Object>> queryAppByUserId(Map<String,Object> param);

    Set<Map<String,Object>> queryUserOrgsByUserAndClient( Map<String,Object> param);

    AuthClientEntity queryAuthClientById(Long id);
}
