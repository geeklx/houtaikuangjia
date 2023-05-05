package com.fosung.workbench.config;

/**
 * 终端信息管理
 *
 * @author fuhao
 * @version V1.0
 * @date 2021/10/21 16:06
 */
public interface TerminalContent {

    // 指纹
    String FINGERPRINT = "fingerprint";
    // false
    String FALSE = "false";
    // true
    String TRUE = "true";
    // 水印姓名
    String CONTACTS_NAME = "contacts_name";
    // 水印手机号
    String CONTACTS_PHONE4 = "contacts_phone4";
    // 水印
    String WATERMARK = "watermark";
    // 终端编码
    String TERMINAL_CODE = "terminalCode";
    // 终端类型
    String TERMINAL_TYPE = "terminalType";
    // 删除标志
    String DEL = "del";
    // 项目名称
    String PACKAGE_NAME = "packageName";
    // 版本号
    String VERSION_NUM = "versionNum";
    // 版本名称
    String VERSION_NAME = "versionName";
    // app版本id
    String APP_VERSION_ID = "appVersionId";
    // 终端应用配置id
    String APP_CONFIG_ID = "appConfigId";
    // 项目id
    String PROJECT_ID = "projectId";
    // 终端id
    String TERMINAL_ID = "terminalId";
    // 应用id
    String APP_ID = "appId";
    // 分类编号
    String CATEGORY_CODE = "categoryCode";
    // 分类名称
    String CATEGORY_NAME = "categoryName";
    // 检查包名是否存在
    String CHECK_PACKAGE_NAME = "此类型包名已经存在!";
    // 检验终端编码
    String IS_EXIST_TERMINAL = "终端编码（唯一标识）已存在！";
    // 检查项目id、终端id、应用id、应用版本id
    String CHECK_PARAM_ID = "项目id、终端id、应用id、应用版本id，不能为空!";
    // 检查终端配置id
    String CHECK_APP_CONFIG_ID = "终端应用配置id参数不能为空!";
    // 检查终端id
    String CHECK_TERMINAL_ID = "终端id不能为空!";
    // 检查分类编码
    String CHECK_CATEGORY_CODE = "分类编码不能为空!";
    // 检查主键id
    String CHECK_PRIMARY_KEY = "id不能为空!";
    // 检查地区
    String CHECK_AREA = "地区不能为空!";
    // 检查是否存在默认显示导航
    String CHECK_DEFALUT_SHOW_NAV = "已存在默认显示的导航";
    // 基础配置redis的key
    String REDIS_TERMINAL_COMMOM_CONFIG_KEY = "workbench:terminal:config:commom";
    // redis过期时间
    int EXPIRES_HOURS = 1;
}
