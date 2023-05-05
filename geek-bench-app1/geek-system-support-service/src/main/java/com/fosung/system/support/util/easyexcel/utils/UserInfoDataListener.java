package com.fosung.system.support.util.easyexcel.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.system.support.util.easyexcel.dto.UserInfoDto;
import com.google.api.client.util.Sets;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;


import java.util.*;

public class UserInfoDataListener extends AnalysisEventListener<UserInfoDto> {
    
    //最大支持条数
    private static final int BATCH_COUNT = 2000;
    //由于每次读都是新new UserInfoDataListener的，所以这个list不会存在线程安全问题
    public HashMap<String, List> resultList = Maps.newHashMap();
    //错误信息
    public HashSet<String> failInfoResult = Sets.newHashSet();

    //这个组件是Spring中的组件，这边推荐两种方法注入这个组件
    //第一种就是提供一个UserInfoDataListener的构造方法，这个方法提供一个参数是UserInfoDataListener类型
    //另外一种方法就是将 UserInfoDataListener 这个类定义成 UserService 实现类的内部类（推荐这种方式）

    public UserInfoDataListener() {
//        result.clear();
//        list.clear();
    }


    @Override
    public void invoke(UserInfoDto data, AnalysisContext analysisContext) {
        Integer count = analysisContext.readSheetHolder().getApproximateTotalRowNumber() - analysisContext.readSheetHolder().getHeadRowNumber();
        if(count > BATCH_COUNT){
            failInfoResult.add("已超过最大行数:"+BATCH_COUNT);
            return;
        }
        StringBuffer failInfo = new StringBuffer("第"+(analysisContext.getCurrentRowNum()+1)+"行,");
        String roleName = data.getRoleName();
        String[] roleNames = new String[]{};
        if(StringUtils.isBlank(data.getIdCard())){
            failInfo.append("身份证信息为空!");
            if(StringUtils.isBlank(data.getRoleName())){
                roleNames = StringUtils.split(roleName, ",");
                if(roleNames == null || roleNames.length < 1){
                    failInfo.append("角色信息为空!");
                }
            }
            failInfoResult.add(failInfo.toString());
        }else {
            if(StringUtils.isBlank(data.getRoleName())){
                failInfo.append("角色信息为空!");
                failInfoResult.add(failInfo.toString());
            }else {
                roleNames = StringUtils.split(roleName, ",");
                if(roleNames == null || roleNames.length < 1){
                    failInfo.append("角色信息为空!");
                    failInfoResult.add(failInfo.toString());
                }

            }
        }
        if(UtilCollection.sizeIsEmpty(failInfoResult)){
            resultList.put(data.getIdCard(), Arrays.asList(roleNames));
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }



}