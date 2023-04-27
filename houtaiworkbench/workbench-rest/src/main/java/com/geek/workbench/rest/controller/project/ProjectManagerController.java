package com.geek.workbench.rest.controller.project;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.dto.project.ProjectManagerDto;
import com.geek.workbench.dto.project.ProjectManagerSaveDto;
import com.geek.workbench.entity.project.ProjectManagerEntity;
import com.geek.workbench.service.project.ProjectManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述:  项目管理员控制层
 * @createDate: 2021/10/13 20:49
 * @author: gaojian
 * @modify:
 * @return:
 */
@RestController
@RequestMapping(value=ProjectManagerController.BASE_PATH)
public class ProjectManagerController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/project/manager" ;

    /**
     * 描述:  注入项目管理员信息服务
     * @createDate: 2021/10/13 20:49
     * @author: gaojian
     */
    @Autowired
    private ProjectManagerService projectManagerService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="项目管理员",optName="查询项目管理员-分页",optType= OptLogConst.QUERY )
	public ResponseParam query(@RequestBody ProjectManagerDto projectManagerDto){

        // 1. 获取查询条件
        Map<String, Object> searchParam =  UtilDTO.toDTO(projectManagerDto, null);

        // 2. 执行分页查询
        String[] sorts = {"createDatetime:desc"};
        Page<ProjectManagerEntity> projectManagerPage = projectManagerService.queryByPage(searchParam
                , projectManagerDto.getPageNum() ,
                projectManagerDto.getPageSize(),
                sorts) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> projectManagerList = UtilDTO.toDTO(projectManagerPage.getContent(),
                null , getDtoCallbackHandler()) ;

        // 4. 返回分页查询结构
        return ResponseParam.success()
                .pageParam( projectManagerPage )
                .datalist( projectManagerList) ;
    }

    /**
     * 查询项目的管理员
     * @return
     * @throws Exception
     */
    @PostMapping("query/all")
    @OptLog(optModule="项目管理员",optName="查询项目管理员-不分页",optType= OptLogConst.QUERY )
    public ResponseParam queryAll(@RequestBody ProjectManagerDto projectManagerDto){

        // 1. 判断项目id是否存在
        Assert.notNull(projectManagerDto.getProjectId(), MessageContent.ID_IS_NULL);

        // 2. 执行查询
        Map<String,Object> result = projectManagerService.queryInfo(projectManagerDto.getProjectId()) ;
        return ResponseParam.success()
                .data( result) ;
    }

    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule="项目管理员",optName="获取项目管理员详情",optType= OptLogConst.GET )
    public ResponseParam get(@RequestBody AppBaseIdParam param){

        // 1. 主键信息非空判断
        Assert.notNull(param.getId(), MessageContent.ID_IS_NULL);

        // 2. 查询项目管理员
        ProjectManagerEntity projectManager = projectManagerService.get(param.getId()) ;

        // 3. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( projectManager ,null , getDtoCallbackHandler() ) ;
        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param data
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule="项目管理员",optName="保存项目管理员",optType= OptLogConst.SAVE )
    public ResponseParam save(@Validated @RequestBody ProjectManagerSaveDto data) {

        // 1. 执行新增权限操作
        projectManagerService.saveInfo(data);
        return ResponseParam.saveSuccess() ;
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule="项目管理员",optName="删除项目管理员",optType= OptLogConst.DELETE )
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

 	    // 1. 非空判断
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        projectManagerService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
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
                dtoMap.remove("del");
            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }

}