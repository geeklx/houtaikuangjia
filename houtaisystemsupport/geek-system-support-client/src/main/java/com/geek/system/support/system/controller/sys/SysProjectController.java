package com.geek.system.support.system.controller.sys;
import java.util.*;

import com.geek.system.support.system.anno.SysLog;
import com.geek.system.support.system.dict.OptLogType;
import com.geek.system.support.system.dto.sys.SysProjectDto;
import com.geek.system.support.system.entity.sys.SysApplicationEntity;
import com.geek.system.support.system.entity.sys.SysProjectConfigEntity;
import com.geek.system.support.system.entity.sys.SysProjectEntity;
import com.geek.system.support.system.service.sys.SysProjectConfigService;
import com.geek.system.support.system.service.sys.SysProjectService;
import com.geek.system.support.system.util.ProjectConstant;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping(value=SysProjectController.BASE_PATH)
public class SysProjectController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/sysproject" ;

    @Autowired
    private SysProjectService sysProjectService ;

    @Autowired
    private SysProjectConfigService sysProjectConfigService;


    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody SysProjectDto sysProjectDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysProjectDto, null);
        //执行分页查询
        String[] order = new String[]{"createDatetime_desc"};
        Page<SysProjectEntity> sysProjectPage = sysProjectService.queryByPage(searchParam , sysProjectDto.getPageNum() , sysProjectDto.getPageSize(),order) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysProjectList = UtilDTO.toDTO(sysProjectPage.getContent(),null, getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( sysProjectPage )
                .datalist( sysProjectList) ;
    }

    /***
     * 描述: 查询全部项目，如：下拉框
     * @param sysProjectDto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/25 9:02
     **/
    @PostMapping("queryall")
    public ResponseParam queryall(@RequestBody SysProjectDto sysProjectDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysProjectDto, null);
        String[] order = new String[]{"createDatetime_desc"};
        List<SysProjectEntity> sysProjects = sysProjectService.queryAll(searchParam,order);
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysProjectList = UtilDTO.toDTO(sysProjects,
                Arrays.asList("id","projectName","projectCode","createDatetime","remark","projectManager","status") , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .datalist( sysProjectList) ;
    }

    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询项目管理
        SysProjectEntity sysProject = sysProjectService.get(param.getId()) ;

        //查询项目配置
        SysProjectConfigEntity sysProjectConfigEntity = sysProjectConfigService.getByProjectId(param.getId());

        sysProject.setSysProjectConfigEntity(sysProjectConfigEntity);
        //查询项目下的应用
        List<SysApplicationEntity> sysApplicationEntities = sysProjectService.queryApp(param.getId());
        List<Map<String, Object>> apps = UtilDTO.toDTO(sysApplicationEntities, null, getDtoCallbackHandler());
        sysProject.setBindApps(apps);
        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysProject ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param sysProject
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @SysLog(optModule = "租户管理",optName = "保存",optType = OptLogType.SAVE)
    public ResponseParam save(@RequestBody SysProjectEntity  sysProject) {
        //id不为空，进行更新操作，否则进行添加
        if(sysProject.getId() != null){
            SysProjectEntity sysProjectEntity = sysProjectService.get(sysProject.getId());
            if(!sysProjectEntity.getProjectName().equals(sysProject.getProjectName())){
                // 判断项目名称是否存在
                HashMap<String, Object> checkProjectName = new HashMap<>();
                checkProjectName.put("projectName",sysProject.getProjectName());
                checkProjectName.put("del",false);
                if(sysProjectService.isExist(checkProjectName)){
                    return ResponseParam.fail().message(ProjectConstant.CHECK_PROJECT_NAME);
                }
            }
            sysProjectService.saveInfo(sysProject);
            return ResponseParam.updateSuccess();
        }else{
            // 判断项目名称是否存在
            HashMap<String, Object> checkProjectName = new HashMap<>();
            checkProjectName.put("projectName",sysProject.getProjectName());
            checkProjectName.put("del",false);
            if(sysProjectService.isExist(checkProjectName)){
                return ResponseParam.fail().message(ProjectConstant.CHECK_PROJECT_NAME);
            }
            if(StringUtils.isNotBlank(sysProject.getProjectCode())){
                // 租户编码唯一性校验
                HashMap<String, Object> checkProjectCode = new HashMap<>();
                checkProjectCode.put("projectCode",sysProject.getProjectCode());
                checkProjectCode.put("del",false);
                if(sysProjectService.isExist(checkProjectCode)){
                    return ResponseParam.fail().message(ProjectConstant.CHECK_PROJECT_CODE);
                }
            }
            sysProjectService.saveInfo(sysProject);
			return ResponseParam.saveSuccess();
        }
    }

    /**
     * 描述: 批量启动/禁用
     * @param sysProject
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/25 10:22
     **/
    @PostMapping("update/status")
    @SysLog(optModule = "租户管理",optName = "批量启用/禁用",optType = OptLogType.BATCH_ENABLED)
    public ResponseParam batchUpdateStatus(@RequestBody List<SysProjectEntity>  sysProject) {
        //id不为空，进行更新操作，否则进行添加
        if(sysProject!=null){
            sysProject.forEach(project -> {
                if(project.getId() != null){
                    //按照字段更新对象
                    sysProjectService.update( project , Arrays.asList("status")) ;
                }
            });
            return ResponseParam.updateSuccess() ;
        }
        return ResponseParam.updateFail();
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @SysLog(optModule = "租户管理",optName = "删除",optType = OptLogType.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        sysProjectService.deleteInfo(list);
        return ResponseParam.deleteSuccess() ;
    }

    /**
     * 描述: 项目关联应用
     * @param ids
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/25 15:33
     **/
    @PostMapping("queryapp")
    public ResponseParam queryApp(@RequestBody AppBaseIdParam ids) {
//        if (ids.getId() == null) {
//            return ResponseParam.fail().message(ProjectConstant.CHECK_ID);
//        }
        // 查询全部应用及选中应用
        List<SysApplicationEntity> sysApp = sysProjectService.queryApp(ids.getId());
        // 返回给前端的格式转换
        List<Map<String, Object>> sysProjectList = UtilDTO.toDTO(sysApp,
                Arrays.asList("id","appCode","appName","appType","orgType","remark","checkFlag") , getDtoCallbackHandler()) ;
        return ResponseParam.success().datalist(sysProjectList);
    }

    /**
     * 描述: 项目绑定应用
     * @param sysProjectApp
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/25 19:46
     **/
    @PostMapping("bindapp")
    public ResponseParam bindApp(@RequestBody SysProjectEntity sysProjectApp) {
        // 项目绑定应用
        sysProjectService.bindApp(sysProjectApp);
        return ResponseParam.saveSuccess();
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