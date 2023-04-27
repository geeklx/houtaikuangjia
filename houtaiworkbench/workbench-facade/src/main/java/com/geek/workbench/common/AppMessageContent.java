package com.geek.workbench.common;

/**
 * @Description 消息内容
 * @Author gaojian
 * @Date 2021/10/13 16:57
 * @Version V1.0
 */
public interface AppMessageContent {

    String APP_ID_IS_NULL = "未获取到应用主键信息";

    String APP_TYPE_IS_NULL = "未获取到应用类型信息";

    String APP_CODE_IS_EXIST = "应用编码已存在";

    String APP_IS_NOT_EXIST = "应用信息不存在！";

    String PARAMS_IS_NULL = "参数信息为空！";

    String VERSION_NUM_SMALL = "版本号小于或等于历史版本号！";

    String APP_VERSION_IS_NULL = "应用版本信息不存在！";

    String PACKAGE_NAME_IS_EXIST = "应用包名已存在！";

}
