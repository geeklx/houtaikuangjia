package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommonDto extends AppBasePageParam {

    private Long userId;

    private Long roleId;

    private Long projectId;

    private Long appId;
}
