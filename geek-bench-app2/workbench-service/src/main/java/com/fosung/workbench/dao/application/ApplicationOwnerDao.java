package com.fosung.workbench.dao.application;

import com.fosung.framework.dao.jpa.AppJPABaseDao;
import com.fosung.framework.dao.jpa.annotation.MybatisQuery;
import com.fosung.workbench.entity.application.ApplicationOwnerEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ApplicationOwnerDao extends AppJPABaseDao<ApplicationOwnerEntity, Long>{
    @MybatisQuery
    List<ApplicationOwnerEntity> queryMyApp(@Param("params") Long terminalId,Long userId );

}