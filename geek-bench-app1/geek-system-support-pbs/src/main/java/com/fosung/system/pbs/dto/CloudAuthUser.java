package com.fosung.system.pbs.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CloudAuthUser implements Serializable {

    private static final long serialVersionUID = 1L ;
    
    private boolean success;
    
    private String message;

    // 异常消息
    private String exception ;
    
    private String userId ;

    private String userName ;
    
    private String realName ;

    private String telephone ;
    
    private String orgId ;

    private String orgCode ;

    private String orgName ;
    
    private String source ;

    /**
     * 用户所属组织级别
     */
    private String orgLevel;

    // 灯塔hash值
    private String dtHash ;

    private CloudToken cloudToken;
    
}
