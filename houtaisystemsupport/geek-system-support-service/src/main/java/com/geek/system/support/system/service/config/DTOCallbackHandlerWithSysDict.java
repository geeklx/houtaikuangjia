package com.geek.system.support.system.service.config;

import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilMap;
import com.fosung.framework.common.util.UtilString;
import com.geek.system.support.system.anno.SysDict;
import com.geek.system.support.system.service.sys.SysDictDataService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author liuke
 * @date  2022/5/13 10:03
 * @version
*/
@Component
@Slf4j
public class DTOCallbackHandlerWithSysDict implements DTOCallbackHandler {


    @Autowired
    private SysDictDataService sysDictDataService;
    private static final String last = "_dict";


    /**
     * 进行字典项转换的处理
     *
     * @param dtoMap
     * @param itemClass
     */
    @Override
    public void doHandler(Map<String, Object> dtoMap, Class<?> itemClass) {
        if (UtilMap.isEmpty(dtoMap)) {
            return;
        }
        Field[] fields = itemClass.getDeclaredFields();
        List<Map<String, String>> list = Lists.newArrayList();
        Field field;
        Map<String, String> map;
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            SysDict annotation = field.getAnnotation(SysDict.class);
            if (annotation == null) {
                continue;
            }
            map = Maps.newHashMapWithExpectedSize(4);
            String dictType = annotation.dictType();
            if (UtilString.isNotBlank(dictType)) {
                map.put("dictType", dictType);
            }
            //租户id
            String project = annotation.project();
            if (UtilString.isNotBlank(project)) {
                Object o = dtoMap.get(project);
                if (o instanceof Long) {
                    map.put("projectId", o.toString());
                }
            }

            String fieldName = field.getName();
            map.put("fieldName", fieldName);
            Object o = dtoMap.get(fieldName);
            if (o instanceof String) {
                String s = o.toString();
                map.put("dictValue", s);
            }
            list.add(map);
        }
        //获取字典值
        if (UtilCollection.isNotEmpty(list)) {
            for (Map<String, String> dict : list) {
                if (dict.get("dictValue") == null) {
                    continue;
                }
                dtoMap.put(dict.get("fieldName") + last, sysDictDataService.getItemName(dict.get("dictValue"),dict.get("dictType"),Long.valueOf(dict.get("projectId"))));
            }
        }
    }

}
