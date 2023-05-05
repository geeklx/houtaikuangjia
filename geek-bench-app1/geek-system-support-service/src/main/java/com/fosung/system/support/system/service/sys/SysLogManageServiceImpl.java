package com.fosung.system.support.system.service.sys;

import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.system.support.system.dao.sys.SysLogManageDao;
import com.fosung.system.support.system.entity.sys.SysLogManageEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author 付昊
 * @date 2022/1/13 10:00
 */
@Service
public class SysLogManageServiceImpl  extends AppJPABaseDataServiceImpl<SysLogManageEntity, SysLogManageDao>
        implements SysLogManageService {

    /**
     * 查询条件表达式
     */
    private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
        {
            put("optModule","optModule:LIKE");
            put("optName","optName:LIKE");
            put("optNameEQ","optName:EQ");
            put("optType","optType:EQ");
            put("status","status:EQ");
            put("createUserId","createUserId:EQ");
            put("operName","operName:LIKE");
            put("operNameEQ","operName:EQ");
            put("startTime", "createDatetime:GTEDATE");
            put("endTime", "createDatetime:LTEDATE");
        }
    };

    @Override
    public Map<String, String> getQueryExpressions() {
        return expressionMap ;
    }

}
