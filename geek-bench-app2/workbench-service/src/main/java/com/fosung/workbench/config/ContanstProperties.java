package com.fosung.workbench.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app.workbench")
@Component
@Setter
@Getter
public class ContanstProperties {

    /**
     * 我的应用查看更多跳转地址
     */
    private String myAppLink = ".hs.act.slbapp.BjyyAct{act}?condition=login&query1={s}aaaaa&query2={s}2a&query3={s}3a";

    /**
     * 我的应用查看更多跳转地址
     */
    private String allAppLink = ".hs.act.slbapp.ShouyeCateAct1{act}?query1={s}1&query2={s}全部应用&query3={s}5";

    /**
     * 过期时间单位s
     */
    private int expireTime = 4;


}
