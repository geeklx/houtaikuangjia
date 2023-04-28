package com.geek.system.support.system.service.sys;

import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.system.support.system.dao.sys.SysUserRoleScopDao;
import com.geek.system.support.system.entity.sys.SysUserRoleScopEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysUserRoleScopServiceImpl extends AppJPABaseDataServiceImpl<SysUserRoleScopEntity, SysUserRoleScopDao>
	implements SysUserRoleScopService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("userId","userId:EQ");
				put("roleId","roleId:EQ");
				put("orgId","orgId:EQ");
				put("projectId","projectId:EQ");
				put("roleIdNull","roleId:ISNULL");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}

	/**
	 * 描述: 删除角色管理范围
	 * @param searchBindParam
	 * @return void
	 * @author fuhao
	 * @date 2021/12/6 9:09
	 **/
	@Override
	public void deleteUserRoleScope(HashMap<String, Object> searchBindParam) {
		entityDao.deleteUserRoleScope(searchBindParam);
	}

	/**
	 * 描述: 删除角色不等于空的
	 * @param searchBindParam
	 * @return void
	 * @author fuhao
	 * @date 2022/1/13 17:27
	 **/
	@Override
	public void deleteRoleIdIsNot(HashMap<String, Object> searchBindParam) {
		entityDao.deleteRoleIdIsNot(searchBindParam);
	}

	/**
	 * 描述: 查询全部用户绑定的范围
	 * @param searchBindParam
	 * @return java.util.List<com.geek.system.support.system.entity.sys.SysUserRoleScopEntity>
	 * @author fuhao
	 * @date 2021/12/6 9:26
	 **/
	@Override
	public List<SysUserRoleScopEntity> queryAllUserRoleScope(HashMap<String, Object> searchBindParam) {
		return entityDao.queryAllUserRoleScope(searchBindParam);
	}
}
