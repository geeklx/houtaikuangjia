package com.fosung.workbench.rest.controller.terminal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.terminal.TerminalUpdateLogDto;
import com.fosung.workbench.entity.terminal.TerminalUpdateLogEntity;
import com.fosung.workbench.service.terminal.TerminalUpdateLogService;
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
@RequestMapping(value=TerminalUpdateLogController.BASE_PATH)
public class TerminalUpdateLogController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/update" ;

    @Autowired
    private TerminalUpdateLogService terminalUpdateLogInfoService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "终端版本发布记录",optName = "不分页查询",optType = OptLogConst.QUERY)
    @Deprecated
    public ResponseParam query(@RequestBody TerminalUpdateLogDto terminalUpdateLogInfoDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalUpdateLogInfoDto, null);

        List<TerminalUpdateLogEntity> list = terminalUpdateLogInfoService.queryAll(searchParam);
        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalUpdateLogInfoList = UtilDTO.toDTO(list,
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .datalist( terminalUpdateLogInfoList) ;
    }
    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule = "终端版本发布记录",optName = "获取详情",optType = OptLogConst.QUERY)
    @Deprecated
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询终端发布记录表
        TerminalUpdateLogEntity terminalUpdateLogInfo = terminalUpdateLogInfoService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( terminalUpdateLogInfo ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalUpdateLogInfo
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule = "终端版本发布记录",optName = "保存信息",optType = OptLogConst.SAVE)
    @Deprecated
    public ResponseParam save(@RequestBody TerminalUpdateLogEntity  terminalUpdateLogInfo) {
        //id不为空，进行更新操作，否则进行添加
        if(terminalUpdateLogInfo.getId() != null){
			//由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(terminalUpdateLogInfo, Arrays.asList("id")).keySet();
            //按照字段更新对象
			terminalUpdateLogInfoService.update( terminalUpdateLogInfo , updateFields ) ;

			return ResponseParam.updateSuccess() ;
        }else{
			terminalUpdateLogInfoService.save(terminalUpdateLogInfo);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule = "终端版本发布记录",optName = "删除",optType = OptLogConst.DELETE)
    @Deprecated
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        terminalUpdateLogInfoService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	

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