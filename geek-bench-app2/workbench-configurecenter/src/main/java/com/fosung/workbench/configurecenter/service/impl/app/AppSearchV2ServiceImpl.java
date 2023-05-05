package com.fosung.workbench.configurecenter.service.impl.app;


import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.workbench.AppBean.*;
import com.fosung.workbench.config.ContanstProperties;
import com.fosung.workbench.configurecenter.service.impl.AbstractWorkBenchConfig;
import com.fosung.workbench.dict.StatusType;
import com.fosung.workbench.dict.TerminalAppCategoryType;
import com.fosung.workbench.dict.TerminalQueryType;
import com.fosung.workbench.dict.TerminalType;
import com.fosung.workbench.dto.config.AppSearchParamDto;
import com.fosung.workbench.entity.application.ApplicationBasicEntity;
import com.fosung.workbench.entity.application.ApplicationConfigHtmlEntity;
import com.fosung.workbench.entity.application.ApplicationOwnerEntity;
import com.fosung.workbench.entity.application.ApplicationVersionEntity;
import com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.fosung.workbench.entity.terminal.TerminalBasicEntity;
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
public class AppSearchV2ServiceImpl extends AbstractWorkBenchConfig {

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
     *查询全部应用无本地应用
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<AppInfo> searchAllAppNoregion(AppSearchParamDto shelvesSearchParamDto,Set<Long> ids){
        //查询不包含本地应用的所有应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities = getCacheAppNoregion(shelvesSearchParamDto);
        //获取不包含本地应用的所有授权应用
        applicationConfigEntities=applicationConfigEntities.stream().filter(app -> (ids.contains(app.getId()))).collect(Collectors.toList());
        //格式化返回结果
        return getAppInfos(applicationConfigEntities,shelvesSearchParamDto);
    }

    /**
     * 查询本地应用和常规应用
     *
     * @param shelvesSearchParamDto
     * @author 高健
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<AppInfo> searchAllAppNoregionAndRoutine(AppSearchParamDto shelvesSearchParamDto,Set<Long> ids){
        //查询不包含本地应用的所有应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities = getCacheAppNoregionAndRoutine(shelvesSearchParamDto);
        //获取不包含本地应用的所有授权应用
        applicationConfigEntities=applicationConfigEntities.stream().filter(app -> (ids.contains(app.getId()))).collect(Collectors.toList());
        //格式化返回结果
        return getAppInfos(applicationConfigEntities,shelvesSearchParamDto);
    }

    /**
     * 查询全部的本地和常规应用
     *
     * @param shelvesSearchParamDto
     * @author 高健
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<AppInfo> searchAllAppNoregionAnd(AppSearchParamDto shelvesSearchParamDto,Set<Long> ids){
        //查询不包含本地应用的所有应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities = getCacheAppNoregion(shelvesSearchParamDto);
        //获取不包含本地应用的所有授权应用
        applicationConfigEntities=applicationConfigEntities.stream().filter(app -> (ids.contains(app.getId()))).collect(Collectors.toList());
        //格式化返回结果
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
        //查询终端下所有应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(shelvesSearchParamDto.getTerminalId());
        //查询所有授权的应用
        applicationConfigEntities=applicationConfigEntities.stream().filter(app -> (ids.contains(app.getId()))).collect(Collectors.toList());
        return applicationConfigEntities;
    }


    /**
     *查询全部应用无需授权无本地应用
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<AppInfo> searchAllAppNoShelvesNoRegion(AppSearchParamDto shelvesSearchParamDto){
        //获取所有不含本地应用之外的其他应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities = getCacheAppNoregion(shelvesSearchParamDto);
        //格式化结果并返回
        return getAppInfos(applicationConfigEntities,shelvesSearchParamDto);
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
        //获取所有授权的应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities = searchAllApp1(shelvesSearchParamDto,ids);
        //获取我的应用分类下所有应用id
        Set<Long> cateids=getCateAppIdList(shelvesSearchParamDto,TerminalAppCategoryType.me);
        //获取自己添加的我的应用
        cateids.addAll(getOwnerAppIdList(shelvesSearchParamDto));
        //在全部应用中筛选出我的应用
        applicationConfigEntities=applicationConfigEntities.stream().filter(app -> (cateids.contains(app.getId()))).collect(Collectors.toList());
        //格式化结果返回
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
        //获取所有授权id
        Set<Long> shlvesIds = getShvleIds(shelvesSearchParamDto);
        MyAppList myAppList = new MyAppList();
        //获取终端下所有授权的应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities =  searchAllApp1(shelvesSearchParamDto,shlvesIds);
        //查询我的应用id
        List<Long> myappIds = getOwnerAppIdList(shelvesSearchParamDto);
        //筛选出我的应用
        applicationConfigEntities = applicationConfigEntities.stream().filter(map -> myappIds.contains(map.getId())).collect(Collectors.toList());
        //格式化返回结果
        List<MyAppBasic> list1=getMyappList(applicationConfigEntities,shelvesSearchParamDto);
        myAppList.setData(list1);
        return myAppList;

    }


    /**
     *获取指定类型分类
     *
     * @author liuke
     * @date 2021/10/26 11:11
     * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalConfigCategoryEntity>
     */
    public List<AppInfo> getTerminalTypeCategory(AppSearchParamDto shelvesSearchParamDto){
        //查询所有分类
        List<TerminalConfigCategoryEntity> terminalConfigCategoryEntities = terminalConfigCategoryService.getCacheAppByTerminal(shelvesSearchParamDto);
        //筛选要查询的分类
        terminalConfigCategoryEntities = terminalConfigCategoryEntities.stream().filter(map -> map.getType().equals(shelvesSearchParamDto.getCategoryTypeEnum())).collect(Collectors.toList());
        //如果是地域应用，筛选出指定地域
        if(shelvesSearchParamDto.getCategoryTypeEnum().equals(TerminalAppCategoryType.regional)&&UtilString.isNotBlank(shelvesSearchParamDto.getCityName())){
            terminalConfigCategoryEntities=terminalConfigCategoryEntities.stream().filter(map -> shelvesSearchParamDto.getCityName().startsWith(map.getArea())&&map.getStatus() == true).collect(Collectors.toList());
        }
        List<AppInfo> appInfos = Lists.newArrayList();
        //格式化返回结果返回
        for (TerminalConfigCategoryEntity terminalConfigCategoryEntity : terminalConfigCategoryEntities) {
            appInfos.add(new AppInfo(terminalConfigCategoryEntity.getCode(),terminalConfigCategoryEntity.getLogoUrl(),terminalConfigCategoryEntity.getName(),"",terminalConfigCategoryEntity.getStatus()));
        }
        return appInfos;
    }

