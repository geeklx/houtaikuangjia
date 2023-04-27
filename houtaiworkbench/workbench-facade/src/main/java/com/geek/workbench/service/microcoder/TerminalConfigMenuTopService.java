package com.geek.workbench.service.microcoder;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.workbench.dto.microcoder.TerminalConfigMenuTopDto;
import com.geek.workbench.entity.microcoder.TerminalConfigMenuTopEntity;

import java.util.List;
import java.util.Map;

/**
 * @Description 顶部菜单服务层
 * @Author gaojian
 * @Date 2021/11/04 10:25
 * @Version V1.0
 */
public interface TerminalConfigMenuTopService extends AppBaseDataService<TerminalConfigMenuTopEntity, Long> {
	
    /**
     * 描述:  保存顶部菜单
     * @createDate: 2021/11/9 16:44
     * @author: gaojian
     * @modify:
     * @param terminalConfigMenuTopEntity
     * @return: void
     */
    void saveInfo(TerminalConfigMenuTopEntity terminalConfigMenuTopEntity);
    
    /**
     * 描述:  删除菜单
     * @createDate: 2021/11/9 18:12
     * @author: gaojian
     * @modify:
     * @param 
     * @return: void
     */
    void deleteInfo(List<AppBaseIdParam> list);

    /**
     * 获取顶部导航菜单
     * @param menuTopDto
     * @param
     * @return
     */
    List<Map<String, Object>> getMeunList(TerminalConfigMenuTopDto menuTopDto);

    /**
     * 获取顶部导航
     * @return
     */
    List<TerminalConfigMenuTopEntity> getMenuTop(TerminalConfigMenuTopDto dto);
}

