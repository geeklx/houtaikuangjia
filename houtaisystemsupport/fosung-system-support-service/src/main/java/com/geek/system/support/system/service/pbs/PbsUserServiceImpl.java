package com.geek.system.support.system.service.pbs;

import com.geek.system.support.system.dao.pbs.PbsUserDao;
import com.geek.system.support.system.entity.sys.SysUserEntity;
import com.geek.system.support.system.entity.sys.SysUserRoleScopEntity;
import com.geek.system.support.system.service.sys.SysUserRoleScopService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PbsUserServiceImpl implements PbsUserService {

    @Autowired
    private PbsUserDao pbsUserDao;

    @Autowired
    private SysUserRoleScopService sysUserRoleScopService;

    @Override
    public Page<Map<String, Object>> queryWorkerByScopOrg(Map<String, Object> sysUserDto, Pageable of) {
        return pbsUserDao.queryWorkerByScopOrg(sysUserDto,of);
    }

    /**
     * 查询工作人员
     *
     * @param sysUserDto
     * @return
     */
    @Override
    public int queryWorkerCountByScopOrg(Map<String, Object> sysUserDto) {
        return pbsUserDao.queryWorkerCountByScopOrg(sysUserDto);
    }

    /**
     * 批量删除
     * @param param
     * @return
     */
    @Override
    public int deleteByIds(Map<String, Object> param){
        return pbsUserDao.deleteByIds(param);
    }

    /**
     * 分页查询
     * @param sysUserDto
     * @param of
     * @return
     */
    @Override
    public Page<Map<String, Object>> queryPageManagerUsers(Map<String, Object> sysUserDto, Pageable of) {
        return pbsUserDao.queryPageManagerUsers(sysUserDto,of);
    }
    /**
     * 批量保存
     *
     * @param infos
     * @return
     */
    @Override
    public int saveinfo(List<SysUserEntity> infos) {
        return pbsUserDao.saveinfo(infos);
    }

    @Override
    public List<Map<String, Object>> queryUserRole(Map<String, Object> params) {
        List<Map<String, Object>> map = pbsUserDao.queryUserRole(params);
        for (Map<String, Object> stringObjectMap : map) {
            Map<String,Object> sear = Maps.newHashMap();
            sear.put("shelvesType",stringObjectMap.get("shelvesType")==null?"role":stringObjectMap.get("shelvesType"));
            sear.put("userId",params.get("userId"));
            sear.put("projectId",params.get("projectId"));
            sear.put("roleId",stringObjectMap.get("id"));
            List<SysUserRoleScopEntity> su = sysUserRoleScopService.queryAllUserRoleScope(new HashMap<>(sear));
            stringObjectMap.put("scopOrg",su);
        }

        return map;
    }


}
