package com.fosung.system.support.system.service.pbs;


import com.fosung.system.support.system.dao.pbs.PbsOrgDao;
import com.fosung.system.support.system.entity.sys.SysOrgEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PbsOrgServiceImpl implements PbsOrgService {

    @Autowired
    private PbsOrgDao pbsOrgDao;

    /**
     * 查询组织
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> queryOrgBylevel(Map<String, Object> param) {
        return pbsOrgDao.queryOrgBylevel(param);
    }

    /**
     * 查询组织总数
     *
     * @param param
     * @return
     */
    @Override
    public int queryOrgCountBylevel(Map<String, Object> param) {
        return pbsOrgDao.queryOrgCountBylevel(param);
    }


    /**
     * 分页查询组织
     * @param searchParam
     * @param pageable
     * @return
     */
    @Override
    public Page<SysOrgEntity> queryByPage(Map<String, Object> searchParam, Pageable pageable) {
        return pbsOrgDao.queryByPage(searchParam,pageable);
    }

    /**
     * 批量删除
     * @param param
     * @return
     */
    @Override
    public int deleteByIds(Map<String, Object> param){
        return pbsOrgDao.deleteByIds(param);
    }

    /**
     * 批量保存
     *
     * @param infos
     * @return
     */
    @Override
    public int saveinfo(List<SysOrgEntity> infos) {
        return pbsOrgDao.saveinfo(infos);
    }
}
