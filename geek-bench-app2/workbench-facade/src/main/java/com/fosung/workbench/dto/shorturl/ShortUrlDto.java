package com.fosung.workbench.dto.shorturl;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description 短网址传输类
 * @Author gaojian
 * @Date 2022/1/17 11:43
 * @Version V1.0
 */
@Data
public class ShortUrlDto {

    /**
     * 长网址url
     */
    @NotBlank( message = "长网址URL不能为空")
    private String longUrl ;

    /**
     * 描述:  默认有效时间
     * @createDate: 2022/3/18 17:24
     * @author: gaojian
     */
    private Long validityTime;
}
