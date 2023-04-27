package com.geek.workbench.rest.controller.terminal;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.common.GlobalVariableValue;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.common.TerminalMessageContent;
import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.dto.terminal.TerminalNavigationTurnUrlDto;
import com.geek.workbench.entity.terminal.TerminalNavigationTurnUrl;
import com.geek.workbench.service.terminal.TerminalNavigationTurnUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 描述:  终端调整url控制层
 * @createDate: 2021/10/13 20:49
 * @author: gaojian
 */
@RestController
@RequestMapping(value=TerminalNavigationTurnUrlController.BASE_PATH)
public class TerminalNavigationTurnUrlController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/navigation/turn/url" ;

    /**
     * 描述:  依赖注入跳转页面服务
     * @createDate: 2021/11/20 9:50
     * @author: gaojian
     */
    @Autowired
    private TerminalNavigationTurnUrlService terminalNavigationTurnUrlService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "终端底部导航跳转页面",optName = "分页查询",optType = OptLogConst.QUERY)
    public ResponseParam query(@RequestBody TerminalNavigationTurnUrlDto terminalNavigationTurnUrlDto){

        // 1. 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalNavigationTurnUrlDto, null);

        // 2. 执行分页查询
        Page<TerminalNavigationTurnUrl> terminalNavigationTurnUrlPage = terminalNavigationTurnUrlService.queryByPage(searchParam ,
                terminalNavigationTurnUrlDto.getPageNum() ,
                terminalNavigationTurnUrlDto.getPageSize());

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalNavigationTurnUrlList = UtilDTO.toDTO(terminalNavigationTurnUrlPage.getContent(),
                null , null) ;
        return ResponseParam.success()
                .pageParam( terminalNavigationTurnUrlPage )
                .datalist( terminalNavigationTurnUrlList) ;
    }

    /**
     * 查询类型配置的
     * @return
     * @throws Exception
     */
    @PostMapping("query/all")
    @OptLog(optModule = "终端底部导航跳转页面",optName = "全部查询",optType = OptLogConst.QUERY)
    public ResponseParam queryAll(@RequestBody TerminalNavigationTurnUrlDto terminalNavigationTurnUrlDto){

        // 1. 参数非空判断
        Assert.isTrue(terminalNavigationTurnUrlDto.getAssociationType() != null, TerminalMessageContent.ASSOCIATION_IS_NULL);
        Assert.notNull(terminalNavigationTurnUrlDto.getTerminalId(), MessageContent.TERMINAL_ID_IS_NULL);

        // 2. 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalNavigationTurnUrlDto, null);

        // 3. 执行查询 没有值查询默认值
        String[] sorts = {"num:asc"};
        List<TerminalNavigationTurnUrl> terminalNavigationTurnUrlList = terminalNavigationTurnUrlService.queryAll(searchParam,sorts) ;
        if(terminalNavigationTurnUrlList == null || terminalNavigationTurnUrlList.size() < 1){
            searchParam.put(GlobalVariableKey.TERMINAL_ID, GlobalVariableValue.DEFAULT_TERMINAL_ID);
            terminalNavigationTurnUrlList = terminalNavigationTurnUrlService.queryAll(searchParam,sorts);
        }

        // 4. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> result = UtilDTO.toDTO(terminalNavigationTurnUrlList,
                null , getDtoCallbackHandler()) ;
        return ResponseParam.success()
                .datalist( result) ;
    }

    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule = "终端底部导航跳转页面",optName = "获取详情",optType = OptLogConst.GET)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){

	    // 1. 查询终端导航跳转地址表
        TerminalNavigationTurnUrl terminalNavigationTurnUrl = terminalNavigationTurnUrlService.get(param.getId()) ;

        // 2. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( terminalNavigationTurnUrl ,null , null ) ;
        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalNavigationTurnUrl
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule = "终端底部导航跳转页面",optName = "保存信息",optType = OptLogConst.SAVE)
    public ResponseParam save(@RequestBody TerminalNavigationTurnUrl  terminalNavigationTurnUrl) {

 	    // 1. 1id不为空，进行更新操作，否则进行添加
        if(terminalNavigationTurnUrl.getId() != null){

			// 2. 执行更新
			terminalNavigationTurnUrlService.updateInfo(terminalNavigationTurnUrl);
			return ResponseParam.updateSuccess() ;
        }else{

            // 3. 执行新增
			terminalNavigationTurnUrlService.saveInfo(terminalNavigationTurnUrl);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule = "终端底部导航跳转页面",optName = "删除",optType = OptLogConst.DELETE)
    @Deprecated
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

 	    // 1. 非空判断
 	    if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        terminalNavigationTurnUrlService.deleteInfo(list);
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
                String url = dtoMap.containsKey(GlobalVariableKey.TURN_URL) ? dtoMap.get(GlobalVariableKey.TURN_URL).toString() : "";
                String name = dtoMap.containsKey(GlobalVariableKey.TURN_NAME) ? dtoMap.get(GlobalVariableKey.TURN_NAME).toString() : "";
                dtoMap.clear();
                dtoMap.put(GlobalVariableKey.DICT_LABEL,name);
                dtoMap.put(GlobalVariableKey.DICT_VALUE,url);
            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }

}