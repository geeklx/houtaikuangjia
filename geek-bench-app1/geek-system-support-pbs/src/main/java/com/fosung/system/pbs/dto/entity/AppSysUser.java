package com.fosung.system.pbs.dto.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 用户表实体对象
 */
@Setter
@Getter
public class AppSysUser {

    /**
     * 描述: 生日
     * @author fuhao
     * @date 2022/2/10 14:48
     **/
    private String birthday;

    /**
     * 描述: 年龄
     * @author fuhao
     * @date 2022/2/10 14:49
     **/
    private String age;

    /**
     * 用户名称
     */
    private String userName ;


    /**
     * 密码
     */
    private String password ;


    /**
     * 电话
     */
    private String telephone ;

    /**
     * 座机
     */
    private String fixedTelephone;


    /**
     * 姓名
     */
    private String realName ;


    /**
     * 身份证号
     */
    private String idCard ;


    /**
     * 邮箱
     */
    private String email ;


    /**
     * 注册来源
     */
    private String source ;


    /**
     * 帐号状态
     */
    @Enumerated(EnumType.STRING)
    private AppUserStatus status ;


    /**
     * 组织id
     */
    private Long orgId ;


    /**
     * 组织编码
     */
    private String orgCode ;


    /**
     * 组织名称
     */
    private String orgName ;


    /**
     * 头像地址
     */
    private String headImgUrl ;


    /**
     * 帐号状态（0正常 1删除）
     */
    private Boolean del;


    /**
     * 性别
     */
    private String sex ;


    /**
     * 用户类型
     */
    private String userType ;


    /**
     * 昵称
     */
    private String nickName ;


    /**
     * 身份hash 身份证和姓名
     */
    private String hash ;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 民族
     */
    private String nation;


    /**
     * 政治面貌
     */
    private String political;

    /**
     * 岗位
     */
    private String post;

    /**
     * 学历
     */
    private String education;

    /**
     * 签名
     */
    private String signature;

    /**
     * 认证状态
     */
    @Enumerated(EnumType.STRING)
    private AppAuthStatus authStatus = AppAuthStatus.AUTH;

    /**
     * 描述: 激活时间
     * @author fuhao
     * @date 2022/1/17 11:10
     **/
    private String authTime;

    /**
     * 身份
     */

    private String identityId;


    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目id
     */
    private String projectCode;

    /**
     * 党组织id
     */
    private Long partyOrgId;

    /**
     * 父党组织id
     */
    private String parentPartyOrgId;


    /**
     * 党组织编码
     */
    private String partyOrgCode;

    /**
     * 党组织名称
     */
    private String partyOrgName;

    /**
     * 描述: 备注
     * @author fuhao
     * @date 2021/11/30 16:21
     **/

    private String remark;

    /**
     * 描述: 授权类型
     * @author fuhao
     * @date 2021/12/6 9:21
     **/
    @Enumerated(EnumType.STRING)
    private AppShelvesType shelvesType;

    /**
     * 描述: 城市名称
     * @author fuhao
     * @date 2022/2/11 13:41
     **/
    private String cityName;

    /**
     * 描述: 指纹
     * @author fuhao
     * @date 2022/2/11 13:41
     **/
    private Boolean fingerprint = false;

    private String createUserId ;


    private String createDatetime ;

    private String lastUpdateUserId ;

    private String lastUpdateDatetime ;

    /**
     * 主键
     */
    private Long id;


}