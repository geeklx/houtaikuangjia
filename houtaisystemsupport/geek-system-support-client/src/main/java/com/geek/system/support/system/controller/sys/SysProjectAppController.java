package com.geek.system.support.system.controller.sys;
import java.util.*;
import java.util.stream.Collectors;

import com.geek.system.support.system.dto.sys.SysProjectAppDto;
import com.geek.system.support.system.entity.sys.SysProjectApp;
import com.geek.system.support.system.service.sys.SysProjectAppService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import org.springframework.web.bind.annotation.*;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;

@RestController
@RequestMapping(value=SysProjectAppController.BASE_PATH)
public class SysProjectAppController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/sysprojectapp" ;

    @Autowired
    private SysProjectAppService sysProjectAppService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody SysProjectAppDto sysProjectAppDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysProjectAppDto, null);
        //执行分页查询
        Page<SysProjectApp> sysProjectAppPage = sysProjectAppService.queryByPage(searchParam , sysProjectAppDto.getPageNum() , sysProjectAppDto.getPageSize()) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysProjectAppList = UtilDTO.toDTO(sysProjectAppPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( sysProjectAppPage )
                .datalist( sysProjectAppList) ;
    }

    /**
     * 描述: 通过应用id查询租户信息
     * @param param
     * @return java.util.List<com.fosung.system.support.system.entity.sys.SysProjectApp>
     * @author fuhao
     * @date 2021/12/31 10:12
     **/
    @PostMapping("query/project")
    public ResponseParam queryInfo(@RequestBody AppBaseIdParam param){
        if(param.getId() == null){
            return ResponseParam.fail().message("应用id不能为空!");
        }
        // 通过应用id查询租户信息
        HashMap<String, Object> searchParam = Maps.newHashMap();
        searchParam.put("appId",param.getId());
        List<SysProjectApp> sysProjectApps = sysProjectAppService.queryInfo(searchParam);
        // projectName集合
        List<String> collect = sysProjectApps.stream().map(map -> map.getProjectName()).collect(Collectors.toList());

        return ResponseParam.success()
                .datalist( collect) ;
    }

    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询应用项目关联表
        SysProjectApp sysProjectApp = sysProjectAppService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysProjectApp ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param sysProjectApp
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    public ResponseParam save(@RequestBody SysProjectApp  sysProjectApp) {
        //id不为空，进行更新操作，否则进行添加
        if(sysProjectApp.getId() != null){
			//由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(sysProjectApp, Arrays.asList("id")).keySet(); ;
			//按照字段更新对象
			sysProjectAppService.update( sysProjectApp , updateFields ) ;

			return ResponseParam.updateSuccess() ;
        }else{
			sysProjectAppService.save(sysProjectApp);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        sysProjectAppService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	

        return ResponseParam.deleteSuccess() ;
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