package com.geek.workbench.configurecenter.service;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * 获取配置接口类
 *
 * @author liuke
 * @date  2021/10/9 10:02
 * @version
*/
public interface  WorkBenchConfigureService {

    /**
     *
     * 获取配置key
     * @author liuke
     * @date 2021/10/9 10:03
     * @return java.lang.String
     */
    String getConfigureKey();

    /**
     *
     * 获取配置详细信息
     * @param searchParam
     * @param servletRequest
     * @author liuke
     * @date 2021/10/9 10:04
     * @return java.lang.String
     */
    Map<String,Object> getConfigDetailMessage(Map<String,Object> searchParam, HttpServletRequest servletRequest) throws Exception;
}
