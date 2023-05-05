package com.fosung.workbench.dict;

/**
 * 发布范围
 *
 * @author fuhao
 * @version V1.0
 * @date 2021/10/15 14:01
 */
public enum RangeType {
    /**
     * 描述:  全部
     */
    all("all","全部"),

    /**
     * 描述:  指定范围
     */
    assigned("assigned","指定范围");


    private String value;

    private String text;

    RangeType(String value, String text) {
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
