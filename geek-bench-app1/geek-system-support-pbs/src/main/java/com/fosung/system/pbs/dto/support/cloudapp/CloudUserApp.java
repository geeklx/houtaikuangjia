package com.fosung.system.pbs.dto.support.cloudapp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2022/2/21 15:39
 */
@Data
public class CloudUserApp implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appTypeCode;

    private String appTypeName;

    private String num;

    private List<CloudApp> appDetailArray;
}
