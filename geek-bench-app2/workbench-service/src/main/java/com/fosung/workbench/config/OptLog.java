package com.fosung.workbench.config;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 * @author 高健
 */
// 注解放置的目标位置,METHOD是可注解在方法级别上
@Target(ElementType.METHOD)
// 注解在哪个阶段执行
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    /**
     * 描述:  操作模块
     * @createDate: 2021/10/15 16:15
     * @author: gaojian
     * @modify:
     * @param
     * @return: java.lang.String
     */
    String optModule() default "";

    /**
     * 描述:  操作功能名称
     * @createDate: 2021/10/15 16:15
     * @author: gaojian
     * @modify:
     * @param
     * @return: java.lang.String
     */
    String optName() default "";

    /**
     * 描述:  操作类型
     * @createDate: 2021/10/15 16:15
     * @author: gaojian
     * @modify:
     * @param
     * @return: java.lang.String
     */
    String optType() default "";
}