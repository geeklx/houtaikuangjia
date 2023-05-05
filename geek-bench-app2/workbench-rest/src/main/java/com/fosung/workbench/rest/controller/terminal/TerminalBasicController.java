package com.fosung.workbench.rest.controller.terminal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.terminal.TerminalBasicDto;
import com.fosung.workbench.entity.terminal.TerminalBasicEntity;
import com.fosung.workbench.service.terminal.TerminalBasicService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(value=TerminalBasicController.BASE_PATH)
public class TerminalBasicController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/basic" ;

    @Autowired
    private TerminalBasicService terminalBasicInfoService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "终端管理",optName = "分页查询",optType = OptLogConst.QUERY)
    public ResponseParam query(@RequestBody TerminalBasicDto terminalBasicInfoDto) {
        //执行分页查询
        Page<TerminalBasicEntity> terminalBasicInfoPage = terminalBasicInfoService.queryTerminalBasicInfoByPage(terminalBasicInfoDto) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalBasicInfoList = UtilDTO.toDTO(terminalBasicInfoPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( terminalBasicInfoPage )
                .datalist( terminalBasicInfoList) ;
    }
    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule = "终端管理",optName = "查询详情",optType = OptLogConst.QUERY)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询终端基本信息
        TerminalBasicEntity terminalBasicInfo = terminalBasicInfoService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( terminalBasicInfo ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalBasicInfo
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule = "终端管理",optName = "终端保存",optType = OptLogConst.SAVE)
    public ResponseParam save(@RequestBody TerminalBasicEntity  terminalBasicInfo) {
        //id不为空，进行更新操作，否则进行添加
        if(terminalBasicInfo.getId() != null){
			//由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(terminalBasicInfo, Arrays.asList("id")).keySet();
            //按照字段更新对象
			terminalBasicInfoService.update( terminalBasicInfo , updateFields) ;

			return ResponseParam.updateSuccess() ;
        }else{
			return terminalBasicInfoService.saveInfo(terminalBasicInfo);
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule = "终端管理",optName = "终端删除",optType = OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        terminalBasicInfoService.deleteAllTerminal(list);
	

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
     * 功能描述: 查询终端下拉框
     * TODO
     *
     * @param terminalBasicInfoDto
     * @return
     * @author fuhao
     * @date 2021/10/13 10:48
     */
    @PostMapping("query/terminaloptions")
    //@OptLog(optModule = "终端管理",optName = "获取终端下拉框",optType = OptLogConst.QUERY)
    public ResponseParam queryTerminalOptions(@RequestBody TerminalBasicDto terminalBasicInfoDto){
        // 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalBasicInfoDto, null);

        // 执行查询操作
        List<Map<String, Object>> terminalOptions = UtilDTO.toDTO(terminalBasicInfoService.queryTerminalOptions(searchParam),
                Arrays.asList("id", "terminalName", "terminalCode"), getDtoCallbackHandler());
        // 返回结果集
        return ResponseParam.success().datalist(terminalOptions);
    }

    /**
     * 描述: 获取项目终端树结构
     * @param
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/10/28 17:09
     **/
    @PostMapping("query/terminal/tree")
    @OptLog(optModule = "终端管理",optName = "终端访问控制树结构",optType = OptLogConst.QUERY)
    public ResponseParam queryTerminalTree(){
        return ResponseParam.success().datalist(terminalBasicInfoService.queryTerminalTree());
    }
}