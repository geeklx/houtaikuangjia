package com.geek.workbench.rest.controller.terminal;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.config.TerminalContent;
import com.geek.workbench.dto.microcoder.TerminalConfigMenuTopDto;
import com.geek.workbench.dto.terminal.TerminalConfigurationInfoDto;
import com.geek.workbench.entity.microcoder.*;
import com.geek.workbench.entity.terminal.TerminalConfigCommonEntity;
import com.geek.workbench.service.microcoder.*;
import com.geek.workbench.service.terminal.TerminalConfigCommonService;
import com.geek.workbench.entity.microcoder.TerminalConfigAgreementEntity;
import com.geek.workbench.service.microcoder.TerminalConfigAgreementService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类的描述
 *
 * @author fuhao
 * @version V1.0
 * @date 2021/9/30 10:51
 */
@RestController
@RequestMapping(value = TerminalConfigController.BASE_PATH)
public class TerminalConfigController extends AppIBaseController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/config";

    @Autowired
    private TerminalConfigAgreementService agreementConfigurationInfoService;

    @Autowired
    private TerminalConfigCommonService terminalCommonConfigurationInfoService ;



    /**
     * 功能描述: 查询终端协议配置接口
     * TODO
     *
     * @param navigationConfigurationInfoDto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/9/28 15:35
     */
    @PostMapping("query/agreement")
    @OptLog(optModule = "终端管理",optName = "终端管理协议配置查询",optType = OptLogConst.QUERY)
    public ResponseParam queryAllConfiguration(@RequestBody TerminalConfigMenuTopDto navigationConfigurationInfoDto) {
        // 返回结果
        HashMap<String, Object> resultHashMap = Maps.newHashMap();
        //获取查询参数
        Map<String, Object> searchParam = UtilDTO.toDTO(navigationConfigurationInfoDto, null);

        //查询拼装协议配置
        List<TerminalConfigAgreementEntity> agreementConfigurationInfoList = agreementConfigurationInfoService.queryAll(searchParam);
        resultHashMap.put("agreementConfigList",agreementConfigurationInfoList);

        //返回结果集
        return ResponseParam.success().data(resultHashMap);
    }


    /**
     * 功能描述: 保存协议接口
     * TODO
     *
     * @return
     * @author fuhao
     * @date 2021/10/11 9:37
     */
    @PostMapping("save/all")
    @OptLog(optModule = "终端管理",optName = "基础配置协议配置保存",optType = OptLogConst.SAVE)
    public ResponseParam saveAllConfiguration(@RequestBody TerminalConfigurationInfoDto terminalConfigurationInfoDto) {

        // 获取查询参数
        Map<String, Object> searchParam = UtilDTO.toDTO(terminalConfigurationInfoDto, null);

        // 保存协议配置
        Object agreementObject = searchParam.get("agreementInfo");
        if(agreementObject != null){
            TerminalConfigAgreementEntity agreementConfigurationInfo = (TerminalConfigAgreementEntity) agreementObject;
            if(agreementConfigurationInfo.getId() != null){
                Set<String> updateFields = UtilDTO.toDTOExcludeFields(agreementConfigurationInfo, Arrays.asList("id")).keySet();
                //按照字段更新对象
                agreementConfigurationInfoService.update(agreementConfigurationInfo , updateFields ) ;
            }else {
                agreementConfigurationInfoService.save(agreementConfigurationInfo);
            }
        }

        //返回结果集
        return ResponseParam.saveSuccess();
    }

    /**
     * 保存基础配置
     * @param params
     * @return
     * @author fuhao
     * @date 2021/10/12 11:05
     */
    @PostMapping("save/common")
    @OptLog(optModule = "终端管理",optName = "终端基础配置保存",optType = OptLogConst.SAVE)
    public ResponseParam saveCommonConfiguration(@RequestBody Map<String,Object> params) {
        return terminalCommonConfigurationInfoService.saveCommonConfiguration(params);
    }

    /**
     * 功能描述: 通过终端id查询全部基础配置
     * TODO
     *
     * @param params
     * @return
     * @author fuhao
     * @date 2021/10/12 11:05
     */
    @PostMapping("query/common")
    @OptLog(optModule = "终端管理",optName = "终端管理基础配置查询",optType = OptLogConst.QUERY)
    public ResponseParam queryCommonConfiguration(@RequestBody Map<String,String> params) {
        if(params.get(TerminalContent.TERMINAL_ID) == null){
            return ResponseParam.fail().message(TerminalContent.CHECK_TERMINAL_ID);
        }
        //String terminalId = ;
        TerminalConfigCommonEntity terminalCommonConfigurationInfo = new TerminalConfigCommonEntity();
        terminalCommonConfigurationInfo.setTerminalId(Long.parseLong(params.get(TerminalContent.TERMINAL_ID)));
        if(params.get("configType") != null){
            terminalCommonConfigurationInfo.setConfigType(params.get("configType"));
        }
        if(params.get("configCode") != null){
            terminalCommonConfigurationInfo.setConfigCode(params.get("configCode"));
        }
        List<TerminalConfigCommonEntity> allCommonConfigurationInfo
                = terminalCommonConfigurationInfoService.getAllCommonConfigurationInfo(terminalCommonConfigurationInfo);
        // 按导航功能分组
        Map<String, List<TerminalConfigCommonEntity>> result = allCommonConfigurationInfo.stream().collect(Collectors.groupingBy(TerminalConfigCommonEntity::getConfigType));
//        List<TerminalConfigAgreementEntity> agreementConfigurationInfoList = agreementConfigurationInfoService.queryAll(searchParam);
//        result.put("agreementConfigurationInfoList",agreementConfigurationInfoList);
        return ResponseParam.success().data(result);
    }
}
