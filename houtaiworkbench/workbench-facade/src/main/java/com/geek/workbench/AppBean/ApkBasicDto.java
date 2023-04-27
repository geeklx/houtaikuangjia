package com.geek.workbench.AppBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lihuiming
 * @className ApkBasicDto
 * @description:
 * @date 2021/11/1519:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApkBasicDto extends  ApkBasic{
    private Long id;
}
