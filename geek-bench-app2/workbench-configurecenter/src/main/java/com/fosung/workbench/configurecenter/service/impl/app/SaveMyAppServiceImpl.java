package com.fosung.workbench.configurecenter.service.impl.app;

import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilString;
import com.fosung.workbench.configurecenter.service.impl.AbstractWorkBenchConfig;
import com.fosung.workbench.dict.TerminalAppCategoryType;
import com.fosung.workbench.dto.config.AppSearchParamDto;
import com.fosung.workbench.entity.application.ApplicationOwnerEntity;
import com.fosung.workbench.service.application.ApplicationOwnerService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * 移除我的app
 * 
 * @author liuke
 * @date  2021/10/9 16:25
 * @version 
*/
@Service
public class SaveMyAppServiceImpl extends AbstractWorkBenchConfig{


    @Autowired
    private ApplicationOwnerService applicationOwnerService;

    @Autowired
    private AppSearchServiceImpl appSearchService;





    /**
     *移除我的应用
     *
     * @param appSearchParamDto
     * @author liuke
     * @date 2021/10/27 9:17
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String, Object> removeMyApp(AppSearchParamDto appSearchParamDto)  {
        checkMyAppParam(appSearchParamDto);
        List<ApplicationOwnerEntity> applicationOwnerEntities = applicationOwnerService.getMyApp(appSearchParamDto.getUserId(),appSearchParamDto.getTerminalId());
        if (UtilCollection.sizeIsEmpty(applicationOwnerEntities)){
            return Maps.newHashMap();
        }
        Set<Long> ids = applicationOwnerEntities.stream().filter(map -> map.getAppConfigId().equals(appSearchParamDto.getAppId())).map(map -> map.getId()).collect(Collectors.toSet());
        applicationOwnerService.delete(ids);
        applicationOwnerService.deleteMyAppCache(appSearchParamDto.getUserId(),appSearchParamDto.getTerminalId());
        return Maps.newHashMap();
    }

    /**
     *保存我的应用
     *
     * @param appSearchParamDto
     * @author liuke
     * @date 2021/10/27 9:17
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String, Object> saveMyApp(AppSearchParamDto appSearchParamDto) {
       checkMyAppParam(appSearchParamDto);
       //移除原来的我的应用
        List<ApplicationOwnerEntity> applicationOwnerEntities = applicationOwnerService.getMyApp(appSearchParamDto.getUserId(),appSearchParamDto.getTerminalId());
        //查询活动的部分
        Set<Long> myappids = applicationOwnerEntities.stream().map(owner -> owner.getId()).collect(Collectors.toSet());
        applicationOwnerService.delete(myappids);
        applicationOwnerService.deleteMyAppCache(appSearchParamDto.getUserId(),appSearchParamDto.getTerminalId());
        //添加
        if(UtilString.isNotBlank(appSearchParamDto.getAppIds())) {
            List<Long> ids = Lists.newArrayList(appSearchParamDto.getAppIds().split(",")).stream().map(a -> Long.valueOf(a)).collect(Collectors.toList());
            //ids = ids.stream().filter(id -> !(appSearchService.getCateAppIdList(appSearchParamDto, TerminalAppCategoryType.me).contains(id))).collect(Collectors.toList());
            List<ApplicationOwnerEntity> list = Lists.newArrayList();
            ids.stream().forEach(id -> {
                list.add(new ApplicationOwnerEntity(appSearchParamDto.getUserId(), id, appSearchParamDto.getTerminalId()));
            });
            applicationOwnerService.saveBatch(list);
        }
       return Maps.newHashMap();
    }

}
