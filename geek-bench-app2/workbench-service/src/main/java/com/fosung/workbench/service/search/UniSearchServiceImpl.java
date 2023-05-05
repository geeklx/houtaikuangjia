package com.fosung.workbench.service.search;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.util.UtilMap;
import com.fosung.framework.mq.common.CallBack;
import com.fosung.framework.mq.common.MessageProducer;
import com.fosung.unionsearch.dto.knowledgeLib.KnowledgeAuthDto;
import com.fosung.unionsearch.dto.knowledgeLib.KnowledgeBasicDto;
import com.fosung.unionsearch.dto.knowledgeLib.KnowledgeDetailDto;
import com.fosung.unionsearch.dto.knowledgeLib.KnowledgeDetailExtDto;
import com.fosung.workbench.dict.*;
import com.fosung.workbench.entity.application.ApplicationConfigHtmlEntity;
import com.fosung.workbench.entity.application.ApplicationVersionEntity;
import com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.fosung.workbench.entity.terminal.TerminalApplicationShelvesEntity;
import com.fosung.workbench.entity.terminal.TerminalConfigCommonEntity;
import com.fosung.workbench.entity.terminal.TerminalThirdPartyConfigEntity;
import com.fosung.workbench.service.application.ApplicationConfigHtmlService;
import com.fosung.workbench.service.application.ApplicationVersionService;
import com.fosung.workbench.service.terminal.TerminalApplicationShelvesService;
import com.fosung.workbench.service.terminal.TerminalConfigCommonService;
import com.fosung.workbench.service.terminal.TerminalThirdPartyConfigService;
import com.fosung.workbench.util.CacheUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 统一搜索服务实现类
 * @Author gaojian
 * @Date 2022/3/2 10:27
 * @Version V1.0
 */
@Service
@Slf4j
public class UniSearchServiceImpl implements UniSearchService {

    /**
     * 描述:  消息生产者
     * @createDate: 2021/12/24 9:34
     * @author: gaojian
     */
    @Resource
    private MessageProducer messageProducer;

    /**
     * 描述:  主题
     * @createDate: 2021/12/24 9:43
     * @author: gaojian
     */
    @Value("${app.mq.producer.rocket.topic:knowledge_gather}")
    private String topic;

    /**
     * 描述:  是否启用统一搜索
     * @createDate: 2021/12/29 11:40
     * @author: gaojian
     */
    @Value("${app.union.search.enable:false}")
    private boolean enable = false;

    /**
     * 描述:  知识来源
     * @createDate: 2021/12/24 9:43
     * @author: gaojian
     */
    @Value("${app.union.search.knowledgeSource:3522367790851822463}")
    private String knowledgeSource;

    @Autowired
    private TerminalConfigCommonService terminalConfigCommonService;

    @Autowired
    private ApplicationVersionService applicationVersionService;

    @Autowired
    private ApplicationConfigHtmlService applicationConfigHtmlService;

    @Autowired
    private TerminalApplicationShelvesService terminalApplicationShelvesService;

    @Autowired
    private TerminalThirdPartyConfigService terminalThirdPartyConfigService;

