package com.geek.workbench.rest.controller.terminal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.dict.DataSourceType;
import com.geek.workbench.dto.terminal.TerminalApplicationShelvesDto;
import com.geek.workbench.entity.terminal.TerminalApplicationShelvesEntity;
import com.geek.workbench.service.terminal.TerminalApplicationShelvesService;
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
@RequestMapping(value=TerminalApplicationShelvesController.BASE_PATH)
public class TerminalApplicationShelvesController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/application/shelves" ;

    @Autowired
    private TerminalApplicationShelvesService terminalApplicationShelvesService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "终端应用授权",optName = "记录分页查询",optType = OptLogConst.QUERY)
    @Deprecated
    public ResponseParam query(@RequestBody TerminalApplicationShelvesDto TerminalApplicationShelvesDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(TerminalApplicationShelvesDto, null);
        //执行分页查询
        Page<TerminalApplicationShelvesEntity> TerminalApplicationShelvesPage = terminalApplicationShelvesService.queryByPage(searchParam , TerminalApplicationShelvesDto.getPageNum() , TerminalApplicationShelvesDto.getPageSize()) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> TerminalApplicationShelvesList = UtilDTO.toDTO(TerminalApplicationShelvesPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( TerminalApplicationShelvesPage )
                .datalist( TerminalApplicationShelvesList) ;
    }
    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @Deprecated
    @OptLog(optModule = "终端应用授权",optName = "获取授权详情",optType = OptLogConst.QUERY)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询终端应用身份授权
        TerminalApplicationShelvesEntity TerminalApplicationShelves = terminalApplicationShelvesService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( TerminalApplicationShelves ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalApplicationShelves
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule = "终端应用授权",optName = "更新授权配置",optType = OptLogConst.SAVE)
    public ResponseParam save(@RequestBody TerminalApplicationShelvesDto  terminalApplicationShelves) {
        //id不为空，进行更新操作，否则进行添加
        terminalApplicationShelves.setDataSource(DataSourceType.workbench.name());
        terminalApplicationShelvesService.save(terminalApplicationShelves);
        return ResponseParam.saveSuccess() ;
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule = "终端应用授权",optName = "删除授权信息",optType = OptLogConst.DELETE)
    @Deprecated
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        terminalApplicationShelvesService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	

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