package com.fosung.system.support.system.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 注解类转换
 *
 * @author liuke
 * @date  2022/5/13 9:04
 * @version
*/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysDict {
    /**
     * 字典类型
     * @return
     */
    String dictType();

    String project() default "projectId";

}
