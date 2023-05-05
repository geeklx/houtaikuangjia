package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.system.support.system.entity.sys.SysUserResourceEntity;
import com.fosung.system.support.system.entity.sys.SysUserRoleScopEntity;
import lombok.Data;

import java.util.List;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2021/12/16 17:04
 */
@Data
public class UserShelvesDto extends AppBasePageParam {

    /**
     * 描述: 角色id
     * @author fuhao
     * @date 2021/12/17 17:23
     **/
    private Long roleId;

    /**
     * 描述: 管理范围
     * @author fuhao
     * @date 2021/12/4 15:40
     **/
    private List<SysUserRoleScopEntity> scopes;

    /**
     * 描述: 资源范围
     * @author fuhao
     * @date 2021/12/16 17:16
     **/
    private List<SysUserResourceEntity> resources;
}
