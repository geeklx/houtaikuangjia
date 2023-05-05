package com.fosung.workbench.common;

/**
 * @Description 项目状态枚举值
 * @Author gaojian
 * @Date 2021/10/13 18:07
 * @Version V1.0
 */
public enum ProjectStatusEnum {

    /**
     * 描述:  发布
     * @createDate: 2021年10月13日19:32:27
     * @author: gaojian
     * @modify:
     */
    RELEASE("1","发布"),

    /**
     * 描述:  待发布
     * @createDate: 2021年10月13日19:32:23
     * @author: gaojian
     * @modify:
     */
    TO_RELEASE("0","待发布");

    private String value;

    private String text;

    ProjectStatusEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
