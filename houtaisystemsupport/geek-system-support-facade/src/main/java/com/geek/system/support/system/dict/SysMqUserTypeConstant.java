package com.geek.system.support.system.dict;

import com.fosung.framework.common.support.AppRuntimeDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2022/2/28 14:59
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum SysMqUserTypeConstant implements AppRuntimeDict {

    addUser("新增"),updateUser("修改"),deleteUser("删除"),active("激活人员");
    public String remark;
}