package com.fosung.system.support.system.service.sys;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fosung.framework.common.util.UtilBean;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.system.support.system.dao.sys.SysDictTypeDao;
import com.fosung.system.support.system.dict.DictStatus;
import com.fosung.system.support.system.dto.sys.SysDictTypeDto;
import com.fosung.system.support.system.entity.sys.SysDictDataEntity;
import com.fosung.system.support.system.entity.sys.SysDictTypeEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

@Service
public class SysDictTypeServiceImpl extends AppJPABaseDataServiceImpl<SysDictTypeEntity, SysDictTypeDao>
        implements SysDictTypeService {

    @Autowired
    private SysDictDataService sysDictDataService;

    /**
     * 查询条件表达式
     */
    private Map<String, String> expressionMap = new LinkedHashMap<String, String>() {
        {
            put("dictName", "dictName:EQ");
            put("dictNameL", "dictNameL:LIKE");
            put("dictType", "dictType:EQ");
            put("dictTypes", "dictType:IN");
            put("projectId", "projectId:EQ");
            put("id", "id:EQ");
            put("ids", "id:IN");
            put("status","status:EQ");
        }
    };

    @Override
    public Map<String, String> getQueryExpressions() {
        return expressionMap;
    }


    /**
     * 描述: 字典项分页查询
     *
     * @param searchParam
     * @param pageNum
     * @param pageSize
     * @return org.springframework.data.domain.Page<com.fosung.system.support.system.entity.sys.SysDictTypeEntity>
     * @author fuhao
     * @date 2022/1/18 15:52
     **/
    @Override
    public Page<SysDictTypeEntity> queryPage(Map<String, Object> searchParam, Integer pageNum, Integer pageSize) {
        Pageable pageable = MybatisPageRequest.of(pageNum, pageSize);
        return entityDao.queryPage(searchParam, pageable);
    }

    /**
     * 描述: 级联删除字典
     *
     * @param collect
     * @return void
     * @author fuhao
     * @date 2022/1/19 11:08
     **/
    @Override
    public void deleteInfo(List<Long> collect) {
        // 删除字典
        this.delete(collect);
        // 删除字典项
        Map<String, Object> delMap = Maps.newHashMap();
        delMap.put("ids", collect);
        List<SysDictTypeEntity> sysDictTypeEntities = this.queryAll(delMap);
        sysDictTypeEntities.forEach(type -> {
            HashMap<String, Object> dataMap = Maps.newHashMap();
            dataMap.put("projectId",type.getProjectId());
            dataMap.put("dictType",type.getDictType());
            List<SysDictDataEntity> sysDictDataEntities = sysDictDataService.queryAll(dataMap);
            if (UtilCollection.isNotEmpty(sysDictDataEntities)) {
                sysDictDataService.delete(sysDictDataEntities.stream().map(map -> map.getId()).collect(Collectors.toList()));
            }
        });
    }

    /**
     * 描述: 复制功能
     *
     * @param dto
     * @return void
     * @author fuhao
     * @date 2022/1/19 15:57
     **/
    @Override
    public void copy(SysDictTypeDto dto) {

        HashMap<String, Object> existParam = Maps.newHashMap();
        existParam.put("projectId",dto.getTargetProjectId());
        existParam.put("dictTypes",dto.getDictTypes());
        List<SysDictTypeEntity> exist = this.queryAll(existParam);
        List<String> existType= Lists.newArrayList();
        if(UtilCollection.isNotEmpty(exist)){
            StringBuilder builder = new StringBuilder("复制失败;目标租户中:");
            exist.forEach(e -> {
                existType.add(e.getDictType());
            });
        }
        // 获取字典
        HashMap<String, Object> typeParam = Maps.newHashMap();
        typeParam.put("projectId", dto.getProjectId());
        typeParam.put("dictTypes", dto.getDictTypes());
        List<SysDictTypeEntity> sysDictTypeEntities = this.queryAll(typeParam);
        for (SysDictTypeEntity sysDictTypeEntity : sysDictTypeEntities) {
            if(existType.contains(sysDictTypeEntity.getDictType())){
                continue;
            }
            // 保存字典
            SysDictTypeEntity sysDictType = UtilBean.copyBean(sysDictTypeEntity, SysDictTypeEntity.class);
            sysDictType.setStatus(DictStatus.normal);
            sysDictType.setId(null);
            sysDictType.setProjectId(dto.getTargetProjectId());
            this.save(sysDictType);
        }
        // 获取字典项
        List<SysDictDataEntity> sysDictDataEntities = sysDictDataService.queryAll(typeParam);
        List<String> targetDictData = sysDictDataService.queryAll(existParam).stream().map(map->{
            return map.getDictType()+map.getDictValue();
        }).collect(Collectors.toList());
        for (SysDictDataEntity sysDictDataEntity : sysDictDataEntities) {
            if(targetDictData.contains(sysDictDataEntity.getDictType()+sysDictDataEntity.getDictValue())){
                continue;
            }
            SysDictDataEntity sysDictData = UtilBean.copyBean(sysDictDataEntity, SysDictDataEntity.class);
            sysDictData.setStatus(DictStatus.normal);
            sysDictData.setId(null);
            sysDictData.setProjectId(dto.getTargetProjectId());
            sysDictDataService.save(sysDictData);
        }
    }

    /**
     * 查询字典
     * @param params
     * @return
     */
    @Override
    public List<Map<String,Object>> queryDictByType(Map<String, Object> params){
        return entityDao.queryDictByType(params);
    }

    /**
     * 批量插入
     * @param infos
     */
    public void saveinfo( List<SysDictTypeEntity> infos){
        entityDao.saveinfo(infos);
    }
}
