package com.geek.workbench.rest.controller.terminal;
import java.util.List;
import java.util.Map;

import com.geek.workbench.common.MessageContent;
import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.dto.terminal.TerminalImageConfigDto;
import com.geek.workbench.entity.terminal.TerminalImageConfigEntity;
import com.geek.workbench.service.terminal.TerminalImageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;

/**
 * 描述:  广告页、引导页 控制层
 * @createDate: 2021/10/25 17:57
 * @author: gaojian
 */
@RestController
@RequestMapping(value=TerminalImageConfigController.BASE_PATH)
public class TerminalImageConfigController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/image/config" ;

    /**
     * 描述:  注入终端图片服务
     * @createDate: 2021/10/25 17:57
     * @author: gaojian
     */
    @Autowired
    private TerminalImageConfigService terminalImageConfigService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "广告页/引导页配置",optName = "分页查询",optType = OptLogConst.QUERY)
	public ResponseParam query(@RequestBody TerminalImageConfigDto terminalImageConfigDto){

        // 1. 获取查询参数
        Assert.notNull(terminalImageConfigDto.getTerminalId(), MessageContent.TERMINAL_ID_IS_NULL);
        Assert.notNull(terminalImageConfigDto.getImageType(), MessageContent.IMAGE_TYPE_IS_NULL);
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalImageConfigDto, null);

        // 2. 执行分页查询
        Page<TerminalImageConfigEntity> terminalImageConfigPage = terminalImageConfigService.queryByPage(searchParam , terminalImageConfigDto.getPageNum() , terminalImageConfigDto.getPageSize()) ;

        // 3. 数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalImageConfigList = UtilDTO.toDTO(terminalImageConfigPage.getContent(),
                null , getDtoCallbackHandler()) ;

        // 4. 返回结果信息
        return ResponseParam.success()
                .pageParam( terminalImageConfigPage )
                .datalist( terminalImageConfigList) ;
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalImageConfigEntities
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule = "广告页/引导页配置",optName = "保存信息",optType = OptLogConst.SAVE)
    public ResponseParam save(@RequestBody List<TerminalImageConfigEntity>  terminalImageConfigEntities) {

 	    // 1. 调用保存接口
        terminalImageConfigService.saveInfo(terminalImageConfigEntities);
        return ResponseParam.saveSuccess() ;
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