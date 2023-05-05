package com.fosung.workbench.common;

/**
 * @Description 消息内容
 * @Author gaojian
 * @Date 2021/10/13 16:57
 * @Version V1.0
 */
public interface MessageContent {

    /**
     * 描述:  未获取到主键信息提示
     * @createDate: 2021/10/13 16:57
     * @author: gaojian
     */
    String ID_IS_NULL = "未获取到主键信息";

    String LOGIN_USER_IS_NULL = "登录用户信息不存在，请确认！";

    String PROJECT_CODE_IS_EXIST = "项目编码已存在，项目编码为唯一编码，请修改项目编码！";

    String PROJECT_MANAGER_IS_EXIST = "用户已经是此项目管理员，无需重复设置！";

    String DICT_TYPE_IS_NULL = "字典类型不能为空";

    String ROLE_ID_IS_NULL = "角色主键信息为空";

    String APP_ID_IS_NULL = "未获取到应用主键信息";

    String APP_IS_NOT_EXIST = "应用信息不存在！";

    String TERMINAL_IS_NOT_EXIST = "终端信息不存在！";

    String DATA_SOURCE_NOT_EXIST = "数据来源信息未匹配到！";

    String USER_ID_IS_NULL = "用户主键信息为空！";

    String PROJECT_ID_IS_NULL = "项目主键信息为空";

    String VERSION_NAME_IS_EXIST = "版本名称已存在!";

    String PARAMS_IS_NULL = "参数信息为空!";

    String PARAMS_CONVERT_ERROR = "参数转换错误，请确认请求信息是否符合要求!";

    String API_CATEGORY_IS_NULL = "接口分类信息为空!";

    String API_GROUP_ID_IS_NULL = "接口所属组主键信息为空!";

    String TERMINAL_API_IS_EXIST = "终端已授权此配置信息，无需重复授权!";

    String TERMINAL_ID_IS_NULL = "终端主键信息为空!";

    String IMAGE_TYPE_IS_NULL = "图片类型信息为空!";

    String FILE_DISPOSE_ERROR = "文件处理失败!";

    String APP_PACKAGE_NAME_INCONFORMITY = "包名不一致!";

    String APP_PACKAGE_NAME_IS_NULL = "包名不存在！";

    String APP_TYPE_IS_NULL = "应用类型不存在！";

    String APP_VERSION_IS_EXIST = "该应用包已存在，请返回列表进行版本升级";

    String CONFIG_INFO_IS_EXIST = "存在相同的配置信息!";

    String TERMINAL_RESOURCE_PLATFORM_IS_NULL = "终端资源配置类型未选定认证平台!";

    String TERMINAL_CONTACTS_PLATFORM_IS_NULL = "终端通讯录配置类型未选定认证平台!";

    String REQUEST_TYPE_IS_NULL = "请求类型信息为空!";

    String THIRD_PARTY_IS_NULL = "未获取到指定的服务处理方！";

    String DICT_CODE_IS_EXIST = "字典编码已存在，请修改字典编码！";

    String NOTICE_TITLE_IS_EXIST = "公告标题已存在，请修改公告标题！";

}
