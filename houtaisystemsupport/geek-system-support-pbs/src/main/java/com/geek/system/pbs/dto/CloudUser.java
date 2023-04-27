package com.geek.system.pbs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class CloudUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String realName;

    private String telephone;

    private String headUrl;

    private String post;

    private Long orgId;

    private String hash;

    private String orgName;

    private String orgCode;

    private String outId;

    private String outName;

    private String sex;

    /**
     * 民族
     */
    private String nation;

    /**
     * 政治面貌
     */
    private String political;

    /**
     * 学历
     */
    private String education;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 联系方式
     */
    private String contactDetails;

    // 个人中心的用户id，组织生活使用
    private Long jnId;

    /**
     * 灯塔userId
     */
    private String outUserId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("jDate")
    private Date joinPartyDate;

    /**
     * 用户职务的集合，包含任职组织、职务
     */
    private List<Map<String, Object>> userPosts;

    private String authStatus;
    private String status;
    private String password;
    private String businessSource;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date authTime;

}
