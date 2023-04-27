package com.geek.workbench.configurecenter.service.impl;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.common.util.UtilDate;
import com.fosung.framework.common.util.UtilString;
import com.geek.workbench.AppBean.AppNavicationBtm;
import com.geek.workbench.configurecenter.bean.InitMessageBean;
import com.geek.workbench.dict.AgreementType;
import com.geek.workbench.dto.config.AppSearchParamDto;
import com.geek.workbench.entity.microcoder.TerminalConfigAgreementEntity;
import com.geek.workbench.entity.terminal.TerminalConfigCommonEntity;
import com.geek.workbench.entity.terminal.TerminalImageConfigEntity;
import com.geek.workbench.service.microcoder.TerminalConfigAgreementService;
import com.geek.workbench.service.terminal.TerminalConfigCommonService;
import com.geek.workbench.service.terminal.TerminalImageConfigService;
import com.geek.workbench.util.CacheUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 获取协议配置信息
 * @author liuke
 * @date  2021/10/9 16:25
 * @version 
*/
@Service
public class TerminalConfigAgreementServiceImpl extends AbstractWorkBenchConfig {



    private final static int CONFIG_NUM = 12;

    /**
     * 描述:  注入终端公共配置服务
     * @createDate: 2021/10/15 9:46
     * @author: gaojian
     */
    @Autowired
    private TerminalConfigAgreementService terminalConfigAgreementService;

    @Autowired
    private TerminalConfigCommonService terminalConfigCommonService;

    @Autowired
    private com.geek.workbench.service.microcoder.TerminalConfigNavigationBtmService TerminalConfigNavigationBtmService;

    @Autowired
    private TerminalImageConfigService terminalImageConfigService;




    /**
     * 描述:  获取配置信息
     * @createDate: 2021/10/15 10:21
     * @author: gaojian
     * @modify:
     * @param
     * @param servletRequest
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> getConfigDetailMessage(AppSearchParamDto appSearchParamDto, HttpServletRequest servletRequest) throws Exception{

        InitMessageBean initMessageBean = terminalInitConfigCache.get(appSearchParamDto.getTerminalId());
        //设置默认标题 终端名称
        initMessageBean.setTitle_title(appSearchParamDto.getTerminalBasicEntity().getTerminalName());
        initMessageBean.setTerminal_logo(appSearchParamDto.getTerminalBasicEntity().getTerminalLogo());
        //设置项目名称及项目编号
        if(null != appSearchParamDto.getProjectBasicEntity()){
            initMessageBean.setProject_name(appSearchParamDto.getProjectBasicEntity().getProjectName() == null ? "" : appSearchParamDto.getProjectBasicEntity().getProjectName());
            initMessageBean.setProject_code(appSearchParamDto.getProjectBasicEntity().getProjectCode() == null ? "" : appSearchParamDto.getProjectBasicEntity().getProjectCode());
         }else{
            initMessageBean.setProject_name("");
            initMessageBean.setProject_code("");
        }
        return UtilDTO.toDTO(initMessageBean,null,null,null);
    }

    /**
     *终端应用分类配置缓存
     *
     * @author liuke
     * @date 2021/10/15 14:56
     * @return
     */
    public LoadingCache<Long,InitMessageBean> terminalInitConfigCache = CacheUtil.getInstance().build(
            new CacheLoader<Long,InitMessageBean>() {
                @Override
                public InitMessageBean load(Long key) throws Exception {
                    InitMessageBean initMessageBean = new InitMessageBean();
                    // 1. 终端主键非空判断
                    Map<String,Object> result = Maps.newHashMap();
                    //设置协议
                    setAgreement(key,initMessageBean);
                    //广告业配置
                    setAdvertMessage(key,initMessageBean);
                    //引导页
                    setGuideImage(key,initMessageBean);
                    //基础配置
                    setBasicMessage(key,initMessageBean);
                    return initMessageBean;
                }
            }
    );

    /**
     * 描述:  获取配置信息
     * @createDate: 2021/10/15 10:21
     * @author: gaojian
     * @modify:
     * @param
     * @param servletRequest
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> getNavicationMessage(AppSearchParamDto appSearchParamDto, HttpServletRequest servletRequest) throws Exception{
        Map<String,Object> result = Maps.newHashMap();
        List<AppNavicationBtm> appNavicationBtms = TerminalConfigNavigationBtmService.getTerminalConfigNavigationBtms(appSearchParamDto);
        List<Map<String,Object>> navigations = UtilDTO.toDTO(appNavicationBtms,null,null,null);
        result.put("data",navigations);
        return result;
    }

    /**
     *广告业
     *
     * @param terminalId
     * @param result
     * @author liuke
     * @date 2021/10/21 16:34
     * @return void
     */
    public void setAdvertMessage(Long terminalId,InitMessageBean result){
        List<TerminalImageConfigEntity> terminalImageConfigEntities = terminalImageConfigService.getCacheTerminalImgByTerminal(terminalId);
        terminalImageConfigEntities = terminalImageConfigEntities.stream().filter(img -> UtilString.equals(img.getImageType().name(),"advert")).collect(Collectors.toList());
        if(!UtilCollection.sizeIsEmpty(terminalImageConfigEntities)){
            TerminalImageConfigEntity terminalImageConfigEntity = terminalImageConfigEntities.get(0);
            result.setAdvertimage(terminalImageConfigEntity.getImgUrl());
            result.setAdvertlinkurl(terminalImageConfigEntity.getLinkUrl());
        }else {
            result.setAdvertimage("");
            result.setAdvertlinkurl("");
        }

    }

