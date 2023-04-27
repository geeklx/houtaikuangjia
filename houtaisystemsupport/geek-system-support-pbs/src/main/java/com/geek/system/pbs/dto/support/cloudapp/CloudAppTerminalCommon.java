package com.geek.system.pbs.dto.support.cloudapp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 公共应用终端信息
 * @Author byb
 * @Date 2019/12/12
**/
@Data
public class CloudAppTerminalCommon implements Serializable {

    private static final long serialVersionUID = 1L ;

    // 应用终端id
    private Long id ;

    // 应用终端所属应用id
    private Long appId ;

    // 终端名称
    private String name ;

    // 终端编码
    private String code ;

    // 终端图标
    private String icon ;

    // 终端url
    private String url ;

    //应用于（类型）
    private String type;

    //应用类型（code）
    private String appTypeCode;

    //应用类型名称（name）
    private String appTypeName;

}
