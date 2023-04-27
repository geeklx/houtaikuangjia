package com.geek.system.pbs.dto.support.cloudapp;

import lombok.Data;

import java.io.Serializable;

@Data
public class CloudProj implements Serializable {

    private static final long serialVersionUID = 1L ;
    
    private boolean success;
    
    private String message;

    // 项目id
    private Long id ;

    // 项目名称
    private String name ;

    // 登录渠道编码
    private String logonChannel ;

    // 登录渠道名称
    private String logonChannelName ;
}
