package com.fosung.workbench.service.sys;

import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.common.ProjectStatusEnum;
import com.fosung.workbench.dao.sys.SysDictDao;
import com.fosung.workbench.entity.project.ProjectBasicEntity;
import com.fosung.workbench.entity.sys.SysDictEntity;
import com.fosung.workbench.entity.ums.TerminalPersonEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lihuiming
 * @className DictServiceImpl
 * @description: 字典服务
 * @date 2022/5/615:24
 */
@Service
public class SysDictServiceImpl extends AppJPABaseDataServiceImpl<SysDictEntity, SysDictDao>
        implements SysDictService {
    /**
     * 查询条件表达式
     */
    private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
        {
            put("dictName","dictName:EQ");
            put("dictCode","dictCode:EQ");
            put("dictValue","dictValue:EQ");
            put("remark","remark:EQ");
            put("status","status:EQ");
            put("startTime", "createDatetime:GTEDATE");
            put("endTime", "createDatetime:LTEDATE");
        }
    };
    @Override
    public Map<String, String> getQueryExpressions() {
        return expressionMap ;
    }

    /**
     * 保存字典信息
     * @param sysDictEntity
     */
    @Override
    public void saveInfo(SysDictEntity sysDictEntity) {
        Map<String,Object> searchParams = new HashMap<>(8);
        searchParams.put("dictCode",sysDictEntity.getDictCode());
        List<SysDictEntity> list = this.queryAll(searchParams);
        // 1. 判断项目编码是否已存在
        if(!list.isEmpty()){
            throw new AppException(MessageContent.DICT_CODE_IS_EXIST);
        }

        // 2. 保存项目
        save(sysDictEntity);
    }
}
