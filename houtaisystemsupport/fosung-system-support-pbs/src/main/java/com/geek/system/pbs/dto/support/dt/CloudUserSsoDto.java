package com.geek.system.pbs.dto.support.dt;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2022/2/15 14:44
 */
@Data
@NoArgsConstructor
public class CloudUserSsoDto {

    /**
     * 描述: 用户id
     * @author fuhao
     * @date 2022/2/15 14:46
     **/
    private String userId;

    /**
     * 描述: 手机号
     * @author fuhao
     * @date 2022/2/15 14:46
     **/
    private String telephone;

    /**
     * 描述: 密码
     * @author fuhao
     * @date 2022/2/15 14:46
     **/
    private String password;

    /**
     * 描述: 确认密码
     * @author fuhao
     * @date 2022/2/15 14:46
     **/
    private String surePassword;

    /**
     * 描述: 验证码
     * @author fuhao
     * @date 2022/2/15 14:49
     **/
    private String messageCode;

    /**
     * 描述: 原密码
     * @author fuhao
     * @date 2022/2/15 14:50
     **/
    private String originPassword;

    /**
     * 描述: 认证状态
     * @author fuhao
     * @date 2022/2/15 15:36
     **/
    private String authStatus;

    /**
     * 描述: token
     * @author fuhao
     * @date 2022/2/15 15:38
     **/
    private String token;
    
    /**
     * 描述: 组织id集合
     * @author fuhao
     * @date 2022/2/15 16:16
     **/
    private List<Long> orgIds;
}
