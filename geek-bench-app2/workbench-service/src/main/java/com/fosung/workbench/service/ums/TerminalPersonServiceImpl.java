package com.fosung.workbench.service.ums;

import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.dao.ums.TerminalPersonDao;
import com.fosung.workbench.entity.ums.TerminalPersonEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description
 * @Author gaojian
 * @Date 2022/2/22 16:20
 * @Version V1.0
 */
@Service
public class TerminalPersonServiceImpl  extends AppJPABaseDataServiceImpl<TerminalPersonEntity, TerminalPersonDao>
        implements TerminalPersonService {

    /**
     * 查询条件表达式
     */
    private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
        {
            put("terminalId","terminalId:EQ");
        }
    };

    @Override
    public Map<String, String> getQueryExpressions() {
        return expressionMap ;
    }
}
