package com.geek.workbench.dto.config;

import com.geek.workbench.dict.TerminalAppCategoryType;
import com.geek.workbench.dict.TerminalType;
import com.geek.workbench.entity.project.ProjectBasicEntity;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import com.google.common.collect.Sets;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@Data
public class AppSearchParamDto {

    private Long terminalId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 身份
     */
    private Set<String> identityIds = Sets.newHashSet();

    /**
     * 组织id
     */
    private Set<String> ordIds = Sets.newHashSet();

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 身份
     */
    private String identityId;

    /**
     * 组织id
     */
    private String orgId;


    /**
     * 地域编码
     */
    private String areaCode;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 应用id
     */
    private String appIds;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 应用分类编码
     */
    private String categoryCode;

    /**
     * 分类类型
     */
    @Enumerated(EnumType.STRING)
    private TerminalAppCategoryType categoryTypeEnum = TerminalAppCategoryType.routine;

    /**
     * 分类类型
     */
    private String categoryType;


    /**
     * 终端
     */
    private TerminalBasicEntity terminalBasicEntity;

    /**
     * 服务类型
     */
    private String serverType;
    /**
     * 项目
     */
    private ProjectBasicEntity projectBasicEntity;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 展示条数
     */
    private String showNum;
    /**
     * 终端类型
     */
    @Enumerated(EnumType.STRING)
    private TerminalType terminalType;
    /**
     * 每页显示条数
     */
    private int pageSize;
    /**
     * 页数
     */
    private int pageNum;

    /**
     * 底部导航ID
     */
    private String navigationBtmId;

    /**
     * 描述:  是否查询全部 默认不查询全部
     * @createDate: 2022/1/21 11:36
     * @author: gaojian
     */
    private Boolean isAll = false;

    private String token;
}
