package com.geek.workbench.dao.shorturl;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.entity.shorturl.NumberSenderEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 发号器持久化层
 * @Author gaojian
 * @Date 2022/1/17 11:23
 * @Version V1.0
 */
public interface NumberSenderDao extends AppJPABaseDao<NumberSenderEntity, Long> {

    /**
     * 描述:
     * @createDate: 2022/1/17 17:16
     * @author: gaojian
     * @modify:
     * @param numberSenderEntity
     * @return: java.lang.Integer
     */
    @MybatisQuery
    Integer updateNumber(@Param("param") NumberSenderEntity numberSenderEntity);

}
