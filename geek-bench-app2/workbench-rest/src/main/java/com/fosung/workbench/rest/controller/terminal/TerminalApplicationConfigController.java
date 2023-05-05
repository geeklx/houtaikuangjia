package com.fosung.workbench.rest.controller.terminal;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.config.TerminalContent;
import com.fosung.workbench.dict.SearchOperateType;
import com.fosung.workbench.dict.StatusType;
import com.fosung.workbench.dict.TerminalType;
import com.fosung.workbench.dto.terminal.TerminalApplicationBindDto;
import com.fosung.workbench.dto.terminal.TerminalApplicationConfigDto;
import com.fosung.workbench.dto.terminal.TerminalApplicationQueryDto;
import com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.fosung.workbench.entity.terminal.TerminalApplicationShelvesEntity;
import com.fosung.workbench.service.application.ApplicationBasicService;
import com.fosung.workbench.service.search.UniSearchService;
import com.fosung.workbench.service.terminal.TerminalApplicationConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value=TerminalApplicationConfigController.BASE_PATH)
public class TerminalApplicationConfigController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/application/config" ;

    @Autowired
    private TerminalApplicationConfigService terminalApplicationConfigService ;

    @Autowired
    private ApplicationBasicService applicationService;

    @Autowired
    private UniSearchService uniSearchService;

    /**
     * 提供给系统管理的接口 根据终端包名和终端类型查询终端绑定的应用
     * @return
     * @throws Exception
     */
    @PostMapping("query/app")
    @OptLog(optModule = "终端应用",optName = "提供给系统管理的接口，根据终端包名和终端类型查询终端绑定的应用",optType = OptLogConst.QUERY)
    public ResponseParam queryApp(@Validated @RequestBody TerminalApplicationQueryDto queryDto) {

        // 1. 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(queryDto, null);

        // 2. 执行查询
        List<TerminalApplicationConfigEntity> list = terminalApplicationConfigService.queryTerminalApp(searchParam) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> result = UtilDTO.toDTO(list,
                null , getDtoCallbackHandler()) ;
        return ResponseParam.success()
                .datalist( result) ;
    }

    /**
     * 提供给系统管理的接口 根据终端包名和终端类型查询终端绑定的应用
     * @return
     * @throws Exception
     */
    @PostMapping("query/app/params")
    @OptLog(optModule = "终端应用",optName = "提供给系统管理的接口，根据终端包名和终端类型查询终端绑定的应用",optType = OptLogConst.QUERY)
    public ResponseParam queryAppParams(
            HttpServletRequest request
    ) {

        // 1. 获取查询参数
        if(StringUtils.isBlank(request.getParameter("packagename")) || StringUtils.isBlank(request.getParameter("terminaltype"))){
            return ResponseParam.success().datalist(new ArrayList<>());
        }
        TerminalApplicationQueryDto queryDto = new TerminalApplicationQueryDto();
        queryDto.setTerminalPackageName(request.getParameter("packagename"));
        queryDto.setTerminalType(TerminalType.valueOf(request.getParameter("terminaltype")));
        queryDto.setAppStatus(StatusType.release);
        Map<String, Object> searchParam =  UtilDTO.toDTO(queryDto, null);

        // 2. 执行查询
        List<TerminalApplicationConfigEntity> list = terminalApplicationConfigService.queryTerminalApp(searchParam) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> result = UtilDTO.toDTO(list,
                null , getDtoCallbackHandler()) ;
        return ResponseParam.success()
                .datalist( result) ;
    }

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "终端应用",optName = "终端应用分页查询",optType = OptLogConst.QUERY)
    public ResponseParam query(@RequestBody TerminalApplicationConfigDto terminalApplicationConfigurationInfoDto) {
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalApplicationConfigurationInfoDto, null);
        //执行分页查询
        Page<TerminalApplicationConfigEntity> terminalApplicationConfigurationInfoPage =
                terminalApplicationConfigService.queryTerminalAppByPage(searchParam,terminalApplicationConfigurationInfoDto) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalApplicationConfigurationInfoList = UtilDTO.toDTO(terminalApplicationConfigurationInfoPage.getContent(),
                null , getDtoCallbackHandler()) ;
        return ResponseParam.success()
                .pageParam( terminalApplicationConfigurationInfoPage )
                .datalist( terminalApplicationConfigurationInfoList) ;
    }
    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule = "终端应用",optName = "查询详情",optType = OptLogConst.QUERY)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询终端应用配置信息
        TerminalApplicationConfigEntity terminalApplicationConfigurationInfo = terminalApplicationConfigService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( terminalApplicationConfigurationInfo ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalApplicationConfigurationInfo
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule = "终端应用",optName = "更新基础配置信息",optType = OptLogConst.SAVE)
    public ResponseParam save(@RequestBody TerminalApplicationConfigEntity  terminalApplicationConfigurationInfo) {
        //id不为空，进行更新操作，否则进行添加
        if(terminalApplicationConfigurationInfo.getId() != null){
            //按照字段更新对象
			terminalApplicationConfigService.update( terminalApplicationConfigurationInfo) ;
            uniSearchService.searchSynchronizationData(terminalApplicationConfigurationInfo, SearchOperateType.upd);

            return ResponseParam.updateSuccess() ;
        }else{
			terminalApplicationConfigService.save(terminalApplicationConfigurationInfo);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 级联删除终端应用信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule = "终端应用",optName = "通过终端类型查询分类信息",optType = OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        terminalApplicationConfigService.deleteByConfigId(list);
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

    /**
     * 功能描述: 根据终端类型获取最新发布版本的app
     * TODO
     *
     * @param terminalAppConfigDto
     * @return
     * @author fuhao
     * @date 2021/10/13 13:57
     */
//    @PostMapping("queryapp")
//    @OptLog(optModule = "终端应用",optName = "根据终端类型获取最新发布版本的app",optType = OptLogConst.QUERY)
//    public  ResponseParam queryApp(@RequestBody TerminalApplicationConfigDto terminalAppConfigDto) {
//        Page<TerminalApplicationConfigEntity> terminalApplicationConfigurationInfoPage =
//                terminalApplicationConfigService.queryApp(terminalAppConfigDto);
//        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
//        List<Map<String, Object>> terminalApplicationConfigurationInfoList = UtilDTO.toDTO(terminalApplicationConfigurationInfoPage.getContent(),
//                null , getDtoCallbackHandler());
//        return ResponseParam.success()
//                .pageParam( terminalApplicationConfigurationInfoPage )
//                .datalist( terminalApplicationConfigurationInfoList) ;
//    }

    @PostMapping("queryapp")
    @OptLog(optModule = "终端应用",optName = "根据终端类型获取最新发布版本的app",optType = OptLogConst.QUERY)
    public  ResponseParam queryApp(@RequestBody TerminalApplicationConfigDto terminalAppConfigDto) {
        List<TerminalApplicationConfigEntity> terminalApplicationConfigurationInfoPage =
                terminalApplicationConfigService.queryApp(terminalAppConfigDto);
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalApplicationConfigurationInfoList = UtilDTO.toDTO(terminalApplicationConfigurationInfoPage,
                null , getDtoCallbackHandler());
        return ResponseParam.success()
                .datalist( terminalApplicationConfigurationInfoList) ;
    }

    /**
     *  通过终端类型查询分类信息
     * @param terminalApplicationConfigDto
     * @return
     * @author fuhao
     * @date 2021/10/13 13:57
     */
    @PostMapping("query/category")
    @OptLog(optModule = "终端应用",optName = "通过终端类型查询分类信息",optType = OptLogConst.QUERY)
    public  ResponseParam queryCategory(@RequestBody TerminalApplicationConfigDto terminalApplicationConfigDto) {

        // 未选择终端返回空信息
        if(terminalApplicationConfigDto.getTerminalId() == null || terminalApplicationConfigDto.getProjectId() == null){
            ResponseParam.success().datalist(new ArrayList<>());
        }

        List<Map> result = terminalApplicationConfigService.queryCategory(terminalApplicationConfigDto);
        if(result != null){
            return ResponseParam.success().datalist(result);
        }
        return ResponseParam.success().datalist(new ArrayList<>());
    }


    /**
     * 终端应用选择确认
     * @param terminalAppBindDto
     * @return
     * @author fuhao
     * @date 2021/10/14 9:18
     */
    @PostMapping("save/terminalappbind")
    @OptLog(optModule = "终端应用",optName = "终端应用选择确认",optType = OptLogConst.SAVE)
    public ResponseParam saveTerminalAppBind(@RequestBody List<TerminalApplicationBindDto> terminalAppBindDto){
        for (TerminalApplicationBindDto dto: terminalAppBindDto) {
            if(dto.getTerminalId() == null || dto.getAppId() == null
                    || dto.getAppVersionId() == null|| dto.getProjectId() == null){
                return ResponseParam.fail().message(TerminalContent.CHECK_PARAM_ID);
            }
        }
        terminalApplicationConfigService.saveTerminalAppBind(terminalAppBindDto);
        return ResponseParam.saveSuccess();
    }

    /**
     * 终端应用复制
     * @param terminalAppConfigDto
     * @return
     * @author fuhao
     * @date 2021/10/14 9:18
     */
    @PostMapping("save/copy")
    @OptLog(optModule = "终端应用",optName = "终端应用复制",optType = OptLogConst.SAVE)
    public ResponseParam saveCopy(@RequestBody TerminalApplicationConfigDto terminalAppConfigDto){
        if(terminalAppConfigDto.getId() == null){
            return ResponseParam.fail().message(TerminalContent.CHECK_APP_CONFIG_ID);
        }
        terminalApplicationConfigService.copy(terminalAppConfigDto);
        return ResponseParam.saveSuccess();
    }

    /**
     * 查询终端应用授权
     * @param terminalAppConfigDto
     * @return
     * @author fuhao
     * @date 2021/10/14 9:18
     */
    @PostMapping("query/shelves")
    @OptLog(optModule = "终端应用",optName = "查询终端应用授权",optType = OptLogConst.QUERY)
    public ResponseParam queryShelves(@RequestBody TerminalApplicationConfigDto terminalAppConfigDto){
        if(terminalAppConfigDto.getId() == null){
            return ResponseParam.fail().message(TerminalContent.CHECK_APP_CONFIG_ID);
        }
        Map<String, List<TerminalApplicationShelvesEntity>> listMap =
                terminalApplicationConfigService.queryShelves(terminalAppConfigDto);
        return ResponseParam.success().data(listMap);
    }

}