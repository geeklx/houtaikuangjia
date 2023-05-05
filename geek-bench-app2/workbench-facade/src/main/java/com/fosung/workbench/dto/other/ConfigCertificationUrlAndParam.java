package com.fosung.workbench.dto.other;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * 描述:  认证配置表dto
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Data
public class ConfigCertificationUrlAndParam  {


    /**
     * 认证地址
     */
	private UrlAndParam authorized;


    /**
     * 资源地址
     */
	private UrlAndParam resource;

    /**
     * 统一存储
     */
	private UrlAndParam oss ;

    /**
     * 通訊錄
     */
	private UrlAndParam contacts;

    /**
     * 消息
     */
    private UrlAndParam ums;
    /**
     * 统一搜索
     */
    private UrlAndParam unisearch;
    /**
     * 即时通讯
     */
    private UrlAndParam immsg;
}