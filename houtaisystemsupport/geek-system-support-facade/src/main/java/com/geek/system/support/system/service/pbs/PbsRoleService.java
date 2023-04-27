package com.geek.system.support.system.service.pbs;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PbsRoleService {

    List<Map<String,Object>> queryAppByRole(Map<String,Object> param);
}

