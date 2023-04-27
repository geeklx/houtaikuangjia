package com.geek.system.pbs.dto.support.dt;

import lombok.Data;

@Data
public class UserTranslationDto {

    private String id;
    private String realName;  // 真实姓名
    private String telephone;  //注册手机号
    private String political;  //政治面貌
    private String businesSource;  // 来源
    private String orgId;  // 组织id
    private String idCard;  // 身份证号
    private String headUrl;  // 头像
    private String sex;  // 性别
    private String identity;  // 身份
    private String post;  //职位
    private String outUserId;

    /**
     * 民族
     */
    private String nation;

    /**
     * 学历
     */
    private String education;

    private String operateType;  // addUser-新增用户 updateUser-更新用户  deleteUser-删除用户
}
