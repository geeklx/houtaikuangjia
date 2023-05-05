package com.fosung.workbench.configurecenter.service.impl.app;


import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.common.util.UtilTree;
import com.fosung.workbench.AppBean.*;
import com.fosung.workbench.config.ContanstProperties;
import com.fosung.workbench.configurecenter.service.impl.AbstractWorkBenchConfig;
import com.fosung.workbench.dict.StatusType;
import com.fosung.workbench.dict.TerminalAppCategoryType;
import com.fosung.workbench.dict.TerminalQueryType;
import com.fosung.workbench.dto.config.AppSearchParamDto;
import com.fosung.workbench.entity.application.ApplicationBasicEntity;
import com.fosung.workbench.entity.application.ApplicationConfigHtmlEntity;
import com.fosung.workbench.entity.application.ApplicationOwnerEntity;
import com.fosung.workbench.entity.application.ApplicationVersionEntity;
import com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.fosung.workbench.entity.terminal.TerminalBasicEntity;
import com.fosung.workbench.entity.terminal.TerminalCategoryAppEntity;
import com.fosung.workbench.entity.terminal.TerminalConfigCategoryEntity;
import com.fosung.workbench.service.application.ApplicationBasicService;
import com.fosung.workbench.service.application.ApplicationOwnerService;
import com.fosung.workbench.service.application.ApplicationVersionService;
import com.fosung.workbench.service.terminal.*;
import com.fosung.workbench.util.CacheUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class AppSearchServiceImpl extends AbstractWorkBenchConfig {

    @Autowired
    private TerminalApplicationShelvesService terminalApplicationShelvesService;

    @Autowired
    private TerminalApplicationConfigService terminalApplicationConfigService;

    @Autowired
    private ApplicationVersionService applicationVersionService;

    @Autowired
    private ApplicationOwnerService applicationOwnerService;

    @Autowired
    private TerminalCategoryAppService terminalCategoryAppService;

    @Autowired
    private TerminalConfigCategoryService terminalConfigCategoryService;

    @Autowired
    private ApplicationBasicService applicationBasicService;

    @Autowired
    private ContanstProperties contanst;

    @Autowired
    private TerminalBasicService terminalBasicService;

    /**
     *根据appName获取应用（应用查询）
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 17:30
     * @return java.util.List<com.fosung.workbench.AppBean.FSearchBean>
     */
    public List<FSearchBean> searchAppByName(AppSearchParamDto shelvesSearchParamDto ){
        List<FSearchBean> infoList = new ArrayList<>();
        FSearchBean fSearchBean = new FSearchBean();
        Long terminalId = shelvesSearchParamDto.getTerminalBasicEntity().getId();
        String appName = shelvesSearchParamDto.getAppName();
        Set<Long> ids = getShvleIds(shelvesSearchParamDto);
        List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(terminalId);
        applicationConfigEntities=applicationConfigEntities.stream().filter(app -> (ids.contains(app.getId())&& UtilString.contains(app.getAppName(),appName))).collect(Collectors.toList());
        for (TerminalApplicationConfigEntity applicationConfigEntity : applicationConfigEntities) {
            fSearchBean.setTitle(applicationConfigEntity.getAppName());
            fSearchBean.setTitleHtml("<span style='color: blue;'>" + applicationConfigEntity.getAppName() + "</span>");
            fSearchBean.setItemId(applicationConfigEntity.getId().toString());
            fSearchBean.setHios(getAppInfoUrl(applicationConfigEntity));
            fSearchBean.setImgUrl(applicationConfigEntity.getIconUrl());
            infoList.add(fSearchBean);
        }
        return infoList;
    }

    /**
     *查询全部应用
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<AppInfo> searchAllApp(AppSearchParamDto shelvesSearchParamDto){
        Set<Long> ids = getShvleIds(shelvesSearchParamDto);
        List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(shelvesSearchParamDto.getTerminalId());
        applicationConfigEntities=applicationConfigEntities.stream().filter(app -> (ids.contains(app.getId()))).collect(Collectors.toList());
        if(StringUtils.isNotBlank(shelvesSearchParamDto.getAppName())){
            applicationConfigEntities = applicationConfigEntities.stream().filter(app -> (app.getAppName().contains(shelvesSearchParamDto.getAppName()))).collect(Collectors.toList());
        }
        return getAppInfos(applicationConfigEntities,shelvesSearchParamDto);
    }

    /**
     *查询全部应用分类
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<Map<String,Object>> searchAllCategoryAppList(AppSearchParamDto shelvesSearchParamDto, TerminalAppCategoryType... terminalAppCategoryTypes){
        Set<Long> shlvesIds = getShvleIds(shelvesSearchParamDto);

        return UtilTree.getTreeData(UtilDTO.toDTO(searchAllCategoryApp(shelvesSearchParamDto,shlvesIds,terminalAppCategoryTypes),null,Lists.newArrayList("createUserId","lastUpdateUserId","del","createDatetime","lastUpdateDatetime"),null),"id","parentId","children",true);
    }
    /**
     *查询地域应用分类
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public Map<String,Object> searchCategoryAppList(AppSearchParamDto shelvesSearchParamDto){
        Map<String,Object> data = Maps.newHashMap();
        Set<Long> shlvesIds = getShvleIds(shelvesSearchParamDto);
        List<TerminalConfigCategoryEntity> configCategoryEntities = searchAllCategoryApp(shelvesSearchParamDto,shlvesIds,shelvesSearchParamDto.getCategoryTypeEnum());
        if(!UtilCollection.sizeIsEmpty(configCategoryEntities)){
            List<AppInfo> appInfos = configCategoryEntities.get(0).getApps();
            List<Map<String,Object>> result = UtilDTO.toDTO(appInfos,null,Lists.newArrayList("createUserId","lastUpdateUserId","del","createDatetime","lastUpdateDatetime"),null);
            data.put("data",result);
        }
        return data;
    }

    /**
     *查询地域应用分类
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public Map<String,Object> searchCategoryAppAllList(AppSearchParamDto shelvesSearchParamDto){
        Set<Long> shlvesIds = getShvleIds(shelvesSearchParamDto);
        Map<String,Object> data = Maps.newHashMap();
        Long terminalId = shelvesSearchParamDto.getTerminalId();
        List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(terminalId);
        //查询所有分类
        List<TerminalConfigCategoryEntity> terminalConfigCategoryEntities = terminalConfigCategoryService.getCacheAppByTerminal(shelvesSearchParamDto);
        terminalConfigCategoryEntities=terminalConfigCategoryEntities.stream().filter(map -> Arrays.asList(shelvesSearchParamDto.getCategoryTypeEnum()).contains(map.getType())).collect(Collectors.toList());
        for (TerminalConfigCategoryEntity terminalConfigCategoryEntity : terminalConfigCategoryEntities) {
            if(terminalConfigCategoryEntity.getType().equals(TerminalAppCategoryType.all)){
                List<AppInfo> allApps = searchAllApp(shelvesSearchParamDto);
                terminalConfigCategoryEntity.setApps(allApps);
            }else if(terminalConfigCategoryEntity.getType().equals(TerminalAppCategoryType.me)){
                List<AppInfo> myApps = searchMyApp(shelvesSearchParamDto,shlvesIds);
                terminalConfigCategoryEntity.setApps(myApps);
            }else {
                terminalConfigCategoryEntity.setApps(getAppInfos(getCateAppsByCode(applicationConfigEntities,terminalId,terminalConfigCategoryEntity.getCode()),shelvesSearchParamDto));
            }
        }
        if(!UtilCollection.sizeIsEmpty(terminalConfigCategoryEntities)){
            List<AppInfo> appInfos = terminalConfigCategoryEntities.get(0).getApps();
            List<Map<String,Object>> result = UtilDTO.toDTO(appInfos,null,Lists.newArrayList("createUserId","lastUpdateUserId","del","createDatetime","lastUpdateDatetime"),null);
            data.put("data",result);
        }
        return data;
    }



    /**
     *查询我的应用
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<AppInfo> searchMyApp(AppSearchParamDto shelvesSearchParamDto,Set<Long> ids){
        List<AppInfo> infos = Lists.newArrayList();
        List<TerminalApplicationConfigEntity> applicationConfigEntities = searchAllApp1(shelvesSearchParamDto,ids);
        Set<Long> cateids=getCateAppIdList(shelvesSearchParamDto,TerminalAppCategoryType.me);
        cateids.addAll(getOwnerAppIdList(shelvesSearchParamDto));
        applicationConfigEntities=applicationConfigEntities.stream().filter(app -> (cateids.contains(app.getId()))).collect(Collectors.toList());
        infos.addAll(getAppInfos(applicationConfigEntities,shelvesSearchParamDto));
        return infos;
    }

    /**
     *查询我的应用
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public MyAppList searchMyAppEdit(AppSearchParamDto shelvesSearchParamDto){
        Set<Long> shlvesIds = getShvleIds(shelvesSearchParamDto);
        MyAppList myAppList = new MyAppList();
        List<TerminalApplicationConfigEntity> applicationConfigEntities =  searchAllApp1(shelvesSearchParamDto,shlvesIds);
        //查询我的应用
        List<Long> myappIds = getOwnerAppIdList(shelvesSearchParamDto);
        applicationConfigEntities = applicationConfigEntities.stream().filter(map -> myappIds.contains(map.getId())).collect(Collectors.toList());

        List<MyAppBasic> list1=getMyappList(applicationConfigEntities,shelvesSearchParamDto);
        myAppList.setData(list1);
        return myAppList;

    }

    /**
     *获取全部父级分类
     *
     * @author liuke
     * @date 2021/10/26 11:11
     * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalConfigCategoryEntity>
     */
    public List<TerminalConfigCategoryEntity> getTerminalAppCategory(AppSearchParamDto shelvesSearchParamDto){
        //查询所有分类
        List<TerminalConfigCategoryEntity> terminalConfigCategoryEntities = terminalConfigCategoryService.getCacheAppByTerminal(shelvesSearchParamDto);
        return terminalConfigCategoryEntities.stream().filter(map -> map.getParentId()==null).collect(Collectors.toList());
    }

    /**
     *查询首页应用
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public Map<String,Object> getFirstPageAllApp(AppSearchParamDto shelvesSearchParamDto){
        int num = Integer.valueOf(shelvesSearchParamDto.getShowNum() == null ?"8": shelvesSearchParamDto.getShowNum()) ;
        Set<Long> shlvesIds = getShvleIds(shelvesSearchParamDto);
        AppList data1 = new AppList();
        List<TerminalConfigCategoryEntity> entities = searchAllCategoryApp(shelvesSearchParamDto,shlvesIds,TerminalAppCategoryType.routine,TerminalAppCategoryType.regional,TerminalAppCategoryType.me,TerminalAppCategoryType.all);
        List<AppInfo> myapps = searchMyApp(shelvesSearchParamDto,shlvesIds);
        List<AppInfo> allApps = searchAllApp(shelvesSearchParamDto);
        List<AppInfo> newAllApps = Lists.newArrayList();
        List<AppBasicType> data2 = new ArrayList<>();
        for (int i = 0; i < allApps.size(); i++) {
            newAllApps.add(allApps.get(i));
            if(i==(num-2)){
                break;
            }
        }
        String code="";
        for (TerminalConfigCategoryEntity entity : entities) {
            if(entity.getType().equals(TerminalAppCategoryType.all)){
                code = entity.getCode();
                break;
            }
        }
        //todo  更多信息
        //AppInfo appInfo = new AppInfo("-1","http://119.188.115.252:8090/resource-handle/uploads/image/2021-12-20/3521848210328913932.png","更多","dataability://"+ shelvesSearchParamDto.getPackageName() + ".hs.act.slbapp.ShouyeCateAct1{act}?query1={s}"+ code +"&query2={s}全部应用&query3={s}5",true);
        //newAllApps.add(appInfo);

        for (TerminalConfigCategoryEntity entity : entities) {
            if(entity.getType().equals(TerminalAppCategoryType.me)){
                data2.add(new AppBasicType(entity.getId().toString(),entity.getLogoUrl(),entity.getName(), "dataability://" + shelvesSearchParamDto.getPackageName() +  ".hs.act.slbapp.BjyyAct{act}?condition=login&query1={s}" + entity.getCode() + "&query2={s}2a&query3={s}3a",entity.getStatus(),myapps));
            }else if(entity.getType().equals(TerminalAppCategoryType.all)){
                data2.add(new AppBasicType(entity.getId().toString(),entity.getLogoUrl(),entity.getName(),"dataability://" + shelvesSearchParamDto.getPackageName() + ".hs.act.slbapp.ShouyeCateAct1{act}?query1={s}"+ entity.getCode() +"&query2={s}全部应用&query3={s}5",entity.getStatus(),allApps));
            }else {
                data2.add(new AppBasicType(entity.getId().toString(),entity.getLogoUrl(),entity.getName(),"dataability://" + shelvesSearchParamDto.getPackageName() + ".hs.act.slbapp.ShouyeCateAct1{act}?query1={s}"+ entity.getCode() +"&query2={s}全部应用&query3={s}5",entity.getStatus(),entity.getApps()));
            }
        }
        data1.setData(data2);
        return UtilDTO.toDTO(data1,null,null,null);
    }

    /**
     *查询我的应用编辑
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public Map<String,Object> getMyAppEdit(AppSearchParamDto shelvesSearchParamDto){
        Set<Long> shlvesIds = getShvleIds(shelvesSearchParamDto);
        AppList data1 = new AppList();
        List<TerminalConfigCategoryEntity> entities = searchAllCategoryApp(shelvesSearchParamDto,shlvesIds,TerminalAppCategoryType.routine,TerminalAppCategoryType.regional);
        List<Long> myapps = getOwnerAppIdList(shelvesSearchParamDto);
        Set<Long> gudings = getCateAppIdList(shelvesSearchParamDto,TerminalAppCategoryType.me);
        List<AppBasicType> data2 = new ArrayList<>();
        for (TerminalConfigCategoryEntity entity : entities) {
            for (AppInfo appInfo : entity.getApps()) {
                if(myapps.contains(Long.valueOf(appInfo.getId()))){
                    appInfo.setEnable(true);
                }else if(gudings.contains(Long.valueOf(appInfo.getId()))){
                    appInfo.setEnable(true);
                    appInfo.setId("-1");
                } else{
                    appInfo.setEnable(false);
                }
            }
            data2.add(new AppBasicType(entity.getId().toString(),entity.getLogoUrl(),entity.getName(),"",entity.getStatus(),entity.getApps()));
        }
        data1.setData(data2);
        return UtilDTO.toDTO(data1,null,null,null);
    }
    /**********************************************通用类***************************************************/

    /**
     *查询全部应用
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<AppInfo> searchAllApp(AppSearchParamDto shelvesSearchParamDto,Set<Long> ids){
        List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(shelvesSearchParamDto.getTerminalId());
        applicationConfigEntities=applicationConfigEntities.stream().filter(app -> (ids.contains(app.getId()))).collect(Collectors.toList());
        return getAppInfos(applicationConfigEntities,shelvesSearchParamDto);
    }

    /**
     *查询全部应用
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<TerminalApplicationConfigEntity> searchAllApp1(AppSearchParamDto shelvesSearchParamDto,Set<Long> ids){
        List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(shelvesSearchParamDto.getTerminalId());
        applicationConfigEntities=applicationConfigEntities.stream().filter(app -> (ids.contains(app.getId()))).collect(Collectors.toList());
        return applicationConfigEntities;
    }

    /**
     *查询全部应用无需授权
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<AppInfo> searchAllAppNoShelves(AppSearchParamDto shelvesSearchParamDto){
        List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(shelvesSearchParamDto.getTerminalId());
        return getAppInfos(applicationConfigEntities,shelvesSearchParamDto);
    }

    /**
     *查询全部指定分类应用实体
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<TerminalConfigCategoryEntity> searchAllCategoryApp(AppSearchParamDto shelvesSearchParamDto,Set<Long> shlvesIds, TerminalAppCategoryType... terminalAppCategoryTypes){

        Long terminalId = shelvesSearchParamDto.getTerminalId();
        List<TerminalApplicationConfigEntity> applicationConfigEntities = searchAllApp1(shelvesSearchParamDto,shlvesIds);
        //查询所有分类
        List<TerminalConfigCategoryEntity> terminalConfigCategoryEntities = searchAllCategory(shelvesSearchParamDto,terminalAppCategoryTypes);
        for (TerminalConfigCategoryEntity terminalConfigCategoryEntity : terminalConfigCategoryEntities) {
            terminalConfigCategoryEntity.setApps(getAppInfos(getCateAppsByCode(applicationConfigEntities,terminalId,terminalConfigCategoryEntity.getCode()),shelvesSearchParamDto));
        }
        return terminalConfigCategoryEntities;
    }

    /**
     *查询全部指定分类
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<TerminalConfigCategoryEntity> searchAllCategory(AppSearchParamDto shelvesSearchParamDto, TerminalAppCategoryType... terminalAppCategoryTypes){


        List<TerminalConfigCategoryEntity> terminalConfigCategoryEntities = terminalConfigCategoryService.getCacheAppByTerminal(shelvesSearchParamDto);
        terminalConfigCategoryEntities=terminalConfigCategoryEntities.stream().filter(map -> Arrays.asList(terminalAppCategoryTypes).contains(map.getType())).collect(Collectors.toList());
        return terminalConfigCategoryEntities;
    }

    /**
     *根据分类获取应用
     *
     * @param applicationConfigEntities
     * @param terminalId
     * @author liuke
     * @date 2021/10/25 20:29
     * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
     */
    public List<TerminalApplicationConfigEntity> getCateAppsByCode(List<TerminalApplicationConfigEntity> applicationConfigEntities,Long terminalId,String code){
        Set<Long> ids = getCacheAppByTerminal(terminalId,code);
        List<TerminalApplicationConfigEntity> applications = new ArrayList<>();
        for(Long id : ids){
             for(TerminalApplicationConfigEntity entity :applicationConfigEntities ){
                 if(entity.getId().equals(id)){
                     TerminalApplicationConfigEntity configEntity = terminalApplicationConfigService.get(id);
                     applications.add(configEntity);
                 }
             }
        }
       // List<TerminalApplicationConfigEntity> applications = UtilBean.copyBeans(applicationConfigEntities,TerminalApplicationConfigEntity.class);
        //applications=applicationConfigEntities.stream().filter(a -> ids.contains(a.getId())).collect(Collectors.toList()) ;
        return applications;
    }

    /**
     *获取用户授权的id
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 17:04
     * @return java.util.Set<java.lang.Long>
     */
    public Set<Long> getShvleIds(AppSearchParamDto shelvesSearchParamDto){
        Set<Long> ids = Sets.newHashSet();

        TerminalBasicEntity terminalBasicEntity = terminalBasicService.get(shelvesSearchParamDto.getTerminalId());

        if(TerminalQueryType.all.equals(terminalBasicEntity.getQueryAppType())

        || terminalBasicEntity.getQueryAppType() == null){

            // 查询终端绑定的全部应用 2021-12-14 加
            List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(shelvesSearchParamDto.getTerminalId());
            ids.addAll( applicationConfigEntities.stream().map(map -> map.getId()).collect(Collectors.toSet()));

        }else{

            // 查询终端授权信息
            ids.addAll(queryShvleIds(shelvesSearchParamDto));
        }
        return ids;
    }

    /**
     *获取用户授权的id
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 17:04
     * @return java.util.Set<java.lang.Long>
     */
    public Set<Long> queryShvleIds(AppSearchParamDto shelvesSearchParamDto){
        Set<Long> ids = Sets.newHashSet();

        //获取以用户维度授权的信息
        if(shelvesSearchParamDto.getUserId()!=null){
            ids.addAll(terminalApplicationShelvesService.getUserRangeIds(shelvesSearchParamDto.getTerminalId(),shelvesSearchParamDto.getUserId()));
        }
        //获取以身份维度授权的信息
        if(!UtilCollection.sizeIsEmpty(shelvesSearchParamDto.getIdentityIds())){
            ids.addAll(terminalApplicationShelvesService.getIdenRangeIds(shelvesSearchParamDto.getTerminalId(),shelvesSearchParamDto.getIdentityIds()));
        }
        //获取以组织纬度授权的信息
        if(!UtilCollection.sizeIsEmpty(shelvesSearchParamDto.getOrdIds())){
            ids.addAll(terminalApplicationShelvesService.getOrgRangeIds(shelvesSearchParamDto.getTerminalId(),shelvesSearchParamDto.getOrdIds()));
        }
        //获取以角色授权的信息  20220801加
        if(StringUtils.isNotBlank(shelvesSearchParamDto.getRoleId())){
            ids.addAll(terminalApplicationShelvesService.getRoleRangeIds(shelvesSearchParamDto.getTerminalBasicEntity().getTerminalCode(),shelvesSearchParamDto.getRoleId()));
        }
        return ids;
    }

    /**
     *用户接口配置信息缓存
     *
     * @author liuke
     * @date 2021/10/15 14:56
     * @return
     */
    public LoadingCache<Long,ApplicationVersionEntity> appConfigCache = CacheUtil.getInstance().build(
            new CacheLoader<Long,ApplicationVersionEntity>() {
                @Override
                public ApplicationVersionEntity load(Long key) throws Exception {
                    ApplicationVersionEntity applicationVersionEntity = applicationVersionService.getVersionInfo(key);
                    return applicationVersionEntity;
                }
            }
    );

    /**
     * 在缓存取出认证配置
     * @param terminalApplicationConfigEntity
     * @author liuke
     * @date 2021/10/18 10:05
     * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
     */
    public String getPackageAndConfig(TerminalApplicationConfigEntity terminalApplicationConfigEntity){
        try {
             ApplicationBasicEntity applicationBasicEntity = applicationBasicService.getAppById(terminalApplicationConfigEntity.getAppId());
            //TerminalBasicEntity terminalBasic = terminalBasicService.get(terminalApplicationConfigEntity.getTerminalId());
            switch (terminalApplicationConfigEntity.getAppType()){
                case ios:
                    return "dataability://" +applicationBasicEntity.getPackageName()+"."+terminalApplicationConfigEntity.getStartName();
                case android:
                    return "dataability://" +applicationBasicEntity.getPackageName()+"."+terminalApplicationConfigEntity.getStartName();
                case h5:
                    ApplicationVersionEntity applicationBaseConfigEntities = appConfigCache.get(terminalApplicationConfigEntity.getAppVersionId());
                    if(null == applicationBaseConfigEntities){
                        return "";
                    }
                    ApplicationConfigHtmlEntity applicationConfigAndroidEntity = (ApplicationConfigHtmlEntity) (applicationBaseConfigEntities.getAppConfigs().get(0));
                    return applicationConfigAndroidEntity.getFrontUrl();
                default:
                    return "dataability://" +applicationBasicEntity.getPackageName()+"."+terminalApplicationConfigEntity.getStartName();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *拼接参数
     *
     * @param applicationConfigEntity
     * @author liuke
     * @date 2021/10/29 9:51
     * @return java.lang.String
     */
    public String getParam(TerminalApplicationConfigEntity applicationConfigEntity){
        StringBuffer sb = new StringBuffer();
        if(UtilString.isNotBlank(applicationConfigEntity.getStartParam())){
            if(!applicationConfigEntity.getStartParam().contains("?")){
                sb.append("?");
            }
            sb.append(applicationConfigEntity.getStartParam());
        }
        return sb.toString();
    }

    /**
     *格式化应用返回类
     *
     * @param applicationConfigEntities
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 19:17
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<AppInfo> getAppInfos(List<TerminalApplicationConfigEntity> applicationConfigEntities,AppSearchParamDto shelvesSearchParamDto){
        List<AppInfo> infos = Lists.newArrayList();
        for (TerminalApplicationConfigEntity applicationConfigEntity : applicationConfigEntities) {
            if(applicationConfigEntity.getAppName().equals("更多")){
                continue;
            }
            AppInfo appInfo = new AppInfo();
            appInfo.setEnable(applicationConfigEntity.getStatus().equals(StatusType.release));
            appInfo.setId(applicationConfigEntity.getId().toString());
            appInfo.setImg(applicationConfigEntity.getAppIcon());
            appInfo.setName(applicationConfigEntity.getAppName());
            appInfo.setUrl("");
            appInfo.setUrl(getAppInfoUrl(applicationConfigEntity));
            infos.add(appInfo);
        }
        return infos;
    }

    /**
     *格式化应用返回类
     *
     * @param applicationConfigEntities
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 19:17
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<MyAppBasic> getMyappList(List<TerminalApplicationConfigEntity> applicationConfigEntities,AppSearchParamDto shelvesSearchParamDto){
        List<MyAppBasic> infos = Lists.newArrayList();

        for (TerminalApplicationConfigEntity applicationConfigEntity : applicationConfigEntities) {
            MyAppBasic myAppBasic = new MyAppBasic();
            AppInfo appInfo = new AppInfo();
            appInfo.setEnable(applicationConfigEntity.getStatus().equals( StatusType.release));
            appInfo.setId(applicationConfigEntity.getId().toString());
            appInfo.setImg(applicationConfigEntity.getAppIcon());
            appInfo.setName(applicationConfigEntity.getAppName());
            appInfo.setUrl(getAppInfoUrl(applicationConfigEntity));
            myAppBasic.setMbean(appInfo);
            infos.add(myAppBasic);
        }
        return infos;
    }

    /**
     *
     * 获取我的应用id(不包含固定)
     * @author liuke
     * @date 2021/10/29 11:12
     * @return java.util.Set<java.lang.Long>
     */
    public List<Long> getOwnerAppIdList(AppSearchParamDto appSearchParamDto){
        List<ApplicationOwnerEntity> applicationOwnerEntities = applicationOwnerService.getMyApp(appSearchParamDto.getUserId(),appSearchParamDto.getTerminalId());
        //查询活动的部分
        List<Long> ids = applicationOwnerEntities.stream().map(owner -> owner.getAppConfigId()).collect(Collectors.toList());
        return ids;
    }

    /**
     *
     * 获取指定分类下应用id
     * @author liuke
     * @date 2021/10/29 11:12
     * @return java.util.Set<java.lang.Long>
     */
    public Set<Long> getCateAppIdList(AppSearchParamDto appSearchParamDto,TerminalAppCategoryType...types){
        //查询所有分类
        List<TerminalConfigCategoryEntity> terminalConfigCategoryEntities = terminalConfigCategoryService.getCacheAppByTerminal(appSearchParamDto);
        Set<String> codes =terminalConfigCategoryEntities.stream().filter(map -> Arrays.asList(types).contains(map.getType())).map( map -> map.getCode()).collect(Collectors.toSet());
        Set<Long> ids = Sets.newHashSet();
        for (String code : codes) {
            ids.addAll(getCacheAppByTerminal(appSearchParamDto.getTerminalId(),code));
        }
        return ids;
    }

    /**
     *终端应用分类配置缓存
     *
     * @author liuke
     * @date 2021/10/15 14:56
     * @return
     */
    public LoadingCache<Map,Set<Long>> terminalCategoryCache = CacheUtil.getInstance().build(
            new CacheLoader<Map,Set<Long>>() {
                @Override
                public Set<Long> load(Map key) throws Exception {
                    Set set=new LinkedHashSet();
                    Map<String,Object> searchParam = Maps.newHashMap();
                    searchParam.put("terminalId",key.get("terminalId"));
                    searchParam.put("categoryCode",key.get("code"));
                    List<TerminalCategoryAppEntity> categoryAppEntities = terminalCategoryAppService.queryAll(searchParam,new String[]{"num"});
                    for(TerminalCategoryAppEntity categoryAppEntity : categoryAppEntities){
                        set.add( categoryAppEntity.getAppConfigId());
                    }
                    return set;

                }
            }
    );


    /**
     *
     * 根据终端获取应用
     * @param terminalId
     * @author liuke
     * @date 2021/10/18 10:05
     * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
     */
    public Set<Long> getCacheAppByTerminal(Long terminalId,String code){
        Set<Long> ids = Sets.newHashSet();
        try {
            if(terminalId==null||UtilString.isBlank(code)){
                return ids;
            }
            Map<String,Object> map = Maps.newHashMap();
            map.put("terminalId",terminalId);
            map.put("code",code);
            ids = terminalCategoryCache.get(map);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ids;
    }

    /**
     *应用url
     *
     * @author liuke
     * @date 2021/10/15 14:56
     * @return
     */
    public LoadingCache<TerminalApplicationConfigEntity,String> applicationUrlCache = CacheUtil.getInstance().build(
            new CacheLoader<TerminalApplicationConfigEntity,String>() {
                @Override
                public String load(TerminalApplicationConfigEntity key) throws Exception {
                    return getPackageAndConfig(key) +getParam(key);
                }
            }
    );


    /**
     *
     * 应用url缓存
     * @param applicationConfigEntity
     * @author liuke
     * @date 2021/10/18 10:05
     * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
     */
    public String getAppInfoUrl(TerminalApplicationConfigEntity applicationConfigEntity){
        String url = "";
        try {
            url = applicationUrlCache.get(applicationConfigEntity);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return url;
    }
    public Map<String,Object>  querySelectedApp( AppSearchParamDto shelvesSearchParamDto){
        AppList data1 = new AppList();
        List<AppBasicType> data2 = new ArrayList<>();
        Set<Long> shelvesIds = this.queryShvleIds(shelvesSearchParamDto);
        //查询全部应用信息
        //List<TerminalConfigCategoryEntity> entities = searchAllCategoryApp(shelvesSearchParamDto,shelvesIds,TerminalAppCategoryType.routine,TerminalAppCategoryType.regional,TerminalAppCategoryType.me,TerminalAppCategoryType.all);
        List<AppInfo>  appInfos =  this.searchAllApp(shelvesSearchParamDto);
        for (AppInfo appInfo : appInfos) {
            //1.获取终端应用种类对应信息
            Map<String,Object> searchParam = Maps.newHashMap();
            searchParam.put("appConfigId",appInfo.getId());
            searchParam.put("terminalId",shelvesSearchParamDto.getTerminalId());
            List<TerminalCategoryAppEntity> appEntities = terminalCategoryAppService.queryAll(searchParam);
            if(!appEntities.isEmpty()){
                TerminalCategoryAppEntity terminalCategoryAppEntity =appEntities.get(0);

                //2.获取终端应用分类信息
                searchParam = Maps.newHashMap();
                searchParam.put("code",terminalCategoryAppEntity.getCategoryCode());
                searchParam.put("terminalId",terminalCategoryAppEntity.getTerminalId());
                List<TerminalConfigCategoryEntity> categoryEntities = terminalConfigCategoryService.queryAll(searchParam);

                //3.获取终端应用分类下的应用
                searchParam = Maps.newHashMap();
                searchParam.put("categoryCode",terminalCategoryAppEntity.getCategoryCode());
                searchParam.put("categoryType",terminalCategoryAppEntity.getCategoryType());
                List<TerminalCategoryAppEntity> categoryApps = terminalCategoryAppService.queryAll(searchParam);
                List<TerminalApplicationConfigEntity>  configEntityList= new ArrayList<>();
                categoryApps.forEach(terminalCategoryAppEntity1  -> {
                    TerminalApplicationConfigEntity  configEntity = terminalApplicationConfigService.get(terminalCategoryAppEntity1.getAppConfigId());
                    if(configEntity.getAppName().contains(shelvesSearchParamDto.getAppName())){
                        configEntityList.add(configEntity);
                    }
                });

                //组装数据
                if(!categoryEntities.isEmpty()){
                    TerminalConfigCategoryEntity entity = categoryEntities.get(0);

                    data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + configEntityList.size() + ")", "dataability://" + shelvesSearchParamDto.getPackageName() + contanst.getAllAppLink(), entity.getStatus(), getAppInfos(configEntityList,shelvesSearchParamDto)));
                }
            }
        }
        data2 = data2.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new  TreeSet<>(Comparator.comparing(AppBasicType :: getId))), ArrayList::new));

        data1.setData( data2);
        return UtilDTO.toDTO(data1,null,null,null);

    }
}
