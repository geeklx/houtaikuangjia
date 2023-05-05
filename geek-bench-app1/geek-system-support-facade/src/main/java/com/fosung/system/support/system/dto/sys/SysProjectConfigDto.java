package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * 项目配置实体类
 *
 * @author liuke
 * @date  2022/5/5 15:11
 * @version
*/
@Data
public class SysProjectConfigDto extends AppJpaBaseEntity{


    /**
     * 主键id
     */
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 是否开启党员身份校验
     */
    private boolean checkParty;

    /**
     * 身份证是否必填
     */
    private boolean idCardMust;

    /**
     * 远程人脸校验
     */
    private boolean faceCheck;
}
