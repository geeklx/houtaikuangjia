package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

@Data
public class CommonDto extends AppBasePageParam {

    private Long userId;

    private Long roleId;

    private Long projectId;

    private Long appId;
}
