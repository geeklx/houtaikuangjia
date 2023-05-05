package com.fosung.workbench.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Description bean 工具类
 * @Author gaojian
 * @Date 2021/7/2 9:27
 * @Version V1.0
 */
public class CustomUtilBean {

    /**
     * @param source
     * @Return: java.util.Set<java.lang.String>
     * @Decription: 获取对象属性值不为空的属性名
     * @CreateDate: Created in 2021/7/2 9:33
     * @Author: gaojian
     * @Modify:
     */
    public static Set<String> getNotNullPropertyNames(Object source) {

        // 1. 获取对象字段
        Field[] fields = source.getClass().getDeclaredFields();
        Set<String> notEmptyNames = new HashSet<>();

        // 2. 循环字段 获取属性值不为空的属性名添加到数组内
        for (Field field : fields) {

            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(source);
                if (value != null){
                    notEmptyNames.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return notEmptyNames;
    }

    /**
     * 描述:  bean 转 map
     * @createDate: 2021/11/25 19:48
     * @author: gaojian
     * @modify:
     * @param object
     * @return: java.util.Map
     */
    public static Map beanToMap(Object object){
        Map result =  Arrays.stream(BeanUtils.getPropertyDescriptors(object.getClass()))
                .filter( item -> !"class".equals(item.getName()))
                .collect(HashMap::new,
                        (map,pd) -> map.put(pd.getName(), ReflectionUtils.invokeMethod(pd.getReadMethod(),object)),
                        HashMap::putAll);
        return result;
    }
}
