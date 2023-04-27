package com.geek.workbench.rest.controller.terminal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.geek.workbench.dto.terminal.TerminalNavigationStrategyDto;
import com.geek.workbench.entity.terminal.TerminalNavigationStrategy;
import com.geek.workbench.service.terminal.TerminalNavigationStrategyService;
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

/**
 * 描述:  终端导航策略控制层
 * @createDate: 2021/10/13 20:49
 * @author: gaojian
 * @modify:
 * @return:
 */
@RestController
@RequestMapping(value=TerminalNavigationStrategyController.BASE_PATH)
public class TerminalNavigationStrategyController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/navigation/strategy" ;

    /**
     * 当前模块跟路径
     */
    @Autowired
    private TerminalNavigationStrategyService terminalNavigationStrategyService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody TerminalNavigationStrategyDto terminalNavigationStrategyDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalNavigationStrategyDto, null);
        //执行分页查询
        Page<TerminalNavigationStrategy> terminalNavigationStrategyPage = terminalNavigationStrategyService.queryByPage(searchParam , terminalNavigationStrategyDto.getPageNum() , terminalNavigationStrategyDto.getPageSize()) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalNavigationStrategyList = UtilDTO.toDTO(terminalNavigationStrategyPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( terminalNavigationStrategyPage )
                .datalist( terminalNavigationStrategyList) ;
    }

    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询终端导航策略
        TerminalNavigationStrategy terminalNavigationStrategy = terminalNavigationStrategyService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( terminalNavigationStrategy ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalNavigationStrategy
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    public ResponseParam save(@RequestBody TerminalNavigationStrategy  terminalNavigationStrategy) {
        //id不为空，进行更新操作，否则进行添加
        if(terminalNavigationStrategy.getId() != null){
			//由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(terminalNavigationStrategy, Arrays.asList("id")).keySet();
            //按照字段更新对象
			terminalNavigationStrategyService.update( terminalNavigationStrategy , updateFields ) ;

			return ResponseParam.updateSuccess() ;
        }else{
			terminalNavigationStrategyService.save(terminalNavigationStrategy);
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
        terminalNavigationStrategyService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	

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