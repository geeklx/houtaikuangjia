package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2022/2/10 15:37
 */
@Data
public class CityDto extends AppBasePageParam {

    private String province;

    private String code;
}
