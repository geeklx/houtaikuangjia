package com.fosung.workbench.rest.controller.microcoder;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilTree;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.common.TerminalMessageContent;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.microcoder.TerminalConfigMenuTopDto;
import com.fosung.workbench.entity.microcoder.TerminalConfigMenuTopEntity;
import com.fosung.workbench.service.microcoder.TerminalConfigMenuTopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 描述:  顶部菜单控制层
 * @createDate: 2021/11/04 10:25
 * @author: gaojian
 */
@RestController
@RequestMapping(value=TerminalConfigMenuTopController.BASE_PATH)
public class TerminalConfigMenuTopController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/config/menu/top" ;

    /**
     * 描述:  顶部菜单服务
     * @createDate: 2021/11/4 13:47
     * @author: gaojian
     */
    @Autowired
    private TerminalConfigMenuTopService TerminalConfigMenuTopService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="终端管理-基础配置",optName="查询顶部菜单配置信息",optType= OptLogConst.QUERY)
	public ResponseParam query(@RequestBody TerminalConfigMenuTopDto terminalConfigMenuTopDto){

        // 1. 获取查询参数
        Assert.notNull(terminalConfigMenuTopDto.getNavigationId(), TerminalMessageContent.TOP_NAVIGATION_ID_IS_NULL);
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalConfigMenuTopDto, null);

        // 2. 执行分页查询
        String[] sorts = {"num:asc"};
        List<TerminalConfigMenuTopEntity> list = TerminalConfigMenuTopService.queryAll(searchParam,sorts);
        if(list == null || list.size() < 1){
            return ResponseParam.success()
                    .datalist(list);
        }
        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> TerminalConfigMenuTopList = UtilDTO.toDTO(list,
                null , getDtoCallbackHandler()) ;
        List<Map<String, Object>> tree = UtilTree.getTreeData(TerminalConfigMenuTopList,"id","parentId","children",false);

        return ResponseParam.success()
                .datalist(tree);
    }

    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule="终端管理-基础配置",optName="获取顶部菜单配置信息",optType= OptLogConst.GET)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){

	    // 1. 查询顶部导航栏表
        TerminalConfigMenuTopEntity TerminalConfigMenuTop = TerminalConfigMenuTopService.get(param.getId()) ;

        // 2. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( TerminalConfigMenuTop ,null , getDtoCallbackHandler() ) ;
        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 描述:  菜单排序
     * @createDate: 2021/11/9 17:33
     * @author: gaojian
     * @modify:
     * @param list
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("sort")
    @OptLog(optModule="终端管理-基础配置",optName="顶部菜单排序",optType= OptLogConst.UPDATE)
    public ResponseParam sort(@RequestBody List<TerminalConfigMenuTopEntity> list){

        // 1. 非空判断
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 排序
        TerminalConfigMenuTopService.update(list,Arrays.asList("num"));
        return ResponseParam.updateSuccess();
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param TerminalConfigMenuTop
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule="终端管理-基础配置",optName="顶部菜单保存",optType= OptLogConst.SAVE)
    public ResponseParam save(@RequestBody TerminalConfigMenuTopEntity TerminalConfigMenuTop) {

 	    // 1. id不为空，进行更新操作，否则进行添加
        if(TerminalConfigMenuTop.getId() != null){

			// 2. 按照字段更新对象
			TerminalConfigMenuTopService.update(TerminalConfigMenuTop,Arrays.asList("name","url","img","num"));
			return ResponseParam.updateSuccess() ;
        }else{

            // 3. 执行保存
			TerminalConfigMenuTopService.saveInfo(TerminalConfigMenuTop);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule="终端管理-基础配置",optName="删除顶部菜单",optType= OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

 	    // 1. 非空判断
 	    if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        TerminalConfigMenuTopService.deleteInfo(list);
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