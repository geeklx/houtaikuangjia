package com.fosung.workbench.service.busi;

import com.fosung.workbench.AppBean.ThirdInit;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lihuiming
 * @className ThirdService
 * @description: 三方服务
 * @date 2022/1/517:17
 */

public interface ThirdService {
    /**
     * 获取腾讯
     * @param servletRequest
     * @param thirdInit
     * @return
     */
    Map getTxSign(HttpServletRequest servletRequest,ThirdInit thirdInit);

    /**
     * 获取容联
     * @param servletRequest
     * @param thirdInit
     * @return
     */
    Map getRlSign(HttpServletRequest servletRequest,ThirdInit thirdInit);

    Map getVideo (HttpServletRequest servletRequest,ThirdInit thirdInit);
}
