package com.fosung.system.pbs.mq.facade;

import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RepairOrderConsumerRecordService extends AppJPABaseDataServiceImpl<RepairOrderConsumerRecord, RepairOrderConsumerRecordDao> {

    /**
     * 查询条件表达式
     */
    private Map<String, String> expressionMap = new HashMap<String, String>() {
        {
            {
                put("topic", "topic:EQ");
                put("tag", "tag:EQ");
                put("consumer", "consumer:EQ");
                put("handler", "handler:LIKE");
                put("startTime", "operateTime:GTEDATE");
                put("endTime", "operateTime:LTEDATE");
                put("status", "status:EQ");
                put("uniqueId", "uniqueId:EQ");
            }
        }
    };

    @Override
    public Map<String, String> getQueryExpressions() {
        return expressionMap;
    }


}
