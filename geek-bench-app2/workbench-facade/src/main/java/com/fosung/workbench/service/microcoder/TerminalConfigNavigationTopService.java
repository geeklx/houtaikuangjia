package com.fosung.workbench.service.microcoder;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.fosung.workbench.dto.microcoder.TerminalConfigMenuTopDto;
import com.fosung.workbench.entity.microcoder.TerminalConfigNavigationTop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 顶部导航服务层
 * @Author gaojian
 * @Date 2021/11/04 10:25
 * @Version V1.0
 */
public interface TerminalConfigNavigationTopService extends AppBaseDataService<TerminalConfigNavigationTop, Long> {

    /**
     * 描述:  查询顶部导航信息
     * @createDate: 2021/11/9 14:53
     * @author: gaojian
     * @modify:
     * @param terminalConfigNavigationTop
     * @return: Page<TerminalConfigNavigationTop>
     */
    List<Map<String,Object>> queryInfo(TerminalConfigNavigationTop terminalConfigNavigationTop);

    /**
     * 描述:  查询顶部导航信息 -- 分页
     * @createDate: 2021/11/9 14:53
     * @author: gaojian
     * @modify:
     * @param terminalConfigNavigationTop
     * @return: Page<TerminalConfigNavigationTop>
     */
    Page<Map<String,Object>> queryInfoPage(TerminalConfigNavigationTop terminalConfigNavigationTop, Pageable pageable);
    
    /**
     * 描述:  保存顶部导航
     * @createDate: 2021/11/8 10:41
     * @author: gaojian
     * @modify:
     * @param terminalConfigNavigationTop
     * @return: void
     */
    void saveInfo(TerminalConfigNavigationTop terminalConfigNavigationTop);

    /**
     * 描述:  修改顶部导航
     * @createDate: 2021/11/8 10:41
     * @author: gaojian
     * @modify:
     * @param terminalConfigNavigationTop
     * @return: void
     */
    void updateInfo(TerminalConfigNavigationTop terminalConfigNavigationTop);
    
    /**
     * 描述:  
     * @createDate: 2021/11/9 15:36
     * @author: gaojian
     * @modify:
     * @param
     * @return: void
     */
    void deleteInfo(List<AppBaseIdParam> list);

    /**
     * 查询地域
     * @return
     */
    List<Map<String,Object>> queryAreaList();

    /**
     * 获取顶部导航
     * @param map
     * @return
     */
    List<TerminalConfigNavigationTop> getNavigationTop(TerminalConfigMenuTopDto dto);
}

