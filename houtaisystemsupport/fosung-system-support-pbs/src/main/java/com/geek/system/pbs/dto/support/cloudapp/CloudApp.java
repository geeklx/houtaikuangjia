package com.geek.system.pbs.dto.support.cloudapp;

import lombok.Data;

import java.io.Serializable;

@Data
public class CloudApp implements Serializable {

    private static final long serialVersionUID = 1L ;

    // 应用终端id
    private Long id ;

    // 终端名称
    private String name ;

    // 终端编码
    private String code ;

    // 终端图标
    private String icon ;
}
