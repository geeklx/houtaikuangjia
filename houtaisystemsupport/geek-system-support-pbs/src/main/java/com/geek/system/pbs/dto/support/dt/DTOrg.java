package com.geek.system.pbs.dto.support.dt;

import lombok.Data;

import java.io.Serializable;

@Data
public class DTOrg implements Serializable {

    private String orgId;
    private String orgName;
    private String code;
    private String parentId;
    private Integer orderId;
    private String hasChildren;
    private Integer level;
    private String ouName;

}
