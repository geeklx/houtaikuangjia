package com.geek.workbench.dao.config;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.entity.config.TerminalConfigApiEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @Description 终端接口配置持久化接口
 * @Author gaojian
 * @Date 2021/10/24 10:25
 * @Version V1.0
 */
public interface TerminalConfigApiDao extends AppJPABaseDao<TerminalConfigApiEntity, Long>{

    /**
     * 描述:  查询绑定的终端
     * @createDate: 2021/10/25 11:48
     * @author: gaojian
     * @modify:
     * @param bindGroupId
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @MybatisQuery
    List<Map<String,Object>> queryBindTerminal(@Param("bindGroupId") Long bindGroupId);

    /**
     * 描述:  查询未绑定的终端
     * @createDate: 2021/10/25 11:48
     * @author: gaojian
     * @modify:
     * @param searchParams
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @MybatisQuery
    List<Map<String,Object>> queryUnBindTerminal(@Param("params") Map<String,Object> searchParams);

    /**
     * 描述: 通过组id删除所有的绑定
     * @createDate: 2021/10/25 17:00
     * @author: gaojian
     * @modify:
     * @param bindGroupId
     * @return: java.lang.Integer
     */
    Integer deleteAllByBindGroupId(Long bindGroupId);

}