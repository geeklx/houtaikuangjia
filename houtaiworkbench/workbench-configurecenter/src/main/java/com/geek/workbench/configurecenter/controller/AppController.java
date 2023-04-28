package com.geek.workbench.configurecenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.util.UtilMap;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.AppBean.ApkBasic;
import com.geek.workbench.AppBean.ApkBasicDto;
import com.geek.workbench.AppBean.FSearchBean;
import com.geek.workbench.configurecenter.service.impl.app.AppSearchV2ServiceImpl;
import com.geek.workbench.dict.TerminalAppCategoryType;
import com.geek.workbench.dto.application.ApplicationConfigHtmlDto;
import com.geek.workbench.dto.config.AppSearchParamDto;
import com.geek.workbench.dto.microcoder.TerminalConfigMenuTopDto;
import com.geek.workbench.dto.terminal.TerminalApplicationConfigDto;
import com.geek.workbench.entity.application.ApplicationBasicEntity;
import com.geek.workbench.entity.application.ApplicationConfigAndroidEntity;
import com.geek.workbench.entity.application.ApplicationConfigHtmlEntity;
import com.geek.workbench.entity.application.ApplicationVersionEntity;
import com.geek.workbench.entity.config.ConfigApiEntity;
import com.geek.workbench.entity.config.TerminalConfigApiEntity;
import com.geek.workbench.entity.terminal.TerminalApplicationConfigEntity;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import com.geek.workbench.service.application.*;
import com.geek.workbench.service.config.ConfigApiService;
import com.geek.workbench.service.config.TerminalConfigApiService;
import com.geek.workbench.service.feign.GetLoginService;
import com.geek.workbench.service.microcoder.TerminalConfigMenuTopService;
import com.geek.workbench.service.microcoder.TerminalConfigNavigationTopService;
import com.geek.workbench.service.terminal.TerminalApplicationConfigService;
import com.geek.workbench.service.terminal.TerminalBasicService;
import com.geek.workbench.service.terminal.TerminalVersionService;
import com.geek.workbench.util.AppHeaderResolution;
import com.geek.workbench.service.application.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author lihuiming
 * @className AppController
 * @description: app接口
 * @date 2021/10/1811:23
 */
@RestController
@RequestMapping(value = AppController.BASE_PATH)
@SuppressWarnings("unchecked")
public class AppController extends AppIBaseController {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    private TerminalBasicService terminalBasicService;
    @Autowired
    private TerminalVersionService terminalVersionService;
    @Autowired
    private ApplicationConfigAndroidService applicationConfigAndroidService;
    @Autowired
    private TerminalConfigApiService terminalConfigApiService;
    @Autowired
    private ConfigApiService configApiService;
    @Autowired
    private ApplicationBasicService applicationBasicService;
    @Autowired
    private ApplicationVersionService applicationVersionService;
    @Autowired
    private TerminalApplicationConfigService terminalApplicationConfigService;
    @Autowired
    private ApplicationConfigHtmlService applicationConfigHtmlService;
    @Autowired
    private ApplicationOwnerService applicationOwnerService;
    @Autowired
    private TerminalConfigMenuTopService terminalConfigMenuTopService;
    @Autowired
    private TerminalConfigNavigationTopService terminalConfigNavigationTopService;
    @Autowired
    private AppSearchV2ServiceImpl appSearchService;
    @Autowired
    private GetLoginService getLoginService;
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/workbench" ;

    /**
 　　* @description: 终端版本升级
 　　* @param ${tags}
 　　* @return ${return_type}
 　　* @author lihuiming
 　　* @date 2021/10/18 15:31
 　　*/
    @RequestMapping("/upgrade")
    public ResponseParam appUpgrade(@RequestBody(required = false) ApkBasicDto info, HttpServletRequest servletRequest) throws Exception{
        Map<String,Object>  map = applicationBasicService.upgrade(info,servletRequest);
        if(null != map.get("msg") && !StringUtils.isEmpty(map.get("msg").toString())){
            return ResponseParam.fail().message(map.get("msg").toString());
        }
        ApkBasic infoNew = (ApkBasic)map.get("info");
        if(null == infoNew){
            return ResponseParam.fail().message("未获取到版本信息");
        }
        return ResponseParam.success().message("获取信息成功").data(infoNew);
    }

