package com.fosung.system.support.system.dto.sys;
import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.system.support.system.dict.AuthStatus;
import com.fosung.system.support.system.dict.UserStatus;
import com.fosung.system.support.system.util.ProjectConstant;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * @version V1.0
 * @Description：用户表Dto
 */
@Data
@Alias("collections")
public class SysUserDto extends AppBasePageParam {

    /**
     * id
     */
    private  Long id;

    /**
     * 用户名称
     */
    private String userName ;

    /**
     * 描述: 用户名称模糊搜索
     * @author fuhao
     * @date 2021/12/6 11:18
     **/
    private String userNameL;

    /**
     * 密码
     */
    private String password ;

    /**
     * 密码确认
     */
    private String surePassword ;

    /**
     * 电话
     */
    private String telephone ;

    /**
     * 描述: 电话模糊搜索
     * @author fuhao
     * @date 2021/12/6 11:37
     **/
    private String telephoneL;

    /**
     * 姓名
     */
    private String realName ;

    /**
     * 姓名
     */
    private String realNameL ;
    /**
     * 身份证号
     */
    private String idCard ;

    /**
     * 身份证号模糊
     */
    private String idCardL ;
    /**
     * 邮箱
     */
    private String email ;
    /**
     * 注册来源
     */
    private String source ;

    /**
     * 账号状态
     */
    @Enumerated(EnumType.STRING)
    private UserStatus status;
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
    private Boolean del ;
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
     * 认证状态
     */
    @Enumerated(EnumType.STRING)
    private AuthStatus authStatus = AuthStatus.NOT_AUTH;

    /**
     * 短信验证码
     */
    private String messageCode;

    /**
     * 原密码
     */
    private String originPassword;

    /***
     * 描述: 身份
     * @author fuhao
     * @date 2021/11/24 16:11
     **/
    private String identityId;

    /**
     * 项目id
     * @author fuhao
     * @date 2021/11/24 16:11
     */
    private Long projectId;

    /**
     * 党组织id
     * @author fuhao
     * @date 2021/11/24 16:11
     */
    private Long partyOrgId;


    /**
     * 党组织编码
     * @author fuhao
     * @date 2021/11/24 16:11
     */
    private String partyOrgCode;

    /**
     * 党组织名称
     * @author fuhao
     * @date 2021/11/24 16:11
     */
    private String partyOrgName;

    /*
     * 描述: 开始时间
     * @author fuhao
     * @date 2021/11/25 10:44
     **/
    @DateTimeFormat(pattern= ProjectConstant.FORMAT_SECOND)
    private Date startTime;

    /*
     * 描述: 结束时间
     * @author fuhao
     * @date 2021/11/25 10:44
     **/
    @DateTimeFormat(pattern= ProjectConstant.FORMAT_SECOND)
    private Date endTime;

    /**
     * 描述:
     * @author fuhao
     * @date 2022/2/17 16:41
     **/
    private String postCode;
}