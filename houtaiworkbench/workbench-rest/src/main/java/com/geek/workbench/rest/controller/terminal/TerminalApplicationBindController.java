package com.geek.workbench.rest.controller.terminal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.dto.terminal.TerminalApplicationBindDto;
import com.geek.workbench.entity.terminal.TerminalApplicationBindEntity;
import com.geek.workbench.service.terminal.TerminalApplicationBindService;
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
@RequestMapping(value=TerminalApplicationBindController.BASE_PATH)
public class TerminalApplicationBindController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/application/bind" ;

    @Autowired
    private TerminalApplicationBindService terminalAppBindService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "终端应用版本",optName = "分页查询",optType = OptLogConst.QUERY)
    @Deprecated
    public ResponseParam query(@RequestBody TerminalApplicationBindDto terminalApplicationBindDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalApplicationBindDto, null);
        //执行分页查询
        Page<TerminalApplicationBindEntity> terminalApplicationCheckedPage = terminalAppBindService.queryByPage(searchParam
                , terminalApplicationBindDto.getPageNum() , terminalApplicationBindDto.getPageSize(),new String[]{"createDatetime_desc"}) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalApplicationCheckedList = UtilDTO.toDTO(terminalApplicationCheckedPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( terminalApplicationCheckedPage )
                .datalist( terminalApplicationCheckedList) ;
    }
    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule = "终端应用版本",optName = "获取终端应用版本详情",optType = OptLogConst.QUERY)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询终端应用选择配置
        TerminalApplicationBindEntity terminalApplicationChecked = terminalAppBindService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( terminalApplicationChecked ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalApplicationChecked
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule = "终端应用版本",optName = "保存终端版本信息",optType = OptLogConst.SAVE)
    @Deprecated
    public ResponseParam save(@RequestBody TerminalApplicationBindEntity  terminalApplicationChecked) {
        //id不为空，进行更新操作，否则进行添加
        if(terminalApplicationChecked.getId() != null){
			//由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(terminalApplicationChecked, Arrays.asList("id")).keySet();
            //按照字段更新对象
            terminalAppBindService.update( terminalApplicationChecked , updateFields ) ;

			return ResponseParam.updateSuccess() ;
        }else{
            terminalAppBindService.save(terminalApplicationChecked);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule = "终端应用版本",optName = "查询终端应用版本删除",optType = OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        terminalAppBindService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	

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
     * 功能描述: 查询终端应用版本
     * TODO
     *
     * @param terminalApplicationBindDto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/10/15 10:16
     */
    @PostMapping("query/terminalappversion")
    @OptLog(optModule = "终端应用版本",optName = "查询终端应用版本",optType = OptLogConst.QUERY)
    public ResponseParam queryTerminalAppVersion(@RequestBody TerminalApplicationBindDto terminalApplicationBindDto){
//        if(terminalApplicationBindDto.getAppConfigId()==null) {
//            return ResponseParam.fail().data("终端应用配置id不能为空!");
//        }
        Page<TerminalApplicationBindEntity> terminalAppVersion = terminalAppBindService.queryTerminalAppVersion(terminalApplicationBindDto);
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalApplicationCheckedList = UtilDTO.toDTO(terminalAppVersion.getContent(),
               null, getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( terminalAppVersion )
                .datalist( terminalApplicationCheckedList) ;
    }

    /**
     * 功能描述: 终端应用上线发布功能
     * TODO
     *
     * @param terminalApplicationBindDto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/10/15 10:16
     */
    @PostMapping("update/status")
    @OptLog(optModule = "终端应用版本",optName = "终端应用上线发布功能",optType = OptLogConst.UPDATE)
    public ResponseParam updateStatus(@RequestBody TerminalApplicationBindDto terminalApplicationBindDto){
        terminalAppBindService.updateStatus(terminalApplicationBindDto);
        return ResponseParam.updateSuccess();
    }



}