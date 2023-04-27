package com.geek.system.pbs.dto.support.dt;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Desc
 * @Author zyy
 * @Date 2021/9/14 18:35
 */
@Data
@NoArgsConstructor
public class CloudPostDto {

    /**
     * 组织id
     */
    private String orgId;

    /**
     * 组织id
     */
    private List<String> orgIds;

    /**
     * 组织code
     */
    private String orgCode;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String realName;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 职务名称
     */
    private String positionInfoName;

    /**
     * 职务编码
     */
    private String positionInfoCode;

    /**
     * 职务对应的身份名称
     */
    private String identityName;

    /**
     * 职务对应身份编码
     */
    private String identityCode;

}
