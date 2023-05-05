package com.fosung.system.pbs.dto.dingding;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author hi dbin
 * @Date 2020/7/1 14:46
 **/
@Getter
@Setter
public class DingtalkToken {

    private int errcode =-1;
    private String access_token;
    private String errmsg;
    private long expires_in = 7200L;

}
