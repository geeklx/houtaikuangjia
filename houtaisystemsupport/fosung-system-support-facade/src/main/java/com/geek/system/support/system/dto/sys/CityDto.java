package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2022/2/10 15:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CityDto extends AppBasePageParam {

    private String province;

    private String code;
}
