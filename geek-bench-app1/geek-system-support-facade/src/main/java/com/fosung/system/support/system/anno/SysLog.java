package com.fosung.system.support.system.anno;

import com.fosung.system.support.system.dict.OptLogType;

import java.lang.annotation.*;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2021/12/9 18:37
 */
// 注解放置的目标位置,METHOD是可注解在方法级别上
@Target({ElementType.METHOD})
// 注解在哪个阶段执行
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    // 功能模块
    String optModule() default "";

    // 功能接口名
    String optName() default "";

    // 操作类型
    OptLogType optType();

    // 操作值
    String optValue() default "";
    
}