    /**
     * 描述:  同步数据到统一搜索
     *
     * @param terminalApplicationConfigEntity
     * @param searchOperateType
     * @createDate: 2022/3/2 10:30
     * @author: gaojian
     * @modify:
     * @return: void
     */
    @Override
    public void searchSynchronizationData(TerminalApplicationConfigEntity terminalApplicationConfigEntity, SearchOperateType searchOperateType) {

        // 根据终端决定是否启用对接统一搜索
        Long terminalId = terminalApplicationConfigEntity.getTerminalId();
        Map<String,Object> searchParams = new HashMap<>(8);
        searchParams.put("terminalId",terminalId);
        searchParams.put("configType", ConfigType.unisearch);
        List<TerminalThirdPartyConfigEntity> list = terminalThirdPartyConfigService.queryAll(searchParams);
        if(list == null || list.size() < 1 || StatusType.DISABLE.equals(list.get(0).getStatusType())){
            log.info("未启动同步应用信息到统一搜索，直接返回信息！");
            return;
        }

        // 获取知识分类ID
        try {

            Map<String,Object> searchConfigInfo = terminalThirdPartyConfigService.getConfigInfo(list.get(0));
            String categoryId = UtilMap.getString(searchConfigInfo,"knowledgeCategoryCode");
            if(StringUtils.isBlank(categoryId)){
                log.info("未获取终端配置的知识分类ID，不进行数据同步到统一搜索。");
                return;
            }

            // 核心信息
            KnowledgeDetailDto kg = new KnowledgeDetailDto();
            kg.setCategoryId(categoryId);
            kg.setSourceId(knowledgeSource);
            kg.setOperateType(searchOperateType.name());

            // 基本属性
            KnowledgeBasicDto basic = new KnowledgeBasicDto();
            basic.setId(terminalApplicationConfigEntity.getId().toString());
            basic.setTitle(terminalApplicationConfigEntity.getAppName());
            basic.setContent(terminalApplicationConfigEntity.getRemark());
            basic.setReleaseTime(new Date());

            // 扩展属性
            KnowledgeDetailExtDto ext = new KnowledgeDetailExtDto();
            ext.setIcon(terminalApplicationConfigEntity.getAppIcon());
            ext.setSourceApp(DataSourceType.workbench.getRemark());
            setAppUrl(ext,terminalApplicationConfigEntity);

            // 基础属性
            kg.setBasic(basic);

            // 设置权限信息
            setAuth(terminalApplicationConfigEntity,searchOperateType,kg);

            // 扩展属性
            kg.setExt(ext);

            // 发送同步数据
            sendSynchronizationData(kg);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("获取终端配置的知识分类ID缓存信息失败，失败信息{}" + e.getMessage());
            return;
        }
    }

    /**
     * 描述:  设置应用URL
     * @createDate: 2021/12/29 14:01
     * @author: gaojian
     * @modify:
     * @param ext
     * @param terminalApplicationConfigEntity
     * @return: void
     */
    public void setAppUrl(KnowledgeDetailExtDto ext,TerminalApplicationConfigEntity terminalApplicationConfigEntity){

        // 组装扩展url参数信息
        ApplicationVersionEntity versionEntity = null;
        if(terminalApplicationConfigEntity.getAppVersionId() != null ){
            versionEntity = applicationVersionService.getVersionInfo(terminalApplicationConfigEntity.getAppVersionId());
        }
        if( versionEntity != null){
            JSONObject jsonObject = new JSONObject();
            switch (versionEntity.getAppType()){
                case ios:
                    jsonObject.put("ios_phone", terminalApplicationConfigEntity.getStartName() +terminalApplicationConfigEntity.getStartParam());
                    ext.setUrl(jsonObject.toJSONString());
                    break;
                case android:
                    jsonObject.put("android_phone","dataability://"+versionEntity.getPackageName()+"." + terminalApplicationConfigEntity.getStartName() +terminalApplicationConfigEntity.getStartParam());
                    ext.setUrl(jsonObject.toJSONString());
                    break;
                case h5:
                    Map param = new ConcurrentHashMap(6);
                    param.put("appId",terminalApplicationConfigEntity.getAppId());
                    param.put("appVersionId",terminalApplicationConfigEntity.getAppVersionId());
                    ApplicationConfigHtmlEntity htmlEntity = new ApplicationConfigHtmlEntity();
                    List<ApplicationConfigHtmlEntity> htmlList = applicationConfigHtmlService.queryAll(param);
                    if(!htmlList.isEmpty()){
                        htmlEntity = htmlList.get(0);
                    }
                    jsonObject.put("web",htmlEntity.getFrontUrl());
                    ext.setUrl(jsonObject.toJSONString());
                    break;
                default:
                    ext.setUrl("dataability://"+versionEntity.getPackageName()+"." + terminalApplicationConfigEntity.getStartName() +terminalApplicationConfigEntity.getStartParam() );
                    break;
            }
        }

    }

