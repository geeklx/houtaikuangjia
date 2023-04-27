package com.geek.system.pbs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 钉钉消息配置
 *
 * @Author DBin
 * @Date 2020/11/11 10:06
 */
@Data
@ConfigurationProperties(prefix = "app.dingtalk")
public class DingTalkProperties {

    /**
     * 群聊token
     */
    private String accessToken = "9aeb3e77d27d1eaa21fabbcd4bb1131aaf53ae0bf2667095845b6a4c652cc615";

    /**
     * 群聊发送接口
     */
    private String msgUrl = "https://oapi.dingtalk.com/robot/send";

    /**
     * @人员手机号
     */
//    private List<String> atMobiles;

}
