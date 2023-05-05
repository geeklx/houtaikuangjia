package com.fosung.workbench.configurecenter.controller;

import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.configurecenter.service.WorkBenchConfigureService;
import com.fosung.workbench.configurecenter.service.WorkBenchConfigureServiceAdapter;
import com.fosung.workbench.configurecenter.service.impl.*;
import com.fosung.workbench.dict.TerminalAppCategoryType;
import com.fosung.workbench.dto.config.AppSearchParamDto;
import com.fosung.workbench.entity.project.ProjectBasicEntity;
import com.fosung.workbench.entity.terminal.TerminalBasicEntity;
import com.fosung.workbench.service.config.ConfigApiService;
import com.fosung.workbench.service.config.TerminalConfigApiService;
import com.fosung.workbench.service.microcoder.TerminalConfigAgreementService;
import com.fosung.workbench.service.project.ProjectBasicService;
import com.fosung.workbench.service.terminal.TerminalBasicService;
import com.fosung.workbench.util.AppHeaderResolution;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
/**
 *
 * 配置信息获取接口
 *
 * @author liuke
 * @date  2021/10/9 16:25
 * @version
*/
@RestController
@RequestMapping(value = WorkBenchConfigureController.BASE_PATH)
public class WorkBenchConfigureController extends AppIBaseController{
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/workbench" ;

    @Autowired
    private TerminalConfigAgreementServiceImpl terminalConfigAgreementService;

    @Autowired
    private TerminalConfigCertificationServiceImpl terminalConfigCertificationService;

    @Autowired
    private TerminalInitShareServiceImpl terminalInitShareService;

    @Autowired
    private TerminalBasicService terminalBasicService;

    @Autowired
    private TerminalAboutServiceImpl aboutService;

    @Autowired
    private TerminalResourceServiceImpl terminalResourceService;

    @Autowired
    private ProjectBasicService projectBasicService;

    /**
     *
     * 获取配置信息
     * @param servletRequest
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/init")
    public ResponseParam getInitDetail(@RequestBody(required = false) AppSearchParamDto appSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(appSearchParamDto,servletRequest);
        try {
            return ResponseParam.success().message("获取配置成功").data(terminalConfigAgreementService.getConfigDetailMessage(appSearchParamDto,servletRequest));
        }catch (Exception e){
            return ResponseParam.fail().message("配置获取失败，请核对参数是否正确");
        }

    }

    /**
     *
     * 获取配置信息
     * @param servletRequest
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/navgation")
    public ResponseParam getNavgation(@RequestBody(required = false) AppSearchParamDto appSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(appSearchParamDto,servletRequest);
        try {
            return ResponseParam.success().message("获取配置成功").data(terminalConfigAgreementService.getNavicationMessage(appSearchParamDto,servletRequest));
        }catch (Exception e){
            return ResponseParam.fail().message("配置获取失败，请核对参数是否正确");
        }

    }
    /**
     *
     * 获取认证信息
     * @param
     * @param servletRequest
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/getconfig")
    public ResponseParam getConfigDetail(@RequestBody AppSearchParamDto appSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(appSearchParamDto,servletRequest);
        try {
            return ResponseParam.success().message("获取配置成功").data(terminalConfigCertificationService.getConfigDetailMessage(appSearchParamDto,servletRequest));
        }catch (Exception e){
            return ResponseParam.fail().message("配置获取失败，请核对参数是否正确");
        }
    }

    /**
     *
     * 获取分享信息
     * @param  appSearchParamDto
     * @param servletRequest
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/getshare")
    public ResponseParam getShareDetail(@RequestBody AppSearchParamDto appSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(appSearchParamDto,servletRequest);
        try {
            return ResponseParam.success().message("获取配置成功").data(terminalInitShareService.getConfigDetailMessage( appSearchParamDto,servletRequest));
        }catch (Exception e){
            return ResponseParam.fail().message("配置获取失败，请核对参数是否正确");
        }
    }

    /**
     *
     * 获取关于信息
     * @param appSearchParamDto
     * @param servletRequest
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/getabout")
    public ResponseParam getAbout(@RequestBody(required = false) AppSearchParamDto appSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(appSearchParamDto,servletRequest);
        try {
            return ResponseParam.success().message("获取配置成功").data(aboutService.getConfigDetailMessage(appSearchParamDto,servletRequest));
        }catch (Exception e){
            return ResponseParam.fail().message("配置获取失败，请核对参数是否正确");
        }
    }

    /**
     *
     * 目录树信息
     * @param appSearchParamDto
     * @param servletRequest
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/getresource")
    public ResponseParam getResource(@RequestBody AppSearchParamDto appSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(appSearchParamDto,servletRequest);
        try {
            return ResponseParam.success().message("获取配置成功").data(terminalResourceService.getConfigDetailMessage(appSearchParamDto,servletRequest));
        }catch (Exception e){
            return ResponseParam.fail().message("配置获取失败，请核对参数是否正确");
        }
    }
    /**
     *获取终端
     *
     * @param httpServletRequest
     * @author liuke
     * @date 2021/10/22 14:34
     * @return com.fosung.workbench.entity.terminal.TerminalBasicEntity
     */
    public TerminalBasicEntity getTerminal( AppSearchParamDto shelvesSearchParamDto, HttpServletRequest httpServletRequest){
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(httpServletRequest);
        shelvesSearchParamDto.setToken(headerMessage.getToken() == null ? "" : headerMessage.getToken());
        List<TerminalBasicEntity> terminals = terminalBasicService.getCacheTerminal(headerMessage.getPackageName(),headerMessage.getTerminalType().name());
        TerminalBasicEntity terminal = new TerminalBasicEntity();
        for (TerminalBasicEntity terminalBasicEntity : terminals) {
            if(terminalBasicEntity.getTerminalType().equals(headerMessage.getTerminalType())){
                terminal = terminalBasicEntity;
            }
        }
        if(terminal!=null){
            shelvesSearchParamDto.setTerminalId(terminal.getId());
            shelvesSearchParamDto.setTerminalBasicEntity(terminal);
            ProjectBasicEntity projectBasicEntity = projectBasicService.get(terminal.getProjectId());
            shelvesSearchParamDto.setProjectBasicEntity(projectBasicEntity);
        }
        if(UtilString.isNotBlank(shelvesSearchParamDto.getIdentityId())){
            shelvesSearchParamDto.setIdentityIds(Sets.newHashSet(shelvesSearchParamDto.getIdentityId().split(",")));
        }
        if(UtilString.isNotBlank(shelvesSearchParamDto.getOrgId())){
            shelvesSearchParamDto.setOrdIds(Sets.newHashSet(shelvesSearchParamDto.getOrgId().split(",")));
        }
        if(Lists.newArrayList("me","all","routine","regional").contains(shelvesSearchParamDto.getCategoryType())){
            shelvesSearchParamDto.setCategoryTypeEnum(TerminalAppCategoryType.valueOf(shelvesSearchParamDto.getCategoryType()));
        }
        //todo  添加用户信息
        return terminal;
    }
}
