package com.geek.workbench.dao.microcoder;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.geek.workbench.entity.microcoder.TerminalConfigMenuTopEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 描述:  终端顶部菜单持久化接口
 * @createDate: 2021/11/11 9:52
 * @author: gaojian
 */
public interface TerminalConfigMenuTopDao extends AppJPABaseDao<TerminalConfigMenuTopEntity, Long>{

    /**
     * 描述:  根据菜单id级联删除子菜单
     * @createDate: 2021/11/11 9:51
     * @author: gaojian
     * @modify:
     * @param id
     * @return: void
     */
    @MybatisQuery
    void deleteByMenuId(@Param("id") Long id);
}