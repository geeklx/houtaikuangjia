package com.geek.workbench.service.common;

import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.pbs.api.SystemSysApi;
import com.geek.workbench.common.GlobalVariableKey;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.dict.MandateOpt;
import com.geek.workbench.dto.pipeline.MandateDto;
import com.geek.workbench.dto.pipeline.MandateParams;
import com.geek.workbench.entity.application.ApplicationBasicEntity;
import com.geek.workbench.entity.terminal.TerminalApplicationShelvesEntity;
import com.geek.workbench.entity.terminal.TerminalBasicEntity;
import com.geek.workbench.service.application.ApplicationBasicService;
import com.geek.workbench.service.terminal.TerminalApplicationShelvesService;
import com.geek.workbench.service.terminal.TerminalBasicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 数据同步服务实现类
 * @Author gaojian
 * @Date 2021/10/15 8:25
 * @Version V1.0
 */
@Service
public class DataPipelineServiceImpl implements DataPipelineService {

    /**
     * 描述:  应用基础信息服务类
     * @createDate: 2021/10/15 8:28
     * @author: gaojian
     */
    @Autowired
    private ApplicationBasicService applicationBasicService;

    /**
     * 描述:  终端基础信息服务类
     * @createDate: 2021/10/15 8:46
     * @author: gaojian
     */
    @Autowired
    private TerminalBasicService terminalBasicService;

    /**
     * 描述:  终端应用授权服务
     * @createDate: 2021/10/15 9:34
     * @author: gaojian
     */
    @Autowired
    private TerminalApplicationShelvesService terminalApplicationShelvesService;

    /**
     * 描述:  字典服务
     * @createDate: 2021/10/15 8:57
     * @author: gaojian
     */
    @Autowired
    private SystemSysApi systemSysApi;

    /**
     * 描述:  同步授权信息
     *
     * @param mandateParams
     * @createDate: 2021/10/15 8:27
     * @author: gaojian
     * @modify:
     * @return: void
     */
    @Override
    public void pullPermission(MandateParams mandateParams) {

        // 1. 判断应用是否存在
        Map<String,Object> searchParams = new HashMap<>(4);
        searchParams.put(GlobalVariableKey.APP_EXTERNAL_ID,mandateParams.getAppId());
        List<ApplicationBasicEntity> appList = applicationBasicService.queryAll(searchParams);
        if(appList == null || appList.size() < 1){
            throw new AppException(MessageContent.APP_IS_NOT_EXIST);
        }
        ApplicationBasicEntity applicationBasicEntity = appList.get(0);

        // 2. 判断终端是否存在
        TerminalBasicEntity terminalBasicEntity = terminalBasicService.get(mandateParams.getTeimalId());
        if(terminalBasicEntity == null || terminalBasicEntity.getDel()){
            throw new AppException(MessageContent.TERMINAL_IS_NOT_EXIST);
        }

        // 3. 判断数据来源是否为配置的来源
        String dataSource = "";
        ResponseParam responseParam = systemSysApi.querySysTemDict(GlobalVariableKey.DATA_SOURCE_DICT);
        if((Boolean) responseParam.get(ResponseParam.SUCCESS_PARAM_VALUE)){
            List<Map<String,Object>> list = (List<Map<String, Object>>) responseParam.get(ResponseParam.DATA_LIST_PARAM);
            for(Map dictItem : list ){
                if(StringUtils.equals(mandateParams.getDataSource(),dictItem.get(GlobalVariableKey.DICT_VALUE).toString())){
                    dataSource = dictItem.get(GlobalVariableKey.DICT_LABEL).toString();
                }
            }
        }
        if(StringUtils.isBlank(dataSource)){
            throw new AppException(MessageContent.DATA_SOURCE_NOT_EXIST);
        }

        // 4. 循环保存授权信息
        List<TerminalApplicationShelvesEntity> list = new ArrayList<>();
        for(MandateDto mandateDto : mandateParams.getMandate()){
            if(StringUtils.equals(MandateOpt.ADD.name(),mandateDto.getMandateOpt().name())){
                TerminalApplicationShelvesEntity terminalApplicationShelvesEntity = new TerminalApplicationShelvesEntity();
                terminalApplicationShelvesEntity.setShelvesRange(mandateDto.getMandateValue());
                terminalApplicationShelvesEntity.setShelvesType(mandateDto.getMandateType());
                //terminalApplicationShelvesEntity.setAppId(applicationBasicEntity.getId());
                terminalApplicationShelvesEntity.setTerminalId(mandateParams.getTeimalId());
                list.add(terminalApplicationShelvesEntity);
            }else{

                Long appId = applicationBasicEntity.getId();
                String mandateType = mandateDto.getMandateType();
                String mandateValue = mandateDto.getMandateValue();

                // @TODO 根据应用id、授权终端Id、授权类型、授权值删除授权
            }
        }
        terminalApplicationShelvesService.saveBatch(list);
    }
}
