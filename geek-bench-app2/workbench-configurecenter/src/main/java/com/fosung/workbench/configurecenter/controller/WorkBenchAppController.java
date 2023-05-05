package com.fosung.workbench.configurecenter.controller;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.AppBean.AppInfo;
import com.fosung.workbench.AppBean.AppNavicationBtm;
import com.fosung.workbench.configurecenter.service.impl.app.AppSearchServiceImpl;
import com.fosung.workbench.configurecenter.service.impl.app.SaveMyAppServiceImpl;
import com.fosung.workbench.dict.TerminalAppCategoryType;
import com.fosung.workbench.dto.busi.BusiOrgAdmDto;
import com.fosung.workbench.dto.config.AppSearchParamDto;
import com.fosung.workbench.entity.terminal.TerminalBasicEntity;
import com.fosung.workbench.service.application.ApplicationConfigHtmlService;
import com.fosung.workbench.service.busi.BusiOrgAdmService;
import com.fosung.workbench.service.terminal.TerminalBasicService;
import com.fosung.workbench.util.AppHeaderResolution;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 配置信息获取接口
 *
 * @author liuke
 * @date  2021/10/9 16:25
 * @version
*/
@RestController
@RequestMapping(value = WorkBenchAppController.BASE_PATH)
public class WorkBenchAppController extends AppIBaseController{
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/workbench" ;


    @Autowired
    private TerminalBasicService terminalBasicService;

    @Autowired
    private AppSearchServiceImpl appSearchService;

    @Autowired
    private SaveMyAppServiceImpl saveMyAppService;
    @Autowired
    private BusiOrgAdmService busiOrgAdmService;
    @Autowired
    private ApplicationConfigHtmlService applicationConfigHtmlService;

    private List<String> excludes = Lists.newArrayList("createUserId","lastUpdateUserId","del","createDatetime","lastUpdateDatetime");
    /**
     *
     * 获取我的应用
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/search/app")
    public ResponseParam searchApp(@RequestBody AppSearchParamDto shelvesSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(shelvesSearchParamDto,servletRequest);
        List<Map<String,Object>> result = UtilDTO.toDTO(appSearchService.searchAppByName(shelvesSearchParamDto),null,excludes,null);
        return ResponseParam.success().message("查询应用成功").data(result );
    }

    /**
     *
     * 查询应用
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/select/app")
    public ResponseParam selectApp(@RequestBody AppSearchParamDto shelvesSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(shelvesSearchParamDto,servletRequest);
        List<Map<String,Object>> result = UtilDTO.toDTO(appSearchService.searchAllApp(shelvesSearchParamDto),null,excludes,null);
        return ResponseParam.success().message("查询应用成功").data( result);
    }

    /**
     *
     * 获取我的应用
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
     * 查询全部分类应用
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/select/allcate/app")
    public ResponseParam selectMarketApp(@RequestBody AppSearchParamDto shelvesSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(shelvesSearchParamDto,servletRequest);
        List<Map<String,Object>> result = appSearchService.searchAllCategoryAppList(shelvesSearchParamDto, TerminalAppCategoryType.all,TerminalAppCategoryType.me,TerminalAppCategoryType.regional,TerminalAppCategoryType.routine);
        return ResponseParam.success().message("查询全部应用分类成功").data(result);
    }

    /**
     *
     * 查询指定分类应用
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/select/cate")
    public ResponseParam selectMarketAppType(@RequestBody AppSearchParamDto shelvesSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(shelvesSearchParamDto,servletRequest);
        return ResponseParam.success().message("查询分类成功").data(appSearchService.searchCategoryAppList(shelvesSearchParamDto));
    }

    /**
     *
     * 查询指定分类应用
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/select/cate/all")
    public ResponseParam selectMarketAppTypeAll(@RequestBody AppSearchParamDto shelvesSearchParamDto, HttpServletRequest servletRequest){
        getTerminal(shelvesSearchParamDto,servletRequest);
        return ResponseParam.success().message("查询分类应用成功").data(appSearchService.searchCategoryAppAllList(shelvesSearchParamDto));
    }

    /**
     *
     * 查询父分类
     * @param
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/parent/cate")
    public ResponseParam selectAllParentCate( HttpServletRequest servletRequest){
        AppSearchParamDto shelvesSearchParamDto = new AppSearchParamDto();
        getTerminal(shelvesSearchParamDto,servletRequest);
        List<Map<String,Object>> result = UtilDTO.toDTO(appSearchService.getTerminalAppCategory(shelvesSearchParamDto),null,excludes,null);
        return ResponseParam.success().message("查询全部应用分类成功").data(result);
    }
    /**
     *
     * 过去首页应用
     * @param appSearchParamDto
     * @param httpServletRequest
     * @author liuke
     * @date 2021/10/28 9:11
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/first/page")
    public ResponseParam getFirstPageAllApp(@RequestBody(required = false) AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest){
        try {
            getTerminal(appSearchParamDto,httpServletRequest);
            return ResponseParam.success().message("获取配置成功").data(appSearchService.getFirstPageAllApp(appSearchParamDto));
        }catch (Exception ex){
            return ResponseParam.fail();
        }
    }

    /**
     *
     * 过去首页应用
     * @param appSearchParamDto
     * @param httpServletRequest
     * @author liuke
     * @date 2021/10/28 9:11
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/myapp/edit")
    public ResponseParam getAppEdit(@RequestBody AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest){
        try {
            getTerminal(appSearchParamDto,httpServletRequest);
            return ResponseParam.success().message("获取配置成功").data(appSearchService.getMyAppEdit(appSearchParamDto));
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
     *
     * 移除我的应用
     * @param appSearchParamDto
     * @param httpServletRequest
     * @author liuke
     * @date 2021/10/14 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @RequestMapping("/removemyapp")
    public ResponseParam removeMyApp(@RequestBody AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest){
        try {
            getTerminal(appSearchParamDto,httpServletRequest);
            return ResponseParam.success().message("获取配置成功").data(saveMyAppService.removeMyApp(appSearchParamDto));
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
    public TerminalBasicEntity getTerminal(AppSearchParamDto shelvesSearchParamDto, HttpServletRequest httpServletRequest){
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(httpServletRequest);
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
        shelvesSearchParamDto.setPackageName(terminal.getPackageName());
        shelvesSearchParamDto.setShowNum(terminal.getShowNum());
        //todo  添加用户信息
        return terminal;
    }
    /**
     *获取轮播
     *
     * @param
     * @author lihuiming
     * @date 2021/12/20 14:34
     * @return com.fosung.workbench.entity.terminal.TerminalBasicEntity
     */
    @RequestMapping("/getplaypic")
    public ResponseParam getplaypic(@RequestBody AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest ){
        List<AppNavicationBtm> list = Lists.newArrayList();
        Map map = new HashMap();
        list.add(new AppNavicationBtm("123456","http://119.188.115.252:8090/resource-handle/uploads/image/2021-12-20/3521862286564142395.png","1","",true,"",""));
        list.add(new AppNavicationBtm("1234567","http://119.188.115.252:8090/resource-handle/uploads/image/2021-12-20/3521862378905937488.png","2","",true,"",""));
        list.add(new AppNavicationBtm("1234568","http://119.188.115.252:8090/resource-handle/uploads/image/2021-12-20/3521863886959548956.png","3","",true,"",""));
        map.put("data",list);
        return ResponseParam.success().message("获取信息成功").data(map);
    }
    /**
     *获取分页信息
     *
     * @param
     * @author lihuiming
     * @date 2021/12/20 14:34
     * @return com.fosung.workbench.entity.terminal.TerminalBasicEntity
     */
    @RequestMapping("/getmore")
    public ResponseParam getmore(@RequestBody AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest){
        if(0== appSearchParamDto.getPageSize()){
            return ResponseParam.fail().message("每页条数需大于0");
        }
        Map map =new HashMap();
        getTerminal(appSearchParamDto,httpServletRequest);
        List<AppInfo>  appList =  appSearchService.searchAllApp(appSearchParamDto);
        List<AppNavicationBtm> btmList= Pager(appSearchParamDto.getPageSize(),appSearchParamDto.getPageNum(),appList);
        map.put("data",btmList);
        return ResponseParam.success().message("获取信息成功").data(map);
    }

