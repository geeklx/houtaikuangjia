package com.fosung.workbench.service.shorturl;

import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.dao.shorturl.ShortUrlMappingDao;
import com.fosung.workbench.entity.shorturl.ShortUrlMappingEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description 短 URL 映射持久化服务实现类
 * @Author gaojian
 * @Date 2022/1/17 11:26
 * @Version V1.0
 */
@Service
public class ShortUrlMappingServiceImpl extends AppJPABaseDataServiceImpl<ShortUrlMappingEntity, ShortUrlMappingDao>
        implements ShortUrlMappingService {

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
            put("shortUrl","shortUrl:RLIKE");
        }
    };
}
