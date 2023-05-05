package com.fosung.workbench.rest.controller.microcoder;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.microcoder.TerminalConfigNavigationTopDto;
import com.fosung.workbench.entity.microcoder.TerminalConfigNavigationTop;
import com.fosung.workbench.service.microcoder.TerminalConfigNavigationTopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 描述:  顶部导航控制层
 * @createDate: 2021/11/04 10:25
 * @author: gaojian
 */
@RestController
@RequestMapping(value=TerminalConfigNavigationTopController.BASE_PATH)
public class TerminalConfigNavigationTopController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/config/navigation/top" ;

    /**
     * 描述:  终端顶部导航服务
     * @createDate: 2021/11/8 10:09
     * @author: gaojian
     */
    @Autowired
    private TerminalConfigNavigationTopService terminalConfigNavigationTopService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="终端管理-导航配置",optName="查询顶部导航信息",optType= OptLogConst.QUERY)
	public ResponseParam query(@RequestBody TerminalConfigNavigationTopDto terminalConfigNavigationTopDto){

        // 1. 执行查询
        TerminalConfigNavigationTop terminalConfigNavigationTop = new TerminalConfigNavigationTop();
        UtilBean.copyProperties(terminalConfigNavigationTopDto,terminalConfigNavigationTop);
        List<Map<String,Object>> list = terminalConfigNavigationTopService.queryInfo(terminalConfigNavigationTop);

        // 2. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> result = UtilDTO.toDTO(list,
                null , getDtoCallbackHandler()) ;

        // 3. 返回结果信息
        return ResponseParam.success()
                .datalist( result) ;
    }

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query/page")
    @OptLog(optModule="终端管理-导航配置",optName="查询顶部导航信息--分页",optType= OptLogConst.QUERY)
    public ResponseParam queryPage(@RequestBody TerminalConfigNavigationTopDto terminalConfigNavigationTopDto){

        // 1. 执行分页查询
        Pageable pageable = MybatisPageRequest.of(terminalConfigNavigationTopDto.getPageNum(),terminalConfigNavigationTopDto.getPageSize());
        TerminalConfigNavigationTop terminalConfigNavigationTop = new TerminalConfigNavigationTop();
        UtilBean.copyProperties(terminalConfigNavigationTopDto,terminalConfigNavigationTop);
        Page<Map<String,Object>> applicationVersionPage = terminalConfigNavigationTopService.queryInfoPage(terminalConfigNavigationTop,pageable);

        // 2. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> applicationVersionList = UtilDTO.toDTO(applicationVersionPage.getContent(),
                null , getDtoCallbackHandler()) ;

        // 3. 返回结果信息
        return ResponseParam.success()
                .pageParam( applicationVersionPage )
                .datalist( applicationVersionList) ;
    }

    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule="终端管理-导航配置",optName="获取顶部导航详细信息",optType= OptLogConst.GET)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){

        // 1. 查询顶部导航表
        TerminalConfigNavigationTop terminalConfigNavigationTop = terminalConfigNavigationTopService.get(param.getId()) ;

        // 2. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( terminalConfigNavigationTop ,null , getDtoCallbackHandler() ) ;
        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalConfigNavigationTop
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule="终端管理-导航配置",optName="保存顶部导航信息",optType= OptLogConst.SAVE)
    public ResponseParam save(@RequestBody TerminalConfigNavigationTop  terminalConfigNavigationTop) {

 	    // 1. id不为空，进行更新操作，否则进行添加
        if(terminalConfigNavigationTop.getId() != null){

            // 2. 执行修改
			terminalConfigNavigationTopService.updateInfo(terminalConfigNavigationTop);
			return ResponseParam.updateSuccess() ;
        }else{

            // 3. 执行新增
			terminalConfigNavigationTopService.saveInfo(terminalConfigNavigationTop);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule="终端管理-导航配置",optName="删除顶部导航信息",optType= OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

 	    // 1. 非空判断
 	    if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        terminalConfigNavigationTopService.deleteInfo(list);
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