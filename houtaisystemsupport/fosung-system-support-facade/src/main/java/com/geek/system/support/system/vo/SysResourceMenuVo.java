package com.geek.system.support.system.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysResourceMenuVo {

    /** 菜单ID */
    private Long menuId;

    /** 菜单名称 */
    private String menuName;


    /** 父菜单ID */
    private String parentId;

    /** 显示顺序 */
    private Integer orderNum;

    /** 路由地址 */
    private String path;

    /** 组件路径 */
    private String component;

//    /** 是否为外链（0是 1否） */
//    private String isFrame;


    /** 类型（M目录 C菜单 F按钮） */
    private String menuType;

    /** 菜单图标 */
    private String icon;

    private Integer status;

    private String redirect;

    private String parentName;

    private Long appId;

}
