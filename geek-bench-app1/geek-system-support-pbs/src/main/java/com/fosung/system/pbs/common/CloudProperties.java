package com.fosung.system.pbs.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Setter
@Getter
@ConfigurationProperties(prefix = CloudProperties.PREFIX)
public class CloudProperties {

    public static final String PREFIX  = "app.dt" ;

    private ConnectionConfig connection = new ConnectionConfig() ;

    private DTAuthConfig auth = new DTAuthConfig() ;

    private DTUrls urls = new DTUrls() ;

    private DTRoles roles = new DTRoles() ;


    /**
     * 灯塔连接配置
     */
    @Getter
    @Setter
    public static class ConnectionConfig{
        /**
         * 连接超时时间，单位秒
         */
        private Integer connectTimeout  = 3 ;

        /**
         * 读取超时时间，单位秒
         */
        private Integer readTimeout  = 5 ;
    }

    /**
     * 基于灯塔的用户认证
     */
    @Getter
    @Setter
    public static class DTAuthConfig{
        // 是否允许登录认证，默认不允许
        private boolean enableLogin = false ;

        // 灯塔用户的登录url
        private String loginUrl = "/login/dt" ;

        //云平台sso clientId CLOUD_SSO
        private String cloudClientId = "box_client";

        //云平台sso clientSecret
        private String cloudClientSecret =  "box_1qaz2wsx!@#" ;

        //灯塔互联网 clientId
        private String dtInteClientId = "fosung_cloud_client";

        //灯塔互联网 clientSecret
        private String dtInteClientSecret =  "fosung_cloud" ;

        //灯塔vpn clientId
        private String dtVpnClientId = "party-build-assist-ui";

        //灯塔vpn clientSecret
        private String dtVpnClientSecret =  "89IUnjf3" ;


        private String jiNanClientId = "cloud_org_life";

        private String jiNanClientSecret = "cloud_org_life_secret";

        // 使用本地灯塔用户
        private boolean useLocalDtUser = false ;

    }

    /**
     * 灯塔url配置
     */
    @Getter
    @Setter
    public static class DTUrls {

        // 灯塔互联网
        private String dtInteSso = "https://sso.dtdjzx.gov.cn/sso" ;
    	//云平台sso
    	private String cloudSso = "http://cloudsso.dengtacloud.com:82" ;
    	//灯塔vpn
    	private String dtVpnSso = "http://10.243.32.7:8922/sso" ;

    	//济南乡村振兴
        private String jinanSso = "http://127.0.0.1:8090";

        // 简项库地址
        private String simpleData = "http://10.254.23.41:8962/simpledata" ;

        // 简项库地址
        private String v2SimpleData = "http://10.254.23.41:8962/v2/simpledata" ;

        //工单发送消息
        private String mqSend = "http://127.0.0.1:8080/api/cloud/repair/send";

        //工单发送队列消息
        private String mqSendQueue = "http://127.0.0.1:8080/api/cloud/repair/sendQueue";



    }

    @Getter
    @Setter
    public static class DTRoles {

        // 党员角色
        private String partyer = "PARTYER" ;

    }

}
