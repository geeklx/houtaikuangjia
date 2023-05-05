package com.fosung.workbench.service.application;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.dao.config.mybatis.page.MybatisPage;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.common.GlobalVariableKey;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.dao.common.CitiesDao;
import com.fosung.workbench.dao.project.ProjectBasicDao;
import com.fosung.workbench.dao.terminal.TerminalApplicationConfigDao;
import com.fosung.workbench.dict.ConfigType;
import com.fosung.workbench.dict.DataSourceType;
import com.fosung.workbench.dto.application.ApplicationQueryDto;
import com.fosung.workbench.entity.config.TerminalConfigManageEntity;
import com.fosung.workbench.service.config.TerminalConfigManageService;
import com.fosung.workbench.util.CacheUtil;
import com.fosung.workbench.util.UnifiedRequestUtils;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mzlion.easyokhttp.HttpClient;
import com.mzlion.easyokhttp.request.TextBodyRequest;
import com.mzlion.easyokhttp.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 终端应用查询服务实现类
 * @Author gaojian
 * @Date 2021/10/23 21:11
 * @Version V1.0
 */
@Service
@Slf4j
public class ApplicationQueryServiceImpl implements ApplicationQueryService{

    /**
     * 描述:  注入终端应用配置服务数据持久化层
     * @createDate: 2021/10/23 23:02
     * @author: gaojian
     */
    @Autowired(required = false)
    private TerminalApplicationConfigDao terminalApplicationConfigDao;
    
    /**
     * 描述:  注入终端运行配置服务
     * @createDate: 2021/11/2 18:49
     * @author: gaojian
     */
    @Autowired
    private TerminalConfigManageService terminalConfigManageService;

    /**
     * 描述:  注入项目基本信息持久化层
     * @createDate: 2021/10/30 15:53
     * @author: gaojian
     */
    @Autowired(required = false)
    private ProjectBasicDao projectBasicDao;

    /**
     * 描述:  注入城市编码持久化层
     * @createDate: 2021/10/30 15:53
     * @author: gaojian
     */
    @Autowired(required = false)
    private CitiesDao citiesDao;

    /**
     * 描述:  根目录城市编码
     * @createDate: 2021/11/1 9:11
     * @author: gaojian
     */
    @Value(value = "${app.customParams.rootCityCode:10112__01}")
    private String rootCityCode;

    @Value(value = "${app.projectCode:fs-user-project-1641547050881}")
    private String projectCode;

    /**
     * 描述:  获取组织url
     * @createDate: 2021/11/1 9:11
     * @author: gaojian
     */
    @Value(value = "${app.customParams.organUrl:http://10.7.211.201:5001%s/api/queryOrgTree}")
    private String organUrl;

    /**
     * 描述:  获取人员url
     * @createDate: 2021/11/1 9:11
     * @author: gaojian
     */
    @Value(value = "${app.customParams.userUrl:http://10.7.211.201:5001%s/api/contracts}")
    private String userUrl;

    /**
     * 描述:  组织类型
     * @createDate: 2022/1/14 14:15
     * @author: gaojian
     */
    @Value(value = "${app.orgType:administration}")
    private String orgType;

    /**
     * 描述:  网关AK
     * @createDate: 2021/11/1 9:11
     * @author: gaojian
     */
    @Value(value = "${app.rpc.accessKey:a385487c5cfe0559}")
    private String accessKey;

    /**
     * 描述:  网关SK
     * @createDate: 2021/11/1 9:11
     * @author: gaojian
     */
    @Value(value = "${app.rpc.accessSecret:b14dde7d6b0d65bd}")
    private String accessSecret;

    /**
     * 描述:  网关版本
     * @createDate: 2021/11/1 9:11
     * @author: gaojian
     */
    @Value(value = "${app.rpc.signVersion:V1}")
    private String signVersion;

    public LoadingCache<String, List<Map<String,Object>>> queryRootCitiesCache = CacheUtil.getInstance().build(
            new CacheLoader<String, List<Map<String,Object>>>() {
                @Override
                public List<Map<String,Object>> load(String key){
                    log.debug("[loadingCache queryRootCities---{}", key);
                    return citiesDao.queryRootCities(key);
                }
            });