    /**
     * 描述:  设置权限信息
     * @createDate: 2021/12/29 11:38
     * @author: gaojian
     * @modify:
     * @param terminalApplicationConfigEntity
     * @param searchOperateType
     * @param kg
     * @return: void
     */
    public void setAuth(TerminalApplicationConfigEntity terminalApplicationConfigEntity,
                        SearchOperateType searchOperateType,
                        KnowledgeDetailDto kg){

        // 权限属性
        KnowledgeAuthDto knowledgeAuthDto = new KnowledgeAuthDto();

        JSONArray jsonArray = new JSONArray();
        jsonArray.add("*");
        if(!searchOperateType.equals(SearchOperateType.del)){

            // 与灯塔侧讨论如果没有身份授权信息则传 *
            Map<String,Object> searchParams = new HashMap<>(6);
            searchParams.put("terminalId",terminalApplicationConfigEntity.getTerminalId());
            searchParams.put("appConfigId",terminalApplicationConfigEntity.getId());
            searchParams.put("shelvesType", ShelvesType.identity.getValue());
            List<TerminalApplicationShelvesEntity> authIdentity = terminalApplicationShelvesService.queryAll(searchParams);
            JSONArray identityJsonArray = new JSONArray();
            authIdentity.forEach(terminalApplicationShelvesEntity -> {
                if(StringUtils.equals("ptdy",terminalApplicationShelvesEntity.getShelvesRange())){
                    // 普通党员
                    identityJsonArray.add("PARTY_MEMBER");
                }else if(StringUtils.equals("dygb",terminalApplicationShelvesEntity.getShelvesRange())){
                    // 党员干部
                    identityJsonArray.add("CADRE");
                }else if(StringUtils.equals("ptqz",terminalApplicationShelvesEntity.getShelvesRange())){
                    // 群众
                    identityJsonArray.add("PEOPLE");
                }else{
                    identityJsonArray.add(terminalApplicationShelvesEntity.getShelvesRange());
                }

            });

            // 与灯塔侧讨论如果没有身份授权信息则传 *
            if(identityJsonArray.size() < 1){
                knowledgeAuthDto.setAuthIdentity(jsonArray);
            }else{
                knowledgeAuthDto.setAuthIdentity(identityJsonArray);
            }

            // 组织
            searchParams.put("shelvesType", ShelvesType.org.getValue());
            List<TerminalApplicationShelvesEntity> authOrganize = terminalApplicationShelvesService.queryAll(searchParams);
            JSONArray organizeJsonArray = new JSONArray();
            authOrganize.forEach(terminalApplicationShelvesEntity -> {
                organizeJsonArray.add(terminalApplicationShelvesEntity.getShelvesRange());
            });

            // 与灯塔侧讨论如果没有组织授权信息则传 *
            if(organizeJsonArray.size() < 1){
                knowledgeAuthDto.setAuthOrganize(jsonArray);
            }else{
                knowledgeAuthDto.setAuthOrganize(organizeJsonArray);
            }

            // 网区
            knowledgeAuthDto.setAuthNetworkPartition(jsonArray);
            kg.setAuth(knowledgeAuthDto);
        }else{
            knowledgeAuthDto.setAuthIdentity(jsonArray);
            knowledgeAuthDto.setAuthOrganize(jsonArray);
            knowledgeAuthDto.setAuthNetworkPartition(jsonArray);
            kg.setAuth(knowledgeAuthDto);
        }
    }

    /**
     * 描述:  终端配置缓存
     * @createDate: 2021/12/27 13:56
     * @author: gaojian
     * @modify:
     * @param null
     * @return:
     */
    public LoadingCache<TerminalConfigCommonEntity,List<TerminalConfigCommonEntity>> terminalConfigCommonEntityCache = CacheUtil.getInstance().build(
            new CacheLoader<TerminalConfigCommonEntity,List<TerminalConfigCommonEntity>>() {
                @Override
                public List<TerminalConfigCommonEntity> load(TerminalConfigCommonEntity terminalConfigCommonEntity) throws Exception {
                    terminalConfigCommonEntity.setConfigCode("knowledgeCategory");
                    List<TerminalConfigCommonEntity> result = terminalConfigCommonService.getAllCommonConfigurationInfo(terminalConfigCommonEntity);
                    return result;
                }
            }
    );

    /**
     * 描述:  同步数据到统一搜索
     * @createDate: 2021/12/24 9:37
     * @author: gaojian
     * @modify:
     * @param knowledgeDetailDto
     * @return: void
     */
    public void sendSynchronizationData(KnowledgeDetailDto knowledgeDetailDto){

        log.info("KnowledgeDetailDto:" + JSONObject.toJSONString(knowledgeDetailDto));
        messageProducer.sendMsg(topic, knowledgeDetailDto.getSourceId(), knowledgeDetailDto, new CallBack() {

            @Override
            public void onCompletion(Object result, Throwable exception) {
                log.info(knowledgeDetailDto.getOperateType() + "："+JSONObject.toJSONString(result));
            }
        });
    }
}
