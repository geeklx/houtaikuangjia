package com.fosung.workbench.dict;

/**
 * 类的描述
 *
 * @author fuhao
 * @version V1.0
 * @date 2021/10/15 14:01
 */
public enum StatusType {
    /**
     * 描述:  已发布
     */
    release("release","已发布"),

    /**
     * 描述:  待发布
     */
    noRelease("noRelease","待发布"),

    /**
     * 描述:  已下线
     */
    offline("offline","已下线"),

    /***
     * 描述: 关闭
     * @author fuhao
     * @date 2021/11/3 8:49
     **/
    DISABLE("disable","关闭"),

    /***
     * 描述: 开启
     * @author fuhao
     * @date 2021/11/3 8:49
     **/
    ENABLE("enable","开启");

    private String value;

    private String text;

    StatusType(String value, String text) {
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