    /**
     *设置引导页
     *
     * @param terminalId
     * @param result
     * @author liuke
     * @date 2021/10/21 16:35
     * @return void
     */
    public void setGuideImage(Long terminalId,InitMessageBean result){
        List<TerminalImageConfigEntity> terminalImageConfigEntities = terminalImageConfigService.getCacheTerminalGuideImgByTerminal(terminalId);
        terminalImageConfigEntities = terminalImageConfigEntities.stream().filter(img -> UtilString.equals(img.getImageType().name(),"guide")).collect(Collectors.toList());
        if(!UtilCollection.sizeIsEmpty(terminalImageConfigEntities)){
            List<String> list = UtilCollection.extractToList(terminalImageConfigEntities,"imgUrl",String.class);
            result.setGuideimage(list);
        }else {
            result.setGuideimage(Lists.newArrayList());
        }
    }

    /**
     *设置基础配置
     *
     * @param terminalId
     * @param result
     * @author liuke
     * @date 2021/10/21 16:37
     * @return void
     */
    public void setBasicMessage(Long terminalId,InitMessageBean result){
        List<TerminalConfigCommonEntity> listcongig = terminalConfigCommonService.getTerminalConfigCommonByTerminalId(terminalId);

        for(TerminalConfigCommonEntity terminalConfigInfo : listcongig ){
            if (UtilString.equals("adverts",terminalConfigInfo.getConfigType())&&UtilString.equals("image",terminalConfigInfo.getConfigCode())){
                result.setAdvertimage(terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("theme",terminalConfigInfo.getConfigType())&&UtilString.equals("theme",terminalConfigInfo.getConfigCode())){
                result.setTheme_theme(terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("language",terminalConfigInfo.getConfigType())&&UtilString.equals("language",terminalConfigInfo.getConfigCode())){
                result.setLanguage_language(terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("authorization",terminalConfigInfo.getConfigType())&&UtilString.equals("ak",terminalConfigInfo.getConfigCode())){
                result.setAuthorization_ak(terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("authorization",terminalConfigInfo.getConfigType())&&UtilString.equals("version",terminalConfigInfo.getConfigCode())){
                result.setAuthorization_version(terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("authorization",terminalConfigInfo.getConfigType())&&UtilString.equals("sk",terminalConfigInfo.getConfigCode())){
                result.setAuthorization_sk(terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("shortStyle",terminalConfigInfo.getConfigType())&&UtilString.equals("style",terminalConfigInfo.getConfigCode())){
                result.setShort_style_style(terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("shortStyle",terminalConfigInfo.getConfigType())&&UtilString.equals("start_time",terminalConfigInfo.getConfigCode())){
                result.setShort_style_start_time(terminalConfigInfo.getConfigValue()==null?"":terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("shortStyle",terminalConfigInfo.getConfigType())&&UtilString.equals("end_time",terminalConfigInfo.getConfigCode())){
                result.setShort_style_end_time(terminalConfigInfo.getConfigValue()==null?"":terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("fingerprint",terminalConfigInfo.getConfigType())&&UtilString.equals("fingerprint",terminalConfigInfo.getConfigCode())){
                result.setFingerprint_fingerprint(terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("watermark",terminalConfigInfo.getConfigType())&&UtilString.equals("contacts_name",terminalConfigInfo.getConfigCode())){
                result.setWatermark_contacts_name(terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("watermark",terminalConfigInfo.getConfigType())&&UtilString.equals("contacts_phone4",terminalConfigInfo.getConfigCode())){
                result.setWatermark_contacts_phone4(terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("phone",terminalConfigInfo.getConfigType())&&UtilString.equals("customer",terminalConfigInfo.getConfigCode())){
                result.setCustomer_phone(terminalConfigInfo.getConfigValue());
            }else if(UtilString.equals("login",terminalConfigInfo.getConfigType())&&UtilString.equals("login",terminalConfigInfo.getConfigCode())){
                result.setLogin_login(terminalConfigInfo.getConfigValue());
            }

        }
        if (UtilString.equals(result.getShort_style_style(),"true") || UtilString.equals(result.getShort_style_style(),"grey")){
            Date date = new Date();
            result.setShort_style_style("true");
            // 设置了时间 除了 当前时间小于开始时间和当前时间大于结束时间 不置灰 其他都要置灰
            if(UtilString.isNoneBlank(result.getShort_style_start_time()) && UtilString.isNoneBlank(result.getShort_style_end_time())){
                if(UtilDate.parse(result.getShort_style_start_time()).getTime() > date.getTime()
                        || UtilDate.parse(result.getShort_style_end_time()).getTime() < date.getTime()){
                    result.setShort_style_style("false");
                }
            }
        }else{
            result.setShort_style_style("false");
        }
    }


    /**
     *设置协议
     *
     * @param terminalId
     * @param result
     * @author liuke
     * @date 2021/10/21 16:40
     * @return void
     */
    public void setAgreement(Long terminalId,InitMessageBean result){
        //获取协议列表
        List<TerminalConfigAgreementEntity> list = terminalConfigAgreementService.getTerminalConfigAgreements(terminalId);
        //存储协议
        if(!UtilCollection.sizeIsEmpty(list)){
            list.forEach(terminalBasicEntity -> {
                if(terminalBasicEntity.getAgreementType().equals(AgreementType.privacy)){
                    result.setPrivacy(terminalBasicEntity.getAgreementUrl());
                }else if (terminalBasicEntity.getAgreementType().equals(AgreementType.user)){
                    result.setUser(terminalBasicEntity.getAgreementUrl());
                }
            });
        }
    }
}
