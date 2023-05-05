package com.fosung.workbench.util;

/**
 *
 * App头解析
 * @author liuke
 * @date  2021/10/20 10:55
 * @version
*/

import com.fosung.workbench.dict.TerminalType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

public class AppHeaderResolution {

    public static HeaderMessage resolutionHeader(HttpServletRequest servletRequest) {
        HeaderMessage headerMessage = new HeaderMessage();
        headerMessage.setToken(servletRequest.getHeader("token"));
        headerMessage.setImei(servletRequest.getHeader("imei"));
        headerMessage.setPlatform( servletRequest.getHeader("platform"));
        headerMessage.setModel(servletRequest.getHeader("model"));
        headerMessage.setPackageName(servletRequest.getHeader("package_name"));
        headerMessage.setVersion(servletRequest.getHeader("version"));
        headerMessage.setVersionCode(servletRequest.getHeader("version_code"));
        headerMessage.setXCaKey(servletRequest.getHeader("X_CA_KEY"));
        headerMessage.setXCaSignature(servletRequest.getHeader("X_CA_SIGNATURE"));
        headerMessage.setXCaTimestamp(servletRequest.getHeader("X_CA_TIMESTAMP"));
        headerMessage.setXCaNonce(servletRequest.getHeader("X_CA_NONCE")) ;
        if(null!=servletRequest.getHeader("platform")){
            switch (servletRequest.getHeader("platform")){
                case "android_phone":
                    headerMessage.setTerminalType(TerminalType.android);
                    break;
                case "android_pad":
                    headerMessage.setTerminalType(TerminalType.android);
                    break;
                case "ios_phone":
                    headerMessage.setTerminalType(TerminalType.ios);
                    break;
                case "ios_pad":
                    headerMessage.setTerminalType(TerminalType.ios);
                    break;
                case "web":
                    headerMessage.setTerminalType(TerminalType.h5);
                    break;
                case "wx_applet":
                    headerMessage.setTerminalType(TerminalType.h5);
                    break;
                case "pcWeb":
                default:
                    headerMessage.setTerminalType(TerminalType.pcWeb);
                    break;
            }
        }else{
            headerMessage.setTerminalType(TerminalType.android);
        }
        try {
            if (null != servletRequest.getHeader("cityName")) {
                headerMessage.setCityName(URLDecoder.decode(servletRequest.getHeader("cityName"), "UTF-8"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return headerMessage;
    }

    /**
     * 校验header
     * @param headerMessage
     * @return
     */
    public static String checkHeader(HeaderMessage headerMessage ){
        String msg = "";
        if(StringUtils.isEmpty(headerMessage.getToken())){
            msg ="header缺少token";
        }
        if(StringUtils.isEmpty(headerMessage.getPackageName())){
            msg ="header缺少参数package_name";
        }
        if(StringUtils.isEmpty(headerMessage.getPlatform())){
            msg ="header缺少参数platform";
        }
     return msg;
    }
    @Data
    public static class HeaderMessage{
        /**
         * 用户唯一标识
         */
        private  String token;
        /**
         * 设备唯一标识，android为imei，web为浏览器唯一标识
         */
        private String imei;

        /**
         * 平台类型，安卓手机为android_phone，安卓平板为android_pad，苹果手机为ios_phone，苹果平板为ios_pad，网页端为web，微信小程序为wx_applet
         */
        private String platform;
        /**
         *此值为记录手机及pad的厂商和型号，备用字段，暂时非必填
         */
        private String model;

        /**
         * 包名唯一
         */
        private String packageName;

        /**
         * 代码版本
         */
        private String version;

        /**
         * 纯数字版的代码版本，方便根据版本进行批量校验
         */
        private String versionCode;

        /**
         *X-CA-KEY
         */
        private String xCaKey;

        /**
         *X-CA-SIGNATURE
         */
        private String xCaSignature;

        /**
         *X-CA-TIMESTAMP
         */
        private String xCaTimestamp;

        /**
         *X-CA-NONCE
         */
        private String xCaNonce;

        /**
         * 终端类型
         */
        private TerminalType terminalType;
        /**
         * 地域
         */
        private String cityName;
    }
}
