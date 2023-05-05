package com.fosung.workbench.configurecenter.service;

import com.fosung.framework.common.util.UtilString;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 *
 * 配置信息获取适配类
 *
 * @author liuke
 * @date  2021/10/9 16:27
 * @version
*/
@Service
public class WorkBenchConfigureServiceAdapter {

    private List<WorkBenchConfigureService> workBenchServices;

    public WorkBenchConfigureServiceAdapter(ObjectProvider<List<WorkBenchConfigureService>> workBenchServices){
        this.workBenchServices = workBenchServices.getIfAvailable();
    }

    /**
     *根据key适配service
     *
     * @param key
     * @author liuke
     * @date 2021/10/9 11:30
     * @return com.fosung.workbench.configurecenter.service.WorkBenchConfigureService
     */
    public WorkBenchConfigureService getWorkBenchConfigureService(String key){
        for (WorkBenchConfigureService workBenchService : workBenchServices) {
            if(UtilString.equals(key,workBenchService.getConfigureKey())){
                return workBenchService;
            }
        }
        return null;
    }
}
