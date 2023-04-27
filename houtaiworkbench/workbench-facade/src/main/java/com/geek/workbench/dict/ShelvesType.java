package com.geek.workbench.dict;

/**
 * 类的描述
 *
 * @author fuhao
 * @version V1.0
 * @date 2021/10/19 15:30
 */
public enum ShelvesType {
    user("user","用户"),
    identity("identity","身份"),
    org("org","组织"),
    role("role","角色"),
    area("area","地区");

    private String name;

    private String value;

    ShelvesType(String value,String name){
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
