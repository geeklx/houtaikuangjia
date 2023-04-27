package com.geek.system.support.system.service.pbs;


import com.geek.system.support.system.dao.pbs.PbsRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PbsRoleServiceImpl implements PbsRoleService {

    @Autowired
    private PbsRoleDao pbsRoleDao;

    @Override
    public List<Map<String,Object>> queryAppByRole(Map<String,Object> param){
        return pbsRoleDao.queryAppByRole(param);
    }
}
