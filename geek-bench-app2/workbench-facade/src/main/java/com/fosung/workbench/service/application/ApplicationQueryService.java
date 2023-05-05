package com.fosung.workbench.service.application;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.dto.application.ApplicationQueryDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @Description 终端应用查询服务接口
 * @Author gaojian
 * @Date 2021/10/23 21:09
 * @Version V1.0
 */
public interface ApplicationQueryService {

    /**
     * 描述:  查询应用信息
     * @createDate: 2021/10/23 21:47
     * @author: gaojian
     * @modify:
     * @param applicationQueryDto
     * @return: org.springframework.data.domain.Page<java.util.Map<java.lang.String,java.lang.Object>>
     */
    Page<Map<String,Object>> queryApplication(ApplicationQueryDto applicationQueryDto);

    /**
     * 描述:  查询地域信息
     * @createDate: 2021/10/30 15:52
     * @author: gaojian
     * @modify:
     * @param applicationQueryDto
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> queryRegional(ApplicationQueryDto applicationQueryDto);

    /**
     * 描述:  查询组织
     * @createDate: 2021/11/2 18:40
     * @author: gaojian
     * @modify:
     * @param jsonObject
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    ResponseParam queryOrgan(JSONObject jsonObject);

    /**
     * 描述:  查询用户
     * @createDate: 2021/11/2 18:40
     * @author: gaojian
     * @modify:
     * @param terminalId
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    ResponseParam queryUser(Long terminalId,Map<String,Object> requestParams);
}
