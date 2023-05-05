package com.fosung.workbench.rest.base;

import com.fosung.workbench.common.AppCategory;
import com.fosung.workbench.common.GlobalVariableKey;
import com.fosung.workbench.entity.application.ApplicationVersionEntity;
import com.fosung.workbench.entity.terminal.TerminalVersionEntity;
import com.fosung.workbench.service.application.ApplicationVersionService;
import com.fosung.workbench.service.terminal.TerminalVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

/**
 * @Description 应用安装数、下载数定时任务
 * @Author gaojian
 * @Date 2021/10/15 14:23
 * @Version V1.0
 */
@Component
@EnableScheduling
public class StatisticalScheduleTask {

    /**
     * 描述:  redis
     *
     * @createDate: 2021/10/15 14:42
     * @author: gaojian
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TerminalVersionService terminalVersionService;

    @Autowired
    private ApplicationVersionService applicationVersionService;

    /**
     * 描述:  定时任务 更新安装数
     * @createDate: 2021/10/15 14:26
     * @author: gaojian
     * @return: void
     */
    @Scheduled(cron = "0 0 23 * * ?")
    private void updateInstall() {

        // 1. 获取终端应用
        String keyTerminalPrefix = GlobalVariableKey.INSTALL_REDIS_PATH_PREFIX + ":" + AppCategory.TERMINAL.getValue() + ":";
        Set<String> TerminalList = stringRedisTemplate.keys(keyTerminalPrefix + "*");
        for( String key : TerminalList ){
            String appId = key.substring(keyTerminalPrefix.length());
            TerminalVersionEntity terminalVersionEntity = terminalVersionService.get(Long.parseLong(appId));
            if(terminalVersionEntity == null ){
                continue;
            }
            Integer installNum = terminalVersionEntity.getInstallationsNumber();
            if(installNum == null ){
                installNum = 0;
            }
            Integer todayInstallNum = Integer.valueOf(stringRedisTemplate.opsForValue().get(key));
            terminalVersionEntity.setInstallationsNumber(installNum + todayInstallNum);
            terminalVersionService.update(terminalVersionEntity, Arrays.asList("installationsNumber"));
            stringRedisTemplate.delete(key);
        }

        System.err.println("更新终端/应用安装次数定时任务完成: " + LocalDateTime.now());
    }

    /**
     * 描述:  定时任务 更新下载数
     * @createDate: 2021/10/15 14:26
     * @author: gaojian
     * @return: void
     */
    @Scheduled(cron = "0 0 23 * * ?")
    private void updateDownload() {

        // 1. 修改终端下载次数
        String keyDownLoadTerminalPrefix = GlobalVariableKey.DOWNLOAD_REDIS_PATH_PREFIX + ":" + AppCategory.TERMINAL.getValue() + ":";
        Set<String> list = stringRedisTemplate.keys( keyDownLoadTerminalPrefix + "*");
        for( String key : list ){
            String appId = key.substring(keyDownLoadTerminalPrefix.length());
            TerminalVersionEntity terminalVersionEntity = terminalVersionService.get(Long.parseLong(appId));
            if(terminalVersionEntity == null ){
                continue;
            }
            Integer downloadNum = terminalVersionEntity.getDownloadNumber();
            if(downloadNum == null ){
                downloadNum = 0;
            }
            Integer todayDownloadNum = Integer.valueOf(stringRedisTemplate.opsForValue().get(key));
            terminalVersionEntity.setDownloadNumber(downloadNum + todayDownloadNum);
            terminalVersionService.update(terminalVersionEntity, Arrays.asList("downloadNumber"));
            stringRedisTemplate.delete(key);
        }

        // 2. 修改应用下载次数
        String keyDownLoadAppPrefix = GlobalVariableKey.DOWNLOAD_REDIS_PATH_PREFIX + ":" + AppCategory.APP.getValue() + ":";
        Set<String> appList = stringRedisTemplate.keys( keyDownLoadAppPrefix + "*");
        for( String key : appList ){
            String appId = key.substring(keyDownLoadAppPrefix.length());
            ApplicationVersionEntity applicationVersionEntity = applicationVersionService.get(Long.parseLong(appId));
            if(applicationVersionEntity == null ){
                continue;
            }
            Integer downloadNum = applicationVersionEntity.getDownloadNum();
            if(downloadNum == null ){
                downloadNum = 0;
            }
            Integer todayDownloadNum = Integer.valueOf(stringRedisTemplate.opsForValue().get(key));
            applicationVersionEntity.setDownloadNum(downloadNum + todayDownloadNum);
            applicationVersionService.update(applicationVersionEntity, Arrays.asList("downloadNum"));
            stringRedisTemplate.delete(key);
        }
        System.err.println("更新终端/应用下载次数定时任务完成: " + LocalDateTime.now());
    }
}
