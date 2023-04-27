package com.geek.system.pbs.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrgDto {

    // 组织id
    private String id;

    // 组织名称
    @NotNull(message = "组织名称不能为空")
    private String name;

    // 是否为最下级组织(叶子节点),默认为false
    @NotNull(message = "是否最下级组织不能为空")
    private boolean leaf = false;

    // 上级组织id
    @NotNull(message = "上级组织不能为空")
    private String parentId;

    // 排序(当前等级的排序)
    private Integer num;

    // 是否为根节点(默认为false)
    private boolean sourceRoot = false;

}
