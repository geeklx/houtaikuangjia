package com.fosung.workbench.rest.controller.terminal;
import java.util.*;
import java.util.stream.Collectors;

import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.config.TerminalContent;
import com.fosung.workbench.dto.terminal.TerminalCategoryAppDto;
import com.fosung.workbench.dto.terminal.TerminalConfigCategoryDto;
import com.fosung.workbench.entity.microcoder.TerminalConfigMenuTopEntity;
import com.fosung.workbench.entity.terminal.TerminalConfigCategoryEntity;
import com.fosung.workbench.service.terminal.TerminalConfigCategoryService;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping(value=TerminalConfigCategoryController.BASE_PATH)
public class TerminalConfigCategoryController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/config/category" ;

    @Autowired
    private TerminalConfigCategoryService terminalConfigCategoryService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "工作台分类配置",optName = "分页查询",optType = OptLogConst.QUERY)
    @Deprecated
    public ResponseParam query(@RequestBody TerminalConfigCategoryDto terminalConfigCategoryDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalConfigCategoryDto, null);
        //执行分页查询
        Page<TerminalConfigCategoryEntity> terminalConfigCategoryPage = terminalConfigCategoryService.queryByPage(searchParam , terminalConfigCategoryDto.getPageNum() , terminalConfigCategoryDto.getPageSize()) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalConfigCategoryList = UtilDTO.toDTO(terminalConfigCategoryPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( terminalConfigCategoryPage )
                .datalist( terminalConfigCategoryList) ;
    }
    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule = "工作台分类配置",optName = "获取详情",optType = OptLogConst.QUERY)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询工作台应用分类
        TerminalConfigCategoryEntity terminalConfigCategory = terminalConfigCategoryService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( terminalConfigCategory ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalConfigCategory
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule = "工作台分类配置",optName = "保存信息",optType = OptLogConst.SAVE)
    public ResponseParam save(@RequestBody TerminalConfigCategoryEntity  terminalConfigCategory) {
        //id不为空，进行更新操作，否则进行添加
        if(terminalConfigCategory.getId() != null){
			//按照字段更新对象
			terminalConfigCategoryService.update(terminalConfigCategory,Arrays.asList("name","area","navigationBtmId","navigationBtmName","logoUrl","remark"));
			return ResponseParam.updateSuccess() ;
        }else{
            terminalConfigCategoryService.saveInfo(terminalConfigCategory);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 功能描述: 禁用/开启
     *
     * @param terminalConfigCategory
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/10/21 18:04
     */
    @PostMapping("update/status")
    @OptLog(optModule = "工作台分类配置",optName = "开启/禁用",optType = OptLogConst.UPDATE)
    public ResponseParam updateStatus(@RequestBody TerminalConfigCategoryEntity  terminalConfigCategory) {
        //按照字段更新对象
        terminalConfigCategoryService.update( terminalConfigCategory , Arrays.asList("status") );
        return ResponseParam.updateSuccess();
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule = "工作台分类配置",optName = "删除",optType = OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        terminalConfigCategoryService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	

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
     * 功能描述: 查询终端绑定应用与分类绑定的应用
     *
     * @param terminalConfigCategory
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/10/26 10:52
     */
    @PostMapping("query/app")
    @OptLog(optModule = "工作台分类配置",optName = "工作台分类配置查询应用",optType = OptLogConst.QUERY)
    public ResponseParam queryCategoryApp(@RequestBody TerminalCategoryAppDto terminalConfigCategory){
        return terminalConfigCategoryService.queryCategoryApp(terminalConfigCategory);
    }

    /**
     * 功能描述: 查询工作台分类
     *
     * @param terminalConfigCategory
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/10/26 10:54
     */
    @PostMapping("query/category")
    @OptLog(optModule = "工作台分类配置",optName = "工作台分类查询",optType = OptLogConst.QUERY)
    public ResponseParam queryCategory(@RequestBody TerminalCategoryAppDto terminalConfigCategory){
        return terminalConfigCategoryService.queryCategory(terminalConfigCategory);
    }

    /**
     * 描述: 终端分类排序
     * @param categoryList
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/6 10:04
     **/
    @PostMapping("sort")
    @OptLog(optModule = "工作台分类配置",optName = "修改排序",optType = OptLogConst.UPDATE)
    public ResponseParam sort(@RequestBody List<TerminalConfigCategoryEntity> categoryList) {
        // 返回结果
        if(categoryList != null && categoryList.size() > 0){
            for (TerminalConfigCategoryEntity terminalConfigCategory: categoryList) {
                terminalConfigCategoryService.update(terminalConfigCategory,Arrays.asList("num"));
            }
            return ResponseParam.updateSuccess();
        }
        //返回结果集
        return ResponseParam.updateFail();
    }


}