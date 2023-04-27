package com.geek.system.support.system.dict;

public  class RedisConstant {

    /**
     * 过期
     */
    public static final int EXPIRES_HOURS = 6;

    /**
     * 过期
     */
    public static final int EXPIRES_MINUTES = 10;

    /**
     * 项目信息
     */
    public static final String PROJECT_DETAIL = "system:project:projectCode:";

    /**
     * 项目配置
     */
    public static final String PROJECT_CONFIG = "system:project:config:projectId:";

    /**
     * 项目下全部通讯录
     */
    public static final String PROJECT_ALL_MAIL = "system:project:user:mail:";

    /**
     * 根据idcard获取用户信息
     */
    public static final String SYSTEM_USER_INFO = "system:user:info:";

    /**
     * 根据用户名获取用户信息
     */
    public static final String SYSTEM_USER_USERNAME_INFO = "system:user:username:info:";

    /**
     * 根据身份证号获取用户信息
     */
    public static final String SYSTEM_USER_IDCARD_INFO = "system:user:username:info:";

    /**
     * 根据手机号获取用户信息
     */
    public static final String SYSTEM_USER_TELEPHONE_INFO = "system:user:telephone:info:";

    /**
     * 根据用户id获取用户信息
     */
    public static final String SYSTEM_USER_USERID_INFO = "system:user:userid:info:";

    /**
     * 工作台获取应用列表
     */
    public static final String SYSTEM_RESOURCE_APP_INFO = "system:resource:app:info:";

    /**
     * 校验灯塔
     */
    public static final String CHECK_DT_USER = "checkdtuser";
}
