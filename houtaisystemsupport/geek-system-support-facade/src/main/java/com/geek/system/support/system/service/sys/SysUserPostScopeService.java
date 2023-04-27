package com.geek.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.system.support.system.entity.sys.SysPostEntity;
import com.geek.system.support.system.entity.sys.SysUserPostScopeEntity;

import java.util.HashMap;
import java.util.List;

public interface SysUserPostScopeService extends AppBaseDataService<SysUserPostScopeEntity, Long> {


    /**
     * 描述: 通过用户id查询岗位信息
     * @param userParams
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysPostEntity>
     * @author fuhao
     * @date 2022/2/18 11:43
     **/
    List<SysPostEntity> queryPostByUserIds(HashMap<String, Object> userParams);

    /**
     * 描述: 是否是首次任职并发送mq
     * @param userid
     * @return void
     * @author fuhao
     * @date 2022/3/1 11:36
     **/
    void isFirstAddPost(Long userid,Long orgId,String type);

    /**
     * 描述: 是否是最后删除任职，并发送mq
     * @param userId
     * @param orgId
     * @return void
     * @author fuhao
     * @date 2022/3/1 15:12
     **/
    void isLastDelPost(Long userId,Long orgId);
}