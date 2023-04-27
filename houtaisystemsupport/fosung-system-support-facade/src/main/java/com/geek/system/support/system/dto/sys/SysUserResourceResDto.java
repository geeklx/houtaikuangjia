package com.geek.system.support.system.dto.sys;

import com.geek.system.support.system.dict.AppType;
import com.geek.system.support.system.entity.sys.SysRoleResourceEntity;
import com.geek.system.support.system.entity.sys.SysUserResourceEntity;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2021/12/20 15:36
 */
@Data
public class SysUserResourceResDto {

    private Long resourceId;

    private String menuName;

    private List<SysUserResourceEntity> userResources;

    private List<SysRoleResourceEntity> roleResources;

    @Enumerated(EnumType.STRING)
    private AppType appType;

    private String appType_dict;

}
