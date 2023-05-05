package com.fosung.workbench.service.shorturl;

import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.dao.shorturl.NumberSenderDao;
import com.fosung.workbench.entity.shorturl.NumberSenderEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description 发号信息持久化服务
 * @Author gaojian
 * @Date 2022/1/17 11:25
 * @Version V1.0
 */
@Service
public class NumberSenderServiceImpl  extends AppJPABaseDataServiceImpl<NumberSenderEntity, NumberSenderDao>
        implements NumberSenderService {

    /**
     * 获取查询条件的表达式，用于匹配查询参数对应的查询条件，保存查询字段和数据库查询表达式的映射<br>
     *
     * @return
     */
    @Override
    public Map<String, String> getQueryExpressions() {
        return expressionMap;
    }

    /**
     * 查询条件表达式
     */
    private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
        {
            put("id","id:EQ");
            put("numberSenderKey","numberSenderKey:EQ");
        }
    };

    /**
     * 描述:  更新发号器值
     *
     * @param numberSenderEntity
     * @createDate: 2022/1/17 17:18
     * @author: gaojian
     * @modify:
     * @return: void
     */
    @Override
    public void updateNumber(NumberSenderEntity numberSenderEntity) {

        // 更新发号器值
        entityDao.updateNumber(numberSenderEntity);
    }
}
