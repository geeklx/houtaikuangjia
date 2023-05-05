package com.fosung.system.pbs.common;

import com.alibaba.fastjson.JSON;
import com.fosung.system.pbs.config.DingTalkProperties;
import com.fosung.system.pbs.dto.dingding.DingtalkResult;
import com.google.api.client.util.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mzlion.easyokhttp.HttpClient;
import com.mzlion.easyokhttp.response.callback.CallbackAdaptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 钉钉消息
 *
 * @Author DBin
 * @Date 2020/11/11 9:35
 */
@Slf4j
@Component
public class DingDingMsg {

    /**
     * 当前系统名称
     */
    @Value(value = "${spring.application.name}")
    String applicationName;

    @Autowired
    private DingTalkProperties dingTalkProperties = new DingTalkProperties();

    /**
     *
     * @param errorTag 业务标签
     * @param errorMsg 错误消息
     * @param content 消费消息内容
     * @param atMobiles @手机号 为空则@全体
     */
    public void sendMessage(String errorTag, String errorMsg, String content, List<String> atMobiles) {
        boolean isAtAll = false;

        // 无手机号则 @所有人
        if (CollectionUtils.isEmpty(atMobiles)) {
            isAtAll = true;
        }

        StringBuilder message = new StringBuilder();

        message.append("系统名称: ");
        message.append(applicationName);

        message.append(" tag: ");
        message.append(errorTag);

        message.append(" 错误信息: ");
        message.append(errorMsg);

        message.append(" 消息内容: ");
        message.append(content);

        sendMessage(message.toString(), isAtAll, atMobiles);
    }

    public void sendMessage(String content, boolean isAtAll) {

        sendMessage(content, isAtAll, null);
    }

    /**
     * 消息发送
     */
    public void sendMessage(String content, boolean isAtAll, List<String> atMobiles) {

        if (atMobiles == null) {
            atMobiles = Lists.newArrayList();
        }
        Set<String> mobileSet = Sets.newHashSet(atMobiles);

        // 2. 参数
        Map<String, Object> params = new HashMap<>();
        params.put("msgtype", "text");

        // 消息内容  注: 消息这两个字是钉钉机器人的校验权限
        Map<String,String> text = Maps.newHashMap();
        text.put("content", "消息: " + content);
        params.put("text", text);

        // 是否@所有人
        Map<String,Object> at = Maps.newHashMap();
        at.put("isAtAll", isAtAll);
        at.put("atMobiles", mobileSet);

        params.put("at", at);

        // 3. 调用http发送请求
        sendAsyncPostJsonHttp(JSON.toJSON(params));
    }

    /**
     * 访问http 参数使用json方式
     */
    private DingtalkResult sendPostJsonHttp(Object params) {
        return HttpClient.textBody(dingTalkProperties.getMsgUrl())
                .queryString("access_token", dingTalkProperties.getAccessToken())
                .json(params)
                .execute()
                .asBean(DingtalkResult.class);
    }

    /**
     * 异步发送请求
     */
    private void sendAsyncPostJsonHttp(Object params) {
        HttpClient.textBody(dingTalkProperties.getMsgUrl())
                .queryString("access_token", dingTalkProperties.getAccessToken())
                .json(params)
                .execute(new CallbackAdaptor<String>(){

                    @Override
                    public void onError(Call call, Exception exception) {
                        log.error("异常: 调用dingding消息接口失败");
                    }

                });
    }

    public static void main(String[] args) {
        new DingDingMsg().sendMessage("12", true);
    }

}
