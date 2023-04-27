package com.geek.system.pbs.dto;

import lombok.Data;

/**
 * 返回多级组织的包装类
 */
@Data
public class CloudROrg {

    private String orgId;
    private String orgName;
    private String orgCode;


    private String mgAreaId;
    private String mgAreaName;

    private String townId;
    private String townName;

    private String countyId;
    private String countyName;

    private String cityId;
    private String cityName;

}