    /**
     * 查询我的应用，全部应用不含地域
     * 20220401 潘潇潇、韩康和 需求增加常规应用
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public Map<String,Object> getMyAndAllApp(AppSearchParamDto shelvesSearchParamDto){
        //获取授权范围
        Set<Long> shlvesIds = getShvleIds(shelvesSearchParamDto);
        AppList data1 = new AppList();
        shelvesSearchParamDto.setCityName(StringUtils.isBlank(shelvesSearchParamDto.getCityName())?"济南":shelvesSearchParamDto.getCityName());
        //获取指定应用分类信息分类
        List<TerminalConfigCategoryEntity> myAndAllEntities = searchAllCategory(shelvesSearchParamDto,TerminalAppCategoryType.me,TerminalAppCategoryType.all,TerminalAppCategoryType.regional,TerminalAppCategoryType.routine);
        //查询我的应用信息
        List<AppInfo> myapps = searchMyApp(shelvesSearchParamDto,shlvesIds);
        //查询全部应用信息
        List<AppInfo> allApps = searchAllAppNoregionAndRoutine(shelvesSearchParamDto,shlvesIds);
        //查询本地应用
        List<AppInfo> regionalapps = searchRegionalApp(shelvesSearchParamDto,shlvesIds);
        shelvesSearchParamDto.setCategoryType(null);
        //查询常规应用信息
        List<AppInfo> routineapps = searchRoutineApp(shelvesSearchParamDto,shlvesIds);
        if(allApps.size()==0){
            return null;
        }
        List<AppBasicType> data2 = new ArrayList<>();
        //格式化返回结果
        for (TerminalConfigCategoryEntity entity : myAndAllEntities) {
            if (entity.getType().equals(TerminalAppCategoryType.me)) {
                if (shelvesSearchParamDto.getTerminalType().equals(TerminalType.android)) {
                    data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + myapps.size() + ")", "dataability://" + shelvesSearchParamDto.getPackageName() + contanst.getMyAppLink(), entity.getStatus(), myapps));
                } else if (shelvesSearchParamDto.getTerminalType().equals(TerminalType.ios)) {
                    data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + myapps.size() + ")", contanst.getMyAppLink(), entity.getStatus(), myapps));
                } else {
                    data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + myapps.size() + ")", contanst.getMyAppLink(), entity.getStatus(), myapps));
                }
            } else if (entity.getType().equals(TerminalAppCategoryType.all)) {
                if (shelvesSearchParamDto.getTerminalType().equals(TerminalType.android)) {
                    data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + allApps.size() + ")", "dataability://" + shelvesSearchParamDto.getPackageName() + contanst.getAllAppLink(), entity.getStatus(), allApps));
                } else if (shelvesSearchParamDto.getTerminalType().equals(TerminalType.ios)) {
                    data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + allApps.size() + ")", contanst.getAllAppLink(), entity.getStatus(), allApps));
                } else {
                    data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + myapps.size() + ")", contanst.getMyAppLink(), entity.getStatus(), allApps));
                }
            }else if (entity.getType().equals(TerminalAppCategoryType.regional)) {
                if (shelvesSearchParamDto.getTerminalType().equals(TerminalType.android)) {
                    data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + regionalapps.size() + ")", "dataability://" + shelvesSearchParamDto.getPackageName() + contanst.getAllAppLink(), entity.getStatus(), regionalapps));
                } else if (shelvesSearchParamDto.getTerminalType().equals(TerminalType.ios)) {
                    data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + regionalapps.size() + ")", contanst.getAllAppLink(), entity.getStatus(), regionalapps));
                } else {
                    data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + regionalapps.size() + ")", contanst.getMyAppLink(), entity.getStatus(), regionalapps));
                }
            }
            else if (entity.getType().equals(TerminalAppCategoryType.routine)) {
                if (shelvesSearchParamDto.getTerminalType().equals(TerminalType.android)) {
                    data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + routineapps.size() + ")", "dataability://" + shelvesSearchParamDto.getPackageName() + contanst.getAllAppLink(), entity.getStatus(), routineapps));
                 } else if (shelvesSearchParamDto.getTerminalType().equals(TerminalType.ios)) {
                     data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + routineapps.size() + ")", contanst.getAllAppLink(), entity.getStatus(), routineapps));
                 } else {
                    data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + routineapps.size() + ")", contanst.getMyAppLink(), entity.getStatus(), routineapps));
                 }
            }
        }
         data1.setData(data2);
        return UtilDTO.toDTO(data1,null,null,null);
    }
    /**
     *查询地域应用
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<AppInfo> searchRegionalApp(AppSearchParamDto shelvesSearchParamDto,Set<Long> ids){
        List<AppInfo> infos = Lists.newArrayList();
        //获取所有授权的应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities = searchAllApp1(shelvesSearchParamDto,ids);
        //获取常规应用分类下所有应用id
        Set<Long> routines=getCateAppIdList(shelvesSearchParamDto,TerminalAppCategoryType.regional);
        //获取自己添加的我的应用
        //cateids.addAll(getOwnerAppIdList(shelvesSearchParamDto));
        //在全部应用中筛选出常规应用
        applicationConfigEntities=applicationConfigEntities.stream().filter(app -> (routines.contains(app.getId()))).collect(Collectors.toList());
        //格式化结果返回
        infos.addAll(getAppInfos(applicationConfigEntities,shelvesSearchParamDto));
        return infos;
    }
    /**
     *查询常规应用
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<AppInfo> searchRoutineApp(AppSearchParamDto shelvesSearchParamDto,Set<Long> ids){
        List<AppInfo> infos = Lists.newArrayList();
        //获取所有授权的应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities = searchAllApp1(shelvesSearchParamDto,ids);
        //获取常规应用分类下所有应用id
        Set<Long> routines=getCateAppIdList(shelvesSearchParamDto,TerminalAppCategoryType.routine);
        //获取自己添加的我的应用
        //cateids.addAll(getOwnerAppIdList(shelvesSearchParamDto));
        //在全部应用中筛选出常规应用
        applicationConfigEntities=applicationConfigEntities.stream().filter(app -> (routines.contains(app.getId()))).collect(Collectors.toList());
        //格式化结果返回
        infos.addAll(getAppInfos(applicationConfigEntities,shelvesSearchParamDto));
        return infos;
    }
    /**
     *查询地域应用
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public Map<String,Object> getRegionalApp(AppSearchParamDto shelvesSearchParamDto){
        //获取所有授权信息
        Set<Long> shlvesIds = getShvleIds(shelvesSearchParamDto);
        Map data1 = new HashMap();
        //查询本地应用下分类
        List<TerminalConfigCategoryEntity> entities = searchAllCategory(shelvesSearchParamDto,TerminalAppCategoryType.regional).stream()
                .filter(terminalConfigCategoryEntity -> shelvesSearchParamDto.getCityName().startsWith(terminalConfigCategoryEntity.getArea()) && terminalConfigCategoryEntity.getStatus()==true)
                .collect(Collectors.toList());
        //获取授权的应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities = searchAllApp1(shelvesSearchParamDto,shlvesIds);
        //得到所有有权限应用
        //List<AppInfo> data2 = new ArrayList<>();
        List<AppBasicType> data2 = new ArrayList<>();
        //应用计数
        int num=0;
        //格式化返回结果
        for (TerminalConfigCategoryEntity entity : entities) {
            List<AppInfo> appInfos =getAppInfos(getCateAppsByCode(applicationConfigEntities,shelvesSearchParamDto.getTerminalId(),entity.getCode()),shelvesSearchParamDto);
            num+=appInfos.size();
           // data2.addAll(appInfos);
            data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + appInfos.size() + ")", "dataability://" + shelvesSearchParamDto.getPackageName() + contanst.getMyAppLink(), entity.getStatus(), appInfos));
        }
       /* if(num != 0){
            data1.put("name","本地应用("+num+")");
        }else{
            data1.put("name","本地应用(0)");
        }*/
        //data1.put("data",data2);
        return UtilDTO.toDTO(data1,null,null,null);
    }


    /**
     *查询我的应用编辑全部
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public Map<String,Object> getMyAppAllEdit(AppSearchParamDto shelvesSearchParamDto){
        //获取所有授权范围
        Set<Long> shlvesIds = getShvleIds(shelvesSearchParamDto);
        AppList data1 = new AppList();
        //获取所有授权的不含本地应用的信息
        List<AppInfo> allApps = searchAllAppNoregion(shelvesSearchParamDto,shlvesIds);
        //获取全部应用分类
        List<TerminalConfigCategoryEntity> entities = searchAllCategory(shelvesSearchParamDto,TerminalAppCategoryType.all);
        //查询我的应用id
        List<Long> myapps = getOwnerAppIdList(shelvesSearchParamDto);
        //查询固定应用的id
        Set<Long> gudings = getCateAppIdList(shelvesSearchParamDto,TerminalAppCategoryType.me);
        List<AppBasicType> data2 = new ArrayList<>();
        if(allApps.size()==0){
            return null;
        }
        //处理展示逻辑
        for (AppInfo appInfo : allApps) {
            if(myapps.contains(Long.valueOf(appInfo.getId()))){
                appInfo.setEnable(true);
            }else if(gudings.contains(Long.valueOf(appInfo.getId()))){
                appInfo.setEnable(true);
                appInfo.setId("-1");
            } else{
                appInfo.setEnable(false);
            }
        }
        //拼装返回结果
        if (!UtilCollection.sizeIsEmpty(entities)) {
            TerminalConfigCategoryEntity entity = entities.get(0);
            data2.add(new AppBasicType(entity.getId().toString(),entity.getLogoUrl(),entity.getName()+"("+allApps.size()+")","",entity.getStatus(),allApps));
        }
        data1.setData(data2);
        return UtilDTO.toDTO(data1,null,null,null);
    }

    /**
     *查询地域单分类应用 V2
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public Map<String,Object> getSingleRegionalApp(AppSearchParamDto shelvesSearchParamDto){
        Map<String,Object> map = Maps.newHashMap();
        //获取所有用户授权的应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(shelvesSearchParamDto.getTerminalId());
        //得到所有有权限应用
        List<AppInfo> appInfos =getAppInfos(getCateAppsByCode(applicationConfigEntities,shelvesSearchParamDto.getTerminalId(),shelvesSearchParamDto.getCategoryCode()),shelvesSearchParamDto);
        map.put("data",UtilDTO.toDTO(appInfos,null,null,null));
        return map;
    }

    /**
     *查询我的应用编辑地域
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public Map<String,Object> getMyAppRegionalEdit(AppSearchParamDto shelvesSearchParamDto){
        //获取所有授权的应用id
        Set<Long> shlvesIds = getShvleIds(shelvesSearchParamDto);
        Map<String,Object> data1 = Maps.newHashMap();
        //获取自己添加的我的应用
        List<Long> myapps = getOwnerAppIdList(shelvesSearchParamDto);
        //获取分类里的固定应用id
        Set<Long> gudings = getCateAppIdList(shelvesSearchParamDto,TerminalAppCategoryType.me);
        //获取所有的地域分类
        List<TerminalConfigCategoryEntity> entities = searchAllCategory(shelvesSearchParamDto,TerminalAppCategoryType.regional).stream()
                .filter(terminalConfigCategoryEntity -> shelvesSearchParamDto.getCityName().startsWith(terminalConfigCategoryEntity.getArea()))
                .collect(Collectors.toList());
        //获取所有有权限的应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities = searchAllApp1(shelvesSearchParamDto,shlvesIds);
        List<AppBasicType> data2 = new ArrayList<>();
        int num=0;
        //格式化返回结果
        for (TerminalConfigCategoryEntity entity : entities) {
            List<AppInfo> appInfos =getAppInfos(getCateAppsByCode(applicationConfigEntities,shelvesSearchParamDto.getTerminalId(),entity.getCode()),shelvesSearchParamDto);
            num+=appInfos.size();
            for (AppInfo appInfo : appInfos) {
                if(myapps.contains(Long.valueOf(appInfo.getId()))){
                    appInfo.setEnable(true);
                }else if(gudings.contains(Long.valueOf(appInfo.getId()))){
                    appInfo.setEnable(true);
                    appInfo.setId("-1");
                } else{
                    appInfo.setEnable(false);
                }
            }
            data2.add(new AppBasicType(entity.getId().toString(),entity.getLogoUrl(),entity.getName(),"dataability://" + shelvesSearchParamDto.getPackageName() +  contanst.getAllAppLink(),entity.getStatus(),appInfos));
        }
        if (num==0){
            return null;
        }

        data1.put("data",data2);
        data1.put("name","本地应用("+num+")");

        return UtilDTO.toDTO(data1,null,null,null);
    }

    /**
     *查询地域应用分类
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public Map<String,Object> searchDtFirstAppList(AppSearchParamDto shelvesSearchParamDto){
        //获取首页配置显示多少个
        int num = Integer.valueOf( shelvesSearchParamDto.getShowNum() == null ?"12": shelvesSearchParamDto.getShowNum()) ;
        //获取所有应用中不包含本地应用的部分
        List<AppInfo> allApps = searchAllAppNoShelvesNoRegion(shelvesSearchParamDto);
        List<AppInfo> newAllApps = Lists.newArrayList();
        for (int i = 0; i < allApps.size(); i++) {
            newAllApps.add(allApps.get(i));
            if(i==(num-2)){
                break;
            }
        }
        if(allApps.size()>= num){
            // 更多信息 超过应用显示个数后展示更多
            Map map = new HashMap();
            map.put("terminalId",shelvesSearchParamDto.getTerminalId());
            map.put("appName","更多");
            List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.queryAll(map);
            if(!applicationConfigEntities.isEmpty()){
                TerminalApplicationConfigEntity terminalApplicationConfigEntity = applicationConfigEntities.get(0);
                AppInfo appInfo = new AppInfo("-1",terminalApplicationConfigEntity.getAppIcon(),"更多","dataability://"+ shelvesSearchParamDto.getPackageName() +".hs.act.slbapp.FenleiAct2{act}?condition=login&query1={s}1&query2={s}更多&query3={s}5",true);
                newAllApps.add(appInfo);
            }
        }

        Map<String,Object> data = Maps.newHashMap();
        if(!UtilCollection.sizeIsEmpty(newAllApps)){
            List<Map<String,Object>> result = UtilDTO.toDTO(newAllApps,null,Lists.newArrayList("createUserId","lastUpdateUserId","del","createDatetime","lastUpdateDatetime"),null);
            data.put("data",result);
        }
        return data;
    }

    /**
     *查询全部指定分类应用实体
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<TerminalConfigCategoryEntity> searchAllCategoryApp(AppSearchParamDto shelvesSearchParamDto,Set<Long> shlvesids, TerminalAppCategoryType... terminalAppCategoryTypes){

        Long terminalId = shelvesSearchParamDto.getTerminalId();
        List<TerminalApplicationConfigEntity> applicationConfigEntities = searchAllApp1(shelvesSearchParamDto,shlvesids);
        //查询所有分类
        List<TerminalConfigCategoryEntity> terminalConfigCategoryEntities = searchAllCategory(shelvesSearchParamDto,terminalAppCategoryTypes);
        for (TerminalConfigCategoryEntity terminalConfigCategoryEntity : terminalConfigCategoryEntities) {
            terminalConfigCategoryEntity.setApps(getAppInfos(getCateAppsByCode(applicationConfigEntities,terminalId,terminalConfigCategoryEntity.getCode()),shelvesSearchParamDto));
        }
        return terminalConfigCategoryEntities;
    }

    /**
     *查询全部指定分类应用实体无需授权
     *
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public List<TerminalConfigCategoryEntity> searchAllCategoryAppNoShelves(AppSearchParamDto shelvesSearchParamDto, TerminalAppCategoryType... terminalAppCategoryTypes){

        Long terminalId = shelvesSearchParamDto.getTerminalId();
        List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(terminalId);
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
        //获取分类
        List<TerminalConfigCategoryEntity> terminalConfigCategoryEntities = Lists.newArrayList();
        try {
             terminalConfigCategoryEntities = terminalCategoryCacheDt.get(shelvesSearchParamDto);
             terminalConfigCategoryEntities=terminalConfigCategoryEntities.stream().filter(map -> Arrays.asList(terminalAppCategoryTypes).contains(map.getType())).collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
        }
        //获取指定类别的分类
        return terminalConfigCategoryEntities;
    }

    /**
     *终端应用分类配置缓存
     *
     * @author liuke
     * @date 2021/10/15 14:56
     * @return
     */
    public LoadingCache<AppSearchParamDto,List<TerminalConfigCategoryEntity>> terminalCategoryCacheDt = CacheUtil.getInstance().build(
            new CacheLoader<AppSearchParamDto,List<TerminalConfigCategoryEntity>>() {
                @Override
                public List<TerminalConfigCategoryEntity> load(AppSearchParamDto shelvesSearchParamDto) throws Exception {
                    /*Map<String,Object> searchParam = Maps.newHashMap();
                    searchParam.put("terminalId",shelvesSearchParamDto.getTerminalId());
                    searchParam.put("status",true);
                    if(StringUtils.isNotBlank(shelvesSearchParamDto.getCategoryType()) && null != shelvesSearchParamDto.getCityName() && !"".equals(shelvesSearchParamDto.getCityName())){
                        searchParam.put("area",shelvesSearchParamDto.getCityName());
                    }
                    return terminalConfigCategoryService.queryAll(searchParam,new String[]{"num"});*/
                    List listAll = new ArrayList();
                    Map<String,Object> searchParam = Maps.newHashMap();
                    //我的应用
                    List listMe = new ArrayList();
                    searchParam = Maps.newHashMap();
                    searchParam.put("terminalId",shelvesSearchParamDto.getTerminalId());
                    searchParam.put("status",true);
                    searchParam.put("type",TerminalAppCategoryType.me);
                    listMe=  terminalConfigCategoryService.queryAll(searchParam,new String[]{"num"});
                    if(!listMe.isEmpty()){
                        listAll.addAll(listMe);
                    }
                    //全部应用
                    List listAllapp = new ArrayList();
                    searchParam = Maps.newHashMap();
                    searchParam.put("terminalId",shelvesSearchParamDto.getTerminalId());
                    searchParam.put("status",true);
                    searchParam.put("type",TerminalAppCategoryType.all);
                    listAllapp = terminalConfigCategoryService.queryAll(searchParam,new String[]{"num"});
                    if(!listAllapp.isEmpty()){
                        listAll.addAll(listAllapp);
                    }
                    //本地
                    List listRegion = new ArrayList();
                    searchParam = Maps.newHashMap();
                    searchParam.put("terminalId",shelvesSearchParamDto.getTerminalId());
                    searchParam.put("status",true);
                    if(StringUtils.isNotBlank(shelvesSearchParamDto.getNavigationBtmId())){
                        searchParam.put("navigationBtmId",shelvesSearchParamDto.getNavigationBtmId());
                    }
                    if(null != shelvesSearchParamDto.getCityName() && !"".equals(shelvesSearchParamDto.getCityName())){
                        if(shelvesSearchParamDto.getCityName().contains("市")){
                            searchParam.put("area",shelvesSearchParamDto.getCityName().split("市")[0]);
                        }else{
                            searchParam.put("area",shelvesSearchParamDto.getCityName());
                        }

                        listRegion = terminalConfigCategoryService. queryAll(searchParam,new String[]{"num"});
                        if(!listRegion.isEmpty()){
                            listAll.addAll(listRegion);
                        }
                    }else{
                        listRegion =  terminalConfigCategoryService.queryAll(searchParam,new String[]{"num"});
                        if(!listRegion.isEmpty()){
                            listAll.addAll(listRegion);
                        }
                    }
                    //常规应用
                    List listRoutine = new ArrayList();
                    searchParam = Maps.newHashMap();
                    searchParam.put("terminalId",shelvesSearchParamDto.getTerminalId());
                    searchParam.put("status",true);
                    searchParam.put("type",TerminalAppCategoryType.routine);
                    if(StringUtils.isNotBlank(shelvesSearchParamDto.getNavigationBtmId())){
                        searchParam.put("navigationBtmId",shelvesSearchParamDto.getNavigationBtmId());
                    }
                    listRoutine =  terminalConfigCategoryService.queryAll(searchParam,new String[]{"num"});
                    if(!listRoutine.isEmpty()){
                        listAll.addAll(listRoutine);
                    }
                    return listAll;
                }
            }
    );
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
        //获取分类下所有的应用id
        Set<Long> ids = getCacheAppByTerminal(terminalId,code);
        List<TerminalApplicationConfigEntity> applications = UtilBean.copyBeans(applicationConfigEntities,TerminalApplicationConfigEntity.class);
        //筛选出分类应用下的详细信息
        applications=applications.stream().filter(a -> ids.contains(a.getId())).collect(Collectors.toList());

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

        if(TerminalQueryType.all.equals(terminalBasicEntity.getQueryAppType())){

            // 查询终端绑定的全部应用 2021-12-14 加
            List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(shelvesSearchParamDto.getTerminalId());
            ids.addAll( applicationConfigEntities.stream().map(map -> map.getId()).collect(Collectors.toSet()));

        }else{

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
     *应用版本信息缓存
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
     * 拼装hios协议
     * @param terminalApplicationConfigEntity
     * @author liuke
     * @date 2021/10/18 10:05
     * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
     */
    public String getPackageAndConfig(TerminalApplicationConfigEntity terminalApplicationConfigEntity){
        try {
            ApplicationBasicEntity applicationBasicEntity = applicationBasicService.getAppById(terminalApplicationConfigEntity.getAppId());

            switch (terminalApplicationConfigEntity.getAppType()){
                case ios:
                    return  terminalApplicationConfigEntity.getStartName();
                case android:
                    return "dataability://" +applicationBasicEntity.getPackageName()+"."+terminalApplicationConfigEntity.getStartName();
                case h5:
                    ApplicationVersionEntity applicationBaseConfigEntities = appConfigCache.get(terminalApplicationConfigEntity.getAppVersionId());
                    if(null == applicationBaseConfigEntities ){
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
     *应用格式化应用返回类
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
        //获取指定分类
        Set<String> codes =terminalConfigCategoryEntities.stream().filter(map -> Arrays.asList(types).contains(map.getType())).map( map -> map.getCode()).collect(Collectors.toSet());
        Set<Long> ids = Sets.newHashSet();
        for (String code : codes) {
            ids.addAll(getCacheAppByTerminal(appSearchParamDto.getTerminalId(),code));
        }
        return ids;
    }
/**********************************************************缓存区******************************************************************/

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
                    Map<String,Object> searchParam = Maps.newHashMap();
                    searchParam.put("terminalId",key.get("terminalId"));
                    searchParam.put("categoryCode",key.get("code"));
                    return terminalCategoryAppService.queryAll(searchParam).stream().map(ca -> ca.getAppConfigId()).collect(Collectors.toSet());
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
     *不包含本地应用的终端应用缓存
     *
     * @author liuke
     * @date 2021/10/15 14:56
     * @return
     */
    public LoadingCache<AppSearchParamDto,List<TerminalApplicationConfigEntity>> terminalCacheAppNoregion = CacheUtil.getInstance().build(
            new CacheLoader<AppSearchParamDto,List<TerminalApplicationConfigEntity>>() {
                @Override
                public List<TerminalApplicationConfigEntity> load(AppSearchParamDto shelvesSearchParamDto) throws Exception {
                    Set<Long> ids = Sets.newHashSet();
                    List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(shelvesSearchParamDto.getTerminalId());
                    List<TerminalConfigCategoryEntity> terminalConfigCategoryEntities = terminalConfigCategoryService.getCacheAppByTerminal(shelvesSearchParamDto);
                    terminalConfigCategoryEntities=terminalConfigCategoryEntities.stream().filter(map -> TerminalAppCategoryType.regional.equals(map.getType())).collect(Collectors.toList());
                    for (TerminalConfigCategoryEntity terminalConfigCategoryEntity : terminalConfigCategoryEntities) {
                         ids.addAll(getCacheAppByTerminal(shelvesSearchParamDto.getTerminalId(),terminalConfigCategoryEntity.getCode())) ;
                    }
                    if(ids.size()>0){
                        return applicationConfigEntities.stream().filter(map -> !ids.contains(map.getId())).sorted(Comparator.comparing(TerminalApplicationConfigEntity::getNum)).collect(Collectors.toList());
                    }else{
                        return applicationConfigEntities;
                    }
                }
            }
    );

    /**
     * 查询本地和常规应用
     * 20220401 潘潇潇、韩康和 需求增加常规应用
     * @author 高健
     * @date 2021/10/15 14:56
     * @return
     */
    public LoadingCache<AppSearchParamDto,List<TerminalApplicationConfigEntity>> terminalCacheAppNoregionAndRoutine = CacheUtil.getInstance().build(
            new CacheLoader<AppSearchParamDto,List<TerminalApplicationConfigEntity>>() {
                @Override
                public List<TerminalApplicationConfigEntity> load(AppSearchParamDto shelvesSearchParamDto) throws Exception {
                    Set<Long> ids = Sets.newHashSet();
                    List<TerminalApplicationConfigEntity> applicationConfigEntities = terminalApplicationConfigService.getCacheAppByTerminal(shelvesSearchParamDto.getTerminalId());
                    List<TerminalConfigCategoryEntity> terminalConfigCategoryEntitiesAll = terminalConfigCategoryService.getCacheAppByTerminal(shelvesSearchParamDto);
                    List<TerminalConfigCategoryEntity> noExsistterminalConfigCategoryEntities = new ArrayList<>();
                    for(TerminalConfigCategoryEntity TerminalConfigCategoryEntity : terminalConfigCategoryEntitiesAll){
                         if(TerminalConfigCategoryEntity.getType().equals(TerminalAppCategoryType.regional) || TerminalConfigCategoryEntity.getType().equals(TerminalAppCategoryType.routine)){
                             noExsistterminalConfigCategoryEntities.add(TerminalConfigCategoryEntity);
                         }
                    }
                    for (TerminalConfigCategoryEntity terminalConfigCategoryEntity : noExsistterminalConfigCategoryEntities) {
                        ids.addAll(getCacheAppByTerminal(shelvesSearchParamDto.getTerminalId(),terminalConfigCategoryEntity.getCode())) ;
                    }

                    if(ids.size()>0){
                        return applicationConfigEntities.stream().filter(map -> !ids.contains(map.getId())).collect(Collectors.toList());
                    }else{
                        return applicationConfigEntities;
                    }
                }
            }
    );


    /**
     *
     * 查询本地和常规应用
     * 20220401 潘潇潇、韩康和 需求增加常规应用
     * @param shelvesSearchParamDto
     * @author 高健
     * @date 2021/10/18 10:05
     * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
     */
    public List<TerminalApplicationConfigEntity> getCacheAppNoregionAndRoutine(AppSearchParamDto shelvesSearchParamDto){
        List<TerminalApplicationConfigEntity> ids = Lists.newArrayList();
        try {
            ids = terminalCacheAppNoregionAndRoutine.get(shelvesSearchParamDto);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ids;
    }

    /**
     *
     * 获取不含本地应用的终端应用
     * @param shelvesSearchParamDto
     * @author liuke
     * @date 2021/10/18 10:05
     * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
     */
    public List<TerminalApplicationConfigEntity> getCacheAppNoregion(AppSearchParamDto shelvesSearchParamDto){
        List<TerminalApplicationConfigEntity> ids = Lists.newArrayList();
        try {
            ids = terminalCacheAppNoregion.get(shelvesSearchParamDto);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ids;
    }

    /**
     *查询指定类型的应用
     *
     * @param shelvesSearchParamDto
     * @author 高健
     * @date 2021/10/25 18:59
     * @return java.util.List<com.fosung.workbench.AppBean.AppInfo>
     */
    public Map<String,Object> queryCategoryApp(AppSearchParamDto shelvesSearchParamDto){

        //获取所有授权信息
        Set<Long> shlvesIds = getShvleIds(shelvesSearchParamDto);
        Map<String,Object> data1 = Maps.newHashMap();

        //查询类型下子分类
        List<TerminalConfigCategoryEntity> entities;
        if(TerminalAppCategoryType.regional.equals(shelvesSearchParamDto.getCategoryTypeEnum())){
            entities = searchAllCategory(shelvesSearchParamDto,shelvesSearchParamDto.getCategoryTypeEnum()).stream()
                    .filter(terminalConfigCategoryEntity -> shelvesSearchParamDto.getCityName().startsWith(terminalConfigCategoryEntity.getArea()) && terminalConfigCategoryEntity.getStatus()==true)
                    .collect(Collectors.toList());
        }else{
            shelvesSearchParamDto.setCityName(null);
            entities = searchAllCategory(shelvesSearchParamDto,shelvesSearchParamDto.getCategoryTypeEnum()).stream()
                    .filter(terminalConfigCategoryEntity -> terminalConfigCategoryEntity.getStatus()==true)
                    .collect(Collectors.toList());
        }


        //获取授权的应用
        List<TerminalApplicationConfigEntity> applicationConfigEntities = searchAllApp1(shelvesSearchParamDto,shlvesIds);

        //得到所有有权限应用
        //List<AppInfo> data2 = new ArrayList<>();
        List<AppBasicType> data2 = new ArrayList<>();
        //应用计数
        int num=0;
        //格式化返回结果
        for (TerminalConfigCategoryEntity entity : entities) {
            List<AppInfo> appInfos =getAppInfos(getCateAppsByCode(applicationConfigEntities,shelvesSearchParamDto.getTerminalId(),entity.getCode()),shelvesSearchParamDto);
            num+=appInfos.size();
            // data2.addAll(appInfos);
            data2.add(new AppBasicType(entity.getId().toString(), entity.getLogoUrl(), entity.getName() + "(" + appInfos.size() + ")", "dataability://" + shelvesSearchParamDto.getPackageName() + contanst.getMyAppLink(), entity.getStatus(), appInfos));
        }

        if(num != 0){
            data1.put("data",data2);
            if(entities.isEmpty() && TerminalAppCategoryType.regional.equals(shelvesSearchParamDto.getCategoryTypeEnum())){
                data1.put("name","本地应用("+num+")");
            }
        }else{
            data1.put("data",data2);
            if(entities.isEmpty() && TerminalAppCategoryType.regional.equals(shelvesSearchParamDto.getCategoryTypeEnum())){
                data1.put("name","本地应用(0)");
            }
        }
        return UtilDTO.toDTO(data1,null,null,null);
    }


}