    public static List Pager(int pageSize, int pageIndex, List list) {
        //使用list 中的sublist方法分页
        List dataList = new ArrayList();
         // 每页显示多少条记录

        int currentPage; //当前第几页数据

        int totalRecord = list.size(); // 一共多少条记录

        int totalPage = totalRecord % pageSize; // 一共多少页
        if (totalPage > 0) {
            totalPage = totalRecord / pageSize + 1;
        } else {
            totalPage = totalRecord / pageSize;
        }

        System.out.println("totalPage" + totalPage);

        // 当前第几页数据
        //currentPage = totalPage > pageIndex ? pageIndex : totalPage;
        currentPage = pageIndex;
        // 起始索引
        int fromIndex = pageSize * (currentPage-1);

        // 结束索引
        int toIndex = pageSize * currentPage > totalRecord ? totalRecord : pageSize * currentPage;
        try {
            if (list.size() > 0) {
                if(fromIndex > toIndex){
                    dataList =new ArrayList();
                    return dataList;
                }
                dataList = list.subList(fromIndex, toIndex);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * 保存选择地域及组织信息
     * @param busiOrgAdmDto
     * @return
     */
    @RequestMapping("/saveSelectedAdm")
    public ResponseParam save(@RequestBody BusiOrgAdmDto busiOrgAdmDto) {
        if (null == busiOrgAdmDto || null == busiOrgAdmDto.getCity()) {
            return ResponseParam.fail().message("请选择城市");
        }
        Map map  = busiOrgAdmService.saveSelectedAdm(busiOrgAdmDto);
        return ResponseParam.saveSuccess().data(map);
    }

    /**
     * 根据选择地域查询组织
     * @param busiOrgAdmDto
     * @return
     */
    @RequestMapping("querySelectedAdm")
    public ResponseParam query(@RequestBody BusiOrgAdmDto busiOrgAdmDto){
       // if(null == busiOrgAdmDto || null == busiOrgAdmDto.getUserId()){
           // return ResponseParam.fail().message("用户Id不能为空");
       // }
        if(null == busiOrgAdmDto || null == busiOrgAdmDto.getTreeClientId()){
            return ResponseParam.fail().message("统一行政树ClientId不能为空");
        }
        ConcurrentHashMap map = busiOrgAdmService.querySelectedAdm(busiOrgAdmDto);
        return ResponseParam.success().message("获取信息成功").data(map) ;

    }

    /**
     * 查询应用列表
     */
    @RequestMapping("querySelectedApp")
    public ResponseParam querySelectedApp(@RequestBody(required = false) AppSearchParamDto appSearchParamDto,HttpServletRequest httpServletRequest  ){
        getTerminal(appSearchParamDto,httpServletRequest);
        return ResponseParam.success().message("获取信息成功").data(appSearchService.querySelectedApp(appSearchParamDto)) ;
    }
}
