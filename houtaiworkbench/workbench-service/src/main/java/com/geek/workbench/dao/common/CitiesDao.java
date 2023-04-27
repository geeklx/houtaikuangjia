package com.geek.workbench.dao.common;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.entity.common.CitiesEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description 地市编码表持久化接口
 * @Author gaojian
 * @Date 2021/11/1 9:01
 * @Version V1.0
 */
public interface CitiesDao extends AppJPABaseDao<CitiesEntity, Long> {

    /**
     * 描述:  根据地市编码模糊查询下级信息
     * @createDate: 2021/11/1 9:06
     * @author: gaojian
     * @modify:
     * @param code
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @MybatisQuery
    List<Map<String,Object>> queryRootCities(@Param(value = "code") String code);

    /**
     * 描述:  根据地市编码模糊查询下级信息
     * @createDate: 2021/11/1 9:06
     * @author: gaojian
     * @modify:
     * @param dimCode 模糊编码
     * @param code 编码
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @MybatisQuery
    List<Map<String,Object>> queryCities(@Param(value = "dimCode") String dimCode,@Param(value = "code") String code);
}
