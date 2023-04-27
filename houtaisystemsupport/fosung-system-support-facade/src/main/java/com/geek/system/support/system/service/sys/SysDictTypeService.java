package com.geek.system.support.system.service.sys;

import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.system.support.system.dto.sys.SysDictTypeDto;
import com.geek.system.support.system.entity.sys.SysDictTypeEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;


public interface SysDictTypeService extends AppBaseDataService<SysDictTypeEntity, Long> {


    /**
     * 描述: 字典项分页查询
     * @param searchParam
     * @param pageNum
     * @param pageSize
     * @return org.springframework.data.domain.Page<com.fosung.system.support.system.entity.sys.SysDictTypeEntity>
     * @author fuhao
     * @date 2022/1/18 15:52
     **/
    Page<SysDictTypeEntity> queryPage(Map<String, Object> searchParam, Integer pageNum, Integer pageSize);

    /**
     * 描述: 级联删除字典
     * @param collect
     * @return void
     * @author fuhao
     * @date 2022/1/19 11:08
     **/
    void deleteInfo(List<Long> collect);

    /**
     * 描述: 复制功能
     * @param list
     * @return void
     * @author fuhao
     * @date 2022/1/19 15:57
     **/
    void copy(SysDictTypeDto list);

    /**
     * 根据类型查询字典
     * @param params
     * @return
     */
    List<Map<String,Object>> queryDictByType(Map<String, Object> params);


    /**
     * 批量插入
     * @param infos
     */
    void saveinfo( List<SysDictTypeEntity> infos);
}