    public LoadingCache<String, List<Map<String,Object>>> queryCitiesCache = CacheUtil.getInstance().build(
            new CacheLoader<String, List<Map<String,Object>>>() {
                @Override
                public List<Map<String,Object>> load(String cityCode){
                    log.debug("[loadingCache queryRootCities---{}", cityCode);
                    StringBuilder cityCodeBuilder = new StringBuilder(cityCode);
                    cityCodeBuilder.replace(cityCode.length()-2,cityCode.length(),"__");
                    return citiesDao.queryCities(cityCodeBuilder.toString(),cityCode);
                }
            });

    /**
     * 描述:  查询应用信息
     *
     * @param applicationQueryDto
     * @createDate: 2021/10/23 21:47
     * @author: gaojian
     * @modify:
     * @return: org.springframework.data.domain.Page<java.util.Map < java.lang.String, java.lang.Object>>
     */
    @Override
    public Page<Map<String, Object>> queryApplication(ApplicationQueryDto applicationQueryDto) {

        // 1. 查询项目信息
        List<Map<String,Object>> list = projectBasicDao.queryAllProjectOption();
        Map<String,String> projectMap = new HashMap<>(32);
        list.forEach(stringObjectMap -> {
            projectMap.put(stringObjectMap.get(GlobalVariableKey.DICT_VALUE).toString(),stringObjectMap.get(GlobalVariableKey.DICT_LABEL).toString());
        });

        // 2. 执行分页查询
        Pageable pageable = MybatisPageRequest.of(applicationQueryDto.getPageNum(),applicationQueryDto.getPageSize());
        MybatisPage<Map<String,Object>> result = terminalApplicationConfigDao.queryApplication(applicationQueryDto,pageable);
        result.forEach( stringObjectMap -> {
            Long projectId = Long.valueOf(stringObjectMap.get(GlobalVariableKey.PROJECT_ID).toString());
            stringObjectMap.put(GlobalVariableKey.PROJECT_NAME,projectMap.get(projectId.toString()));
            if(stringObjectMap.get(GlobalVariableKey.DATA_SOURCE_DICT) != null){
                stringObjectMap.put(GlobalVariableKey.DATA_SOURCE_DICT_NAME, DataSourceType.valueOf(stringObjectMap.get(GlobalVariableKey.DATA_SOURCE_DICT).toString()).getRemark());
            }else{
                stringObjectMap.put(GlobalVariableKey.DATA_SOURCE_DICT_NAME, DataSourceType.workbench.getRemark());
            }
        });
        return result;
    }

