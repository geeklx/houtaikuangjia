package com.geek.workbench.common;

/**
 * @Description 终端相关提示信息
 * @Author gaojian
 * @Date 2021/11/3 18:48
 * @Version V1.0
 */
public interface TerminalMessageContent {

    String PLATFORM_IS_NULL = "终端运行配置信息存在未选中认证平台的配置类型";

    String AUTHORIZATION_IS_NULL = "终端访问控制信息为空";

    String ASSOCIATION_IS_NULL = "关联对象信息为空";

    String NAVIGATION_IS_EXIST = "此底部导航菜单已关联其他顶部导航";

    String AREA_IS_NULL = "地域信息为空";

    String TOP_NAVIGATION_ID_IS_NULL = "顶部导航主键信息为空";
}
