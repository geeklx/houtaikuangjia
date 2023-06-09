package com.fosung.system.support.util.easyexcel.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class UserInfoDto extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String realName;

    @ExcelProperty(index = 1)
    private String idCard;

    @ExcelProperty(index = 2)
    private String roleName;

}