    /**
     * 描述:  查询地域信息
     *
     * @param applicationQueryDto
     * @createDate: 2021/10/30 15:52
     * @author: gaojian
     * @modify:
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    @Override
    public List<Map<String, Object>> queryRegional(ApplicationQueryDto applicationQueryDto) {

        List<Map<String, Object>> list = new ArrayList<>();
        try{
            // 1. 获取跟目录城市
            if(StringUtils.isBlank(applicationQueryDto.getCityCode())){

                list = queryRootCitiesCache.get(rootCityCode);
                if(list == null){
                    list = citiesDao.queryRootCities(rootCityCode);
                    queryRootCitiesCache.put(rootCityCode,list);
                }
                return list;
            }

            // 2. 替换指定位置字符串
            String cityCode = applicationQueryDto.getCityCode();
            list = queryCitiesCache.get(cityCode);
            if(list == null){
                StringBuilder cityCodeBuilder = new StringBuilder(cityCode);
                cityCodeBuilder.replace(cityCode.length()-2,cityCode.length(),"__");
                list = citiesDao.queryCities(cityCodeBuilder.toString(),cityCode);
                queryCitiesCache.put(cityCode,list);
            }
            return list;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return list;
    }

    /**
     * 描述:  查询组织
     *
     * @param jsonObject
     * @createDate: 2021/11/2 18:40
     * @author: gaojian
     * @modify:
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @Override
    public ResponseParam queryOrgan(JSONObject jsonObject) {

        // 1. 查询终端配置的资源服务平台信息
        Map<String,Object> searchParams = new HashMap<>(8);
        searchParams.put("terminalId",jsonObject.getLongValue(GlobalVariableKey.TERMINAL_ID));
        searchParams.put("configType", ConfigType.resource);
        List<TerminalConfigManageEntity> list = terminalConfigManageService.queryAll(searchParams);

        // 2. 如果资源配置类型没有配置认证平台则报错
        if( list == null || list.size() < 1 || StringUtils.isBlank(list.get(0).getConfigPlatform())){
            throw new AppException(MessageContent.TERMINAL_RESOURCE_PLATFORM_IS_NULL);
        }

        // 3. 获取配置的认证平台信息
        String gwapiUrl = String.format(organUrl,list.get(0).getConfigPlatform());
        Map<String,Object> requestParams = new HashMap<>(8);
        if(jsonObject.containsKey(GlobalVariableKey.PARENT_ID)){
            requestParams.put(GlobalVariableKey.ORGAN_ID,jsonObject.getString(GlobalVariableKey.PARENT_ID));
        }
        return callGwapi(gwapiUrl,requestParams);
    }

    /**
     * 描述:  查询用户
     *
     * @param terminalId
     * @createDate: 2021/11/2 18:40
     * @author: gaojian
     * @modify:
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    @Override
    public ResponseParam queryUser(Long terminalId , Map<String,Object> requestParams) {

        // 1. 查询终端配置的通讯录服务平台信息
        Map<String,Object> searchParams = new HashMap<>(8);
        searchParams.put("terminalId",terminalId);
        searchParams.put("configType", ConfigType.resource);
        List<TerminalConfigManageEntity> list = terminalConfigManageService.queryAll(searchParams);

        // 2. 如果资源配置类型没有配置认证平台则报错
        if( list == null || list.size() < 1 || StringUtils.isBlank(list.get(0).getConfigPlatform())){
            throw new AppException(MessageContent.TERMINAL_CONTACTS_PLATFORM_IS_NULL);
        }

        // 3. 获取配置的认证平台信息
        String gwapiUrl = String.format(userUrl,list.get(0).getConfigPlatform());
        if( requestParams == null){
            requestParams = new HashMap<>(8);
        }
        if(requestParams.containsKey(GlobalVariableKey.PARENT_ID)){
            requestParams.put(GlobalVariableKey.ORGAN_ID,requestParams.get(GlobalVariableKey.PARENT_ID));
        }
        return callGwapi(gwapiUrl,requestParams);
    }

    /**
     * 描述:  调用网关获取结果信息
     * @createDate: 2021/11/2 19:30
     * @author: gaojian
     * @modify:
     * @param url
     * @param requestParams
     * @return: com.fosung.framework.web.http.ResponseParam
     */
    public ResponseParam callGwapi(String url,Map<String,Object> requestParams){

        requestParams.put(GlobalVariableKey.ORG_TYPE,orgType);
        requestParams.put(GlobalVariableKey.PROJECT_CODE,projectCode);
        TextBodyRequest textBodyRequest = HttpClient.textBody(url);
        UnifiedRequestUtils.formatHeaders(accessKey,accessSecret,textBodyRequest);
        textBodyRequest.header("Accept-Encoding","deflate,br");
        HttpResponse httpResponse = textBodyRequest.json(requestParams).execute();
        if (!httpResponse.isSuccess()) {
            StringBuilder msg = new StringBuilder("远程执行:"+httpResponse.getErrorMessage() + ":" + httpResponse.getErrorCode());
            log.error(msg.toString());
            return ResponseParam.success().datalist(new ArrayList<>());
        }
        String content = httpResponse.asString();
        if(StringUtils.isNotBlank(content)){
            ResponseParam responseParam = JSONObject.parseObject(content,ResponseParam.class);
            return responseParam;
        }else {
            return ResponseParam.fail();
        }
    }

}
