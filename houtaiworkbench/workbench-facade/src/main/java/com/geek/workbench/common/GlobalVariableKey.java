package com.geek.workbench.common;

/**
 * @Description 全局变量key
 * @Author gaojian
 * @Date 2021/10/15 8:32
 * @Version V1.0
 */
public interface GlobalVariableKey {

    /**
     * 描述:  app 外部应用主键
     * @createDate: 2021/10/15 8:34
     * @author: gaojian
     */
    String APP_EXTERNAL_ID = "externalId";

    /**
     * 描述:  数据来源字典类型
     * @createDate: 2021/10/15 8:59
     * @author: gaojian
     */
    String DATA_SOURCE_DICT = "dataSource";

    /**
     * 描述:  字典值
     * @createDate: 2021/10/15 9:24
     * @author: gaojian
     */
    String DICT_VALUE = "dictValue";

    /**
     * 描述:  字典值
     * @createDate: 2021/10/15 9:24
     * @author: gaojian
     */
    String DICT_LABEL = "dictLabel";

    /**
     * 描述:  终端ID
     * @createDate: 2021/10/15 9:57
     * @author: gaojian
     */
    String TERMINAL_ID= "terminalId";

    /**
     * 描述:  userId
     * @createDate: 2021/10/15 9:57
     * @author: gaojian
     */
    String USER_ID= "userId";

    String APP_ID= "appId";

    String INSTALL_TYPE = "installType";

    /**
     * 终端认证配置ak
     */
    String AUTHORIZATION_AK = "ak";

    /**
     * 终端认证配置sk
     */
    String AUTHORIZATION_SK = "sk";

    /**
     *终端广告页
     */
    String  ADVERTS_IMAGE="image";

    /**
     * 主题配置
     */
    String  THEME_THEME = "theme";
    /**
     * 语言配置配置
     */
    String  LANGUAGE_LANGUAGE = "language";

    /**
     *置灰
     */
    String  SHORT_STYLE_STYLE = "style";

    /**
     *开始时间
     */
    String  SHORT_STYLE_START_TIME = "start_time";

    /**
     *
     */
    String  SHORT_STYLE_END_TIME = "end_time";

    /**
     *终端配置分享客户端logo的code
     */
    String SHARE_CLIENT_URL= "client_url";
    /**
     *终端配置分享底部logo的code
     */
    String SHARE_BOTTOM_URL = "bottom_url";
    /**
     *安卓下载地址
     */
    String SHARE_ANDROID_URL = "android_url";
    /**
     *IOS客户端下载地址
     */
    String SHARE_IOS_URL = "ios_url";
    /**
     *Android hosts
     */
    String SHARE_ANDROID_HOSTS = "android_hosts";
    /**
     *Android URL Schemes
     */
    String SHARE_ANDROID_URL_SCHEMES = "android_url_schemes";
    /**
     *IOS URL Schemes
     */
    String SHARE_IOS_URL_SCHEMES = "ios_url_schemes";
    /**
     *分享链接域名
     */
    String SHARE_DOMAIN = "domain";
    /**
     *分享渠道
     */
    String SHARE_CHANNEL = "channel";

    /**
     * 客服电话
     */
    String PHONE_CUSTOMER = "customer";
    /**
     * 描述:  应用安装数前缀
     * @createDate: 2021/10/15 14:02
     * @author: gaojian
     */
    String INSTALL_REDIS_PATH_PREFIX = "workbench:install:application";

    /**
     * 描述:  应用下载数前缀
     * @createDate: 2021/10/15 14:02
     * @author: gaojian
     */
    String DOWNLOAD_REDIS_PATH_PREFIX = "workbench:download:application";

    /**
     * 描述:  包名
     */
    String APP_PACKAGE_NAME = "appPackageName";

    /**
     * 描述:  应用类型
     */
    String APP_TYPE = "appType";

    /**
     * 描述:  应用id
     */
    String ID = "id";

    /**
     * 描述:  应用名称
     */
    String APP_NAME = "appName";

    /**
     * 描述:  项目id
     */
    String PROJECT_ID = "projectId";

    /**
     * 描述:  项目名称
     */
    String PROJECT_NAME = "projectName";

    /**
     * 描述:  父ID
     * @createDate: 2021/10/28 17:36
     * @author: gaojian
     */
    Long PRATENT_ID = 0L;

    /**
     * 描述:  配置类型
     * @createDate: 2021/10/28 17:36
     * @author: gaojian
     */
    String CONFIG_TYPE = "config";


    String IDENTITY = "identity";

    String CHECKED = "checked";

    String UNCHECK = "uncheck";

    String USER_NAME = "userName";

    String PARENT_ID = "parentId";

    String ORG_TYPE = "orgType";

    String PROJECT_CODE = "projectCode";

    /**
     * 描述:  数据来源字典类型
     * @createDate: 2021/10/15 8:59
     * @author: gaojian
     */
    String DATA_SOURCE_DICT_NAME = "dataSource_dict";

    String TURN_NAME = "turnName";

    String TURN_URL = "navigationTurnUrl";

    String NAVIGATION_BTM_ID = "navigationBtmId";

    String ORGAN_ID = "orgId";

    String AREA = "area";

    //视频会议appId
    String MEETING_APP_ID = "c7459035704c54c30cd15409130870de";
    //视频会议token
    String MEETING_APP_TOKEN ="345bff8501af85f02ba98590da2f3dea";
    //视频会议userId
    String MEETING_USER_ID = "18965656534";
    //视频会议token
    String MEETING_PWD ="321@@Qwe";
}
