package com.fosung.workbench.AppBean;

import lombok.Data;

/**
 * @author lihuiming
 * @className ThirdInit
 * @description: 三方初始化
 * @date 2022/1/517:12
 */
@Data
public class ThirdInit {
    /**
     * 用户id
     */
    private String userId;
    /**
     * appcode
     */
    private String appCode;
    /**
     * 过期时间
     */
    private Long expireTime;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 手机号
     */
    private String phoneNum;
    /**
     * 男 女
     */
    private String gender;

    /**
     * 用户头像地址
     */
    private String headUrl;
}
