package com.geek.system.pbs.api;


import com.fosung.framework.web.http.ResponseParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@FeignClient(name = "SystemSysApi", url = "${app.pbs.client.url}")
@RequestMapping(value = "/api/system/sys")
public interface SystemSysApi {



    /**
     *获取系统管理字典数据
     *
     * @param dictType
     * @date 2021/4/23 9:47
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("query/systemdict")
    ResponseParam querySysTemDict(@RequestParam("dictType") String dictType);

    /**
     *根据app获取权限
     *
     * @param appId
     * @author liuke
     * @date 2021/4/30 9:28
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("query/resource/byapp")
    ResponseParam queryResourceByApp(@RequestParam("appId") Long appId);

    /**
     *查询字典
     *
     * @param project
     * @param dictType
     * @author liuke
     * @date 2022/5/8 15:18
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping("/dict/{project}")
    List<Map<String,Object>> queryDictByTypes(@PathVariable("project") String project, @RequestParam("dictType") String dictType);

    /**
     *
     *查询租户列表
     * @author liuke
     * @date 2022/8/22 10:20
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping("/project/list")
    List<Map<String,Object>> queryProjectList();

}
