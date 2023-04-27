package com.geek.system.pbs.dto;

import lombok.Data;

import java.util.List;

/**
 * @Desc
 * @Author zyy
 * @Date 2021/1/7 15:08
 */
@Data
public class UserPostDto {

    private Long userId;

    private Long orgId;

    /**
     * 党组织id，党务职务同步必传
     */
    private String outOrgId;

    /**
     * 同步的职务类型：PARTY：党委职务
     */
    private String postType;

    /**
     * 职务code
     */
    private String post;

    /**
     * 系统来源
     */
    private String businessSource;

    /**
     * 新增的职务
     */
    private List<UserPostDto> addPost;

    /**
     * 删除的职务
     */
    private List<UserPostDto> deletePost;
}
