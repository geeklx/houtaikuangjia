package com.fosung.system.pbs.dto;

import lombok.Data;

import java.util.List;

/**
 * @Desc 分页查询用户条件
 * @Author zyy
 * @Date 2021/1/6 10:49
 */
@Data
public class SysUserDto {

    private String orgId;

    private String idCard;

    private String telephone;

    /**
     * 名字或者手机号模糊匹配
     */
    private String realNameOrTelephoneLike;

    /**
     * 名字模糊查询
     */
    private String realNameLike;

    /**
     * 是否要查询党员
     */
    private Boolean party;

    /**
     * 职务code
     */
    private String postCode;

    /**
     * 需要过滤的组织
     */
    private String ignoreOrgId;

    /**
     * 需要过滤的职务
     */
    private String ignorePostCode;

    /**
     * 需要过滤的人员id
     */
    private List<Long> userIds;

    /**
     * 需要查询人员的id
     */
    private String ids;

    private Integer pageNum = 0;

    private Integer pageSize = 10;
}
