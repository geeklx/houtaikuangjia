package com.fosung.system.support.util.easyexcel.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.system.support.util.easyexcel.dto.UserDetailDto;
import com.google.api.client.util.Sets;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;

public class UserDetailDataListener extends AnalysisEventListener<UserDetailDto> {

    //最大支持条数
    private static final int BATCH_COUNT = 2000;
    //由于每次读都是新new UserInfoDataListener的，所以这个list不会存在线程安全问题
    public HashSet<UserDetailDto> list = Sets.newHashSet();
    //错误信息
    public HashSet<String> result = Sets.newHashSet();

    //这个组件是Spring中的组件，这边推荐两种方法注入这个组件
    //第一种就是提供一个UserInfoDataListener的构造方法，这个方法提供一个参数是UserInfoDataListener类型
    //另外一种方法就是将 UserInfoDataListener 这个类定义成 UserService 实现类的内部类（推荐这种方式）

    public UserDetailDataListener() {
//        result.clear();
//        list.clear();
    }


    @Override
    public void invoke(UserDetailDto data, AnalysisContext analysisContext) {
        Integer count = analysisContext.readSheetHolder().getApproximateTotalRowNumber() - analysisContext.readSheetHolder().getHeadRowNumber();
        if(count > BATCH_COUNT){
            result.add("已超过最大行数:"+BATCH_COUNT);
            return;
        }
        StringBuffer failInfo = new StringBuffer("第"+(analysisContext.getCurrentRowNum()+1)+"行,");
        if(StringUtils.isBlank(data.getIdCard())){
            failInfo.append("身份证信息为空!");
            result.add(failInfo.toString());
        }
        if(StringUtils.isBlank(data.getProjectCode())){
            failInfo.append("租户编码信息为空!");
            result.add(failInfo.toString());
        }
        if(StringUtils.isBlank(data.getOrgName())){
            failInfo.append("组织信息为空!");
            result.add(failInfo.toString());
        }
        if(StringUtils.isBlank(data.getUserName())){
            failInfo.append("用户名信息为空!");
            result.add(failInfo.toString());
        }
        if(StringUtils.isBlank(data.getRealName())){
            failInfo.append("真实姓名信息为空!");
            result.add(failInfo.toString());
        }
        if(StringUtils.isBlank(data.getTelephone())){
            failInfo.append("手机号为空!");
            result.add(failInfo.toString());
        }
        if(StringUtils.isBlank(data.getPassword())){
            failInfo.append("密码为空!");
            result.add(failInfo.toString());
        }
        if(UtilCollection.sizeIsEmpty(result)){
            list.add(data);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }



}