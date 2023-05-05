package com.fosung.workbench.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @author lihuiming
 * @className DictDto
 * @description: 字典信息
 * @date 2022/5/614:16
 */
@Data
public class SysDictDto extends    AppBasePageParam  {

        /**
         * 是否删除
         */
        private Boolean del = Boolean.FALSE ;

        /**
         * 字典名称
         */
        private String dictName;
        /**
         * 字典名称
         */
        private String dictCode;

        /**
         * 字典值
         */
        private String dictValue;

        /**
         * 字典值
         */
        private String remark;

        /**
         * 排序
         */
        private Integer num;
        /**
         * 字典值
         */
        private String status;
        /**
         * 父id
         */
        private Long parentId;
}
