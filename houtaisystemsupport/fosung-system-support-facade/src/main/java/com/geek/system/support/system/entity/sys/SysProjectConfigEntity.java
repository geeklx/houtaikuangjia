package com.geek.system.support.system.entity.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;

/**
 *
 * 项目配置实体类
 *
 * @author liuke
 * @date  2022/5/5 15:11
 * @version
*/
@Entity
@Table(name = "sys_project_config")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysProjectConfigEntity extends AppJpaBaseEntity {



    /**
     * 项目id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 是否开启党员身份校验
     */
    @Column(name = "check_party")
    private Boolean checkParty = false;

    /**
     * 身份证是否必填
     */
    @Column(name = "id_card_must")
    private Boolean idCardMust =false;

    /**
     * 远程人脸校验
     */
    @Column(name = "face_check")
    private Boolean faceCheck = false;

    /**
     * 是否开启手机号脱敏
     */
    @Transient
    private Boolean telephoneDesensitization = false;

    /**
     * 真实姓名是否必填
     */
    @Transient
    private Boolean realNameMast = false;

    /**
     * 主题背景
     */
    @Column(name = "theme")
    private String theme;


    public boolean isTelephoneDesensitization(){
        return telephoneDesensitization==null?false:telephoneDesensitization;
    }

    public boolean isCheckParty(){
        return checkParty==null?false:checkParty;
    }

    public boolean isIdCardMust(){
        return idCardMust==null?false:idCardMust;
    }

    public boolean isFaceCheck(){
        return faceCheck==null?false:faceCheck;
    }
}
