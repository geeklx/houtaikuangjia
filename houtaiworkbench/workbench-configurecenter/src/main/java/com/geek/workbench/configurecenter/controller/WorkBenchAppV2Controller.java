package com.geek.workbench.configurecenter.controller;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.configurecenter.service.impl.app.AppSearchV2ServiceImpl;
import com.geek.workbench.configurecenter.service.impl.app.SaveMyAppServiceImpl;
import com.geek.workbench.dict.TerminalAppCategoryType;
import com.geek.workbench.dto.config.AppSearchParamDto;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import com.geek.workbench.service.terminal.TerminalBasicService;
import com.geek.workbench.util.AppHeaderResolution;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
@RequestMapping(value = WorkBenchAppV2Controller.BASE_PATH)
public class WorkBenchAppV2Controller extends AppIBaseController{
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/workbench/v2" ;


    @Autowired
    private TerminalBasicService terminalBasicService;

    @Autowired
    private AppSearchV2ServiceImpl appSearchService;

    @Autowired
    private SaveMyAppServiceImpl saveMyAppService;

    private List<String> excludes = Lists.newArrayList("createUserId","lastUpdateUserId","del","createDatetime","lastUpdateDatetime");



    /**
     *
     * 获取我的应用  V2
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/select/my/app")
    public ResponseParam selectMyApp(@RequestBody AppSearchParamDto shelvesSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(shelvesSearchParamDto,servletRequest);
        Map<String,Object> result = UtilDTO.toDTO(appSearchService.searchMyAppEdit(shelvesSearchParamDto),null,excludes,null);
        return ResponseParam.success().message("查询我的应用成功").data(result);
    }






    /**
     *
     * 查询地域分类 V2
     * @param
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/region/cate")
    public ResponseParam selectRegionCate(@RequestBody AppSearchParamDto shelvesSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(shelvesSearchParamDto,servletRequest);
        List<Map<String,Object>> result = UtilDTO.toDTO(appSearchService.getTerminalTypeCategory(shelvesSearchParamDto),null,null,null);
        Map<String,Object> map = Maps.newHashMap();
        map.put("data",result);
        return ResponseParam.success().message("查询全部应用分类成功").data(map);
    }


    /**
     *
     * 查询地域分类 V2
     * @param
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/region/single/app")
    public ResponseParam getSingleRegionalApp(@RequestBody AppSearchParamDto shelvesSearchParamDto,  HttpServletRequest servletRequest){
        getTerminal(shelvesSearchParamDto,servletRequest);
        return ResponseParam.success().message("查询应用成功").data(appSearchService.getSingleRegionalApp(shelvesSearchParamDto));
    }
    /**
     *
     * 获取我的和全部应用  V2
     * @param appSearchParamDto
     * @param httpServletRequest
     * @author liuke
     * @date 2021/10/28 9:11
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/myandall")
    public ResponseParam getMyAndAllApp (@RequestBody AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest){
        try {
            getTerminal(appSearchParamDto,httpServletRequest);
            Map<String,Object> map = appSearchService.getMyAndAllApp(appSearchParamDto);
            if(map != null){
                return ResponseParam.success().message("查询应用成功").data(map);
            }else {
                return ResponseParam.fail().message("未查询到数据");
            }
        }catch (Exception ex){
            return ResponseParam.fail();
        }
    }

    /**
     *
     * 获取地域应用   V2
     * @param appSearchParamDto
     * @param httpServletRequest
     * @author liuke
     * @date 2021/10/28 9:11
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/regionalapp")
    public ResponseParam getRegionalApp(@RequestBody AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest){
        try {
            getTerminal(appSearchParamDto,httpServletRequest);
            Map<String,Object> map =new HashMap<>();
            //map = appSearchService.getRegionalApp(appSearchParamDto);
            //if(map != null){
                return ResponseParam.success().message("获取配置成功").data(map);
            //}else {
              //  return ResponseParam.fail().message("未查询到数据");
           // }
        }catch (Exception ex){
            return ResponseParam.fail();
        }
    }

    /**
     * 查询指定类型的应用
     * @param appSearchParamDto
     * @param httpServletRequest
     * @author 高健
     * @date 2022年4月2日17:33:33
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/query/category/app")
    public ResponseParam queryCategoryApp(@RequestBody AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest){
        try {
            getTerminal(appSearchParamDto,httpServletRequest);
            if(StringUtils.isBlank(appSearchParamDto.getCategoryType()) || (TerminalAppCategoryType.regional.equals(appSearchParamDto.getCategoryTypeEnum())
            && StringUtils.isBlank(appSearchParamDto.getCityName()))){
                return ResponseParam.fail().message("未获取到正确查询条件");
            }
            String categoryType = appSearchParamDto.getCategoryType();
            TerminalAppCategoryType terminalAppCategoryType = TerminalAppCategoryType.valueOf(categoryType);
            if(terminalAppCategoryType == null){
                return ResponseParam.fail().message("未获取到正确查询条件");
            }
            appSearchParamDto.setCategoryTypeEnum(terminalAppCategoryType);
            Map<String,Object> map = appSearchService.queryCategoryApp(appSearchParamDto);
            if(map != null){
                return ResponseParam.success().message("获取配置成功").data(map);
            }else {
                return ResponseParam.fail().message("未查询到数据");
            }
        }catch (Exception ex){
            return ResponseParam.fail();
        }
    }

    /**
     *
     * 编辑获取全部应用V2
     * @param appSearchParamDto
     * @param httpServletRequest
     * @author liuke
     * @date 2021/10/28 9:11
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/myapp/edit/all")
    public ResponseParam getAppAllEdit(@RequestBody AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest){
        try {
            getTerminal(appSearchParamDto,httpServletRequest);
            Map<String,Object> map = appSearchService.getMyAppAllEdit(appSearchParamDto);
            if(map != null){
                return ResponseParam.success().message("获取配置成功").data(map);
            }else {
                return ResponseParam.fail().message("未查询到数据");
            }
        }catch (Exception ex){
            return ResponseParam.fail();
        }
    }

    /**
     *
     * 编辑获取本地应用 V2
     * @param appSearchParamDto
     * @param httpServletRequest
     * @author liuke
     * @date 2021/10/28 9:11
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/myapp/edit/region")
    public ResponseParam getMyAppRegionalEdit(@RequestBody AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest){
        try {
            getTerminal(appSearchParamDto,httpServletRequest);
            Map<String,Object> map = appSearchService.getMyAppRegionalEdit(appSearchParamDto);
            if(map != null){
                return ResponseParam.success().message("获取配置成功").data(map);
            }else {
                return ResponseParam.fail().message("未查询到数据");
            }

        }catch (Exception ex){
            return ResponseParam.fail();
        }
    }


    /**
     *
     * 保存我的应用
     * @param appSearchParamDto
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/savemyapp")
    public ResponseParam saveMyApp(@RequestBody AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest){
        getTerminal(appSearchParamDto,httpServletRequest);
        saveMyAppService.saveMyApp(appSearchParamDto);
        return ResponseParam.success().message("保存成功");
    }

    /**
     *获取首页应用
     *
     * @param appSearchParamDto
     * @param httpServletRequest
     * @author liuke
     * @date 2021/11/9 10:42
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/search/first")
    public ResponseParam searchDtFirstAppList(@RequestBody AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest){
        try {
            getTerminal(appSearchParamDto,httpServletRequest);
            return ResponseParam.success().message("获取首页应用成功").data(appSearchService.searchDtFirstAppList(appSearchParamDto));
        }catch (Exception ex){
            return ResponseParam.fail();
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
    public TerminalBasicEntity getTerminal(AppSearchParamDto shelvesSearchParamDto, HttpServletRequest httpServletRequest)   {
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(httpServletRequest);
        List<TerminalBasicEntity> terminals = terminalBasicService.getCacheTerminal(headerMessage.getPackageName(),headerMessage.getTerminalType().name());
        TerminalBasicEntity terminal = new TerminalBasicEntity();
        for (TerminalBasicEntity terminalBasicEntity : terminals) {
            if(terminalBasicEntity.getTerminalType().equals(headerMessage.getTerminalType())){
                terminal = terminalBasicEntity;
            }
        }
        //终端类型
        shelvesSearchParamDto.setTerminalType(headerMessage.getTerminalType());
        if(terminal!=null){
            shelvesSearchParamDto.setTerminalId(terminal.getId());
            shelvesSearchParamDto.setPackageName(terminal.getPackageName());
            shelvesSearchParamDto.setTerminalBasicEntity(terminal);
            shelvesSearchParamDto.setShowNum(terminal.getShowNum());
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
        if(StringUtils.isBlank(shelvesSearchParamDto.getCityName())){
            shelvesSearchParamDto.setCityName(headerMessage.getCityName());
        }
        //todo  添加用户信息
        return terminal;
    }
}
