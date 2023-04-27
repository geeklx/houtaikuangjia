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
import com.geek.workbench.dto.project.ProjectBasicDto;
import com.geek.workbench.entity.project.ProjectBasicEntity;
import com.geek.workbench.service.project.ProjectBasicService;
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

/**
 * 描述:  项目基本信息控制层
 * @createDate: 2021/10/13 20:49
 * @author: gaojian
 * @modify:
 * @return:
 */
@RestController
@RequestMapping(value=ProjectBasicController.BASE_PATH)
public class ProjectBasicController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/project/basic" ;

    /**
     * 描述:  注入项目基础信息服务
     * @createDate: 2021/10/13 20:49
     * @author: gaojian
     */
    @Autowired
    private ProjectBasicService projectBasicService ;

    /**
     * 项目信息分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="项目管理",optName="查询项目信息",optType="query")
	public ResponseParam query(@RequestBody ProjectBasicDto projectBasicDto){

        // 1. 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(projectBasicDto, null);

        // 2. 执行分页查询
        String[] sorts = {"createDatetime:desc"};
        Page<ProjectBasicEntity> projectBasicPage = projectBasicService.queryByPage(searchParam , projectBasicDto.getPageNum() , projectBasicDto.getPageSize(),sorts) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> projectBasicList = UtilDTO.toDTO(projectBasicPage.getContent(),
                null , getDtoCallbackHandler()) ;

        // 4. 返回结果
        return ResponseParam.success()
                .pageParam( projectBasicPage )
                .datalist( projectBasicList) ;
    }

    /**
     * 查询项目选项
     * @return
     * @throws Exception
     */
    @PostMapping("query/option")
    @OptLog(optModule="项目管理",optName="查询项目为选项信息",optType= OptLogConst.QUERY)
    public ResponseParam queryProjectOption(){

        // 1. 查询项目为选项信息
        List<Map<String,Object>> list = projectBasicService.queryProjectOption();

        // 2. 返回结果
        return ResponseParam.success().datalist(list);

    }

    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule="项目管理",optName="获取项目详情",optType = OptLogConst.GET)
    public ResponseParam get(@RequestBody AppBaseIdParam param){

	    // 1. 主键信息非空判断
	    Assert.notNull(param.getId(), MessageContent.ID_IS_NULL);

        // 2. 查询项目基本信息
        ProjectBasicEntity projectBasic = projectBasicService.get(param.getId()) ;

        // 3. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( projectBasic ,null , getDtoCallbackHandler() ) ;
        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param projectBasic
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule="项目管理",optName="保存项目信息",optType = OptLogConst.SAVE)
    public ResponseParam save(@Validated @RequestBody ProjectBasicEntity  projectBasic) {

        // 1. id 不为空进行修改操作 为空进行保存操作
        if(projectBasic.getId() != null){

			// 2. 执行修改操作
			projectBasicService.updateInfo(projectBasic);
			return ResponseParam.updateSuccess() ;
        }else{

            // 3. 执行新增操作
			projectBasicService.saveInfo(projectBasic);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule="项目管理",optName="删除项目信息",optType= OptLogConst.DELETE )
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

 	    // 1. 删除请求结果集为空校验
 	    if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        projectBasicService.deleteInfo(list);
        return ResponseParam.deleteSuccess() ;
    }

    /**
     * 获取PO到DTO转换的接口。主要用于在前端展示数据之前首先将数据格式处理完成。
     * @return
     */
    public DTOCallbackHandler getDtoCallbackHandler() {

        // 1. 创建转换接口类
        DTOCallbackHandler dtoCallbackHandler = new DTOCallbackHandler() {
            @Override
            public void doHandler(Map<String, Object> dtoMap, Class<?> itemClass) {
                dtoMap.remove("status");
                dtoMap.remove("del");
            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }

}