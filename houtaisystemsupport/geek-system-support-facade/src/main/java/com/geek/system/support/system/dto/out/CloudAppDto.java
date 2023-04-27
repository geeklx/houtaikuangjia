package com.geek.system.support.system.dto.out;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CloudAppDto extends AppBasePageParam {

    // 应用终端id
    private Long id ;

    // 终端名称
    private String name ;

    // 终端编码
    private String code ;

    // 终端图标
    private String icon ;

    // 访问地址
    private String url;

    // 应用类型
    private String type;

    private List<CloudAppDto> appDetailArray;

    private String appTypeCode;

    private String appTypeName;

    private Integer num;

    private Long appId;

    private Long projectId;
}