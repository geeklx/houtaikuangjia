package com.fosung.workbench.rest.controller.terminal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.terminal.TerminalCategoryAppDto;
import com.fosung.workbench.dto.terminal.TerminalCategoryAppSaveDto;
import com.fosung.workbench.entity.terminal.TerminalCategoryAppEntity;
import com.fosung.workbench.service.terminal.TerminalCategoryAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.common.config.AppProperties;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilString;

@RestController
@RequestMapping(value=TerminalCategoryAppController.BASE_PATH)
public class TerminalCategoryAppController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/category/app" ;

    @Autowired
    private TerminalCategoryAppService terminalCategoryAppService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "工作台分类绑定应用",optName = "分页查询",optType = OptLogConst.QUERY)
    @Deprecated
    public ResponseParam query(@RequestBody TerminalCategoryAppDto terminalCategoryAppDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalCategoryAppDto, null);
        //执行分页查询
        Page<TerminalCategoryAppEntity> terminalCategoryAppPage = terminalCategoryAppService.queryByPage(searchParam , terminalCategoryAppDto.getPageNum() , terminalCategoryAppDto.getPageSize()) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalCategoryAppList = UtilDTO.toDTO(terminalCategoryAppPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( terminalCategoryAppPage )
                .datalist( terminalCategoryAppList) ;
    }
    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule = "工作台分类绑定应用",optName = "获取详情",optType = OptLogConst.QUERY)
    @Deprecated
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询终端分类应用信息表
        TerminalCategoryAppEntity terminalCategoryApp = terminalCategoryAppService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( terminalCategoryApp ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalCategoryAppSaveDto
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule = "工作台分类绑定应用",optName = "工作台分类绑定应用",optType = OptLogConst.SAVE)
    public ResponseParam save(@RequestBody TerminalCategoryAppSaveDto terminalCategoryAppSaveDto) {
        return terminalCategoryAppService.saveInfo(terminalCategoryAppSaveDto);
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule = "工作台分类绑定应用",optName = "删除",optType = OptLogConst.DELETE)
    @Deprecated
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        terminalCategoryAppService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	

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