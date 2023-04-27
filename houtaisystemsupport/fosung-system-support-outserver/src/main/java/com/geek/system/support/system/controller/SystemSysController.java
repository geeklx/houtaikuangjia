package com.geek.system.support.system.controller;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.entity.sys.SysDictDataEntity;
import com.geek.system.support.system.entity.sys.SysProjectEntity;
import com.geek.system.support.system.entity.sys.SysResourceEntity;
import com.geek.system.support.system.service.sys.SysDictDataService;
import com.geek.system.support.system.service.sys.SysDictTypeService;
import com.geek.system.support.system.service.sys.SysProjectService;
import com.geek.system.support.system.service.sys.SysResourceService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/system/sys")
public class SystemSysController extends AppIBaseController {

    @Autowired
    private SysDictDataService sysDictDataService;

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysProjectService sysProjectService;



    /**
     *获取系统管理字典数据
     *
     * @param dictType
     * @date 2021/4/23 9:47
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("query/systemdict")
    public ResponseParam querySysTemDict(@RequestParam("dictType") String dictType) {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("dictType",dictType);
        List<SysDictDataEntity> sysDictDataEntities = sysDictDataService.queryAll(searchParam);
        List<Map<String,Object>> results = UtilDTO.toDTO(sysDictDataEntities,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(results);
    }

    /**
     *根据app获取权限
     *
     * @param appId
     * @author liuke
     * @date 2021/4/30 9:28
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("query/resource/byapp")
    public ResponseParam queryResourceByApp(@RequestParam("appId") Long appId) {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("appId",appId);
        searchParam.put("status","0");
        List<SysResourceEntity> sysResourceEntities = sysResourceService.queryAll(searchParam);
        List<Map<String,Object>> results = UtilDTO.toDTO(sysResourceEntities,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(results);
    }

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
    public List<Map<String,Object>> queryDictByTypes(@PathVariable("project") String project,@RequestParam("dictType") String dictType){
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("dictType",dictType);
        searchParam.put("projectId",sysProjectService.getProjectId(project));
        return sysDictTypeService.queryDictByType(searchParam);
    }


    /**
     *
     *查询租户列表
     * @author liuke
     * @date 2022/8/22 10:20
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping("/project/list")
    public List<Map<String,Object>> queryProjectList(){
        List<SysProjectEntity> lists = sysProjectService.queryAll(Maps.newHashMap());
        List<Map<String,Object>> results = UtilDTO.toDTO(lists,null,null,null);
        return results;
    }


    /**
     * 获取PO到DTO转换的接口。主要用于在前端展示数据之前首先将数据格式处理完成。
     * @return
     */
    public DTOCallbackHandler getDtoCallbackHandler() {

        //创建转换接口类
        DTOCallbackHandler dtoCallbackHandler = new DTOCallbackHandler() {
            @Override
            public void doHandler(Map<String, Object> dtoMap, Class<?> itemClass) {

            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }
}
