package com.geek.system.support.system.entity.sys;

import com.fosung.framework.common.config.AppConstants;
import com.geek.system.support.system.anno.SysDict;
import com.geek.system.support.system.dict.AuthStatus;
import com.geek.system.support.system.dict.ShelvesType;
import com.geek.system.support.system.dict.UserStatus;
import com.geek.system.support.system.util.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 用户表实体对象
 */
@Entity
@Table(name="sys_user")
@Setter
@Getter
public class SysUserEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity {

    @Column(name="id")
    private Long id;
    /**
     * 描述: 生日
     * @author fuhao
     * @date 2022/2/10 14:48
     **/
    @Column(name = "birthday")
    private String birthday;

    /**
     * 描述: 年龄
     * @author fuhao
     * @date 2022/2/10 14:49
     **/
    @Column(name = "age")
    private String age;

    /**
     * 用户名称
     */
    @Column(name="user_name",nullable = false,unique = true)
    private String userName ;


    /**
     * 密码
     */
    @Column(name="password",nullable = false)
    private String password ;


    /**
     * 电话
     */
    @Column(name="telephone",nullable = false,unique = true)
    private String telephone ;

    /**
     * 座机
     */
    @Column(name = "fixed_telephone")
    private String fixedTelephone;


    /**
     * 姓名
     */
    @Column(name="real_name")
    private String realName ;


    /**
     * 身份证号
     */
    @Column(name="id_card")
    private String idCard ;


    /**
     * 邮箱
     */
    @Column(name="email")
    private String email ;


    /**
     * 注册来源
     */
    @Column(name="source")
    private String source ;


    /**
     * 帐号状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private UserStatus status = UserStatus.VALID;


    /**
     * 组织id
     */
    @Column(name="org_id")
    private Long orgId ;


    /**
     * 组织编码
     */
    @Column(name="org_code")
    private String orgCode ;


    /**
     * 组织名称
     */
    @Column(name="org_name")
    private String orgName ;


    /**
     * 头像地址
     */
    @Column(name="head_img_url")
    private String headImgUrl ;


    /**
     * 帐号状态（0正常 1删除）
     */
    @Column(name="del")
    private Boolean del;


    /**
     * 性别
     */
    @Column(name="sex")
    private String sex ;


    /**
     * 用户类型
     */
    @Column(name="user_type")
    private String userType ;


    /**
     * 昵称
     */
    @Column(name="nick_name")
    private String nickName ;


    /**
     * 身份hash 身份证和姓名
     */
    @Column(name="hash")
    private String hash ;


    /**
     * 民族
     */
    @SysDict(dictType = Constant.NATION)
    @Column(name="nation")
    private String nation;


    /**
     * 政治面貌
     */
    @SysDict(dictType = Constant.POLITICS_STATUS)
    @Column(name="political")
    private String political;

    /**
     * 岗位
     */
    @Column(name="post")
    private String post;

    /**
     * 学历
     */
    @Column(name="education")
    @SysDict(dictType = Constant.EDUCATION)
    private String education;

    /**
     * 签名
     */
    @Column(name = "signature")
    private String signature;

    /**
     * 认证状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_status")
    private AuthStatus authStatus = AuthStatus.AUTH;

    /**
     * 描述: 激活时间
     * @author fuhao
     * @date 2022/1/17 11:10
     **/
    @Column(name = "auth_time")
    @DateTimeFormat(pattern= AppConstants.DATE_TIME_PATTERN)
    private Date authTime;

    /**
     * 身份
     */

    @Column(name = "identity_id")
    private String identityId;


    /**
     * 项目id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 项目id
     */
    @Column(name = "project_code")
    private String projectCode;

    /**
     * 党组织id
     */
    @Column(name = "party_org_id")
    private String partyOrgId;

    /**
     * 父党组织id
     */
    @Column(name = "parent_party_org_id")
    private String parentPartyOrgId;


    /**
     * 党组织编码
     */
    @Column(name = "party_org_code")
    private String partyOrgCode;

    /**
     * 党组织名称
     */
    @Column(name = "party_org_name")
    private String partyOrgName;

    /**
     * 描述: 备注
     * @author fuhao
     * @date 2021/11/30 16:21
     **/

    @Column(name = "remark")
    private String remark;

    /**
     * 描述: 用户角色管理表
     * @author fuhao
     * @date 2021/12/1 9:28
     **/
    @Transient
    private List<SysUserRoleEntity> roles;

    @Transient
    private String roleName;

    /**
     * 描述: 授权类型
     * @author fuhao
     * @date 2021/12/6 9:21
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "shelves_type")
    private ShelvesType shelvesType;

    /**
     * 描述: 城市名称
     * @author fuhao
     * @date 2022/2/11 13:41
     **/
    @Column(name = "city_name")
    private String cityName;

    /**
     * 描述: 指纹
     * @author fuhao
     * @date 2022/2/11 13:41
     **/
    @Column(name = "fingerprint")
    private Boolean fingerprint = false;

    /**
     * 描述: 灯塔用户id
     * @author fuhao
     * @date 2022/2/14 16:07
     **/
    @Column(name = "dt_user_id")
    private String dtUserId;

    /**
     * wxopenId
     */
    @Column(name = "wx_open_id")
    private String wxOpenId;

    /**
     * wxUnitId
     */
    @Column(name = "wx_unit_id")
    private String wxUnitId;

    @Transient
    private String faceStatus;

    /**
     * 描述: 人脸同步容联状态
     * @author fuhao
     * @date 2022/2/14 16:46
     **/
    @Transient
    private String rlFaceStatus;

    /**
     * 描述: 岗位信息（用户权限中心）
     * @author fuhao
     * @date 2022/2/16 13:57
     **/
    @Transient
    private List<SysUserPostScopeEntity> posts;

    /**
     * 描述: 岗位信息（肥城项目）
     * @author fuhao
     * @date 2022/2/16 13:58
     **/
    @Transient
    private List<SysUserPostScopeEntity> userPosts;

    /**
     * 描述: 头像（肥城项目）
     * @author fuhao
     * @date 2022/2/16 14:33
     **/
    @Transient
    private String headUrl;

    /**
     * 描述: 联系方式（肥城项目）
     * @author fuhao
     * @date 2022/2/16 14:41
     **/
    @Transient
    private String contactDetails;

    /**
     * 描述: 灯塔用户id（肥城项目）
     * @author fuhao
     * @date 2022/2/17 11:09
     **/
    @Transient
    private String outUserId;

    @Transient
    private String roleType;
}