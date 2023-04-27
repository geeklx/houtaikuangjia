package com.geek.system.support.util.easyexcel.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDetailDto extends BaseRowModel {

    @ExcelProperty(index = 2)
    private String projectCode;

    @ExcelProperty(index = 3)
    private String orgName;

    @ExcelProperty(index = 5)
    private String userName;

    @ExcelProperty(index = 6)
    private String realName;

    @ExcelProperty(index = 7)
    private String telephone;

    @ExcelProperty(index = 8)
    private String idCard;

    @ExcelProperty(index = 9)
    private String password;

}