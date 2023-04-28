package com.geek.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.system.support.system.entity.sys.SysPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface SysPostService extends AppBaseDataService<SysPostEntity, Long> {


    void bindPost(Long userId, Set<Long> postIds, Long appId);

    void rebindPost(Long userId, Set<Long> postIds,Long appId);

    List<SysPostEntity> getPostByUser(Long userId);

    /**
     * 描述: 岗位分页查询
     * @param searchParam
     * @param pageable
     * @return org.springframework.data.domain.Page<com.geek.system.support.system.entity.sys.SysPostEntity>
     * @author fuhao
     * @date 2022/2/8 14:32
     **/
    Page<SysPostEntity> queryPage(Map<String, Object> searchParam, Pageable pageable);

}

