package com.fosung.system.pbs.api.impl;

import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.system.pbs.api.SystemRoleApi;
import com.fosung.system.pbs.dto.AppSysRoleDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractSystemRoleApi extends AppIBaseController implements SystemRoleApi {
    @Override
    public ResponseParam getRole() {
        return null;
    }

    @Override
    public ResponseParam getRoleUser(Long appId,String roleCode) {
        return null;
    }

    @Override
    public ResponseParam getRoleByApp(Long appId) {
        return null;
    }

    @Override
    public ResponseParam getRoleByParam(@PathVariable("project") String project, @RequestBody AppSysRoleDto sysRoleDto){
        return null;
    }

    @Override
    public ResponseParam queryAppByRole(@PathVariable("project") String project, @RequestParam("roleId") Long roleId){
        return null;
    }
}
