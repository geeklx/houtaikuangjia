package com.geek.workbench.common;

/**
 * @Description 应用类型
 * @Author gaojian
 * @Date 2021/10/15 14:47
 * @Version V1.0
 */
public enum AppCategory {

    /**
     * 描述:  发布
     * @createDate: 2021年10月13日19:32:27
     * @author: gaojian
     * @modify:
     */
    APP("app","内部应用"),

    /**
     * 描述:  终端应用
     * @createDate: 2021年10月13日19:32:23
     * @author: gaojian
     * @modify:
     */
    TERMINAL("terminal","终端应用");

    private String value;

    private String text;

    AppCategory(String value, String text) {
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
