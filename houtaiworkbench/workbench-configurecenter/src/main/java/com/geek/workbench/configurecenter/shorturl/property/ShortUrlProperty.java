package com.geek.workbench.configurecenter.shorturl.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description 短网址配置
 * @Author gaojian
 * @Date 2022/1/17 10:01
 * @Version V1.0
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app.short.url")
public class ShortUrlProperty {

    /**
     * 描述:  发号器KEY
     * @createDate: 2022/1/17 10:04
     * @author: gaojian
     * @modify:
     */
    private String numberSenderKey = "numberSenderKey";

    /**
     * 描述: 发号器步长
     * @createDate: 2022/1/17 10:07
     * @author: gaojian
     */
    private Long numberSenderStep = 1L;

    /**
     * 描述: 发号器初始值
     * @createDate: 2022/1/17 10:07
     * @author: gaojian
     */
    private String numberSenderInit = "0";

    /**
     * 描述: url 最大长度
     * @createDate: 2022/1/17 10:07
     * @author: gaojian
     */
    private Integer maxLongUrlLength = -1;

    /**
     * 描述: url前缀
     * @createDate: 2022/1/17 10:07
     * @author: gaojian
     */
    private String prefix;

    /**
     * 描述:  重复秒 即多少秒之内同一个url生成的短url相同
     * @createDate: 2022/1/17 10:20
     * @author: gaojian
     */
    private Long repetitionSecond = 600L;

    /**
     * 描述:  默认 redis 保存时间
     * @createDate: 2022/3/18 17:17
     * @author: gaojian
     */
    private Long defaultSaveKeySecond = 300L;

    /**
     * 描述:  长URL可以前缀
     * @createDate: 2022/1/17 10:20
     * @author: gaojian
     */
    private String LongUrlKeyPrefix = "long:url:key";

    /**
     * 描述:  端URL可以前缀
     * @createDate: 2022/1/17 10:20
     * @author: gaojian
     */
    private String shortUrlKeyPrefix = "short:url:key";

}