    /**
     　　* @description: 应用版本升级
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @author lihuiming
     　　* @date 2021/10/18 15:31
     　　*/
    @RequestMapping("/upgradeapp")
    public ResponseParam upgradeapp(@RequestBody ApkBasicDto dto, HttpServletRequest servletRequest) throws Exception{
        Map<String,Object>  map = applicationBasicService.upgradeApp(dto,servletRequest);
        if(null != map.get("msg") && !StringUtils.isEmpty(map.get("msg").toString())){
            return ResponseParam.fail().message(map.get("msg").toString());
        }
        ApkBasic infoNew = (ApkBasic)map.get("info");
        if(null == infoNew){
            return ResponseParam.fail().message("未获取到版本信息");
        }
        return ResponseParam.success().message("获取信息成功").data(infoNew);
    }
    /**
     * 获取人员及目录树信息
     * @param   servletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping("/category")
    public ResponseParam category(@RequestBody  HttpServletRequest servletRequest) throws Exception{
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
        List<TerminalBasicEntity> terminals = terminalBasicService.getCacheTerminal(headerMessage.getPackageName(),headerMessage.getTerminalType().name());
        TerminalBasicEntity terminalBasic = new TerminalBasicEntity();
        for (TerminalBasicEntity terminalBasicEntity : terminals) {
            if(terminalBasicEntity.getTerminalType().equals(headerMessage.getTerminalType())){
                terminalBasic = terminalBasicEntity;
            }
        }
        Map<String, Object> searchParam = new HashMap<String, Object>();
        searchParam.put("terminalId",terminalBasic.getId());
        TerminalConfigApiEntity configApiEntity = new TerminalConfigApiEntity();
        List<TerminalConfigApiEntity>  apiEntityList = terminalConfigApiService.queryAll(searchParam);
        Map map = new HashMap();
        if(!apiEntityList.isEmpty()){
            configApiEntity = apiEntityList.get(0);
        }else{
            return ResponseParam.success().message("获取配置信息失败");
        }
        searchParam = new HashMap<String, Object>();
        searchParam.put("apiCategory",configApiEntity.getBindCategory());

        List<ConfigApiEntity> entityList = configApiService.queryAll(searchParam);

        return ResponseParam.success().message("获取信息成功");
    }

    /**
     * 模糊查询
     * @param fSearchBean
     * @return
     */
    @RequestMapping("/search")
    public ResponseParam search(@RequestBody FSearchBean fSearchBean) {
        //根据包名查授权信息
        //过滤组织 地域 身份等
        Map<String, Object> searchParam = new HashMap<String, Object>();
        List<FSearchBean> infoList = new ArrayList<>();
        searchParam.put("appName",fSearchBean.getKey());
        List<ApplicationBasicEntity> basicEntityList = applicationBasicService.queryAll(searchParam);
        for(ApplicationBasicEntity basicEntity : basicEntityList){
                String appName = basicEntity.getAppName();
                Pattern pattern = Pattern.compile(fSearchBean.getKey().trim());
                Matcher matcher = pattern.matcher(basicEntity.getAppName());
                while (matcher.find()) {
                    appName = matcher.replaceAll("<span style='color: blue;'>" + matcher.group() + "</span>");
                }

            fSearchBean.setTitle(basicEntity.getAppName());
            fSearchBean.setTitleHtml(appName);
            fSearchBean.setItemId(basicEntity.getId().toString());
            fSearchBean.setHios("https://www.baidu.com/");
            fSearchBean.setImgUrl(basicEntity.getIconUrl());
            infoList.add(fSearchBean);
        }

        infoList = infoList.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(m -> m.getTitle()))), ArrayList::new));

        return ResponseParam.success().message("获取信息成功").data(infoList) ;
    }


    private static String getFileSuffix(String fileName) {
        if (fileName != null) {
            if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
                return fileName.substring(fileName.lastIndexOf(".") + 1);
            }
        }
        return "";
    }

    @RequestMapping("/saveImg")
    public ResponseParam saveImg(@RequestBody JSONObject jsonObject ) throws Exception{

        return ResponseParam.success().message("保存信息成功");
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseParam uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
        String uploadAPKUrl = "http://119.188.115.252:8090/api/oss/resource-handle/upload?inner=false";
        Assert.notNull(file, "参数异常");
        String fileName = file.getOriginalFilename();// 文件原名称
        String suffix = getFileSuffix(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap map = new LinkedMultiValueMap();
        ByteArrayResource is = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }

            @Override
            public long contentLength() {
                return file.getSize();
            }
        };
        map.add("file", is);
        map.add("name", "uploads/gongzuotai/" +  fileName);
        HttpEntity requestBody = new HttpEntity(map, headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(uploadAPKUrl, requestBody, JSONObject.class);
        JSONObject jsonObject = responseEntity.getBody();
        if (UtilMap.getBoolean(jsonObject, "success", false)) {
            JSONObject data = jsonObject.getJSONObject("data");
            Map<String, Object> rmap = new HashMap<>();
            rmap.put("url", data.get("url").toString());
            return ResponseParam.success().message("上传成功").data(rmap);
        } else {
            return ResponseParam.fail().message("上传失败");
        }
    }
    @RequestMapping("/safecenter/view")
    public ResponseParam view(HttpServletRequest servletRequest) {
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
        String token = headerMessage.getToken();
        Map<String,Object> map = Maps.newHashMap();
        if(null == token || "".equals(token)){
            return ResponseParam.fail().message("获取信息失败").data(map);
        }else {
            map.put("telphone", "15810900543");
            map.put("hostmail", "lhm@163.com");
            map.put("landline", "0543-1234567");
        }
        return ResponseParam.success().message("获取信息成功").data(map);
    }

    @RequestMapping("/safecenter/update")
    public ResponseParam safecenterupdate(@RequestBody JSONObject jsonObject,HttpServletRequest servletRequest ) {
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
        String token = headerMessage.getToken();
        Map<String,Object> map = Maps.newHashMap();
        if(null == token || "".equals(token)){
            return ResponseParam.fail().message("获取信息失败").data(map);
        }else {
            map.put("telphone", jsonObject.get("telphone") == null ? "15810900543" : jsonObject.get("telphone").toString());
            map.put("hostmail", jsonObject.get("hostmail") == null ? "未设置" : jsonObject.get("hostmail").toString());
            map.put("landline", jsonObject.get("landline") == null ? "0543-1234567" : jsonObject.get("landline").toString());
        }
        //map.put("ischeck",jsonObject.get("ischeck")==null?false:jsonObject.get("ischeck"));
        return ResponseParam.success().message("获取信息成功").data(map);
    }

    /**
     * 获取是否维护接口
     * @param configDto
     * @return
     */
    @RequestMapping("/ismaintain")
    public ResponseParam ismaintain(@RequestBody TerminalApplicationConfigDto configDto , HttpServletRequest servletRequest){
        //获取终端应用配置
        TerminalApplicationConfigEntity terminalApplicationConfig = terminalApplicationConfigService.getConfigById(configDto.getId());
        if(null == terminalApplicationConfig){
            return ResponseParam.fail().message("未获取到应用配置信息");
        }
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
        ConcurrentHashMap map = new ConcurrentHashMap();
        if(headerMessage.getPackageName().equals("com.fosung.lighthouse.dt2")){//目前只针对灯塔2.0app  TODO
            // 判断是否有权限访问
            AppSearchParamDto shelvesSearchParamDto = new AppSearchParamDto();
            shelvesSearchParamDto.setUserId(configDto.getUserId());
            shelvesSearchParamDto.setIdentityIds(configDto.getIdentityIds());
            shelvesSearchParamDto.setOrdIds(configDto.getOrdIds());
            getTerminal(shelvesSearchParamDto,servletRequest);
            Set<Long> shelvesIds = appSearchService.queryShvleIds(shelvesSearchParamDto);
            if(!shelvesIds.contains(terminalApplicationConfig.getId())){
                return ResponseParam.fail().message("无操作权限");
            }
        }
        JSONObject jsonObject =new JSONObject();
        ResponseParam result = new ResponseParam();
        ConcurrentHashMap  param = new ConcurrentHashMap();
        jsonObject.put("u",configDto.getU());
        jsonObject.put("p",configDto.getP());
        try{
            result = getLoginService.loginXz(jsonObject);
        }catch (Exception e){
           // e.printStackTrace();
        }
        //todo 获取肥城用户登录信息 后期删除
        if(null != result.get("cloudToken")){
            Map cloudToken= (HashMap)result.get("cloudToken") ;
            String access_token =cloudToken.get("access_token").toString();
            map.put("xz_token", access_token);
        }else{
            map.put("xz_token", "");
        }
        ApplicationBasicEntity basicEntity = applicationBasicService.getAppById(terminalApplicationConfig.getAppId());
        ApplicationVersionEntity versionEntity = applicationVersionService.get(terminalApplicationConfig.getAppVersionId());
        map.put("maintain",basicEntity.getMaintain());
        // 应APP侧要求此处以 maintainMessage 的值给APP侧 他根据 maintainMessage 是否为空判断应用是否处于维护中 2021-12-15 加 维护标题和维护背景url字段
        if(basicEntity.getMaintain()){
            String maintainMessage = basicEntity.getMaintainMessage();
            String htmlRegex="<[^>]+>";
            maintainMessage = maintainMessage.replaceAll(htmlRegex, "").replaceAll("\r|\n", "");
            map.put("maintainMessage", StringUtils.isBlank(maintainMessage) ? "维护中..." : maintainMessage);
        }else{
            map.put("maintainMessage", "");
        }
        map.put("maintainTitle", StringUtils.isBlank(basicEntity.getMaintainTitle()) ? "维护提示" : basicEntity.getMaintainTitle());
        map.put("maintainBackgroundUrl", StringUtils.isBlank(basicEntity.getMaintainBackgroundUrl()) ? "" : basicEntity.getMaintainBackgroundUrl() );
        switch (versionEntity.getAppType()){
            case ios:
                map.put("address","dataability://"+basicEntity.getPackageName()+"." + terminalApplicationConfig.getStartName() + terminalApplicationConfig.getStartParam());
                break;
            case android:
                param.put("appId",terminalApplicationConfig.getAppId());
                param.put("appVersionId",versionEntity.getId());
                param.put("del",false);
                List<ApplicationConfigAndroidEntity> androidEntitylist = applicationConfigAndroidService.queryAll(param);
                if(!androidEntitylist.isEmpty()){
                    ApplicationConfigAndroidEntity   androidEntity = androidEntitylist.get(0);
                    map.put("startApp",androidEntity.getStartApp());
                    //调起应用为否
                    if(!androidEntity.getStartApp()){
                        map.put("downloadPath",androidEntity.getAppPackagePath());
                    }
                }
                map.put("address","dataability://"+basicEntity.getPackageName()+"." + terminalApplicationConfig.getStartName() + terminalApplicationConfig.getStartParam());
                break;
            case h5:
                param = new ConcurrentHashMap();
                param.put("appId",terminalApplicationConfig.getAppId());
                param.put("appVersionId",versionEntity.getId());
                ApplicationConfigHtmlEntity htmlEntity = new ApplicationConfigHtmlEntity();
                        List<ApplicationConfigHtmlEntity> htmlList = applicationConfigHtmlService.queryAll(param);
                if(!htmlList.isEmpty()){
                       htmlEntity = htmlList.get(0);
                }
                map.put("address",htmlEntity.getFrontUrl());
                break;
            default:
                map.put("address","dataability://"+basicEntity.getPackageName()+"." + versionEntity.getStartName() +versionEntity.getStartParams() );
                break;
        }
        return ResponseParam.success().message("查询信息成功").data(map);
    }

    /**
     *获取终端顶部导航
     * @return
     */
    @RequestMapping("/navigationtop")
    public ResponseParam navigationTop(@RequestBody TerminalConfigMenuTopDto menuTopDto, HttpServletRequest servletRequest ){
        Long time = System.currentTimeMillis();
        AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
        //根据包名和终端类型获取包名
        TerminalBasicEntity terminalBasic = terminalBasicService.getCacheTerminalByNameAndType(headerMessage.getPackageName(),headerMessage.getTerminalType().name());
        if(null == terminalBasic){
            return ResponseParam.fail().message("未获取到终端信息");
        }
        if(null == menuTopDto.getCityName()){
            return ResponseParam.fail().message("城市编码不能为空");
        }
        menuTopDto.setTerminalId(terminalBasic.getId());
        //获取顶部导航菜单列表
        List<Map<String, Object>> resultList  = terminalConfigMenuTopService.getMeunList(menuTopDto);
        if(null == resultList){
            return ResponseParam.fail().message("未获取到分类信息");
        }
        Map map = new HashMap();
        map.put("data",resultList);
        System.out.println( "结束："+ (System.currentTimeMillis()-time) +"");
        //treeList= UtilTree.getTreeData(treeList,"id","parentId","children",false);
        return ResponseParam.success().data(map).message("查询分类成功");
    }



    @RequestMapping("/download")
    public ResponseParam download( HttpServletRequest servletRequest ){
        return ResponseParam.success().data("");
    }

    /**
     *获取终端
     *
     * @param httpServletRequest
     * @author liuke
     * @date 2021/10/22 14:34
     * @return com.geek.workbench.entity.terminal.TerminalBasicEntity
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
        //todo  添加用户信息
        return terminal;
    }
    @RequestMapping("/getAppletpath")
    public ResponseParam query(@RequestBody ApplicationConfigHtmlDto configHtmlDto){
        if(null == configHtmlDto.getUserName() || "".equals(configHtmlDto.getUserName())){
            return  ResponseParam.fail().message("请传递小程序名称") ;
        }
        TerminalApplicationConfigEntity terminalApplicationConfig = terminalApplicationConfigService.getConfigById(Long.valueOf(configHtmlDto.getAppId()));
        if(null == terminalApplicationConfig){
            return ResponseParam.fail().message("未获取到应用配置信息");
        }
        ApplicationBasicEntity basicEntity = applicationBasicService.getAppById(terminalApplicationConfig.getAppId());
        Map map = new HashMap();
        map.put("frontUrl",configHtmlDto.getUserName());
        map.put("appId",basicEntity.getId());
        List<ApplicationConfigHtmlEntity> configHtmlEntityList = applicationConfigHtmlService.queryAll(map);
        if(!configHtmlEntityList.isEmpty()){
            ApplicationConfigHtmlEntity configHtmlEntity = configHtmlEntityList.get(0);
            map.put("appletId",configHtmlEntity.getAppletId());
            map.put("url",configHtmlEntity.getFrontUrl());
            map.put("appletPath",configHtmlEntity.getAppletPath());
        }else{
            return  ResponseParam.fail().message("未获取到小程序信息") ;
        }
        return  ResponseParam.success().message("获取信息成功").data(map) ;
    }
}
