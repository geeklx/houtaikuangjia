package com.fosung.workbench.rest.controller.terminal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.terminal.TerminalVersionDto;
import com.fosung.workbench.entity.terminal.TerminalVersionEntity;
import com.fosung.workbench.entity.terminal.TerminalVersionUpgradeRangeEntity;
import com.fosung.workbench.service.terminal.TerminalVersionService;
import com.fosung.workbench.service.terminal.TerminalVersionUpgradeRangeService;
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
@RequestMapping(value=TerminalVersionController.BASE_PATH)
public class TerminalVersionController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/version" ;

    @Autowired
    private TerminalVersionService terminalVersionService ;

    @Autowired
    private TerminalVersionUpgradeRangeService upgradeRangeService;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "终端版本",optName = "终端版本分页查询",optType = OptLogConst.QUERY)
    public ResponseParam query(@RequestBody TerminalVersionDto terminalVersionDto) {
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(terminalVersionDto, null);
        //执行分页查询

        Page<TerminalVersionEntity> terminalVersionPage = terminalVersionService.queryByPageTerminalVersion(searchParam , terminalVersionDto) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalVersionList = UtilDTO.toDTO(terminalVersionPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( terminalVersionPage )
                .datalist( terminalVersionList) ;
    }
    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule = "终端版本",optName = "获取详情",optType = OptLogConst.QUERY)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询终端版本号控制
        TerminalVersionEntity terminalVersion = terminalVersionService.get(param.getId()) ;
        Map<String,Object> searchParam = new HashMap<>();
        searchParam.put("terminalVersionId",terminalVersion.getId());
        List<TerminalVersionUpgradeRangeEntity> terminalVersionUpgradeRange = upgradeRangeService.queryAll(searchParam);
        if(terminalVersionUpgradeRange!=null && terminalVersionUpgradeRange.size() > 0){
            terminalVersion.setUpgradeRangeType(terminalVersionUpgradeRange.get(0).getUpgradeRangeType());
//            for (TerminalVersionUpgradeRangeEntity versionUpgradeRangeEntity:terminalVersionUpgradeRange) {
//                upgradeRangeValues.add(versionUpgradeRangeEntity.getUpgradeRangeValue());
//            }
            terminalVersion.setUpgradeRangeValues(terminalVersionUpgradeRange);
        }
        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( terminalVersion ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param terminalVersion
     * @return
     * @throws Exception
     * @author fuhao
     * @date 2021/10/14 9:18
     */
 	@PostMapping("save")
    @OptLog(optModule = "终端版本",optName = "新建/编辑功能",optType = OptLogConst.SAVE)
    public ResponseParam save(@RequestBody TerminalVersionEntity  terminalVersion) {
         return terminalVersionService.saveInfo(terminalVersion);
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule = "终端版本",optName = "终端版本删除",optType = OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        terminalVersionService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
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

    @PostMapping("update/status")
    @OptLog(optModule = "终端版本",optName = "发布/下线功能",optType = OptLogConst.SAVE)
    public ResponseParam updateStatus(@RequestBody TerminalVersionEntity  terminalVersion) {
        terminalVersionService.updateStatus(terminalVersion);
        return ResponseParam.updateSuccess();
    }
}